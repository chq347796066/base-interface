package com.spring.workflow.activiti.listener;

import com.spring.workflow.dao.BusinessMapper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * 流程实例监听类
 *
 * @author dell
 * @auther: Ace Lee
 * @date: 2019/3/8 11:57
 */
@Slf4j
@Service("myProcessExecutionListener")
public class MyProcessExecutionListener implements ExecutionListener {

	@Autowired
	private BusinessMapper businessMapper;

	
	@Override
	public void notify(DelegateExecution execution) throws Exception {

//		String processDefinitionId=task.getProcessDefinitionId();
//		// 获取下一个节点的名称
//		String currentNode = nextTaskDefinition(processInstanceId);
//		
//		ProcessDefinition a = repositoryService.createProcessDefinitionQuery().singleResult();
//		execution.setVariable("assigness", "123@qq.com");
		

		// 获取表名
//		String table = "";
//		WorkflowTypeVo workflowTypeVo=WorkflowMap.map.get(businessType);
//		if (workflowTypeVo!=null) {
//			table = workflowTypeVo.getTableName();
//		}
//
//		// 拼接sql语句
//		String sql = "update " + table + " set status=4,audit_process='已完成' where id='" + businessId + "'";
//		// 更新业务表，执行sql
//		if (businessMapper == null) {
//			// 从spring容器中获取bean
//			businessMapper = (BusinessMapper) SpringContextUtil.getBean("businessMapper");
//			businessMapper.execSql(sql);
//			log.info("Process Execution Listener eventName:"+eventName+",businessType:"+businessType+",businessId:"+businessId+",opeartion:"+opeartion+", sql:"+sql);
//		}
//		 start
//		if ("start".equals(eventName)) {
//			log.info("==================start==================");
//		} else if ("end".equals(eventName)) {
//			log.info("==================end==================");
//		}
	}
	
}
