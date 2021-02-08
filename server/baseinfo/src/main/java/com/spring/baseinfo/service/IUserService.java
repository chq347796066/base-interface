package com.spring.baseinfo.service;

import com.spring.base.entity.baseinfo.UserEntity;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.vo.baseinfo.user.*;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.buiness.myhouse.MyHouseUpdateVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 用户信息业务接口类
 */
public interface IUserService extends IBaseService<UserEntity, String> {

    /**
     * 新增用户信息
     *
     * @param vo
     * @return
     * @throws Exception
     * @author 作者：ZhaoJinHua
     * @version 创建时间：2020-03-31 19:02:26
     */
    ApiResponseResult addUser(UserAddVo vo) throws Exception;

    /**
     * @description:app注册
     * @return:
     * @author: 赵进华
     * @time: 2020/4/9 16:09
     */
    ApiResponseResult appRegister(LoginVo loginVo) throws Exception;

    /**
     * 更用户信息
     *
     * @param vo
     * @return
     * @throws Exception
     * @author 作者：ZhaoJinHua
     * @version 创建时间：2020-03-31 19:02:26
     */
    ApiResponseResult updateUser(UserUpdateVo vo) throws Exception;

    /**
     * 更新用户角色
     *
     * @param vo
     * @return
     * @throws Exception
     */
    ApiResponseResult updateUserRole(UpadateUserRoleVo vo) throws Exception;


    /**
     * @description:导入数据
     * @return:
     * @author: 赵进华
     * @time: 2020/4/2 17:46
     */
    ApiResponseResult importExcel(HttpServletRequest req, MultipartFile file) throws Exception;


    /**
     * @description:修改用户密码
     * @return:
     * @author: 赵进华
     * @time: 2020/4/9 16:40
     */
    ApiResponseResult modifyPassword(LoginVo loginVo) throws Exception;

    /**
     * @description:忘记用户密码
     * @return:
     * @author: 赵进华
     * @time: 2020/4/9 16:40
     */
    ApiResponseResult forgetPassword(LoginVo loginVo) throws Exception;

    /**
     * @description:修改账号状态
     * @return:
     * @author: 吕文祥
     * @time: 2020/4/9 16:40
     */
    ApiResponseResult modifyAccountStatus(UserAccountStatusVo vo) throws Exception;

    /**
     * @description:重置密码
     * @return:
     * @author: 吕文祥
     * @time: 2020/4/9 16:40
     */
    ApiResponseResult resetPassword(String id) throws Exception;

    /**
     * 根据条件分页查询业主app用户
     *
     * @param requestPageVO
     * @return
     * @throws Exception
     */
    ApiResponseResult queryCustomerAppUserPage(RequestPageVO<CustomerAppUserPageVo> requestPageVO) throws Exception;

    /**
     * 查询业主app用户详情
     *
     * @param userId
     * @return
     * @throws Exception
     */
    ApiResponseResult queryCustomerAppUserDetail(String userId) throws Exception;

    /**
     * @param id
     * @Desc: java类作用描述
     * @Author:邓磊
     * @UpdateDate:2020/4/18 15:34
     * @return: ApiResponseResult
     */
    ApiResponseResult deleteUser(String id) throws Exception;

    /**
     * 查询我的房产信息列表
     *
     * @param myHouseEntity
     * @return
     * @throws Exception
     */
    ApiResponseResult queryMyHouseInfoList(MyHouseEntity myHouseEntity) throws Exception;


    /**
     * 查询APP用户绑定房产审核列表
     *
     * @param myHouseEntity
     * @return
     * @throws Exception
     */
    ApiResponseResult queryAppUserBindHouseAuditList(MyHouseEntity myHouseEntity) throws Exception;

    /**
     * 查询APP用户绑定房产审核分页
     *
     * @param requestPageVO
     * @return
     * @throws Exception
     */
    ApiResponseResult queryAppUserBindHouseAuditPage(RequestPageVO<MyHouseEntity> requestPageVO) throws Exception;


