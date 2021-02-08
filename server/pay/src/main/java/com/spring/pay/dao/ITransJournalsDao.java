package com.spring.pay.dao;

import com.spring.base.entity.pay.TransJournalsEntity;
import com.spring.base.vo.pay.queryrecord.QueryRecordVo;
import com.spring.base.vo.pay.transjournals.TodayIncomeResult;
import com.spring.base.vo.pay.transjournals.TodayResult;
import com.spring.base.vo.pay.transjournals.TotalIncome;
import com.spring.base.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 缴费记录Dao
 */
@Mapper
public interface ITransJournalsDao extends BaseDao<TransJournalsEntity> {

       String getPdsSeq(String bsCode);

       String getNextId(String businessCode);

       String getNextRid(Map<String, String> params);

       List<TransJournalsEntity> payQueryRecordList(QueryRecordVo queryRecordVo);

       List<TodayIncomeResult> getTotalIncome(TotalIncome totalIncome);

       TodayResult highestPlotIncome(TotalIncome highTotal);

       List<TodayIncomeResult> getTotalChargeOrder(TotalIncome highTotal);

       TodayResult getHighestChannel(TotalIncome highestChannel);

       String getCurrentCollection(TotalIncome highestChannel);

       String getCurrentAllCollection(TotalIncome highestChannel);

}
