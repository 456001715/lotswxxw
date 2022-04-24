package com.lots.lots.vehicle.dao;

import com.lots.lots.admin.vehicle.dto.po.VehicleCarOwner;

import java.util.List;

public interface VehicleCarOwnerMapper {
    /**
     * 根据车主user——id查询列表
     * @param id
     * @return
     */
    List<VehicleCarOwner> findListByUserId(Long userId);
}
