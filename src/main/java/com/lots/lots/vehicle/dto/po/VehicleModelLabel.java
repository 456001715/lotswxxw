package com.lots.lots.vehicle.dto.po;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@ApiModel("车型标签")
@Table(name = "vehicle_model_label")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "vehicle_model_label", comment = "车型标签")
public class VehicleModelLabel {
  @Id
  @Min(0L)
  @Column(columnDefinition = "bigint COMMENT '系统标识'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(columnDefinition = "bigint COMMENT '车型系统标识'")
  private Long modelId;
  
  @Column(columnDefinition = "varchar(255) COMMENT '名称'")
  private String name;
  
  @Min(0L)
  @Max(1L)
  @Column(columnDefinition = "int(1) COMMENT '是否删除0=否,1=是'")
  private Integer isDelete;

}
