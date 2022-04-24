package com.lots.lots.vehicle.controller;

import com.lots.lots.admin.vehicle.service.TransferService;
import com.lots.lots.common.entity.JsonResult;
import com.lots.lots.common.entity.vehicle.TransferVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 换车
 */
@RestController
@Api(tags = "换车")
@RequestMapping("/transfer")
public class TransferController {
    @Resource
    private TransferService transferService;

    @PostMapping("/differenceOfPrices")
    @ApiOperation("获取差价")
    public JsonResult<BigDecimal> getDifferenceOfPrices(@RequestBody TransferVO transferVO) {
        return this.transferService.calcDifferencePrice(transferVO);
    }

    @PostMapping("/commit")
    @ApiOperation("提交换车信息")
    public JsonResult<TransferVO> commit(@RequestBody TransferVO transferVO) {
        return this.transferService.commit(transferVO);
    }
}
