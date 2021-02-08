package com.spring.workflow.activiti.listener;

import com.spring.workflow.dao.BusinessMapper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 
 *
 * 节点任务完成监听类
 *
 * @author dell
 * @auther: Ace Lee
 * @date: 2019/3/8 11:57
 */
@Service()
@Slf4j
public class MyTaskListener implements TaskListener,JavaDelegate  {
	@Autowired
	private BusinessMapper businessMapper;
    
	// 监听任务的事件
	@Override
	public void notify(DelegateTask delegateTask) {
//		String aa="";
//		String bb=aa;
//		String cc=bb;
//		// 审核人
//		String assignee = delegateTask.getAssignee();
//		String eventName = delegateTask.getEventName();
//		String name = delegateTask.getName();
//		String processInstanceId = delegateTask.getProcessInstanceId();
//		Set<String> variableNames = delegateTask.getVariableNames();
//		
//		String [] candidateUsers={"a","b","c"};
//		delegateTask.setVariable("assignessList", Arrays.asList(candidateUsers));

		//delegateTask.setAssignee("assignessList");

		// 从set中取出businessId
//		String businessId = "";
//		// 从set中取出businessType
//		String businessType = "";
//		for (String key : variableNames) {
//			if (key.equals("businessId")) {
//				Object value = delegateTask.getVariable(key);
//				businessId = value.toString();
//			}
//			if (key.equals("businessType")) {
//				Object value = delegateTask.getVariable(key);
//				businessType = value.toString();
//			}
//		}
//		// 获取表名
//		String table = "";
//		WorkflowTypeVo workflowTypeVo = WorkflowMap.map.get(businessType);
//		if (workflowTypeVo != null) {
//			table = workflowTypeVo.getTableName();
//		}
//		// 拼接sql语句
//		String sql = "update " + table + " set status=2,audit_process='已完成' where id='" + businessId + "'";
//		// 更新业务表，执行sql
//		// businessMapper.execSql(sql);
//		log.info("一个任务[" + name + "]被创建了，由[" + assignee + "]负责办理");
	}


	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
	}
	

}
