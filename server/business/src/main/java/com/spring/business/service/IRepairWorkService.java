package com.spring.business.service;

import com.spring.base.entity.buiness.RepairEntity;
import com.spring.base.service.IBaseService;
import com.spring.business.vo.ConfirmPayVo;
import com.spring.business.vo.RepairFinishVo;
import com.spring.business.vo.RepairTransferVo;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 维修接口类
 */
public interface IRepairWorkService extends IBaseService<RepairEntity, Long> {

    /**
     * 维修确认接单
     *
     * @param
     * @return
     * @throws Exception
     * @author 作者：ZhaoJinHua
     * @version 创建时间：2021-01-05 14:57:47
     */
    ApiResponseResult acceptRepair(Long repairId) throws Exception;

    /**
     * @description:转单
     * @return:
     * @author: 赵进华
     * @time: 2021/1/6 16:30
     */
    ApiResponseResult repairTransfer(RepairTransferVo vo) throws Exception;

    /**
     * @description:派单
     * @return:
     * @author: 赵进华
     * @time: 2021/1/6 16:30
     */
    ApiResponseResult repairAllocation(RepairTransferVo vo) throws Exception;

    /**
     * @description:维修完成
     * @return:
     * @author: 赵进华
     * @time: 2021/1/6 17:08
     */
    ApiResponseResult repairFinish(RepairFinishVo vo) throws Exception;

    /**
     * @description:确认支付
     * @return: 
     * @author: 赵进华
     * @time: 2021/1/6 19:18
     */  
    ApiResponseResult confirmPay(ConfirmPayVo vo) throws Exception;
}
