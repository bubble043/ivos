package edu.fdu.ivos.application.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApplicationQuery {
    @ApiModelProperty(value = "Departure address")
    private String departureAddr;

    @ApiModelProperty(value = "Destination address")
    private String destinationAddr;

    @ApiModelProperty(value = "Application status")
    private String status;

    @ApiModelProperty(value = "Applicant name")
    private String username;
}
