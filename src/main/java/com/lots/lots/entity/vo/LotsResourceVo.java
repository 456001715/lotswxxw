package com.lots.lots.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 后台资源表(lots_resource)
 *
 * @author lots
 * @version 1.0.0 2021-04-28
 */
@ApiModel(description = "后台资源表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotsResourceVo implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -3751976129878016298L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 资源名称
     */
    @ApiModelProperty(value = "资源名称")
    private String name;
    /**
     * 资源URL
     */
    @ApiModelProperty(value = "资源URL")
    private String url;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 资源分类ID
     */
    @ApiModelProperty(value = "资源分类ID")
    private Long categoryId;
}