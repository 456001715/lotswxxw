package com.lots.lots.vehicle.dto.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lots.lots.admin.shop.dto.po.MemberShop;
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
import java.util.List;

@Entity
@ApiModel("送车点表")
@Table(name = "delivery")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "delivery", comment = "送车点表")
public class Delivery {
  @Id
  @Min(0L)
  @Column(columnDefinition = "bigint COMMENT '系统标识'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ApiModelProperty("送车点名称")
  @Column(columnDefinition = "varchar(255) COMMENT '送车点名称'")
  private String name;
  
  @ApiModelProperty("详细地址")
  @Column(columnDefinition = "varchar(255) COMMENT '详细地址'")
  private String address;
  
  @ApiModelProperty("经度")
  @Column(columnDefinition = "varchar(255) COMMENT '经度'")
  private String lon;
  
  @ApiModelProperty("纬度")
  @Column(columnDefinition = "varchar(255) COMMENT '纬度'")
  private String lat;

  @ApiModelProperty("所属区域")
  @Column(columnDefinition = "varchar(255) COMMENT '所属区域'")
  private String areaCode;

  @ApiModelProperty("所属城市")
  @Column(columnDefinition = "varchar(255) COMMENT '所属城市'")
  private String cityCode;

  @ApiModelProperty("所属城市名称")
  @Column(columnDefinition = "varchar(255) COMMENT '所属城市名称'")
  private String cityName;

  @ApiModelProperty("所属区域名称")
  @Column(columnDefinition = "varchar(255) COMMENT '所属区域名称'")
  private String areaName;

  @ApiModelProperty("所属省份编码")
  @Column(columnDefinition = "varchar(255) COMMENT '所属省份编码'")
  private String provinceCode;

  @ApiModelProperty("所属省份名称")
  @Column(columnDefinition = "varchar(255) COMMENT '所属省份名称'")
  private String provinceName;
  
  @ApiModelProperty("手续费")
  @Column(columnDefinition = "decimal(10,2) COMMENT '手续费'")
  private BigDecimal commission;
  
  @ApiModelProperty("状态  0=关闭 1=开启")
  @Column(columnDefinition = "int(2) COMMENT '状态  0=关闭 1=开启'")
  private Integer status;
  
  @Min(0L)
  @Max(1L)
  @Column(columnDefinition = "int(1) COMMENT '是否删除0=否,1=是'")
  private Integer isDelete;
  
  @Min(0L)
  @Max(1L)
  @Column(columnDefinition = "int(1) COMMENT '是否属于交通枢纽0=否,1=是'")
  private Integer isTraffic;
  
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
  
  @Transient
  @ApiModelProperty("距离:单位米")
  private Double distance;
  
  @Transient
  @ApiModelProperty("是否可以添加")
  private Integer isAdd;
  

  @Transient
  @ApiModelProperty("门店信息")
  private List<MemberShop> memberShops;
  
  @Transient
  @ApiModelProperty("门店id")
  private Long memberShopId;
}
