package com.spring.workflow.service.impl;

import com.spring.workflow.dao.ProcessMapper;
import com.spring.workflow.service.ProcessInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author dell
 * @Auther: Ace Lee
 * @Date: 2019/3/7 16:55
 */
@Service
public class ProcessInfoServiceImpl implements ProcessInfoService {

    @Autowired
    private ProcessMapper processMapper;

    @Override
    public List<Map<String, Object>> models() {
        return processMapper.selectModels();
    }

    @Override
    public List<Map<String, Object>> process() {
        return processMapper.selectProcess();
    }

}
