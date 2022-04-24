package com.lots.lots.vehicle.controller;


import com.lots.lots.admin.vehicle.dto.po.VehicleBrand;
import com.lots.lots.admin.vehicle.service.VehicleBrandService;
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
 * 车辆品牌管理
 *
 * @author: ld
 * @date: 2021/6/5/ 13：18
 */
@RestController
@Api(tags = {"车辆品牌管理"})
@RequestMapping({"/vehicleBrand"})
public class VehicleBrandController {

    private static final Logger log = LoggerFactory.getLogger(VehicleBrandController.class);

    @Resource
    private VehicleBrandService vehicleBrandService;

    @PostMapping({"/queryAll"})
    @ApiOperation("车辆品牌列表")
    public JsonResult<VehicleBrand> queryAll() {
        return this.vehicleBrandService.queryAll();
    }
}
