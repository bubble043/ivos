package edu.fdu.ivos.vehicle.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class VehicleSaveParam {
    @ApiModelProperty(value = "Vehicle ID")
    private Long id;

    @ApiModelProperty(value = "Vehicle brand")
    private String brand;

    @ApiModelProperty(value = "License plate number")
    private String license;

    @ApiModelProperty(value = "Vehicle model")
    private String model;

    @ApiModelProperty(value = "Vehicle identification number (VIN)")
    private String code;

    @ApiModelProperty(value = "Displacement")
    private String displacement;

    @ApiModelProperty(value = "Vehicle status")
    private String status;

    @ApiModelProperty(value = "Vehicle type")
    private String type;

    @ApiModelProperty(value = "Vehicle color")
    private String color;

    @ApiModelProperty(value = "Vehicle mileage")
    private String kilometers;

    // The date sent from the frontend like "2020-1-1" is of type String.
    // To convert it to a Date object, use the @DateTimeFormat annotation.
    // Otherwise, a 400 TypeMismatch exception will occur.
    @ApiModelProperty(value = "Registration date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "Purchase date")
    private Date buyTime;

    @ApiModelProperty(value = "Purchase price")
    private String price;

    @ApiModelProperty(value = "Battery type")
    private String batteryType;

    @ApiModelProperty(value = "Geofence binding status")
    private String geofenceBindStatus;

    @ApiModelProperty(value = "Geofence ID")
    private Long geofenceId;
}
