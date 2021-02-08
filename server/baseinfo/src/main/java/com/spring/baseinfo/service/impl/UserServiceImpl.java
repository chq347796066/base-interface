package com.spring.baseinfo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.*;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.company.CompanyUpdateVo;
import com.spring.base.vo.baseinfo.user.*;
import com.spring.base.vo.buiness.myhouse.MyHouseUpdateVo;
import com.spring.baseinfo.dao.*;
import com.spring.baseinfo.service.ICommonService;
import com.spring.baseinfo.service.ICompanyService;
import com.spring.baseinfo.service.ICurrentUserInfoService;
import com.spring.baseinfo.service.IUserService;
import com.spring.baseinfo.vo.UserJobVo;
import com.spring.common.StringUtil;
import com.spring.common.constants.Constants;
import com.spring.common.constants.MessageCode;
import com.spring.common.feign.client.MaintenanceFeignClient;
import com.spring.common.page.RequestPageVO;
import com.spring.common.redis.RedisCacheUtils;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.aes.AesEncryptUtil;
import com.spring.common.util.excel.EasyExcelUtils;
import com.spring.common.util.excel.ExceUtils;
import com.spring.common.util.id.UUIDFactory;
import com.spring.common.util.md5.Md5Util;
import com.spring.common.util.random.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 用户信息业务接口实现类
 */
@Slf4j
@Service("userService")
public class UserServiceImpl<ICustomerDaoDao> extends BaseServiceImpl<UserEntity, String> implements IUserService {

    @Autowired
    private IUserDao userDao;


    @Autowired
    private IPicDao picDao;

    @Autowired
    private IUserRoleDao userRoleDao;

    @Autowired
    private ICommunityDao communityDao;

    @Autowired
    private ICompanyDao companyDao;

    @Autowired
    private ICustomerDao customerDao;

    @Autowired
    private IHouseDao houseDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private RedisCacheUtils redisUtils;

    @Autowired
    private ICurrentUserInfoService currentUserInfoService;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private ICommonService commonService;


    @Autowired
    private MaintenanceFeignClient maintenanceFeignClient;

    @Override
    public BaseDao getBaseMapper() {
        return userDao;
    }

