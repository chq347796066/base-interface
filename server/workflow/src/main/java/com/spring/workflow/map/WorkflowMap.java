package com.spring.workflow.map;

import com.spring.workflow.vo.WorkflowTypeVo;

import java.util.HashMap;
import java.util.Map;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年6月28日 下午2:00:40
* @Desc类说明:工作流类型map
*/
public class WorkflowMap {
    /**
     * 工作流类型map
     */
	public final static Map<String,WorkflowTypeVo> MAP = new HashMap<String,WorkflowTypeVo>();
	
	/**
	 * 初始化
	 */
	static {
		WorkflowTypeVo projectReformVo=new WorkflowTypeVo();
		projectReformVo.setTableName("p_rec_project");
		projectReformVo.setWorkflowName("项目整改流程");
		MAP.put("ProjectReform", projectReformVo);
		
		WorkflowTypeVo projectDevelopMainVo=new WorkflowTypeVo();
		projectDevelopMainVo.setTableName("p_dev_task");
		projectDevelopMainVo.setWorkflowName("项目开发任务珠海单位流程");
		MAP.put("ProjectDevelopMain", projectDevelopMainVo);
		
		
		WorkflowTypeVo projectDevelopSubVo=new WorkflowTypeVo();
		projectDevelopSubVo.setTableName("p_dev_task");
		projectDevelopSubVo.setWorkflowName("项目开发任务子公司流程");
		MAP.put("ProjectDevelopSub", projectDevelopSubVo);
		
		WorkflowTypeVo projectCreateSubVo=new WorkflowTypeVo();
		projectCreateSubVo.setTableName("p_pro_approval");
		projectCreateSubVo.setWorkflowName("项目立项子公司流程");
		MAP.put("ProjectCreateSub", projectCreateSubVo);
		
		WorkflowTypeVo projectCreateMainVo=new WorkflowTypeVo();
		projectCreateMainVo.setTableName("p_pro_approval");
		projectCreateMainVo.setWorkflowName("项目立项珠海单位流程");
		MAP.put("ProjectCreateMain", projectCreateMainVo);
		
		WorkflowTypeVo projectVerifySubVo=new WorkflowTypeVo();
		projectVerifySubVo.setTableName("p_pro_check_accept");
		projectVerifySubVo.setWorkflowName("项目结题验收子公司流程");
		MAP.put("ProjectVerifySub", projectVerifySubVo);
	
		WorkflowTypeVo projectVerifyMainVo=new WorkflowTypeVo();
		projectVerifyMainVo.setTableName("p_pro_check_accept");
		projectVerifyMainVo.setWorkflowName("项目结题验收珠海单位流程");
		MAP.put("ProjectVerifyMain", projectVerifyMainVo);
		
		WorkflowTypeVo projectNodeChangeVo=new WorkflowTypeVo();
		projectNodeChangeVo.setTableName("p_pro_approval_node_change");
		projectNodeChangeVo.setWorkflowName("项目节点变更流程");
		MAP.put("ProjectNodeChange", projectNodeChangeVo);
		
		WorkflowTypeVo projectSupervisionVo=new WorkflowTypeVo();
		projectSupervisionVo.setTableName("p_pro_supervise_bulletin");
		projectSupervisionVo.setWorkflowName("项目监督通报流程");
		MAP.put("ProjectSupervision", projectSupervisionVo);
	}
}
