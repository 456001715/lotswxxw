package com.lots.lots.entity.vo;

import com.lots.lots.common.validation.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 修改用户名密码参数
 *
 * @author lots
 */
@Getter
@Setter
public class UpdateAdminPasswordParam {
    @ApiModelProperty(value = "用户名", required = true)
    @NotNull(message = "用户名不能为空", groups = {Update.class})

    private String username;
    @ApiModelProperty(value = "旧密码", required = true)
    @NotNull(message = "旧密码不能为空", groups = {Update.class})
    private String oldPassword;

    @ApiModelProperty(value = "新密码", required = true)
    @NotNull(message = "新密码不能为空", groups = {Update.class})
    private String newPassword;
}
