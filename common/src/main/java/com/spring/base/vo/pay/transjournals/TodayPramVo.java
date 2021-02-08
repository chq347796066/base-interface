package com.spring.base.vo.pay.transjournals;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author dell
 */
@ApiModel
@Data
@ToString
public class TodayPramVo {

    //公司id
    @ApiModelProperty(value = "公司id")
    private String pcid;

}
