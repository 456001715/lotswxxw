package com.lots.lots.vehicle.service.impl;

import com.lots.lots.admin.vehicle.dto.po.VehicleBrand;
import com.lots.lots.admin.vehicle.repository.VehicleBrandRepository;
import com.lots.lots.admin.vehicle.service.VehicleBrandService;
import com.lots.lots.common.entity.JsonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 车辆品牌 service
 */
@Service
public class VehicleBrandServiceImpl implements VehicleBrandService {

    @Resource
    private VehicleBrandRepository vehicleBrandRepository;
    @Override
    public JsonResult queryAll() {
        List<VehicleBrand> list = vehicleBrandRepository.findAll();
        return JsonResult.success(list);
    }
}
