package com.spring.baseinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.DictionaryEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.dictionary.DictionaryAddVo;
import com.spring.base.vo.baseinfo.dictionary.DictionaryDataVo;
import com.spring.base.vo.baseinfo.dictionary.DictionaryNodeVo;
import com.spring.base.vo.baseinfo.dictionary.DictionaryUpdateVo;
import com.spring.common.constants.MessageCode;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import com.spring.baseinfo.dao.IDictionaryDao;
import com.spring.baseinfo.service.IDictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-18 17:32:18
 * @Desc类说明: 字典业务接口实现类
 */
@Service("dictionaryService")
public class DictionaryServiceImpl extends BaseServiceImpl<DictionaryEntity, String> implements IDictionaryService {
	
	@Autowired
	private IDictionaryDao dictionaryDao;

	@Override
	public BaseDao getBaseMapper() {
		return dictionaryDao;
	}
	
	/**
	 * 新增字典
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-03-18 17:32:18
	 */
	@Override
	public ApiResponseResult addDictionary(DictionaryAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		if (StringUtils.isEmpty(vo.getDictCode())) {
			result.setCode(MessageCode.FAIL);
			result.setMsg("Code为空！");
			return result;
		}
		//判断字典类型是否重复
		if("0000".equals(vo.getDictParentCode())) {
			int no = dictionaryDao.dictTypeExist(vo.getDictType());
			if (no > 0) {
				result.setCode(MessageCode.FAIL);
				result.setMsg("字典类型已存在！");
				return result;
			}
		}

		//判断同一个字典类型里code是否存在
		QueryWrapper<DictionaryEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("dict_code", vo.getDictCode()).eq("dict_type", vo.getDictType());
		List<DictionaryEntity> dictionaryList=dictionaryDao.selectList(queryWrapper);
		if (dictionaryList.size()>0) {
			result.setCode(MessageCode.FAIL);
			result.setMsg("Code已存在，请修改后再保存！");
			return result;
		}

		DictionaryEntity entity = new DictionaryEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setTenantId(RequestUtils.getTenantId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = dictionaryDao.insert(entity);
		if (no > 0) {
			result.setCode(MessageCode.SUCCESS);
			result.setMsg("成功");
		} else {
			result.setCode(MessageCode.FAIL);
			result.setMsg("新增失败");
		}
		return result;
	}

	/**
	 * 更新字典
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-18 17:32:18
	 */
	@Override
	public ApiResponseResult updateDictionary(DictionaryUpdateVo vo) throws Exception {
		DictionaryEntity entity = new DictionaryEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = dictionaryDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	/**
	 * @description:分页查询字典
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/3/23 13:54
	 */
	@Override
	public ApiResponseResult queryPageDictionary(RequestPageVO<DictionaryEntity> requestPageVO) throws Exception {
		DictionaryEntity entity = requestPageVO.getEntity();
		entity.setDictParentId("0");
		entity.setStatus(1);
		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		List<DictionaryNodeVo> list = dictionaryDao.queryListPage(entity);
		PageInfo<DictionaryNodeVo> pageInfo = new PageInfo<>(list);
		return createSuccessResult(pageInfo);
	}

	/**
	 * @description:根据单个code查询字典列表
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/3/23 14:38
	 */
	@Override
	public ApiResponseResult getDictionaryByOne(String parentCode) throws Exception {
		List<DictionaryDataVo> list = dictionaryDao.getListByParentCode(parentCode);
		return createSuccessResult(list);
	}

	/**
	 * @description:根据多个code查询字典列表
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/3/23 14:38
	 */
	@Override
	public ApiResponseResult getDictionaryByMulti(List<String> parentCodes) throws Exception {
		HashMap<String, List<DictionaryDataVo>> resultMap = new HashMap<>(16);
		for (String parentCode : parentCodes) {
			List<DictionaryDataVo> dictList = dictionaryDao.getListByParentCode(parentCode);
			resultMap.put(parentCode,dictList);
		}
		return createSuccessResult(resultMap);
	}

	/**
	 * @description:删除字典
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/4/2 11:19
	 */
	@Override
	public ApiResponseResult deleteDictionary(String id) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		//判断id是否有子项，有子项不允许删除
		QueryWrapper<DictionaryEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("dict_parent_id", id);
		// 根据用户id和密码查询是否正确
		List<DictionaryEntity> dictList = dictionaryDao.selectList(queryWrapper);
		if(dictList.size()>0)
		{
			result.setCode(MessageCode.FAIL);
			result.setMsg("请先删除子项，再删除当前项！");
			return result;
		}
		int no=dictionaryDao.deleteById(id);
		if(no>0)
		{
			result.setCode(MessageCode.SUCCESS);
			result.setMsg(MessageCode.SUCCESS_TEXT);
		}else
		{
			result.setCode(MessageCode.FAIL);
			result.setMsg(MessageCode.FAIL_TEXT);
		}
		return result;
	}
}
