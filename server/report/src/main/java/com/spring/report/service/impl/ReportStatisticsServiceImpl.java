package com.spring.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.entity.baseinfo.CompanyEntity;
import com.spring.base.entity.report.ArrearsReportEntity;
import com.spring.base.vo.report.ArrearsReportInEntity;
import com.spring.base.vo.report.ArrearsReportOutEntity;
import com.spring.common.page.RequestPageVO;
import com.spring.report.mapper.ReportStatisticsMapper;
import com.spring.report.service.IReportStatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * zwb
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReportStatisticsServiceImpl extends ServiceImpl<ReportStatisticsMapper, ArrearsReportEntity> implements IReportStatisticsService {

    /**
     *根据小区统计
     * @return
     */
    @Override
    public PageInfo<ArrearsReportEntity> selectArrearReportByCIdList(RequestPageVO<ArrearsReportInEntity> arrearsReportInEntity) {
        PageHelper.startPage(arrearsReportInEntity.getCurrentPage(), arrearsReportInEntity.getPageSize(), true);
        List<ArrearsReportEntity> arrearsReportEntities = this.baseMapper.selectArrearReportByCIdList(arrearsReportInEntity.getEntity());
        PageInfo<ArrearsReportEntity> pageInfo = new PageInfo<>(arrearsReportEntities);
        return pageInfo;
    }

    /**
     * 根据公司统计
     * @return
     */
    @Override
    public PageInfo<ArrearsReportOutEntity> selectArrearReportByPcIdList(RequestPageVO<ArrearsReportInEntity> arrearsReportInEntity) {
        ArrearsReportInEntity entity = arrearsReportInEntity.getEntity();
        String compayId = entity.getCompayId();
        CompanyEntity companyEntity =this.baseMapper.queryChlidTreeByParentId(compayId);
        List<String> chlidrenNodeByParen = this.getChlidrenNodeByParen(companyEntity);
        PageHelper.startPage(arrearsReportInEntity.getCurrentPage(), arrearsReportInEntity.getPageSize(), true);
        entity.setCompayIdList(chlidrenNodeByParen);
        List<ArrearsReportEntity> arrearsReportEntities = this.baseMapper.selectArrearReportByPcIdList(entity);
        Map<String,String> map;
        //查询headList
        Map<String,String> mapHeader = new HashMap<>();

        String chargeName,chargeNo;
        for(ArrearsReportEntity arrearsReport:arrearsReportEntities){
            //处理费项
            List<String> chargeNameList = Arrays.stream(arrearsReport.getChargeNameArr().split(",")).collect(Collectors.toList());
            map = new HashMap<>();
            if(chargeNameList!=null&&chargeNameList.size()>0){
                for(String chargeNameStr:chargeNameList){
                    chargeNo=chargeNameStr.split("_")[0];
                    chargeName=chargeNameStr.split("_")[1];
                    map.put(chargeNo,chargeName);
                    arrearsReport.setChargeNameList(map);
                    //设置头
                    if(!mapHeader.containsKey(chargeNo)){
                        mapHeader.put(chargeNo,chargeName);
                    }
                }
            }

            //处理金额
            List<String> paymentAmountList = Arrays.stream(arrearsReport.getPaymentAmountArr().split(",")).collect(Collectors.toList());
            map = new HashMap<>();
            if(paymentAmountList!=null && paymentAmountList.size()>0){
                for(String paymentAmount:paymentAmountList){
                    map.put(paymentAmount.split("_")[0],paymentAmount.split("_")[1]);
                    arrearsReport.setPaymentAmountList(map);
                }
            }
        }

        ArrearsReportOutEntity arrearsReportOutEntity = new ArrearsReportOutEntity();
        arrearsReportOutEntity.setArrearsReportEntityList(arrearsReportEntities);
        arrearsReportOutEntity.setHeadMap(mapHeader);
        List<ArrearsReportOutEntity> retrunData = new ArrayList<>();
        retrunData.add(arrearsReportOutEntity);

        PageInfo<ArrearsReportOutEntity> pageInfo = new PageInfo<>(retrunData);
        return pageInfo;
    }

    /**
     * 递归处理子公司
     * @return
     */
    private List<String> getChlidrenNodeByParen(CompanyEntity companyEntity){
        List<String> list = new ArrayList<>();
        String companyId = companyEntity.getId();
        if(!list.contains(companyId)){
            list.add(companyId);
        }
        List<CompanyEntity> childrenList = companyEntity.getChildren();
        if(childrenList!=null && childrenList.size()>0){
            for(CompanyEntity cEntity:childrenList){
                list.addAll(getChlidrenNodeByParen(cEntity));
            }
        }
        return list;
    };
}