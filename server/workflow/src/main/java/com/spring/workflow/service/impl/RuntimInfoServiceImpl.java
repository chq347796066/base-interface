package com.spring.workflow.service.impl;

import com.spring.workflow.dao.HistoryMapper;
import com.spring.workflow.dao.RuntimeMapper;
import com.spring.workflow.service.RuntimeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author dell
 * @Auther: Ace Lee
 * @Date: 2019/3/7 16:55
 */
@Slf4j
@Service
public class RuntimInfoServiceImpl implements RuntimeInfoService {
    @Autowired
    private RuntimeMapper runtimeMapper;
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;

    @Override
    public List<Map<String, Object>> myTasks(String userId) {
        return runtimeMapper.selectMyTasksToDo(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rejected(String taskId, String rejectElemKey, String dealReason) {
        int res = 0;
        //1.历史表
        //判断是否结束
        Map<String,Object> endEvent = historyMapper.selectEndEventByTaskId(taskId);
        log.info("查询hi_taskinst结束事件的结果，{}",endEvent);
        List<Map<String,Object>> hiTask=historyMapper.selectHiTaskByTaskId(taskId);
        String ruExcutionId=(String) hiTask.get(0).get("EXECUTION_ID_");
        String processId=(String) hiTask.get(0).get("PROC_INST_ID_");

        //2.运行表
        String str="S00000";
        if (str.equals(rejectElemKey)){
            if (null==endEvent || endEvent.isEmpty()){
                //删variables
                res = runtimeMapper.deleteRuVariable(taskId);
                log.info("删ru_variables结束，{}",res);

                TaskEntity currentTaskEntity = (TaskEntity)taskService.createTaskQuery()
                        .processInstanceId(processId).singleResult();
                currentTaskEntity.setExecutionId(null);
                taskService.saveTask(currentTaskEntity);
                taskService.deleteTask(currentTaskEntity.getId(), true);
                log.info("删ru_task结束，{}",currentTaskEntity);

                res = runtimeMapper.deleteRuExecution(taskId);
                log.info("删ru_execution结束，{}",res);

                res = runtimeMapper.deleteRuIdentity(taskId);
                log.info("删ru_identitylink结束，{}",res);

            }else {
                res = historyMapper.deleteHiEndEvent(taskId);
                log.info("删掉hi_actinst中endEvent结束，{}",res);
            }
        }else {
            jumpEndActivity(ruExcutionId,rejectElemKey,dealReason);
        }
        return true;
    }


    /**
     *  根据ActivityId 查询出来想要活动Activity
     * @param id
     * @return
     */
    public ActivityImpl queryTargetActivity(String id){

        ReadOnlyProcessDefinition deployedProcessDefinition = ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition("ziyouliu:1:4");
        List<ActivityImpl> activities = (List<ActivityImpl>) deployedProcessDefinition.getActivities();
        for (ActivityImpl activityImpl : activities) {
            if(activityImpl.getId().equals(id)){
                return activityImpl;
            }
        }
        return null;
    }
    /**
     * 查询当前的活动节点
     */
    public ActivityImpl qureyCurrentTask(String processDefinitionId){
//		String processDefinitionId="ziyouliu:1:4";
        Execution execution = runtimeService.createExecutionQuery().processDefinitionId(processDefinitionId).singleResult();
        String activityId = execution.getActivityId();
        ActivityImpl currentActivity = queryTargetActivity(activityId);
        log.info(currentActivity.getId()+""+currentActivity.getProperty("name"));
        return currentActivity;
    }




    /**
     * 第一种自由跳转的方式:
     *   这种方式是通过 重写命令  ，推荐这种方式进行实现(这种方式的跳转，最后可以通过taskDeleteReason 来进行查询 )
     */

    public void jumpEndActivity(final String executionId,final String targetActId,final String reason){
/*        //当前节点
        ActivityImpl currentActivityImpl = qureyCurrentTask("ziyouliu:1:4");
        //目标节点
        ActivityImpl targetActivity = queryTargetActivity("shenchajigou");*/

        ((RuntimeServiceImpl)runtimeService).getCommandExecutor().execute(new Command<Object>() {
            @Override
            public Object execute(CommandContext commandContext) {
                ExecutionEntity execution = commandContext.getExecutionEntityManager().findExecutionById(executionId);
                execution.destroyScope(reason);  //
                ProcessDefinitionImpl processDefinition = execution.getProcessDefinition();
                ActivityImpl findActivity = processDefinition.findActivity(targetActId);
                execution.executeActivity(findActivity);
                return execution;
            }

        });
        //log.info("自由跳转至节点：{}-----完成",targetActId);
    }


}
