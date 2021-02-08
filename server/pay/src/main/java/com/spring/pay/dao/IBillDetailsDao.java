package com.spring.pay.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.pay.BillDetailsEntity;
import com.spring.base.vo.pay.billdetail.BillDetailParamVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:13
 * @Desc类说明: 账单详情Dao
 */
@Mapper
public interface IBillDetailsDao extends BaseDao<BillDetailsEntity> {

      /**
       *
       * @Title: selectBYBillNo
       * @Description: 根据条件查询账单
       * @param  map
       * @return List<Map<String,String>>
       * @throws
       */
      List<Map<String, String>> queryPayDetial(Map map);

      List<BillDetailsEntity> queryDebtBillPage(BillDetailParamVo billDetailsEntity);

      List<BillDetailsEntity> queryDebtBillPageForOne(BillDetailParamVo billDetailsEntity);

      /**
       * 根据条件查询列表
       * @param entity
       * @return
       */
      List<BillDetailsEntity> queryAllBills(BillDetailParamVo entity) throws Exception;

      List<BillDetailsEntity> queryAllBillsForOne(BillDetailParamVo entity) throws Exception;

      List<BillDetailsEntity> queryAllBillPreview(String orderNo) throws Exception;

      /**
       * 删除
       */
      int deleteDelFlag(BillDetailsEntity vo);

}
