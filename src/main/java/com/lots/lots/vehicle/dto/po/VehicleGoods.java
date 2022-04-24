package com.lots.lots.vehicle.dto.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel("车辆物件表")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "vehicle_goods", comment = "车辆物件表")
public class VehicleGoods implements Serializable {
    @Id
    @Min(0L)
    @Column(columnDefinition = "bigint COMMENT '系统标识'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(" 物件名称 ")
    @Column(columnDefinition = "varchar(255) COMMENT '物件名称'")
    private String name;

    @Min(0L)
    @ApiModelProperty(" 父级id ")
    @Column(columnDefinition = "bigint COMMENT '父级id'")
    private Long parentId;

    @Min(0L)
    @Max(1L)
    @ApiModelProperty(" 0-固件 1-随车证件 ")
    @Column(columnDefinition = "int COMMENT '0-固件 1-随车证件'")
    private int type;

    @Min(0L)
    @Max(1L)
    @ApiModelProperty(" 是否删除 默认0-否 1-是 ")
    @Column(columnDefinition = "int COMMENT '是否删除 默认0-否 1-是'")
    private int is_delete;

    @ApiModelProperty(" 添加时间 ")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(columnDefinition = "datetime COMMENT '添加时间'")
    private LocalDateTime createTime;
}
