package com.lots.lots.vehicle.controller;

import com.lots.lots.admin.vehicle.dto.po.VehicleCategory;
import com.lots.lots.admin.vehicle.service.VehicleCategoryService;
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
 * 车辆类别管理
 *
 * @author: ld
 * @date: 2021/6/5/ 13：25
 */
@RestController
@Api(tags = {"车辆类别管理"})
@RequestMapping({"/vehicleCategory"})
public class VehicleCategoryController {

    private static final Logger log = LoggerFactory.getLogger(VehicleCategoryController.class);

    @Resource
    private VehicleCategoryService vehicleCategoryService;

    @PostMapping({"/queryAll"})
    @ApiOperation("车辆类别列表")
    public JsonResult<VehicleCategory> queryAll() {
        return this.vehicleCategoryService.queryAll();
    }
}
