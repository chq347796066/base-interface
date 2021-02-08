package com.spring.baseinfo.service;

import com.spring.base.entity.baseinfo.BuildEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.baseinfo.build.BuildAddVo;
import com.spring.base.vo.baseinfo.build.BuildUpdateVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明:  楼栋业务接口类
 */
public interface IBuildService extends IBaseService<BuildEntity,String> {
	
	/**
	 * 新增 楼栋
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-31 19:02:26
	 */
    ApiResponseResult addBuild(BuildAddVo vo) throws Exception;
	
	/**
	 * 更 楼栋
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-31 19:02:26
	 */
    ApiResponseResult updateBuild(BuildUpdateVo vo) throws Exception;

	 /**
	  * 更 楼栋
	  * @param id
	  * @return
	  * @throws Exception
	  * @author 作者：ZhaoJinHua
	  * @version 创建时间：2020-03-31 19:02:26
	  */
     ApiResponseResult deleteCell(String id) throws Exception;

	/**
	 * 根据条件分页查询楼栋
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult queryBuildPage(RequestPageVO<BuildEntity> requestPageVO) throws Exception;

	BuildEntity queryBuildInfo(BuildEntity vo) throws Exception;
}
