package com.lots.lots.vehicle.dto.po;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel("车型租金列表")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "rent_list", comment = "车型租金列表")
public class RentList {

    @Id
    @Min(0L)
    @Column(columnDefinition = "bigint COMMENT '系统标识'")
    @ApiModelProperty(" 系统标识 ")
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss ")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(" 租金时间 ")
    @Column(columnDefinition = "datetime COMMENT '租金时间'")
    private LocalDateTime rentalTime;

    @ApiModelProperty(" 价格 ")
    @Column(columnDefinition = "decimal(10,2) COMMENT '价格'")
    private BigDecimal rentalMoney;

    @ApiModelProperty(" 车型id ")
    @Column(columnDefinition = "bigint COMMENT '车型id'")
    private Long modelId;

    @ApiModelProperty(" 门店id ")
    @Column(columnDefinition = "bigint COMMENT '门店id'")
    private Long memberShopId;
}
