package com.spring.baseinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.base.dto.CommunityDto;
import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.baseinfo.dao.ICommunityDao;
import com.spring.baseinfo.service.ICompanyService;
import com.spring.baseinfo.service.ICurrentUserInfoService;
import com.spring.common.request.RequestUtils;
import com.spring.common.util.list.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 熊锋
 * @date 2021-01-14 9:47
 * @Describe:
 */
@Service
@Slf4j
public class ICurrentUserInfoServiceImpl implements ICurrentUserInfoService {

    @Autowired
    private ICommunityDao communityDao;

    @Autowired
    private ICompanyService companyService;

    /**
     * 获取当前登录着公司信息
     * @Author 熊锋
     * @param
     * @Description //TODO
     * @Date 2021/1/14 9:45
     * @return
     */
    @Override
    public List<String> queryCurrentUserCompanyInfo() {
        String companyId = RequestUtils.getCompanyId();
        List<String> companyIds =companyService.queryCompanyChidrenList(companyId);
        return companyIds;
    }

    /**
     * 获取当前登录着公司id 查询项目信息
     * @Author 熊锋
     * @param companyIds
     * @Description //TODO
     * @Date 2021/1/14 9:45
     * @return
     */
    @Override
    public List<CommunityDto> queryCommunityInfoByCompanyId(List<String> companyIds) {
        QueryWrapper<CommunityEntity> queryWrapper=new QueryWrapper<>();
        List<CommunityDto> communityList=new ArrayList<>();
        if (CollectionUtils.isEmpty(companyIds)){
            return communityList;
        }else {
            queryWrapper.eq("del_flag",0).in("company_id",companyIds);
        }
        List<CommunityEntity> list=communityDao.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(list)){
            return new ArrayList<>();
        }
        ListUtil.copy(list,CommunityDto.class);
        CollectionUtils.addAll(communityList,ListUtil.copy(list,CommunityDto.class));
        return communityList;
    }
    /**
     * 获取当前登录着项目信息
     * @Author 熊锋
     * @param
     * @Description //TODO
     * @Date 2021/1/14 9:45
     * @return
     */
    @Override
    public List<String> queryCurrentUserCommunityInfo() {
        QueryWrapper<CommunityEntity> queryWrapper=new QueryWrapper<>();
        List<String> communityIds=new ArrayList<>();
        String communityId=RequestUtils.getCommunityId();
        log.info("当前登录人小区id为:{}",communityId);
        queryWrapper.eq("del_flag",0).eq("community_id",communityId);
        if (StringUtils.isBlank(communityId)){
            List<CommunityDto> list=queryCommunityInfoByCompanyId(queryCurrentUserCompanyInfo());
            if (CollectionUtils.isNotEmpty(list)){
                list.stream().forEach(communityDto -> {
                    communityIds.add(communityDto.getCommunityId());
                });
            }
            return communityIds;
        }else {
            CommunityEntity communityEntity=communityDao.selectOne(queryWrapper);
            CommunityDto communityDto=new CommunityDto();
            if (communityDto!=null){
                BeanUtils.copyProperties(communityEntity,communityDto);
            }
            communityIds.add(communityDto.getCommunityId());
            log.info("返回的小区id为:{}",communityIds.toString());
            return communityIds;
        }
    }
}
