package com.lots.lots.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 后台菜单表(lots_menu)
 *
 * @author lots
 * @version 1.0.0 2021-04-28
 */
@ApiModel(description = "后台菜单表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotsMenuVo implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -7930072446345558071L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;
    /**
     * 父级ID
     */
    @ApiModelProperty(value = "父级ID")
    private Long parentId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String title;
    /**
     * 菜单级数
     */
    @ApiModelProperty(value = "菜单级数")
    private Integer level;
    /**
     * 菜单排序
     */
    @ApiModelProperty(value = "菜单排序")
    private Integer sort;
    /**
     * 前端名称
     */
    @ApiModelProperty(value = "前端名称")
    private String name;
    /**
     * 前端图标
     */
    @ApiModelProperty(value = "前端图标")
    private String icon;
    /**
     * 前端隐藏
     */
    @ApiModelProperty(value = "前端隐藏")
    private Integer hidden;
}