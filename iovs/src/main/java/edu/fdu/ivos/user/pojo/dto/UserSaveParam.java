package edu.fdu.ivos.user.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserSaveParam {
    @ApiModelProperty(value = "User ID")
    private Long id;

    @ApiModelProperty(value = "Username")
    private String username;

    @ApiModelProperty(value = "Email")
    private String email;

    @ApiModelProperty(value = "Phone number")
    private String phone;

    @ApiModelProperty(value = "Age")
    private Integer age;

    @ApiModelProperty(value = "Gender")
    private String gender;

    @ApiModelProperty(value = "User status")
    private String status;

    @ApiModelProperty(value = "User level")
    private String level;

    @ApiModelProperty(value = "Supervisor ID")
    private Long parentId;
}
