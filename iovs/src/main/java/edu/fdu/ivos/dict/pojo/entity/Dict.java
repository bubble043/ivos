package edu.fdu.ivos.dict.pojo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Dict {
    @ApiModelProperty(value = "Dictionary ID")
    private Long id;

    @ApiModelProperty(value = "Dictionary name")
    private String name;

    @ApiModelProperty(value = "Dictionary code")
    private String code;

    @ApiModelProperty(value = "Remarks")
    private String remark;

    @ApiModelProperty(value = "Creation time")
    private Date createTime;

    @ApiModelProperty(value = "Update time")
    private Date updateTime;
}
