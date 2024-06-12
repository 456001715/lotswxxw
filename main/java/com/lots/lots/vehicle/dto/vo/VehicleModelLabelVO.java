package com.lots.lots.vehicle.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("车型标签接收")
@Data
public class VehicleModelLabelVO implements Serializable {

  @ApiModelProperty(" 系统标识 ")
  private Long id;
  
  @ApiModelProperty(" 名称 ")
  private String name;
  
  @ApiModelProperty(" 车型id ")
  private Long modelId;
}
