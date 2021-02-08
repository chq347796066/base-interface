package com.spring.saas.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import com.spring.base.entity.baseinfo.CompanyEntity;
import com.spring.base.entity.saas.UserEntity;
import com.spring.base.vo.AppVo;
import com.spring.base.vo.TokenVo;
import com.spring.base.vo.baseinfo.user.LoginReturnVo;
import com.spring.base.vo.baseinfo.user.LoginVo;
import com.spring.base.vo.saas.UserAddVo;
import com.spring.base.vo.saas.UserUpdateVo;
import com.spring.common.constants.Constants;
import com.spring.common.redis.RedisCacheUtils;
import com.spring.common.request.RequestUtils;
import com.spring.common.token.SignToken;
import com.spring.common.util.aes.AesEncryptUtil;
import com.spring.common.util.md5.Md5Util;
import com.spring.saas.dao.IUserDao;
import org.apache.commons.lang.StringUtils;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.saas.service.IUserService;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-10 10:09:25
 * @Desc类说明: 运营用户列业务接口实现类
 */

 @Slf4j
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<UserEntity, String> implements IUserService {
	
	@Autowired
	private IUserDao userDao;

	@Override
	public BaseDao getBaseMapper() {
		return userDao;
	}

	@Autowired
	private RedisCacheUtils redisUtils;
	
	/**
	 * 新增运营用户列
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-07-10 10:09:25
	 */
	@Override
	public ApiResponseResult addUser(UserAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = userDao.insert(entity);
		if (no > 0) {
			result.setCode(MessageCode.SUCCESS);
			result.setMsg("成功");
		} else {
			result.setCode(MessageCode.FAIL);
			result.setMsg("新增失败");
		}
		return result;
	}

	/**
	 * 更新运营用户列
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-10 10:09:25
	 */
	@Override
	public ApiResponseResult updateUser(UserUpdateVo vo) throws Exception {
		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = userDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	/**
	 * 运营后台用户登录
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
		// 存redis中token的key
		String tokenKey = Constants.RedisPrefix.TOKEN + vo.getAppId();

		// 用户名
		String userCode = vo.getAppId();
		// 密码
		String password = Md5Util.md5Encode(vo.getAppPassword());
		log.info("user login search user userCode:"+userCode+",password:"+password);
		// 根据用户id和密码查询是否正确
		UserEntity user = userDao.getUserByUserIdPassword(userCode, password);
		if (user != null) {
			log.info("user login user is not null:"+user.toString());

			// token中包含信息的map
			Map<String, Object> claims = new HashMap<String, Object>();
			// token中包含用户id
			claims.put(Constants.Token.USER_ID, vo.getAppId());
			claims.put(Constants.Token.USER_CODE, vo.getAppId());
			claims.put(Constants.Token.USER_TYPE,"saas");
			claims.put(Constants.Token.TENANT_ID,"");
			claims.put(Constants.Token.COMPANY_ID,"");
			claims.put(Constants.Token.COMMUNITY_ID,"");
			claims.put(Constants.Token.IS_SAAAS, "");

			// token中包含时间,时间在变化，保证每次生成的token不一样
			claims.put(Constants.Token.DATE, new Date());
			// 生成token
			String token = SignToken.createJavaWebToken(claims);
			// 存的token对象
			TokenVo tokenVo = new TokenVo();
			tokenVo.setUserId(vo.getAppId());
			tokenVo.setUserCode(vo.getAppId());
			tokenVo.setToken(token);
			tokenVo.setTokenDate(new Date());
			tokenVo.setUserName(user.getUserName());

			// 先删除key
			redisUtils.del(tokenKey);
			String userInformation= JSON.toJSONString(tokenVo);
			// token存redis,设置过期时间
			redisUtils.set(tokenKey, userInformation, Constants.RedisExpire.TOKTN_EXPIRE);

			// 构建返回的vo信息
			LoginReturnVo returnVo = new LoginReturnVo();
			returnVo.setId(user.getId());
			returnVo.setUserId(userCode);
			returnVo.setUserName(user.getUserName());
			returnVo.setToken(token);
			returnVo.setUserCode(userCode);

			result.setCode(MessageCode.SUCCESS);
			result.setMsg("成功");
			result.setData(returnVo);
		} else {
			result.setCode(MessageCode.FAIL);
			result.setMsg("帐号密码不正确");
		}
		return result;
	}
}
