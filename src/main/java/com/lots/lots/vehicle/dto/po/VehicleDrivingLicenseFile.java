package com.lots.lots.vehicle.dto.po;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;


@Entity
@ApiModel("车辆行驶证图片")
@Table(name = "vehicle_driving_license_file")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "vehicle_driving_license_file", comment = "车辆行驶证图片")
public class VehicleDrivingLicenseFile {
  @Id
  @Min(0L)
  @Column(columnDefinition = "bigint COMMENT '系统标识'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(columnDefinition = "bigint COMMENT '车辆id'")
  private Long vehicleId;
  
  @Column(columnDefinition = "bigint COMMENT '车辆行驶证图片id'")
  private Long fileManageId;
}
