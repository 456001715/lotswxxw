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


/**
 * 后台角色菜单关系表(lots_role_menu_relation)
 *
 * @author lots
 * @version 1.0.0 2022-03-29
 */
@ApiModel(description = "后台角色菜单关系表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("lots_role_menu_relation")
public class LotsRoleMenuRelation implements java.io.Serializable {
   /**
    * 版本号
    */
    private static final long serialVersionUID = -6336869864218156513L;


    @ApiModelProperty(value = "主键ID")
    @TableId
    private Long id;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "菜单ID")
    private Long menuId;
}