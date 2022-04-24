package com.lots.lots.vehicle.dao;

import com.lots.lots.admin.vehicle.dto.po.RentList;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 车型租金列表 - mapper
 */
public interface RentListMapper {

    /**
     * 新增车型租金
     *
     * @param rentList
     * @return
     */
    int insertRentList(@Param("rentList") RentList rentList);

    /**
     * 修改车型租金
     *
     * @param rentList
     * @return
     */
    int updateRentList(RentList rentList);

    /**
     * 根据时间和车型id查询是否存在数据
     *
     * @param dayStartTime 查询时间当天的凌晨时间
     * @param dayEndTime   查询时间当天晚上的凌晨时间
     * @param modelId      车型id
     * @param memberShopId 门店id
     * @param modelId
     * @return
     */
    RentList findByRentTimeAndModelId(@Param("dayStartTime") LocalDateTime dayStartTime, @Param("dayEndTime") LocalDateTime dayEndTime, @Param("modelId") Long modelId, @Param("memberShopId") Long memberShopId);

    /**
     * 根据时间、车型、门店id查询租金
     */
    RentList findByRentalTimeAndMemberShopIdAndModelId(@Param("localDateTime") LocalDateTime localDateTime, @Param("memberShopId") Long memberShopId, @Param("modelId") Long modelId);

    /**
     * 根据时间、车型、门店id查询租金
     */
    List<RentList> findAllByRentalTimeAndMemberShopIdAndModelId(@Param("starTime") LocalDateTime starTime, @Param("endTime") LocalDateTime endTime, @Param("memberShopId") Long memberShopId, @Param("modelId") Long modelId);
}
