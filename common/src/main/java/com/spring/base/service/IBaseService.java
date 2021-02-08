package com.spring.base.service;

import com.spring.base.vo.Vo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

import java.io.Serializable;
import java.util.List;


/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:service基础父类
*/
public interface IBaseService<T extends Vo<PK>,PK extends Serializable>{
	/**
	 * 新增
	 * @param entity
	 * @return
	 */
     ApiResponseResult add(T entity) throws Exception;
    
    /**
     * 批量新增
     * @param list
     * @return
     * @throws Exception
     */
     ApiResponseResult addList(List<T> list) throws Exception;
    
    /**
     * 更新
     * @param entity
     * @return
     */
     ApiResponseResult update(T entity) throws Exception;
    
    /**
     * 删除
     * @param id
     * @return
     */
     ApiResponseResult delete(String id) throws Exception;

    /**
     * 批量删除
     * @param
     * @return
     */
     ApiResponseResult batchDelete(String[] idArray) throws Exception;

    
    /**
     * 根据主键id查询对象
     * @param id
     * @return
     */
     ApiResponseResult queryObject(String id) throws Exception;

    /**
     * 根据条件查询列表
     * @param
     * @return
     */
     ApiResponseResult queryList(T entity) throws Exception;
    
    /**
     * 根据条件分页查询
     * @param requestPageVO
     * @return
     * @throws Exception
     */
     ApiResponseResult queryPage(RequestPageVO<T> requestPageVO) throws Exception;

}

