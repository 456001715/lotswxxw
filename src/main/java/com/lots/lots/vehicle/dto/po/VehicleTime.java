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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@ApiModel("车辆下单预约表")
@Table(name = "vehicle_time")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "vehicle_time", comment = "车辆下单预约表")
public class VehicleTime implements Serializable {
  @Id
  @Min(0L)
  @Column(columnDefinition = "bigint COMMENT '系统标识'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ApiModelProperty("租车开始时间")
  @Column(columnDefinition = "datetime COMMENT '租车开始时间'")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime rentBeginTime;
  
  @ApiModelProperty("车辆状态")
  @Column(columnDefinition = "int(1) COMMENT '车辆状态1可用2下单中(下单未支付锁定车辆)3已预订(客户已支付)4在租中5内部用车6预留中,7=挪车'")
  private Integer vehicleStatus;
  
  @ApiModelProperty("租车结束时间")
  @Column(columnDefinition = "datetime COMMENT '租车结束时间'")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime rentEndTime;
  
  @ApiModelProperty("车辆Id")
  @Column(columnDefinition = "bigint COMMENT '车辆Id'")
  private Long vehicleId;
  
  @ApiModelProperty("订单Id")
  @Column(columnDefinition = "bigint COMMENT '订单Id'")
  private Long orderId;
  
  @ApiModelProperty("车型Id")
  @Column(columnDefinition = "bigint COMMENT '车型Id'")
  private Long vehicleModelId;
  
  @Min(0L)
  @Max(1L)
  @Column(columnDefinition = "int(1) COMMENT '是否处理0=否,1=是'")
  private Integer isHandle;
  
  @Min(0L)
  @Max(1L)
  @Column(columnDefinition = "int(1) COMMENT '是否删除0=否,1=是'")
  private Integer isDelete;
  
  @Column(columnDefinition = "datetime COMMENT '创建时间'")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime createTime;
  
  @Column(columnDefinition = "datetime COMMENT '修改时间'")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime updateTime;
  
  @Column(columnDefinition = "datetime COMMENT '删除时间'")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime deleteTime;
  
  @ApiModelProperty("剩余多少天小时分")
  @Transient
  private String surplusTime;
}
