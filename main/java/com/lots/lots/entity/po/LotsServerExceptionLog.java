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
 * 服务器异常日志表(lots_server_exception_log)
 *
 * @author lots
 * @version 1.0.0 2022-03-29
 */
@ApiModel(description = "服务器异常日志表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("lots_server_exception_log")
public class LotsServerExceptionLog implements java.io.Serializable {
   /**
    * 版本号
    */
    private static final long serialVersionUID = -5212583679443964675L;


    @ApiModelProperty(value = "日志id")
    @TableId
    private String logid;

    @ApiModelProperty(value = "ip地址")
    private String ipaddress;

    @ApiModelProperty(value = "端口号")
    private String port;

    @ApiModelProperty(value = "异常名称")
    private String exceptionname;

    @ApiModelProperty(value = "异常内容")
    private Byte[] content;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date createtime;

    @ApiModelProperty(value = "系统上下文")
    private String syspath;

    @ApiModelProperty(value = "客户端ip")
    private String clientip;

    @ApiModelProperty(value = "请求地址")
    private String url;

    @ApiModelProperty(value = "菜单id")
    private String menuid;

    @ApiModelProperty(value = "菜单名称")
    private String menuname;

    @ApiModelProperty(value = "")
    private String useragent;

    @ApiModelProperty(value = "异常类型")
    private String exceptiontype;

    @ApiModelProperty(value = "请求参数")
    private Byte[] requestparameter;
}