package com.lots.lots.vehicle.dto.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("车型标签数据返回类")
@Data
public class VehicleModelLabelDTO implements Serializable {
  private static final long serialVersionUID = 352544042988947876L;
  
  @ApiModelProperty(" 系统标识 ")
  private Long id;
  
  @ApiModelProperty(" 名称 ")
  private String name;
  
  @ApiModelProperty(" 系统标识 ")
  private Long modelId;
}
