package edu.fdu.ivos.dictoption.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DictOptionVO {
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

    @ApiModelProperty(value = "Creation date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
}
