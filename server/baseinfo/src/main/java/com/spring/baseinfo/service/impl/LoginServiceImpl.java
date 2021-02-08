package com.spring.baseinfo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.base.entity.baseinfo.CompanyEntity;
import com.spring.base.entity.baseinfo.DictionaryEntity;
import com.spring.base.vo.TokenVo;
import com.spring.base.vo.AppVo;
import com.spring.base.vo.baseinfo.menu.MenuOneVo;
import com.spring.base.vo.baseinfo.role.GetRolePowerVo;
import com.spring.base.vo.baseinfo.user.LoginReturnVo;
import com.spring.base.vo.baseinfo.user.LoginVo;
import com.spring.baseinfo.dao.ICompanyDao;
import com.spring.baseinfo.dao.IDictionaryDao;
import com.spring.common.cache.util.CacheUtil;
import com.spring.common.constants.Constants;
import com.spring.common.constants.MessageCode;
import com.spring.common.redis.RedisCacheUtils;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.token.SignToken;
import com.spring.common.util.aes.AesEncryptUtil;
import com.spring.baseinfo.dao.IUserDao;
import com.spring.baseinfo.service.ILoginService;
import com.spring.baseinfo.service.IMenuService;
import com.spring.common.util.md5.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description:登录业务实现类
 * @author: 赵进华
 * @time: 2020/3/13 9:37
 */

@Slf4j
@Service("loginService")
public class LoginServiceImpl implements ILoginService {
    @Autowired
    private IUserDao userDao;

    @Autowired
    private ICompanyDao companyDao;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private RedisCacheUtils redisUtils;

    @Autowired
    private IDictionaryDao dictionaryDao;

