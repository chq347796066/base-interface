package com.spring.workflow.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author dell
 */
@Mapper
public interface ProcessMapper {

    List<Map<String, Object>> selectModels();

    List<Map<String, Object>> selectProcess();

}