package edu.fdu.ivos.vehicle.pojo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Vehicle {
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

    @ApiModelProperty(value = "Registration date")
    private Date regTime;

    @ApiModelProperty(value = "Purchase date")
    private Date buyTime;

    @ApiModelProperty(value = "Purchase price")
    private String price;

    @ApiModelProperty(value = "Battery type")
    private String batteryType;

    @ApiModelProperty(value = "Creation time")
    private Date createTime;

    @ApiModelProperty(value = "Update time")
    private Date updateTime;

    @ApiModelProperty(value = "Geofence binding status")
    private String geofenceBindStatus;

    @ApiModelProperty(value = "Geofence ID")
    private Long geofenceId;
}
