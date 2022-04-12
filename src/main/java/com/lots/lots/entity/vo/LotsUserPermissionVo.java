package com.lots.lots.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户权限表(lots_user_permission)
 *
 * @author lots
 * @version 1.0.0 2021-04-28
 */
@ApiModel(description = "用户权限表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotsUserPermissionVo implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -938089165346897323L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;
    /**
     * 父级权限id
     */
    @ApiModelProperty(value = "父级权限id")
    private Long pid;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 权限值
     */
    @ApiModelProperty(value = "权限值")
    private String value;
    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;
    /**
     * 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    @ApiModelProperty(value = "权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）")
    private Integer type;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;
    /**
     * 资源路径
     */
    @ApiModelProperty(value = "资源路径")
    private String uri;
    /**
     * 启用状态；0->禁用；1->启用
     */
    @ApiModelProperty(value = "启用状态；0->禁用；1->启用")
    private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}