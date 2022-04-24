package com.lots.lots.vehicle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.lots.lots.admin.order.dao.RentalOrderMapper;
import com.lots.lots.admin.order.dto.po.RentalOrder;
import com.lots.lots.admin.shop.repository.MemberShopRepository;
import com.lots.lots.admin.shop.service.VerifyOperationAuth;
import com.lots.lots.admin.user.service.LotsUserService;
import com.lots.lots.admin.vehicle.dao.*;
import com.lots.lots.admin.vehicle.dto.po.*;
import com.lots.lots.admin.vehicle.dto.vo.OTAVehicleVO;
import com.lots.lots.admin.vehicle.dto.vo.VehicleVO;
import com.lots.lots.admin.vehicle.repository.VehicleCarOwnerRepository;
import com.lots.lots.admin.vehicle.repository.VehicleModelRepository;
import com.lots.lots.admin.vehicle.repository.VehicleRentalPlanRepository;
import com.lots.lots.admin.vehicle.repository.VehicleRepository;
import com.lots.lots.admin.vehicle.service.VehicleService;
import com.lots.lots.common.entity.JsonPage;
import com.lots.lots.common.entity.JsonResult;
import com.lots.lots.common.entity.ResultCode;
import com.lots.lots.common.entity.user.LotsRoleVo;
import com.lots.lots.common.entity.user.LotsUserVo;
import com.lots.lots.common.enums.DeleteEnum;
import com.lots.lots.common.enums.VehicleRentalPlanStatusEnum;
import com.lots.lots.common.enums.user.UserRoleEnum;
import com.lots.lots.common.enums.vehicle.PlatformEnum;
import com.lots.lots.common.enums.vehicle.VehicleStatusEnum;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Resource
    private VehicleMapper vehicleMapper;

    @Resource
    private VehicleRepository vehicleRepository;

    @Resource
    private LotsUserService lotsUserService;

    @Resource
    private VehicleModelRepository vehicleModelRepository;

    @Resource
    private VehicleBrandMapper vehicleBrandMapper;

    @Resource
    private VehicleOutputVolumeMapper vehicleOutputVolumeMapper;

    @Resource
    private VehicleCategoryMapper vehicleCategoryMapper;

    @Resource
    private VehicleRentalPlanMapper vehicleRentalPlanMapper;

    @Resource
    private VehicleRentalPlanRepository vehicleRentalPlanRepository;

    @Resource
    private MemberShopRepository memberShopRepository;

    @Resource
    private RentalOrderMapper rentalOrderMapper;

    @Resource
    private VehicleCarOwnerRepository vehicleCarOwnerRepository;

    @Resource
    private VerifyOperationAuth verifyOperationAuth;

    @Override
    public JsonResult pageQuery(VehicleVO vo) {
        LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        final List<LotsRoleVo> roles = currentAdmin.getRoles();

        PageHelper.startPage(vo.getPage(), vo.getSize());
        // 如果为管理员  查询所有的车辆
        if (CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() == UserRoleEnum.ADMINISTRATOR.getRole())) {
            List<VehicleVO> vehicleVOS = vehicleMapper.pageQuery(vo);
            // 查询车辆当前的租赁状态
            this.extracted(vehicleVOS);
            return JsonResult.success(JsonPage.restPage(vehicleVOS));
        }

        // 如果是店主或员工 查询自己门店下面的车辆
        if (CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() >= UserRoleEnum.SHOPKEEPER.getRole())) {
            Long shopId = currentAdmin.getShopId();
            if (shopId == null) {
                return JsonResult.failed("未关联门店");
            } else {
                vo.setMemberShopId(shopId);
            }
            List<VehicleVO> vehicleVOS = vehicleMapper.pageQuery(vo);
            // 查询车辆当前的租赁状态
            this.extracted(vehicleVOS);
            return JsonResult.success(JsonPage.restPage(vehicleVOS));
        }

        // 如果是车主 查询自己的车辆列表
        if (CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() == UserRoleEnum.CAR_OWNER.getRole())) {
            Long userId = currentAdmin.getId();
            vo.setCarOwnerId(userId);
            vo.setMemberShopId(currentAdmin.getShopId());
            List<VehicleVO> vehicleVOS = vehicleMapper.pageQuery(vo);
            // 查询车辆当前的租赁状态
            this.extracted(vehicleVOS);
            return JsonResult.success(JsonPage.restPage(vehicleVOS));
        }

        return JsonResult.failed(ResultCode.NOT_FOUNT_DATA);
    }


    @Override
    public JsonResult pageLeaseQuery(VehicleVO vo) {
        LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        final List<LotsRoleVo> roles = currentAdmin.getRoles();
        final Long shopId = currentAdmin.getShopId();

        // 查询时间不能小于当前时间
        if (null != vo.getPlanBeginTime() && vo.getPlanBeginTime().isBefore(LocalDateTime.now().plusDays(-1))) {
            return JsonResult.failed("查询时间不能小于当前时间");
        }

        // 查询时间不能大于15天
        if (null != vo.getPlanBeginTime() && vo.getPlanEndTime().isAfter(LocalDateTime.now().plusDays(15))) {
            return JsonResult.failed("查询时间不能大于15天");
        }

        PageHelper.startPage(vo.getPage(), vo.getSize());
        //如果为管理员
        if (CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() == UserRoleEnum.ADMINISTRATOR.getRole())) {
            List<VehicleVO> vehicleVOS = getVehicleVOS(vo, currentAdmin);
            return JsonResult.success(JsonPage.restPage(vehicleVOS));
        }

        // 如果是店主或员工 查询自己门店下面的车辆
        if (CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() >= UserRoleEnum.SHOPKEEPER.getRole())) {
            if (shopId == null) {
                return JsonResult.failed("未关联门店");
            } else {
                vo.setMemberShopId(shopId);
            }
            // 分页查询
            List<VehicleVO> vehicleVOS = getVehicleVOS(vo, currentAdmin);
            return JsonResult.success(JsonPage.restPage(vehicleVOS));
        }

        // 如果是车主 查询自己的车辆列表
        if (CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() == UserRoleEnum.CAR_OWNER.getRole())) {
            Long userId = currentAdmin.getId();
            vo.setCarOwnerId(userId);

            // 分页查询
            List<VehicleVO> vehicleVOS = getVehicleVOS(vo, currentAdmin);
            return JsonResult.success(JsonPage.restPage(vehicleVOS));
        }

        return JsonResult.failed(ResultCode.NOT_FOUNT_DATA);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult insert(Vehicle vehicle) {
        LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        final List<LotsRoleVo> roles = currentAdmin.getRoles();

        //如果为管理员
        if (CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() == UserRoleEnum.ADMINISTRATOR.getRole())) {
          if (vehicle.getMemberShopId() == null){
              return JsonResult.failed("请选择一个门店");
          }
        }

        // 如果是店主或员工
        if (CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() >= UserRoleEnum.SHOPKEEPER.getRole())) {
            vehicle.setMemberShopId(currentAdmin.getShopId());
        }

        if (vehicle.getVehicleModelId() == null){
            return JsonResult.failed("车型不能为空!");
        }

        if (vehicle.getCarNumber() == null){
            return JsonResult.failed("车牌号不能为空！");
        }

        if (vehicle.getColour() == null){
            return JsonResult.failed("车辆颜色不能为空!");
        }

        if (vehicle.getVehicleStatus() == null){
            return JsonResult.failed("车辆状态不能为空!");
        }

        if (vehicle.getVehicleDrivingPositiveFiles() == null){
            return JsonResult.failed("行驶证正面照片不能为空!");
        }

        if (vehicle.getVehicleDrivingNegativeFiles() == null){
            return JsonResult.failed("行驶证反面照片不能为空!");
        }

        if (vehicle.getPlateNumber() == null){
            return JsonResult.failed("行驶证号牌号码不能为空!");
        }

        if (vehicle.getBrandModel() == null ){
            return JsonResult.failed("行驶证品牌型号不能为空!");
        }

        if (vehicle.getDiscernCode() == null){
            return JsonResult.failed("行驶车辆识别代号不能为空!");
        }

        if (vehicle.getEngineNum() == null){
            return JsonResult.failed("发动机号码不能为空!");
        }

        if (vehicle.getIssueDate() == null){
            return JsonResult.failed("发证日期不能为空!");
        }

        if (vehicle.getRegisteredDate() == null ){
            return JsonResult.failed("注册日期不能为空!");
        }

        if (vehicle.getInsuranceSn() == null ){
            return JsonResult.failed("交强险单号不能为空!");
        }

        if (vehicle.getInsuranceTime() == null ){
            return JsonResult.failed("交强险续保日期不能为空!");
        }

        if (vehicle.getInsuranceFile() == null ){
            return JsonResult.failed("交强险图片不能为空!");
        }

        if (vehicle.getBusinessSn() == null ){
            return JsonResult.failed("商业险单号不能为空!");
        }

        if (vehicle.getBusinessDate() == null ){
            return JsonResult.failed("商业险续保日期不能为空!");
        }

        if (vehicle.getBusinessFile() == null ){
            return JsonResult.failed("商业险图片不能为空!");
        }

        if (vehicle.getInsuranceUrls() == null ){
            return JsonResult.failed("保险证图片不能为空!");
        }

        // 目标：删除拼接字符的最后一个 "," 行驶证正面
        if ( vehicle.getVehicleDrivingPositiveFiles().substring(vehicle.getVehicleDrivingPositiveFiles().length() - 1, vehicle.getVehicleDrivingPositiveFiles().length()).equals(",") ){
            vehicle.setVehicleDrivingPositiveFiles(vehicle.getVehicleDrivingPositiveFiles().substring(0, vehicle.getVehicleDrivingPositiveFiles().length() - 1));
        }
        // 行驶证反面
        if ( vehicle.getVehicleDrivingNegativeFiles().substring(vehicle.getVehicleDrivingNegativeFiles().length() - 1, vehicle.getVehicleDrivingNegativeFiles().length()).equals(",") ){
            vehicle.setVehicleDrivingNegativeFiles(vehicle.getVehicleDrivingNegativeFiles().substring(0, vehicle.getVehicleDrivingNegativeFiles().length() - 1));
        }
        // 交强险图片
        if ( vehicle.getInsuranceFile().substring(vehicle.getInsuranceFile().length() - 1, vehicle.getInsuranceFile().length()).equals(",") ){
            vehicle.setInsuranceFile(vehicle.getInsuranceFile().substring(0, vehicle.getInsuranceFile().length() - 1));
        }
        // 商业险图片
        if ( vehicle.getBusinessFile().substring(vehicle.getBusinessFile().length() - 1, vehicle.getBusinessFile().length()).equals(",") ){
            vehicle.setBusinessFile(vehicle.getBusinessFile().substring(0, vehicle.getBusinessFile().length() - 1));
        }
        // 保险证图片
        if ( vehicle.getInsuranceUrls().substring(vehicle.getInsuranceUrls().length() - 1, vehicle.getInsuranceUrls().length()).equals(",") ){
            vehicle.setInsuranceUrls(vehicle.getInsuranceUrls().substring(0, vehicle.getInsuranceUrls().length() - 1));
        }

        // 根据车牌号查询当前车辆是否录入
        Vehicle vehicleNumber = this.vehicleMapper.findVehicleByCarNumber(vehicle.getCarNumber());

        if (vehicleNumber != null) {
            return JsonResult.failed("车牌号信息已存在！");
        }

        final VehicleModel vehicleModel = this.vehicleModelRepository.findById(vehicle.getVehicleModelId()).get();

        if (Objects.isNull(vehicleModel)){
            return JsonResult.failed("匹配的车型不存在");
        }

        vehicle.setMemberShopId(vehicleModel.getMemberShopId());
        vehicle.setIsDelete(DeleteEnum.NO.getIsDelete());
        vehicle.setCreateTime(LocalDateTime.now());
        vehicle.setMemberShopId(currentAdmin.getShopId());

        // 新增
        final Vehicle save = vehicleRepository.save(vehicle);


        // 如果是车主 - 车主关联车辆表
        if (CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() == UserRoleEnum.CAR_OWNER.getRole())) {
            final VehicleCarOwner vehicleCarOwner = new VehicleCarOwner();
            vehicleCarOwner.setUserId(currentAdmin.getId());
            vehicleCarOwner.setVehicleId(save.getId());
            vehicleCarOwner.setMemberShopId(currentAdmin.getShopId());

            final VehicleCarOwner carOwner = vehicleCarOwnerRepository.save(vehicleCarOwner);
        }

        return JsonResult.success(save);
    }


    @Override
    public JsonResult update(Vehicle vehicle) {
        LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();

        if (ObjectUtil.isNull(currentAdmin)) {
            return JsonResult.failed(ResultCode.UNAUTHORIZED);
        }

        final Vehicle vehicle1 = vehicleRepository.findById(vehicle.getId()).get();

        if (Objects.isNull(vehicle1)){
            return JsonResult.failed("未查询到车辆信息");
        }

        // 修改
        final int i = vehicleMapper.update(vehicle);

        if (i != 1){
            return JsonResult.failed(ResultCode.FAILED);
        }

        return JsonResult.success("车辆修改成功");

    }

    @Override
    public JsonResult selectOne(Long id) {
        VehicleVO vehicleVO = new VehicleVO();

        // 根据主键id查询车辆
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);

        if (!vehicle.isPresent()) {
            JsonResult.failed(ResultCode.NOT_FOUNT_DATA);
        }
        val vehicle1 = vehicle.get();

        // 对象复制
        BeanUtil.copyProperties(vehicle1, vehicleVO);

        // 根据车型id查询当前车辆的车型信息
        Optional<VehicleModel> vehicleModel = vehicleModelRepository.findById(vehicle1.getVehicleModelId());

        if (!vehicleModel.isPresent()) {
            JsonResult.failed(ResultCode.NOT_FOUNT_DATA);
        }
        val vehicleModel1 = vehicleModel.get();

        // 根据车辆品牌id查询信息
        VehicleBrand vehicleBrand = vehicleBrandMapper.findBrandNameById(vehicleModel1.getBrandId());
        if (null != vehicleBrand) {
            vehicleVO.setBrandName(vehicleBrand.getName());
        }
        // 根据车辆排量id查询信息
        VehicleOutputVolume vehicleOutputVolume = vehicleOutputVolumeMapper.findOutputVolumeById(vehicleModel1.getOutputVolumeId());
        if (null != vehicleOutputVolume) {
            vehicleVO.setOutputVolumeName(vehicleOutputVolume.getName());
        }
        // 根据车辆类别id查询信息
        VehicleCategory vehicleCategory = vehicleCategoryMapper.findOneById(vehicleModel1.getCategoryId());
        if (null != vehicleCategory) {
            vehicleVO.setCategoryName(vehicleCategory.getName());
        }

        final VehicleRentalPlan vehicleRentalPlan = new VehicleRentalPlan();
        vehicleRentalPlan.setVehicleId(id);
        List<VehicleRentalPlan> rentalPlanList = vehicleRentalPlanMapper.findPlanListByVehicleId(vehicleRentalPlan);
        if (rentalPlanList.size() > 0){
            // 已经预约的租金列表
            vehicleVO.setRentalPlans(rentalPlanList);
        }
        final List<VehicleRentalPlan> vehicleRentalPlans = this.getVehicleRentalPlans(vehicleVO);

        // 未预约的租金列表
        vehicleVO.setRentalReservedPlans(vehicleRentalPlans);
        vehicleVO.setVehicleModelFiles(vehicleModel1.getVehicleModelFiles());
        vehicleVO.setLabels(vehicleModel1.getLabels());
        vehicleVO.setVehicleModelName(vehicleModel1.getName());
        vehicleVO.setModel(vehicleModel1.getModel());
        vehicleVO.setGears(vehicleModel1.getGears());
        vehicleVO.setCapacity(vehicleModel1.getCapacity());

        return JsonResult.success(vehicleVO);
    }

    @Override
    public JsonResult<VehicleVO> changeStatus(Long id, Integer vehicleStatus) {
        LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        List<LotsRoleVo> roles = currentAdmin.getRoles();

        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        // 验证车辆信息是否存在
        if (!vehicle.isPresent()) {
            return JsonResult.failed(ResultCode.NOT_FOUNT_DATA);
        }

        //不是同一门店 也不是管理员 不能操作
        if (!currentAdmin.getShopId().equals(vehicle.get().getMemberShopId()) || CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() == UserRoleEnum.ADMINISTRATOR.getRole())) {
            return JsonResult.failed(ResultCode.FORBIDDEN);
        }

        vehicle.get().setVehicleStatus(vehicleStatus);

        // 修改
        vehicleRepository.save(vehicle.get());

        return this.selectOne(id);
    }


    @Override
    public JsonResult insertVehicleRentalPlan(VehicleRentalPlan vehicleRentalPlan) {

        final LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();

        if (ObjectUtil.isNull(currentAdmin)) {
            return JsonResult.failed(ResultCode.UNAUTHORIZED);
        }

        // 预留时间不能小于当前时间
        if (!vehicleRentalPlan.getPlanStartTime().isAfter(LocalDateTime.now())) {
            return JsonResult.failed("预留时间不能小于当前时间!");
        }

        // 开始时间不能小于结束时间
        if (vehicleRentalPlan.getPlanStartTime().isAfter(vehicleRentalPlan.getPlanEndTime())){
            return JsonResult.failed("开始时间不能小于结束时间!");
        }

        vehicleRentalPlan.setMemberShopId(currentAdmin.getShopId());
        vehicleRentalPlan.setUserId(currentAdmin.getId());

        // 验证
        int i = vehicleRentalPlanMapper.verificationVehiclePlan(vehicleRentalPlan);

        if (i > 0) {
            return JsonResult.failed("当前时间段该车辆不能被预留!");
        }

        // 设置预留状态
        vehicleRentalPlan.setPlanStatus(VehicleRentalPlanStatusEnum.RESERVED.getStatus());
        vehicleRentalPlan.setCreateTime(LocalDateTime.now());
        vehicleRentalPlan.setIsDelete(DeleteEnum.NO.getIsDelete());
        // 新增
        final VehicleRentalPlan save = vehicleRentalPlanRepository.save(vehicleRentalPlan);

        return JsonResult.success(vehicleRentalPlan);
    }

    @Override
    public JsonResult selectRentalPlanListByVehicleId(Long vehicleId) {
        final LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        final VehicleRentalPlan vehicleRentalPlan = new VehicleRentalPlan();
        vehicleRentalPlan.setVehicleId(vehicleId);
        if (currentAdmin.getShopId() != null) {
            vehicleRentalPlan.setMemberShopId(currentAdmin.getShopId());
        }
        List<VehicleRentalPlan> rentalPlanList = vehicleRentalPlanMapper.findPlanListByVehicleId(vehicleRentalPlan);

        return JsonResult.success(rentalPlanList);
    }

    @Override
    public JsonResult queryAllByCondition(String searchField,Integer status,Long categoryId,Long brandId,Integer page,Integer size) {
        final LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        final Long shopId = currentAdmin.getShopId();
        final List<LotsRoleVo> roles = currentAdmin.getRoles();

        // 如果为管理员  此处未作分页  不允许操作
        if (CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() == UserRoleEnum.ADMINISTRATOR.getRole())) {
                return JsonResult.failed("车辆信息太多,请联系店主或者员工进行查询!");
        }

        PageHelper.startPage(page, size);
        List<VehicleVO> vehicleVOS = null;
        if (shopId != null){
              vehicleVOS = vehicleMapper.queryAllByCondition(shopId,searchField,status,categoryId,brandId);

             if (vehicleVOS.size() == 0){
                 return JsonResult.failed(ResultCode.NOT_FOUNT_DATA);
             }

            for (VehicleVO vOne : vehicleVOS) {
                // 车龄处理
                if (vOne.getCarAge() != null) {
                    // 将车龄转化成int计算
                    final Integer value = Integer.valueOf(vOne.getCarAge());
                    String carAge = "";
                    if (value >= 365) {
                        final int year = value / 365;
                        carAge += year + "年";
                        final int day = value % 365;
                        if (day > 0) {
                            carAge += day + "天";
                        }
                    } else {
                        carAge += value + "天";
                    }
                    vOne.setCarAge(carAge);
                }
            }
             return JsonResult.success(vehicleVOS);
        }
        return JsonResult.failed(ResultCode.FAILED);
    }

    @Override
    public JsonResult otaPageQuery(OTAVehicleVO vo) {
        LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        final List<LotsRoleVo> roles = currentAdmin.getRoles();

        PageHelper.startPage(vo.getPage(), vo.getSize());
        // 如果为管理员  查询所有的车辆
        if (CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() == UserRoleEnum.ADMINISTRATOR.getRole())) {
            List<OTAVehicleVO> otaVehicleVOS = vehicleMapper.otaPageQuery(vo);
            // 查询车辆当前的租赁状态
            return JsonResult.success(JsonPage.restPage(otaVehicleVOS));
        }

        // 如果是店主或员工 查询自己门店下面的车辆
        if (CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() >= UserRoleEnum.SHOPKEEPER.getRole())) {
            Long shopId = currentAdmin.getShopId();
            if (shopId == null) {
                return JsonResult.failed("未关联门店");
            } else {
                vo.setMemberShopId(shopId);
            }
            List<OTAVehicleVO> otaVehicleVOS = vehicleMapper.otaPageQuery(vo);
            return JsonResult.success(JsonPage.restPage(otaVehicleVOS));
        }

        // 如果是车主 查询自己的车辆列表
        if (CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() == UserRoleEnum.CAR_OWNER.getRole())) {
            Long userId = currentAdmin.getId();
            vo.setCarOwnerId(userId);
            vo.setMemberShopId(currentAdmin.getShopId());
            List<OTAVehicleVO> otaVehicleVOS = vehicleMapper.otaPageQuery(vo);
            return JsonResult.success(JsonPage.restPage(otaVehicleVOS));
        }

        return JsonResult.failed(ResultCode.NOT_FOUNT_DATA);
    }

    @Override
    public JsonResult vehicleOTAOpen(Long vehicleId,  Integer platform,Integer open) {
        LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        List<LotsRoleVo> roles = currentAdmin.getRoles();

        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);
        // 验证车辆信息是否存在
        if (!vehicle.isPresent()) {
            return JsonResult.failed("未匹配到车辆信息!");
        }

        //不是管理员 不是同一门店 不能操作
        if (CollUtil.isNotEmpty(roles) && roles.stream().anyMatch(m -> m.getId().intValue() != UserRoleEnum.ADMINISTRATOR.getRole())) {
            if (!currentAdmin.getShopId().equals(vehicle.get().getMemberShopId())){
                return JsonResult.failed(ResultCode.FORBIDDEN);
            }
        }

        if (platform == PlatformEnum.FEIZHU.getType()){
            vehicle.get().setFeizhuOpen(open);
        }

        if (platform == PlatformEnum.XIECHENG.getType()){
            vehicle.get().setXiechengOpen(open);
        }

        if (platform == PlatformEnum.QUNAER.getType()){
            vehicle.get().setQunaerOpen(open);
        }

        // 修改
        vehicleRepository.save(vehicle.get());

        return JsonResult.success(ResultCode.SUCCESS);
    }


    // 查询车辆当前的租赁状态
    private void extracted(List<VehicleVO> vehicleVOS) {
        for (VehicleVO vOne : vehicleVOS) {
            VehicleRentalPlan planByNowDayTime = null;

            // 车龄处理
            if (vOne.getCarAge() != null ){
                // 将车龄转化成int计算
                final Integer value = Integer.valueOf(vOne.getCarAge());
                String carAge = "";
                if (value >= 365){
                    final int year = value / 365;
                    carAge += year + "年";
                    final int day = value % 365;
                    if (day >0){
                        carAge += day+"天";
                    }
                }else {
                    carAge += value + "天";
                }
                vOne.setCarAge(carAge);
            }

            // 当前时间
            final LocalDateTime now = LocalDateTime.now();
            // 车辆当前状态不是异常 查询车辆当前的租赁状态
            if (vOne.getVehicleStatus() != VehicleStatusEnum.ABNORMAL.getStatus()) {
                planByNowDayTime = vehicleRentalPlanMapper.findNewPlanByTime(vOne.getId(), now);
            }
            if (null != planByNowDayTime) {
                vOne.setPlanStatus(planByNowDayTime.getPlanStatus());
                vOne.setRentBeginTime(planByNowDayTime.getPlanStartTime());
                vOne.setRentEndTime(planByNowDayTime.getPlanEndTime());
                // 如果当前车辆的租赁状态为租赁中 查看使用人的用户信息
                if (planByNowDayTime.getPlanStatus() == VehicleRentalPlanStatusEnum.LEASED.getStatus()) {
                    final RentalOrder rentalOrder = rentalOrderMapper.selectRentalOrderBy(planByNowDayTime.getVehicleId(), now);
                    if (rentalOrder != null) {
                        vOne.setCustomInfoId(rentalOrder.getCustomInfoId());
                        vOne.setCustomerName(rentalOrder.getCustomerName());
                    }
                }
            }
        }
    }

    // 返回30天的车辆租赁计划列表
    private List<VehicleRentalPlan> getVehicleRentalPlans(VehicleVO vehicleVO) {
        // 根据车辆id和时间查询车辆的租赁计划
        List<VehicleRentalPlan> vehicleRentalPlanList = new ArrayList<>();
        final Optional<VehicleModel> vehicleModel = vehicleModelRepository.findById(vehicleVO.getVehicleModelId());

        // 默认查询30天的租赁计划  , 开始时间距离当前的时间天数-默认0
        int maxDayNum = 30, toDays = 0;

        // 如果查询了时间
        if (vehicleVO.getPlanBeginTime() != null || vehicleVO.getPlanEndTime() != null) {
            // 计算两个时间段的天数
            final Duration duration = Duration.between(vehicleVO.getPlanBeginTime() == null ? LocalDate.now().atStartOfDay() : vehicleVO.getPlanBeginTime(), vehicleVO.getPlanEndTime() == null ? LocalDateTime.now().plusDays(maxDayNum) : vehicleVO.getPlanEndTime());
            maxDayNum = (int) duration.toDays();

            // 如果查询了开始时间
            if (vehicleVO.getPlanBeginTime() != null) {
                toDays = (int) Duration.between(LocalDate.now().atStartOfDay(), vehicleVO.getPlanBeginTime()).toDays();
            }
        }

        for (int i = 0; i < maxDayNum; i++) {
            // 当天的最大时间
            final LocalDateTime planStartTime = LocalDate.now().plusDays(i + toDays).atStartOfDay().with(LocalTime.MAX);

            // 结束时间 后一天的最小时间
            final LocalDateTime planEndTime = LocalDate.now().plusDays(i + toDays + 1 ).atStartOfDay();

            Integer planStatus = vehicleVO.getPlanStatus();

            if (planStatus == null) {
                planStatus = 0;
            }

            VehicleRentalPlan vehicleRentalPlan = vehicleRentalPlanMapper.findPlanByNowDayTime(vehicleVO.getId(), planStartTime, planEndTime, planStatus);

            // 根据车型id and 当前操作门店id
            final RentList rentOne = vehicleMapper.findRentListByModelId(vehicleVO.getVehicleModelId(),vehicleModel.get().getMemberShopId(),planStartTime, planEndTime);


            if (null != vehicleRentalPlan) {
                if (rentOne != null ){
                    vehicleRentalPlan.setMoney(rentOne.getRentalMoney());
                }else {
                    // 如果当天时间不是周末 价格设置成周内价格
                    if (planStartTime.getDayOfWeek().getValue() == 6 || planStartTime.getDayOfWeek().getValue() == 7){
                        vehicleRentalPlan.setMoney(vehicleModel.get().getWeekExternal());
                    }else {
                        vehicleRentalPlan.setMoney(vehicleModel.get().getWeekWithin());
                    }
                }
                vehicleRentalPlan.setPlanStartTime(planStartTime);
                vehicleRentalPlan.setPlanEndTime(planEndTime);
                vehicleRentalPlanList.add(vehicleRentalPlan);
            } else {
                final VehicleRentalPlan vehicleRentalPlan1 = new VehicleRentalPlan();

                vehicleRentalPlan1.setVehicleId(vehicleVO.getId());
                vehicleRentalPlan1.setPlanStatus(0);
                vehicleRentalPlan1.setMemberShopId(vehicleVO.getMemberShopId());
                vehicleRentalPlan1.setPlanStartTime(planStartTime);
                vehicleRentalPlan1.setPlanEndTime(planEndTime);
                if (rentOne != null ){
                    vehicleRentalPlan1.setMoney(rentOne.getRentalMoney());
                }else {
                    // 如果当天时间不是周末 价格设置成周内价格
                    if (planStartTime.getDayOfWeek().getValue() == 6 || planStartTime.getDayOfWeek().getValue() == 7){
                        vehicleRentalPlan1.setMoney(vehicleModel.get().getWeekExternal());
                    }else {
                        vehicleRentalPlan1.setMoney(vehicleModel.get().getWeekWithin());
                    }
                }
                vehicleRentalPlanList.add(vehicleRentalPlan1);
            }
        }
        return vehicleRentalPlanList;
    }

    // 验证用户证件是否过期
    private void verificationExpired(LotsUserVo currentAdmin, VehicleVO vehicleVO) {
        VehicleRentalPlan vehicleRentalPlan = new VehicleRentalPlan();
        // 验证用户证件是否过期
        if (vehicleVO.getBusinessDate().isBefore(LocalDate.now()) || vehicleVO.getBusinessDate().isEqual(LocalDate.now())) {
            vehicleRentalPlan.setVehicleId(vehicleVO.getId());
            vehicleRentalPlan.setMemberShopId(vehicleVO.getMemberShopId());
            vehicleRentalPlan.setPlanStatus(VehicleRentalPlanStatusEnum.EXPIRED_CERTIFICATE.getStatus());
            vehicleRentalPlan.setUserId(currentAdmin.getId());
            vehicleRentalPlan.setPlanStartTime(LocalDateTime.now());
            vehicleRentalPlan.setPlanEndTime(LocalDateTime.now());
            vehicleRentalPlanRepository.save(vehicleRentalPlan);
        }
    }

    // 车辆分页查询
    private List<VehicleVO> getVehicleVOS(VehicleVO vo, LotsUserVo currentAdmin) {
        List<VehicleVO> vehicleVOS = vehicleMapper.pageQuery(vo);
        for (VehicleVO vehicleVO : vehicleVOS) {
            if (vo.getPlanBeginTime() != null) {
                vehicleVO.setPlanBeginTime(vo.getPlanBeginTime());
            }
            if (vo.getPlanEndTime() != null) {
                vehicleVO.setPlanEndTime(vo.getPlanEndTime());
            }
            if (vo.getPlanStatus() != null) {
                vehicleVO.setPlanStatus(vo.getPlanStatus());
            }

            if (vehicleVO.getCarAge() != null ){
                // 将车龄转化成int计算
                final Integer value = Integer.valueOf(vehicleVO.getCarAge());
                String carAge = "";
                if (value >= 365){
                    final int year = value / 365;
                    carAge += year + "年";
                    final int day = value % 365;
                    if (day >0){
                        carAge += day+"天";
                    }
                }else {
                    carAge += value + "天";
                }

                vehicleVO.setCarAge(carAge);
            }
            // 验证证件是否过期
            verificationExpired(currentAdmin, vehicleVO);


            // 获取车辆租赁计划
            List<VehicleRentalPlan> vehicleRentalPlanList = getVehicleRentalPlans(vehicleVO);

            vehicleVO.setRentalPlans(vehicleRentalPlanList);
        }
        return vehicleVOS;
    }
}
