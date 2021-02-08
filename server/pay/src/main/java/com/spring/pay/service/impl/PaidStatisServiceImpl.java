package com.spring.pay.service.impl;
import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.pay.paidstatis.*;
import com.spring.common.response.ApiResponseResult;
import com.spring.pay.dao.IPaidStatisDao;
import com.spring.pay.service.IPaidStatisService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * @author dell
 */
@Service
public class PaidStatisServiceImpl extends BaseServiceImpl implements IPaidStatisService {
    @Autowired
    private IPaidStatisDao paidStatisDao;

    @Override
    public ApiResponseResult queryPaidList(PaidQueryParam param) {
        List<PaymentRecordVo> paymentRecordVoList = paidStatisDao.queryPaidList(param);
        if(CollectionUtils.isNotEmpty(paymentRecordVoList)){
            Map<String, List<PaymentRecordVo>> listMap = paymentRecordVoList.stream().collect(Collectors.groupingBy(PaymentRecordVo::getChargeType));
            Set<Map.Entry<String, List<PaymentRecordVo>>> entrySet = listMap.entrySet();
            List<PaidStatisVo> paidStatisVoList=new ArrayList<>();
            List<ChargeNameGroup> allGroupList = new ArrayList<>();

            for (Map.Entry s :entrySet) {
                PaidStatisVo paidStatisVo = new PaidStatisVo();
                List<PaymentRecordVo> value =(List<PaymentRecordVo>) s.getValue();
                //设置费项科目
                paidStatisVo.setChargeTypeName(value.get(0).getChargeTypeName());
                //设置属于当前费项科目下的
                Map<String, List<PaymentRecordVo>> collect = value.stream().collect(Collectors.groupingBy(PaymentRecordVo::getChargeNo));
                List<ChargeNameGroup> groupList=new ArrayList<>();
                Set<Map.Entry<String, List<PaymentRecordVo>>> entries = collect.entrySet();
                for (Map.Entry e: entries) {
                    ChargeNameGroup group=new ChargeNameGroup();
                    List<PaymentRecordVo> voList =(List<PaymentRecordVo>) e.getValue();
                    group.setChargeNo(voList.get(0).getChargeNo());
                    group.setChargeName(voList.get(0).getChargeName());
                    group.setCash(voList.stream().filter(k->"cash".equals(k.getPaytype())).map(PaymentRecordVo::getBusinessAmount).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
                    group.setAlipay(voList.stream().filter(k->"支付宝".equals(k.getPaytype())).map(PaymentRecordVo::getBusinessAmount).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
                    group.setWechat(voList.stream().filter(k->"微信".equals(k.getPaytype())).map(PaymentRecordVo::getBusinessAmount).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
                    group.setUnionpay(voList.stream().filter(k->"unionpay".equals(k.getPaytype())).map(PaymentRecordVo::getBusinessAmount).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
                    group.setRefund(voList.stream().filter(k->"".equals(k.getPaytype())).map(PaymentRecordVo::getBusinessAmount).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));

                    group.setSubtotal(voList.stream().map(PaymentRecordVo::getBusinessAmount).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
                    groupList.add(group);

                }
                paidStatisVo.setChargeGroupList(groupList);
                paidStatisVo.setTotal(groupList.stream().map(ChargeNameGroup::getSubtotal).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
                paidStatisVoList.add(paidStatisVo);
                allGroupList.addAll(groupList);
            }
            PaidReportVo paidReportVo = new PaidReportVo();
            paidReportVo.setPaidStatisVoList(paidStatisVoList);
            SummaryVo summaryVo = new SummaryVo();
            summaryVo.setCashTotal(allGroupList.stream().map(ChargeNameGroup::getSubtotal).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
            summaryVo.setAlipayTotal(allGroupList.stream().map(ChargeNameGroup::getAlipay).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
            summaryVo.setWechatTotal(allGroupList.stream().map(ChargeNameGroup::getWechat).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
            summaryVo.setUnionpayTotal(allGroupList.stream().map(ChargeNameGroup::getUnionpay).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
            summaryVo.setRefundTotal(allGroupList.stream().map(ChargeNameGroup::getRefund).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
            summaryVo.setSubtotalTotal(allGroupList.stream().map(ChargeNameGroup::getSubtotal).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
            paidReportVo.setSummaryVo(summaryVo);


            return  createSuccessResult(paidReportVo);


        }


        return  createSuccessResult(null);
    }

    /**
     * @Desc:实收统计报表导出
     * @param param
     * @Author:邓磊
     * @UpdateDate:2020/5/22 19:24
     * @return: 返回
     */
    @Override
    public void exportPaidQueryParamInfo(PaidQueryParam param) throws Exception {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        PaidReportVo paidReportVo = queryList(param);
        excelDownload(response,paidReportVo);
    }
    public void excelDownload(HttpServletResponse response,PaidReportVo paidReportVo) throws Exception {
        String[] header = {"费项科目","费项标准","现金","支付宝","微信","pos机","退款","小计","合计"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("实收统计报表信息");
        sheet.setDefaultColumnWidth(15);
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFRow headrow = sheet.createRow(0);
        List<PaidStatisVo> paidStatisVoList =new ArrayList<>();
        if(paidReportVo !=null){
            paidStatisVoList = paidReportVo.getPaidStatisVoList();
        }
        for (int i = 0; i < header.length; i++) {
            HSSFCell cell = headrow.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(header[i]);
            cell.setCellValue(text);
            cell.setCellStyle(headerStyle);
        }
        for(int i=0;i<paidStatisVoList.size();i++){
            List<ChargeNameGroup> chargeGroupList = paidStatisVoList.get(i).getChargeGroupList();
            BigDecimal gotMoeny = new BigDecimal(0);
            BigDecimal moeny = new BigDecimal(0);
            BigDecimal amountOfMoney = new BigDecimal(0);
            int count =0;
            for(int j=0;j<chargeGroupList.size();j++){
                HSSFRow row1 = sheet.createRow(j+1);
                row1.createCell(1).setCellValue(new HSSFRichTextString(chargeGroupList.get(j).getChargeName()));
                row1.createCell(2).setCellValue(new HSSFRichTextString(chargeGroupList.get(j).getCash()==null?"":chargeGroupList.get(j).getCash().toString()));
                if(null!=chargeGroupList.get(j).getCash()){
                    BigDecimal cash = chargeGroupList.get(j).getCash();
                    amountOfMoney = amountOfMoney.add(cash);
                }
                row1.createCell(3).setCellValue(new HSSFRichTextString(chargeGroupList.get(j).getAlipay()==null?"":chargeGroupList.get(j).getAlipay().toString()));
                row1.createCell(4).setCellValue(new HSSFRichTextString(chargeGroupList.get(j).getWechat()==null?"":chargeGroupList.get(j).getWechat().toString()));
                row1.createCell(5).setCellValue(new HSSFRichTextString(chargeGroupList.get(j).getUnionpay()==null?"":chargeGroupList.get(j).getUnionpay().toString()));
                row1.createCell(6).setCellValue(new HSSFRichTextString(chargeGroupList.get(j).getRefund()==null?"":chargeGroupList.get(j).getRefund().toString()));
                row1.createCell(7).setCellValue(new HSSFRichTextString(chargeGroupList.get(j).getSubtotal()==null?"":chargeGroupList.get(j).getSubtotal().toString()));
                if(null!=chargeGroupList.get(j).getSubtotal()){
                    BigDecimal subtotal = chargeGroupList.get(j).getSubtotal();
                    gotMoeny = gotMoeny.add(subtotal);
                }
                if(chargeGroupList.size()-1==j){
                    count = j;
                    row1.createCell(0).setCellValue(new HSSFRichTextString(paidStatisVoList.get(0).getChargeTypeName()));
                    row1.createCell(8).setCellValue(new HSSFRichTextString(gotMoeny.toString()));
                }
                if(count > 1){
                    row1 = sheet.createRow(count+2);
                    if(null!=paidReportVo.getSummaryVo()){
                        row1.createCell(0).setCellValue(new HSSFRichTextString("总价"));
                        row1.createCell(1).setCellValue(new HSSFRichTextString(""));
                        row1.createCell(2).setCellValue(new HSSFRichTextString(amountOfMoney==null?"":amountOfMoney.toString()));
                        row1.createCell(3).setCellValue(new HSSFRichTextString(paidReportVo.getSummaryVo().getAlipayTotal()==null?"":paidReportVo.getSummaryVo().getAlipayTotal().toString()));
                        row1.createCell(4).setCellValue(new HSSFRichTextString(paidReportVo.getSummaryVo().getWechatTotal()==null?"":paidReportVo.getSummaryVo().getWechatTotal().toString()));
                        row1.createCell(5).setCellValue(new HSSFRichTextString(paidReportVo.getSummaryVo().getUnionpayTotal()==null?"":paidReportVo.getSummaryVo().getUnionpayTotal().toString()));
                        row1.createCell(6).setCellValue(new HSSFRichTextString(paidReportVo.getSummaryVo().getRefundTotal()==null?"":paidReportVo.getSummaryVo().getRefundTotal().toString()));
                        row1.createCell(7).setCellValue(new HSSFRichTextString(paidReportVo.getSummaryVo().getSubtotalTotal()==null?"":paidReportVo.getSummaryVo().getSubtotalTotal().toString()));
                        moeny=moeny.add(amountOfMoney.add(paidReportVo.getSummaryVo().getAlipayTotal()
                                .add(paidReportVo.getSummaryVo().getWechatTotal().add(paidReportVo.getSummaryVo().getUnionpayTotal())
                                        .add(paidReportVo.getSummaryVo().getRefundTotal().add(paidReportVo.getSummaryVo().getSubtotalTotal())))));
                        row1.createCell(8).setCellValue(new HSSFRichTextString(moeny.toString()));
                    }
                }
            }
        }
        String name = "实收统计报表信息";
        response.setHeader("content-Type","application/ms-excel");
        String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    public PaidReportVo queryList(PaidQueryParam param) {
        List<PaymentRecordVo> paymentRecordVoList = paidStatisDao.queryPaidList(param);
        if(CollectionUtils.isNotEmpty(paymentRecordVoList)){
            Map<String, List<PaymentRecordVo>> listMap = paymentRecordVoList.stream().collect(Collectors.groupingBy(PaymentRecordVo::getChargeType));
            Set<Map.Entry<String, List<PaymentRecordVo>>> entrySet = listMap.entrySet();
            List<PaidStatisVo> paidStatisVoList=new ArrayList<>();
            List<ChargeNameGroup> allGroupList = new ArrayList<>();

            for (Map.Entry s :entrySet) {
                PaidStatisVo paidStatisVo = new PaidStatisVo();
                List<PaymentRecordVo> value =(List<PaymentRecordVo>) s.getValue();
                //设置费项科目
                paidStatisVo.setChargeTypeName(value.get(0).getChargeTypeName());
                //设置属于当前费项科目下的
                Map<String, List<PaymentRecordVo>> collect = value.stream().collect(Collectors.groupingBy(PaymentRecordVo::getChargeNo));
                List<ChargeNameGroup> groupList=new ArrayList<>();
                Set<Map.Entry<String, List<PaymentRecordVo>>> entries = collect.entrySet();
                for (Map.Entry e: entries) {
                    ChargeNameGroup group=new ChargeNameGroup();
                    List<PaymentRecordVo> voList =(List<PaymentRecordVo>) e.getValue();
                    group.setChargeNo(voList.get(0).getChargeNo());
                    group.setChargeName(voList.get(0).getChargeName());
                    group.setCash(voList.stream().filter(k->"cash".equals(k.getPaytype())).map(PaymentRecordVo::getBusinessAmount).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
                    group.setAlipay(voList.stream().filter(k->"支付宝".equals(k.getPaytype())).map(PaymentRecordVo::getBusinessAmount).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
                    group.setWechat(voList.stream().filter(k->"微信".equals(k.getPaytype())).map(PaymentRecordVo::getBusinessAmount).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
                    group.setUnionpay(voList.stream().filter(k->"unionpay".equals(k.getPaytype())).map(PaymentRecordVo::getBusinessAmount).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
                    group.setRefund(voList.stream().filter(k->"".equals(k.getPaytype())).map(PaymentRecordVo::getBusinessAmount).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));

                    group.setSubtotal(voList.stream().map(PaymentRecordVo::getBusinessAmount).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
                    groupList.add(group);

                }
                paidStatisVo.setChargeGroupList(groupList);
                paidStatisVo.setTotal(groupList.stream().map(ChargeNameGroup::getSubtotal).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
                paidStatisVoList.add(paidStatisVo);
                allGroupList.addAll(groupList);
            }
            PaidReportVo paidReportVo = new PaidReportVo();
            paidReportVo.setPaidStatisVoList(paidStatisVoList);
            SummaryVo summaryVo = new SummaryVo();
            summaryVo.setCashTotal(allGroupList.stream().map(ChargeNameGroup::getSubtotal).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
            summaryVo.setAlipayTotal(allGroupList.stream().map(ChargeNameGroup::getAlipay).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
            summaryVo.setWechatTotal(allGroupList.stream().map(ChargeNameGroup::getWechat).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
            summaryVo.setUnionpayTotal(allGroupList.stream().map(ChargeNameGroup::getUnionpay).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
            summaryVo.setRefundTotal(allGroupList.stream().map(ChargeNameGroup::getRefund).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
            summaryVo.setSubtotalTotal(allGroupList.stream().map(ChargeNameGroup::getSubtotal).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
            paidReportVo.setSummaryVo(summaryVo);
            return paidReportVo;
        }
        return null;
    }

    @Override
    public BaseDao getBaseMapper() {
        return null;
    }
}
