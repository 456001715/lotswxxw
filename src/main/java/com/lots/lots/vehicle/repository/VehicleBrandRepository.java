package com.lots.lots.vehicle.repository;

import com.lots.lots.admin.vehicle.dto.po.VehicleBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 车辆品牌 Repository
 */
@Repository
public interface VehicleBrandRepository extends JpaRepository<VehicleBrand, Long> {
}
