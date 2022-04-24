package com.lots.lots.vehicle.repository;

import com.lots.lots.admin.vehicle.dto.po.VehicleCarOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 车主车辆关联表 - repository
 */
@Repository
public interface VehicleCarOwnerRepository extends JpaRepository<VehicleCarOwner,Long>{
}
