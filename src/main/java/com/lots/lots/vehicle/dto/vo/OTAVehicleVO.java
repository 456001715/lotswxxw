package com.lots.lots.vehicle.dto.vo;

import com.lots.lots.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("OTA-车辆vo表")
@Data
public class OTAVehicleVO extends BaseEntity implements Serializable {

    @ApiModelProperty(" 车辆id ")
    private Long id;

    @ApiModelProperty(" 车牌号 ")
    private String carNumber;

    @ApiModelProperty(" 搜索字段 ")
    private String searchField;

    @ApiModelProperty(" 车辆类别id ")
    private Long categoryId;

    @ApiModelProperty(" 车辆品牌id ")
    private Long brandId;

    @ApiModelProperty(" 车型名称 ")
    private String modelName;

    @ApiModelProperty(" 车辆品牌名称 ")
    private String brandName;

    @ApiModelProperty("查询车辆状态 全部-不传 1-正常 2-异常 3-租赁中 4-预留中")
    private Integer status;

    @ApiModelProperty(" 门店id ")
    private Long memberShopId;

    @ApiModelProperty(" 车主user_id ")
    private Long carOwnerId;

    @ApiModelProperty(" 飞猪平台是否开启 0-关闭 1-开启 ")
    private Integer feizhuOpen;

    @ApiModelProperty(" 携程平台是否开启 0-关闭 1-开启  ")
    private Integer xiechengOpen;

    @ApiModelProperty(" 去哪儿平台是否开启 0-关闭 1-开启  ")
    private Integer qunaerOpen;
}
