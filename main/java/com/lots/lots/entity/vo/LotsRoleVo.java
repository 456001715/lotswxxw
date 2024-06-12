package com.lots.lots.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 后台用户角色表(lots_role)
 *
 * @author lots
 * @version 1.0.0 2021-04-28
 */
@ApiModel(description = "后台用户角色表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotsRoleVo implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -1166196468413606810L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 后台用户数量
     */
    @ApiModelProperty(value = "后台用户数量")
    private Integer adminCount;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 启用状态：0->禁用；1->启用
     */
    @ApiModelProperty(value = "启用状态：0->禁用；1->启用")
    private Integer status;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;
}