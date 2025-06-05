package edu.fdu.ivos.user.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserLoginParam {
    @ApiModelProperty(value = "Username")
    private String username;

    @ApiModelProperty(value = "Password")
    private String password;
}
