package com.lots.lots.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;


/**
* 申请开店记录表(shop_apply_record)
*
* @author lots
* @version 1.0.0 2021-09-24
*/
@ApiModel(description = "申请开店记录表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopApplyRecordDTO implements java.io.Serializable {

    /**
	 * 真实姓名
	 */
    @ApiModelProperty(value = "真实姓名")
    @NotEmpty(message="真实姓名不能为空")
    @Length(max = 30, min = 2,message = "姓名长度限制2~30字符")
    private String actualName;
    /**
	 * 身份证号
	 */
    @ApiModelProperty(value = "身份证号")
    @NotEmpty(message="身份证号不能为空")
    @Pattern(regexp = "^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$", message = "身份证格式错误")
    private String identityCard;
    /**
	 * 身份证正面
	 */
    @ApiModelProperty(value = "身份证正面")
    @NotEmpty(message="身份证正面不能为空")
    private String identityCardFront;
    /**
	 * 身份证反面
	 */
    @ApiModelProperty(value = "身份证反面")
    @NotEmpty(message="身份证反面不能为空")
    private String identityCardLater;
    /**
	 * 营业执照
	 */
    @ApiModelProperty(value = "营业执照")
    @NotEmpty(message="营业执照不能为空")
    private String businessLicense;
    /**
	 * 店铺名称
	 */
    @ApiModelProperty(value = "店铺名称")
    @NotEmpty(message="店铺名称不能为空")
    private String shopName;
    /**
	 * 省
	 */
    @ApiModelProperty(value = "省")
    @NotEmpty(message="省不能为空")
    private String province;
    /**
	 * 市
	 */
    @ApiModelProperty(value = "市")
    @NotEmpty(message="市不能为空")
    private String city;
    /**
	 * 区
	 */
    @ApiModelProperty(value = "区")
    @NotEmpty(message="区不能为空")
    private String area;
    /**
	 * 街道
	 */
    @ApiModelProperty(value = "街道")
    @NotEmpty(message="街道不能为空")
    private String street;
    /**
	 *  详细地址
	 */
    @ApiModelProperty(value = " 详细地址")
    @NotEmpty(message="详细地址不能为空")
    private String address;
    /**
	 * 经度
	 */
    @ApiModelProperty(value = "经度")
    @NotNull(message="经度不能为空")
    private BigDecimal longitude;
    /**
	 * 维度
	 */
    @ApiModelProperty(value = "维度")
    @NotNull(message="维度不能为空")
    private BigDecimal latitude;
    /**
	 * 手机号
	 */
    @ApiModelProperty(value = "手机号")
    @NotEmpty(message="手机号不能为空")
    @Pattern(regexp ="^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    @Length(max = 11, min = 11,message = "手机号只能为11位")
    private String telephone;
    /**
	 * 店铺logo
	 */
    @ApiModelProperty(value = "店铺logo")
    @NotNull(message="店铺logo不能为空")
    private String shopLogo;

    /**
     * 状态,0提交审核 1审核通过，2审核未通过
     */
    @ApiModelProperty(value = "状态,0提交审核 1审核通过，2审核未通过")
    private Integer applyStatus;

    /**
     * 店铺类型（1内部新增 2外部注册）
     */
    @ApiModelProperty(value = "店铺类型（1内部新增 2外部注册）")
    private Integer type;

    /**
	 * 创建时间
	 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
	 * 更新时间
	 */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @Size(max = 30)
    @NotEmpty(message="用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    @Size(max = 64)
    @NotEmpty(message="密码不能为空")
    @ApiModelProperty(value = "密码",required=true)
    private String password;
    /**
	 * 用户id
	 */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 佣金比例(单位为%)
     */
    @ApiModelProperty(value = "佣金比例(单位为%)")
    @NotNull(message="佣金比例不能为空")
    private Integer serviceCharge;

    /**
     * 银行卡
     */
    @ApiModelProperty(value = "银行卡号")
    @NotEmpty(message="银行卡号不能为空")
    private String bankCard;
    /**
     * 开户行
     */
    @ApiModelProperty(value = "开户行")
    @NotEmpty(message="开户行不能为空")
    private String openBank;
    /**
     * 商品售出超出七天是否允许退换(0-否，1-是)
     */
    @ApiModelProperty(value = "商品售出超出七天是否允许退换(0-否，1-是)")
    @NotNull(message="商品售出超出七天是否允许退换不能为空")
    private Integer sevenDaysReturnFlag;

    /**
     * 商铺主图
     */
    @ApiModelProperty(value = "店铺主图")
    @NotEmpty(message="店铺主图不能为空")
    private String mobileBackgroundPic;

    @ApiModelProperty(value = "店铺简介")
    @NotEmpty(message="店铺简介不能为空")
    private String intro;
}