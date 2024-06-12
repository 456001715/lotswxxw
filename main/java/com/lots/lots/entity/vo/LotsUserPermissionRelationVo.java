package com.lots.lots.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限)(lots_user_permission_relation)
 *
 * @author lots
 * @version 1.0.0 2021-04-28
 */
@ApiModel(description = "后台用户和权限关系表(除角色中定义的权限以外的加减权限)")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotsUserPermissionRelationVo implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 3605132592731387226L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    /**
     * 权限ID
     */
    @ApiModelProperty(value = "权限ID")
    private Long permissionId;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private Integer type;
}