package com.spring.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.base.entity.item.ChargeConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 费项配置
 *
 * @author zwb
 * @date 2020-04-13 17:35:46
 */
@Mapper
public interface ChargeConfigMapper extends BaseMapper<ChargeConfig> {


    List<ChargeConfig> getPageChargeConfig(@Param("bChargeConfig") ChargeConfig bChargeConfig);

    List<ChargeConfig> getChargeTypeByHid(@Param("bChargeConfig") ChargeConfig bChargeConfig);

    List<ChargeConfig> getChargeNoByChargeType(@Param("bChargeConfig") ChargeConfig bChargeConfig);

    List<ChargeConfig> getbchargeconfigbybatch(@Param("bChargeConfig") ChargeConfig bChargeConfig);
}
