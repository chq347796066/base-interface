package com.spring.common.feign.client.fallback;

import com.spring.base.dto.CommunityDto;
import com.spring.base.entity.baseinfo.*;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.vo.baseinfo.company.AddSaasVo;
import com.spring.base.vo.baseinfo.housingcertification.CertificationHouseAddVo;
import com.spring.base.vo.baseinfo.housingcertification.HouseDeleteParamVo;
import com.spring.base.vo.baseinfo.role.RoleVo;
import com.spring.base.vo.baseinfo.role.SaasRoleVo;
import com.spring.base.vo.baseinfo.user.UserUpdateLogo;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.response.ApiResponseResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dell
 * @Model: BaseInfoFeignClientFallbackFactory
 * @Description: BaseInfoFeignClientFallbackFactory
 * @Auter: Mollen
 * @Date: 2020/5/23  19:19
 *      
 **/
@Slf4j
@Component
public class BaseInfoFeignClientFallbackFactory implements FallbackFactory<BaseInfoFeignClient> {

    @Override
    public BaseInfoFeignClient create(Throwable throwable) {

        return new BaseInfoFeignClient(){

            @Override
            public CompanyEntity queryCompanyEntity(String id) throws Exception {
                log.error("通过id获取公司信息异常:{}", id, throwable);
                return new CompanyEntity();
            }

            @Override
            public ApiResponseResult queryList(CommunityEntity vo) {
                return null;
            }

            @Override
            public ApiResponseResult queryList(HouseEntity vo) {
                return null;
            }

            @Override
            public ApiResponseResult queryList(CompanyEntity vo) {
                return null;
            }

            @Override
            public ApiResponseResult queryList(BuildEntity vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult queryList(CellEntity vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult queryOwnerNameMobile(HouseEntity vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult queryList(NoticeEntity vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult queryObject(String id) {
                return null;
            }

            @Override
            public ApiResponseResult addPicList(List<PicEntity> vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult deletePicDelFlag(PicEntity vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult queryPicEntityVoList(PicEntity vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult queryList(RoleEntity vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult queryRoleUserList(RoleVo vo) throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult updateLogoUser(UserUpdateLogo vo) throws Exception {
                return null;
            }

            @Override
            public CommunityEntity queryCommunityEntity(String id) throws Exception {
                return null;
            }

            @Override
            public HouseEntity queryHouseInfo(HouseEntity vo) {
                return null;
            }

            @Override
            public BuildEntity queryBuildInfo(BuildEntity vo) {
                return null;
            }

            @Override
            public ApiResponseResult addSaasRole(SaasRoleVo vo) {
                return null;
            }

            @Override
            public ApiResponseResult delete(String id) {
                return null;
            }

            @Override
            public ApiResponseResult addTenant(AddSaasVo vo) {
                return null;
            }

            @Override
            public List<LinkedHashMap<String, String>> queryExportList(Map<String, Object> params) {

                return null;
            }

            @Override
            public ApiResponseResult addHouseUser(MyHouseEntity myHouseEntity) {
                log.error("住户插入异常:{}", myHouseEntity, throwable);
                return new ApiResponseResult();
            }

            @Override
            public ApiResponseResult deleteHouseUser(HouseDeleteParamVo houseDeleteParamVo) {
                log.error("删除异常:{}", houseDeleteParamVo, throwable);
                return new ApiResponseResult();
            }

            @Override
            public List<CommunityDto> getCommunityInfo() throws Exception {
                return null;
            }

            @Override
            public ApiResponseResult addCustomerHouseInfo(CertificationHouseAddVo vo) throws Exception {
                return null;
            }

        };

    }


}
