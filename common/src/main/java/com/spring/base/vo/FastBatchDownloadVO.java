package com.spring.base.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @description:FastDFS批量下载vo
 * @return:
 * @author: 赵进华
 * @time: 2020/4/2 15:39
 */
@Data
@ToString
public class FastBatchDownloadVO implements Serializable {
    private static final long serialVersionUID = 2637755431406080379L;

    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @ApiModelProperty(value = "文件名称")
    private String specFileName;
}

