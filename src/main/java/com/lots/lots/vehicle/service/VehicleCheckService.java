package com.lots.lots.vehicle.service;

import com.lots.lots.admin.order.dto.po.RentalOrder;
import com.lots.lots.admin.order.dto.po.RentalOrderDelivery;
import com.lots.lots.admin.vehicle.dto.po.VehicleCertificates;
import com.lots.lots.admin.vehicle.dto.po.VehicleOrderRemarks;
import com.lots.lots.common.entity.JsonResult;
import com.lots.lots.common.entity.vehicle.VehicleReturnCheckVo;

public interface VehicleCheckService {

    /**
     * 查询车辆物件列表
     * @return
     */
    JsonResult queryVehicleGoods(Long vehicleId, Long orderId);

    /**
     * 员工端出车检验
     * @param  vehicleReturnCheckVo
     * @return
     */
    JsonResult staffVehicleCheck(VehicleReturnCheckVo vehicleReturnCheckVo);

    /**
     * 记录检验备注信息
     * @param vehicleOrderRemarks
     * @return
     */
    JsonResult addCheckRemarks(VehicleOrderRemarks vehicleOrderRemarks);

    /**
     * 出车检验-关联我的订单管理
     * @param rentalOrderDelivery
     * @return
     */
    JsonResult addOrderDelivery(RentalOrderDelivery rentalOrderDelivery);

    /**
     * 修改车辆油量和里程
     * @param rentalOrder
     * @return
     */
    JsonResult changeOilOrMileage(RentalOrder rentalOrder);

    /**
     * 交互车辆
     * @param vehicleCertificates
     * @return
     */
    JsonResult deliver(VehicleCertificates vehicleCertificates);
}
