package com.spring.baseinfo.service;

import com.spring.base.dto.CommunityDto;

import java.util.List;

/**
 * @author 熊锋
 * @date 2021-01-14 9:22
 * @Describe: 当前登录用户相关信息（公司 项目 楼栋 单元）
 */
public interface ICurrentUserInfoService {

    /**
     * 获取当前登录着公司id
     * @Author 熊锋
     * @param
     * @Description //TODO
     * @Date 2021/1/14 9:45
     * @return
     */
    List<String> queryCurrentUserCompanyInfo();

    /**
     * 获取当前登录着公司id 查询项目信息
     * @Author 熊锋
     * @param companyIds
     * @Description //TODO
     * @Date 2021/1/14 9:45
     * @return
     */
    List<CommunityDto> queryCommunityInfoByCompanyId(List<String> companyIds);

    /**
     * 获取当前登录着项目信息
     * @Author 熊锋
     * @param
     * @Description //TODO
     * @Date 2021/1/14 9:45
     * @return
     */
    List<String> queryCurrentUserCommunityInfo();

}