    /**
     * 新增用户信息
     *
     * @param vo
     * @return
     * @throws Exception
     * @author 作者：ZhaoJinHua
     * @version 创建时间：2020-03-31 19:02:26
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public ApiResponseResult addUser(UserAddVo vo) throws Exception {
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
//        String tenantId=v;
        //判断用户是否选租户和公司
        /*if (vo.getTenantCompanyArray() == null ) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("请填写公司！");
            return result;
        }*/
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(vo, entity);
        //判断用户是否已注册
        UserEntity userParam = new UserEntity();
        userParam.setMobile(entity.getMobile());
        List<UserEntity> userList = userDao.queryMobileList(userParam);
        if (userList.size() > 0) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("你填写的联系电话已存在，请核实后输入！");
            return result;
        }

        //1平台管理员,2物业公司管理员
        String platAdmin="1";
        String propertyAdmin="2";
        String passWord=null;
        if (platAdmin.equals(vo.getUserType())) {
            //电话号码后６为数
            passWord=entity.getMobile().substring(entity.getMobile().length() - 6);
            entity.setPassword(Md5Util.md5Encode(passWord));
            entity.setUserCode(entity.getMobile());
            //用户状态(1:启用或2:禁用
            entity.setEnableStatusFlag(1);
            //用户类型(1平台管理员,2物业公司管理员
            entity.setUserType(platAdmin);
        } else {
            //电话号码后６为数
            passWord=entity.getMobile().substring(entity.getMobile().length() - 6);
            entity.setPassword(Md5Util.md5Encode(passWord));
            entity.setUserCode(entity.getMobile());
            //用户类型(1平台管理员,2物业公司管理员  员工信息新增userType2为物业
            entity.setUserType(propertyAdmin);
            //用户状态(1:启用或2:禁用
            entity.setEnableStatusFlag(1);
        }
        //拆分租户公司数组
        Integer integer=2;
        if (vo.getTenantCompanyArray() != null) {
            if(vo.getTenantCompanyArray().length==1)
            {
                entity.setCompanyId(vo.getTenantCompanyArray()[0]);
            }
            else if(vo.getTenantCompanyArray().length==integer){
                entity.setCompanyId(vo.getTenantCompanyArray()[1]);
            }
        }

        entity.setTenantId(RequestUtils.getTenantId());
        //entity.setStatus(Constants.Status.ENABLE);
        entity.setCreateUser(RequestUtils.getUserId());
        entity.setCreateDate(new Date());

        int updateNum = 0;
        //判断是否有待注册的用户
        userParam.setHouseHoldType(Constants.HouseHoldType.WEIZUCE);
        List<UserEntity> userWaitList = userDao.queryMobileList(userParam);
        //如果没有,则作为游客新增
        if (userWaitList.size() <= 0) {
            String userId = UUIDFactory.createId();
            entity.setId(userId);
            entity.setHouseHoldType(Constants.HouseHoldType.YOUKE);
            entity.setTenantId(RequestUtils.getTenantId());
            entity.setCreateUser(RequestUtils.getUserId());
            entity.setCreateDate(new Date());
            entity.setJobId("5");
            updateNum = userDao.inserUser(entity);
        } else {    //如果有,则修改已注册的业主
            UserEntity userWait = userWaitList.get(0);
            entity.setId(userWait.getId());
            entity.setHouseHoldType(Constants.HouseHoldType.YEZU);
            entity.setTenantId(userWait.getTenantId());
            entity.setCreateDate(userWait.getCreateDate());
            entity.setCreateUser(userWait.getCreateUser());
            entity.setModifyUser(RequestUtils.getUserId());
            entity.setModifyDate(new Date());

            updateNum = userDao.updateUser(entity);
        }

        // 新增
        if (updateNum > 0) {
            //批量插入用户角色
            List<UserRoleEntity> userRoleList = new ArrayList<>();
            if (StringUtils.isNotEmpty(vo.getRoleId())) {
                String[] roleArray = vo.getRoleId().split(",");
                if (roleArray != null && roleArray.length > 0) {
                    for (int i = 0; i < roleArray.length; i++) {
                        UserRoleEntity userRole = new UserRoleEntity();
                        userRole.setId(UUIDFactory.createId());
                        userRole.setRoleId(roleArray[i]);
                        userRole.setUserId(entity.getId());
                        userRole.setStatus(1);
                        userRole.setDelFlag(0);
                        userRole.setTenantId(entity.getTenantId());
                        userRole.setCreateUser(entity.getCreateUser());
                        userRole.setCreateDate(new Date());
                        userRoleList.add(userRole);
                    }
                }
                if (CollectionUtils.isNotEmpty(userRoleList)) {
                    int roleNo = userDao.addListUserRole(userRoleList);
                    if (updateNum > 0 && roleNo > 0) {
                        result.setCode(MessageCode.SUCCESS);
                        result.setMsg("成功");
                    } else {
                        result.setCode(MessageCode.FAIL);
                        result.setMsg("新增失败");
                    }
                }
            }
            result.setCode(MessageCode.SUCCESS);
            result.setMsg("成功");
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg("新增失败");
        }
        return result;
    }

    /**
     * 更新用户信息
     *
     * @param vo
     * @return
     * @throws Exception
     * @author 作者：ZhaoJinHua
     * @version 创建时间：2020-03-31 19:02:26
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public ApiResponseResult updateUserRole(UpadateUserRoleVo vo) throws Exception {
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        int no = 0;
        String[] userArray = vo.getUserId().split(",");

        if(userArray!=null && userArray.length>0){
            UserEntity userEntity = new UserEntity();
            for (int userIdex = 0; userIdex < userArray.length; userIdex++) {
                userEntity.setId(userArray[userIdex]);
                userEntity.setRoleId(vo.getRoleId());
                no = userDao.updateById(userEntity);
                //根据用户id删除用户角色
                userDao.deleteUserRole(userArray[userIdex]);
                if (StringUtils.isNotEmpty(vo.getRoleId())) {
                    //批量插入用户角色
                    List<UserRoleEntity> userRoleList = new ArrayList<>();
                    String[] roleArray = vo.getRoleId().split(",");
                    if (roleArray != null && roleArray.length > 0) {
                        for (int roleIndex = 0; roleIndex < roleArray.length; roleIndex++) {
                            UserRoleEntity userRole = new UserRoleEntity();
                            userRole.setId(UUIDFactory.createId());
                            userRole.setRoleId(roleArray[roleIndex]);
                            userRole.setUserId(userArray[userIdex]);
                            //1：启用,2:禁用
                            userRole.setStatus(1);
                            userRoleList.add(userRole);
                        }
                    }
                    //通过userId逻辑删除用户角色表
                    int num = userDao.updateUserRoleByUserId(userArray[userIdex]);
                    int roleNo = userDao.addListUserRole(userRoleList);
                    if (no > 0 && roleNo > 0) {
                        result.setCode(MessageCode.SUCCESS);
                        result.setMsg("成功");
                    } else {
                        result.setCode(MessageCode.FAIL);
                        result.setMsg("失败");
                    }
                }
            }
        }
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("成功");
        return result;
    }
    /**
     * 更新用户信息
     *
     * @param vo
     * @return
     * @throws Exception
     * @author 作者：ZhaoJinHua
     * @version 创建时间：2020-03-31 19:02:26
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public ApiResponseResult updateUser(UserUpdateVo vo) throws Exception {
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(vo, entity);
        //判断用户是否已注册
        UserEntity userParam = new UserEntity();
        userParam.setId(entity.getId());
        userParam.setMobile(entity.getMobile());
        List<UserEntity> userList = userDao.queryMobileList(userParam);
        if (userList.size() > 0) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("你填写的联系电话已存在，请核实后输入！");
            return result;
        }

        //1平台管理员,2物业公司管理员
        String passWord=null;
        boolean isTrue=StringUtils.isNotEmpty(vo.getRoleId()) && ("2".equals(vo.getUserType()) || "1".equals(vo.getUserType()));
        if (isTrue) {
            passWord= RandomUtil.getRandomCode(6);
            entity.setPassword(Md5Util.md5Encode(passWord));
            entity.setUserCode(entity.getMobile());
        }
        //拆分租户公司数组
        /*  if (vo.getTenantCompanyArray() != null) {
            if(vo.getTenantCompanyArray().length==1)
            {
                entity.setCompanyId(vo.getTenantCompanyArray()[0]);
            }
            else if(vo.getTenantCompanyArray().length==2){
                entity.setCompanyId(vo.getTenantCompanyArray()[1]);
            }
        }*/
        int no = userDao.updateUser(entity);
        if (no > 0) {
            //根据用户id删除用户角色
            userDao.deleteUserRole(vo.getId());
            if (StringUtils.isNotEmpty(vo.getRoleId())) {
                //批量插入用户角色
                List<UserRoleEntity> userRoleList = new ArrayList<>();
                String[] roleArray = vo.getRoleId().split(",");
                if (roleArray != null && roleArray.length > 0) {
                    for (int i = 0; i < roleArray.length; i++) {
                        UserRoleEntity userRole = new UserRoleEntity();
                        userRole.setId(UUIDFactory.createId());
                        userRole.setRoleId(roleArray[i]);
                        userRole.setUserId(vo.getId());
                        //1：启用,2:禁用
                        userRole.setStatus(1);
                        userRole.setDelFlag(0);
                        userRole.setTenantId(RequestUtils.getTenantId());
                        userRole.setCreateUser(RequestUtils.getUserId());
                        userRole.setCreateDate(new Date());
                        userRoleList.add(userRole);
                    }
                }
                //通过userId逻辑删除用户角色表
                int num = userDao.updateUserRoleByUserId(vo.getId());
                log.info("updateUserRoleByUserId:" + num);
                int roleNo = userDao.addListUserRole(userRoleList);
                if (no > 0 && roleNo > 0) {
                    result.setCode(MessageCode.SUCCESS);
                    result.setMsg("成功");
                } else {
                    result.setCode(MessageCode.FAIL);
                    result.setMsg("失败");
                }
            }
            //注册成功发送短信验证码
            if("2".equals(vo.getUserType())){
                commonService.sendMessage(entity.getMobile(),passWord,1);
            }
            log.info("密码为 {}:",passWord);
            result.setCode(MessageCode.SUCCESS);
            result.setMsg("成功");
        }
        return result;
    }

    /**
     * @description:导入数据
     * @return:
     * @author: 赵进华
     * @time: 2020/4/2 17:46
     */
    @Override
    public ApiResponseResult importExcel(HttpServletRequest req, MultipartFile file) throws Exception {
        List<List<String[]>> list = ExceUtils.importExcel(req, file);
        List<UserEntity> importList = new ArrayList<>();
        try {
            for (List<String[]> listRow : list) {
                int i = 0;
                for (String[] strings : listRow) {
                    // 过滤掉表格第一行标题
                    if (i > 0) {
                        // 构建对象
                        UserEntity entity = new UserEntity();
                        entity.setId(UUIDFactory.createId());
                        entity.setStatus(1);
                        // 对象加入列表
                        importList.add(entity);
                    }
                    i = i + 1;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return createFailResult("系统错误，请联系管理员");
        }

        if (importList.size() > 0) {
            try {
                // 批量插入数据
                userDao.addList(importList);
                return createSuccessResult(importList);
            } catch (Exception e) {
                log.error("插入数据库失败", e);
            }
            return createFailResult("导入失败");
        } else {
            return createSuccessResult(0);
        }
    }


    /**
     * @description:app注册
     * @return:
     * @author: 赵进华
     * @time: 2020/4/9 16:09
     */
    @Override
    public ApiResponseResult appRegister(LoginVo loginVo) throws Exception {
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        // AES解密成json字符串
        String json = AesEncryptUtil.desEncrypt(loginVo.getSecretString()).trim();
        // json字符串返序列化成对象
        AppRegisterVo vo = JSON.parseObject(json, AppRegisterVo.class);
        if (vo == null) {
            result.setCode(MessageCode.FAIL);
            result.setMsg(MessageCode.FAIL_TEXT);
            return result;
        }
        // 存redis中的key
        String telCodeKey = Constants.RedisPrefix.TEL_MSG + vo.getVerifyCode();
        if (!redisUtils.exists(telCodeKey)) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("短信验证码已过期");
        }
        //判断用户是否已注册
        UserEntity userParam = new UserEntity();
        userParam.setMobile(vo.getTel());
        List<UserEntity> userList = userDao.queryMobileList(userParam);
        if (userList.size() > 0) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("你的手机号已经注册！");
            return result;
        }

        UserEntity entity = new UserEntity();

        entity.setUserCode(vo.getTel());
        entity.setMobile(vo.getTel());
        entity.setPassword(Md5Util.md5Encode(vo.getPassword()));
        entity.setUserType(Constants.UserType.OWNER_APP);
        //用户状态(1:启用或2:禁用)
        entity.setEnableStatusFlag(Constants.Status.ENABLE);

        //判断是否有待注册的用户
        int updateNum = 0;
        userParam.setHouseHoldType(Constants.HouseHoldType.WEIZUCE);
        List<UserEntity> userWaitList = userDao.queryMobileList(userParam);
        //如果没有,则作为游客新增
        if (userWaitList.size() <= 0) {
            String userId = UUIDFactory.createId();
            entity.setId(userId);
            entity.setHouseHoldType(Constants.HouseHoldType.YOUKE);
            entity.setTenantId(RequestUtils.getTenantId());
            entity.setCreateUser(RequestUtils.getUserId());
            entity.setCreateDate(new Date());

            updateNum = userDao.inserUser(entity);
        } else {    //如果有修改已注册的业主
            UserEntity userWait = userWaitList.get(0);
            entity.setId(userWait.getId());
            entity.setHouseHoldType(Constants.HouseHoldType.YEZU);
            entity.setTenantId(userWait.getTenantId());
            entity.setCreateDate(userWait.getCreateDate());
            entity.setCreateUser(userWait.getCreateUser());
            entity.setModifyUser(RequestUtils.getUserId());
            entity.setModifyDate(new Date());

            updateNum = userDao.updateUser(entity);
        }

//        entity.setTenantId(RequestUtils.getTenantId());
//        entity.setId(UUIDFactory.createId());
//        entity.setCreateUser(RequestUtils.getUserId());
//        entity.setCreateDate(new Date());
//        // 新增
//        int no = userDao.insert(entity);
        {
            result.setCode(MessageCode.SUCCESS);
            result.setMsg("成功");
        }

//        //查看用户是否有房产，如有房产则新增房产数据
//        HouseEntity houseEntity = new HouseEntity();
//        houseEntity.setOwnerMobile(vo.getTel());
//        List<HouseEntity> houseEntityList = (List<HouseEntity>) baseInfoFeignCilnet.queryList(houseEntity).getData();
//        if (houseEntityList != null && houseEntityList.size() > 0) {
//            myHouseFeignCilnet.batchAddHouseUserInfo(houseEntityList);
//        }
        return result;
    }

    /**
     * @description:修改用户密码
     * @return:
     * @author: 赵进华
     * @time: 2020/4/9 16:41
     */
    @Override
    public ApiResponseResult modifyPassword(LoginVo loginVo) throws Exception {
        log.info("user modify password begin vo:" + loginVo.toString());
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        // AES解密成json字符串
        String json = AesEncryptUtil.desEncrypt(loginVo.getSecretString()).trim();
        // json字符串返序列化成对象
        ModifyPasswordVo vo = JSON.parseObject(json, ModifyPasswordVo.class);
        if (vo == null) {
            result.setCode(MessageCode.FAIL);
            result.setMsg(MessageCode.FAIL_TEXT);
            return result;
        }
        // 存redis中的key
        String telCodeKey = Constants.RedisPrefix.TEL_MSG + vo.getVerifyCode();
        if (!redisUtils.exists(telCodeKey)) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("短信验证码已过期");
        }
        // 密码
        String password = Md5Util.md5Encode(vo.getPassword());
        int no = userDao.modifyPassword(vo.getUserCode(), password);
        if (no > 0) {
            result.setCode(MessageCode.SUCCESS);
            result.setMsg(MessageCode.SUCCESS_TEXT);
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg(MessageCode.FAIL_TEXT);
        }
        return result;
    }

    /**
     * @description:忘记用户密码
     * @return:
     * @author: 赵进华
     * @time: 2020/4/9 16:41
     */
    @Override
    public ApiResponseResult forgetPassword(LoginVo loginVo) throws Exception {
        log.info("user modify password begin vo:" + loginVo.toString());
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        // AES解密成json字符串
        String json = AesEncryptUtil.desEncrypt(loginVo.getSecretString()).trim();
        // json字符串返序列化成对象
        ForgetPasswordVo vo = JSON.parseObject(json, ForgetPasswordVo.class);
        if (vo == null) {
            result.setCode(MessageCode.FAIL);
            result.setMsg(MessageCode.FAIL_TEXT);
            return result;
        }
        // 存redis中的key
        String telCodeKey = Constants.RedisPrefix.TEL_MSG + vo.getUserCode();
        if (!redisUtils.exists(telCodeKey)) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("短信验证码已过期");
        }
        // 密码
        String password = Md5Util.md5Encode(vo.getPassword());
        int no = userDao.modifyPassword(vo.getUserCode(), password);
        if (no > 0) {
            result.setCode(MessageCode.SUCCESS);
            result.setMsg(MessageCode.SUCCESS_TEXT);
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg(MessageCode.FAIL_TEXT);
        }
        return result;
    }

    @Override
    public ApiResponseResult modifyAccountStatus(UserAccountStatusVo vo) throws Exception {
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();

        Integer enableStatusFlag = null;
        if (vo.getEnableStatusFlag() == 1) {
            enableStatusFlag = 2;
        }

        if (vo.getEnableStatusFlag() == 2) {
            enableStatusFlag = 1;
        }
        int no = userDao.modifyAccountStatus(enableStatusFlag, vo.getId());
        if (no > 0) {
            result.setCode(MessageCode.SUCCESS);
            result.setMsg(MessageCode.SUCCESS_TEXT);
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg(MessageCode.FAIL_TEXT);
        }
        return result;
    }

    @Override
    public ApiResponseResult resetPassword(String id) throws Exception {
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        //通过id,查找用户信息
        UserEntityResponseVO userEntityResponseVO = userDao.queryUser(id);
        String userType = userEntityResponseVO.getUserType();
        UserEntity entity = new UserEntity();
        //平台管理员
        if ("1".equals(userType)) {
            String mobile = userEntityResponseVO.getMobile();
            String password = mobile.substring(mobile.length() - 6);
            entity.setPassword(Md5Util.md5Encode(password));
        }
        //物业人员
        if ("2".equals(userType)) {
            String mobile = userEntityResponseVO.getMobile();
            String password = mobile.substring(mobile.length() - 6);
            entity.setPassword(Md5Util.md5Encode(password));
        }
        int no = userDao.modifyPasswordById(userEntityResponseVO.getId(), entity.getPassword());
        if (no > 0) {
            result.setCode(MessageCode.SUCCESS);
            result.setMsg(MessageCode.SUCCESS_TEXT);
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg(MessageCode.FAIL_TEXT);
        }
        return result;
    }

    /**
     * @param requestPageVO
     * @Desc: 系统管理用户管理　业主APP用户列表
     * @Author:邓磊
     * @UpdateDate:2020/5/7 15:45
     * @return: 返回
     */
    @Override
    public ApiResponseResult queryCustomerAppUserPage(RequestPageVO<CustomerAppUserPageVo> requestPageVO) throws Exception {
        //获取当前登录者所拥有的小区信息
        List<String> communityIds=currentUserInfoService.queryCurrentUserCommunityInfo();
        PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
        requestPageVO.getEntity().setCommunityIds(communityIds);
        List<UserEntityResponseVO> list = userDao.queryCustomerAppUserList(requestPageVO.getEntity());
        PageInfo<UserEntityResponseVO> pageInfo = new PageInfo<UserEntityResponseVO>(list);
        return createSuccessResult(pageInfo);
    }

    /**
     * @param userId
     * @Desc: 系统管理用户管理　业主APP用户列表详情
     * @Author:邓磊
     * @UpdateDate:2020/5/7 15:45
     * @return: 返回
     */
    @Override
    public ApiResponseResult queryCustomerAppUserDetail(String userId) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        UserAppMyHouseVo userAppMyHouseVo = userDao.queryCustomerAppUserDetail(userId);
        if (StringUtils.isNotEmpty(userAppMyHouseVo.getUserLogoId())) {
            PicEntity picVo = new PicEntity();
            picVo.setTableId(userAppMyHouseVo.getUserLogoId());
            List<PicEntity> picEntities = picDao.queryPicEntityList(picVo);
            if (CollectionUtils.isNotEmpty(picEntities)) {
                userAppMyHouseVo.setPicEntityList(picEntities);
            }
        }
        if (StringUtils.isNotEmpty(userAppMyHouseVo.getId())) {
            MyHouseEntity houseEntity = new MyHouseEntity();
            //审核状态(0 待审核 1已通过 2未通过)
            houseEntity.setUserId(userAppMyHouseVo.getId());
            houseEntity.setAuditStatus(1);
            List<MyHouseEntity> myHouseEntities = maintenanceFeignClient.queryUserAppHouseList(houseEntity);
            if (CollectionUtils.isNotEmpty(myHouseEntities)) {
                userAppMyHouseVo.setMyHouseEntityList(myHouseEntities);
            }
        }
        result.setCode(MessageCode.SUCCESS);
        result.setData(userAppMyHouseVo);
        result.setMsg(MessageCode.SUCCESS_TEXT);
        return result;
    }

    /**
     * @param id
     * @Desc: 删除用户
     * @Author:邓磊
     * @UpdateDate:2020/4/18 15:34
     * @return: ApiResponseResult
     */
    @Override
    public ApiResponseResult deleteUser(String id) throws Exception { ;
        int no = userDao.deleteById(id);
        if (no > 0) {
            return createSuccessResult(null);
        }
        return createFailResult();
    }

    @Override
    public ApiResponseResult queryMyHouseInfoList(MyHouseEntity myHouseEntity) throws Exception {
        return maintenanceFeignClient.queryList(myHouseEntity);
    }

    @Override
    public ApiResponseResult queryAppUserBindHouseAuditList(MyHouseEntity myHouseEntity) throws Exception {
        return null;
    }

