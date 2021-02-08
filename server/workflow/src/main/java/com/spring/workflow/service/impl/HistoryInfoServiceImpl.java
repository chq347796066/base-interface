package com.spring.workflow.service.impl;

import com.spring.workflow.dao.HistoryMapper;
import com.spring.workflow.service.HistoryInfoService;
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
public class HistoryInfoServiceImpl implements HistoryInfoService {
    @Autowired
    private HistoryMapper historyMapper;

    @Override
    public List<Map<String, Object>> myTasksCompleted(String userId) {
        return historyMapper.selectMyTasksCompleted(userId);
    }

    @Override
    public List<Map<String, Object>> myProcessStarted(String userId) {
        return historyMapper.selectMyProcessStarted(userId);
    }
}
