package com.lots.lots.vehicle.repository;

import com.lots.lots.admin.vehicle.dto.po.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 车辆 Repository 管理
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    /**
     * 根据门店id查询车辆信息
     * @param shopId
     * @param delete
     * @return
     */
    List<Vehicle> findAllByMemberShopIdAndIsDelete(Long shopId,Integer delete);
}
