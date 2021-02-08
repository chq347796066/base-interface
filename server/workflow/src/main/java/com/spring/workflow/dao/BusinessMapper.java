package com.spring.workflow.dao;

import com.spring.workflow.vo.UserCodeNameVo;
import com.spring.workflow.vo.WorkflowInfoVo;
import com.spring.workflow.model.BillRepeatDataEntity;
import com.spring.workflow.model.BillRepeatIdEntity;
import com.spring.workflow.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author dell
 */
@Mapper
public interface BusinessMapper {

	/**
	 * 根据业务类型查询流程id
	 * 
	 * @param businessType
	 * @return
	 * @author 作者：赵进华
	 * @version 创建时间：2019年6月5日 下午2:56:34
	 */
	String getProcessId(String businessType);

	/**
	 * 执行sql
	 * 
	 * @param sql
	 * @return
	 * @author 作者：赵进华
	 * @version 创建时间：2019年6月5日 下午4:00:53
	 */
	List<Map<String, Object>> execSql(String sql);

	/**
	 * 新增业务信息
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华
	 * @version 创建时间：2019年6月5日 下午5:29:09
	 */
    Integer addBusinessInfo(ActBusinessVo entity) throws Exception;

	/**
	 * 查询工作流类型
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华
	 * @version 创建时间：2019年5月22日 下午4:09:59
	 */
    List<DictionaryDataVo> getWorkTypeList() throws Exception;

	/**
	 * 查询我待审核的工作流
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华
	 * @version 创建时间：2019年6月5日 下午6:33:37
	 */
    List<WorkflowInfoVo> getMyAuditList(String userId) throws Exception;

	/**
	 * 查询流程任务id
	 * 
	 * @param processId
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华
	 * @version 创建时间：2019年6月9日 下午2:27:44
	 */
    String getTaskId(@Param("processId") String processId, @Param("userId") String userId) throws Exception;
	
	/**
	 * 根据用户id查询用户姓名
	 * @param userName
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华 
	 * @version 创建时间：2019年9月26日 下午3:37:10
	 */
    UserCodeNameVo getUserName(String userId) ;

	/**
	 * 保存审核记录
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华
	 * @version 创建时间：2019年6月10日 下午4:11:38
	 */
    Integer saveApproveInfo(ApproveInfoVo entity) throws Exception;

	/**
	 * 查询我的已审批列表
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华
	 * @version 创建时间：2019年6月10日 下午4:48:59
	 */
    List<ApproveInfoVo> getMyAuditOkList(String userId) throws Exception;
	
	/**
	 * 查询审批记录
	 * @param businessId
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华 
	 * @version 创建时间：2019年6月28日 下午5:38:02
	 */
    List<AuditInfoVo> getAuditInfoList(String businessId) throws Exception;
	
	/**
	 * 根据节点id查询角色对应的存储过程
	 * @param node
	 * @return
	 * @author 作者：赵进华 
	 * @version 创建时间：2019年8月7日 下午5:35:08
	 */
    String getProcName(String node);
	
	/**
	 * 获取动态设置审核人节点列表
	 * @return
	 * @author 作者：赵进华 
	 * @version 创建时间：2019年8月13日 下午3:52:02
	 */
    List<String> getAuditList();
	
	/**
	 * 调用存储过程
	 * @param params
	 * @author 作者：赵进华 
	 * @version 创建时间：2019年8月8日 上午11:19:56
	 */
    List<String> callProc(Map<String, Object> params);
	
	
	/**
	 * 判断是否是第一个驳回节点，第一个驳回节点，驳回路由为pass
	 * @param node
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华 
	 * @version 创建时间：2019年9月12日 下午5:54:02
	 */
    Integer checkFirstReject(String node) throws Exception;
	
	/**
	 * 获取所有审核用户
	 * @return
	 * @author 作者：赵进华 
	 * @version 创建时间：2019年9月26日 上午9:45:30
	 */
    List<UserVo> getAllAuditUser();
	
	/**
	 * 判断是否需要动态设置审核人
	 * @param node
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华 
	 * @version 创建时间：2019年9月26日 上午11:15:52
	 */
    Integer checkIsSetAuditUser(String node) throws Exception;
	
	/**
	 * 获取节点变更信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华 
	 * @version 创建时间：2019年10月18日 下午3:15:55
	 */
    NodeChangeVo getNodeChange(String id) throws Exception;
	
	/**
	 * 更新实施计划的时间
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华 
	 * @version 创建时间：2019年10月18日 下午3:24:24
	 */
    int updatePlanDate(NodeChangeVo vo) throws Exception;
	
	/**
	 * 新增日志
	 * @param entity
	 * @return
	 */
    Integer addLog(LogVo entity) throws Exception;
	
    /**
     * 批量新增日志
     * @param list
     * @return
     * @throws Exception
     */
    Integer addLogList(List<LogVo> list) throws Exception;

	EmailVo getRecEmail(String id) ;

	EmailVo gettaskEmail(String id) ;

	/**
	 * @description:获取立项是否是自动化
	 * @param:
	 * @return:
	 * @author: 赵进华
	 * @time: 2019/12/22 9:55
	 */
    Integer getApprovalType(String id) throws Exception;

    /**
     * @description:结题驳回删除其它节点的审核
     * @param:
     * @return:
     * @author: 赵进华
     * @time: 2019/12/30 10:15
     */
    Integer deleteAcceptNode(@Param("instId") String instId, @Param("taskId") String taskId) throws Exception;

	List<BillRepeatDataEntity> getData();

	List<BillRepeatDataEntity> getDataTemp();

	/**
	 * 批量新增
	 * @param list
	 * @return
	 * @throws Exception
	 */
    Integer addList(List<BillRepeatDataEntity> list) throws Exception;

	Integer addListId(List<BillRepeatIdEntity> list) throws Exception;


	Integer addListOk(List<BillRepeatDataEntity> list) throws Exception;
}