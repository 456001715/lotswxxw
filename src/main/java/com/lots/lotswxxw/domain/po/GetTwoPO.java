package com.lots.lotswxxw.domain.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * get_two
 * 
 * @author lots
 * @version 1.0.0 2020-05-19
 */
@ApiModel(description = "")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTwoPO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 2059249616930630126L;

    /** 主键 */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /** user_id */
    @ApiModelProperty(value = "user_id")
    @NotBlank(message = "user_id不能为空！")
    private String uid;

    /** 红色号数 */
    @ApiModelProperty(value = "红色号数")
    @NotBlank(message = "红色号数不能为空！")
    private String redNumber;

    /** 蓝色号数 */
    @ApiModelProperty(value = "蓝色号数")
    @NotBlank(message = "蓝色号数不能为空！")
    private String blueNumber;

    /** 第几期 */
    @ApiModelProperty(value = "第几期")
    @NotNull(message = "第几期不能为空！")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date chapter;

    /** 是否中奖 */
    @ApiModelProperty(value = "是否中奖")
    private Boolean isTrue;

    /** 金额 */
    @ApiModelProperty(value = "金额")
    private Integer isRmb;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimestamp;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTimestamp;

    
   

    
   

    
   

    
   

    
   

    
   

    
   

    
   

    
   
}