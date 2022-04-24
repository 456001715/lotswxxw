package com.lots.lots.vehicle.dao;

import com.lots.lots.admin.vehicle.dto.po.VehicleGoodsCheck;
import org.apache.ibatis.annotations.Mapper;

/**
 * 车辆检验表 - Mapper
 */
@Mapper
public interface VehicleGoodsCheckMapper {
    /**
     * 修改检验表
     * @param vehicleGoodsCheck
     * @return
     */
    int update(VehicleGoodsCheck vehicleGoodsCheck);
}
