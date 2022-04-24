package com.lots.lots.vehicle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.lots.lots.admin.feign.RegionProvinceFeign;
import com.lots.lots.admin.order.dao.OrderHistoryDetailsMapper;
import com.lots.lots.admin.order.dao.OrderRecordMapper;
import com.lots.lots.admin.order.dto.po.OrderRecord;
import com.lots.lots.admin.order.dto.po.RentalOrder;
import com.lots.lots.admin.order.repository.RentalOrderRepository;
import com.lots.lots.admin.order.service.OrderRecordService;
import com.lots.lots.admin.order.service.RentalOrderService;
import com.lots.lots.admin.payment.service.CallBackService;
import com.lots.lots.admin.shop.dao.DeliveryMapper;
import com.lots.lots.admin.user.service.LotsUserService;
import com.lots.lots.admin.vehicle.dao.VehicleMapper;
import com.lots.lots.admin.vehicle.dto.po.Vehicle;
import com.lots.lots.admin.vehicle.dto.po.VehicleModel;
import com.lots.lots.admin.vehicle.dto.po.VehicleRentalPlan;
import com.lots.lots.admin.vehicle.repository.VehicleModelRepository;
import com.lots.lots.admin.vehicle.repository.VehicleRentalPlanRepository;
import com.lots.lots.admin.vehicle.service.TransferService;
import com.lots.lots.common.constant.CallBackConstant;
import com.lots.lots.common.constant.ShouQianBaConstant;
import com.lots.lots.common.entity.JsonResult;
import com.lots.lots.common.entity.common.CouponVo;
import com.lots.lots.common.entity.order.*;
import com.lots.lots.common.entity.payment.shouqianba.DataVO;
import com.lots.lots.common.entity.payment.shouqianba.Reflect;
import com.lots.lots.common.entity.payment.shouqianba.ResponseVO;
import com.lots.lots.common.entity.shop.DeliveryVo;
import com.lots.lots.common.entity.vehicle.GetVehiclePriceVo;
import com.lots.lots.common.entity.vehicle.RentListVo;
import com.lots.lots.common.entity.vehicle.TransferVO;
import com.lots.lots.common.entity.vehicle.VehicleModelVo;
import com.lots.lots.common.enums.DeleteEnum;
import com.lots.lots.common.enums.VehicleRentalPlanStatusEnum;
import com.lots.lots.common.enums.order.OrderRecordStatusEnum;
import com.lots.lots.common.enums.order.OrderStatusEnum;
import com.lots.lots.common.util.ShouQianBaUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 换车
 */
