package com.spring.business.service;

import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.vo.baseinfo.housingcertification.*;
import com.spring.common.response.ApiResponseResult;

/**
 * @className: HousingCertificationController
 * @Description: 美泰房屋认证Service
 * @author: shangshanshan
 * @date: 2021/1/4 15:01
 * @version: v1.0
 */
public interface IHousingCertificationService {

    //获取用户绑定的房屋列表
    ApiResponseResult queryHousingList(String userId);

    //查询用户是否绑定房屋
    ApiResponseResult getBindingStatus(String userId, String community);

    //修改房屋认证状态
    ApiResponseResult updateCertificationStatus(CertificationStatusUpdateVo vo);

    //获取房屋认证详情
    ApiResponseResult getCertificationDetail(String id);

    //新增房屋认证
    ApiResponseResult addHouseCertification(CertificationHouseAddVo vo);

    //修改房屋认证
    ApiResponseResult updateHouseCertification(CertificationHouseAddVo vo);

    //我的审批列表
    ApiResponseResult getCertifications(AuditHouseVo auditHouseVo);

    //我的审批列表
    ApiResponseResult certificationDetail(String id);

    //房屋审核
    ApiResponseResult housingAudit(CertificationVo vo);

    //查看当前业主的租客和亲属
    ApiResponseResult getOwnerTenantsRelatives(String houseId);

    //新增住户
    ApiResponseResult addHouseUser(CertificationHouseAddVo houseId);

    //业主删除亲属租客
    ApiResponseResult deleteHouseUser(HouseDeleteParamVo houseDeleteParamVo);

    //添加业主租客亲属和房屋关系的数据
    ApiResponseResult addHouseUserInfo(HouseEntity houseEntity);
}
