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
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.hutool.core.date.DatePattern;


/**
 * lots_test
 *
 * @author lots
 * @version 1.0.0 2022-03-29
 */
@ApiModel(description = "")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("lots_test")
public class LotsTest implements java.io.Serializable {
   /**
    * 版本号
    */
    private static final long serialVersionUID = 6008864693722973825L;


    @ApiModelProperty(value = "主键ID")
    @TableId
    private Integer id;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "来自")
    private String form;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = DatePattern.NORM_DATETIME_PATTERN)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "最后更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = DatePattern.NORM_DATETIME_PATTERN)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "最后更新人")
    private String updateBy;

    @ApiModelProperty(value = "删除标记，字典数据，例如：0：已删除、1：未删除")
    @TableLogic
    private Integer deleteFlag;
}