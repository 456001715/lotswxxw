package com.lots.lots.vehicle.service;

import com.lots.lots.admin.order.dto.po.RentalOrder;
import com.lots.lots.common.entity.JsonResult;
import com.lots.lots.common.entity.vehicle.TransferVO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 换车
 */
public interface TransferService {
    /**
     * 计算差价
     */
    JsonResult<BigDecimal> calcDifferencePrice(TransferVO transferVO);

    /**
     * 提交换车信息
     */
    JsonResult<TransferVO> commit(TransferVO transferVO);

    /**
     * 更新在用车辆租赁计划
     */
    void updateVehiclesInUsePlan(RentalOrder rentalOrder, LocalDateTime transferBeginTime);

    /**
     * 添加换车车辆租赁计划
     */
    void addTransferVehiclePlan(TransferVO transferVO, RentalOrder rentalOrder, LocalDateTime transferBeginTime);
}
