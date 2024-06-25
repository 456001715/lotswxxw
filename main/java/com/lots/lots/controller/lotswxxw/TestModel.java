package com.lots.lots.controller.lotswxxw;

import com.lots.lots.common.validation.Delete;
import com.lots.lots.common.validation.Insert;
import com.lots.lots.common.validation.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * TestModel
 *
 * @author lots
 * @date 2022/3/24 11:26
 */
@Data
public class TestModel {
    /**
     * 订单id
     */
    @Schema(description = "id")
    @NotNull(message = "ID不能为空", groups = {Delete.class, Update.class})
    private Long id;

    @Schema(description = "经度")
    @NotNull(message = "经度不能为空")
    @Range(max = 180, min = -180, message = "经度值错误")
    private BigDecimal lon;

    @Schema(description = "维度")
    @NotNull(message = "维度不能为空")
    @Range(max = 90, min = -90, message = "维度值错误")
    private BigDecimal lat;

    @Schema(description = "身份证号")
    @NotEmpty(message = "身份证号不能为空")
    @Pattern(regexp = "^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$", message = "身份证格式错误")
    private String idCard;

    @Schema(description = "手机号")
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    @Length(max = 11, min = 11, message = "手机号只能为11位")
    private String telephone;

    @Schema(description = "邮箱")
    @Email
    private String email;
    @Schema(description = "密码")
    @NotEmpty(message = "密码不能为空")
    @Pattern(regexp = "[a-zA-Z0-9]\\w{5,17}$", message = "密码必须为6-18位的英文与数字组成")
    private String password;

    @Schema(description = "用户名")
    @NotEmpty(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{5,15}$", message = "用户名长度在6-16位之间且不能包含汉字与空格")
    private String username;

    @Schema(description = "购买金额")
    @NotNull(message = "购买金额不能为空")
    @Digits(integer = 9, fraction=2, message = "购买金额格式不正确")
    @DecimalMin(value = "0.01", message = "购买金额需大于等于0.01")
    private BigDecimal price;

}
