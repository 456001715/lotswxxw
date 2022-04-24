package com.lots.lots.vehicle.service;

import com.lots.lots.common.entity.JsonResult;
import com.lots.lots.common.entity.vehicle.ReturnVehicleVO;

public interface ReturnVehicleService {
    /**
     * 检验收车所需数据
     */
    JsonResult<ReturnVehicleVO> data(Long orderId);

    /**
     * 结算
     */
    JsonResult<ReturnVehicleVO> settleAccounts(ReturnVehicleVO returnVehicleVO);
}
