package com.lots.lots.controller.lotswxxw;

import com.lots.lots.common.validation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
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
    @ApiModelProperty(value = "id")
    @NotNull(message = "ID不能为空", groups = {Delete.class, Update.class})
    private Long id;

    @ApiModelProperty(value = "已收取的取车其他费用")
    private BigDecimal otherFeesPickCarPrice;

    @ApiModelProperty("免押类型 0=全额免押,1=未免押")
    @NotNull(message="免押类型不能为空", groups = Insert.class)
    private Integer depositType;

}
