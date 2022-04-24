package com.lots.lots.vehicle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.lots.lots.admin.feign.RegionProvinceFeign;
import com.lots.lots.admin.order.dao.InspectionVehicleMapper;
import com.lots.lots.admin.order.dao.OrderHistoryDetailsMapper;
import com.lots.lots.admin.order.dao.OrderRecordMapper;
import com.lots.lots.admin.order.dao.RentalOrderMapper;
import com.lots.lots.admin.order.dto.po.OrderRecord;
import com.lots.lots.admin.order.dto.po.RentalOrder;
import com.lots.lots.admin.order.service.OrderRecordService;
import com.lots.lots.admin.order.service.RentalOrderService;
import com.lots.lots.admin.payment.service.CallBackService;
import com.lots.lots.admin.shop.dao.DeliveryMapper;
import com.lots.lots.admin.vehicle.dao.RentListMapper;
import com.lots.lots.admin.vehicle.dao.VehicleModelMapper;
import com.lots.lots.admin.vehicle.dto.po.RentList;
import com.lots.lots.admin.vehicle.dto.vo.VehicleModelVO;
import com.lots.lots.admin.vehicle.service.ReturnVehicleService;
import com.lots.lots.common.constant.CallBackConstant;
import com.lots.lots.common.constant.ShouQianBaConstant;
import com.lots.lots.common.entity.JsonResult;
import com.lots.lots.common.entity.common.CouponVo;
import com.lots.lots.common.entity.order.*;
import com.lots.lots.common.entity.payment.shouqianba.DataVO;
import com.lots.lots.common.entity.payment.shouqianba.Reflect;
import com.lots.lots.common.entity.shop.DeliveryVo;
import com.lots.lots.common.entity.vehicle.GetVehiclePriceVo;
import com.lots.lots.common.entity.vehicle.RentListVo;
import com.lots.lots.common.entity.vehicle.ReturnVehicleVO;
import com.lots.lots.common.entity.vehicle.VehicleModelVo;
import com.lots.lots.common.enums.order.DepositTypeEnum;
import com.lots.lots.common.enums.order.OrderRecordStatusEnum;
import com.lots.lots.common.enums.vehicle.IsChangeVehicleEnum;
import com.lots.lots.common.util.ShouQianBaUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 检验收车
 */
@Service(CallBackConstant.RETURN_CAR)
public class ReturnVehicleServiceImpl implements ReturnVehicleService, CallBackService<DataVO, String> {
    @Resource
    private RentalOrderService rentalOrderService;
    @Resource
    private RentalOrderMapper rentalOrderMapper;
    @Resource
    private InspectionVehicleMapper inspectionVehicleMapper;
    @Resource
    private OrderRecordMapper orderRecordMapper;
    @Resource
    private OrderRecordService orderRecordService;
    @Resource
    private VehicleModelMapper vehicleModelMapper;
    @Resource
    private RentListMapper rentListMapper;
    @Resource
    private OrderHistoryDetailsMapper orderHistoryDetailsMapper;
    @Resource
    private RegionProvinceFeign regionProvinceFeign;
    @Resource
    private DeliveryMapper deliveryMapper;

