package com.spring.item.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.spring.base.entity.item.ItemCharge;
import com.spring.common.page.RequestPageVO;
import com.spring.item.common.constants.BusinessCodeConstant;
import com.spring.item.mapper.ItemChargeMapper;
import com.spring.item.service.ItemChargeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 费项类表
 *
 * @author zwb
 * @date 2020-04-13 16:46:09
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ItemChargeServiceImpl extends ServiceImpl<ItemChargeMapper, ItemCharge> implements ItemChargeService {


    @Override
    public PageInfo<ItemCharge> getPageItemCharge(RequestPageVO<ItemCharge> requestPageVO) {
        PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
        ItemCharge itemCharge;
        if(requestPageVO.getEntity()==null){
            itemCharge= new ItemCharge();
        }else{
            itemCharge=requestPageVO.getEntity();
        }
        List<ItemCharge> list = this.getBaseMapper().getPageItemCharge(itemCharge);
        PageInfo<ItemCharge> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public String getNextId(BusinessCodeConstant bsCode, String refId){
        switch (bsCode) {
            case SUB_CHARGE_NO:
                return getNextId(bsCode.getBsCode(), refId);
            default :
                return getNextId(bsCode.getBsCode());
        }
    }

    @Override
    public String getNextId(String preSuffix){
        try {
            return this.getBaseMapper().getNextId(preSuffix);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String getNextId(String preSuffix, String refId){
        try {
            if (StringUtil.isEmpty(refId)) {
            }
            Map<String, String> params = new HashMap<>(16);
            params.put("refId", refId);
            params.put("bsCode", preSuffix);
            return this.getBaseMapper().getNextRid(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * @Desc:导出
     * @parambItemCharge
     * @Author:邓磊
     * @UpdateDate:2020/5/20 10:03
     * @return: 返回
     */
    @Override
    public void exportBitemChargeInfo(ItemCharge bItemCharge){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        List<ItemCharge> list = this.getBaseMapper().getPageItemCharge(bItemCharge);
        try {
            excelDownload(response,list);
            log.info("导出查询出数据exportBitemChargeInfo"+list);
        }catch (Exception e){
            log.error("导出exportBitemChargeInfo费项科目管理信息异常"+e);
        }
    }
    public void excelDownload(HttpServletResponse response,List<ItemCharge> list) throws Exception {
        String[] header = {"费项科目", "费项名称", "备注"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("费项科目管理信息");
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
            row1.createCell(0).setCellValue(new HSSFRichTextString(list.get(i).getChargeTypeName()));
            row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getChargeName()));
            row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getRemark()));
        }
        String name = "费项科目管理信息";
        response.setHeader("content-Type","application/ms-excel");
        String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}
