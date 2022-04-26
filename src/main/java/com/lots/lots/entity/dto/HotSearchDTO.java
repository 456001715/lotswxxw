package com.lots.lots.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 热搜DTO
 *
 * @Author liujh
 * @date 2021-01-27 09:10:00
 */
@Data
public class HotSearchDTO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long hotSearchId;

    @ApiModelProperty("店铺ID 0为全局热搜")
    private Long shopId;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("顺序")
    private Integer seq;

    @ApiModelProperty("状态 0下线 1上线")
    private Integer status;

    @ApiModelProperty("热搜标题")
    private String title;


}