    @Override
    public JsonResult<ReturnVehicleVO> data(Long orderId) {
        RentalOrder rentalOrder = this.rentalOrderService.selectByPrimaryKey(orderId);
        if (ObjectUtil.isNull(rentalOrder)) {
            return JsonResult.failed("订单不存在");
        }
        ReturnVehicleVO.ReturnVehicleVOBuilder builder = ReturnVehicleVO.builder();
        builder.carRentalDeposit(rentalOrder.getRetreatRentalDeposit())
                .paymentMethod(rentalOrder.getDepositType());
        if (DepositTypeEnum.FULL_WAIVED_DEPOSIT.getId() == rentalOrder.getDepositType()) {
            builder.preAcceptanceFreeze(rentalOrder.getRetreatRentalDeposit());
        } else {
            builder.preAcceptanceFreeze(BigDecimal.ZERO);
        }
        LocalDateTime now = LocalDateTime.now();
        VehicleModelVO vehicleModelVO = this.vehicleModelMapper.findById(rentalOrder.getVehicleModelId());
        // 如果还车时间大于租车结束时间则计算超时费
        if (now.isAfter(rentalOrder.getRentEndTime())) {
            builder.overtimeFee(getOvertimeFee(orderId, rentalOrder, now));
        }
        // 如果还车时间小于租车结束时间则计算提前还车费用
        if (now.isBefore(rentalOrder.getRentEndTime())) {
            Long count = this.orderRecordMapper.countByOrderIdAndTypeAndStatus(orderId, Math.toIntExact(OrderEnum.renewalFee.id), OrderRecordStatusEnum.SUCCESS.getStatus());
            // 判断有无换过车，换过车就直接计算新换车辆的提前还车费用
            BigDecimal returnTheCarEarlyAmount = BigDecimal.ZERO;
            if (rentalOrder.getIsChange() == IsChangeVehicleEnum.CHANGED.ordinal()) {
                OrderRecord orderRecord = this.orderRecordMapper.findFirstByOrderIdAndTypeAndStatusOrderByCreateTimeDesc(rentalOrder.getId(), Math.toIntExact(OrderEnum.exchangeFee.id), OrderRecordStatusEnum.SUCCESS.getStatus());
                returnTheCarEarlyAmount = earlyReturnFee(rentalOrder, now, vehicleModelVO, orderRecord.getInfo().getInfo());
            }
            // 续租过后提前还车
            else if (count > 0) {
                OrderRecord orderRecord = this.orderRecordMapper.findFirstByOrderIdAndTypeAndStatusOrderByCreateTimeDesc(rentalOrder.getId(), Math.toIntExact(OrderEnum.renewalFee.id), OrderRecordStatusEnum.SUCCESS.getStatus());
                returnTheCarEarlyAmount = earlyReturnFee(rentalOrder, now, vehicleModelVO, orderRecord.getInfo().getInfo());
            } else {
                OrderHistoryDetailsVo orderHistoryDetailsVo = this.orderHistoryDetailsMapper.findByOrderId(orderId);
                returnTheCarEarlyAmount = earlyReturnFee(rentalOrder, now, vehicleModelVO, orderHistoryDetailsVo.getInfo());
            }
            builder.returnTheCarEarly(returnTheCarEarlyAmount);
        }
        builder.illegalDeposit(vehicleModelVO.getBreakRulesMoney());
        List<AccidentVo> accidentVos = this.inspectionVehicleMapper.findByOrderId(orderId);
        builder.accidentVo(accidentVos);
        builder.orderId(orderId);
        // 总计
        ReturnVehicleVO returnVehicleVO = builder.build();
        return JsonResult.success(returnVehicleVO);
    }

