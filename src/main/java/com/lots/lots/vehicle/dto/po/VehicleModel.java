package com.lots.lots.vehicle.dto.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lots.lots.common.validation.Insert;
import com.lots.lots.common.validation.Update;
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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@ApiModel("车型")
@Table(name = "vehicle_model")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "vehicle_model", comment = "车型")
public class VehicleModel {

  @Id
  @Min(0L)
  @Column(columnDefinition = "bigint COMMENT '系统标识'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ApiModelProperty(value="车辆品牌id",required = true)
  @Column(columnDefinition = "bigint COMMENT '车辆品牌id'")
  private Long brandId;

  @ApiModelProperty(value="车辆类别id",required = true)
  @Column(columnDefinition = "bigint COMMENT '车辆类别id'")
  private Long categoryId;

  @ApiModelProperty(value="门店id")
  @Column(columnDefinition = "bigint COMMENT '门店id'")
  @NotNull(message = "门店信息不能为空",groups = {Insert.class, Update.class})
  private Long memberShopId;

  @ApiModelProperty(value="座位数",required = true)
  @Column(columnDefinition = "int COMMENT '座位数'")
  private Integer capacity;

  @ApiModelProperty(value="名称",required = true)
  @Column(columnDefinition = "varchar(255) COMMENT '名称'")
  private String name;

  @ApiModelProperty(value="排挡",required = true)
  @Column(columnDefinition = "varchar(255) COMMENT '排挡'")
  private String Gears;

  @ApiModelProperty(value="排量id",required = true)
  @Column(columnDefinition = "bigint COMMENT '排量id'")
  private Long outputVolumeId;

  @ApiModelProperty(value="燃油型号",required = true)
  @Column(columnDefinition = "varchar(255) COMMENT '燃油型号'")
  private String model;

  @ApiModelProperty(value="标签")
  @Column(columnDefinition = "varchar(255) COMMENT '标签'")
  private String labels;

  @ApiModelProperty(value="车型图片",required = true)
  @Column(columnDefinition = "text COMMENT '车型图片'")
  private String vehicleModelFiles;

  @ApiModelProperty(value="实拍图片",required = true)
  @Column(columnDefinition = "text COMMENT '实拍图片'")
  private  String realPicturesFiles;

  @ApiModelProperty(value="租车押金",required = true)
  @Column(columnDefinition = "decimal(10,2) COMMENT '租车押金'")
  private BigDecimal rentalMoney;

  @ApiModelProperty(value="基本保障费",required = true)
  @Column(columnDefinition = "decimal(10,2) COMMENT '基本保障费'")
  private BigDecimal protectionMoney;

  @ApiModelProperty(value="违章押金",required = true)
  @Column(columnDefinition = "decimal(10,2) COMMENT '违章押金'")
  private BigDecimal breakRulesMoney;

  @ApiModelProperty(value="周内价格",required = true)
  @Column(columnDefinition = "decimal(10,2) COMMENT '周内价格'")
  private BigDecimal weekWithin;

  @ApiModelProperty(value="周末价格",required = true)
  @Column(columnDefinition = "decimal(10,2) COMMENT '周末价格'")
  private BigDecimal weekExternal;

  @Min(0L)
  @Max(1L)
  @Column(columnDefinition = "int(1) COMMENT '是否删除0=否,1=是'")
  @ApiModelProperty(value="是否删除0=否,1=是")
  private Integer isDelete;

  @Column(columnDefinition = "datetime COMMENT '创建时间'")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @ApiModelProperty(value="创建时间")
  private LocalDateTime createTime;
}
