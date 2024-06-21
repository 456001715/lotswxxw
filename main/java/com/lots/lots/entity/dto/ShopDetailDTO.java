package com.lots.lots.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * 店铺详情DTO
 *
 * @Author liujh
 * @date 2020-12-05 15:50:25
 */
@Data
public class ShopDetailDTO{
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键")
	private Long hotSearchId;

	@ApiModelProperty("店铺ID 0为全局热搜")
	private Long shopId;

	@ApiModelProperty("内容")
	private String content;

	@ApiModelProperty("顺序")
	private Integer seq;

	@ApiModelProperty("状态 0下线 1上线")
	private Integer status;

	@ApiModelProperty("热搜标题")
	private String title;


}