    private BigDecimal earlyReturnFee(RentalOrder rentalOrder, LocalDateTime now, VehicleModelVO vehicleModelVO, OrderPriceInfo orderPriceInfo) {
        LocalDateTime midnightTimeOfTheDay = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);
        Duration between = Duration.between(midnightTimeOfTheDay, now);
        // 超过4小时就按天算，不计算零散小时费了，直接时间+1天从第二天开始算
        int maximumScatteredHourlyTime = 4;
        if (between.toHours() >= maximumScatteredHourlyTime) {
            now = midnightTimeOfTheDay.plusDays(1);
        }
        CreateOrderVo createOrderVo = CreateOrderVo.builder()
                .rentBeginTime(Date.from(now.toInstant(ZoneOffset.ofHours(8))))
                .rentEndTime(Date.from(rentalOrder.getRentEndTime().toInstant(ZoneOffset.ofHours(8))))
                .pickPlace(rentalOrder.getPickPlace())
                .returnPlace(rentalOrder.getReturnPlace())
                .isInsurance(rentalOrder.getIsInsurance())
                .build();
        //查询交易流水, 如果换车记录不存在，则直接取明细，换车记录存在则第一条是上一次已换车辆的历史信息详情
        List<RentListVo> rentListVos = getRentListVos(orderPriceInfo, now);
        DeliveryVo deliveryVo = deliveryMapper.findById(rentalOrder.getPickPlace());
        OrderPriceInfo vehiclePrice = this.regionProvinceFeign.getVehiclePrice(
                new GetVehiclePriceVo(
                        deliveryVo
                        , createOrderVo
                        , BeanUtil.copyProperties(vehicleModelVO, VehicleModelVo.class)
                        , rentListVos
                        , getCouponVos(rentalOrder, orderPriceInfo))
        );
        return vehiclePrice.getTotal().negate();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult<ReturnVehicleVO> settleAccounts(ReturnVehicleVO returnVehicleVO) {
        List<OrderPrice> orderPriceList = new ArrayList<>();
        // 超时费
        orderPriceList.add(OrderPrice.builder()
                .id(OrderEnum.overtimeFee.id)
                .name(OrderEnum.overtimeFee.name)
                .price(returnVehicleVO.getOvertimeFee())
                .build());
        // 提前还车
        orderPriceList.add(OrderPrice.builder()
                .id(OrderEnum.returnTheCarEarly.id)
                .name(OrderEnum.returnTheCarEarly.name)
                .price(returnVehicleVO.getReturnTheCarEarly())
                .build());
        // 违约金
        orderPriceList.add(OrderPrice.builder()
                .id(OrderEnum.liquidatedDamages.id)
                .name(OrderEnum.liquidatedDamages.name)
                .price(returnVehicleVO.getLiquidatedDamages())
                .build());
        // 滞纳金
        orderPriceList.add(OrderPrice.builder()
                .id(OrderEnum.penalty.id)
                .name(OrderEnum.penalty.name)
                .price(returnVehicleVO.getPenalty())
                .build());
        // 其他费用
        orderPriceList.add(OrderPrice.builder()
                .id(OrderEnum.otherFeesReturnCarPrice.id)
                .name(OrderEnum.otherFeesReturnCarPrice.name)
                .price(returnVehicleVO.getOtherFee())
                .build());
        OrderPriceInfo orderPriceInfo = OrderPriceInfo.builder()
                .orderPriceList(orderPriceList)
                .build();
        OrderHistoryDetailsVo orderHistoryDetailsVo = OrderHistoryDetailsVo.builder()
                .orderId(returnVehicleVO.getOrderId())
                .info(orderPriceInfo)
                .build();
        OrderRecordVO orderRecordVO = OrderRecordVO.builder()
                .amount(returnVehicleVO.getTotalAmount())
                .orderId(returnVehicleVO.getOrderId())
                .info(orderHistoryDetailsVo)
                .type(Math.toIntExact(OrderEnum.returnSettlement.id))
                .build();
        // 创建客户端需要的结算流水
        this.orderRecordService.createOrderRecord(orderRecordVO);
        // 支付回传对象
        Reflect reflect = new Reflect()
                .setOrderId(returnVehicleVO.getOrderId())
                .setCallBackBeanName(CallBackConstant.RETURN_CAR)
                .setOrderRecordType(OrderEnum.collectionOfIllegalDeposit.id);
        ReturnVehicleVO result = ReturnVehicleVO.builder()
                .reflect(reflect)
                .build();
        return JsonResult.success(result);
    }


