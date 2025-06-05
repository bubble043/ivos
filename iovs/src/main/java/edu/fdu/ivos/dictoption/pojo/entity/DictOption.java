package edu.fdu.ivos.dictoption.pojo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DictOption {
    @ApiModelProperty(value = "Option ID")
    private Long id;

    @ApiModelProperty(value = "Dictionary ID")
    private Long dictId;

    @ApiModelProperty(value = "Option label")
    private String label;

    @ApiModelProperty(value = "Option value")
    private String value;

    @ApiModelProperty(value = "Option sort order")
    private String sort;

    @ApiModelProperty(value = "Remarks")
    private String remark;

    @ApiModelProperty(value = "Creation time")
    private Date createTime;

    @ApiModelProperty(value = "Update time")
    private Date updateTime;
}
