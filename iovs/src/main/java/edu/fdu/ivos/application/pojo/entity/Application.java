package edu.fdu.ivos.application.pojo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Application {
    @ApiModelProperty(value = "Application form ID")
    private Long id;

    @ApiModelProperty(value = "Applicant ID")
    private Long userId;

    @ApiModelProperty(value = "Applicant name")
    private String username;

    @ApiModelProperty(value = "Vehicle usage start time")
    private Date startTime;

    @ApiModelProperty(value = "Vehicle usage end time")
    private Date endTime;

    @ApiModelProperty(value = "Driver's license image URL")
    private String imgUrl;

    @ApiModelProperty(value = "Departure address")
    private String departureAddr;

    @ApiModelProperty(value = "Destination address")
    private String destinationAddr;

    @ApiModelProperty(value = "Reason for application")
    private String reason;

    @ApiModelProperty(value = "Remarks")
    private String remark;

    @ApiModelProperty(value = "Application status")
    private String status;

    @ApiModelProperty(value = "Assigned vehicle ID")
    private Long vehicleId;

    @ApiModelProperty(value = "Rejection reason")
    private String rejectReason;

    @ApiModelProperty(value = "Creation timestamp")
    private Date createTime;

    @ApiModelProperty(value = "Update timestamp")
    private Date updateTime;

    @ApiModelProperty(value = "List of approver user IDs")
    private List<Long> auditUserIdList; // e.g., [106, 103]

    @ApiModelProperty(value = "Approver names (comma-separated string)")
    private String auditUsernameList; // e.g., "moly,tom"
}
