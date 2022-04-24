package com.lots.lots.vehicle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpStatus;
import com.lots.lots.admin.order.dto.po.RentalOrder;
import com.lots.lots.admin.order.dto.po.RentalOrderDelivery;
import com.lots.lots.admin.order.repository.RentalOrderDeliveryRepository;
import com.lots.lots.admin.order.repository.RentalOrderRepository;
import com.lots.lots.admin.user.service.LotsUserService;
import com.lots.lots.admin.vehicle.dao.VehicleGoodsCheckMapper;
import com.lots.lots.admin.vehicle.dao.VehicleGoodsMapper;
import com.lots.lots.admin.vehicle.dao.VehicleOrderRemarksMapper;
import com.lots.lots.admin.vehicle.dto.po.*;
import com.lots.lots.admin.vehicle.repository.VehicleCertificatesRepository;
import com.lots.lots.admin.vehicle.repository.VehicleGoodsCheckRepository;
import com.lots.lots.admin.vehicle.repository.VehicleOrderRemarksRepository;
import com.lots.lots.admin.vehicle.repository.VehicleRepository;
import com.lots.lots.admin.vehicle.service.VehicleCheckService;
import com.lots.lots.common.entity.JsonResult;
import com.lots.lots.common.entity.ResultCode;
import com.lots.lots.common.entity.user.LotsRoleVo;
import com.lots.lots.common.entity.user.LotsUserVo;
import com.lots.lots.common.entity.vehicle.VehicleCheckVo;
import com.lots.lots.common.entity.vehicle.VehicleGoodsVo;
import com.lots.lots.common.entity.vehicle.VehicleOrderRemarksVo;
import com.lots.lots.common.entity.vehicle.VehicleReturnCheckVo;
import com.lots.lots.common.enums.DeleteEnum;
import com.lots.lots.common.enums.RolesEnum;
import com.lots.lots.common.enums.order.BusinessOrderTypeEnum;
import com.lots.lots.common.enums.order.OrderStatusEnum;
import com.lots.lots.common.enums.order.PayStatusEnum;
import com.lots.lots.common.enums.user.UserRoleEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VehicleCheckServiceImpl implements VehicleCheckService {

    @Resource
    private LotsUserService lotsUserService;

    @Resource
    private RentalOrderRepository rentalOrderRepository;

    @Resource
    private VehicleRepository vehicleRepository;

    @Resource
    private VehicleGoodsMapper vehicleGoodsMapper;

    @Resource
    private RentalOrderDeliveryRepository rentalOrderDeliveryRepository;

    @Resource
    private VehicleOrderRemarksMapper vehicleOrderRemarksMapper;

    @Resource
    private VehicleGoodsCheckRepository vehicleGoodsCheckRepository;

    @Resource
    private VehicleOrderRemarksRepository vehicleOrderRemarksRepository;

    @Resource
    private VehicleCertificatesRepository vehicleCertificatesRepository;

    @Resource
    private VehicleGoodsCheckMapper vehicleGoodsCheckMapper;

    @Override
    public JsonResult queryVehicleGoods(Long vehicleId, Long orderId) {
        final LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        if (currentAdmin == null) {
            return JsonResult.failed(ResultCode.UNAUTHORIZED);
        }

        if (vehicleId == null){
            return JsonResult.failed(ResultCode.VALIDATE_FAILED);
        }

        // 查询当前订单是否存在
        final Optional<RentalOrder> rentalOrder = rentalOrderRepository.findById(orderId);

        if (!rentalOrder.isPresent()){
            return JsonResult.failed("订单不存在！");
        }

        final VehicleCheckVo vehicleCheckVo = new VehicleCheckVo();

        // 查询车辆是否存在
        final Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);
        if (!vehicle.isPresent()) {
            return JsonResult.failed("车辆不存在");
        }

        vehicleCheckVo.setCarNumber(vehicle.get().getCarNumber());
        vehicleCheckVo.setMileage(vehicle.get().getMileage());
        vehicleCheckVo.setOil(vehicle.get().getOil());

        // 备注信息
        List<VehicleOrderRemarksVo> vehicleOrderRemarks = vehicleOrderRemarksMapper.findOneByCondition(vehicleId,orderId);

        for (VehicleOrderRemarksVo vo:vehicleOrderRemarks) {
            if (vo.getType() == 0){
                vo.setUserName(rentalOrder.get().getCustomerName());
            }
        }
        vehicleCheckVo.setRemarksList(vehicleOrderRemarks);

        // 查询最高级的车辆物件
        List<VehicleGoodsVo> list = vehicleGoodsMapper.findGoodsList(Long.valueOf(0));

        // 处理集合
        processingCollection(vehicleId, orderId, list);

        vehicleCheckVo.setGoodsList(list);
        return JsonResult.success(vehicleCheckVo);
    }

    public static void main(String[] args) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult staffVehicleCheck(VehicleReturnCheckVo vehicleReturnCheckVo) {
        // 获取当前操作用户信息
        LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        // 获取当前操作用户角色
        List<LotsRoleVo> roles = currentAdmin.getRoles();
        // 获取当前操作人员的门店id
        Long shopId = currentAdmin.getShopId();
        // 获取当前操作人员的id
        final Long userId = currentAdmin.getId();
        // 判断当前用户是否登录
        if (Objects.isNull(currentAdmin)){
            return JsonResult.failed(ResultCode.UNAUTHORIZED);
        }

        Long vehicleId = vehicleReturnCheckVo.getVehicleId();
        Long orderId = vehicleReturnCheckVo.getOrderId();

        if (vehicleId == null){
            return JsonResult.failed("车辆id不能为空");
        }

        if (orderId == null){
            return JsonResult.failed("订单id不能为空");
        }

        if (vehicleReturnCheckVo.getOil() == null){
            return JsonResult.failed("油量不能为空");
        }

        if (vehicleReturnCheckVo.getMileage() == null){
            return JsonResult.failed("里程不能为空");
        }

        // 查看当前车辆是否存在
        Vehicle vehicle = vehicleRepository.findById(vehicleId).get();

        if (Objects.isNull(vehicle)){
            return JsonResult.failed("未匹配到当前车辆信息");
        }
        // 修改车辆的里程 油量
        vehicle.setMileage(vehicleReturnCheckVo.getMileage());
        vehicle.setOil(vehicleReturnCheckVo.getOil());
        Vehicle save = vehicleRepository.save(vehicle);

        // 如果不是管理员也不是当前车辆的门店员工 也不是车主 那肯定不准操作
        if (CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() != UserRoleEnum.ADMINISTRATOR.getRole())) {
            if (!shopId.equals(vehicle.getMemberShopId())){
                return JsonResult.failed(ResultCode.FORBIDDEN);
            }
        }

        // 查看当前订单是否存在
        final Optional<RentalOrder> rentalOrder = rentalOrderRepository.findById(orderId);

        if (!rentalOrder.isPresent()){
            return JsonResult.failed("未匹配到订单信息！");
        }

        // 修改订单里程  油量
        rentalOrder.get().setOrderStatus(OrderStatusEnum.SEND_CAR.getId());
        rentalOrder.get().setOil(vehicleReturnCheckVo.getOil());
        rentalOrder.get().setMileage(vehicleReturnCheckVo.getMileage());
        rentalOrderRepository.save(rentalOrder.get());


        // 出车检验-关联我的订单管理
        final RentalOrderDelivery rentalOrderDelivery = new RentalOrderDelivery();
        rentalOrderDelivery.setRentalOrderId(orderId);
        rentalOrderDelivery.setVehicleId(vehicleId);
        this.addOrderDelivery(rentalOrderDelivery);

        // 记录检验备注
        if (!Objects.isNull(vehicleReturnCheckVo.getRemarks())){
            final VehicleOrderRemarks remarks = new VehicleOrderRemarks();
            remarks.setOrderId(orderId);
            remarks.setVehicleId(vehicleId);
            remarks.setType(Integer.valueOf(0));
            BeanUtil.copyProperties(vehicleReturnCheckVo.getRemarks(),remarks);
            final JsonResult jsonResult = this.addCheckRemarks(remarks);
            if (jsonResult.getCode() != HttpStatus.HTTP_OK){
                return jsonResult;
            }
        }

        // 车辆固件检验
        final List<VehicleGoodsVo> list = vehicleReturnCheckVo.getGoodsList();
        List<VehicleGoodsCheck> vehicleGoodsChecks = vehicleGoodsCheckRepository.findByOrderIdAndVehicleId(orderId,vehicleId);

        // 第一次检验
        if (vehicleGoodsChecks.size() == 0){
            return this.insertVehicleCheckGoods(currentAdmin, roles, list, orderId, vehicleId);
        // 已经检验过了 修改检验内容
        }else {
            return this.updateVehicleCheckGoods(currentAdmin, roles, list, orderId, vehicleId);
        }
    }

    // 修改车辆检验
    private JsonResult updateVehicleCheckGoods(LotsUserVo currentAdmin, List<LotsRoleVo> roles, List<VehicleGoodsVo> list, Long orderId, Long vehicleId) {
        final ArrayList<VehicleGoodsVo> checkArrayList = new ArrayList<>();

        Integer type = null;

        // 如果是车主上传
        if (roles.stream().anyMatch(m -> m.getId().intValue() == UserRoleEnum.CAR_OWNER.getRole())){
            type = RolesEnum.CAR_OWNER.getType();
        }

        // 如果是员工上传
        if (roles.stream().anyMatch(m -> m.getId().intValue() >= UserRoleEnum.SHOPKEEPER.getRole())){
            type =  RolesEnum.STAFF.getType();
        }

        for (int i = 0; i < list.size(); i++) {
            final List<VehicleGoodsVo> goodsVos = list.get(i).getChildren();
            checkArrayList.addAll(goodsVos);
        }

        for (int i = 0; i < checkArrayList.size(); i++) {
            final VehicleGoodsCheck vehicleGoodsCheck = new VehicleGoodsCheck();

            if (checkArrayList.get(i).getCondition() == null){
                return JsonResult.failed("检验状态不能为空！");
            }
            final VehicleGoodsCheck goodsCheck = vehicleGoodsCheckRepository.findByOrderIdAndVehicleIdAndGoodsId(orderId,vehicleId,checkArrayList.get(i).getId());

            if (checkArrayList.get(i).getCondition() != null){
                goodsCheck.setCondition(checkArrayList.get(i).getCondition());
            }

            if (checkArrayList.get(i).getImage() != null){
                goodsCheck.setImage(checkArrayList.get(i).getImage());
            }

            goodsCheck.setType(type);
            goodsCheck.setUpdateTime(LocalDateTime.now());

            vehicleGoodsCheckRepository.save(goodsCheck);
        }
        return JsonResult.success("检验成功");
    }

    // 新增车辆检验
    private JsonResult insertVehicleCheckGoods(LotsUserVo currentAdmin, List<LotsRoleVo> roles, List<VehicleGoodsVo> list,Long orderId, Long vehicleId) {
        final ArrayList<VehicleGoodsVo> checkArrayList = new ArrayList<>();
        Integer type = null;
        // 如果是车主上传
        if (roles.stream().anyMatch(m -> m.getId().intValue() == UserRoleEnum.CAR_OWNER.getRole())){
            type = RolesEnum.CAR_OWNER.getType();
        }

        // 如果是员工上传
        if (roles.stream().anyMatch(m -> m.getId().intValue() >= UserRoleEnum.SHOPKEEPER.getRole())){
            type =  RolesEnum.STAFF.getType();
        }

        for (int i = 0; i < list.size(); i++) {
            final List<VehicleGoodsVo> goodsVos = list.get(i).getChildren();
            checkArrayList.addAll(goodsVos);
        }

        for (int i = 0; i < checkArrayList.size(); i++) {
            final VehicleGoodsCheck vehicleGoodsCheck = new VehicleGoodsCheck();

            if (checkArrayList.get(i).getCondition() == null){
                return JsonResult.failed("检验状态不能为空！");
            }

            vehicleGoodsCheck.setOrderId(orderId);
            vehicleGoodsCheck.setVehicleId(vehicleId);
            vehicleGoodsCheck.setGoodsId(checkArrayList.get(i).getId());
            vehicleGoodsCheck.setCondition(checkArrayList.get(i).getCondition());
            vehicleGoodsCheck.setImage(checkArrayList.get(i).getImage());
            vehicleGoodsCheck.setType(type);
            vehicleGoodsCheck.setUserId(currentAdmin.getId());
            vehicleGoodsCheck.setCreateTime(LocalDateTime.now());
            vehicleGoodsCheck.setIsDelete(Integer.valueOf(0));
            VehicleGoodsCheck save = vehicleGoodsCheckRepository.save(vehicleGoodsCheck);
        }
        return JsonResult.success("检验成功");
    }

    @Override
    public JsonResult addCheckRemarks(VehicleOrderRemarks vehicleOrderRemarks) {
        // 获取当前操作用户信息
        final LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        // 获取当前操作用户角色
        final List<LotsRoleVo> roles = currentAdmin.getRoles();
        // 获取当前操作人员的门店id
        final Long shopId = currentAdmin.getShopId();
        // 判断当前用户是否登录
        if (Objects.isNull(currentAdmin)){
            return JsonResult.failed(ResultCode.UNAUTHORIZED);
        }
        // 查看当前车辆是否存在
        Vehicle vehicle = vehicleRepository.findById(vehicleOrderRemarks.getVehicleId()).get();
        if (Objects.isNull(vehicle)){
            return JsonResult.failed(ResultCode.NOT_FOUNT_DATA);
        }
        // 查看当前订单是否存在
        final Optional<RentalOrder> rentalOrder = rentalOrderRepository.findById(vehicleOrderRemarks.getOrderId());
        if (!rentalOrder.isPresent()){
            return JsonResult.failed("当前订单不存在！");
        }
        // 如果不是管理员也不是当前车辆的门店员工  那肯定不准操作
        if (CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() != UserRoleEnum.ADMINISTRATOR.getRole())) {
            if (!shopId.equals(vehicle.getMemberShopId())){
                return JsonResult.failed(ResultCode.FORBIDDEN);
            }
        }

        // 根据用户id 订单id 车辆id查询一条记录
        VehicleOrderRemarks remarks = vehicleOrderRemarksRepository.findByUserIdAndOrderIdAndVehicleId(currentAdmin.getId(),vehicleOrderRemarks.getOrderId(),vehicle.getId());

        if (!Objects.isNull(remarks)){
            remarks.setRemarks(vehicleOrderRemarks.getRemarks());
            remarks.setImage(vehicleOrderRemarks.getImage());
            remarks.setCreateTime(LocalDateTime.now());
            final VehicleOrderRemarks save = vehicleOrderRemarksRepository.save(remarks);
            return JsonResult.success(save);
        }else {
            vehicleOrderRemarks.setType(RolesEnum.USER.getType());
            vehicleOrderRemarks.setUserId(currentAdmin.getId());
            vehicleOrderRemarks.setCreateTime(LocalDateTime.now());
            vehicleOrderRemarks.setIsDelete(Integer.valueOf(0));
            VehicleOrderRemarks save = vehicleOrderRemarksRepository.save(vehicleOrderRemarks);
            return JsonResult.success(save);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult addOrderDelivery(RentalOrderDelivery rentalOrderDelivery) {
        final LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        if (Objects.isNull(currentAdmin)){
            return JsonResult.failed(ResultCode.UNAUTHORIZED);
        }
        if (!Objects.isNull(rentalOrderDelivery) && rentalOrderDelivery.getRentalOrderId() == null){
            return JsonResult.failed("订单号不能为空!");
        }
        final Optional<RentalOrder> rentalOrder = rentalOrderRepository.findById(rentalOrderDelivery.getRentalOrderId());
        if (Objects.isNull(rentalOrder)){
            return JsonResult.failed("当前订单不存在!");
        }

        RentalOrderDelivery one = rentalOrderDeliveryRepository.findByRentalOrderIdAndOrderNumberAndBusinessOrderTypeAndIsDelete(rentalOrder.get().getId(),rentalOrder.get().getOrderSn(),Integer.valueOf(0),Integer.valueOf(0));

        if (!Objects.isNull(one)){
            return JsonResult.failed("当前订单已被其他员工检验了!");
        }

        rentalOrderDelivery.setDistributionId(null);
        rentalOrderDelivery.setDistributionName("系统分配");
        rentalOrderDelivery.setBusinessOrderType(BusinessOrderTypeEnum.CAR_DELIVERY.getStatus());
        rentalOrderDelivery.setOrderNumber(rentalOrder.get().getOrderSn());
        rentalOrderDelivery.setStaffDeliveryId(currentAdmin.getId());
        rentalOrderDelivery.setStaffDeliveryName(currentAdmin.getRealName());
        rentalOrderDelivery.setGetOrderType(Integer.valueOf(0));
        rentalOrderDelivery.setIsReturn(Integer.valueOf(0));
        rentalOrderDelivery.setIsDelete(Integer.valueOf(0));
        rentalOrderDelivery.setCreateTime(LocalDateTime.now());

        RentalOrderDelivery delivery = rentalOrderDeliveryRepository.save(rentalOrderDelivery);

        if (Objects.isNull(delivery)){
            return JsonResult.failed(ResultCode.FAILED);
        }

        // 待送车状态的订单才能改
        if (rentalOrder.get().getOrderStatus().equals(OrderStatusEnum.WAITING_SEND_CAR.getId())){
            rentalOrder.get().setOrderStatus(OrderStatusEnum.SEND_CAR.getId());
            RentalOrder save = rentalOrderRepository.save(rentalOrder.get());

            if (Objects.isNull(save)){
                return JsonResult.failed(ResultCode.FAILED);
            }
        }

        return JsonResult.failed("检验成功");
    }

    @Override
    public JsonResult changeOilOrMileage(RentalOrder rentalOrder) {
        LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        if (Objects.isNull(currentAdmin)){
            return JsonResult.failed(ResultCode.UNAUTHORIZED);
        }

        if (rentalOrder.getId() == null){
            return JsonResult.failed("订单id不能为空");
        }
        if (rentalOrder.getOil() == null && rentalOrder.getMileage() == null){
            return JsonResult.failed("油量或里程不能为空");
        }

        // 根据订单id查询一条订单
        RentalOrder rentalOrder1 = rentalOrderRepository.findById(rentalOrder.getId()).get();
        if (Objects.isNull(rentalOrder1)){
            return JsonResult.failed("未匹配到订单信息");
        }
        if (rentalOrder.getOil() != null){
            rentalOrder1.setOil(rentalOrder.getOil());
        }
        if (rentalOrder.getMileage() != null){
            rentalOrder1.setMileage(rentalOrder.getMileage());
        }

        RentalOrder order = rentalOrderRepository.save(rentalOrder1);
        if (Objects.isNull(order)){
            return JsonResult.failed(ResultCode.FAILED);
        }

        // 根据车辆id查询一辆车辆信息
        Vehicle vehicle1 = vehicleRepository.findById(rentalOrder1.getVehicleId()).get();

        if (Objects.isNull(vehicle1)){
            return JsonResult.failed("车辆信息不存在");
        }

        if (rentalOrder.getMileage() != null){
            vehicle1.setMileage(rentalOrder.getMileage());
        }

        if (rentalOrder.getOil() != null){
            vehicle1.setOil(rentalOrder.getOil());
        }

        Vehicle save = vehicleRepository.save(vehicle1);

        if (Objects.isNull(save)){
            return JsonResult.failed(ResultCode.FAILED);
        }

        return JsonResult.failed(ResultCode.SUCCESS);
    }

    @Override
    public JsonResult deliver(VehicleCertificates vehicleCertificates) {
        if (vehicleCertificates.getOrderId() == null){
            return JsonResult.failed("订单信息不能为空");
        }

        VehicleCertificates certificates = this.vehicleCertificatesRepository.findByOrderId(vehicleCertificates.getOrderId());

        if (certificates != null){
            return JsonResult.failed("请勿重复提交");
        }

        final RentalOrder rentalOrder = this.rentalOrderRepository.findById(vehicleCertificates.getOrderId()).get();

        if (rentalOrder == null){
            return JsonResult.failed("订单信息不存在");
        }

        if (rentalOrder.getOrderStatus().intValue() == OrderStatusEnum.CANCEL.getId()){
            return JsonResult.failed("订单已取消,交付失败");
        }

        if (rentalOrder.getPayStatus().intValue() == PayStatusEnum.UNPAID.getStatus()){
            return JsonResult.failed("请提醒客户支付租金");
        }

        vehicleCertificates.setBusinessDate(LocalDate.now());
        vehicleCertificates.setIsDelete(DeleteEnum.NO.getIsDelete());

        final VehicleCertificates save = this.vehicleCertificatesRepository.save(vehicleCertificates);
        return JsonResult.success(save);
    }


    /* 处理集合 */
    private void processingCollection(Long vehicleId, Long orderId, List<VehicleGoodsVo> list) {
        for (VehicleGoodsVo vo: list) {
            // 儿子集合
            List<VehicleGoodsVo> sonList = vehicleGoodsMapper.findGoodsListByVehicleId(vo.getId(), vehicleId, orderId);

            for (VehicleGoodsVo one:sonList) {
                // 孙子集合
                List<VehicleGoodsVo> grandSonList = vehicleGoodsMapper.findGoodsList(one.getId());

                // 描述
                StringBuffer description = new StringBuffer();
                description.append("(");

                if (grandSonList.size() > 0){
                    for (int i = 0; i < grandSonList.size(); i++) {
                        if (one.getId().equals(grandSonList.get(i).getParentId())){
                            description.append(grandSonList.get(i).getName() +"/");
                        }
                    }
                }
                String s = description.toString();
                // 去掉多余的"/"
                if (s.substring(s.length()-1).equals("/")){
                    s = s.substring(0,description.length()-1);
                }
                s +=")";
                one.setDescription(s);
            }
            vo.setChildren(sonList);
        }
    }
}
