package com.lots.lots.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 后台菜单节点封装
 *
 * @author lots
 * @date 2020/2/4
 */
@Getter
@Setter
public class LotsMenuNode extends LotsMenuVo {
    @ApiModelProperty(value = "子级菜单")
    private List<LotsMenuNode> children;
}
