package com.spring.pay.filter;

import com.spring.base.entity.pay.BdHousePrepayInfoDto;
import com.spring.common.importExcel.dto.CellValidateResultDto;
import com.spring.common.importExcel.dto.RowValidateResultDto;
import com.spring.common.importExcel.filter.AbstractValidator;
import com.spring.common.importExcel.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class BankContractValidator extends AbstractValidator<BdHousePrepayInfoDto, RowValidateResultDto> {
    @Override
    protected RowValidateResultDto validate(BdHousePrepayInfoDto con) {
        RowValidateResultDto rvrDto = new RowValidateResultDto();
        rvrDto.setRowMarker(con.getIndex());


        rvrDto.addCellValidateResultList(isValidEmpty(con.getCompanyName(),"公司名称"));

        rvrDto.addCellValidateResultList(isValidEmpty(con.getCompanyId(),"公司编号"));

        rvrDto.addCellValidateResultList(isValidEmpty(con.getCommunityName(),"小区名称"));

        rvrDto.addCellValidateResultList(isValidEmpty(con.getCid(),"小区编号"));

        rvrDto.addCellValidateResultList(isValidEmpty(con.getHid(),"房屋编号"));

        //rvrDto.addCellValidateResultList(isValidEmpty(con.getUnpaidAmount(),"预缴金额"));

        //rvrDto.addCellValidateResultList(isValidEmpty(con.getUpdateTime(),"预缴时间"));

        // 校验预缴金额
        rvrDto.addCellValidateResultList(isValidPrepaidAmount(con.getUnpaidAmount()));

        // 校验预缴时间
        rvrDto.addCellValidateResultList(isValidDate(con.getUpdateTime()));


       /* // 校验房屋编号
        rvrDto.addCellValidateResultList(isValidHid(billDto.getHid()));

        // 校验费项编号
        rvrDto.addCellValidateResultList(isValidChargeNo(billDto.getChargeNo()));

        // 校验账单金额
        rvrDto.addCellValidateResultList(isValidBillAmount(billDto.getBillAmount()));

        // 校验违约金
        rvrDto.addCellValidateResultList(isValidPenaltyAmount(billDto.getPenaltyAmount()));

        // 校验开始日期
        rvrDto.addCellValidateResultList(isValidStartDate(billDto.getStartDate(), billDto.getEndDate()));

        // 校验结束日期
        rvrDto.addCellValidateResultList(isValidEndDate(billDto.getEndDate()));*/

        return rvrDto;
    }

    private CellValidateResultDto isValidEmpty(String colVal, String colName) {
        if (StringUtils.isEmpty(colVal)) {
            return new CellValidateResultDto(colName+"不能为空");
        }

        return null;
    }
    /**
     * 校验开始日期<br>
     * 规则：<br>
     * 1.开始日期不能为空<br>
     * 2.必须为日期格式<br>
     * 3.必须小于今天<br>
     * 4.必须大于结束日期
     *
     * @param startDate
     *            开始日期
     * @param endDate
     *            结束日期
     * @return
     */
    private CellValidateResultDto isValidStartDate(String startDate, String endDate) {
        if (StringUtils.isEmpty(startDate)) {
            return new CellValidateResultDto("开始日期不能为空");
        }

        if (DateUtil.formatDate(startDate) == null) {
            return new CellValidateResultDto("开始日期[" + startDate + "]的格式不正确");
        }

        if (!DateUtil.date1LtDate2(startDate, DateUtil.getCurrentDate())) {
            return new CellValidateResultDto("开始日期[" + startDate + "]必须小于今天");
        }

        if (DateUtil.formatDate(endDate) != null && DateUtil.date1GtDate2(startDate, endDate)) {
            return new CellValidateResultDto("开始日期[" + startDate + "]不能大于结束日期[" + endDate + "]");
        }

        return null;
    }

    /**
     * 校验预缴金额<br>
     * 规则：<br>
     * 1.不能为空<br>
     * 2.必须为大于0的合法金额
     *
     * @param prepaidAmount
     *            预缴金额
     * @return
     */
    private CellValidateResultDto isValidPrepaidAmount(String prepaidAmount) {
        if (StringUtils.isEmpty(prepaidAmount)) {
            return new CellValidateResultDto("预缴金额不能为空");
        }

        double d = NumberUtils.toDouble(prepaidAmount);
        if (d <= 0)
            return new CellValidateResultDto("预缴金额必须为大于0的合法数字");

        return null;
    }

    /**
     * 校验结束日期<br>
     * 规则：<br>
     * 1.不能为空<br>
     * 2.必须为日期格式<br>
     * 3.不能大于本月月底日期
     *
     * @param endDate
     *            结束日期
     * @return
     */
    private CellValidateResultDto isValidDate(String endDate) {
        if (StringUtils.isEmpty(endDate)) {
            return new CellValidateResultDto("预缴日期不能为空");
        }

        if (DateUtil.formatDate(endDate) == null) {
            return new CellValidateResultDto("预缴日期[" + endDate + "]的格式不正确");
        }

/*        if (DateUtil.date1GtDate2(endDate, DateUtil.getEndOfCurrentMonth())) {
            return new CellValidateResultDto("结束日期[" + endDate + "]不能大于本月底");
        }*/

        return null;
    }


}
