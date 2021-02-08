package com.spring.workflow.activiti.listener.main;

import com.spring.common.spring.SpringContextUtil;
import com.spring.workflow.dao.BusinessMapper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2019-09-11 08:50:05
 * @Desc类说明: 项目立项总部人力资源审核连线监听器
 */
// @Component
@Service()
@Slf4j
public class MainTaskListener implements TaskListener, JavaDelegate {
	@Autowired
	private BusinessMapper businessMapper;

	// 监听任务的事件
	@Override
	public void notify(DelegateTask delegateTask) {

	}

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// 获取业务id
		String businessId = (String) execution.getVariable("businessId");
		// 获取业务类型
		String businessType = (String) execution.getVariable("businessType");
		StringBuffer auditUserBuffer = new StringBuffer();
		log.info("MainHRTaskListener begin businessId:" + businessId + ",nodeId:pcmAutoAuditLine,businessType:"
				+ businessType);
		if (businessMapper == null) {
			// 从spring容器中获取bean
			businessMapper = (BusinessMapper) SpringContextUtil.getBean("businessMapper");
		}
		// 参数列表
		Map<String, Object> params = new HashMap<String, Object>(16);
		params.put("businessId", businessId);
		params.put("nodeId", "pcmHRAuditLine");
		// 调用存储过程动态获取审核用户列表
		List<String> auditUserList = auditUserList = businessMapper.callProc(params);
		if (auditUserList != null && auditUserList.size() > 0) {
			auditUserList.forEach(action -> {
				auditUserBuffer.append("|" + action);
			});
		}
		log.info("MainHRTaskListener businessId:" + businessId + ",nodeId:pcmHRAuditLine,businessType:" + businessType
				+ ",auditUser" + auditUserBuffer.toString());
		// 动态设置下一节点审核用户
		execution.setVariable("assignessList", auditUserList);
	}

}
