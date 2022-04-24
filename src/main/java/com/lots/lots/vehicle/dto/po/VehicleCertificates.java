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
import java.time.LocalDate;

@Entity
@ApiModel("车辆订单证件表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "vehicle_certificates", comment = "车辆订单证件表")
public class VehicleCertificates implements Serializable {

  @Id
  @Min(0L)
  @Column(columnDefinition = "bigint COMMENT '系统标识'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ApiModelProperty("订单id")
  @Column(columnDefinition = "bigint COMMENT '订单id'")
  private Long orderId;
  
  @ApiModelProperty("车辆id")
  @Column(columnDefinition = "bigint COMMENT '车辆id'")
  private Long vehicleId;
  
  @ApiModelProperty("身份证正面")
  @Column(columnDefinition = "varchar(255) COMMENT '身份证正面'")
  private String idCardPositive;
  
  @ApiModelProperty("身份证背面")
  @Column(columnDefinition = "varchar(255) COMMENT '身份证背面'")
  private String idCardBack;
  
  @ApiModelProperty("驾照正页面")
  @Column(columnDefinition = "varchar(255) COMMENT '驾照正页面'")
  private String drivingPositive;
  
  @ApiModelProperty("驾照副页面")
  @Column(columnDefinition = "varchar(255) COMMENT '驾照副页面'")
  private String drivingBack;
  
  @ApiModelProperty("备注")
  @Column(columnDefinition = "varchar(500) COMMENT '备注'")
  private String remarks;
  
  @ApiModelProperty("租赁合同")
  @Column(columnDefinition = "varchar(500) COMMENT '租赁合同'")
  private String contract;

  @ApiModelProperty("操作日期")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @Column(columnDefinition = "date COMMENT '操作日期'")
  private LocalDate businessDate;
  
  @Min(0L)
  @Max(1L)
  @Column(columnDefinition = "int(1) COMMENT '是否删除0=否,1=是'")
  private Integer isDelete;
}
