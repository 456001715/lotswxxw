package com.lots.lots.vehicle.dao;

import com.lots.lots.admin.vehicle.dto.po.VehicleCategory;

import java.util.List;

/**
 * 车辆类别 mapper
 */
public interface VehicleCategoryMapper {

    /**
     * 根据id查询车辆类别信息
     * @param categoryId
     * @return
     */
    VehicleCategory findOneById(Long categoryId);

    /**
     * 查询所有的车辆类别
     * @return
     */
    List<VehicleCategory> findAll();

    /**
     * 根据门店id查询车辆类别
     * @param shopId
     * @return
     */
    List<VehicleCategory> findCategoryListByMemberShopId(Long shopId);
}
