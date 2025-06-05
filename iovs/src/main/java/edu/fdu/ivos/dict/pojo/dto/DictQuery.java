package edu.fdu.ivos.dict.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DictQuery {
    @ApiModelProperty(value = "Dictionary ID")
    private Long id;

    @ApiModelProperty(value = "Dictionary name")
    private String name;

    @ApiModelProperty(value = "Dictionary code")
    private String code;
}
