package edu.fdu.ivos.audit.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AuditVO {
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

    // Since audit requires full application details, embed them in the VO
    @ApiModelProperty(value = "Applicant user ID")
    private Long userId;

    @ApiModelProperty(value = "Applicant name")
    private String username;

    @ApiModelProperty(value = "Vehicle use start time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "Vehicle use end time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "Driver’s license image URL")
    private String imgUrl;

    @ApiModelProperty(value = "Departure address")
    private String departureAddr;

    @ApiModelProperty(value = "Destination address")
    private String destinationAddr;

    @ApiModelProperty(value = "Reason for vehicle application")
    private String reason;

    @ApiModelProperty(value = "Remarks")
    private String remark;

    @ApiModelProperty(value = "Application status")
    private String status;

    @ApiModelProperty(value = "Assigned vehicle ID")
    private Long vehicleId;

    @ApiModelProperty(value = "Rejection reason")
    private String rejectReason;

    @ApiModelProperty(value = "List of auditor user IDs")
    private List<Long> auditUserIdList; // [106,103]

    @ApiModelProperty(value = "List of auditor usernames, comma-separated")
    private String auditUsernameList;   // "moly,tom"
}
