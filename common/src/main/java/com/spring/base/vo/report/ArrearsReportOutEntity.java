package com.spring.base.vo.report;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.spring.base.entity.report.ArrearsReportEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * 欠费统计
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "欠费统计")
public class ArrearsReportOutEntity extends Model<ArrearsReportOutEntity> {
    private static final long serialVersionUID = 1L;

    private List<ArrearsReportEntity> ArrearsReportEntityList;

    private Map<String,String> headMap;

}
