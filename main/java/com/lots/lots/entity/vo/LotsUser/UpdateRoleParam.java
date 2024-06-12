package com.lots.lots.entity.vo.LotsUser;

import com.lots.lots.common.validation.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * UpdateRoleParam
 *
 * @author lots
 * @date 2022/3/30 15:17
 */

@Data
@ApiModel("修改角色请求数据")
public class UpdateRoleParam implements Serializable {

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID不能为空",groups = {Update.class})
    private Long userId;

    @ApiModelProperty(value = "角色ID集合")
    @NotNull(message = "角色ID集合不能为空",groups = {Update.class})
    private List<Long> roleIds;
}