//    @Override
//    public ApiResponseResult queryAppUserBindHouseAuditPage(RequestPageVO<MyHouseEntity> requestPageVO) throws Exception {
//        return myHouseFeignCilnet.queryPage(requestPageVO);
//    }

    @Override
    public ApiResponseResult queryAppUserBindHouseAuditPage(RequestPageVO<MyHouseEntity> requestPageVO) throws Exception {
        //获取当前登录者所拥有的小区信息
        List<String> communityIds=currentUserInfoService.queryCurrentUserCommunityInfo();
        requestPageVO.getEntity().setCommunityIds(communityIds);
        return maintenanceFeignClient.queryPage(requestPageVO);
    }

    @Override
    public ApiResponseResult queryAppUserBindHouseAudit(String id) throws Exception {
        return maintenanceFeignClient.queryObject(id);
    }

    @Override
    public ApiResponseResult approveHouseAuthentication(MyHouseUpdateVo vo) throws Exception {
        MyHouseEntity myHouseParam = new MyHouseEntity();
        myHouseParam.setId(vo.getId());
        //获得房屋信息
        List<MyHouseEntity> myHouseList = (List<MyHouseEntity>) maintenanceFeignClient.queryList(myHouseParam).getData();
        if(myHouseList.size() > 0) {
            MyHouseEntity myHouseEntity = JSON.parseObject(JSON.toJSONString(myHouseList.get(0)), MyHouseEntity.class);

            //绑定房屋和客户信息
            QueryWrapper<CustomerEntity> queryCustomerWrapper = new QueryWrapper<>();
            queryCustomerWrapper.lambda().eq(CustomerEntity :: getPhone,myHouseEntity.getMobile()).eq(CustomerEntity :: getCertificatesNumber,myHouseEntity.getCertificateNo());
            CustomerEntity queryCustomerEntity = customerDao.selectOne(queryCustomerWrapper);

            String customerId = queryCustomerEntity == null || StringUtils.isEmpty(queryCustomerEntity.getId()) ? UUIDFactory.createId() : queryCustomerEntity.getId();
            if (queryCustomerEntity == null) {      //如果没有绑定客户信息，则绑定
                CustomerEntity customerEntity = new CustomerEntity();
                customerEntity.setId(customerId);
                customerEntity.setCommunityId(myHouseEntity.getCommunityId());
                customerEntity.setCommunityName(myHouseEntity.getCommunityName());
                customerEntity.setCustomerType(myHouseEntity.getIdentityType()+"");
                customerEntity.setCustomerName(myHouseEntity.getUserName());
                customerEntity.setCertificatesType(myHouseEntity.getCertificateType());

                customerEntity.setCertificatesNumber(myHouseEntity.getCertificateNo());
                customerEntity.setPhone(myHouseEntity.getMobile());
                customerEntity.setCreateDate(new Date());
                customerEntity.setCreateUser(myHouseEntity.getUserId());
                customerDao.insert(customerEntity);
            }

            QueryWrapper<HouseEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(HouseEntity :: getId,myHouseEntity.getHouseId());
            HouseEntity queryHouseEntity = houseDao.selectOne(queryWrapper);
            if (queryHouseEntity != null) {     //房屋信息不为空
                queryHouseEntity.setCustomerId(customerId);
                queryHouseEntity.setOwnerMobile(myHouseEntity.getMobile());
                queryHouseEntity.setOwnerName(myHouseEntity.getUserName());
                queryHouseEntity.setOwnerCard(myHouseEntity.getCertificateNo());
                queryHouseEntity.setModifyDate(new Date());
                queryHouseEntity.setModifyUser(myHouseEntity.getUserId());
                houseDao.updateById(queryHouseEntity);
            }

        }

        return maintenanceFeignClient.update(vo);
    }

    @Override
    public ApiResponseResult expUser(MyHouseUpdateVo vo, BindingResult result) throws Exception {
        CompanyUpdateVo companyUpdateVo = new CompanyUpdateVo();
        companyUpdateVo.setId(vo.getCompanyId());
        //通过公司ID，查找公司名称
        CompanyEntity companyEntity = companyDao.queryCompanyEntity(companyUpdateVo);

        if (companyEntity == null || StringUtils.isEmpty(companyEntity.getCompanyName())) {
            return createFailResult("公司不存在");
        }
        //通过公司ID和小区ID,查小区表
        Map<String, Object> mapPara = new HashMap<String, Object>(16);
        mapPara.put("companyId", vo.getCompanyId());
        mapPara.put("communityId", vo.getCommunityId());
        String communityName = communityDao.queryCommunityName(mapPara);
        if (StringUtils.isEmpty(communityName)) {
            return createFailResult("小区不存在");
        }
        return createSuccessResult(null);
    }

    @Override
    public ApiResponseResult queryUserById(String userId) throws Exception {
        ApiResponseResult apiResponseResult = new ApiResponseResult();
        List<UserRoleEntity> roleList = userDao.getUserHadRoles(userId);
        UserEntityResponseVO userEntity = userDao.queryUserById(userId);
        Map<String, Object> mapResult = new HashMap<String, Object>(16);
        mapResult.put("userEntity", userEntity);
        mapResult.put("roleList", roleList);

        if (userEntity == null) {
            apiResponseResult.setCode(MessageCode.FAIL);
            apiResponseResult.setMsg(MessageCode.FAIL_TEXT);
        } else {
            apiResponseResult.setCode(MessageCode.SUCCESS);
            apiResponseResult.setMsg("成功");
            apiResponseResult.setData(mapResult);
        }
        return apiResponseResult;
    }

    /**
     * @Desc: 更新个人信息加用户头像
     * @Author:邓磊
     * @UpdateDate:2020/4/23 15:40
     * @return: 返回
     */
    @Override
    public ApiResponseResult updateLogoUser(UserUpdateLogo vo) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        List<PicEntity> picEntityList = vo.getPicEntityList();
        List<PicEntity> list = new ArrayList<PicEntity>();
        if (CollectionUtils.isNotEmpty(picEntityList)) {
            String userLogoId = UUIDFactory.createId();
            vo.setUserLogoId(userLogoId);
            picEntityList.stream().forEach(picEntity -> {
                PicEntity picVo = new PicEntity();
                picVo.setId(UUIDFactory.createId());
                picVo.setTableId(userLogoId);//头像图片ID
                picVo.setDataId(picEntity.getDataId());
                picVo.setName(picEntity.getName());
                picVo.setUrl(picEntity.getUrl());
                picVo.setTenantId(RequestUtils.getUserId());
                picVo.setDelFlag(0);//删除标志（0 未删除 1 已删除）")
                picVo.setCreateUser(RequestUtils.getUserId());
                picVo.setCreateDate(new Date());
                list.add(picVo);
            });
        }
        if (CollectionUtils.isNotEmpty(list)) {
            log.info("头像图片ID" + list);
            picDao.addList(list);
        }
        Integer integer = userDao.updateUserLogo(vo);
        if (integer > 0) {
            UserUpdateLogo userUpdateLogo = userDao.queryLogoUserInfo(vo.getId());
            if (null != userUpdateLogo) {
                if (StringUtils.isNotEmpty(userUpdateLogo.getUserLogoId())) {
                    PicEntity picVo = new PicEntity();
                    picVo.setTableId(userUpdateLogo.getUserLogoId());
                    List<PicEntity> picEntities = picDao.queryPicEntityList(picVo);
                    if (CollectionUtils.isNotEmpty(picEntities)) {
                        userUpdateLogo.setPicEntityList(picEntities);
                    }
                }
            }
            result.setData(userUpdateLogo);
            result.setCode(MessageCode.SUCCESS);
            result.setMsg("成功");
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg("失败");
        }
        return result;
    }

    /**
     * @Desc: 查看个人app端个人信息
     * @Author:邓磊
     * @UpdateDate:2020/4/23 15:40
     */
    @Override
    public ApiResponseResult queryLogoUserInfo(String id) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        UserUpdateLogo userUpdateLogo = userDao.queryLogoUserInfo(id);
        if (null != userUpdateLogo) {
            if (StringUtils.isNotEmpty(userUpdateLogo.getUserLogoId())) {
                PicEntity picVo = new PicEntity();
                picVo.setTableId(userUpdateLogo.getUserLogoId());
                List<PicEntity> picEntities = picDao.queryPicEntityList(picVo);
                if (CollectionUtils.isNotEmpty(picEntities)) {
                    userUpdateLogo.setPicEntityList(picEntities);
                }
            }
        }
        result.setData(userUpdateLogo);
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("成功");
        return result;
    }

    /**
     * @param vo
     * @Desc: 更换手机号
     * @Author:邓磊
     * @UpdateDate:2020/4/24 9:48
     */
    @Override
    public ApiResponseResult updateUserMobile(UserVo vo) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        String telCodeKey = Constants.RedisPrefix.TEL_MSG + vo.getVerifyCode();
        if (!redisUtils.exists(telCodeKey)) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("短信验证码已过期");
        }
        List<UserVo> userVos = userDao.queryUserMobile(vo);
        if (userVos.size() > 0) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("你修改的手机号已经存在,请核对后修改！");
            return result;
        }
        Integer no = userDao.updateUserMobile(vo);
        if (no > 0) {
            result.setCode(MessageCode.SUCCESS);
            result.setMsg("成功");
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg("失败");
        }
        return result;
    }

    @Override
    public ApiResponseResult getUserHadRoles(String userId) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        List<UserRoleEntity> userRoleEntityList = userDao.getUserHadRoles(userId);
        result.setData(userRoleEntityList);
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("成功");
        return result;
    }

    /**
     * @param companyId
     * @Desc: 用户消息下载模板
     * @Author:邓磊
     * @UpdateDate:2020/4/26 15:00
     * @return: 返回
     */
    @Override
    public void expUserDemoDownload(HttpServletResponse response, String companyId, String communityId) throws Exception {
        CommunityEntity vo = new CommunityEntity();
        vo.setId(communityId);
        CommunityEntity entity = communityDao.queryCommunity(vo);
        CompanyEntity userEntity = new CompanyEntity();
        userEntity.setId(companyId);
        List<CompanyEntity> companyEntities = companyDao.queryList(userEntity);
        List<User> userList = new ArrayList<>();
        User uerVo = new User();
        uerVo.setCompanyName(companyEntities.get(0).getCompanyName());
        uerVo.setCommunityName(entity.getCommunityName());
        uerVo.setUserName("员工姓名必填");
        uerVo.setStatus("员工状态填写其一/在职/离职");
        uerVo.setMobile("联系电话必填");
        uerVo.setSex("员工性别必填写其一/男/女");
        uerVo.setBirthday("出生日期非必填");
        uerVo.setIdCard("身份证号码必填");
        uerVo.setNativePlace("籍贯非必填");
        uerVo.setAddress("住址非必填");
        User uerVo1 = new User();
        uerVo1.setCompanyName(companyEntities.get(0).getCompanyName());
        uerVo1.setCommunityName(entity.getCommunityName());
        uerVo1.setUserName("张三");
        uerVo1.setStatus("在职");
        uerVo1.setMobile("13410119798");
        uerVo1.setSex("男");
        uerVo1.setBirthday("1988-08-1");
        uerVo1.setIdCard("440514198312317554");
        uerVo1.setNativePlace("籍贯非必填");
        uerVo1.setAddress("住址非必填");
        userList.add(uerVo);
        userList.add(uerVo1);
        if (userList != null && userList.size() > 0) {
            EasyExcelUtils.exportExcel(userList, null, "员工数据", User.class, "员工信息.xls", response);
        }
    }

    /**
     * @param users
     * @Desc: 用户消息批量导入
     * @Author:邓磊
     * @UpdateDate:2020/4/26 16:30
     */
    @Override
    public ApiResponseResult importUser(List<User> users, String[] tenantCompanyArray, String communityId) {
        ApiResponseResult result = new ApiResponseResult();
        CompanyEntity companyEntity = new CompanyEntity();
        CommunityEntity communityVO = new CommunityEntity();
        List<UserEntity> userEntityList = new ArrayList<>();

        CommunityEntity entityVo = new CommunityEntity();
        entityVo.setId(communityId);
        if (tenantCompanyArray.length==1){
            entityVo.setTenantId(tenantCompanyArray[0]);
        }
        if (tenantCompanyArray.length==2){
            entityVo.setTenantId(tenantCompanyArray[0]);
            entityVo.setCompanyId(tenantCompanyArray[1]);
        }
        CommunityEntity entity1 = communityDao.queryCommunity(entityVo);
        if (CollectionUtils.isNotEmpty(users)) {
            if (StringUtils.isNotEmpty(users.get(0).getCompanyName()) && entity1.getCompanyName().equals(users.get(0).getCompanyName())) {
                CompanyEntity userEntity1 = new CompanyEntity();
                userEntity1.setCompanyName(users.get(0).getCompanyName());
                companyEntity = companyDao.queryCompanyEntityInfo(userEntity1);
            } else {
                result.setCode(MessageCode.FAIL);
                result.setMsg("填写的所属公司,与下拉选择的公司匹配不上,请填写下拉选择的公司");
                result.setData(null);
                return result;
            }
            if (StringUtils.isNotEmpty(users.get(0).getCommunityName()) && entity1.getCommunityName().equals(users.get(0).getCommunityName())) {
                String communityName = users.get(0).getCommunityName();
                CommunityEntity entity = new CommunityEntity();
                entity.setCommunityName(communityName);
                communityVO = communityDao.queryCommunity(entity);
            } else {
                result.setCode(MessageCode.FAIL);
                result.setMsg("填写的所属小区,与下拉选择的公司下的小区匹配不上,请填写下拉选择的公司小区");
                result.setData(null);
                return result;
            }
            int count = 0;
            for (int i = 0; i < users.size(); i++) {
                count++;
                User user = users.get(i);
                ApiResponseResult apiResponseResult = checkCarExecl(count, user, communityVO, companyEntity, entity1);
                if (apiResponseResult.getCode().equals(MessageCode.SUCCESS)) {
                    UserEntity userVo = new UserEntity();
                    userVo.setMobile(user.getMobile());
                    userVo.setIdCard(user.getIdCard());
                    UserEntity userEntity = userDao.queryIdCardMobile(userVo);
                    if (userEntity != null && StringUtils.isNotEmpty(userEntity.getMobile()) && StringUtils.isNotEmpty(userEntity.getIdCard())) {
                        result.setCode(MessageCode.FAIL);
                        result.setMsg("第" + count + "行" + "员工身份证号手机号码已存在,请核实后填写");
                        result.setData(null);
                        return result;
                    } else {
                        UserEntity vo = new UserEntity();
                        vo.setId(UUIDFactory.createId());
                        vo.setCompanyId(entityVo.getCompanyId());
                        vo.setCommunityId(entityVo.getId());
                        vo.setTenantId(entityVo.getTenantId());
                        vo.setStatus(Constants.Status.ENABLE);
                        vo.setCreateUser(entityVo.getTenantId());
                        vo.setCreateDate(new Date());
                        vo.setUserName(user.getUserName());
                        vo.setUserType("2");
                        vo.setEnableStatusFlag(1);
                        //登录开关(1开,2关)
                        vo.setLoginStatus(1);
                        vo.setMobile(user.getMobile());
                        vo.setBirthday(user.getBirthday());
                        vo.setIdCard(user.getIdCard());
                        vo.setAddress(user.getAddress());
                        vo.setNativePlace(user.getNativePlace());
                        if ("男".equals(user.getSex())) {
                            vo.setSex(1);
                        } else {
                            vo.setSex(2);
                        }
                        if ("在职".equals(user.getStatus())) {
                            vo.setStatus(1);
                        } else {
                            vo.setStatus(2);
                        }
                        userEntityList.add(vo);
                    }
                } else {
                    return apiResponseResult;
                }
            }
            if (CollectionUtils.isNotEmpty(userEntityList)) {
                Integer integer = userDao.addList(userEntityList);
                if (integer > 0) {
                    result.setCode(MessageCode.SUCCESS);
                    result.setMsg("成功");
                } else {
                    result.setCode(MessageCode.FAIL);
                    result.setMsg("新增失败");
                }
                return result;
            }
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg("请填写导入的数据");
            result.setData(null);
            return result;
        }
        return result;
    }

    /**
     * @param user
     * @Desc: 用户消息导入校验
     * @Author:邓磊
     * @UpdateDate:2020/4/26 17:24
     */
    public ApiResponseResult checkCarExecl(int count, User user, CommunityEntity communityVO, CompanyEntity companyEntity, CommunityEntity entity1) {
        ApiResponseResult result = new ApiResponseResult();
        result.setCode(MessageCode.SUCCESS);
        result.setMsg(MessageCode.SUCCESS_TEXT);
        result.setData(1);
        if (companyEntity != null) {
            if (StringUtils.isNotEmpty(user.getCompanyName()) && entity1.getCompanyName().equals(user.getCompanyName())) {
                CompanyEntity comp = new CompanyEntity();
                comp.setId(companyEntity.getId());
                comp.setCompanyName(user.getCompanyName());
                CompanyEntity companyEntity1 = companyDao.queryCompanyEntityInfo(comp);
                if (companyEntity1 == null) {
                    result.setCode(MessageCode.FAIL);
                    result.setMsg("第" + count + "行" + "填写的所属公司,与下拉选择的公司匹配不上,请填写下拉选择的公司");
                    result.setData(null);
                    return result;
                }
            } else {
                result.setCode(MessageCode.FAIL);
                result.setMsg("填写的所属公司,与下拉选择的公司匹配不上,请填写下拉选择的公司");
                result.setData(null);
                return result;
            }
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg("第" + count + "行" + "填写的所属公司不存在请核实后填写");
            result.setData(null);
            return result;
        }
        if (communityVO != null) {
            if (StringUtils.isNotEmpty(user.getCommunityName()) && entity1.getCommunityName().equals(user.getCommunityName())) {
                CommunityEntity comm = new CommunityEntity();
                comm.setCommunityName(user.getCommunityName());
                comm.setId(communityVO.getId());
                CommunityEntity entity = communityDao.queryCommunity(comm);
                if (entity == null) {
                    result.setCode(MessageCode.FAIL);
                    result.setMsg("第" + count + "行" + "填写的所属小区,与下拉选择的公司下的小区匹配不上,请填写下拉选择的公司小区");
                    result.setData(null);
                    return result;
                }
            } else {
                result.setCode(MessageCode.FAIL);
                result.setMsg("填写的所属小区,与下拉选择的公司下的小区匹配不上,请填写下拉选择的公司小区");
                result.setData(null);
                return result;
            }
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg("第" + count + "行" + "填写的所属小区,与下拉选择的公司下的小区匹配不上,请填写下拉选择的公司小区");
            result.setData(null);
            return result;
        }
        if (StringUtils.isEmpty(user.getUserName())) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("第" + count + "行" + "员工姓名名称不能为空");
            result.setData(null);
            return result;
        }
        if (StringUtils.isEmpty(user.getStatus())) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("第" + count + "行" + "员工状态不能为空");
            result.setData(null);
            return result;
        }
        if (StringUtils.isEmpty(user.getMobile())) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("第" + count + "行" + "联系电话不能为空");
            result.setData(null);
            return result;
        }
        if (StringUtils.isEmpty(user.getSex())) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("第" + count + "行" + "员工性别不能为空");
            result.setData(null);
            return result;
        }
        if (StringUtils.isEmpty(user.getIdCard())) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("第" + count + "行" + "身份证号码不能为空");
            result.setData(null);
            return result;
        }
        return result;
    }

    /**
     * 字符串反串
     *
     * @param str
     * @return
     */
    private static String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }


    /**
     * @param requestPageVO
     * @Desc: 根据员工信息条件分页查询
     * @Author:邓磊
     * @UpdateDate:2020/4/28 9:39
     * @return: 返回
     */
    @Override
    public ApiResponseResult queryUserPage(RequestPageVO<UserEntity> requestPageVO) {
        //获取当前登录者所拥有的小区信息
        List<String> communityIds = currentUserInfoService.queryCurrentUserCommunityInfo();

        requestPageVO.getEntity().setCommunityIds(communityIds);
        PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
        List<UserEntityResponseVO> userList = userDao.queryUserList(requestPageVO.getEntity());
        if(CollectionUtils.isNotEmpty(userList))
		{
            userList.forEach(item->{
				//拼接租户公司数组
                if (StringUtils.isNotBlank(item.getTenantName())){
                    item.setCompanyName(item.getTenantName()+"/"+item.getCompanyName());
                }
			});
		}
        PageInfo<UserEntityResponseVO> pageInfo = new PageInfo<>(userList);
        return createSuccessResult(pageInfo);
    }

    /**
     * @param vo
     * @Desc: 员工信息详情
     * @Author:邓磊
     * @UpdateDate:2020/4/29 19:32
     * @return: 返回
     */
    @Override
    public ApiResponseResult queryUserVoInfo(UserEntity vo) throws Exception {
        UserEntityResponseVO userEntityResponseVO = userDao.queryUserVoInfo(vo);


            CompanyEntity companyEntities = companyDao.queryParentListById(userEntityResponseVO.getCompanyId());
            String parenStr=null;
            if(companyEntities !=null){
                parenStr = getParentNameByChildren(companyEntities,new StringBuilder());
            }
        userEntityResponseVO.setCompanyNameList(parenStr);
        return createSuccessResult(userEntityResponseVO);
    }

    private String getParentNameByChildren(CompanyEntity companyEntities, StringBuilder parenStr) {
        if(companyEntities!=null){
            if(companyEntities.getParentEntity()!=null){
                getParentNameByChildren(companyEntities.getParentEntity(),parenStr);
            }
        }
        return parenStr.append(companyEntities.getId()).append(",").toString();
    }

    /**
     * @Desc: 角色用户分页
     * @Author:邓磊
     * @UpdateDate:2020/5/7 9:36
     * @return: 返回
     */
    @Override
    public ApiResponseResult queryRoleUserPage(RequestPageVO<UserEntity> requestPageVO) throws Exception {
        //获取当前登录者所拥有的小区信息
        List<String> communityIds=currentUserInfoService.queryCurrentUserCommunityInfo();

        PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
        if (StringUtils.isNotBlank(RequestUtils.getCompanyId()) || StringUtils.isNotBlank(RequestUtils.getCommunityId())){
            requestPageVO.getEntity().setCommunityIds(communityIds);
        }
        List<UserEntityResponseVO> list = userDao.queryRoleUserList(requestPageVO.getEntity());
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(userEntityResponseVO -> {
                StringBuffer buffer = new StringBuffer();
                if (StringUtils.isNotEmpty(userEntityResponseVO.getRoleId())) {
                    String[] split = userEntityResponseVO.getRoleId().split(",");
                    List<RoleEntity> roleEntities = roleDao.queryBatchRole(split);
                    if (CollectionUtils.isNotEmpty(roleEntities)) {
                        roleEntities.stream().forEach(roleEntity -> {
                            buffer.append(roleEntity.getRoleName() + ",");
                        });
                    }
                    if(!StringUtils.isEmpty(buffer.toString())) {
                        String roleName = buffer.substring(0, buffer.length() - 1);
                        userEntityResponseVO.setRoleName(roleName);
                    }
                }
            });
        }
        PageInfo<UserEntityResponseVO> pageInfo = new PageInfo<>(list);
        return createSuccessResult(pageInfo);
    }

    /**
     * @param userEntity
     * @Desc:员工消息导出
     * @Author:邓磊
     * @UpdateDate:2020/5/14 17:17
     * @return: 返回
     */
    @Override
    public void exportUserEntityInfo(UserEntity userEntity) throws Exception {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        if (null != userEntity) {
            List<UserEntityResponseVO> userList = userDao.queryUserList(userEntity);
            excelDownload(response, userList);
        }
    }

    /**
     * 查询用户角色
     * @param requestPageVO
     * @return
     */
    @Override
    public ApiResponseResult selectUserRole(RequestPageVO<UserEntity> requestPageVO) {
        //查询公司ids
        List<String> companyIds =companyService.queryCompanyChidrenList(requestPageVO.getEntity().getCompanyId());
        requestPageVO.getEntity().setCompanyIds(companyIds);
        PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
        List<UserRoleEntity> userRoleEntities = userRoleDao.selectUserRole(requestPageVO.getEntity());
        PageInfo<UserRoleEntity> pageInfo = new PageInfo<>(userRoleEntities);
        return createSuccessResult(pageInfo);
    }

    public void excelDownload(HttpServletResponse response, List<UserEntityResponseVO> list) throws Exception {
        //表头数据
        String[] header = {"公司名称", "小区名称", "员工名字", "员工性别", "员工状态", "身份证号码", "员工电话"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("员工信息");
        sheet.setDefaultColumnWidth(15);
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFRow headrow = sheet.createRow(0);
        for (int i = 0; i < header.length; i++) {
            HSSFCell cell = headrow.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(header[i]);
            cell.setCellValue(text);
            cell.setCellStyle(headerStyle);
        }
        for (int i = 0; i < list.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(new HSSFRichTextString(list.get(i).getCompanyName()));
            row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getCommunityName()));
            row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getUserName()));
            if (null != list.get(i).getSex()) {
                if (list.get(i).getSex() == 1) {
                    row1.createCell(3).setCellValue(new HSSFRichTextString("男"));
                } else {
                    row1.createCell(3).setCellValue(new HSSFRichTextString("女"));
                }
            }
            if (StringUtils.isNotEmpty("" + list.get(i).getStatus())) {
                if (list.get(i).getSex() == 1) {
                    row1.createCell(4).setCellValue(new HSSFRichTextString("在职"));
                } else {
                    row1.createCell(4).setCellValue(new HSSFRichTextString("离职"));
                }
            }
            row1.createCell(5).setCellValue(new HSSFRichTextString(list.get(i).getIdCard()));
            row1.createCell(6).setCellValue(new HSSFRichTextString(list.get(i).getMobile() == null ? "" : "" + list.get(i).getMobile()));
        }
        String name = "员工信息";
        response.setHeader("content-Type", "application/ms-excel");
        String fileName = URLEncoder.encode(name, "utf-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    /**
     * @description:获取岗位用户列表
     * @return:
     * @author: 赵进华
     * @time: 2021/1/8 17:40
     */
    @Override
    public ApiResponseResult getUserJobList() throws Exception {
        return createSuccessResult(userDao.getUserJobList());
    }

    /**
     * @description:获取岗位用户列表
     * @return:
     * @author: 赵进华
     * @time: 2021/1/8 17:40
     */
    @Override
    public ApiResponseResult getUserJobListByCommunityId(List<String>communityIds) throws Exception {
        List<UserJobVo> userJobVos = userDao.getUserJobListByCommunityId(communityIds);
        String userId=RequestUtils.getUserId();
        filter(userId,userJobVos);
        return createSuccessResult(userJobVos);
    }

    /**
     * @description:过滤当前登录者的信息
     * @return:
     * @author: 赵进华
     * @time: 2021/1/8 17:40
     */
    private void filter(String userId,List<UserJobVo> userJobVos){
        for(UserJobVo userJobVo:userJobVos){
            if(userJobVo.getUserId().equals(userId)){
                userJobVos.remove(userJobVo);
                break;
            }
        }
    }
}
