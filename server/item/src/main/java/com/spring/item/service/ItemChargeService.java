package com.spring.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.spring.base.entity.item.ItemCharge;
import com.spring.common.page.RequestPageVO;
import com.spring.item.common.constants.BusinessCodeConstant;


/**
 * 费项类表
 *
 * @author zwb
 * @date 2020-04-13 16:46:09
 */
public interface ItemChargeService extends IService<ItemCharge> {

    PageInfo<ItemCharge> getPageItemCharge(RequestPageVO<ItemCharge> requestPageVO);

    String getNextId(BusinessCodeConstant bsCode, String refId);

    String getNextId(String preSuffix);

    String getNextId(String preSuffix, String refId);

    /**
     * @Desc:导出
     * @param bItemCharge
     * @Author:邓磊
     * @UpdateDate:2020/5/20 10:02
     * @return: 返回
     */
     void exportBitemChargeInfo(ItemCharge bItemCharge);

}
