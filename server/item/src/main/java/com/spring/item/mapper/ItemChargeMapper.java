package com.spring.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.base.entity.item.ItemCharge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 费项类表
 *
 * @author zwb
 * @date 2020-04-13 16:46:09
 */
@Mapper
public interface ItemChargeMapper extends BaseMapper<ItemCharge> {

    List<ItemCharge>  getPageItemCharge(@Param("bItemCharge") ItemCharge bItemCharge);

    String getPdsSeq(String bsCode);

    String getNextId(String businessCode);

    String getNextRid(Map<String, String> params);

}
