package com.spring.workflow.dao;

import com.spring.workflow.model.ActRuTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 @author dell
 */
@Mapper
public interface ActRuTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(ActRuTask record);

    int insertSelective(ActRuTask record);

    ActRuTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ActRuTask record);

    int updateByPrimaryKey(ActRuTask record);

    List<ActRuTask> selectAll();
}