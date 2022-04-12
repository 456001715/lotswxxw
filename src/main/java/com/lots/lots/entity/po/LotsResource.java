package com.lots.lots.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.FieldFill;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.hutool.core.date.DatePattern;


/**
 * 后台资源表(lots_resource)
 *
 * @author lots
 * @version 1.0.0 2022-03-29
 */
@ApiModel(description = "后台资源表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("lots_resource")
public class LotsResource implements java.io.Serializable {
   /**
    * 版本号
    */
    private static final long serialVersionUID = -5215696892790867409L;


    @ApiModelProperty(value = "主键ID")
    @TableId
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = DatePattern.NORM_DATETIME_PATTERN)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "资源URL")
    private String url;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "资源分类ID")
    private Long categoryId;
}