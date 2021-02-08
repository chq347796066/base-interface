package com.spring.pay.filter;


import com.spring.base.entity.ICIPContext;
import com.spring.base.entity.pay.BdHousePrepayInfoDto;
import com.spring.common.importExcel.dto.RowValidateResultDto;
import com.spring.common.importExcel.filter.AbstractFilterChain;

public class BankContractFilterChain extends AbstractFilterChain<BdHousePrepayInfoDto, RowValidateResultDto> {
    public BankContractFilterChain(ICIPContext context) {
        init(context);
    }

    /**
     * 初始化过滤器链
     *
     * @param context
     *            过滤过程中需要使用到的数据，统一放在了这个上下文中
     */
    private void init(ICIPContext context) {
        /** 基础性数据校验器 */
        this.filters.add(new BankContractValidator());

    }

}
