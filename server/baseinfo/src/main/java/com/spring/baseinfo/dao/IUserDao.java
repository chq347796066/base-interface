package com.spring.baseinfo.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.UserEntity;
import com.spring.base.entity.baseinfo.UserRoleEntity;
import com.spring.base.vo.baseinfo.user.*;
import com.spring.baseinfo.vo.UserJobVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 用户信息Dao
 */
@Mapper
public interface IUserDao extends BaseDao<UserEntity> {

    /**
     * 根据用户名和密码查询用户
     * @param userId
     * @param password
     * @return
     * @throws Exception
     * @author 作者：赵进华
     * @version 创建时间：2019年5月21日 下午3:07:32
     */
    LoginReturnVo getUserByUserIdPassword(@Param("userId") String userId, @Param("password") String password, @Param("userType") String userType) throws Exception;


    /**
     * @description:批量新增用户角色
     * @return:
     * @author: 赵进华
     * @time: 2020/3/18 16:07
     */
    Integer addListUserRole(List<UserRoleEntity> list) throws Exception;

    /**
     * @description:根据用户id删除用户角色
     * @return:
     * @author: 赵进华
     * @time: 2020/4/3 10:45
     */
    Integer deleteUserRole(String userId) throws Exception;

    /**
     * @description:修改用户密码
     * @return:
     * @author: 赵进华
     * @time: 2020/4/9 16:47
     */
    int modifyPassword(@Param("userCode") String userCode, @Param("password") String password) throws Exception;

    /**
     * @description:根据id重置用户密码
     * @return:
     * @author: 赵进华
     * @time: 2020/6/15 13:55
     */
    int modifyPasswordById(@Param("id") String id, @Param("password") String password) throws Exception;

    /**
     * @description:修改登录时间
     * @return:
     * @author: 吕文祥
     * @time: 2020/4/9 16:47
     */
    int modifyLoginTimeSourceChannel(@Param("userCode") String userCode, @Param("loginTime") Date loginTime, @Param("sourceChannel") String sourceChannel) throws Exception;


    /**
     * @description:修改账号状态
     * @return:
     * @author: 吕文祥
     * @time: 2020/4/9 16:47
     */
    int modifyAccountStatus(@Param("enableStatusFlag") Integer enableStatusFlag, @Param("id") String id) throws Exception;

    /**
     * @description:
     * @return:
     * @author: 吕文祥
     * @time: 2020/4/9 16:47
     */
    UserEntityResponseVO queryUserById(@Param("id") String id) throws Exception;

    /**
     * @Desc: 根据条件查询物业管理系统，系统管理，用户管理业主APP用户列表
     * @param entity
     * @Author:邓磊
     * @UpdateDate:2020/5/7 16:13
     * @return: 返回
     */
    List<UserEntityResponseVO> queryCustomerAppUserList(CustomerAppUserPageVo entity) throws Exception;

    /**
     * @Desc: 根据物业管理系统，系统管理，用户管理业主APP用户详情
     * @param userId
     * @Author:邓磊
     * @UpdateDate:2020/5/7 16:13
     * @return: 返回
     */
    UserAppMyHouseVo queryCustomerAppUserDetail(@Param("userId") String userId) throws Exception;
    /**
     * @Desc:    删除用户
     * @param userEntity
     * @Author:邓磊
     * @UpdateDate:2020/4/18 15:42
     * @return: int
     */
    int deleteUser(UserEntity userEntity);

    /**
     * 查询用户所拥有的角色
     * @param userId
     * @return
     */
    List<UserRoleEntity> getUserHadRoles(@Param("userId") String userId);

    /**
     * 逻辑删除用户角色中间表
     * @param userId
     * @return
     */
    int updateUserRoleByUserId(@Param("userId") String userId);


    /**
     * @Desc:  更改个人信息的数据头像
     * @param updateUserLogo
     * @Author:邓磊
     * @UpdateDate:2020/4/23 16:04
     * @return: 返回
     */
    Integer updateUserLogo(UserUpdateLogo updateUserLogo);

    /**
     * @Desc:   查看个人信息APP端
     * @param id
     * @Author:邓磊
     * @UpdateDate:2020/4/23 16:24
     * @return: 返回
     */
    UserUpdateLogo queryLogoUserInfo(@Param("id") String id);
    
