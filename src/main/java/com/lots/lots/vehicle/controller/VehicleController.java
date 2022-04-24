package com.lots.lots.vehicle.controller;

import com.lots.lots.admin.vehicle.dto.po.Vehicle;
import com.lots.lots.admin.vehicle.dto.po.VehicleRentalPlan;
import com.lots.lots.admin.vehicle.dto.vo.OTAVehicleVO;
import com.lots.lots.admin.vehicle.dto.vo.VehicleVO;
import com.lots.lots.admin.vehicle.service.VehicleService;
import com.lots.lots.common.entity.JsonResult;
import com.lots.lots.common.validation.Insert;
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
 * 车辆管理
 *
 * @author: ld
 * @date: 2021/6/2/ 11：31
 */
@RestController
@Api(tags = {"车辆管理"})
@RequestMapping({"/vehicle"})
public class VehicleController {

    private static final Logger log = LoggerFactory.getLogger(VehicleController.class);

    @Resource
    private VehicleService vehicleService;

    @PostMapping({"/pageQuery"})
    @ApiOperation("车辆管理首页条件分页查询")
    public JsonResult<VehicleVO> pageQuery(@RequestBody VehicleVO vo) {
        return this.vehicleService.pageQuery(vo);
    }

    @GetMapping({"/queryAll"})
    @ApiOperation("车辆列表-带条件查询")
    public JsonResult<VehicleVO> queryAll(
            @ApiParam(value = "车牌号或品牌名称") String searchField,
            @ApiParam(value = "查询车辆状态 全部-不传 1-正常 2-异常 3-租赁中 4-预留中")  Integer status,
            @ApiParam(value = "车辆类别id") Long categoryId,
            @ApiParam(value = "车辆品牌id") Long brandId,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
        return this.vehicleService.queryAllByCondition(searchField,status,categoryId,brandId,pageNum,pageSize);
    }

    @PostMapping({"/pageLeaseQuery"})
    @ApiOperation("车辆管理租赁计划条件分页查询")
    public JsonResult<VehicleVO> pageLeaseQuery(@RequestBody VehicleVO vo) {
        return this.vehicleService.pageLeaseQuery(vo);
    }

    @PostMapping({"/insert"})
    @ApiOperation("车辆新增")
    public JsonResult<Vehicle> insert(@Validated(Insert.class) @RequestBody Vehicle vehicle) {
        return this.vehicleService.insert(vehicle);
    }

    @PostMapping({"/update"})
    @ApiOperation("车辆修改")
    public JsonResult<Vehicle> update(@Validated @RequestBody Vehicle vehicle) {
        return this.vehicleService.update(vehicle);
    }

    @PostMapping({"/selectOne/{id}"})
    @ApiOperation("根据车辆主键查询")
    public JsonResult<VehicleVO> selectOne(@ApiParam(value = "主键id", required = true) @PathVariable("id") @NotNull @Min(1L) Long id) {
        return this.vehicleService.selectOne(id);
    }

    @PostMapping({"changeStatus/{vehicleId}/{vehicleStatus}"})
    @ApiOperation("修改车辆状态")
    public JsonResult<VehicleVO> changeStatus(
            @ApiParam(value = "车辆id", required = true)
            @PathVariable
            @NotNull
            @Min(1L) Long vehicleId,
            @ApiParam(value = "车辆状态 1-正常 2-异常 ", required = true)
            @NotNull
            @PathVariable
            Integer vehicleStatus
    ) {
        return this.vehicleService.changeStatus(vehicleId,vehicleStatus);
    }

    @PostMapping({"/selectRentalPlanList/{vehicleId}"})
    @ApiOperation("车辆详情租赁计划列表")
    public JsonResult<VehicleRentalPlan>selectRentalPlanList( @ApiParam(value = "车辆id", required = true)
                                                @PathVariable
                                                @NotNull
                                                @Min(1L) Long vehicleId) {
        return this.vehicleService.selectRentalPlanListByVehicleId(vehicleId);
    }

    @PostMapping({"/planInsert"})
    @ApiOperation("车辆租赁计划预留")
    public JsonResult<VehicleRentalPlan> planInsert(@Validated @RequestBody VehicleRentalPlan vehicleRentalPlan) {
        return this.vehicleService.insertVehicleRentalPlan(vehicleRentalPlan);
    }

    @PostMapping({"/otaPageQuery"})
    @ApiOperation("OTA车辆列表")
    public JsonResult<OTAVehicleVO> otaPageQuery(@RequestBody OTAVehicleVO vo) {
        return this.vehicleService.otaPageQuery(vo);
    }

    @PostMapping({"vehicleOTAOpen/{vehicleId}/{platform}/{open}"})
    @ApiOperation("OTA车辆平台开关")
    public JsonResult vehicleOTAOpen(
            @ApiParam(value = "车辆id", required = true)
            @PathVariable
            @NotNull
            @Min(1L) Long
                    vehicleId,
            @ApiParam(value = "平台(0-飞猪 1-携程 2-去哪儿)", required = true)
            @PathVariable
            @NotNull
            @Min(1L) Integer
                    platform,
            @ApiParam(value = "是否开启 0-关闭 1-开启 ", required = true)
            @NotNull
            @PathVariable
                    Integer open
    ) {
        return this.vehicleService.vehicleOTAOpen(vehicleId,platform,open);
    }
}
