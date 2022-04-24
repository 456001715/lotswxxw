package com.lots.lots.vehicle.dto.po;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@ApiModel("文件管理")
@Table(name = "file_manage")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "file_manage", comment = "文件管理")
public class FileManage implements Serializable {
  @Id
  @Column(columnDefinition = "BIGINT(11) COMMENT '系统标识'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(columnDefinition = "varchar(255) COMMENT '文件名称'")
  private String fileOldName;
  
  @Column(columnDefinition = "varchar(255) COMMENT '文件名称'")
  private String fileNewName;
  
  @Column(columnDefinition = "varchar(255) COMMENT '文件后缀'")
  private String fileSuffix;
  
  @Column(columnDefinition = "varchar(255) COMMENT '文件路径'")
  private String url;
}
