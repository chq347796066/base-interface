package com.spring.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.base.entity.item.HouseCharge;
import com.spring.base.vo.item.HouseChargeVO;
import com.spring.common.sequence.SequeceName;
import com.spring.common.sequence.SequenceUtil;
import com.spring.item.mapper.HouseChargeMapper;
import com.spring.item.service.HouseChargeService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 房屋管理
 *
 * @author zwb
 * @date 2020-04-13 17:35:56
 */
@Service
public class HouseChargeServiceImpl extends ServiceImpl<HouseChargeMapper, HouseCharge> implements HouseChargeService {


    /**
     * 修改
     * @param bHouseChargeVO
     */
    @Override
    public Object saveList(HouseChargeVO bHouseChargeVO) {
        List<HouseCharge> bHouseChargeList=bHouseChargeVO.getBhouseChargeList();
        String cid = bHouseChargeVO.getCid();
        List<String> hids = bHouseChargeVO.getHids();
        LambdaQueryWrapper<HouseCharge> lambda=null;
        for(String hid:hids){
            lambda = new LambdaQueryWrapper<>();
            lambda.eq(HouseCharge::getHid, hid);
            lambda.eq(HouseCharge::getCid, cid);
            this.getBaseMapper().delete(lambda);

            if(CollectionUtils.isNotEmpty(bHouseChargeList)){
                bHouseChargeList.forEach(bHouseCharge -> {
                    bHouseCharge.setBhcId(SequenceUtil.getSequence(SequeceName.TablePrefix.BHCID));
                    bHouseCharge.setCid(cid);
                    bHouseCharge.setHid(hid);
                });
                this.saveBatch(bHouseChargeList);
            }
        }
        return null;
    }
}
