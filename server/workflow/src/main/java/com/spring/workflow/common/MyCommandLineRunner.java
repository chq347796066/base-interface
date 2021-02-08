package com.spring.workflow.common;

import com.spring.common.constants.Constants;
import com.spring.common.redis.RedisCacheUtils;
import com.spring.workflow.dao.BusinessMapper;
import com.spring.workflow.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 作者 E-mail:赵进华 louie.zhao@farben.com.cn
 * @version 创建时间：2019年8月13日 下午7:25:13
 * @Desc类说明: 项目启动加载工作流自定义节点审核人列表数据进redis
 */
@Component
public class MyCommandLineRunner implements CommandLineRunner {

	@Autowired
	private BusinessMapper businessMapper;

	@Override
	public void run(String... arg0) throws Exception {
//		// ---------------获取动态设置审核人节点列表--------------------
//		List<String> list = businessMapper.getAuditList();
//		// 存储进redis
//		// 存redis中的key
//		String workflowSetKey = Constants.REDIS_PREFIX.WORKFLOW_SET;
//		// 存redis,设置永不过期
//		RedisCacheUtils.set(workflowSetKey, list, 0);
//
//	    //---------------存在redis中的用户map--------------------
//		Map<String,String> mapUser=new HashMap<String,String>();
//		// 审核用户id和姓名，方便系统根据id获取姓名
//		List<UserVo> userList = businessMapper.getAllAuditUser();
//		if(userList!=null && userList.size()>0)
//		{
//			userList.forEach(item->{
//				mapUser.put(item.getUserId(), item.getUserName());
//			});
//		}
//		// 存redis中的key
//		String workflowUserKey = Constants.REDIS_PREFIX.WORKFLOW_USER;
//		// 存redis,设置永不过期
//		RedisCacheUtils.set(workflowUserKey, mapUser, 0);
	}

}
