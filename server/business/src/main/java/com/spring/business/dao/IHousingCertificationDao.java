package com.spring.business.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.vo.baseinfo.housingcertification.*;
import com.spring.business.dto.HouseInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IHousingCertificationDao {

    /**
     * @Description //查询用户房屋列表
     * @Author shangshanshan
     * @param userId
     * @return java.util.List<com.spring.base.vo.baseinfo.housingcertification.UserHouseListVo>
     * @throws
     * @Date 2021/1/4 19:13
     */
    List<UserHouseListVo> queryHousingList(String userId);

    /**
     * @Description //获取用户绑定的房屋id列表
     * @Author shangshanshan
     * @param param
     * @return java.util.List<java.lang.String>
     * @throws
     * @Date 2021/1/5 10:14
     */
    @SqlParser(filter=true)
    List<String> getBindingList(MyHouseEntity param);

    /**
     * @Description 修改房屋认证状态
     * @Author shangshanshan
     * @param vo
     * @return int
     * @throws
     * @Date 2021/1/5 11:37
     */
    int updateCertificationStatus(CertificationStatusUpdateVo vo);

    /**
     * @Description //查询认证详情
     * @Author shangshanshan
     * @param id
     * @return com.spring.base.vo.baseinfo.housingcertification.HouseCertificationDetailVo
     * @throws
     * @Date 2021/1/5 15:32
     */
    HouseCertificationDetailVo getCertificationDetail(String id);

    /**
     * @Description //查询当前房屋的业主数量
     * @Author shangshanshan
     * @param houseId
     * @return int
     * @throws
     * @Date 2021/1/6 11:04
     */
    int selectHouseOwnerCount(String houseId);

    /**
     * @Description //新增房屋认证
     * @Author shangshanshan
     * @param vo
     * @return int
     * @throws
     * @Date 2021/1/6 11:47
     */
    int addHouseCertification(CertificationHouseAddVo vo);

    /**
     * @Description //修改房屋认证
     * @Author shangshanshan
     * @param vo
     * @return int
     * @throws
     * @Date 2021/1/6 17:35
     */
    int updateHouseCertification(CertificationHouseAddVo vo);

    /**
     * @Author 熊锋
     * @param userId
     * @Description 查询用户信息
     * @Date 2021/1/7 9:53
     */
    List<HouseInfoDto> queryRepairHouseInfo(String userId);

    List<MyHouseEntity> queryList(AuditHouseVo auditHouseVo);

    MyHouseEntity queryObject(String id);

    int update(MyHouseEntity myHouseEntity);

    List<MyHouseEntity> getOwnerTenantsRelatives(String houseId);

    int add(MyHouseEntity myHouseEntity);

    int updateHouserUserStatus(String id);
}
