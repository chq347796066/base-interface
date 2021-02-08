package com.spring.account.service;

import com.spring.base.entity.account.SubAccountEntity;
import com.spring.base.entity.baseinfo.BuildEntity;
import com.spring.base.entity.pay.BdHousePrepayInfoDto;
import com.spring.base.vo.pay.payonekeypay.BillOffsetVo;
import com.spring.base.vo.pay.payonekeypay.PayOneVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.response.R;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface IPrePayService {

    void createPrePay(PayOneVo vo) throws Exception;

    void importPrePay(List<BdHousePrepayInfoDto> bdHousePrepayInfoDtos)throws Exception;

    List<SubAccountEntity> queryPrePayList(BillOffsetVo billOffsetVo) throws Exception;

    List<LinkedHashMap<String,String>> queryExportList(Map<String, Object> params) throws Exception;

    ApiResponseResult getPrepayList(RequestPageVO<SubAccountEntity> requestPageVO) throws Exception;

    ApiResponseResult getPrepayList(SubAccountEntity requestPageVO) throws Exception;

    ApiResponseResult exportPrepayExcelAsync(SubAccountEntity vo)throws Exception;

    /**
     * @description:异步导出预收Excel
     * @return: 
     * @author: 赵进华
     * @time: 2020/12/9 10:49
     */
    List<SubAccountEntity> exportPrepayExcel(SubAccountEntity vo)throws Exception;

}
