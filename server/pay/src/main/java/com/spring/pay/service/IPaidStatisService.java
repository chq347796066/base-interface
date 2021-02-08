package com.spring.pay.service;

import com.spring.base.vo.pay.paidstatis.PaidQueryParam;
import com.spring.common.response.ApiResponseResult;

/**
 * @author dell
 */
public interface IPaidStatisService {
    /**
     * 实收统计报表
     * @param param
     * @return
     */
    ApiResponseResult queryPaidList(PaidQueryParam param);


    /**
     * @Desc:    实收统计报表导出
     * @param param
     * @Author:邓磊
     * @UpdateDate:2020/5/22 19:59
     * @return: 返回
     */
    void  exportPaidQueryParamInfo(PaidQueryParam param)throws Exception;

}
