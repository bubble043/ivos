package edu.fdu.ivos.application.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class ApplicationSaveParam {
    @ApiModelProperty(value = "Application form ID")
    private Long id;

    @ApiModelProperty(value = "Applicant ID")
    private Long userId;

    @ApiModelProperty(value = "Applicant name")
    private String username;

    @ApiModelProperty(value = "Vehicle usage start time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty(value = "Vehicle usage end time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    // Rejection reason and update timestamp are not included here by design

    @ApiModelProperty(value = "List of approver user IDs")
    private List<Long> auditUserIdList; // e.g., [106, 103], may include more than two
}
