package com.spring.baseinfo.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.buiness.ReportingRepairEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.buiness.reportingrepai.ReportingRepairUpdateVo;
import com.spring.baseinfo.service.IReportingRepaiService;
import com.spring.common.feign.client.BusinessFeignClient;
import com.spring.common.feign.client.MaintenanceFeignClient;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-11 15:49:31
 * @Desc类说明: 报事 报修业务接口实现类
 */

@Service("reportingRepaiService")
public class ReportingRepaiServiceImpl extends BaseServiceImpl<ReportingRepairEntity, String> implements IReportingRepaiService {
    @Autowired
    private MaintenanceFeignClient reportingRepaiFeignCilnet;

    @Override
    public BaseDao getBaseMapper() {
        return null;
    }

    /**
     * @Desc:   报事报修更改
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/23 11:05
     * @return: 返回
     */
    @Override
    public ApiResponseResult reportingRepairUpdate(ReportingRepairUpdateVo vo) throws Exception {
        return reportingRepaiFeignCilnet.update(vo);
    }

    /**
     * @Desc:   报事报修详情
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/23 11:05
     * @return: 返回
     */
    @Override
    public ApiResponseResult getReportingRepair(ReportingRepairEntity vo) throws Exception {
        return reportingRepaiFeignCilnet.getReportingRepair(vo);
    }

    /**
     * @Desc:    报事报修分页列表
     * @param requestPageVO
     * @Author:邓磊
     * @UpdateDate:2020/4/23 11:06
     * @return: 返回
     */
    @Override
    public ApiResponseResult queryReportingRepairPage(RequestPageVO<ReportingRepairEntity> requestPageVO) throws Exception {
        return reportingRepaiFeignCilnet.queryPageReportingrepair(requestPageVO);
    }


    /**
     * @Desc: 报事报修列表
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/28 19:47
     * @return: 返回
     */
    @Override
    public ApiResponseResult queryReportingRepairList(ReportingRepairEntity vo) throws Exception {
        return reportingRepaiFeignCilnet.queryReportingRepairList(vo);
    }


    /**
     * @Desc: 报事报修导出
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/24 20:23
     * @return: 返回
     */
    @Override
    public ApiResponseResult asynExportReportingRepair(ReportingRepairEntity vo) throws Exception {
        return reportingRepaiFeignCilnet.exportExcelAsync(vo);
    }



    /**
     * @Desc:报事报修信息数据导出
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/5/15 15:28
     * @return: 返回
     */
    @Override
    public void exportReportingRepairEntityInfo(ReportingRepairEntity vo) throws Exception {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        if(null!=vo){
            List<ReportingRepairEntity> list  = reportingRepaiFeignCilnet.queryReporList(vo);
            excelDownload(response,list);
        }
    }
    public void excelDownload(HttpServletResponse response,List<ReportingRepairEntity> list) throws Exception {
        //表头数据
        String[] header = {"报修地址","报修内容","报修人","联系电话","报修类型","状态","维修金额","报修时间","派工时间","完成时间"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("报事报修信息");
        sheet.setDefaultColumnWidth(15);
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFRow headrow = sheet.createRow(0);
        for (int i = 0; i < header.length; i++) {
            HSSFCell cell = headrow.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(header[i]);
            cell.setCellValue(text);
            cell.setCellStyle(headerStyle);
        }
        for(int i=0;i<list.size();i++){
            HSSFRow row1 = sheet.createRow(i+1);
            row1.createCell(0).setCellValue(new HSSFRichTextString(list.get(i).getComplaintAddress()));
            row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getReportingDescribe()));
            row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getUserName()));
            row1.createCell(3).setCellValue(new HSSFRichTextString(list.get(i).getMobile()));
            if(null != list.get(i).getRepairType()){
                if(list.get(i).getRepairType() ==1){
                    row1.createCell(4).setCellValue(new HSSFRichTextString("个人报修"));
                }else{
                    row1.createCell(4).setCellValue(new HSSFRichTextString("公共报修"));
                }
            }
            if(null != list.get(i).getReportingStatus()){
                if(list.get(i).getReportingStatus() ==1){
                    row1.createCell(5).setCellValue(new HSSFRichTextString("待派单"));
                }else if(list.get(i).getReportingStatus() ==2){
                    row1.createCell(5).setCellValue(new HSSFRichTextString("待接单"));
                }else if(list.get(i).getReportingStatus() ==3){
                    row1.createCell(5).setCellValue(new HSSFRichTextString("处理中"));
                }else if(list.get(i).getReportingStatus() ==4){
                    row1.createCell(5).setCellValue(new HSSFRichTextString("待确认"));
                }else if(list.get(i).getReportingStatus() ==5){
                    row1.createCell(5).setCellValue(new HSSFRichTextString("已完成"));
                }else if(list.get(i).getReportingStatus() ==6){
                    row1.createCell(5).setCellValue(new HSSFRichTextString("已评价"));
                }else{
                    row1.createCell(5).setCellValue(new HSSFRichTextString("已取消"));
                }
            }
            row1.createCell(6).setCellValue(new HSSFRichTextString(list.get(i).getOutlay()==null?"":list.get(i).getOutlay().toString()));
            row1.createCell(7).setCellValue(new HSSFRichTextString(list.get(i).getStartDate()));
            row1.createCell(8).setCellValue(new HSSFRichTextString(list.get(i).getDispatchDate()));
            row1.createCell(9).setCellValue(new HSSFRichTextString(list.get(i).getEndDate()));
        }
        response.setHeader("content-Type","application/ms-excel");
        String name = "报事报修信息";
        String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}

