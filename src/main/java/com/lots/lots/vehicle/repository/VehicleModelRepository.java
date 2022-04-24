package com.lots.lots.vehicle.repository;

import com.lots.lots.admin.vehicle.dto.po.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleModelRepository  extends JpaRepository<VehicleModel, Long> {
}
