package com.spring.baseinfo.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.buiness.ComplaintProposalEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalEntityVo;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalUpdateVo;
import com.spring.baseinfo.service.IComplaintProposaService;
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
 * @Desc类说明: 业主投诉建议业务接口实现类
 */

@Service("complaintProposalService")
public class ComplaintProposalServiceImpl extends BaseServiceImpl<ComplaintProposalEntityVo, String> implements IComplaintProposaService {

    @Autowired
    private MaintenanceFeignClient complaintProposalFeignCilnet;

    @Override
    public BaseDao getBaseMapper() {
        return null;
    }

    /**
     * 查询投诉建议列表
     * @param requestPageVO
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponseResult queryComplaintProposalPage(RequestPageVO<ComplaintProposalEntityVo> requestPageVO) throws Exception {
        return complaintProposalFeignCilnet.queryPageComplaintproposal(requestPageVO);
    }

    /**
     * 查询投诉建议详情
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponseResult queryComplaintProposal(ComplaintProposalEntityVo vo) throws Exception {
        return complaintProposalFeignCilnet.queryComplaintProposal(vo);
    }

    /**
     * @Desc:    更新
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/23 9:47
     * @return: 返回
     */
    @Override
    public ApiResponseResult update(ComplaintProposalUpdateVo vo) throws Exception {
        return complaintProposalFeignCilnet.update(vo);
    }



    /**
     * @Desc:报事报修信息数据导出
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/5/15 17:23
     * @return: 返回
     */
    @Override
    public void exportComplaintProposalEntityInfo(ComplaintProposalEntity vo) throws Exception {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        if(null != vo){
            List<ComplaintProposalEntity> list = complaintProposalFeignCilnet.queryProposalList(vo);
            excelDownload(response,list);
        }
    }
    public void excelDownload(HttpServletResponse response, List<ComplaintProposalEntity> list) throws Exception {
        //表头数据
        String[] header = {"联系地址","投诉建议内容","联系人","联系电话","状态","投诉建议时间","完成时间"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("投诉建议信息");
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
            row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getComplaintDescribe()));
            row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getUserName()));
            row1.createCell(3).setCellValue(new HSSFRichTextString(list.get(i).getMobile()));
            if(null != list.get(i).getProposalStatus()){
                if(list.get(i).getProposalStatus() ==1){
                    row1.createCell(4).setCellValue(new HSSFRichTextString("待处理"));
                }else if(list.get(i).getProposalStatus() ==2){
                    row1.createCell(4).setCellValue(new HSSFRichTextString("已完成"));
                }else if(list.get(i).getProposalStatus() ==3){
                    row1.createCell(4).setCellValue(new HSSFRichTextString("已评价"));
                }else{
                    row1.createCell(4).setCellValue(new HSSFRichTextString("已取消"));
                }
            }
            row1.createCell(5).setCellValue(new HSSFRichTextString(list.get(i).getStartDate()));
            row1.createCell(6).setCellValue(new HSSFRichTextString(list.get(i).getEndDate()));
        }
        response.setHeader("content-Type","application/ms-excel");
        String name = "投诉建议信息";
        String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }


}

