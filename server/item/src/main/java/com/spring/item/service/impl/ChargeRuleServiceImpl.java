package com.spring.item.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.base.entity.item.ChargeRule;
import com.spring.item.mapper.ChargeRuleMapper;
import com.spring.item.service.ChargeRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 计算公式
 *
 * @author zwb
 * @date 2020-04-16 17:32:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChargeRuleServiceImpl extends ServiceImpl<ChargeRuleMapper, ChargeRule> implements ChargeRuleService {

}