    /**
     * 登录
     *
     * @param loginVo
     * @return
     * @throws Exception
     * @author 作者：赵进华
     * @version 创建时间：2019年5月21日 下午2:50:57
     */
    @Override
    public ApiResponseResult userLogin(LoginVo loginVo) throws Exception {
        log.info("user login begin vo:"+loginVo.toString());
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        // AES解密成json字符串
        String json = AesEncryptUtil.desEncrypt(loginVo.getSecretString()).trim();
        // json字符串返序列化成对象
        AppVo vo = JSON.parseObject(json, AppVo.class);
        if (vo == null) {
            result.setCode(MessageCode.FAIL);
            result.setMsg(MessageCode.FAIL_TEXT);
            return result;
        }
        //超级管理员不能登录业主app和物业app
        if(vo.getAppType().equals(Constants.UserType.OWNER_APP) || vo.getAppType().equals(Constants.UserType.ESTATE_APP))
        {
            if(vo.getAppId().equals(Constants.SYSADMIN))
            {
                result.setCode(MessageCode.FAIL);
                result.setMsg("超级管理员不能登录物业App和业主App");
                return result;
            }
        }

        // 存redis中token的key
        String tokenKey = Constants.RedisPrefix.TOKEN + vo.getAppId();

        // 用户名
        String userCode = vo.getAppId();
        // 密码
        String password = Md5Util.md5Encode(vo.getAppPassword());

        log.info("user login search user userCode:"+userCode+",password:"+password);
        // 根据用户id和密码查询是否正确
        LoginReturnVo user = userDao.getUserByUserIdPassword(userCode, password, Constants.UserType.OWNER_APP);
        if (user != null) {
            log.info("user login user is not null:"+user.toString());
            //saas用户判断时间是否过期
            if(user.getIsSaas()==Constants.isSaaS.OK)
            {
                CompanyEntity companyEntity=companyDao.selectById(user.getTenantId());
                if(companyEntity!=null)
                {
                    if(companyEntity.getTryDate().before(new Date()))
                    {
                        result.setCode(MessageCode.FAIL);
                        result.setMsg("您的帐号已到期限，请购买服务或者续费，谢谢！");
                        return result;
                    }
                }
            }
            // token中包含信息的map
            Map<String, Object> claims = new HashMap<String, Object>(16);
            // token中包含用户id
            claims.put(Constants.Token.USER_ID, user.getUserId());
            claims.put(Constants.Token.USER_CODE, vo.getAppId());
            claims.put(Constants.Token.USER_TYPE,user.getUserType());
            //岗位id
            if (StringUtils.isEmpty(user.getJobId())){
                claims.put(Constants.Token.JOB_ID,"");
            }else {
                claims.put(Constants.Token.JOB_ID,user.getJobId());
            }
            //角色id
            if (StringUtils.isEmpty(user.getRoleId())){
                claims.put(Constants.Token.ROLE_ID,"");
            }else {
                claims.put(Constants.Token.ROLE_ID,user.getRoleId());
            }
            // token中包含租户id
            if(StringUtils.isEmpty(user.getTenantId())){
                claims.put(Constants.Token.TENANT_ID,"");
            }else{
                claims.put(Constants.Token.TENANT_ID, user.getTenantId());
            }
            // token中包含公司id
            if(StringUtils.isEmpty(user.getCompanyId())){
                claims.put(Constants.Token.COMPANY_ID,"");
            }else{
                claims.put(Constants.Token.COMPANY_ID, user.getCompanyId());
            }
            // token中包含小区id
            if(StringUtils.isEmpty(user.getCompanyId())){
                claims.put(Constants.Token.COMMUNITY_ID,"");
            }else{
                // token中包含小区id
                claims.put(Constants.Token.COMMUNITY_ID, user.getCommunityId());
            }

            // token中包含是否是saas用户
            if(StringUtils.isEmpty(user.getIsSaas().toString())){
                claims.put(Constants.Token.IS_SAAAS, "");
            }else{
                claims.put(Constants.Token.IS_SAAAS, user.getIsSaas());
            }
            // token中包含是否是saas用户
            if(StringUtils.isEmpty(user.getUserName())){
                claims.put(Constants.Token.USER_NAME, "");
            }else{
                claims.put(Constants.Token.USER_NAME, user.getUserName());
            }
            // token中包含时间,时间在变化，保证每次生成的token不一样
            claims.put(Constants.Token.DATE, new Date());

            //根据用户职位id查询职位名称
            QueryWrapper<DictionaryEntity> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("del_flag",0).eq("status",1).
                    eq("dict_parent_code","jobs").eq("dict_code",user.getJobId());
            DictionaryEntity dictionaryEntity=dictionaryDao.selectOne(queryWrapper);
            // 生成token
            String token = SignToken.createJavaWebToken(claims);
            // 存的token对象
            TokenVo tokenVo = new TokenVo();
            tokenVo.setUserId(user.getUserId());
            tokenVo.setUserCode(vo.getAppId());
            tokenVo.setToken(token);
            tokenVo.setTokenDate(new Date());
            tokenVo.setUserName(user.getUserName());
            if (dictionaryEntity!=null){
                tokenVo.setJobName(dictionaryEntity.getDictName());
            }
            // 先删除key
            redisUtils.del(tokenKey);
            String userInformation= JSON.toJSONString(tokenVo);
            // token存redis,设置过期时间
            redisUtils.set(tokenKey, userInformation, Constants.RedisExpire.TOKTN_EXPIRE);
            //更新用户登录时间,来源渠道
            userDao.modifyLoginTimeSourceChannel(userCode,new Date(),vo.getSourceChannel());
            // 构建返回的vo信息
            LoginReturnVo returnVo = new LoginReturnVo();
            returnVo.setId(user.getId());
            returnVo.setUserId(userCode);
            returnVo.setUserName(user.getUserName());
            returnVo.setUserNickname(user.getUserNickname());
            returnVo.setRoleId(user.getRoleId());
            returnVo.setToken(token);
            returnVo.setUserCode(userCode);
            returnVo.setCompanyId(user.getCompanyId());
            returnVo.setCommunityId(user.getCommunityId());
            returnVo.setUserLogoId(user.getUserLogoId());
            returnVo.setSex(user.getSex());
            returnVo.setMobile(user.getMobile());
            returnVo.setIdCard(user.getIdCard());
            if(StringUtils.isEmpty(returnVo.getUserName()) || StringUtils.isEmpty(returnVo.getIdCard()) || StringUtils.isEmpty(returnVo.getSex())){
                returnVo.setStatusTyeps(1);
            }else{
                returnVo.setStatusTyeps(0);
            }
            if(user.getDelFlag() == 1){
                result.setCode(MessageCode.FAIL);
                result.setMsg("当前登录用户已被删除，请联系管理员");
                return result;
            }
            if(user.getEnableStatusFlag() == 2){
                result.setCode(MessageCode.FAIL);
                result.setMsg("当前登录用户已被禁用，请联系管理员");
                return result;
            }
            result.setCode(MessageCode.SUCCESS);
            result.setMsg("成功");
            result.setData(returnVo);
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg("帐号密码不正确");
        }
        return result;
    }

    /**
     * @description:根据角色查询用户权限
     * @return:
     * @author: 赵进华
     * @time: 2020/4/3 15:01
     */
    @Override
    public ApiResponseResult getUserPower(GetRolePowerVo vo) throws Exception {
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        List<MenuOneVo> list =null;
        if("admin".equals(RequestUtils.getUserCode()))
        {
            list = menuService.queryAdminMenu(vo.getSystemCode());
        }
        else {
            if(StringUtils.isEmpty(vo.getRoleId()))
            {
                result.setCode(MessageCode.FAIL);
                result.setMsg("登录用户没有权限,请联系管理员");
                return result;
            }
            list = menuService.queryRoleMenu(vo.getRoleId(), "0", vo.getSystemCode());
        }
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("成功");
        result.setData(list);
        return result;
    }

    public static void main(String[] args) throws Exception {
        String pcSecreStr = "Dl6sfKwI6Zq+zNbHFP9hcJc/65medZUFjCAaQow5WBI1y5V7AipUE9rI4blnYLa5xUjQh6SzfqmA1dcrnvoULRNptUJ29mZd6yZdpR/ulhY=";
        String yzAppSecreStr = "Dl6sfKwI6Zq+zNbCFP9hcLaJu+OSCDHjbZCbmhccoDiQwxhyS95aP8H3zk0+Ol5tMhNfXFZqrzrjKzfRvVpqRggQBHtRi6nsO9Dd0F0KFnAQqT5dK1IsYnhPowIhoAvL";
        String json = AesEncryptUtil.desEncrypt(yzAppSecreStr).trim();
        System.out.println(json);
    }
}
