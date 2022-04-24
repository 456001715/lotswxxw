package com.lots.lots.vehicle.dto.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lots.lots.admin.vehicle.dto.po.VehicleRentalPlan;
import com.lots.lots.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel("车辆vo表")
@Data
public class VehicleVO extends BaseEntity implements Serializable {

  @ApiModelProperty(" 系统标识 ")
  private Long id;

  @ApiModelProperty(" 搜索字段 ")
  private String searchField;

  @ApiModelProperty(" 车型id ")
  private Long vehicleModelId;

  @ApiModelProperty("车型图片")
  private String vehicleModelFiles;

  @ApiModelProperty(" 车型名称 ")
  private String vehicleModelName;

  @ApiModelProperty(" 车辆类别id ")
  private Long categoryId;

  @ApiModelProperty(" 车辆类别名称 ")
  private String categoryName;

  @ApiModelProperty(" 车辆品牌id ")
  private Long brandId;

  @ApiModelProperty(" 车辆品牌名称 ")
  private String brandName;

  @ApiModelProperty(" 排挡 ")
  private String gears;

  @ApiModelProperty(" 车牌号 ")
  private String carNumber;

  @ApiModelProperty(" 车辆颜色 ")
  private String colour;

  @ApiModelProperty("车龄")
  private String carAge;

  @ApiModelProperty("车辆座位数")
  private Integer capacity;

  @ApiModelProperty("排量")
  private String outputVolumeName;

  @ApiModelProperty("燃油型号")
  private String model;

  @ApiModelProperty("标签")
  private String labels;

  @ApiModelProperty("查询车辆状态 全部-不传 1-正常 2-异常 3-租赁中 4-预留中")
  private Integer vehicleStatus;

  @ApiModelProperty("返回租赁状态 1-已预定 2-租赁中 3-预留 4-客户使用 5-证件过期")
  private Integer planStatus;

  @ApiModelProperty(" 购置日期 ")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private LocalDate purchaseTime;

  @ApiModelProperty(value=" 行驶证正面 ")
  private String vehicleDrivingPositiveFiles;

  @ApiModelProperty(value=" 行驶证反面 ")
  private String vehicleDrivingNegativeFiles;

  @ApiModelProperty(" 号牌号码 ")
  private String plateNumber;

  @ApiModelProperty(" 车辆识别代号 ")
  private String discernCode;

  @ApiModelProperty(" 发动机号码 ")
  private String engineNum;

  @ApiModelProperty(value=" 行驶证品牌型号 ",required = true)
  private String brandModel;

  @ApiModelProperty(" 发证日期 ")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private LocalDate issueDate;

  @ApiModelProperty(" 注册日期 ")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private LocalDate registeredDate;

  @ApiModelProperty(" 交强险单号 ")
  private String insuranceSn;

  @ApiModelProperty(" 交强险续保日期 ")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private LocalDate insuranceTime;

  @ApiModelProperty(" 交强险图片 ")
  private String insuranceFile;

  @ApiModelProperty(" 商业险单号 ")
  private String businessSn;

  @ApiModelProperty(" 商业险续保日期 ")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private LocalDate businessDate;

  @ApiModelProperty(" 商业险图片 ")
  private String businessFile;

  @ApiModelProperty(" 保险证图片 ")
  private String insuranceUrls;

  @ApiModelProperty(" 是否删除0=否,1=是 ")
  private Integer isDelete;

  @ApiModelProperty(" 当前里程 ")
  private String mileage;
  
  @ApiModelProperty(" 当前油量 ")
  private BigDecimal oil;
  
  @ApiModelProperty(" 油耗 ")
  private String oilLoss;
  
  @ApiModelProperty("当前所处城市")
  private String cityCode;

  @ApiModelProperty(" 添加时间 ")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime createTime;

  @ApiModelProperty(" 门店id ")
  private Long memberShopId;

  @ApiModelProperty(" 车主user_id ")
  private Long carOwnerId;

  @ApiModelProperty(" 使用人id ")
  private Long customInfoId;

  @ApiModelProperty(" 使用人名称 ")
  private String customerName;

  @ApiModelProperty(" 开始使用时间 ")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime rentBeginTime;

  @ApiModelProperty(" 结束使用时间 ")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime rentEndTime;

  @ApiModelProperty(" 租赁计划查询开始时间 ")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime planBeginTime;

  @ApiModelProperty(" 租赁计划查询结束时间 ")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime planEndTime;

  @ApiModelProperty(" 车辆已租赁计划 ")
  private List<VehicleRentalPlan> rentalPlans;

  @ApiModelProperty(" 车辆预留租金列表")
  private List<VehicleRentalPlan> rentalReservedPlans;
}
