package com.lots.lots.vehicle.repository;

import com.lots.lots.admin.vehicle.dto.po.VehicleGoods;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 员工端-车辆物件表 repository
 */
public interface VehicleGoodsRepository extends JpaRepository<VehicleGoods, Long> {
}