    /**
     * 查看APP用户绑定房产审核
     *
     * @param id
     * @return
     * @throws Exception
     */
    ApiResponseResult queryAppUserBindHouseAudit(String id) throws Exception;

    /**
     * 同意或拒绝房屋APP用户绑定房产认证
     *
     * @param vo
     * @param vo
     * @return
     * @throws Exception
     */
    ApiResponseResult approveHouseAuthentication(MyHouseUpdateVo vo) throws Exception;

    /**
     * 导出员工下载模板
     *
     * @param vo
     * @param result
     * @return
     * @throws Exception
     */
    ApiResponseResult expUser(MyHouseUpdateVo vo, BindingResult result) throws Exception;

    /**
     * @param userId
     * @return
     * @throws Exception
     */
    ApiResponseResult queryUserById(String userId) throws Exception;


    /**
     * @param vo
     * @Desc: 更新个人信息加用户头像
     * @Author:邓磊
     * @UpdateDate:2020/4/23 15:39
     */
    ApiResponseResult updateLogoUser(UserUpdateLogo vo) throws Exception;

    /**
     * @param id
     * @Desc: 查看个人信息app端
     * @Author:邓磊
     * @UpdateDate:2020/4/23 16:13
     * @return: 返回
     */
    ApiResponseResult queryLogoUserInfo(String id) throws Exception;


    /**
     * @param vo
     * @Desc: 更换手机号
     * @Author:邓磊
     * @UpdateDate:2020/4/24 9:47
     */
    ApiResponseResult updateUserMobile(UserVo vo) throws Exception;

    /**
     * 查询用户所拥有的角色
     *
     * @param userId
     * @return
     * @throws Exception
     */
    ApiResponseResult getUserHadRoles(String userId) throws Exception;


    /**
     * @param companyId
     * @Desc: 用户消息下载模板
     * @Author:邓磊
     * @UpdateDate:2020/4/26 14:59
     * @return: 返回
     */
    void expUserDemoDownload(HttpServletResponse response, String companyId, String communityId) throws Exception;

    /**
     * @param users
     * @Desc: 用户信息批量导入
     * @Author:邓磊
     * @UpdateDate:2020/4/26 16:29
     * @return: 返回
     */
    ApiResponseResult importUser(List<User> users, String[] tenantCompanyArray, String communityId);


    /**
     * @param requestPageVO
     * @Desc: 根据员工信息条件分页查询
     * @Author:邓磊
     * @UpdateDate:2020/4/28 9:37
     * @return: 返回
     */
    ApiResponseResult queryUserPage(RequestPageVO<UserEntity> requestPageVO) throws Exception;


    /**
     * @param vo
     * @Desc: java类作用描述
     * @Author:邓磊
     * @UpdateDate:2020/4/29 19:34
     * @return: 返回
     */
    ApiResponseResult queryUserVoInfo(UserEntity vo) throws Exception;


    /**
     * @param requestPageVO
     * @Desc: 根据角色用户信息条件分页查询
     * @Author:邓磊
     * @UpdateDate:2020/5/7 9:33
     * @return: 返回
     */
    ApiResponseResult queryRoleUserPage(RequestPageVO<UserEntity> requestPageVO) throws Exception;


    /**
     * @param userEntity
     * @Desc: 公司导出数据信息
     * @Author:邓磊
     * @UpdateDate:2020/5/14 17:17
     * @return: 返回
     */
    void exportUserEntityInfo(UserEntity userEntity) throws Exception;


    ApiResponseResult selectUserRole(RequestPageVO<UserEntity> requestPageVO);

    /**
     * @description:获取岗位用户列表
     * @return:
     * @author: 赵进华
     * @time: 2021/1/8 17:40
     */  
	ApiResponseResult getUserJobList() throws Exception;
    /**
     * @description:获取岗位用户列表
     * @return:
     * @author: 赵进华
     * @time: 2021/1/8 17:40
     */
	ApiResponseResult getUserJobListByCommunityId(List<String> communityIds) throws Exception;


}
