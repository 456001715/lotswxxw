package com.lots.lots.crypto.document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 评论图片关联表(order_evaluation_img)
 * 
 * @author bianj
 * @version 1.0.0 2021-09-03
 */
@ApiModel(description = "评论图片关联表")
@Data
public class OrderEvaluationImgDocument {
    /** 版本号 */
    private static final long serialVersionUID = -1802800430101704226L;

    @ApiModelProperty(value = "主键ID")
    @Field(type = FieldType.Long, store = true)
    private Long orderEvaluationImgId;

    @ApiModelProperty(value = "关联评论ID")
    @Field(type = FieldType.Long, store = true)
    private Long orderEvaluationId;

    @ApiModelProperty(value = "图片地址")
    @Field(type = FieldType.Keyword, store = true)
    private String imgUrl;

}