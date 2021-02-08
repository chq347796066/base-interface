package com.spring.workflow.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author dell
 */
@ApiModel
@Data
@ToString
public class BatchApproveVo {

    private List<ApproveVo> list;

}
