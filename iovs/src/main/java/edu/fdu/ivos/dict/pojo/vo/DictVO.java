package edu.fdu.ivos.dict.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DictVO {
    @ApiModelProperty(value = "Dictionary ID")
    private Long id;

    @ApiModelProperty(value = "Dictionary name")
    private String name;

    @ApiModelProperty(value = "Dictionary code")
    private String code;

    @ApiModelProperty(value = "Remarks")
    private String remark;

    @ApiModelProperty(value = "Creation date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
}
