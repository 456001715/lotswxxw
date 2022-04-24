package com.lots.lots.vehicle.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.lots.lots.admin.user.service.LotsUserService;
import com.lots.lots.admin.vehicle.dto.po.VehicleOutputVolume;
import com.lots.lots.admin.vehicle.repository.VehicleOutputVolumeRepository;
import com.lots.lots.admin.vehicle.service.VehicleOutputVolumeService;
import com.lots.lots.common.entity.JsonResult;
import com.lots.lots.common.entity.ResultCode;
import com.lots.lots.common.entity.user.LotsUserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 车辆排量service
 */
@Service
public class VehicleOutputVolumeServiceImpl implements VehicleOutputVolumeService {

    @Resource
    private LotsUserService lotsUserService;

    @Resource
    private VehicleOutputVolumeRepository vehicleOutputVolumeRepository;

    @Override
    public JsonResult queryAll() {
        // 获取当前登录用户
        LotsUserVo admin = lotsUserService.getCurrentAdmin();

        if (ObjectUtil.isNull(admin)){
            return JsonResult.failed(ResultCode.UNAUTHORIZED);
        }

        final List<VehicleOutputVolume> volumeList = vehicleOutputVolumeRepository.findAll();

        if (volumeList.size() == 0){
            return JsonResult.failed(ResultCode.NOT_FOUNT_DATA);
        }

        return JsonResult.success(volumeList);
    }
}
