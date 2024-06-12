package com.lots.lots.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName WrpDTO
 * @Description 提现申请dto
 * @Author gb w
 * @Date 2021/11/19
 */
@Data
@ApiModel(value = "提现申请")
public class WrpDTO implements Serializable {

    /** 店铺id */
    private Long shopId;
    /** 店铺名称 */
    @NotBlank(message = "店铺名称不能为空")
    private String shopName;
    /** 姓名 */
    private String actualName;
    /** 手机号 */
    private String mobile;
    /** 提现时可用余额 */
    private Integer balance;
    /** 提现金额 */
    private Integer extractNum;
    /** 佣金比例 */
    private Integer serviceCharge;
    /** 打款金额 */
    private Integer remitNum;
    /** 银行卡号 */
    @NotBlank(message = "银行卡号不能为空")
    private String bankcardNumbers;
    /** 审核人id */
    private Long checkUserId;
    /** 是否打款 */
    private Integer isRemit;
    /** 状态 1待审核 2审核通过 3审核失败 */
    private Integer state;
    /** 备注 */
    private String remark;
    /** 验证码 */
    @NotBlank(message = "验证码不能为空")
    private String code;

    private String startTime;
    private String endTime;


}
