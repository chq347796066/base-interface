package com.spring.common.feign.client.fallback;

import com.spring.base.dto.CommunityDto;
import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.entity.buiness.ComplaintProposalEntity;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.entity.buiness.ReportingRepairEntity;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalEntityVo;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalUpdateVo;
import com.spring.base.vo.buiness.myhouse.MyHouseUpdateVo;
import com.spring.base.vo.buiness.reportingrepai.ReportingRepairUpdateVo;
import com.spring.common.feign.client.BusinessFeignClient;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author dell
 * @Model: BusinessFeignClientFallbackFactory
 * @Description: BusinessFeignClientFallbackFactory
 * @Auter: Mollen
 * @Date: 2020/5/23  19:19
 *      
 **/
@Slf4j
@Component
public class BusinessFeignClientFallbackFactory implements FallbackFactory<BusinessFeignClient> {

    @Override
    public BusinessFeignClient create(Throwable throwable) {

        return new BusinessFeignClient() {

            @Override
            public ApiResponseResult addHouseUserInfo(HouseEntity houseEntity) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult batchAddHouseUserInfo(List<HouseEntity> houseEntityList) throws Exception {
                return null;
            }
        };

     }

}
