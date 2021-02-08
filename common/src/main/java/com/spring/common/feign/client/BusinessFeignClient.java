package com.spring.common.feign.client;

import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.entity.buiness.ComplaintProposalEntity;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.entity.buiness.ReportingRepairEntity;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalEntityVo;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalUpdateVo;
import com.spring.base.vo.buiness.myhouse.MyHouseUpdateVo;
import com.spring.base.vo.buiness.reportingrepai.ReportingRepairUpdateVo;
import com.spring.common.constants.ServiceNameConstants;
import com.spring.common.feign.client.fallback.BusinessFeignClientFallbackFactory;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author dell
 * @Model: BusinessFeignClient
 * @Description: BusinessFeignClient
 * @Auter: Mollen
 * @Date: 2020/5/23  19:18
 *      
 **/
@FeignClient(name = ServiceNameConstants.BUSSINESS, fallbackFactory = BusinessFeignClientFallbackFactory.class, decode404 = true)
public interface BusinessFeignClient {



    /**
     * 同步房屋认证信息
     * @param houseEntity
     * @return
     * @throws Exception
     */
    @PostMapping(value = "houseManage/addHouseUserInfo")
    ApiResponseResult addHouseUserInfo(HouseEntity houseEntity) throws Exception;

    /**
     * 同步房屋认证信息
     * @param houseEntityList
     * @return
     * @throws Exception
     */
    @PostMapping(value = "houseManage/batchAddHouseUserInfo")
    ApiResponseResult batchAddHouseUserInfo(List<HouseEntity> houseEntityList) throws Exception;

}
