package com.lots.lots.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 服务器异常日志表(lots_server_exception_log)
 *
 * @author lots
 * @version 1.0.0 2021-04-28
 */
@ApiModel(description = "服务器异常日志表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotsServerExceptionLogVo implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -5027167971652106713L;

    /**
     * 日志id
     */
    @ApiModelProperty(value = "日志id")
    private String logid;
    /**
     * ip地址
     */
    @ApiModelProperty(value = "ip地址")
    private String ipaddress;
    /**
     * 端口号
     */
    @ApiModelProperty(value = "端口号")
    private String port;
    /**
     * 异常名称
     */
    @ApiModelProperty(value = "异常名称")
    private String exceptionname;
    /**
     * 异常内容
     */
    @ApiModelProperty(value = "异常内容")
    private byte[] content;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    /**
     * 系统上下文
     */
    @ApiModelProperty(value = "系统上下文")
    private String syspath;
    /**
     * 客户端ip
     */
    @ApiModelProperty(value = "客户端ip")
    private String clientip;
    /**
     * 请求地址
     */
    @ApiModelProperty(value = "请求地址")
    private String url;
    /**
     * 菜单id
     */
    @ApiModelProperty(value = "菜单id")
    private String menuid;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String menuname;
    /**
     * useragent
     */
    @ApiModelProperty(value = "")
    private String useragent;
    /**
     * 异常类型
     */
    @ApiModelProperty(value = "异常类型")
    private String exceptiontype;
    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private byte[] requestparameter;
}