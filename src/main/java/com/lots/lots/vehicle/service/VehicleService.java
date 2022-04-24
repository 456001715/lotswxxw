package com.lots.lots.vehicle.service;

import com.lots.lots.admin.vehicle.dto.po.Vehicle;
import com.lots.lots.admin.vehicle.dto.po.VehicleRentalPlan;
import com.lots.lots.admin.vehicle.dto.vo.OTAVehicleVO;
import com.lots.lots.admin.vehicle.dto.vo.VehicleVO;
import com.lots.lots.common.entity.JsonResult;

public interface VehicleService {

    /**
     * 添加车辆信息
     * @param vo
     * @return
     */
    JsonResult insert(Vehicle vo);

    /**
     * 车辆首页条件分页查询
     * @param vo
     * @return
     */
    JsonResult pageQuery(VehicleVO vo);

    /**
     * 车辆信息修改
     * @param vehicle
     * @return
     */
    JsonResult update(Vehicle vehicle);

    /**
     * 根据车辆id查询详情
     * @param id
     * @return
     */
    JsonResult selectOne(Long id);

    /**
     * 修改车辆状态
     * @param id
     * @param vehicleStatus  1-正常 2-异常
     * @return
     */
    JsonResult<VehicleVO> changeStatus(Long id, Integer vehicleStatus);

    /**
     * 车辆租赁计划条件分页查询
     * @param vo
     * @return
     */
    JsonResult pageLeaseQuery(VehicleVO vo);

    /**
     * 车辆租赁计划预留
     * @param vehicleRentalPlan
     * @return
     */
    JsonResult insertVehicleRentalPlan(VehicleRentalPlan vehicleRentalPlan);

    /**
     * 根据车辆id 门店id 查询车辆租赁计划列表
     * @param vehicleId
     * @return
     */
    JsonResult selectRentalPlanListByVehicleId(Long vehicleId);

    /**
     * 门店的所有车辆
     * @return
     */
    JsonResult queryAllByCondition(String searchField,Integer status,Long categoryId,Long brandId,Integer page,Integer size);

    /**
     * OTA 查询车辆列表
     * @param vo
     * @return
     */
    JsonResult otaPageQuery(OTAVehicleVO vo);

    /**
     * 车辆是否开启平台租赁
     * @param vehicleId
     * @param open
     * @return
     */
    JsonResult vehicleOTAOpen(Long vehicleId, Integer platform, Integer open);
}
