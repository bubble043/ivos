package edu.fdu.ivos.geofence.pojo.vo;

import edu.fdu.ivos.vehicle.pojo.vo.VehicleVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GeofenceVO {
    @ApiModelProperty(value = "Geofence ID")
    private Long id;

    @ApiModelProperty(value = "Geofence name")
    private String name;

    @ApiModelProperty(value = "Geofence status")
    private String status;

    @ApiModelProperty(value = "Geofence position data")
    private String position;

    @ApiModelProperty(value = "Creation time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /* The following three fields are used during vehicle assignment: view vehicles bound to the geofence */
    @ApiModelProperty(value = "Total number of vehicles bound to the geofence")
    private Integer totalNum;

    @ApiModelProperty(value = "Number of available vehicles under the geofence")
    private Integer availableNum;

    @ApiModelProperty(value = "List of vehicles bound to the geofence")
    private List<VehicleVO> vehicleList;
}
