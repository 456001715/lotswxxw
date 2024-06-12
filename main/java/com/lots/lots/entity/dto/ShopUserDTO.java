package com.lots.lots.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author liujh
 * @date 2020/9/8
 */
@Data
public class ShopUserDTO {

    @ApiModelProperty("店铺用户id")
    private Long shopUserId;

    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("员工编号")
    private String code;

    @ApiModelProperty("联系方式")
    private String phoneNum;

    @ApiModelProperty("头像")
    private String shopLogo;

    @ApiModelProperty("角色id列表")
    private List<Long> roleIds;


}
