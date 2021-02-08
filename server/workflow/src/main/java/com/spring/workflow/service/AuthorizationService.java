package com.spring.workflow.service;

import java.util.List;

/**
 * @author dell
 * @Auther: Ace Lee
 * @Date: 2019/3/7 16:55
 */
public interface AuthorizationService {

    List<String> selectRoleIdsByUserId(String userId);
}
