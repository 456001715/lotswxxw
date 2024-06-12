package com.lots.lots.vehicle.dto.vo;

import com.lots.lots.admin.vehicle.dto.po.RentList;
import com.lots.lots.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 车型 - VO类
 */
@ApiModel("车型vo表")
@Data
public class VehicleModelVO extends BaseEntity implements Serializable {

  @Min(0L)
  @ApiModelProperty(" 系统标识 ")
  private Long id;

  @ApiModelProperty(" 品牌id ")
  private Long brandId;

  @ApiModelProperty(" 品牌名称 ")
  private String brandName;

  @ApiModelProperty(" 类别 ")
  private Long categoryId;

  @ApiModelProperty(" 类别名称 ")
  private String categoryName;

  @ApiModelProperty(" 座位数 ")
  private Integer capacity;

  @ApiModelProperty(" 车型名称 ")
  private String name;

  @ApiModelProperty("排挡")
  private String Gears;

  @ApiModelProperty(" 排量id ")
  private Long outputVolumeId;

  @ApiModelProperty(" 排量名称 ")
  private String outputVolumeName;
  
  @ApiModelProperty(" 燃油型号 ")
  private String model;

  @ApiModelProperty("标签")
  private String labels;

  @ApiModelProperty("车型图片")
  private String vehicleModelFiles;

  @ApiModelProperty("实拍图片")
  private String realPicturesFiles;

  @ApiModelProperty("租车押金")
  private BigDecimal rentalMoney;

  @ApiModelProperty("基本保障费")
  private BigDecimal protectionMoney;

  @ApiModelProperty("违章押金")
  private BigDecimal breakRulesMoney;

  @ApiModelProperty("周内价格")
  private BigDecimal weekWithin;

  @ApiModelProperty("周末价格")
  private BigDecimal weekExternal;

  @ApiModelProperty("当前车型-车辆数量")
  private int vehicleNumber;

  @ApiModelProperty("租金列表")
  private List<RentList> rentLists;

  @ApiModelProperty("门店id")
  private Long memberShopId;
}
