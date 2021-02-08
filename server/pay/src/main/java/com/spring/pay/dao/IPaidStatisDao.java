package com.spring.pay.dao;

import com.spring.base.vo.pay.paidstatis.PaidQueryParam;
import com.spring.base.vo.pay.paidstatis.PaymentRecordVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * @author dell
 */
@Mapper
public interface IPaidStatisDao {

    List<PaymentRecordVo> queryPaidList(PaidQueryParam param);
}
