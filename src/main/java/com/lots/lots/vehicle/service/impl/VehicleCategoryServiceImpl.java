package com.lots.lots.vehicle.service.impl;

import com.lots.lots.admin.user.service.LotsUserService;
import com.lots.lots.admin.vehicle.dao.VehicleCategoryMapper;
import com.lots.lots.admin.vehicle.dto.po.VehicleCategory;
import com.lots.lots.admin.vehicle.service.VehicleCategoryService;
import com.lots.lots.common.entity.JsonResult;
import com.lots.lots.common.entity.ResultCode;
import com.lots.lots.common.entity.user.LotsUserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 车辆类别 serviceimpl
 */
@Service
public class VehicleCategoryServiceImpl implements VehicleCategoryService {

    @Resource
    private VehicleCategoryMapper vehicleCategoryMapper;

    @Resource
    private LotsUserService lotsUserService;

    @Override
    public JsonResult queryAll() {
        final LotsUserVo currentAdmin = lotsUserService.getCurrentAdmin();
        if (Objects.isNull(currentAdmin)){
            return JsonResult.failed(ResultCode.UNAUTHORIZED);
        }

        List<VehicleCategory> list = vehicleCategoryMapper.findAll();
        return JsonResult.success(list);
    }
}
