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
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * UpdateRecordParam
 *
 * @author lots
 * @date 2022/4/25 17:07
 */

@ApiModel(description = "修改店铺详情提交审核提交参数")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRecordParam implements Serializable {
    private static final long serialVersionUID = 5462550677296113779L;


    @ApiModelProperty("店铺名称")
    @NotEmpty(message="店铺名称不能为空")
    private String shopName;

    @ApiModelProperty("店铺简介")
    @NotEmpty(message="店铺简介不能为空")
    private String intro;

    @ApiModelProperty("店铺logo")
    @NotEmpty(message="店铺logo不能为空")
    private String shopLogo;

    @ApiModelProperty("商铺主图")
    @NotEmpty(message="身份证反面不能为空")
    private String mobileBackgroundPic;

    @ApiModelProperty(value = "身份证正面")
    @NotEmpty(message="身份证正面不能为空")
    private String identityCardFront;
    /**
     * 身份证反面
     */
    @ApiModelProperty(value = "身份证反面")
    @NotEmpty(message="身份证反面不能为空")
    private String identityCardLater;

    @ApiModelProperty(value = "营业执照")
    @NotEmpty(message="营业执照不能为空")
    private String businessLicense;

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
    @ApiModelProperty(value = "纬度")
    @NotNull(message="纬度不能为空")
    private BigDecimal latitude;



    @ApiModelProperty(value = "联系人")
    @NotEmpty(message="联系人不能为空")
    @Length(max = 30, min = 2,message = "姓名长度限制2~30字符")
    private String actualName;

    @ApiModelProperty(value = "手机号")
    @NotEmpty(message="手机号不能为空")
    @Pattern(regexp ="^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    @Length(max = 11, min = 11,message = "手机号只能为11位")
    private String telephone;

    @ApiModelProperty(value = "身份证号")
    @Pattern(regexp = "^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$", message = "身份证格式错误")
    private String identityCard;

    @ApiModelProperty(value = "开户行")
    @NotEmpty(message="开户行不能为空")
    private String openBank;

    @ApiModelProperty(value = "银行卡号")
    @NotEmpty(message="银行卡号不能为空")
    private String bankCard;

    @ApiModelProperty(value = "佣金比例(单位为%)")
    @NotNull(message="佣金比例不能为空")
    private Integer serviceCharge;

    @ApiModelProperty(value = "商品售出超出七天是否允许退换(0-否，1-是)")
    @NotNull(message="商品售出超出七天是否允许退换不能为空")
    private Integer sevenDaysReturnFlag;

    /**
     * todo lots 分组方法
     */

    private String getChangeInformationGroup(){
        String ChangeInformationGroup=new String();
        if(getServiceCharge() != null){

        }
        return null;
    }
}
