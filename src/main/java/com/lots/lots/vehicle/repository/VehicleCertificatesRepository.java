package com.lots.lots.vehicle.repository;

import com.lots.lots.admin.vehicle.dto.po.VehicleCertificates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 交互车辆证件表 - repository
 */
@Repository
public interface VehicleCertificatesRepository extends JpaRepository<VehicleCertificates,Long> {
    /**
     * 根据订单号查询一条车辆交互证件信息
     * @param orderId
     * @return
     */
    VehicleCertificates findByOrderId(Long orderId);
}
