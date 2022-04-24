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
import java.time.LocalDateTime;


@Entity
@ApiModel("还车信息")
@Table(name = "change_car")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "change_car", comment = "还车信息")
public class ChangeCar implements Serializable {
  @Id
  @Min(0L)
  @Column(columnDefinition = "bigint COMMENT '系统标识'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ApiModelProperty("订单Id")
  @Column(columnDefinition = "bigint(20) COMMENT '订单Id'")
  private Long orderId;
  
  @ApiModelProperty("车辆Id")
  @Column(columnDefinition = "bigint COMMENT '车辆Id'")
  private Long vehicleOldId;
  
  @ApiModelProperty("车牌号")
  @Column(columnDefinition = "varchar(255) COMMENT '车牌号'")
  private String vehicleNumber;
  
  @ApiModelProperty("关联id-换车id,如id未空则是换车的车辆信息")
  @Column(columnDefinition = "bigint COMMENT '关联id-换车id'")
  private Long changeId;
  
  @Column(columnDefinition = "datetime COMMENT '车辆使用开始时间'")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime beginTime;
  
  @Column(columnDefinition = "datetime COMMENT '车辆使用结束时间'")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime endTime;
  
  @Column(columnDefinition = "datetime COMMENT '创建时间'")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime createTime;
  
  @ApiModelProperty("车辆信息")
  @Transient
  private Vehicle vehicle;
  
  @ApiModelProperty("换车信息")
  @Transient
  private ChangeCar changeCar;
}
