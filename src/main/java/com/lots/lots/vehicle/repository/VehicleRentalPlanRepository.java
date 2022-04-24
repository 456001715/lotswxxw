package com.lots.lots.vehicle.repository;

import com.lots.lots.admin.vehicle.dto.po.VehicleRentalPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 车辆租赁计划 repository
 */
@Repository
public interface VehicleRentalPlanRepository  extends JpaRepository<VehicleRentalPlan, Long> {
    /**
     * 根据车辆id查租赁计划
     * @param vehicleId 车辆id
     * @return 租赁计划
     */
    Optional<VehicleRentalPlan> findByVehicleId(Long vehicleId);
}
