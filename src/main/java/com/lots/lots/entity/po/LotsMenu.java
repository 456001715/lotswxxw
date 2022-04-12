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
 * 后台菜单表(lots_menu)
 *
 * @author lots
 * @version 1.0.0 2022-03-29
 */
@ApiModel(description = "后台菜单表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("lots_menu")
public class LotsMenu implements java.io.Serializable {
   /**
    * 版本号
    */
    private static final long serialVersionUID = -747606262293645214L;


    @ApiModelProperty(value = "主键ID")
    @TableId
    private Long id;

    @ApiModelProperty(value = "父级ID")
    private Long parentId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = DatePattern.NORM_DATETIME_PATTERN)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "菜单名称")
    private String title;

    @ApiModelProperty(value = "菜单级数")
    private Integer level;

    @ApiModelProperty(value = "菜单排序")
    private Integer sort;

    @ApiModelProperty(value = "前端名称")
    private String name;

    @ApiModelProperty(value = "前端图标")
    private String icon;

    @ApiModelProperty(value = "前端隐藏")
    private Integer hidden;
}