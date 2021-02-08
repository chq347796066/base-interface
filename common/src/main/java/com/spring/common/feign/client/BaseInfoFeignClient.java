package com.spring.common.feign.client;


import com.spring.base.dto.CommunityDto;
import com.spring.base.entity.baseinfo.*;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.vo.baseinfo.company.AddSaasVo;
import com.spring.base.vo.baseinfo.housingcertification.CertificationHouseAddVo;
import com.spring.base.vo.baseinfo.housingcertification.HouseDeleteParamVo;
import com.spring.base.vo.baseinfo.role.RoleVo;
import com.spring.base.vo.baseinfo.role.SaasRoleVo;
import com.spring.base.vo.baseinfo.user.UserUpdateLogo;
import com.spring.common.constants.ServiceNameConstants;
import com.spring.common.feign.client.fallback.BaseInfoFeignClientFallbackFactory;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author dell
 * @Model: BaseInfoFeignClient
 * @Description: BaseInfoFeignClient
 * @Auter: Mollen
 * @Date: 2020/5/23  19:18
 *
 **/
@FeignClient(name = ServiceNameConstants.BASEINFO, fallbackFactory = BaseInfoFeignClientFallbackFactory.class, decode404 = true)
public interface BaseInfoFeignClient {

    /**
     * 获取公司信息
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/company/queryCompanyEntity")
    CompanyEntity queryCompanyEntity(@RequestParam(value = "id", required = true) String id) throws Exception;

    /**
     * @Method:queryList
     * @Description: 获取小区信息列表
     * @Auter: Mollen
     * @Date: 2020/5/26  9:41
     * @return 
     **/
    @PostMapping(value = "/community/queryList")
    ApiResponseResult queryList(@RequestBody CommunityEntity vo) ;

    /**
     * @Method: queryList
     * @Description: 获取房屋信息
     * @Auter: Mollen
     * @Date: 2020/5/26  9:46
     * @return 
     **/
    @PostMapping(value = "/house/queryList")
    ApiResponseResult queryList(@RequestBody HouseEntity vo) ;

    /**
     * @Method: queryList
     * @Description:获取公司信息
     * @Auter: Mollen
     * @Date: 2020/5/26  9:47
     * @return 
     **/
    @PostMapping(value = "/company/queryList")
    ApiResponseResult queryList(@RequestBody CompanyEntity vo) ;


    /**
     * 楼栋
     * @param vo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "build/queryList")
    ApiResponseResult queryList(BuildEntity vo) throws Exception;

    /**
     * 单元
     * @param vo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "cell/queryList")
    ApiResponseResult queryList(CellEntity vo) throws Exception;

    /**
     * @Desc:    查看房屋信息审核用户信息
     * @Author:邓磊
     * @UpdateDate:2020/5/13 10:26
     * @return: 返回
     */
    @PostMapping(value = "house/queryOwnerNameMobile")
    ApiResponseResult queryOwnerNameMobile(HouseEntity vo) throws Exception;

    /**
     * 公告消息通知列表
     */
    @PostMapping(value = "notice/queryList")
    ApiResponseResult queryList(NoticeEntity vo) throws Exception;

    /**
     * 根据ID查询对象详情
     */
    @GetMapping(value = "notice/queryObject")
    ApiResponseResult queryObject(@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id);


    /**
     * 批量新增图片
     * @param vo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "pic/addPicList")
    ApiResponseResult addPicList(List<PicEntity> vo) throws Exception;

    /**
     * 删除图片
     * @param vo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "pic/deletePicDelFlag")
    ApiResponseResult deletePicDelFlag(PicEntity vo)throws Exception;

    /**
     *查看图片List
     * @param vo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "pic/queryPicEntityVoList")
    ApiResponseResult queryPicEntityVoList(PicEntity vo)throws Exception;

    /**
     * @Desc: 角色列表
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/22 20:10
     * @return: 返回
     */
    @PostMapping(value = "role/queryList")
    ApiResponseResult queryList(RoleEntity vo) throws Exception;

    /**
     * @Desc: 获取角色下所有的用户
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/22 20:42
     * @return: 返回
     */
    @PostMapping(value = "role/queryRoleUserList")
    ApiResponseResult queryRoleUserList(RoleVo vo) throws Exception;

    /**
     * @Desc: 更新个人信息加用户头像
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/23 15:29
     * @return: 返回
     */
    @PostMapping(value = "user/updateUserLogo")
    ApiResponseResult updateLogoUser(UserUpdateLogo vo) throws Exception;

    /**
     * 获取小区信息
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/community/queryCommunityEntity")
    CommunityEntity queryCommunityEntity(@RequestParam(value = "id", required = true) String id) throws Exception;

    /**
     * @Method: queryHouseInfo
     * @Description: 获取房屋信息
     * @Auter: Mollen
     * @Date: 2020/5/26  15:14
     * @return 
     **/
    @PostMapping(value = "/house/queryHouseInfo")
    HouseEntity queryHouseInfo(@RequestBody HouseEntity vo) ;

    /**
     * @Method: queryBuildInfo
     * @Description: 获取楼栋信息
     * @Auter: Mollen
     * @Date: 2020/5/26  15:14
     * @return
     **/
    @PostMapping(value = "/build/queryBuildInfo")
    BuildEntity queryBuildInfo(@RequestBody BuildEntity vo) ;

    /**
     * @description:saas角色新增
     * @return:
     * @author: 赵进华
     * @time: 2020/7/2 11:00
     */
    @PostMapping(value = "/role/addSaasRole")
    ApiResponseResult addSaasRole(@RequestBody SaasRoleVo vo);

    /**
     * @description:删除角色
     * @return:
     * @author: 赵进华
     * @time: 2020/7/2 15:03
     */
    @GetMapping(value = "/role/delete")
    ApiResponseResult delete(@RequestParam(value = "id", required = true) String id);

    /**
     * @description:saas审核，新增公司，新增管理员，管理员绑定角色
     * @return:
     * @author: 赵进华
     * @time: 2020/7/7 14:28
     */
    @PostMapping(value = "/company/addTenant")
    ApiResponseResult addTenant(@RequestBody AddSaasVo vo);

    @PostMapping(value = "/house/queryExportList")
    List<LinkedHashMap<String, String>> queryExportList(@RequestBody Map<String, Object> params);

    @PostMapping(value = "/house/addHouseUser")
    ApiResponseResult addHouseUser(@RequestBody MyHouseEntity myHouseEntity);

    @PostMapping(value = "/house/deleteHouseUser")
    ApiResponseResult deleteHouseUser(@RequestBody HouseDeleteParamVo houseDeleteParamVo);

    /**
     * 根据公司id获取项目信息
     * @param
     * @return
     * @throws Exception
     */
    @GetMapping(value = "community/inner/getCommunityInfoByCompanyId")
    List<CommunityDto> getCommunityInfo() throws Exception;

    @PostMapping(value = "/customer/addCustomerHouseInfo")
    ApiResponseResult addCustomerHouseInfo(@RequestBody CertificationHouseAddVo vo) throws Exception;
}
