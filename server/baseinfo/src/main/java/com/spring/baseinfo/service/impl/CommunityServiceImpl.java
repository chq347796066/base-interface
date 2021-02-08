package com.spring.baseinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.dto.CommunityDto;
import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.base.entity.baseinfo.CompanyEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.community.CommunityAddVo;
import com.spring.base.vo.baseinfo.community.CommunityUpdateVo;
import com.spring.baseinfo.dao.ICommunityDao;
import com.spring.baseinfo.dao.ICompanyDao;
import com.spring.baseinfo.service.ICommunityService;
import com.spring.baseinfo.service.ICurrentUserInfoService;
import com.spring.common.constants.Constants;
import com.spring.common.constants.MessageCode;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import com.spring.common.util.list.ListUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 小区信息业务接口实现类
 */

@Service("communityService")
public class CommunityServiceImpl extends BaseServiceImpl<CommunityEntity, String> implements ICommunityService {
	
	@Autowired
	private ICommunityDao communityDao;

	@Autowired
	private ICompanyDao companyDao;

	@Autowired
	private ICurrentUserInfoService currentUserInfoService;

	@Override
	public BaseDao getBaseMapper() {
		return communityDao;
	}

	 @Override
	 public ApiResponseResult queryCommunityEntity(String id) {
		 CommunityEntity communityEntity = communityDao.selectById(id);
		 CompanyEntity companyEntities = companyDao.queryParentListById(communityEntity.getCompanyId());
		 if(companyEntities !=null){
			 String parenStr = getParentNameByChildren(companyEntities,new StringBuilder());
			 communityEntity.setCompanyNameList(parenStr.substring(0,parenStr.length()-1));
		 }
		 return createSuccessResult(communityEntity);
	 }


	public String queryParentByCompanyId(String id) {
		CompanyEntity companyEntities = companyDao.queryParentListById(id);
		String parenStr=null;
		if(companyEntities !=null){
			parenStr = getParentNameByChildren(companyEntities,new StringBuilder());
		}
		return parenStr;
	}

	/**
	 * 获取父项名称
	 * @param companyEntities
	 * @param parenStr
	 * @return
	 */
	 private String getParentNameByChildren(CompanyEntity companyEntities, StringBuilder parenStr) {
	 	if(companyEntities!=null){
			if(companyEntities.getParentEntity()!=null){
				getParentNameByChildren(companyEntities.getParentEntity(),parenStr);
			}
		}
	 	return parenStr.append(companyEntities.getId()).append(",").toString();
	 }


