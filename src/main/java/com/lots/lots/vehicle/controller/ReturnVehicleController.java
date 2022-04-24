package com.lots.lots.vehicle.controller;

import com.lots.lots.admin.vehicle.service.ReturnVehicleService;
import com.lots.lots.common.entity.JsonResult;
import com.lots.lots.common.entity.vehicle.ReturnVehicleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * 还车
 */
@Api(tags = "检验收车")
@RestController
@RequestMapping("/return/vehicle")
public class ReturnVehicleController {
    @Resource
    private ReturnVehicleService returnVehicleService;

    @GetMapping("/{orderId}")
    @ApiOperation("检验收车")
    public JsonResult<ReturnVehicleVO> data(
            @Validated
            @NotNull(message = "订单id不能为空")
            @PathVariable Long orderId
    ) {
        return this.returnVehicleService.data(orderId);
    }

    @PostMapping
    @ApiOperation("结算")
    public JsonResult<ReturnVehicleVO> settleAccounts(@RequestBody ReturnVehicleVO returnVehicleVO) {
        return this.returnVehicleService.settleAccounts(returnVehicleVO);
    }
}
