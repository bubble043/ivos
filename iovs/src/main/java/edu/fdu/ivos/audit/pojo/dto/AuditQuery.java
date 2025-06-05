package edu.fdu.ivos.audit.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuditQuery {
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

    @ApiModelProperty(value = "Applicant name")
    private String username;
}
