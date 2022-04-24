package com.lots.lots.vehicle.dao;

import com.lots.lots.admin.vehicle.dto.po.VehicleRentalPlan;
import io.lettuce.core.dynamic.annotation.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 车辆租赁计划  mapper
 */
public interface VehicleRentalPlanMapper {
    /**
     * 根据车辆id 查询车辆租赁计划
     * @param vo    车辆租赁对象
     * @return
     */
    List<VehicleRentalPlan> findPlanListByVehicleId(VehicleRentalPlan vo);

    /**
     * 根据车辆id  时间段查询当前车辆租赁计划
     * @param vehicleId                 车辆id
     * @param planStartTime      开始时间
     * @param planEndTime        结束时间
     * @param status             状态
     * @return
     */
    VehicleRentalPlan findPlanByNowDayTime(@Param("vehicleId")Long vehicleId, @Param("planStartTime")LocalDateTime planStartTime,@Param("planEndTime") LocalDateTime planEndTime,@Param("status") int status);

    /**
     * 根据车辆id and 当前时间查询车辆的租赁状态
     * @param vehicleId 车辆id
     * @param time 最新时间
     * @return
     */
    VehicleRentalPlan findNewPlanByTime(@Param("vehicleId")Long vehicleId, @Param("time")LocalDateTime time);

    /**
     * 验证车辆在当前时间段是否存在租赁计划
     * @param vehicleRentalPlan
     * @return
     */
    int verificationVehiclePlan(VehicleRentalPlan vehicleRentalPlan);
}
