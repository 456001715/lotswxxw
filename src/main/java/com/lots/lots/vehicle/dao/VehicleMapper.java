package com.lots.lots.vehicle.dao;

import com.lots.lots.admin.vehicle.dto.po.RentList;
import com.lots.lots.admin.vehicle.dto.po.Vehicle;
import com.lots.lots.admin.vehicle.dto.vo.OTAVehicleVO;
import com.lots.lots.admin.vehicle.dto.vo.VehicleVO;
import com.lots.lots.common.entity.order.AdminOrderVehicleInfoVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VehicleMapper {

    /**
     * 根据车牌号查询一条车辆信息
     *
     * @param carNumber
     * @return
     */
    Vehicle findVehicleByCarNumber(String carNumber);

    /**
     * 根据车辆id查询一条车辆信息
     *
     * @param vehicleId
     * @return
     */
    Vehicle selectByPrimaryKey(Long vehicleId);


    /**
     * 车辆条件分页查询
     *
     * @param vo
     * @return
     */
    List<VehicleVO> pageQuery(@Param("vehicleVO") VehicleVO vo);

    /**
     * 根据车型id and 门店id查询属于当前车型的车辆数量
     *
     * @param modelId
     * @param memberShopId
     * @return
     */
    int findVehicleNumByModelIdAndMemberShopId(@Param("modelId") Long modelId, @Param("memberShopId") Long memberShopId);

    /**
     * 车辆修改
     * @param vehicle
     * @return
     */
    int update(Vehicle vehicle);

    /**
     * 查询本门店内下的所有车辆信息
     * @param shopId 门店id
     * @param searchField 搜索车牌号或品牌名称
     * @param status 车辆状态
     * @param categoryId 类别id
     * @param brandId 品牌id
     * @return
     */
    List<VehicleVO> queryAllByCondition(@Param("shopId")Long shopId,
                                        @Param("searchField")String searchField,
                                        @Param("status")Integer status,
                                        @Param("categoryId")Long categoryId,
                                        @Param("brandId")Long brandId);

    /**
     * 根据车型id 和 门店id 和 租赁时间查询租金详情
     * @param vehicleModelId
     * @param shopId
     * @param planStartTime
     * @param planEndTime
     * @return
     */
    RentList findRentListByModelId(@Param("vehicleModelId")Long vehicleModelId,
                                   @Param("shopId")Long shopId,
                                   @Param("planStartTime")LocalDateTime planStartTime,
                                   @Param("planEndTime")LocalDateTime planEndTime);

    AdminOrderVehicleInfoVo findInfoById(Long Id);

    /**
     * 条件查询OTA车辆列表
     * @param vo
     * @return
     */
    List<OTAVehicleVO> otaPageQuery(@Param("vo")OTAVehicleVO vo);
}
