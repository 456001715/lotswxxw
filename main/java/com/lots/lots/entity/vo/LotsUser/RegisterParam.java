package com.lots.lots.entity.vo.LotsUser;

import com.lots.lots.common.validation.Insert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * RegisterParam
 *
 * @author lots
 * @date 2022/3/30 9:31
 */
@Data
@ApiModel("注册接口请求数据")
public class RegisterParam implements Serializable {

    @ApiModelProperty(value = "用户名")
    @NotNull(message = "用户名不能为空" , groups = {Insert.class})
    private String username;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "密码不能为空", groups = {Insert.class})
    private String password;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    @NotNull(message = "昵称不能为空", groups = {Insert.class})
    private String nickName;
}
