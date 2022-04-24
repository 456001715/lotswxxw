package com.lots.lots.vehicle.repository;

import com.lots.lots.admin.vehicle.dto.po.VehicleOrderRemarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 出车检验之备注信息 repository
 */
@Repository
public interface VehicleOrderRemarksRepository extends JpaRepository<VehicleOrderRemarks,Long> {
    /**
     * 根据用户id 订单id 车辆id查询一条备注信息
     * @param userId
     * @param orderId
     * @param vehicleId
     * @return
     */
    VehicleOrderRemarks findByUserIdAndOrderIdAndVehicleId(Long userId, Long orderId, Long vehicleId);
}
