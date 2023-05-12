package com.lots.lots.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 轮播图DTO
 *
 * @Author liujh
 * @date 2020-11-24 16:38:32
 */
@Data
public class IndexImgDTO{

    @NotNull(message = "userId not null")
    @ApiModelProperty("用户id")
    private Long userId;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @NotNull(message = "status not null")
    @ApiModelProperty("状态 1启用 0禁用")
    private Integer status;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("性别 0-为男，1-女")
    private Integer sex;

}
