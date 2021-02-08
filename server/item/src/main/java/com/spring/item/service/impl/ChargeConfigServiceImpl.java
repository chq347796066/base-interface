package com.spring.item.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.entity.item.ChargeConfig;
import com.spring.base.entity.item.ChargeRule;
import com.spring.common.page.RequestPageVO;
import com.spring.common.sequence.SequeceName;
import com.spring.common.sequence.SequenceUtil;
import com.spring.item.mapper.ChargeConfigMapper;
import com.spring.item.service.ChargeConfigService;
import com.spring.item.service.ChargeRuleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * 费项配置
 *
 * @author zwb
 * @date 2020-04-13 17:35:46
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ChargeConfigServiceImpl extends ServiceImpl<ChargeConfigMapper, ChargeConfig> implements ChargeConfigService {

    @Autowired
    private ChargeRuleService bChargeRuleService;
    /**
     * 分页查询
     * @param requestPageVO
     * @return
     */
    @Override
    public PageInfo<ChargeConfig> getPageChargeConfig(RequestPageVO<ChargeConfig> requestPageVO) {
        PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
        ChargeConfig bChargeConfig;
        if(requestPageVO.getEntity()==null){
            bChargeConfig= new ChargeConfig();
        }else{
            bChargeConfig=requestPageVO.getEntity();
        }
        List<ChargeConfig> list = this.getBaseMapper().getPageChargeConfig(bChargeConfig);
        PageInfo<ChargeConfig> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 保存
     * @param bChargeConfig
     * @return
     */
    @Override
    public Object saveChargeConfig(ChargeConfig bChargeConfig) throws Exception{
        ChargeRule bChargeRule = new ChargeRule();
        BeanUtils.copyProperties(bChargeConfig,bChargeRule);
        if(bChargeRule != null ){
            String conRuleNo = SequenceUtil.getSequence(SequeceName.TablePrefix.CON_RULE_NO);
            bChargeRule.setConRuleNo(conRuleNo);
            bChargeRuleService.save(bChargeRule);
            bChargeConfig.setConRuleNo(conRuleNo);
        }
        return this.save(bChargeConfig);
    }

    /**
     * 修改费项配置
     * @param bChargeConfig
     * @return
     */
    @Override
    public Object updatebchargeconfig(ChargeConfig bChargeConfig) throws Exception{
        bChargeRuleService.removeById(bChargeConfig.getConRuleNo());
        ChargeRule bChargeRule = new ChargeRule();
        BeanUtils.copyProperties(bChargeRule,bChargeConfig);
        if(bChargeRule != null ){
            String conRuleNo = SequenceUtil.getSequence(SequeceName.TablePrefix.CON_RULE_NO);
            bChargeRule.setConRuleNo(conRuleNo);
            bChargeRuleService.save(bChargeRule);
            bChargeConfig.setConRuleNo(conRuleNo);
        }
        this.updateById(bChargeConfig);
        return null;
    }

    @Override
    public List<ChargeConfig> getbchargeconfigbycid(ChargeConfig bChargeConfig) {
        List<ChargeConfig> list = this.getBaseMapper().getPageChargeConfig(bChargeConfig);
        return list;
    }

    @Override
    public List<ChargeConfig> getChargeTypeByHid(ChargeConfig bChargeConfig) {
        List<ChargeConfig> list = this.getBaseMapper().getChargeTypeByHid(bChargeConfig);
        return list;
    }

    @Override
    public List<ChargeConfig> getChargeNoByChargeType(ChargeConfig bChargeConfig) {
        List<ChargeConfig> list = this.getBaseMapper().getChargeNoByChargeType(bChargeConfig);
        return list;
    }

    /**
     * @Desc:费项参数设置导出
     * @param bChargeConfig
     * @Author:邓磊
     * @UpdateDate:2020/5/20 10:47
     * @return: 返回
     */
    @Override
    public void exportBchargeConfigInfo(ChargeConfig bChargeConfig) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        List<ChargeConfig> list = this.getBaseMapper().getPageChargeConfig(bChargeConfig);
        try {
            excelDownload(response,list);
            log.info("导出查询出数据exportBchargeConfigInfo"+list);
        }catch (Exception e){
            log.error("导出exportBchargeConfigInfo费项参数设置信息异常"+e);
        }
    }

    @Override
    public List<ChargeConfig> getbchargeconfigbybatch(ChargeConfig bChargeConfig) {

        List<ChargeConfig> list = this.getBaseMapper().getbchargeconfigbybatch(bChargeConfig);

        return list;
    }

    public void excelDownload(HttpServletResponse response,List<ChargeConfig> list) throws Exception {
        String[] header = {"公司名称", "小区名称", "费项科目","费项名称","费项类别","计算方式","单价","计量单位","固定金额","计费周期","备注"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("费项参数设置信息");
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
            row1.createCell(0).setCellValue(new HSSFRichTextString(list.get(i).getCompanyName()));
            row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getCommunityName()));
            row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getChargeTypeName()));
            row1.createCell(3).setCellValue(new HSSFRichTextString(list.get(i).getChargeName()));
            row1.createCell(4).setCellValue(new HSSFRichTextString(list.get(i).getChargeCategoryName()));
            row1.createCell(5).setCellValue(new HSSFRichTextString(list.get(i).getValuationDescription()));
            row1.createCell(6).setCellValue(new HSSFRichTextString(list.get(i).getPrice()));
            row1.createCell(7).setCellValue(new HSSFRichTextString(list.get(i).getComputingUnit()));
            row1.createCell(8).setCellValue(new HSSFRichTextString(list.get(i).getFixedAmount()));
            switch(list.get(i).getCycle()){
                case "1":
                    row1.createCell(9).setCellValue(new HSSFRichTextString("每1个月收一次"));
                    break;
                case "2":
                    row1.createCell(9).setCellValue(new HSSFRichTextString("每2个月收一次"));
                    break;
                case "3":
                    row1.createCell(9).setCellValue(new HSSFRichTextString("每3个月收一次"));
                    break;
                case "4":
                    row1.createCell(9).setCellValue(new HSSFRichTextString("每4个月收一次"));
                    break;
                case "5":
                    row1.createCell(9).setCellValue(new HSSFRichTextString("每5个月收一次"));
                    break;
                case "6":
                    row1.createCell(9).setCellValue(new HSSFRichTextString("每6个月收一次"));
                    break;
                case "7":
                    row1.createCell(9).setCellValue(new HSSFRichTextString("每7个月收一次"));
                    break;
                case "8":
                    row1.createCell(9).setCellValue(new HSSFRichTextString("每8个月收一次"));
                    break;
                case "9":
                    row1.createCell(9).setCellValue(new HSSFRichTextString("每9个月收一次"));
                    break;
                case "10":
                    row1.createCell(9).setCellValue(new HSSFRichTextString("每10个月收一次"));
                    break;
                case "11":
                    row1.createCell(9).setCellValue(new HSSFRichTextString("每11个月收一次"));
                    break;
                default:
                    row1.createCell(9).setCellValue(new HSSFRichTextString("每12个月收一次"));
            }
            //备注没有给空
            row1.createCell(10).setCellValue(new HSSFRichTextString(""));
        }
        String name = "费项参数设置信息";
        response.setHeader("content-Type","application/ms-excel");
        String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}
