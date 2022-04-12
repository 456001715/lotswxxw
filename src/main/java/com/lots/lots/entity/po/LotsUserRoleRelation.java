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
 * 后台用户和角色关系表(lots_user_role_relation)
 *
 * @author lots
 * @version 1.0.0 2022-03-29
 */
@ApiModel(description = "后台用户和角色关系表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("lots_user_role_relation")
public class LotsUserRoleRelation implements java.io.Serializable {
   /**
    * 版本号
    */
    private static final long serialVersionUID = 2487268145753097768L;


    @ApiModelProperty(value = "主键ID")
    @TableId
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;
}