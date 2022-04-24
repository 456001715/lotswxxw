package com.lots.lots.vehicle.dto.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * @name: 车主关联车辆表
 * @author: ld
 * @date: 2021/6/8 11:36
 */
@Entity
@ApiModel("车主关联车辆表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "vehicle_car_owner", comment = "车主关联车辆表")
public class VehicleCarOwner implements java.io.Serializable {

    @Id
    @Min(0L)
    @Column(columnDefinition = "bigint COMMENT '系统标识'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 车主user_id
     */
    @ApiModelProperty(value = "车主user_id")
    @Column(columnDefinition = "bigint COMMENT '车主user_id'")
    private Long userId;

    /**
     * 车辆vehicle_id
     */
    @ApiModelProperty(value = "车辆vehicle_id")
    @Column(columnDefinition = "bigint COMMENT '车辆vehicle_id'")
    private Long vehicleId;

    /**
     * 店铺member_shop_id
     */
    @ApiModelProperty(value = "店铺member_shop_id")
    @Column(columnDefinition = "bigint COMMENT '店铺member_shop_id'")
    private Long memberShopId;
}
