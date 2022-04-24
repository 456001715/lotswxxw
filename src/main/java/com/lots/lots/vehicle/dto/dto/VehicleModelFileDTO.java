package com.lots.lots.vehicle.dto.dto;

import com.lots.lots.common.enums.vehicle.VehicleModelTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("车型图片数据返回类")
@Data
public class VehicleModelFileDTO implements Serializable {
  @ApiModelProperty(" 系统标识 ")
  private Long id;
  
  @ApiModelProperty(" 是否封面0=否,1=是 ")
  private Integer isFrontCover;
  
  @ApiModelProperty(" 分类 ")
  private VehicleModelTypeEnum typeEnum;
  
  @ApiModelProperty("文件系统标识")
  private Long fileManageId;
  
  @ApiModelProperty("文件url")
  private String fileManageUrl;
  
  @ApiModelProperty(" 系统标识 ")
  private Long vehicleModelId;
}
