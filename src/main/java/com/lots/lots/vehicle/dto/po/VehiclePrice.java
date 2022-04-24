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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@ApiModel("车型动态价格表")
@Table(name = "vehicle_price")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "vehicle_price", comment = "车型动态价格表")
public class VehiclePrice {
  @Id
  @Min(0L)
  @Column(columnDefinition = "bigint COMMENT '系统标识'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(columnDefinition = "bigint COMMENT '车型系统标识'")
  @ApiModelProperty(" 车型")
  private Long vehicleModelId;
  
  @ApiModelProperty("日期")
  @Column(columnDefinition = "date COMMENT '日期'")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private LocalDate priceTime;
  
  @ApiModelProperty("价格")
  @Column(columnDefinition = "decimal(10,2) COMMENT '价格'")
  private BigDecimal price;
  
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
  
  @ApiModelProperty("当前日")
  @Transient
  private Integer daytime;
  
  @ApiModelProperty("当前周")
  @Transient
  private String weekTime;
  
  @ApiModelProperty("农历")
  @Transient
  private String lunarTime;
  
  @ApiModelProperty("优惠价格")
  @Column(columnDefinition = "decimal(10,2) COMMENT '优惠价格'")
  private BigDecimal discountsPrice;
  
  @ApiModelProperty("租赁计划状态")
  private Integer leaseScheduleStatus;
  
  @ApiModelProperty("计划")
  @Transient
  private List<VehicleTime> vehicleTimes;
}