@Service(CallBackConstant.TRANSFER)
public class TransferServiceImpl implements TransferService, CallBackService<DataVO, String> {
    @Resource
    private RentalOrderService rentalOrderService;
    @Resource
    private RentalOrderRepository rentalOrderRepository;
    @Resource
    private VehicleMapper vehicleMapper;
    @Resource
    private OrderHistoryDetailsMapper orderHistoryDetailsMapper;
    @Resource
    private RegionProvinceFeign regionProvinceFeign;
    @Resource
    private DeliveryMapper deliveryMapper;
    @Resource
    private VehicleModelRepository vehicleModelRepository;
    @Value("${car.price.pickAndReturnAtNightPrice}")
    private BigDecimal pickAndReturnAtNightPrice;
    @Value("${car.price.insuranceDayPrice}")
    private BigDecimal insuranceDayPrice;
    @Resource
    private VehicleRentalPlanRepository vehicleRentalPlanRepository;
    @Resource
    private LotsUserService lotsUserService;
    @Resource
    private OrderRecordMapper orderRecordMapper;
    @Resource
    private OrderRecordService orderRecordService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult<BigDecimal> calcDifferencePrice(TransferVO transferVO) {
        /* 查询订单信息*/
        RentalOrder rentalOrder = this.rentalOrderService.selectByPrimaryKey(transferVO.getRentalOrderId());
        if (ObjectUtil.isNull(rentalOrder)) {
            return JsonResult.failed("订单号错误");
        }
        if (transferVO.getBeginTime().isAfter(rentalOrder.getRentEndTime())) {
            return JsonResult.failed("换车开始时间不能大于上一次租车的结束时间");
        }
        if (transferVO.getEndTime().isBefore(rentalOrder.getRentEndTime())) {
            return JsonResult.failed("换车结束时间不能早于上一次租车结束时间");
        }
        LocalDateTime transferBeginTime = LocalDateTime.now();
        if (transferVO.getEndTime().isBefore(transferBeginTime)) {
            return JsonResult.failed("还车时间不能小于当前时间");
        }
        // 判断是否续租, 续租就不能换车了
        Long count = this.orderRecordMapper.countByOrderIdAndTypeAndStatus(rentalOrder.getId(), Math.toIntExact(OrderEnum.renewalFee.id), OrderRecordStatusEnum.SUCCESS.getStatus());
        if (count > 0) {
            return JsonResult.failed("续租以后不能换车");
        }
        /*计算在用车辆已使用时间价格*/
        DeliveryVo deliveryVo = deliveryMapper.findById(transferVO.getDeliveryId());
        // 查询成功的最新的换车记录
        OrderRecord orderRecord = this.orderRecordMapper.findFirstByOrderIdAndTypeAndStatusOrderByCreateTimeDesc(transferVO.getRentalOrderId(), Math.toIntExact(OrderEnum.exchangeFee.id), OrderRecordStatusEnum.SUCCESS.getStatus());
        // 如果一次没有换过,则查询第一次收取租金的记录
        if (ObjectUtil.isNull(orderRecord)) {
            this.orderRecordMapper.findFirstByOrderIdAndTypeAndStatusOrderByCreateTimeDesc(transferVO.getRentalOrderId(), Math.toIntExact(OrderEnum.rent.id), OrderRecordStatusEnum.SUCCESS.getStatus());
        }
        OrderHistoryDetailsVo orderHistoryDetailsVo = orderRecord.getInfo();
        OrderPriceInfo inUseVehiclePriceInfo = getInUseVehicleOrderPriceInfo(transferVO, rentalOrder, deliveryVo, transferBeginTime, orderHistoryDetailsVo);
        /*计算换车车辆价格*/
        OrderPriceInfo transferVehicleOrderPriceInfo = getTransferVehicleOrderPriceInfo(transferVO, rentalOrder, deliveryVo, transferBeginTime, orderHistoryDetailsVo);
        /* 计算两辆车的差价,返回的价格是包含了所有费用的价格部分价格需要扣除 */
        //差价 = [总计价格]-[更换前车辆已收取费用]-[优惠券价格]
        BigDecimal differenceOfPrices = transferVehicleOrderPriceInfo.getTotal().add(inUseVehiclePriceInfo.getTotal()).subtract(rentalOrder.getPayMoney());
        //如果换车时间有夜间费用，在用车辆和换车车辆只收取一次
        final long returnAtNightPrice = inUseVehiclePriceInfo.getOrderPriceList().stream().filter(orderPrice -> Objects.equals(OrderEnum.returnAtNightPrice.id, orderPrice.getId())).count();
        final long pickAtNightPrice = transferVehicleOrderPriceInfo.getOrderPriceList().stream().filter(orderPrice -> Objects.equals(OrderEnum.pickAtNightPrice.id, orderPrice.getId())).count();
        if (returnAtNightPrice > 0 && pickAtNightPrice > 0) {
            //扣除一次夜间取/还车费用,只收取一次
            differenceOfPrices = differenceOfPrices.subtract(pickAndReturnAtNightPrice);
        }
        //换车的还车点默认为在用车辆的还车点，如果有异地还车费用只计算一次
        if (ObjectUtil.notEqual(rentalOrder.getPickPlace(), rentalOrder.getReturnPlace())) {
            //扣除一次异地还车费用
            Optional<BigDecimal> diffDeliveryPrice = transferVehicleOrderPriceInfo.getOrderPriceList().stream()
                    .filter(orderPrice -> Objects.equals(OrderEnum.deliveryDistancePrice.id, orderPrice.getId()))
                    .map(OrderPrice::getPrice).findFirst();
            if (diffDeliveryPrice.isPresent()) {
                differenceOfPrices = differenceOfPrices.subtract(diffDeliveryPrice.get());
            }
        }
        //驾无忧只收取换车超出天数的费用, 不足天数的部分退还
        BigDecimal numberOfDaysPaid = BigDecimal.valueOf(LocalDateTimeUtil.between(rentalOrder.getRentBeginTime(), rentalOrder.getRentEndTime()).toDays() + 1);
        BigDecimal actualNumberOfDaysPayable = BigDecimal.valueOf(LocalDateTimeUtil.between(rentalOrder.getRentBeginTime(), transferVO.getEndTime()).toDays() + 1);
        BigDecimal differenceInCostDays = actualNumberOfDaysPayable.subtract(numberOfDaysPaid);
        if (!differenceInCostDays.equals(BigDecimal.ONE)) {
            differenceOfPrices = differenceOfPrices.add(insuranceDayPrice.multiply(differenceInCostDays));
        }
        return JsonResult.success(differenceOfPrices);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult<TransferVO> commit(TransferVO transferVO) {
        RentalOrder rentalOrder = this.rentalOrderService.selectByPrimaryKey(transferVO.getRentalOrderId());
        if (ObjectUtil.isNull(rentalOrder)) {
            return JsonResult.failed("订单号错误");
        }
        /* 查询订单信息*/
        if (transferVO.getBeginTime().isAfter(rentalOrder.getRentEndTime())) {
            return JsonResult.failed("换车开始时间不能大于上一次租车的结束时间");
        }
        if (transferVO.getEndTime().isBefore(rentalOrder.getRentEndTime())) {
            return JsonResult.failed("换车结束时间不能早于上一次租车结束时间");
        }
        /*计算在用车辆已使用时间价格*/
        DeliveryVo deliveryVo = deliveryMapper.findById(transferVO.getDeliveryId());
        LocalDateTime transferBeginTime = LocalDateTime.now();
        OrderHistoryDetailsVo orderHistoryDetailsVo = this.orderHistoryDetailsMapper.findByOrderId(transferVO.getRentalOrderId());
        OrderPriceInfo inUseVehicleOrderPriceInfo = this.getInUseVehicleOrderPriceInfo(transferVO, rentalOrder, deliveryVo, transferBeginTime, orderHistoryDetailsVo);
        inUseVehicleOrderPriceInfo.getVehicleIds().add(0, transferVO.getTransferVehicleId());
        // 有其他费用需要店主审核, 没有则直接走换车流程
        if (ObjectUtil.isNull(transferVO.getOtherDeduction())) {
            //没有附加费用直接走流程，订单状态修改为换车中
            // 修改在用车辆的租赁计划结束时间
            this.updateVehiclesInUsePlan(rentalOrder, transferBeginTime);
            // 新增换车车辆的租赁计划
            this.addTransferVehiclePlan(transferVO, rentalOrder, transferBeginTime);
            //修改订单相关数据
            rentalOrder.setVehicleId(transferVO.getVehicleId());
            rentalOrder.setRentEndTime(transferVO.getEndTime());
            rentalOrder.setOrderStatus(OrderStatusEnum.CHANGE_CAR.getId());
        }
        this.rentalOrderRepository.save(rentalOrder);
        // 创建流水
        OrderRecordVO orderRecord = OrderRecordVO.builder()
                .amount(inUseVehicleOrderPriceInfo.getTotal())
                .type(Math.toIntExact(OrderEnum.exchangeFee.id))
                .info(
                        OrderHistoryDetailsVo.builder()
                                .orderId(rentalOrder.getId())
                                .info(inUseVehicleOrderPriceInfo)
                                .build()
                )
                .build();
        this.orderRecordService.createOrderRecord(orderRecord);
        Reflect reflect = new Reflect()
                .setCallBackBeanName(CallBackConstant.TRANSFER)
                .setOrderId(rentalOrder.getId())
                .setOrderRecordType(OrderEnum.exchangeFee.id)
                .setRecordSn(orderRecord.getId());
        return JsonResult.success(new TransferVO().setReflect(reflect));
    }

    /**
     * 更新在用车辆租赁计划
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateVehiclesInUsePlan(RentalOrder rentalOrder, LocalDateTime transferBeginTime) {
        Optional<VehicleRentalPlan> vehiclesInUsePlanOptional = this.vehicleRentalPlanRepository.findByVehicleId(rentalOrder.getVehicleId());
        VehicleRentalPlan vehiclesInUsePlan = vehiclesInUsePlanOptional.get();
        vehiclesInUsePlan.setPlanEndTime(transferBeginTime);
        this.vehicleRentalPlanRepository.save(vehiclesInUsePlan);
    }

    /**
     * 新增换车车辆的租赁计划
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addTransferVehiclePlan(TransferVO transferVO, RentalOrder rentalOrder, LocalDateTime transferBeginTime) {
        VehicleRentalPlan transferVehiclePlan = VehicleRentalPlan.builder()
                .vehicleId(transferVO.getTransferVehicleId())
                .createTime(transferBeginTime)
                .planStartTime(transferBeginTime)
                .planEndTime(transferVO.getEndTime())
                .isDelete(DeleteEnum.NO.getIsDelete())
                .planStatus(VehicleRentalPlanStatusEnum.LEASED.getStatus())
                .memberShopId(rentalOrder.getMemberShopId())
                .userId(lotsUserService.getCurrentAdmin().getId())
                .build();
        this.vehicleRentalPlanRepository.save(transferVehiclePlan);
    }

    /**
     * 换车车辆价格
     */
    private OrderPriceInfo getTransferVehicleOrderPriceInfo(TransferVO transferVO, RentalOrder rentalOrder, DeliveryVo deliveryVo, LocalDateTime transferBeginTime, OrderHistoryDetailsVo orderHistoryDetailsVo) {
        TransferVO transferVehicleVo = BeanUtil.copyProperties(transferVO, TransferVO.class);
        transferVehicleVo.setBeginTime(transferBeginTime);
        Vehicle vehicle = this.vehicleMapper.selectByPrimaryKey(transferVO.getTransferVehicleId());
        Optional<VehicleModel> transferVehicleModelOptional = this.vehicleModelRepository.findById(vehicle.getVehicleModelId());
        return getThePriceOfTheVehicleInUse(transferVehicleVo, rentalOrder, deliveryVo, orderHistoryDetailsVo, BeanUtil.copyProperties(transferVehicleModelOptional.get(), VehicleModelVo.class), Collections.emptyList());
    }

    /**
     * 在用车辆价格
     */
    private OrderPriceInfo getInUseVehicleOrderPriceInfo(TransferVO transferVO, RentalOrder rentalOrder, DeliveryVo deliveryVo, LocalDateTime transferBeginTime, OrderHistoryDetailsVo orderHistoryDetailsVo) {
        Optional<VehicleModel> vehicleModelOptional = this.vehicleModelRepository.findById(rentalOrder.getVehicleModelId());
        TransferVO inUseVehicleTransferVO = BeanUtil.copyProperties(transferVO, TransferVO.class);
        inUseVehicleTransferVO
                //计算在用车辆价格开始时间为订单开始时间
                .setBeginTime(rentalOrder.getRentBeginTime())
                //计算在用车辆价格结束时间为换车订单开始时间
                .setEndTime(transferBeginTime);
        RentalOrder inUseVehicleRentalOrder = BeanUtil.copyProperties(rentalOrder, RentalOrder.class);
        //在用车辆换车只能去送车点换车
        inUseVehicleRentalOrder.setReturnPlace(rentalOrder.getPickPlace());
        return getThePriceOfTheVehicleInUse(inUseVehicleTransferVO, inUseVehicleRentalOrder, deliveryVo, orderHistoryDetailsVo, BeanUtil.copyProperties(vehicleModelOptional.get(), VehicleModelVo.class), getCouponVos(rentalOrder, orderHistoryDetailsVo.getInfo()));
    }


    /**
     * 获取车辆价格
     */
    private OrderPriceInfo getThePriceOfTheVehicleInUse(TransferVO transferVO
            , RentalOrder rentalOrder
            , DeliveryVo deliveryVo
            , OrderHistoryDetailsVo orderHistoryDetailsVos
            , VehicleModelVo vehicleModel
            , List<CouponVo> couponVos) {
        CreateOrderVo createOrderVo = CreateOrderVo.builder()
                .rentBeginTime(Date.from(transferVO.getBeginTime().toInstant(ZoneOffset.ofHours(8))))
                .rentEndTime(Date.from(transferVO.getEndTime().toInstant(ZoneOffset.ofHours(8))))
                .pickPlace(rentalOrder.getPickPlace())
                .returnPlace(rentalOrder.getReturnPlace())
                .isInsurance(rentalOrder.getIsInsurance())
                .build();
        //查询交易流水, 如果换车记录不存在，则直接取明细，换车记录存在则第一条是上一次已换车辆的历史信息详情
        OrderRecord transferOrderRecord = this.orderRecordMapper.findFirstByOrderIdAndTypeAndStatusOrderByCreateTimeDesc(rentalOrder.getId(), Math.toIntExact(OrderEnum.exchangeFee.id), OrderRecordStatusEnum.SUCCESS.getStatus());
        OrderPriceInfo orderPriceInfo;
        if (ObjectUtil.isNotNull(transferOrderRecord)) {
            orderPriceInfo = transferOrderRecord.getInfo().getInfo();
        } else {
            orderPriceInfo = orderHistoryDetailsVos.getInfo();
        }
        List<RentListVo> rentListVos = getRentListVos(orderPriceInfo);
        return this.regionProvinceFeign.getVehiclePrice(
                new GetVehiclePriceVo(
                        deliveryVo
                        , createOrderVo
                        , vehicleModel
                        , rentListVos
                        , couponVos)
        );
    }

    /**
     * 获取租金列表
     */
    private List<RentListVo> getRentListVos(OrderPriceInfo orderPriceInfo) {
        List<VehicleDatePrice> actualHistoryPriceList = orderPriceInfo.getOrderVehiclePrice().getActualHistoryPriceList();
        return actualHistoryPriceList.stream()
                .map(vehicleDatePrice -> RentListVo.builder()
                        .rentalTime(vehicleDatePrice.getTime())
                        .rentalMoney(vehicleDatePrice.getPrice())
                        .build())
                .collect(Collectors.toList());
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String callBack(DataVO vo) {
        JsonResult<ResponseVO> tJsonResult = ShouQianBaUtil.checkUpayV2Precreate(vo);
        // 更新流水
        OrderRecord orderRecord = this.orderRecordMapper.selectByPrimaryKey(vo.getReflect().getRecordSn());
        if (HttpStatus.HTTP_OK == tJsonResult.getCode()) {
            // 成功
            orderRecord.setStatus(OrderRecordStatusEnum.SUCCESS.getStatus());
        } else {
            // 失败
            orderRecord.setStatus(OrderRecordStatusEnum.FAILURE.getStatus());
        }
        this.orderRecordMapper.updateByPrimaryKeySelective(orderRecord);
        // 更新订单价格
        return ShouQianBaConstant.RECEIVE_CALLBACK_RETURN_VALUE;
    }
}
