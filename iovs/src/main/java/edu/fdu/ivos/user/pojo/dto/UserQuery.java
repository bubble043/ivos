package edu.fdu.ivos.user.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserQuery {
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "Username")
    private String username;

    @ApiModelProperty(value = "User status")
    private String status;

    @ApiModelProperty(value = "User level")
    private String level;

    @ApiModelProperty(value = "Supervisor ID")
    private Long parentId;
}