	 /**
	 * 新增小区信息
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-03-31 19:02:26
	 */
	@Override
	public ApiResponseResult addCommunity(CommunityAddVo vo) {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		//判断用户是否选租户和公司
		if (vo.getTenantCompanyArray() == null && vo.getTenantCompanyArray().length == 0) {
			result.setCode(MessageCode.FAIL);
			result.setMsg("请填写公司！");
			return result;
		}
		//saas用户判断创建的小区数量是否超过购买的小区数
		if(RequestUtils.getIsSaas())
		{
			//获取租户下的小区数量
			int createNo=communityDao.getTenantCommunityNum(RequestUtils.getTenantId());
			//获取租户购买的小区数量
			int communityNum=0;
			CompanyEntity companyEntity=companyDao.selectById(RequestUtils.getTenantId());
			if(companyEntity!=null)
			{
				communityNum=companyEntity.getCommunityNum();
			}
			if((createNo+1)>communityNum)
			{
				result.setCode(MessageCode.FAIL);
				result.setMsg("您创建的小区数大于您购买的小区数！");
				return result;
			}
		}
		CommunityEntity entity = new CommunityEntity();
		BeanUtils.copyProperties(vo, entity);
		String id=UUIDFactory.createId();
		entity.setId(id);
		entity.setCommunityId(id);
		entity.setStatus(Constants.Status.ENABLE);
		entity.setCommunityCode("COM"+UUIDFactory.createId());
		entity.setTenantId(RequestUtils.getTenantId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		//拆分省市区，分开保存
		if(!StringUtils.isEmpty(vo.getCommunityAddress()))
		{
			String[] array=vo.getCommunityAddress().split("\\,");
			if(array!=null && array.length==3)
			{
				entity.setProvince(array[0]);
				entity.setCity(array[1]);
				entity.setRegion(array[2]);
			}
		}
		//校验公司下小区名唯一性
		CommunityEntity entityVo = new CommunityEntity();
		entityVo.setCompanyId(entity.getCompanyId());
		entityVo.setCommunityName(entity.getCommunityName());
		List<CommunityEntity> communityEntities = communityDao.queryCommunityName(entityVo);
		if(communityEntities.size()>0){
			result.setCode(MessageCode.FAIL);
			result.setMsg("你填写的公司下小区名称已存在，请核实后请重新输入！");
			return result;
		}
		//拆分租户公司数组
		if (vo.getTenantCompanyArray() != null) {
			entity.setTenantId(vo.getTenantCompanyArray()[0]);
			entity.setCompanyId(vo.getTenantCompanyArray()[vo.getTenantCompanyArray().length-1]);
		}
		// 新增
		int no = communityDao.insert(entity);
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
	 * 更新小区信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-31 19:02:26
	 */
	@Override
	public ApiResponseResult updateCommunity(CommunityUpdateVo vo) {
		ApiResponseResult result = new ApiResponseResult();
		//判断用户是否选租户和公司
		if (vo.getTenantCompanyArray() == null) {
			result.setCode(MessageCode.FAIL);
			result.setMsg("请填写公司！");
			return result;
		}
		CommunityEntity entity = new CommunityEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		//校验公司下小区名唯一性
		CommunityEntity entityVo = new CommunityEntity();
		entityVo.setId(entity.getId());
		entityVo.setCompanyId(entity.getCompanyId());
		entityVo.setCommunityName(entity.getCommunityName());
		List<CommunityEntity> communityEntities = communityDao.queryCommunityName(entityVo);
		if(communityEntities.size()>0){
			result.setCode(MessageCode.FAIL);
			result.setMsg("你填写的公司下小区名称已存在，请核实后请重新输入！");
			return result;
		}
		//拆分租户公司数组
		if (vo.getTenantCompanyArray() != null && vo.getTenantCompanyArray().length==2) {
			entity.setTenantId(vo.getTenantCompanyArray()[0]);
			entity.setCompanyId(vo.getTenantCompanyArray()[1]);
		}
		//拆分省市区，分开保存
		if(!StringUtils.isEmpty(vo.getCommunityAddress()))
		{
			String[] array=vo.getCommunityAddress().split("\\,");
			if(array!=null && array.length==3)
			{
				entity.setProvince(array[0]);
				entity.setCity(array[1]);
				entity.setRegion(array[2]);
			}
		}
		// 更新
		int no = communityDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}


	/**
	 * @Desc: 小区导出信息
	 * @param requestPageVO
	 * @Author:邓磊
	 * @UpdateDate:2020/5/14 16:58
	 * @return: 返回
	 */
	 @Override
	 public void exportCommunityEntityInfo(CommunityEntity requestPageVO) throws Exception {
		 ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		 HttpServletResponse response = servletRequestAttributes.getResponse();
		 if(null !=requestPageVO){
		 	List<CommunityEntity> communityEntities = communityDao.queryList(requestPageVO);
		 	excelDownload(response,communityEntities);
		 }
	 }
	public void excelDownload(HttpServletResponse response,List<CommunityEntity> list) throws Exception {
		//表头数据
		String[] header = {"小区名称", "所属公司", "小区电话", "小区地址", "总户数"};
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("小区信息");
		sheet.setDefaultColumnWidth(15);
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		HSSFRow headrow = sheet.createRow(0);
		for (int i = 0; i < header.length; i++) {
			HSSFCell cell = headrow.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(header[i]);
			cell.setCellValue(text);
			cell.setCellStyle(headerStyle);
		}
		for(int i=0;i<list.size();i++){
			HSSFRow row1 = sheet.createRow(i+1);
			row1.createCell(0).setCellValue(new HSSFRichTextString(list.get(i).getCommunityName()));
			row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getCompanyName()));
			row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getCommunityMobile()));
			row1.createCell(3).setCellValue(new HSSFRichTextString(list.get(i).getCommunityAddress()));
			row1.createCell(4).setCellValue(new HSSFRichTextString(list.get(i).getAllHouseholds()==null?"":""+list.get(i).getAllHouseholds()));
		}
		String name = "小区信息";
		response.setHeader("content-Type","application/ms-excel");
		String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}

	/**
	 * @description:根据条件分页查询小区
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/6/5 15:28
	 */
	@Override
	public ApiResponseResult queryCommunityPage(RequestPageVO<CommunityEntity> requestPageVO) throws Exception {
		//获取当前登录者所拥有的小区信息
		List<String> communityIds=currentUserInfoService.queryCurrentUserCommunityInfo();

		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		requestPageVO.getEntity().setCommunityIds(communityIds);
		List<CommunityEntity> list = communityDao.queryList(requestPageVO.getEntity());
		if(list!=null && list.size()>0)
		{
			list.forEach(item->{
				//拼接租户公司数组
				if (StringUtils.isNotBlank(item.getCompanyId())){
					item.setCompanyNameList(this.queryParentByCompanyId(item.getCompanyId()));
				}
			});

		}
		PageInfo<CommunityEntity> pageInfo = new PageInfo<>(list);


		return createSuccessResult(pageInfo);
	}

	/**
	 * 根据条件查询列表
	 * @param
	 * @return
	 */
	@Override
	public ApiResponseResult queryCommunityList(CommunityEntity entity) throws Exception {
		List<CommunityEntity> list=communityDao.queryList(entity);
		if(list!=null && list.size()>0)
		{
			list.forEach(item->{
				//拼接租户公司数组
				if(StringUtils.isEmpty(item.getCompanyId()))
				{
					String[] tenantCompanyArray = new String[1];
					tenantCompanyArray[0] = item.getTenantId();
					item.setTenantCompanyArray(tenantCompanyArray);
				}else
				{
					String[] tenantCompanyArray = new String[2];
					tenantCompanyArray[0] = item.getTenantId();
					tenantCompanyArray[1] = item.getCompanyId();
					item.setTenantCompanyArray(tenantCompanyArray);
				}
			});
		}
		return createSuccessResult(list);
	}

	/**
	 * 根据公司id查询项目信息
	 * @param
	 * @return
	 */
	@Override
	public List<CommunityDto> getCommunityInfo(String companyId) {
		QueryWrapper<CommunityEntity> queryWrapper=new QueryWrapper<>();
		List<CommunityDto> communityList=new ArrayList<>();
		if (StringUtils.isBlank(companyId)){
			queryWrapper.eq("del_flag",0);
		}else {
			queryWrapper.eq("del_flag",0).eq("company_id",companyId);
		}
		List<CommunityEntity> list=communityDao.selectList(queryWrapper);
		if (CollectionUtils.isEmpty(list)){
			return new ArrayList<>();
		}
		ListUtil.copy(list,CommunityDto.class);
		CollectionUtils.addAll(communityList,ListUtil.copy(list,CommunityDto.class));
		return communityList;
	}

	/**
	 * 根据公司id查询项目信息下拉框
	 * @param
	 * @return
	 */
	@Override
	public ApiResponseResult getCommunityInfoByCompanyId() {
		List<CommunityDto> communityList=new ArrayList<>();
		String communityId=RequestUtils.getCommunityId();
		QueryWrapper<CommunityEntity> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("del_flag",0).eq("community_id",communityId);
		if (StringUtils.isBlank(communityId)){
			List<CommunityDto> list=getCommunityInfo(RequestUtils.getCompanyId());
			return createSuccessResult(list);
		}else {
			List<CommunityEntity> list=communityDao.selectList(queryWrapper);
			ListUtil.copy(list,CommunityDto.class);
			CollectionUtils.addAll(communityList,ListUtil.copy(list,CommunityDto.class));
			return createSuccessResult(communityList);
		}
	}

	/**
	 * 根据公司id查询项目信息下拉框
	 * @param
	 * @return
	 */
	@Override
	public List<CommunityDto> queryCommunityInfo() {
		List<CommunityDto> communityList=new ArrayList<>();
		String communityId=RequestUtils.getCommunityId();
		QueryWrapper<CommunityEntity> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("del_flag",0).eq("community_id",communityId);
		if (StringUtils.isBlank(communityId)){
			List<CommunityDto> list=getCommunityInfo(RequestUtils.getCompanyId());
			return list;
		}else {
			List<CommunityEntity> list=communityDao.selectList(queryWrapper);
			ListUtil.copy(list,CommunityDto.class);
			CollectionUtils.addAll(communityList,ListUtil.copy(list,CommunityDto.class));
			return communityList;
		}
	}
}
