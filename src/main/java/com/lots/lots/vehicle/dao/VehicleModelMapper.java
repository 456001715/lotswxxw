package com.lots.lots.vehicle.dao;

import com.lots.lots.admin.vehicle.dto.po.RentList;
import com.lots.lots.admin.vehicle.dto.po.VehicleModel;
import com.lots.lots.admin.vehicle.dto.vo.VehicleModelVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 车型管理 - mapper
 */
public interface VehicleModelMapper {

    /**
     * 车型条件分页查询
     * @param vo
     * @return
     */
    List<VehicleModelVO> queryPage(@Param("vo") VehicleModelVO vo);

    /**
     * 根据车型id查询当前车型下的租金列表
     * @param modelId 车型id
     * @param memberShopId 门店id
     * @return
     */
    RentList findRentListByModelId(@Param("modelId")Long modelId, @Param("memberShopId")Long memberShopId, @io.lettuce.core.dynamic.annotation.Param("planStartTime") LocalDateTime planStartTime, @io.lettuce.core.dynamic.annotation.Param("planEndTime") LocalDateTime planEndTime);

    /**
     *  // 查询当前门店下面的所有车型
     * @param memberShopId
     * @return
     */
    List<VehicleModel> findModelByMemberShop(Long memberShopId);

    /**
     * 根据主键修改
     * @param vehicleModel
     * @return
     */
    int update(VehicleModel vehicleModel);

    /**
     * 根据id查询车型详情
     * @param id
     * @return
     */
    VehicleModelVO findById(Long id);

}
