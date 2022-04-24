package com.lots.lots.vehicle.repository;

import com.lots.lots.admin.vehicle.dto.po.VehicleGoodsCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 车辆检验 - repository
 */
@Repository
public interface VehicleGoodsCheckRepository extends JpaRepository<VehicleGoodsCheck,Long> {
    /**
     * 根据订单id 车辆id 查询当前车辆是否检验过
     * @return
     */
    List<VehicleGoodsCheck> findByOrderIdAndVehicleId(Long orderId,Long vehicleId);

    /**
     * 根据订单id 车辆id 物件id查询一条检验信息
     * @param orderId
     * @param vehicleId
     * @param goodsId
     * @return
     */
    VehicleGoodsCheck findByOrderIdAndVehicleIdAndGoodsId(Long orderId, Long vehicleId, Long goodsId);
}
