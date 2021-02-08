package com.spring.item.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.base.entity.item.BusDict;
import com.spring.item.mapper.BusDictMapper;
import com.spring.item.service.BusDictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据字典
 *
 * @author zwb
 * @date 2020-04-13 17:35:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BusDictServiceImpl extends ServiceImpl<BusDictMapper, BusDict> implements BusDictService {

}
