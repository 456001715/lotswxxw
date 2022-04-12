package com.lots.lots.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后台用户角色和权限关系表(lots_role_permission_relation)
 *
 * @author lots
 * @version 1.0.0 2021-05-07
 */
@ApiModel(description = "后台用户角色和权限关系表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotsRolePermissionRelationVo implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -3644906229629903414L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;
    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private Long roleId;
    /**
     * 权限ID
     */
    @ApiModelProperty(value = "权限ID")
    private Long permissionId;
}