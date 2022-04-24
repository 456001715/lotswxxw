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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@ApiModel("车辆")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "vehicle", comment = "车辆")
public class Vehicle implements Serializable {

  @Id
  @Min(0L)
  @Column(columnDefinition = "bigint COMMENT '系统标识'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ApiModelProperty(value=" 车型id ",required = true)
  @Column(columnDefinition = "bigint COMMENT '车型id'")
  private Long vehicleModelId;

  @ApiModelProperty(value=" 车牌号 ",required = true)
  @Column(columnDefinition = "varchar(255) COMMENT '车牌号'")
  @NotNull(message = "车牌号不能未空", groups = {Insert.class, Update.class})
  private String carNumber;

  @ApiModelProperty(value=" 车辆颜色 ",required = true)
  @Column(columnDefinition = "varchar(255) COMMENT '车辆颜色'")
  private String colour;

  @ApiModelProperty(value="车辆状态 1-正常 2-异常",required = true)
  @Column(columnDefinition = "int(1) COMMENT '车辆状态 1-正常 2-异常'")
  private Integer vehicleStatus;

  @ApiModelProperty(value=" 购置日期 ",required = true)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @Column(columnDefinition = "datetime COMMENT '购置日期'")
  private LocalDate purchaseTime;

  @ApiModelProperty(value=" 行驶证正面 ",required = true)
  @Column(columnDefinition = "varchar(255) COMMENT '行驶证正面'")
  private String vehicleDrivingPositiveFiles;

  @ApiModelProperty(value=" 行驶证反面 ",required = true)
  @Column(columnDefinition = "varchar(255) COMMENT '行驶证反面'")
  private String vehicleDrivingNegativeFiles;

  @ApiModelProperty(value=" 号牌号码 ",required = true)
  @Column(columnDefinition = "varchar(255) COMMENT '号牌号码'")
  private String plateNumber;

  @ApiModelProperty(value=" 行驶证品牌型号 ",required = true)
  @Column(columnDefinition = "varchar(255) COMMENT '行驶证品牌型号'")
  private String brandModel;

  @ApiModelProperty(value=" 车辆识别代号 ",required = true)
  @Column(columnDefinition = "varchar(255) COMMENT '车辆识别代号'")
  private String discernCode;

  @ApiModelProperty(value=" 发动机号码 ",required = true)
  @Column(columnDefinition = "varchar(255) COMMENT '发动机号码'")
  private String engineNum;

  @ApiModelProperty(value=" 发证日期 ",required = true)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @Column(columnDefinition = "datetime COMMENT '发证日期'")
  private LocalDate issueDate;

  @ApiModelProperty(value=" 注册日期 ",required = true)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @Column(columnDefinition = "datetime COMMENT '注册日期'")
  private LocalDate registeredDate;

  @ApiModelProperty(value=" 交强险单号 ",required = true)
  @Column(columnDefinition = "varchar(255) COMMENT '交强险单号'")
  private String insuranceSn;

  @ApiModelProperty(value=" 交强险续保日期 ",required = true)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @Column(columnDefinition = "datetime COMMENT '交强险续保日期'")
  private LocalDate insuranceTime;

  @ApiModelProperty(value=" 交强险图片 ",required = true)
  @Column(columnDefinition = "text COMMENT '交强险图片'")
  private String insuranceFile;

  @ApiModelProperty(value=" 商业险单号 ",required = true)
  @Column(columnDefinition = "varchar(255) COMMENT '商业险单号'")
  private String businessSn;

  @ApiModelProperty(value=" 商业险续保日期 ",required = true)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @Column(columnDefinition = "datetime COMMENT '商业险续保日期'")
  private LocalDate businessDate;

  @ApiModelProperty(value=" 商业险图片 ",required = true)
  @Column(columnDefinition = "text COMMENT '商业险图片'")
  private String businessFile;

  @ApiModelProperty(value=" 保险证图片 ",required = true)
  @Column(columnDefinition = "text COMMENT '商业险图片'")
  private String insuranceUrls;

  @ApiModelProperty(value=" 是否删除 默认0=否,1=是 ")
  @Column(columnDefinition = "int(1) COMMENT '是否删除 默认0=否,1=是'")
  private Integer isDelete;

  @ApiModelProperty(value=" 当前里程 ")
  @Column(columnDefinition = "varchar(255) COMMENT '当前里程'")
  private String mileage;
  
  @ApiModelProperty(value=" 当前油量 例如1.5就存1.5 ")
  @Column(columnDefinition = "decimal(10,2) COMMENT '当前油量'")
  private BigDecimal oil;
  
  @ApiModelProperty(value=" 油耗 ")
  @Column(columnDefinition = "varchar(255) COMMENT '油耗如：平均6L/100KM'")
  private String oilLoss;

  @ApiModelProperty(value=" 添加时间 ")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @Column(columnDefinition = "datetime COMMENT '添加时间'")
  private LocalDateTime createTime;

  @ApiModelProperty(value=" 门店id")
  @Column(columnDefinition = "bigint(255) COMMENT '门店id'")
  private Long memberShopId;

  @ApiModelProperty(" 是否开启飞猪平台 0-关闭 1-开启 ")
  @Column(columnDefinition = "int COMMENT ' 是否开启飞猪平台 0-关闭 1-开启 '")
  private Integer feizhuOpen;

  @ApiModelProperty(" 是否开启携程平台 0-关闭 1-开启 ")
  @Column(columnDefinition = "int COMMENT ' 是否开启携程平台 0-关闭 1-开启 '")
  private Integer xiechengOpen;

  @ApiModelProperty(" 是否开启去哪儿平台 0-关闭 1-开启 ")
  @Column(columnDefinition = "int COMMENT ' 是否开启去哪儿平台 0-关闭 1-开启 '")
  private Integer qunaerOpen;
}
