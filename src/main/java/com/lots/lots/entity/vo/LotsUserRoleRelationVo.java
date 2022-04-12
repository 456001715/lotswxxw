package com.lots.lots.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后台用户和角色关系表(lots_user_role_relation)
 *
 * @author lots
 * @version 1.0.0 2021-04-28
 */
@ApiModel(description = "后台用户和角色关系表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotsUserRoleRelationVo implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -8912201347790872063L;

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
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private Long roleId;
}