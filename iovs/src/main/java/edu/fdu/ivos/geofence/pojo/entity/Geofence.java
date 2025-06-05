package edu.fdu.ivos.geofence.pojo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Geofence {
    @ApiModelProperty(value = "Geofence ID")
    private Long id;

    @ApiModelProperty(value = "Geofence name")
    private String name;

    @ApiModelProperty(value = "Geofence status")
    private String status;

    @ApiModelProperty(value = "Geofence position data")
    private String position;

    @ApiModelProperty(value = "Creation time")
    private Date createTime;

    @ApiModelProperty(value = "Update time")
    private Date updateTime;
}
