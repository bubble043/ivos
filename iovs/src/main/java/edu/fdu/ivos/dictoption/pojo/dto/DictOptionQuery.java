package edu.fdu.ivos.dictoption.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DictOptionQuery {
    @ApiModelProperty(value = "Option ID")
    private Long id;

    @ApiModelProperty(value = "Dictionary ID")
    private Long dictId;

    @ApiModelProperty(value = "Option label")
    private String label;

    @ApiModelProperty(value = "Option value")
    private String value;
}
