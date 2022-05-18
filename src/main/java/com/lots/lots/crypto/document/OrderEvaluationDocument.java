package com.lots.lots.crypto.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

/**
 * 订单评价表(order_evaluation)
 * 
 * @author bianj
 * @version 1.0.0 2021-09-03
 */
@ApiModel(description = "订单评价表")
@Document(indexName = "order_evaluation",shards = 1,replicas = 0)
@Data
public class OrderEvaluationDocument {
    /** 版本号 */
    private static final long serialVersionUID = 3251428088412265551L;

    @ApiModelProperty(value = "ID主键")
    @Id
    @Field(type = FieldType.Long, store = true)
    private Long orderEvaluationId;

    @ApiModelProperty(value = "订单ID")
    @Field(type = FieldType.Long, store = true)
    private Long orderId;

    @ApiModelProperty(value = "店铺ID")
    @Field(type = FieldType.Long, store = true)
    private Long shopId;

    @ApiModelProperty(value = "商品IDS")
    @Field(type = FieldType.Text, store = true)
    private String spuId;

    @ApiModelProperty(value = "用户ID")
    @Field(type = FieldType.Long, store = true)
    private Long userId;

    @ApiModelProperty(value = "用户昵称")
    @Field(type = FieldType.Keyword, store = true)
    private String userNickName;

    @ApiModelProperty(value = "用户头像")
    @Field(type = FieldType.Keyword, store = true)
    private String userPic;

    @ApiModelProperty(value = "是否为店主回复(0为否，1为是)")
    @Field(type = FieldType.Integer, store = true)
    private Integer isShopOwner;

    @ApiModelProperty(value = "是否匿名(0为否，1为是)")
    @Field(type = FieldType.Integer, store = true)
    private Integer isAnonymous;

    @ApiModelProperty(value = "是否嗮图(0为否，1为是)")
    @Field(type = FieldType.Integer, store = true)
    private Integer isImg;

    @ApiModelProperty(value = "是否为追加类型(0为否，1为是)")
    @Field(type = FieldType.Integer, store = true)
    private Integer isAdd;

    @ApiModelProperty(value = "评分(1-5星，默认为1星)")
    @Field(type = FieldType.Integer, store = true)
    private Integer score;

    @ApiModelProperty(value = "评价内容(大于6个字，小于140个字)")
    @Field(type = FieldType.Text, store = true)
    private String content;

    @ApiModelProperty(value = "上级评论ID(顶级为0)")
    @Field(type = FieldType.Long, store = true)
    private Long parentId;

    @ApiModelProperty(value = "审核状态(0-未审核，1-审核通过，2-审核未通过)")
    @Field(type = FieldType.Integer, store = true)
    private Integer checkStatus;

    @ApiModelProperty(value = "审核失败类型(0-文字图片违规，1-恶意给差评，2-虚假刷好评)")
    @Field(type = FieldType.Integer, store = true)
    private Integer checkFailureType;

    @ApiModelProperty(value = "点赞数量")
    @Field(type = FieldType.Long, store = true)
    private Long likeNumber;

    @ApiModelProperty(value = "追加评论距上次评论天数")
    @Field(type = FieldType.Integer, store = true)
    private Integer addDay;

    @ApiModelProperty(value = "商品规格详情")
    @Field(type = FieldType.Keyword, store = true)
    private String skuProperties;

    @ApiModelProperty(value = "商品名称")
    @Field(type = FieldType.Keyword, store = true)
    private String spuName;

    @ApiModelProperty("商品介绍主图")
    @Field(type = FieldType.Keyword, store = true)
    private String mainImgUrl;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "创建用户")
    @Field(type = FieldType.Keyword, store = true)
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "更新用户")
    @Field(type = FieldType.Keyword, store = true)
    private String updateUser;

    @ApiModelProperty(value = "评论图片")
    @Field(type = FieldType.Nested)
    private List<OrderEvaluationImgDocument> orderEvaluationImgDocumentList;

}