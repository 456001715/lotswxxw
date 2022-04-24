package com.lots.lots.vehicle.dao;

import com.lots.lots.common.entity.vehicle.VehicleGoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车辆物件 - mapper
 */
public interface VehicleGoodsMapper {
    /**
     * 根据车辆id查询当前车辆的物件列表
     *
     * @param parentId
     * @param vehicleId
     * @return
     */
    List<VehicleGoodsVo> findGoodsListByVehicleId(@Param("parentId") Long parentId,
                                                  @Param("vehicleId") Long vehicleId,
                                                  @Param("orderId") Long orderId);

    /**
     * 查询最高级的车辆配件列表
     *
     * @param parentId
     * @return
     */
    List<VehicleGoodsVo> findGoodsList(@Param("parentId") Long parentId);
}
