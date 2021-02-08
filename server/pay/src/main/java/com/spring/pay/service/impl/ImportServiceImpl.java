package com.spring.pay.service.impl;

import com.spring.base.entity.ICIPContext;
import com.spring.base.entity.pay.BdHousePrepayInfoDto;
import com.spring.common.exception.ServiceException;
import com.spring.common.importExcel.dto.RowValidateResultDto;
import com.spring.common.importExcel.exception.InvalidExcelTemplateException;
import com.spring.common.importExcel.filter.AbstractFilterChain;
import com.spring.common.importExcel.helper.ExcelToBeanConvertor;
import com.spring.pay.filter.BankContractFilterChain;
import com.spring.pay.service.ImportService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class ImportServiceImpl implements ImportService {

    @Autowired
    private ImportPrepay importPrepay;

    @Override
    public Map<String, Object> readToBeanList(InputStream inputStream) throws ServiceException {

        String[] titles = {"公司名称","公司编号","小区名称","小区编号","楼栋名称","楼栋id","单元名称","单元id","房屋编码","房屋编号","业主编码","业主姓名","手机号",  "预缴金额", "预缴时间"};

        ExcelToBeanConvertor<BdHousePrepayInfoDto> convertor = new ExcelToBeanConvertor<>();

        /*******************************************************************/
        // 1.读取excel数据至对象map集合中
        /*******************************************************************/
        Map<String, List<BdHousePrepayInfoDto>> map = null;
        try {
            map = convertor.readToBeanMapForBank(inputStream, titles, BdHousePrepayInfoDto.class);
        } catch (InvalidExcelTemplateException e) { // 模板不正确则抛出异常
            throw new ServiceException("模板不正确",e);
        }

        if (MapUtils.isEmpty(map)){
            return null;
        }

        Map<String, Object> returnMap = new HashMap<>();

        // 总记录数
        int totalCount = 0;

        // 导入数据的校验结果集合
        List<RowValidateResultDto> rvrDtoList = new ArrayList<>();

        // 循环每一个符合模板格式的sheet页
        for (Map.Entry<String, List<BdHousePrepayInfoDto>> entry : map.entrySet()) {

            List<BdHousePrepayInfoDto>  list = entry.getValue();
            if(CollectionUtils.isEmpty(list)){
                continue;
            }
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setIndex((i+2)+"");
            }
            totalCount += list.size();
            ICIPContext context = new ICIPContext();


            // 3.校验数据
            /*******************************************************************/
            AbstractFilterChain<BdHousePrepayInfoDto, RowValidateResultDto> filterChain = new BankContractFilterChain(context);
            List<RowValidateResultDto> errorMsgList = filterChain.doFilterChain(list);

            if (CollectionUtils.isNotEmpty(errorMsgList)){
                rvrDtoList.addAll(errorMsgList); // 汇总经过过滤器链过滤后返回的校验结果
            }

            //4.批量插入数据
            /*******************************************************************/
            importPrepay.addList(list);
        }
        // 对校验结果集合进行排序，方便前端页面按照行号进行展示
        Collections.sort(rvrDtoList);

        returnMap.put("totalCount", totalCount);
        returnMap.put("rvrDtoList", rvrDtoList);

        return returnMap;
    }
}
