package com.spring.workflow.activiti.listener.line;

import com.spring.common.spring.SpringContextUtil;
import com.spring.workflow.dao.BusinessMapper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:结题流程是否自动化监听
 * @author: 赵进华
 * @time: 2019/12/22 9:48
 */
@Service()
@Slf4j
public class AcceptLineListener implements TaskListener, JavaDelegate {
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

        if (businessMapper == null) {
            // 从spring容器中获取bean
            businessMapper = (BusinessMapper) SpringContextUtil.getBean("businessMapper");
        }
        //是否属于自动化减员(1是2否)
        Integer isAuto=businessMapper.getApprovalType(businessId);
        log.info("AcceptLineListener begin businessId:" + businessId + ",businessType:"
                + businessType+",isAuto:"+isAuto);
        // 动态设置下一节点是否走自动化审核还是走工艺部审核
        execution.setVariable("operation", isAuto.toString());
    }
}
