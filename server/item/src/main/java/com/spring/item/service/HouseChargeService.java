package com.spring.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring.base.entity.item.HouseCharge;
import com.spring.base.vo.item.HouseChargeVO;

/**
 * 房屋管理
 *
 * @author zwb
 * @date 2020-04-13 17:35:56
 */
public interface HouseChargeService extends IService<HouseCharge> {

    /**批量保存**/
    Object saveList(HouseChargeVO bHouseChargeVO);

}
