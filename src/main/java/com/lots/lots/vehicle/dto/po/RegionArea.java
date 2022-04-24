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
import java.util.List;


@Entity
@ApiModel("地区设置(RegionArea)表实体类")
@Table(name = "region_area")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "region_area", comment = "地区设置(RegionArea)表实体类")
public class RegionArea implements Serializable {
  @Id
  @Min(0L)
  @Column(columnDefinition = "bigint COMMENT '自增列'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ApiModelProperty(value = "区代码", position = 1)
  @Column(columnDefinition = "varchar(255) COMMENT '区代码'")
  private String areaCode;
  
  @ApiModelProperty(value = "父级市代码", position = 1)
  @Column(columnDefinition = "varchar(255) COMMENT '父级市代码'")
  private String cityCode;
  
  @ApiModelProperty(value = "市名称", position = 1)
  @Column(columnDefinition = "varchar(255) COMMENT '市名称'")
  private String name;
  
  @ApiModelProperty(value = "简称", position = 1)
  @Column(columnDefinition = "varchar(255) COMMENT '简称'")
  private String shortName;
  
  @ApiModelProperty(value = "经度", position = 1)
  @Column(columnDefinition = "varchar(255) COMMENT '经度'")
  private String lng;
  
  @ApiModelProperty(value = "纬度", position = 1)
  @Column(columnDefinition = "varchar(255) COMMENT '纬度'")
  private String lat;
  
  @ApiModelProperty(value = "排序", position = 1)
  @Column(columnDefinition = "int(255) COMMENT '排序'")
  private Integer sort;
  
  @Column(columnDefinition = "datetime COMMENT '创建时间'")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime createTime;
  
  @Column(columnDefinition = "datetime COMMENT '修改时间'")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime updateTime;
  
  @ApiModelProperty(value = "备注", position = 1)
  @Column(columnDefinition = "varchar(255) COMMENT '纬度'")
  private String memo;
  
  @ApiModelProperty(value = "状态", position = 1)
  @Column(columnDefinition = "int(11) COMMENT '状态'")
  private Integer dataState;
  
  @ApiModelProperty(value = "租户ID", position = 1)
  @Column(columnDefinition = "varchar(255) COMMENT '租户ID'")
  private String tenantCode;
  
  @ApiModelProperty("送车点")
  @Transient
  private List<Delivery> deliveryList;
}
