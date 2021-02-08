package com.spring.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.spring.base.entity.item.ChargeConfig;
import com.spring.common.page.RequestPageVO;

import java.util.List;

/**
 * 费项配置
 *
 * @author zwb
 * @date 2020-04-13 17:35:46
 */
public interface ChargeConfigService extends IService<ChargeConfig> {

    PageInfo<ChargeConfig> getPageChargeConfig(RequestPageVO<ChargeConfig> requestPageVO);

    Object saveChargeConfig(ChargeConfig bChargeConfig) throws Exception;

    Object updatebchargeconfig(ChargeConfig bChargeConfig) throws Exception;

    List<ChargeConfig> getbchargeconfigbycid(ChargeConfig bChargeConfig);

    List<ChargeConfig> getChargeTypeByHid(ChargeConfig bChargeConfig);

    List<ChargeConfig> getChargeNoByChargeType(ChargeConfig bChargeConfig);

    /**
     * @Desc:费项参数设置导出
     * @param bChargeConfig
     * @Author:邓磊
     * @UpdateDate:2020/5/20 10:46
     * @return: 返回
     */
    void  exportBchargeConfigInfo(ChargeConfig bChargeConfig);

    List<ChargeConfig> getbchargeconfigbybatch(ChargeConfig bChargeConfig);
}
