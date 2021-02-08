package com.spring.base.vo.baseinfo.document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @description:文档上传后显示vo
 * @return:
 * @author: 赵进华
 * @time: 2020/4/3 16:46
 */
@ApiModel
@Data
@ToString
public class DocumentShowVo {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "url")
    private String url;
}
