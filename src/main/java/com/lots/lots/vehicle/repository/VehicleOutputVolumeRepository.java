package com.lots.lots.vehicle.repository;

import com.lots.lots.admin.vehicle.dto.po.VehicleOutputVolume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 车辆排量repository
 */
@Repository
public interface VehicleOutputVolumeRepository extends JpaRepository<VehicleOutputVolume,Long> {
}
