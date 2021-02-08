package com.spring.common.feign.client.fallback;

import com.spring.base.entity.buiness.ComplaintProposalEntity;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.entity.buiness.ReportingRepairEntity;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalEntityVo;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalUpdateVo;
import com.spring.base.vo.buiness.myhouse.MyHouseUpdateVo;
import com.spring.base.vo.buiness.reportingrepai.ReportingRepairUpdateVo;
import com.spring.common.feign.client.MaintenanceFeignClient;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author dell
 * @Model: MaintenanceFeignClientFallbackFactory
 * @Description: MaintenanceFeignClientFallbackFactory
 * @Auter: Mollen
 * @Date: 2020/5/23  19:19 
 *      
 **/
@Slf4j
@Component
public class MaintenanceFeignClientFallbackFactory implements FallbackFactory<MaintenanceFeignClient> {

    @Override
    public MaintenanceFeignClient create(Throwable throwable) {
        return new MaintenanceFeignClient(){
            @Override
            public ApiResponseResult queryPageComplaintproposal(RequestPageVO<ComplaintProposalEntityVo> requestPageVO) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult queryComplaintProposal(ComplaintProposalEntityVo vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult update(ComplaintProposalUpdateVo vo) throws Exception {
                return null;
            }

            @Override
            public List<ComplaintProposalEntity> queryProposalList(ComplaintProposalEntity requestPageVO) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult queryList(MyHouseEntity vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult queryPage(RequestPageVO<MyHouseEntity> requestPageVO) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult queryPage(MyHouseEntity vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult queryObject(String id) {
                return null;
            }

            @Override
            public ApiResponseResult update(MyHouseUpdateVo vo) throws Exception {
                return null;
            }

            @Override
            public List<MyHouseEntity> queryUserAppHouseList(MyHouseEntity vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult update(ReportingRepairUpdateVo vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult getReportingRepair(ReportingRepairEntity vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult queryPageReportingrepair(RequestPageVO<ReportingRepairEntity> requestPageVO) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult queryReportingRepairList(ReportingRepairEntity vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult exportExcelAsync(ReportingRepairEntity vo) throws Exception {
                return null;
            }

            @Override
            public List<ReportingRepairEntity> queryReporList(ReportingRepairEntity vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult queryHouseInfo(String userId) throws Exception {
                return null;
            }
        };
    }

}
