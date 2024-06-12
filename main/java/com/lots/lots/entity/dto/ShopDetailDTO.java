package com.lots.lots.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * 店铺详情DTO
 *
 * @Author liujh
 * @date 2020-12-05 15:50:25
 */
@Data
public class ShopDetailDTO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店铺id")
    private Long shopId;

    @ApiModelProperty("店铺类型（1内部新增 2外部注册）")
    private Integer type;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("店铺简介")
    private String intro;

    @ApiModelProperty("店铺logo")
    private String shopLogo;

    @ApiModelProperty("店铺状态(-1:已删除 0: 停业中 1:营业中)")
    private Integer shopStatus;

    @ApiModelProperty("营业执照")
    private String businessLicense;

    @ApiModelProperty("身份证正面")
    private String identityCardFront;

    @ApiModelProperty("身份证反面")
    private String identityCardLater;

    @Size(max = 30)
    @ApiModelProperty(value = "用户名",required=true)
    private String username;
    @Size(max = 30)

    @Size(max = 64)
    @ApiModelProperty(value = "密码",required=true)
    private String password;

	@ApiModelProperty("商铺主图")
//	@NotBlank(message="商铺主图不能为空")
	private String mobileBackgroundPic;

	/**
	 * 真实姓名
	 */
	@ApiModelProperty(value = " 真实姓名/银行预留联系人")
	private String actualName;

	/**
	 * 身份证号码
	 */
	@ApiModelProperty(value = " 身份证号码")
	private String identityCard;


	/**
	 * 省
	 */
	@ApiModelProperty(value = " 省")
	private String province;

	/**
	 * 市
	 */
	@ApiModelProperty(value = "市")
	private String city;

	/**
	 * 区
	 */
	@ApiModelProperty(value = "区")
	private String area;

	/**
	 * 街道
	 */
	@ApiModelProperty(value = "街道")
	private String street;

	/**
	 * 详细地址
	 */
	@ApiModelProperty(value = " 详细地址")
	private String address;

	/**
	 * 经度
	 */
	@ApiModelProperty(value = "经度")
	private BigDecimal longitude;

	/**
	 * 维度
	 */
	@ApiModelProperty(value = "维度")
	private BigDecimal latitude;

	/**
	 * 电话
	 */
	@ApiModelProperty(value = "电话/银行预留电话")
	private String telephone;

	/**
	 * 营业开始时间
	 */
	@ApiModelProperty(value = "营业开始时间")
	private String startHours;

	/**
	 * 营业结束时间
	 */
	@ApiModelProperty(value = "营业结束时间")
	private String endHours;

	/**
	 * 状态,0提交审核 1审核通过，2审核未通过
	 */
	@ApiModelProperty(value = "状态,0提交审核 1审核通过，2审核未通过")
	private Integer status;

	@ApiModelProperty(value = "用户id")
	private Long userId;
	/**
	 * 收藏量
	 */
	private Integer collect;
	/**
	 * 销量
	 */
	private Integer sales;
	/**
	 * 关联的闪送店铺id
	 */
	private Long shansongStoreId;
	/**
	 * 佣金比例(单位为%)
	 */
	@ApiModelProperty(value = "佣金比例(单位为%)")
	private Integer serviceCharge;

	/**
	 * 银行卡
	 */
	@ApiModelProperty(value = "银行卡号")
	private String bankCard;
	/**
	 * 开户行
	 */
	@ApiModelProperty(value = "开户行")
	private String openBank;
	/**
	 * 商品售出超出七天是否允许退换(0-否，1-是)
	 */
	@ApiModelProperty(value = "商品售出超出七天是否允许退换(0-否，1-是)")
	private Integer sevenDaysReturnFlag;

}
