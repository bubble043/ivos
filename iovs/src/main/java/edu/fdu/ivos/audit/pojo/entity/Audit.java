package edu.fdu.ivos.audit.pojo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Audit {
    @ApiModelProperty(value = "Audit record ID")
    private Long id;

    @ApiModelProperty(value = "Application ID")
    private Long applicationId;

    @ApiModelProperty(value = "Auditor user ID")
    private Long auditUserId;

    @ApiModelProperty(value = "Audit status")
    private String auditStatus;

    @ApiModelProperty(value = "Audit order (sort)")
    private Integer auditSort;

    @ApiModelProperty(value = "Creation time")
    private Date createTime;

    @ApiModelProperty(value = "Last update time")
    private Date updateTime;
}
