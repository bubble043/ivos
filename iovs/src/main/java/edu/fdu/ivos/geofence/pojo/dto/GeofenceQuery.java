package edu.fdu.ivos.geofence.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GeofenceQuery {
    @ApiModelProperty(value = "Geofence ID")
    private Long id;

    @ApiModelProperty(value = "Geofence name")
    private String name;

    @ApiModelProperty(value = "Geofence status")
    private String status;
}