    /**
     * 获取优惠券列表
     */
    private List<CouponVo> getCouponVos(RentalOrder rentalOrder, OrderPriceInfo orderPriceInfo) {
        return orderPriceInfo.getOrderPriceList().stream()
                .filter(orderPrice -> OrderEnum.useCouponPrice.id.equals(orderPrice.getId()))
                .map(orderPrice -> CouponVo.builder()
                        .id(rentalOrder.getCouponId())
                        .discountAmount(rentalOrder.getCouponMoney())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 获取租金列表
     */
    private List<RentListVo> getRentListVos(OrderPriceInfo orderPriceInfo, LocalDateTime now) {
        List<VehicleDatePrice> actualHistoryPriceList = orderPriceInfo.getOrderVehiclePrice().getActualHistoryPriceList();
        return actualHistoryPriceList.stream()
                .map(vehicleDatePrice -> RentListVo.builder()
                        .rentalTime(vehicleDatePrice.getTime())
                        .rentalMoney(vehicleDatePrice.getPrice())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 计算超时费用
     */
    private BigDecimal getOvertimeFee(Long orderId, RentalOrder rentalOrder, LocalDateTime now) {
        /*
          ·租期不足24小时的部分，半小时收取零散小时费:当日租金/8
          ·1小时收取零散小时费:当日租金/4
          ·2小时收取零散小时费:当日租金/2
          ·3小时收取零散小时费:3*当日租金/4
          ·4小时及以上:当日租金
          若有小数向上取整
         */
        List<RentList> rentLists = new ArrayList<>();
        // 结束当天的价格用下单时的价格计算
        OrderHistoryDetailsVo historyDetailsVo = orderHistoryDetailsMapper.findByOrderId(orderId);
        Optional<VehicleDatePrice> first = historyDetailsVo.getInfo()
                .getOrderVehiclePrice()
                .getActualHistoryPriceList()
                .stream()
                .filter(vehicleDatePrice -> DateUtil.isSameDay(vehicleDatePrice.getTime(), Date.from(rentalOrder.getRentEndTime().atOffset(ZoneOffset.ofHours(8)).toInstant())))
                .findFirst();
        if (first.isPresent()) {
            VehicleDatePrice vehicleDatePrice = first.get();
            RentList rentList = new RentList();
            rentList.setRentalTime(vehicleDatePrice.getTime().toInstant().atOffset(ZoneOffset.ofHours(8)).toLocalDateTime());
            rentList.setRentalMoney(vehicleDatePrice.getPrice());
            rentList.setMemberShopId(rentalOrder.getMemberShopId());
            rentList.setModelId(rentalOrder.getVehicleModelId());
            rentLists.add(0, rentList);
        }
        // 其他日租金按实时价格
        LocalDateTime tomorrow = rentalOrder.getRentEndTime().plusDays(1);
        List<RentList> realTimePriceList = this.rentListMapper.findAllByRentalTimeAndMemberShopIdAndModelId(tomorrow, now, rentalOrder.getVehicleModelId(), rentalOrder.getMemberShopId());
        rentLists.addAll(realTimePriceList);
        // 超时费
        BigDecimal overtimeFee = BigDecimal.ZERO;
        Duration between = Duration.between(rentalOrder.getRentEndTime(), now);
        long betweenMinute = between.toMinutes();
        long fourHourMinute = 240L;
        // 2小时收取零散小时费：当日租金/2 3小时收取零散小时费：3*当日租金/4 4小时及以上：当日租金
        VehicleModelVO vehicleModelVO = this.vehicleModelMapper.findById(rentalOrder.getVehicleModelId());
        LocalDateTime rentEndTime = rentalOrder.getRentEndTime();
        do {
            if (betweenMinute > fourHourMinute) {
                overtimeFee = overtimeFee.add(toDayPrice(rentEndTime, vehicleModelVO.getWeekExternal(), vehicleModelVO.getWeekWithin(), rentLists));
            } else {
                overtimeFee = overtimeFee.add(overtimeFee.multiply(new BigDecimal(betweenMinute)
                        .divide(new BigDecimal(30), 2, RoundingMode.HALF_UP))
                        .divide(new BigDecimal(8), 0, BigDecimal.ROUND_UP));
            }
            rentEndTime = rentEndTime.plusDays(1);
        } while (rentEndTime.isBefore(now));
        return overtimeFee;
    }


    /**
     * 通过日期算出该车该天的租金
     */
    private BigDecimal toDayPrice(LocalDateTime today, BigDecimal weekExternal, BigDecimal weekWithin, List<RentList> rentLists) {
        Optional<RentList> rentListOptional = rentLists.stream().filter(rentList -> rentList.getRentalTime().isEqual(today)).findFirst();
        if (rentListOptional.isPresent()) {
            return rentListOptional.get().getRentalMoney();
        }
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        if (DayOfWeek.SUNDAY.getValue() == dayOfWeek.getValue() || DayOfWeek.SATURDAY.getValue() == dayOfWeek.getValue()) {
            return weekExternal;
        }
        return weekWithin;
    }

    @Override
    public String callBack(DataVO vo) {
        JsonResult<Object> objectJsonResult = ShouQianBaUtil.checkUpayV2Precreate(vo);
        // 成功
        RentalOrder rentalOrder = this.rentalOrderService.selectByPrimaryKey(vo.getReflect().getOrderId());
        OrderRecord orderRecord = this.orderRecordMapper.selectByPrimaryKey(vo.getReflect().getRecordSn());
        if (HttpStatus.HTTP_OK == objectJsonResult.getCode()) {
            if (ObjectUtil.isNotNull(orderRecord)) {
                orderRecord.setStatus(OrderRecordStatusEnum.SUCCESS.getStatus());
                rentalOrder.setActualRentalDeposit(orderRecord.getAmount());
                this.orderRecordMapper.updateByPrimaryKeySelective(orderRecord);
            }
        } else {
            if (ObjectUtil.isNotNull(orderRecord)) {
                orderRecord.setStatus(OrderRecordStatusEnum.FAILURE.getStatus());
            }
        }
        this.rentalOrderMapper.update(BeanUtil.copyProperties(rentalOrder, RentalOrderVo.class));
        return ShouQianBaConstant.RECEIVE_CALLBACK_RETURN_VALUE;
    }
}
