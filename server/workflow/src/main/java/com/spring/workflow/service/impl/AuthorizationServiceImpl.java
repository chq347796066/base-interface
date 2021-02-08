package com.spring.workflow.service.impl;

import com.spring.workflow.dao.AuthorizationMapper;
import com.spring.workflow.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 *
 * @author dell
 * @Auther: Ace Lee
 * @Date: 2019/3/7 16:55
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private AuthorizationMapper authorizationMapper;

    @Override
    public List<String> selectRoleIdsByUserId(String userId) {
        return authorizationMapper.selectRoleIdsByUserId(userId);
    }
}
