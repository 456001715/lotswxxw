package com.lots.lots.vehicle.controller;

import com.lots.lots.admin.vehicle.dto.po.VehicleOutputVolume;
import com.lots.lots.admin.vehicle.service.VehicleOutputVolumeService;
import com.lots.lots.common.entity.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 车辆排量
 *
 * @author: ld
 * @date: 2021/6/1/ 10：16
 */
@RestController
@Api(tags = {"车辆排量"})
@RequestMapping({"/outputVolume"})
public class VehicleOutputVolumeController {

    private static final Logger log = LoggerFactory.getLogger(VehicleCategoryController.class);

    @Resource
    private VehicleOutputVolumeService vehicleOutputVolumeService;

    @PostMapping({"/queryAll"})
    @ApiOperation("车辆排量列表")
    public JsonResult<VehicleOutputVolume> queryAll() {
        return this.vehicleOutputVolumeService.queryAll();
    }
}
