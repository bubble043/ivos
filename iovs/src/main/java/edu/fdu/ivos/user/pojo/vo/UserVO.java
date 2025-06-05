package edu.fdu.ivos.user.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserVO {
    @ApiModelProperty(value = "User ID")
    private Long id;

    @ApiModelProperty(value = "Username")
    private String username;

    @ApiModelProperty(value = "Password")
    private String password;

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

    @ApiModelProperty(value = "Creation time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "Update time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "Supervisor ID")
    private Long parentId;
}
