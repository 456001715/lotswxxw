package com.lots.lots.vehicle.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.lots.lots.admin.user.service.LotsUserService;
import com.lots.lots.admin.vehicle.dao.RentListMapper;
import com.lots.lots.admin.vehicle.dao.VehicleMapper;
import com.lots.lots.admin.vehicle.dao.VehicleModelMapper;
import com.lots.lots.admin.vehicle.dto.po.RentList;
import com.lots.lots.admin.vehicle.dto.po.VehicleModel;
import com.lots.lots.admin.vehicle.dto.vo.VehicleModelVO;
import com.lots.lots.admin.vehicle.repository.VehicleModelRepository;
import com.lots.lots.admin.vehicle.service.VehicleModelService;
import com.lots.lots.common.entity.JsonPage;
import com.lots.lots.common.entity.JsonResult;
import com.lots.lots.common.entity.ResultCode;
import com.lots.lots.common.entity.user.LotsRoleVo;
import com.lots.lots.common.entity.user.LotsUserVo;
import com.lots.lots.common.enums.DeleteEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleModelServiceImpl implements VehicleModelService {

    @Resource
    private VehicleModelRepository vehicleModelRepository;

    @Resource
    private LotsUserService lotsUserService;

    @Resource
    private VehicleModelMapper vehicleModelMapper;

    @Resource
    private RentListMapper rentListMapper;

    @Resource
    private VehicleMapper vehicleMapper;

    @Override
    public JsonResult insertModel(VehicleModel vehicleModel) {

        // 获取当前登录用户
        LotsUserVo admin = lotsUserService.getCurrentAdmin();
        final List<LotsRoleVo> roles = admin.getRoles();
        if (ObjectUtil.isNull(admin)){
            return JsonResult.failed(ResultCode.UNAUTHORIZED);
        }

        //如果为管理员
        if(CollUtil.isNotEmpty(roles)&& roles.stream().anyMatch(m->m.getId().equals(1L))){
            if(vehicleModel.getMemberShopId() == null){
                return JsonResult.failed("请选择一个门店");
            }
        }else {

            final Long shopId = admin.getShopId();

            vehicleModel.setMemberShopId(shopId);
        }


        if (vehicleModel.getBrandId() == null){
            return JsonResult.failed("品牌不能为空！");
        }

        if (vehicleModel.getCategoryId() == null){
            return JsonResult.failed("车辆类别不能为空!");
        }

        if (vehicleModel.getName() == null){
            return JsonResult.failed("名称不能为空!");
        }

        if (vehicleModel.getGears() == null){
            return JsonResult.failed("排挡不能为空！");
        }

        if (vehicleModel.getCapacity() == null){
            return JsonResult.failed("座位数不能为空！");
        }

        if (vehicleModel.getOutputVolumeId() == null){
            return JsonResult.failed("排量不能为空!");
        }

        if (vehicleModel.getModel() == null){
            return JsonResult.failed("燃油型号不能为空！");
        }

        if (vehicleModel.getVehicleModelFiles() == null){
            return JsonResult.failed("车型图片不能为空！");
        }

        if (vehicleModel.getRealPicturesFiles() == null){
            return JsonResult.failed("实拍图片不能为空！");
        }

        if (vehicleModel.getRentalMoney() == null){
            return JsonResult.failed("租车押金不能为空！");
        }

        if (vehicleModel.getBreakRulesMoney() == null){
            return JsonResult.failed("违章押金不能为空");
        }

        if (vehicleModel.getProtectionMoney() == null){
            return JsonResult.failed("基本保障费不能为空");
        }

        if (vehicleModel.getWeekWithin() == null){
            return JsonResult.failed("周内价格不能为空");
        }

        if (vehicleModel.getWeekExternal() == null){
            return JsonResult.failed("周末价格不能为空");
        }

        // 查询当前门店下面的所有车型
        List<VehicleModel> vehicleModelList = vehicleModelMapper.findModelByMemberShop(vehicleModel.getMemberShopId());

        for (VehicleModel one:vehicleModelList) {
            // 判断当前门店下面的车型名称是否存在重合
            if (one.getName().replace(" ","").equals(vehicleModel.getName().replace(" ",""))){
                return JsonResult.failed("该门店下此车型名称已存在！");
            }
        }
        vehicleModel.setIsDelete(DeleteEnum.NO.getIsDelete());
        vehicleModel.setCreateTime(LocalDateTime.now());
        vehicleModelRepository.save(vehicleModel);
        return JsonResult.success("添加成功");
    }


    @Override
    public JsonResult updateModel(VehicleModel vehicleModel) {
        // 获取当前登录用户
        LotsUserVo admin = lotsUserService.getCurrentAdmin();

        final List<LotsRoleVo> roles = admin.getRoles();
        if (ObjectUtil.isNull(admin)){
            return JsonResult.failed(ResultCode.UNAUTHORIZED);
        }

        //如果为管理员
        if(CollUtil.isNotEmpty(roles)&& roles.stream().anyMatch(m->m.getId().equals(1L))){
            if(vehicleModel.getMemberShopId() == null){
                return JsonResult.failed("请选择一个门店");
            }
        }else {
            final Long shopId = admin.getShopId();
            vehicleModel.setMemberShopId(shopId);
        }

        // 查询当前门店下面的所有车型
        List<VehicleModel> vehicleModelList = vehicleModelMapper.findModelByMemberShop(vehicleModel.getMemberShopId());

        for (VehicleModel one:vehicleModelList) {
            // 判断当前门店下面的车型名称是否存在重合
            if (!one.getId().equals(vehicleModel.getId()) && one.getName().replace(" ","").equals(vehicleModel.getName().replace(" ",""))){
                return JsonResult.failed("该门店下此车型名称已存在！");
            }
        }
        final int i = vehicleModelMapper.update(vehicleModel);

        if (i != 1){
            return JsonResult.failed("修改失败");
        }

        return JsonResult.success(vehicleModel);
    }

    @Override
    public JsonResult pageQuery(VehicleModelVO vo) {
        LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        Long shopId = currentAdmin.getShopId();
        final List<LotsRoleVo> roles = currentAdmin.getRoles();

        PageHelper.startPage(vo.getPage(), vo.getSize());
        //如果为管理员
        if(CollUtil.isNotEmpty(roles)&& roles.stream().anyMatch(m->m.getId().equals(1L))){
            List<VehicleModelVO> vehicleModelVOList = vehicleModelMapper.queryPage(vo);

            for (VehicleModelVO one:vehicleModelVOList) {
                final int vehicleNum = vehicleMapper.findVehicleNumByModelIdAndMemberShopId(one.getId(), null);
                one.setVehicleNumber(vehicleNum);
            }

            return JsonResult.success(JsonPage.restPage(vehicleModelVOList));
        }

        if(shopId == null){
            return JsonResult.failed("未查询到您的门店信息，请先去关联门店！");
        }
        vo.setMemberShopId(shopId);
        List<VehicleModelVO> vehicleModelVOList = vehicleModelMapper.queryPage(vo);

        for (VehicleModelVO one:vehicleModelVOList) {
            final int vehicleNum = vehicleMapper.findVehicleNumByModelIdAndMemberShopId(one.getId(), shopId);
            one.setVehicleNumber(vehicleNum);
        }
        return JsonResult.success(JsonPage.restPage(vehicleModelVOList));
    }

    @Override
    public JsonResult findOneById(Long id) {
        final LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        if (null == currentAdmin){
            return JsonResult.success(ResultCode.UNAUTHORIZED);
        }

        VehicleModelVO vehicleModelVO  = vehicleModelMapper.findById(id);

        if (ObjectUtil.isNull(vehicleModelVO)){
            return JsonResult.failed("未查询到当前车型详情信息！");
        }

        // 查询当前车型的车辆数量
        int vehicleNum = vehicleMapper.findVehicleNumByModelIdAndMemberShopId(vehicleModelVO.getId(),vehicleModelVO.getMemberShopId());

        vehicleModelVO.setVehicleNumber(vehicleNum);

        // 根据车型id查询当前车型下面的租金列表
        List<RentList> rentLists = getRentLists(id, vehicleModelVO);
        vehicleModelVO.setRentLists(rentLists);

        return JsonResult.success(vehicleModelVO);
    }

    @Override
    public JsonResult updateRentList(RentList rentList) {
        final LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();

        if (null == currentAdmin){
            return JsonResult.failed(ResultCode.UNAUTHORIZED);
        }

        // 只能修改之后的时间
        if (!rentList.getRentalTime().isBefore(LocalDateTime.now())){
            return JsonResult.failed("不能修改今天以前的数据");
        }

        // 新增
        if (null == rentList.getId()){
            rentList.setMemberShopId(currentAdmin.getShopId());
            // 新增
            rentListMapper.insertRentList(rentList);
            return JsonResult.success("设置成功");
        }else {
            // 修改
            rentListMapper.updateRentList(rentList);
            return JsonResult.success("重置成功");
        }
    }

    @Override
    public JsonResult queryAll() {
        LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        Long shopId = currentAdmin.getShopId();
        final List<LotsRoleVo> roles = currentAdmin.getRoles();
        final VehicleModelVO vehicleModelVO = new VehicleModelVO();
        //如果为管理员
        if(CollUtil.isNotEmpty(roles)&& roles.stream().anyMatch(m->m.getId().equals(1L))){
            vehicleModelVO.setMemberShopId(null);
            List<VehicleModelVO> vehicleModelVOList = vehicleModelMapper.queryPage(vehicleModelVO);

            for (VehicleModelVO one:vehicleModelVOList) {
                final int vehicleNum = vehicleMapper.findVehicleNumByModelIdAndMemberShopId(one.getId(), null);
                one.setVehicleNumber(vehicleNum);
            }

            return JsonResult.success(JsonPage.restPage(vehicleModelVOList));
        }

        if(shopId == null){
            return JsonResult.failed("未查询到您的门店信息，请先去关联门店！");
        }
        vehicleModelVO.setMemberShopId(shopId);
        List<VehicleModelVO> vehicleModelVOList = vehicleModelMapper.queryPage(vehicleModelVO);
        for (VehicleModelVO one:vehicleModelVOList) {
            final int vehicleNum = vehicleMapper.findVehicleNumByModelIdAndMemberShopId(one.getId(), shopId);
            one.setVehicleNumber(vehicleNum);
        }
        return JsonResult.success(JsonPage.restPage(vehicleModelVOList));
    }

    @Override
    public JsonResult findAll() {
        LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        Long shopId = currentAdmin.getShopId();
        final List<LotsRoleVo> roles = currentAdmin.getRoles();
        final VehicleModelVO vehicleModelVO = new VehicleModelVO();
        //如果为管理员
        if(CollUtil.isNotEmpty(roles)&& roles.stream().anyMatch(m->m.getId().equals(1L))){
            vehicleModelVO.setMemberShopId(null);
            List<VehicleModelVO> vehicleModelVOList = vehicleModelMapper.queryPage(vehicleModelVO);

            for (VehicleModelVO one:vehicleModelVOList) {
                final int vehicleNum = vehicleMapper.findVehicleNumByModelIdAndMemberShopId(one.getId(), null);
                final List<RentList> rentLists = getRentLists(one.getId(), one);
                one.setRentLists(rentLists);
                one.setVehicleNumber(vehicleNum);
            }

            return JsonResult.success(JsonPage.restPage(vehicleModelVOList));
        }

        if(shopId == null){
            return JsonResult.failed("未查询到您的门店信息，请先去关联门店！");
        }
        vehicleModelVO.setMemberShopId(shopId);
        List<VehicleModelVO> vehicleModelVOList = vehicleModelMapper.queryPage(vehicleModelVO);
        for (VehicleModelVO one:vehicleModelVOList) {
            final int vehicleNum = vehicleMapper.findVehicleNumByModelIdAndMemberShopId(one.getId(), shopId);
            final List<RentList> rentLists = getRentLists(one.getId(), one);
            one.setRentLists(rentLists);
            one.setVehicleNumber(vehicleNum);
        }
        return JsonResult.success(JsonPage.restPage(vehicleModelVOList));
    }


    // 根据车型id查询当前车型下面的租金列表
    private List<RentList> getRentLists(Long id, VehicleModelVO vehicleModelVO) {
        // 根据车型id查询当前车型下面的租金列表
        List<RentList> rentLists = new ArrayList<>();
        // 生成当前车型30天的租金列表
        for (int i = 0; i < 29; i++) {
            // 今天开始往前第七天的早上凌晨
            final LocalDateTime planStartTime = LocalDate.now().plusDays(i-7).atStartOfDay();
            // 当天的晚上凌晨
            final LocalDateTime planEndTime = planStartTime.plusHours(24);
            // 根据车型id and 当前操作门店id
            final RentList rentOne = vehicleModelMapper.findRentListByModelId(id, vehicleModelVO.getMemberShopId(), planStartTime, planEndTime);
            if (null != rentOne){
                rentLists.add(rentOne);
            }else {
                final RentList rentList = new RentList();
                rentList.setRentalTime(planStartTime);
                // 如果当天时间不是周末 价格设置成周内价格
                if (planStartTime.getDayOfWeek().getValue() == 6 || planStartTime.getDayOfWeek().getValue() == 7){
                    rentList.setRentalMoney(vehicleModelVO.getWeekExternal());
                }else {
                    rentList.setRentalMoney(vehicleModelVO.getWeekWithin());
                }
                rentList.setId(Long.valueOf(i+1));
                rentList.setModelId(vehicleModelVO.getId());
                rentList.setMemberShopId(vehicleModelVO.getMemberShopId());

                rentLists.add(rentList);
            }
        }
        return rentLists;
    }
}
