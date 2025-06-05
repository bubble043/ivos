package edu.fdu.ivos.vehicle.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VehicleQuery {
    @ApiModelProperty(value = "Vehicle ID")
    private Long id;

    @ApiModelProperty(value = "License plate number")
    private String license;

    @ApiModelProperty(value = "Vehicle brand")
    private String brand;

    @ApiModelProperty(value = "Geofence binding status")
    private String geofenceBindStatus;

    @ApiModelProperty(value = "Geofence ID")
    private Long geofenceId;
}
