package com.lots.lots.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @Author liujh
 * @date 2020/12/30
 */
@Data
@ApiModel(value= "用户名和密码参数")
public class UsernameAndPasswordDTO {

    @NotBlank(message="用户名不能为空")
    @Size(max = 30)
    @ApiModelProperty(value = "用户名",required=true)
    private String username;

    @NotBlank(message="密码不能为空")
    @Size(max = 64)
    @ApiModelProperty(value = "密码",required=true)
    private String password;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;


}
