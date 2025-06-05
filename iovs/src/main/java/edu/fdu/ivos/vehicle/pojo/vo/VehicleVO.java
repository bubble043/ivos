package edu.fdu.ivos.vehicle.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class VehicleVO {
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date regTime;

    @ApiModelProperty(value = "Purchase date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date buyTime;

    @ApiModelProperty(value = "Purchase price")
    private String price;

    @ApiModelProperty(value = "Battery type")
    private String batteryType;

    // No formatting required for creation and update time as they're not displayed on the frontend
    @ApiModelProperty(value = "Creation time")
    private Date createTime;

    @ApiModelProperty(value = "Update time")
    private Date updateTime;

    @ApiModelProperty(value = "Geofence binding status")
    private String geofenceBindStatus;

    @ApiModelProperty(value = "Geofence ID")
    private Long geofenceId;
}
