package com.spring.workflow.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dell
 */
@Mapper
public interface AuthorizationMapper {

    List<String> selectRoleIdsByUserId(String userId);
}