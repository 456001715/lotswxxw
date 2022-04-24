package com.lots.lots.vehicle.controller;

import com.lots.lots.admin.vehicle.dto.po.RentList;
import com.lots.lots.admin.vehicle.dto.po.VehicleModel;
import com.lots.lots.admin.vehicle.dto.vo.VehicleModelVO;
import com.lots.lots.admin.vehicle.service.VehicleModelService;
import com.lots.lots.common.entity.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 车型管理
 *
 * @author: ld
 * @date: 2021/6/1/ 10：16
 */
@RestController
@Api(tags = {"车型管理"})
@RequestMapping({"/vehicleModel"})
public class VehicleModelController {

    private static final Logger log = LoggerFactory.getLogger(VehicleModelController.class);

    @Resource
    private VehicleModelService vehicleModelService;

    @PostMapping({"/pageQuery"})
    @ApiOperation("车型条件分页查询")
    public JsonResult<VehicleModelVO> pageQuery(@RequestBody VehicleModelVO vo) {
        return this.vehicleModelService.pageQuery(vo);
    }
    
    @GetMapping({"/queryAll"})
    @ApiOperation("所有车型列表")
    public JsonResult<VehicleModelVO> queryListByShopId() {
        return this.vehicleModelService.queryAll();
    }

    @PostMapping({"/findById/{id}"})
    @ApiOperation("根据id查询车型详情")
    public JsonResult<VehicleModelVO> findById(@ApiParam(value = "主键id", required = true) @PathVariable("id") @NotNull @Min(1L) Long id) {
        return this.vehicleModelService.findOneById(id);
    }

    @PostMapping({"/insert"})
    @ApiOperation("车型新增")
    public JsonResult<VehicleModel> insert(@Validated @RequestBody VehicleModel vehicleModel) {
        return this.vehicleModelService.insertModel(vehicleModel);
    }

    @PostMapping({"/update"})
    @ApiOperation("车型修改")
    public JsonResult<VehicleModel> update(@Validated @RequestBody VehicleModel vehicleModel) {
        return this.vehicleModelService.updateModel(vehicleModel);
    }

    @PostMapping({"rentList/update"})
    @ApiOperation("车型租金修改")
    public JsonResult<RentList> updateRentList(@Validated @RequestBody RentList rentList) {
        return this.vehicleModelService.updateRentList(rentList);
    }

    @GetMapping({"/findAll"})
    @ApiOperation("第三方-所有车型列表")
    public JsonResult<VehicleModelVO> findAll() {
        return this.vehicleModelService.findAll();
    }
}
