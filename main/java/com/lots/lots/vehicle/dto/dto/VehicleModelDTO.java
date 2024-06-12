package com.lots.lots.vehicle.dto.dto;

import com.lots.lots.admin.shop.dto.po.MemberShop;
import com.lots.lots.admin.vehicle.dto.po.DynamicPrice;
import com.lots.lots.admin.vehicle.dto.po.FileManage;
import com.lots.lots.admin.vehicle.dto.po.VehicleModelLabel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApiModel("车型数据返回类")
public class VehicleModelDTO implements Serializable {
  @ApiModelProperty(" 系统标识 ")
  private Long id;
  
  @ApiModelProperty(" 座位数 ")
  private Integer capacity;
  
  @ApiModelProperty(" 燃油型号 ")
  private String model;
  
  @ApiModelProperty(" 所属区域 ")
  private String areaName;
  
  @ApiModelProperty(" 名称 ")
  private String name;
  
  @ApiModelProperty(" 限时优惠状态0未开启1开启")
  private Integer status;
  
  @ApiModelProperty(" 是否允许设置动态0否1是")
  private Integer isSetUp;
  
  @ApiModelProperty(" 品牌系统标识 ")
  private Long brandId;
  
  @ApiModelProperty(" 品牌系统标识 ")
  private String brandName;
  
  @ApiModelProperty(" 排量系统标识 ")
  private Long outputVolumeId;
  
  @ApiModelProperty(" 排量系统标识 ")
  private String outputVolumeName;
  
  @ApiModelProperty(" 变速器系统标识 ")
  private Long transmissionId;
  
  @ApiModelProperty(" 变速器系统标识 ")
  private String transmissionName;
  
  @ApiModelProperty("图片集")
  private String VehicleModelFile;
  
  @ApiModelProperty("标签集")
  private List<VehicleModelLabel> labels;
  
  @ApiModelProperty("车辆优惠价格")
  private BigDecimal vehiclePrice;
  
  @ApiModelProperty("周内价格")
  private BigDecimal weekWithin;
  
  @ApiModelProperty("周末价格")
  private BigDecimal weekExternal;
  
  @ApiModelProperty("车辆类别")
  private String vehicleCategoryName;
  
  @ApiModelProperty("车辆数量")
  private Integer vehicleNum;
  
  @ApiModelProperty("门店id")
  private String memberShopName;
  
  @ApiModelProperty("门店id")
  private Long memberShopId;
  
  @ApiModelProperty("是否发布0否1是")
  private Integer isOnline;
  
  @Transient
  @ApiModelProperty("汽车封面图")
  private String vehicleImage;
  
  @Transient
  @ApiModelProperty("汽车封面图")
  private FileManage veImage;
  
  @Transient
  @ApiModelProperty("汽车封面图")
  private MemberShop memberShop;
  
  @ApiModelProperty("当前所处城市")
  private String cityCode;
  
  @Transient
  @ApiModelProperty("动态价格")
  private DynamicPrice dynamicPrice;
  
}
