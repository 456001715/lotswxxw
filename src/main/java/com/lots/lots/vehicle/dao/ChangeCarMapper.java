package com.lots.lots.vehicle.dao;

import com.lots.lots.admin.vehicle.dto.po.ChangeCar;

import java.time.LocalDateTime;

/**
 * 还车信息 - mapper
 */
public interface ChangeCarMapper {

    /**
     * 根据违章时间 和 车辆信息id 匹配一条还车信息
     * @param rulesTime
     * @param vehicleId
     * @return
     */
    ChangeCar matchingOrder(LocalDateTime rulesTime, Long vehicleId);
}
