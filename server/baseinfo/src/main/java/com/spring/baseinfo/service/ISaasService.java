package com.spring.baseinfo.service;

import com.spring.base.vo.saas.BuyConfirmVo;
import com.spring.base.vo.saas.UpgradeVo;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: saas业务接口类
 */
public interface ISaasService {

    /**
     * @description:购买正式应用
     * @return:
     * @author: 赵进华
     * @time: 2020/7/15 9:52
     */
    ApiResponseResult buyApp(BuyConfirmVo vo) throws Exception;

    /**
     * @description:应用时间续费或者扩增小区数
     * @return:
     * @author: 赵进华
     * @time: 2020/7/15 11:23
     */
    ApiResponseResult extendApp(BuyConfirmVo vo) throws Exception;

    /**
     * @description:升级新应用
     * @return:
     * @author: 赵进华
     * @time: 2020/7/15 11:34
     */
    ApiResponseResult upgradeApp(UpgradeVo vo) throws Exception;
}
