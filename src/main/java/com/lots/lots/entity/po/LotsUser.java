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
 * 后台用户表(lots_user)
 *
 * @author lots
 * @version 1.0.0 2022-03-29
 */
@ApiModel(description = "后台用户表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("lots_user")
public class LotsUser implements java.io.Serializable {
   /**
    * 版本号
    */
    private static final long serialVersionUID = -1599748129705975491L;


    @ApiModelProperty(value = "主键ID")
    @TableId
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "备注信息")
    private String note;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = DatePattern.NORM_DATETIME_PATTERN)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "最后登录时间")
    @JsonFormat(timezone = "GMT+8", pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date loginTime;

    @ApiModelProperty(value = "帐号启用状态：0->禁用；1->启用")
    private Integer status;
}