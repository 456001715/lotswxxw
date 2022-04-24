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

@ApiModel("车辆订单备注")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "vehicle_order_remarks", comment = "车辆订单备注")
public class VehicleOrderRemarks implements Serializable {

    @Id
    @Min(0L)
    @Column(columnDefinition = "bigint COMMENT '系统标识'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0L)
    @ApiModelProperty(" 操作人user_id ")
    @Column(columnDefinition = "bigint COMMENT '操作人user_id'")
    private Long userId;

    @Min(1L)
    @ApiModelProperty(" 订单id ")
    @Column(columnDefinition = "bigint COMMENT '订单id'")
    private Long orderId;

    @Min(1L)
    @ApiModelProperty(" 车辆id ")
    @Column(columnDefinition = "bigint COMMENT '车辆id'")
    private Long vehicleId;

    @ApiModelProperty(" 备注信息 ")
    @Column(columnDefinition = "varchar(255) COMMENT '备注信息'")
    private String remarks;

    @ApiModelProperty(" 图片地址 ")
    @Column(columnDefinition = "text COMMENT '图片地址'")
    private String image;

    @Min(0L)
    @Max(1L)
    @ApiModelProperty(" 0-客户上传 1-员工上传 ")
    @Column(columnDefinition = "int COMMENT '0-客户上传 1-员工上传'")
    private int type;

    @Min(0L)
    @Max(1L)
    @ApiModelProperty(" 是否删除 默认0=否,1=是 ")
    @Column(columnDefinition = "int COMMENT '是否删除 默认0=否,1=是'")
    private int isDelete;

    @ApiModelProperty(" 添加时间 ")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(columnDefinition = "datetime COMMENT '添加时间'")
    private LocalDateTime createTime;
}
