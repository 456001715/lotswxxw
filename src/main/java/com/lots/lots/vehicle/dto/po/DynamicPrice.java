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
import java.time.LocalDateTime;


@Entity
@ApiModel("动态价格")
@Table(name = "dynamic_price")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "dynamic_price", comment = "动态价格")
public class DynamicPrice {
  @Id
  @Min(0L)
  @Column(columnDefinition = "bigint COMMENT '系统标识'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(columnDefinition = "bigint COMMENT '车型id'")
  private Long vehicleModelId;
  
  @Column(columnDefinition = "int(10) COMMENT '最低车辆数量'")
  private Integer lowNumber;
  
  @Column(columnDefinition = "int(10) COMMENT '最高车辆数量'")
  private Integer highNumber;
  
  @ApiModelProperty("低于最低车辆数量的价格")
  @Column(columnDefinition = "decimal(10,2) COMMENT '低于最低车辆数量的价格'")
  private BigDecimal lowPrice;
  
  @ApiModelProperty("高于最高车辆数量的价格")
  @Column(columnDefinition = "decimal(10,2) COMMENT '高于最高车辆数量的价格'")
  private BigDecimal highPrice;
  
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
}
