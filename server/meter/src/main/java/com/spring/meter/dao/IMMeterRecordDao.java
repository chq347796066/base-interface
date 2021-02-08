package com.spring.meter.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.meter.MMeterRecordEntity;
import com.spring.base.vo.meter.MMeterRecordStatisParam;
import com.spring.base.vo.meter.MMeterRecordStatisVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-10-28 14:30:45
 * @Desc类说明: 房屋抄记录Dao
 */
@Mapper
public interface IMMeterRecordDao extends BaseDao<MMeterRecordEntity> {

     int batchDeleteMeterRecord(@Param("ids") List<Long> ids) throws Exception;

    List<MMeterRecordStatisVo> queryStatisList(MMeterRecordStatisParam entity) throws Exception;

    List<MMeterRecordEntity> queryStatisDetailList(MMeterRecordStatisParam entity) throws Exception;

    MMeterRecordStatisVo queryStatisByCommunity(MMeterRecordStatisParam queryParam) throws Exception;
}
