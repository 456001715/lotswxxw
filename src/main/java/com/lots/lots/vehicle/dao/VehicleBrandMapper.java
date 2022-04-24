package com.lots.lots.vehicle.dao;

import com.lots.lots.admin.vehicle.dto.po.VehicleBrand;

/**
 * 车辆品牌 mapper
 */
public interface VehicleBrandMapper {

    /**
     * 根据主键id查询一条车辆品牌信息
     * @param brandId
     * @return
     */
    VehicleBrand findBrandNameById(Long brandId);
}
