package com.spring.baseinfo.service;

import com.spring.base.entity.baseinfo.DictionaryEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.baseinfo.dictionary.DictionaryAddVo;
import com.spring.base.vo.baseinfo.dictionary.DictionaryUpdateVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-18 17:32:18
 * @Desc类说明: 字典业务接口类
 */
public interface IDictionaryService extends IBaseService<DictionaryEntity,String> {
	
	/**
	 * 新增字典
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-18 17:32:18
	 */
    ApiResponseResult addDictionary(DictionaryAddVo vo) throws Exception;
	
	/**
	 * 更字典
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-18 17:32:18
	 */
    ApiResponseResult updateDictionary(DictionaryUpdateVo vo) throws Exception;


	/**
	 * @description:分页查询字典
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/3/23 13:54
	 */
    ApiResponseResult queryPageDictionary(RequestPageVO<DictionaryEntity> requestPageVO) throws Exception;

	/**
	 * @description:根据单个code查询字典列表
	 * @return: 
	 * @author: 赵进华
	 * @time: 2020/3/23 14:38
	 */
    ApiResponseResult getDictionaryByOne(String parentCode) throws Exception;

	/**
	 * @description:根据多个code查询字典列表
	 * @return: 
	 * @author: 赵进华
	 * @time: 2020/3/23 14:38
	 */
    ApiResponseResult getDictionaryByMulti(List<String> parentCodes) throws Exception;

	/**
	 * @description:删除字典
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/4/2 11:19
	 */
    ApiResponseResult deleteDictionary(String id) throws Exception;
}
