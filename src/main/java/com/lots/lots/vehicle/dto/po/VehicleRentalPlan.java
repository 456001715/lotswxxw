package com.lots.lots.vehicle.dto.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@ApiModel("车辆租赁计划")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "vehicle_rental_plan", comment = "车辆租赁计划")
public class VehicleRentalPlan implements Serializable {

    @Id
    @Min(0L)
    @Column(columnDefinition = "bigint COMMENT '系统标识'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(" 操作人id ")
    @Column(columnDefinition = "bigint COMMENT '操作人id'")
    private Long userId;

    @ApiModelProperty(" 车辆id ")
    @Column(columnDefinition = "bigint COMMENT '车辆id'")
    private Long vehicleId;

    @ApiModelProperty("租赁状态 1-已预定 2-租赁中 3-预留 4-客户使用 5-证件过期")
    @Column(columnDefinition = "int(1) COMMENT '租赁状态 1-已预定 2-租赁中 3-预留 4-客户使用 5-证件过期'")
    private Integer planStatus;

    @ApiModelProperty(" 计划开始时间 ")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(columnDefinition = "datetime COMMENT '计划开始时间'")
    private LocalDateTime planStartTime;

    @ApiModelProperty(" 计划结束时间 ")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(columnDefinition = "datetime COMMENT '计划结束时间'")
    private LocalDateTime planEndTime;

    @ApiModelProperty(" 门店id")
    @Column(columnDefinition = "bigint(255) COMMENT '门店id'")
    private Long memberShopId;

    @ApiModelProperty(" 是否删除0=否,1=是 ")
    @Column(columnDefinition = "int(1) COMMENT '是否删除0=否,1=是'")
    private Integer isDelete;

    @ApiModelProperty(" 添加时间 ")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(columnDefinition = "datetime COMMENT '添加时间'")
    private LocalDateTime createTime;

    @ApiModelProperty(" 租金 ")
    private BigDecimal money;

    /**
     * 租赁计划开始前两个小时
     */
    @Transient
    private LocalDateTime startTime;

    /**
     * 租赁计划结束后两个小时
     */
    @Transient
    private LocalDateTime endTime;

    public LocalDateTime getStartTime() {
        return this.startTime = this.planStartTime.plusHours(-2);
    }

    public LocalDateTime getEndTime() {
        return this.endTime = this.planEndTime.plusHours(2);
    }
}
