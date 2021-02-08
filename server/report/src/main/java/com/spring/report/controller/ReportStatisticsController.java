package com.spring.report.controller;

import com.spring.base.entity.item.ChargeConfig;
import com.spring.base.vo.report.ArrearsReportInEntity;
import com.spring.common.StringUtil;
import com.spring.common.constants.MessageCode;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ResponseData;
import com.spring.report.service.IReportStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 欠费统计
 */
@RestController
@AllArgsConstructor
@RequestMapping("/statistics" )
@Api(value = "statistics", tags = "欠费统计")
public class ReportStatisticsController {

    private final IReportStatisticsService reportStatisticsService;

    /**
     * 欠费统计
     * @param requestPageVO
     * @return
     */
    @ApiOperation(value = "欠费统计", response = ChargeConfig.class)
    @PostMapping(value = "/selectReportStatistics")
    @Cacheable(cacheNames = "selectReportStatistics",key = "#requestPageVO")
    public ResponseData selectReportStatistics(@Valid @RequestBody  RequestPageVO<ArrearsReportInEntity> requestPageVO, BindingResult result){
        if(result.hasErrors()){
            throw new ValidationException(result);
        }

        ArrearsReportInEntity arrearsReportInEntity = requestPageVO.getEntity();
        if(arrearsReportInEntity == null){
            return new ResponseData<>("参数错误!", MessageCode.FAIL);
        }
        String communityId = arrearsReportInEntity.getCommunityId();
        if(communityId !=null){
            return new ResponseData<>(reportStatisticsService.selectArrearReportByCIdList(requestPageVO));
        }
        String compayId = arrearsReportInEntity.getCompayId();
        if(!StringUtil.isEmpty(compayId)){
            return new ResponseData<>(reportStatisticsService.selectArrearReportByPcIdList(requestPageVO));
        }
        return  new ResponseData<>("参数错误!", MessageCode.FAIL);
    }
}
