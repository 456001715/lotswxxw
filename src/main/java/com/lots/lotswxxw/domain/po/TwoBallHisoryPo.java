package com.lots.lotswxxw.domain.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
    import java.util.Date;
    import com.fasterxml.jackson.annotation.JsonFormat;

/**
* 双色球开奖历史(two_ball_hisory)
*
* @author lots
* @version 1.0.0 2020-09-18
*/
@ApiModel(description = "双色球开奖历史")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TwoBallHisoryPo implements java.io.Serializable {
/** 版本号 */
private static final long serialVersionUID = 1606833029074169593L;


            /** 主键 */
        @ApiModelProperty(value = "主键")
        private Integer id;

            /** 红球 */
        @ApiModelProperty(value = "红球")
        private String redNumber;

            /** 篮球 */
        @ApiModelProperty(value = "篮球")
        private String blueNumber;

            /** 创建时间 */
        @ApiModelProperty(value = "创建时间")
            @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
        private Date creatTime;

}