package com.spring.workflow.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @description:批量驳回vo
 * @author: 赵进华
 * @time: 2020/2/20 18:24
 */
@ApiModel
@Data
@ToString
public class BatchRejectVo {
    private List<RejectVo> list;
}
