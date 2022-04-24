package com.lots.lots.vehicle.dao;

import com.lots.lots.common.entity.vehicle.VehicleOrderRemarksVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车辆订单备注 - mapper
 */
public interface VehicleOrderRemarksMapper {
    /**
     * 根据车辆id and 订单id查询一条备注信息
     * @param vehicleId
     * @param orderId
     * @return
     */
    List<VehicleOrderRemarksVo> findOneByCondition(@Param("vehicleId") Long vehicleId, @Param("orderId")Long orderId);
}
