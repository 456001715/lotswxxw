package com.lots.lotswxxw.domain.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 听歌历史(listen_hisory)
 *
 * @author lots
 * @version 1.0.0 2020-04-22
 */
@ApiModel(description = "听歌历史")
@Data
public class ListenHisoryEntity implements Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -8423853144673012800L;

    @ApiModelProperty(value = "表Id")
    private Integer id;

    @ApiModelProperty(value = "歌名称")
    private String songName;

    @ApiModelProperty(value = "歌分数")
    private Integer songScore;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "歌手")
    private String singer;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date creatTime;

    @ApiModelProperty(value = "歌曲Id")
    private Long songId;
}
