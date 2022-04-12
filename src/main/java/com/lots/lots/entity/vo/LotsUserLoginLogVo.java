package com.lots.lots.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 后台用户登录日志表(lots_user_login_log)
 *
 * @author lots
 * @version 1.0.0 2021-04-28
 */
@ApiModel(description = "后台用户登录日志表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotsUserLoginLogVo implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 5426995592712201829L;

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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * IP
     */
    @ApiModelProperty(value = "IP")
    private String ip;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;
    /**
     * 浏览器登录类型
     */
    @ApiModelProperty(value = "浏览器登录类型")
    private String userAgent;
}