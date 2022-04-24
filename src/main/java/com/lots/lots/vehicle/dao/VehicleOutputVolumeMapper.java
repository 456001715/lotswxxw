package com.lots.lots.vehicle.dao;

import com.lots.lots.admin.vehicle.dto.po.VehicleOutputVolume;

/**
 * 车辆排量 mapper
 */
public interface VehicleOutputVolumeMapper {

    /**
     * 根据主键查询一条车辆排量信息
     * @param outputVolumeId
     * @return
     */
    VehicleOutputVolume findOutputVolumeById(Long outputVolumeId);
}
