package com.lots.lots.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 轮播图DTO
 *
 * @Author liujh
 * @date 2020-11-24 16:38:32
 */
@Data
public class IndexImgDTO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long imgId;

    @ApiModelProperty("店铺ID")
    private Long shopId;

//    @NotNull(message = "图片不能为空")
    @ApiModelProperty("图片")
    private String imgUrl;

    @ApiModelProperty("状态")
    private Integer status;

    @NotNull(message = "序号不能为空")
    @ApiModelProperty("顺序")
    private Integer seq;

    @ApiModelProperty("关联商品id")
    private Long spuId;

//	@NotNull(message = "图片类型不能为空")
    @ApiModelProperty("图片类型 0:小程序 1:pc")
    private Integer imgType;

}
