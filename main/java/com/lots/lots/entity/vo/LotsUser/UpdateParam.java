package com.lots.lots.entity.vo.LotsUser;

import com.lots.lots.common.validation.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * UpdateParam
 *
 * @author lots
 * @date 2022/3/30 14:18
 */
@Data
@ApiModel("修改接口请求数据")
public class UpdateParam implements Serializable {
    /**
     * 主键ID
     */
    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID不能为空" , groups = {Update.class})
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "备注信息")
    private String note;

    @ApiModelProperty(value = "帐号启用状态：0->禁用；1->启用")
    private Integer status;
}
