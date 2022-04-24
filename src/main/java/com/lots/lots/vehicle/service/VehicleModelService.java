package com.lots.lots.vehicle.service;

import com.lots.lots.admin.vehicle.dto.po.RentList;
import com.lots.lots.admin.vehicle.dto.po.VehicleModel;
import com.lots.lots.admin.vehicle.dto.vo.VehicleModelVO;
import com.lots.lots.common.entity.JsonResult;


public interface VehicleModelService {

    /**
     * 车型新增
     * @param vehicleModel
     * @return
     */
    JsonResult insertModel(VehicleModel vehicleModel);

    /**
     * 车型修改
     * @param vehicleModel
     * @return
     */
    JsonResult updateModel(VehicleModel vehicleModel);

    /**
     * 车型条件分页查询
     * @param vo
     * @return
     */
    JsonResult pageQuery(VehicleModelVO vo);

    /**
     * 根据id查询车型详情
     * @param id
     * @return
     */
    JsonResult findOneById(Long id);

    /**
     * 车型租金修改
     * @param rentList
     * @return
     */
    JsonResult updateRentList(RentList rentList);

    /**
     * 查询所有的车型列表
     * @return
     */
    JsonResult queryAll();

    /**
     * 第三方 - 查询所有的车型列表
     * @return
     */
    JsonResult findAll();
}
