package com.spring.saas.dao;


import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.InvoiceEntity;
import com.spring.base.vo.saas.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-09 10:48:56
 * @Desc类说明: 发票信息Dao
 */
@Mapper
public interface IInvoiceDao extends BaseDao<InvoiceEntity> {

    /**
     * 查询发票列表
     * @author 熊锋
     * @param vo
     * @date 2020/7/10 11:35
     * @return java.util.List<com.spring.base.entity.saas.InvoiceEntity>
     * @throws Exception
     */
    List<InvoiceEntity> queryInvoice(InvoiceQueryVo vo);

    /**
     * 分页查询开票查询（运营后台）
     *
     * @param invoiceInquiryQueryVo
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/10 19:26
     */
    List<InvoiceInquiryPageVo> invoiceInquiryQueryPage(InvoiceInquiryQueryVo invoiceInquiryQueryVo) throws Exception;

    /**
     * 查询开票摘要
     *
     * @param invoiceId
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/13 14:55
     */
    InvoiceSummaryVo queryInvoiceSummary(String invoiceId) throws Exception;

    /**
     * 查询开票详情
     *
     * @param invoiceId
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/13 15:21
     */
    List<InvoiceDetailVo> queryInvoiceDetailList(String invoiceId) throws Exception;

    /**
     * 查询开票摘要
     * @author 熊锋
     * @param id
     * @date 2020/7/10 14:06
     * @return com.spring.common.response.ApiResponseResult
     * @throws Exception
     */
    MakeSummaryVo queryMakeInvoiceSummary(String id) throws Exception;
}