    /**
     * @Desc:   查询账号是否存在
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/24 10:08
     * @return: 返回
     */

    List<UserVo> queryUserMobile(UserVo vo);

    /**
     * @Desc:  更新手机号以及账号
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/24 10:19
     * @return: 返回
     */
     Integer updateUserMobile(UserVo vo);


     /**
      * @Desc: 去重手机号身份证号唯一
      * @param userEntity
      * @Author:邓磊
      * @UpdateDate:2020/4/26 17:50
      * @return: 返回
      */
    UserEntity queryIdCardMobile(UserEntity userEntity);

    /**
     * @Desc:  员工信息批量新增
     * @param list
     * @Author:邓磊
     * @UpdateDate:2020/4/26 18:16
     * @return: 返回
     */
    @Override
    Integer addList(@Param("list") List<UserEntity> list);

    /**
     * @description:
     * @return:
     * @author: 吕文祥
     * @time: 2020/4/9 16:47
     */
    UserEntityResponseVO queryUser(@Param("id") String id) throws Exception;


    /**
     * @Desc:    员工用户列表分页查询
     * @param userEntity
     * @Author:邓磊
     * @UpdateDate:2020/4/28 10:02
     * @return: 返回
     */
   List<UserEntityResponseVO> queryUserList(UserEntity userEntity);


    /**
     * @Desc:    员工用户列表分页查询
     * @param userEntity
     * @Author:邓磊
     * @UpdateDate:2020/4/28 10:02
     * @return: 返回
     */
    List<UserEntityResponseVO> queryUserInfo(UserEntity userEntity);

   /**
    * @Desc:     员工用户信息详情
    * @param userEntity
    * @Author:邓磊
    * @UpdateDate:2020/4/29 19:30
    * @return: 返回
    */
    UserEntityResponseVO queryUserVoInfo(UserEntity userEntity);


    /**
     * @Desc:    角色用户中间表查询用户信息勿动
     * @param updateLogo
     * @Author:邓磊
     * @UpdateDate:2020/5/6 10:31
     * @return: UserUpdateLogo
     */
    UserUpdateLogo queryRoleInfoUserList(UserUpdateLogo updateLogo);

    /**
     * @Desc:  用户新增
     * @param userEntity
     * @Author:邓磊
     * @UpdateDate:2020/5/6 20:36
     * @return: 返回
     */
    Integer inserUser(UserEntity userEntity);

    /**
     * @Desc:   用户信息修改
     * @param userEntity
     * @Author:邓磊
     * @UpdateDate:2020/5/6 20:51
     * @return: 返回
     */
    Integer  updateUser(UserEntity userEntity);

    /**
     * @Desc: 用户角色分页
     * @param userEntity
     * @Author:邓磊
     * @UpdateDate:2020/5/7 10:06
     * @return: 返回
     */
    List<UserEntityResponseVO> queryRoleUserList(UserEntity userEntity);


    /**
     * @Desc:  用户手机号生成密码去重
     * @param userEntity
     * @Author:邓磊
     * @UpdateDate:2020/5/7 17:28
     * @return: 返回
     */
    List<UserEntity> queryMobileList(UserEntity userEntity);

    /**
     * @description:saas更新租户下用户角色id为购买的应用角色id
     * @return:
     * @author: 赵进华
     * @time: 2020/7/15 10:55
     */
    int updateTenantRole(@Param("tenantId") String tenantId, @Param("roleId") String roleId) throws Exception;

    /**
     * @description:升级应用租户管理员新绑定升级的角色
     * @return:
     * @author: 赵进华
     * @time: 2020/7/15 14:03
     */
    int upgradeUserRole(@Param("tenantId") String tenantId, @Param("roleId") String roleId) throws Exception;

    /**
     * @description:获取岗位用户列表
     * @return:
     * @author: 赵进华
     * @time: 2021/1/8 17:40
     */
    List<UserJobVo> getUserJobList() throws Exception;

    /**
     * @description:获取岗位用户列表
     * @return:
     * @author: 赵进华
     * @time: 2021/1/8 17:40
     */
    List<UserJobVo> getUserJobListByCommunityId(@Param("communityIds")List<String>communityIds) throws Exception;



}
