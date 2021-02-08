package com.spring.workflow.service;

import java.util.List;
import java.util.Map;

/**
 * @author dell
 * @Auther: Ace Lee
 * @Date: 2019/3/7 16:55
 */
public interface HistoryInfoService {

    List<Map<String, Object>> myTasksCompleted(String userId);

    List<Map<String, Object>> myProcessStarted(String userId);
}
