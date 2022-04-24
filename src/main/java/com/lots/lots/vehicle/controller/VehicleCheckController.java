package com.lots.lots.vehicle.controller;

import com.lots.lots.admin.order.dto.po.RentalOrder;
import com.lots.lots.admin.vehicle.dto.po.VehicleCertificates;
import com.lots.lots.admin.vehicle.dto.po.VehicleOrderRemarks;
import com.lots.lots.admin.vehicle.service.VehicleCheckService;
import com.lots.lots.common.entity.JsonResult;
import com.lots.lots.common.entity.vehicle.VehicleCheckVo;
import com.lots.lots.common.entity.vehicle.VehicleReturnCheckVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 员工端-出车检验
 *
 * @author: ld
 * @date: 2021/6/10/ 10：31
 */
@RestController
@Api(tags = {"车辆出车检验"})
@RequestMapping({"/vehicleCheck"})
public class VehicleCheckController {

    @Resource
    private VehicleCheckService vehicleCheckService;

    @PostMapping({"/queryGoods/{vehicleId}/{orderId}"})
    @ApiOperation("车辆物件列表")
    public JsonResult<VehicleCheckVo> queryVehicleGoods(@ApiParam(value = "车辆id", required = true)
                                             @PathVariable
                                             @NotNull
                                             @Min(1L) Long vehicleId,
                                                        @ApiParam(value = "订单id", required = true)
                                             @PathVariable
                                             @NotNull
                                             @Min(1L) Long orderId) {
        return this.vehicleCheckService.queryVehicleGoods(vehicleId,orderId);
    }

    @PostMapping({"/changeOilOrMileage"})
    @ApiOperation("修改车辆里程/油量")
    public JsonResult<RentalOrder> changeOilOrMileage(@RequestBody RentalOrder rentalOrder) {
        return this.vehicleCheckService.changeOilOrMileage(rentalOrder);
    }

    @PostMapping({"/addRecord"})
    @ApiOperation("出车检验-点击完成")
    public JsonResult<VehicleReturnCheckVo> addRecord(@RequestBody VehicleReturnCheckVo vehicleReturnCheckVo) {
        return this.vehicleCheckService.staffVehicleCheck(vehicleReturnCheckVo);
    }

    @PostMapping({"/addRemarks"})
    @ApiOperation("记录检验备注")
    public JsonResult<VehicleOrderRemarks> addRemarks(VehicleOrderRemarks vehicleOrderRemarks) {
        return this.vehicleCheckService.addCheckRemarks(vehicleOrderRemarks);
    }

    /*@PostMapping({"/addOrderDelivery"})
    @ApiOperation("出车检验-点击完成")
    public JsonResult<RentalOrderDelivery> orderDelivery(RentalOrderDelivery rentalOrderDelivery) {
        return this.vehicleCheckService.addOrderDelivery(rentalOrderDelivery);
    }*/

    @PostMapping({"/deliver"})
    @ApiOperation("员工-交付车辆")
    public JsonResult<VehicleCertificates> deliver(@RequestBody VehicleCertificates vehicleCertificates) {
        return this.vehicleCheckService.deliver(vehicleCertificates);
    }
}
