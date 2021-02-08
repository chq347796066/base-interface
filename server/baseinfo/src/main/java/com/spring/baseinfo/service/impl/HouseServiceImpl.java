package com.spring.baseinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.*;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.entity.pay.PayBillsEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.house.HouseAddVo;
import com.spring.base.vo.baseinfo.house.HouseTemplateVo;
import com.spring.base.vo.baseinfo.house.HouseUpdateVo;
import com.spring.base.vo.baseinfo.housingcertification.HouseDeleteParamVo;
import com.spring.baseinfo.dao.*;
import com.spring.baseinfo.service.ICurrentUserInfoService;
import com.spring.baseinfo.service.IHouseService;
import com.spring.common.constants.Constants;
import com.spring.common.constants.MessageCode;
import com.spring.common.feign.client.BusinessFeignClient;
import com.spring.common.feign.client.MaintenanceFeignClient;
import com.spring.common.feign.client.PayFeignClient;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 房产信息业务接口实现类
 */
@Slf4j
@Service("houseService")
public class HouseServiceImpl extends BaseServiceImpl<HouseEntity, String> implements IHouseService {
	private static final Logger logger = LoggerFactory.getLogger(HouseServiceImpl.class);

	@Autowired
	private IHouseDao houseDao;

	@Autowired
	private ICustomerDao customerDao;

	@Autowired
	private ICommunityDao communityDao;

	@Autowired
	private IBuildDao  buildDao;

	@Autowired
	private ICellDao cellDao;

	@Autowired
	private PayFeignClient payBillsFeignCilnet;

	@Autowired
	private IUserDao iUserDao;

	@Autowired
	private ICurrentUserInfoService currentUserInfoService;

	@Autowired
	private BusinessFeignClient mybusinessFeignCilnet;

	@Override
	public BaseDao getBaseMapper() {
		return houseDao;
	}

	/**
	 * 新增房产信息
	 *
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-03-31 19:02:26
	 */
	@Override
	//@GlobalTransactional(timeoutMills = 60000,rollbackFor = Exception.class)
	@Transactional(rollbackFor = Exception.class)
	public ApiResponseResult addHouse(HouseAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		HouseEntity houseEntity = new HouseEntity();
		BeanUtils.copyProperties(vo, houseEntity);
		String customerId = houseEntity.getCustomerId();
		CustomerEntity customerParam = new CustomerEntity();
		customerParam.setId(customerId);
		CustomerEntity customerEntity = customerDao.queryCustomerVo(customerParam);
		if (customerEntity != null && StringUtils.isNotBlank(customerId)) {
			houseEntity.setOwnerName(customerEntity.getCustomerName());
		}
		houseEntity.setId(UUIDFactory.createId());
		houseEntity.setStatus(Constants.Status.ENABLE);
		houseEntity.setCreateUser(RequestUtils.getUserId());
		houseEntity.setCreateDate(new Date());
		//校验房屋编号是否重复
		HouseEntity houseParam = new HouseEntity();
		houseParam.setHouseCode(houseEntity.getHouseCode());
		houseParam.setBuildId(houseEntity.getBuildId());
		houseParam.setCellId(houseEntity.getCellId());
		List<HouseEntity> houseEntityList = houseDao.queryHouseCodeList(houseParam);
		if(houseEntityList.size()>0){
			result.setCode(MessageCode.FAIL);
			result.setMsg("你填写的房屋编号存在，请核实后请重新输入！");
			return result;
		}
		//赋值租户id,公司id
		CommunityEntity communityEntity=communityDao.selectById(vo.getCommunityId());
		if(communityEntity!=null)
		{
			houseEntity.setTenantId(communityEntity.getTenantId());
			houseEntity.setCompanyId(communityEntity.getCompanyId());
		}
		// 新增
		int no = houseDao.insert(houseEntity);


		//绑定了业主的房屋
		if (customerEntity != null  && StringUtils.isNotBlank(customerId)) {
			UserEntity userParam = new UserEntity();
			userParam.setMobile(customerEntity.getPhone());
			userParam.setHouseHoldType(Constants.HouseHoldType.WEIZUCE);
			List<UserEntity> userWaitList = iUserDao.queryMobileList(userParam);

			if (userWaitList.size() <= 0) {		//不存在未注冊,新增一条待注册的用户信息
				UserEntity userEntity = new UserEntity();
				userEntity.setId(UUIDFactory.createId());
				userEntity.setCommunityId(customerEntity.getCommunityId());
				userEntity.setCompanyId(customerEntity.getCompanyId());
				userEntity.setTenantId(customerEntity.getTenantId());
				userEntity.setUserName(customerEntity.getCustomerName());
				userEntity.setIdCard(customerEntity.getCertificatesNumber());
				userEntity.setMobile(customerEntity.getPhone());
				userEntity.setCreateDate(new Date());
				//未注册
				userEntity.setHouseHoldType(Constants.HouseHoldType.WEIZUCE);
				userEntity.setSex(customerEntity.getSex());
				userEntity.setSourceChannel(3);
				userEntity.setTenantAdmin(0);
				userEntity.setUserType(Constants.UserType.OWNER_APP);
				userEntity.setEnableStatusFlag(Constants.Status.ENABLE);
				userEntity.setDelFlag(0);
				userEntity.setCreateUser(RequestUtils.getUserId());
				iUserDao.insert(userEntity);

				houseEntity.setUserId(userEntity.getId());
			}

			//新增一条app用户的我的房屋
			if(communityEntity!=null){
				houseEntity.setCommunityAddress(communityEntity.getCommunityAddress());
				houseEntity.setCommunityName(communityEntity.getCommunityName());

				houseEntity.setCommunityAddressDetails(communityEntity.getCommunityAddressDetails());
				houseEntity.setCompanyName(communityEntity.getCompanyName());
			}

			mybusinessFeignCilnet.addHouseUserInfo(houseEntity);

		}
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
	 * 更新房产信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-03-31 19:02:26
	 */
	@Override
	public ApiResponseResult updateHouse(HouseUpdateVo vo) {
		ApiResponseResult result = new ApiResponseResult();
		HouseEntity entity = new HouseEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		String customerId = entity.getCustomerId();
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setId(customerId);
		CustomerEntity customerEntity1 = customerDao.queryCustomerVo(customerEntity);
		if(customerEntity1 !=null){
			entity.setOwnerName(customerEntity1.getCustomerName());
		}
		//校验房屋编号是否重复
		HouseEntity entity1 = new HouseEntity();
		entity1.setHouseCode(entity.getHouseCode());
		entity1.setBuildId(entity.getBuildId());
		entity1.setCellId(entity.getCellId());
		entity1.setId(entity.getId());
		List<HouseEntity> houseEntityList = houseDao.queryHouseCodeList(entity1);
		if(houseEntityList.size()>0){
			result.setCode(MessageCode.FAIL);
			result.setMsg("房屋编号存在，请核实后请重新输入！");
			return result;
		}
		//赋值租户id,公司id
		CommunityEntity communityEntity=communityDao.selectById(vo.getCommunityId());
		if(communityEntity!=null)
		{
			entity.setTenantId(communityEntity.getTenantId());
			entity.setCompanyId(communityEntity.getCompanyId());
		}
		// 更新
		int no = houseDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}


	/**
	 * 关联删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult deleteCustomer(String id) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		if(StringUtils.isNotEmpty(id)){
			HouseEntity houseEntity = new HouseEntity();
			houseEntity.setId(id);
			houseEntity.setTenantId(RequestUtils.getTenantId());
			PayBillsEntity payBills = new PayBillsEntity();
			payBills.setHid(id);
			ApiResponseResult result1 = payBillsFeignCilnet.queryList(payBills);
			if(result1.getData() != null){
				List<LinkedMap> mapList  = (List<LinkedMap>)result1.getData();
				if(mapList.size()>0){
					result.setCode(MessageCode.FAIL);
					result.setMsg("房产已生成账单，不能进行删除");
					result.setData(null);
				}else{
					houseEntity.setDelFlag(1);
					UpdateWrapper<HouseEntity> updateWrapper = new UpdateWrapper<>();
					updateWrapper.lambda().set(HouseEntity::getDelFlag,1);
					updateWrapper.lambda().eq(HouseEntity::getId,houseEntity.getId());
					houseDao.delete(updateWrapper);
					result.setCode(MessageCode.SUCCESS);
					result.setMsg(MessageCode.SUCCESS_TEXT);
					result.setData(1);
				}
			}
		}
		return result;
	}

	/**
	 * @Desc: 房产信息导入数据
	 * @param voList
	 * @Author:邓磊
	 * @UpdateDate:2020/4/27 14:01
	 * @return: 返回
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ApiResponseResult batchImportHouse(List<HouseTemplateVo> voList,String[] tenantCompanyArray,String communityId) {
		ApiResponseResult result = new ApiResponseResult();
		CommunityEntity communityVO = new CommunityEntity();
		Integer integer=2;
		CommunityEntity entityVo = new CommunityEntity();
		entityVo.setId(communityId);
		if (tenantCompanyArray.length==1){
			entityVo.setTenantId(RequestUtils.getTenantId());
		}

		if (tenantCompanyArray.length==integer){
			entityVo.setTenantId(RequestUtils.getTenantId());
			entityVo.setCompanyId(tenantCompanyArray[1]);
		}
		CommunityEntity entity1 = communityDao.queryCommunity(entityVo);
		if(CollectionUtils.isNotEmpty(voList)){
			if(StringUtils.isNotEmpty(voList.get(0).getCommunityName())  && entity1.getCommunityName().equals(voList.get(0).getCommunityName())){
				String communityName = voList.get(0).getCommunityName();
				CommunityEntity entity = new CommunityEntity();
				entity.setCommunityName(communityName);
				communityVO = communityDao.queryCommunity(entity);
			}else{
				result.setCode(MessageCode.FAIL);
				result.setMsg("填写的所属小区,与下拉选择的公司下的小区匹配不上,请填写下拉选择的公司小区");
				result.setData(null);
				return result;
			}
			int count = 0;
			List<HouseEntity> houseEntityArrayList = new ArrayList<>();
			List<CustomerEntity> customerEntityList = new ArrayList<>();
			List<BuildEntity> buildEntities = new ArrayList<>();
			List<CellEntity> cellEntities = new ArrayList<>();
			for(int i=0;i<voList.size();i++){
				count++;
				HouseTemplateVo houseTemplateVo = voList.get(i);
				ApiResponseResult  apiResponseResult = checkCarExecl(count,houseTemplateVo,communityVO,entity1);
				if(apiResponseResult.getCode().equals(MessageCode.SUCCESS)){
					//查询客户是否存在
					CustomerEntity customerVo = new CustomerEntity();
					customerVo.setCertificatesNumber(houseTemplateVo.getCertificatesNumber());
					customerVo.setPhone(houseTemplateVo.getPhone());
					CustomerEntity customerEntity1 = customerDao.queryCustomerVo(customerVo);
					if(null != customerEntity1 && StringUtils.isNotEmpty(customerEntity1.getCertificatesNumber()) && StringUtils.isNotEmpty(customerEntity1.getPhone())){
						result.setCode(MessageCode.FAIL);
						result.setMsg("第"+count+"行"+"客户身份证号手机号码已存在,请核实后填写");
						result.setData(null);
						return result;
					}else {
						//插入楼栋
						BuildEntity buildEntity = new BuildEntity();
						final String  bId = UUIDFactory.createId();
						buildEntity.setCommunityId(entityVo.getId());
						buildEntity.setBuildName(houseTemplateVo.getBuildName());
						buildEntity.setId(bId);
						buildEntity.setStatus(Constants.Status.ENABLE);
						buildEntity.setTenantId(entityVo.getTenantId());
						buildEntity.setCreateUser(entityVo.getTenantId());
						buildEntity.setCreateDate(new Date());
						buildEntity.setDelFlag(0);
						buildEntities.add(buildEntity);
						//插入单元
						CellEntity cellEntity = new CellEntity();
						final String ceId = UUIDFactory.createId();
						cellEntity.setId(ceId);
						cellEntity.setCommunityId(communityVO.getId());
						cellEntity.setBuildId(bId);
						cellEntity.setCellName(houseTemplateVo.getCellName());
						cellEntity.setStatus(Constants.Status.ENABLE);
						cellEntity.setTenantId(entityVo.getTenantId());
						cellEntity.setCreateUser(entityVo.getTenantId());
						cellEntity.setCreateDate(new Date());
						cellEntity.setDelFlag(0);
						cellEntities.add(cellEntity);
						//插入客户信息
						CustomerEntity customerEntity = new CustomerEntity();
						final String cId = UUIDFactory.createId();
						customerEntity.setId(cId);
						customerEntity.setCommunityId(communityVO.getId());
						customerEntity.setCommunityName(houseTemplateVo.getCommunityName());
						customerEntity.setCustomerName(houseTemplateVo.getCustomerName());
						customerEntity.setCertificatesNumber(houseTemplateVo.getCertificatesNumber());
						customerEntity.setPhone(houseTemplateVo.getPhone());
						customerEntity.setCreateUser(entityVo.getTenantId());
						customerEntity.setTenantId(entityVo.getTenantId());
						customerEntity.setCreateDate(new Date());
						customerEntity.setDelFlag(0);
						customerEntity.setStatus("1");
						if ("男".equals(houseTemplateVo.getSex())) {
							customerEntity.setSex(1);
						} else {
							customerEntity.setSex(2);
						}
						if ("身份证".equals(houseTemplateVo.getCertificatesType())) {
							customerEntity.setCertificatesType(1);
						} else {
							customerEntity.setCertificatesType(2);
						}
						if ("个人".equals(houseTemplateVo.getCustomerType())) {
							customerEntity.setCustomerType("1");
						} else if ("单位".equals(houseTemplateVo.getCustomerType())) {
							customerEntity.setCustomerType("2");
						} else {
							customerEntity.setCustomerType("3");
						}
						customerEntityList.add(customerEntity);

						//插入房产信息
						HouseEntity houseEntity = new HouseEntity();
						houseEntity.setId(UUIDFactory.createId());
						houseEntity.setCommunityId(communityVO.getId());
						houseEntity.setCustomerId(cId);
						houseEntity.setBuildId(bId);
						houseEntity.setCellId(ceId);
						houseEntity.setFloor(houseTemplateVo.getFloor());
						houseEntity.setHouseNo(houseTemplateVo.getHouseNo());
						houseEntity.setHouseCode(houseTemplateVo.getHouseCode());
						if("未售".equals(houseTemplateVo.getHouseStatus())){
							houseEntity.setHouseStatus(1);
						}else if("未装修".equals(houseTemplateVo.getHouseStatus())){
							houseEntity.setHouseStatus(2);
						}else{
							houseEntity.setHouseStatus(3);
						}
						if("住宅".equals(houseTemplateVo.getHouseType())){
							houseEntity.setHouseType(1);
						}else if("商业".equals(houseTemplateVo.getHouseType())){
							houseEntity.setHouseType(2);
						}else if("别墅".equals(houseTemplateVo.getHouseType())){
							houseEntity.setHouseType(3);
						}else if("高层".equals(houseTemplateVo.getHouseType())){
							houseEntity.setHouseType(4);
						}else if("平层".equals(houseTemplateVo.getHouseType())){
							houseEntity.setHouseType(5);
						}else if("联排".equals(houseTemplateVo.getHouseType())){
							houseEntity.setHouseType(6);
						}else{
							houseEntity.setHouseType(7);
						}
						houseEntity.setUseArea(houseTemplateVo.getUseArea());
						houseEntity.setArchitectureArea(houseTemplateVo.getArchitectureArea());
						houseEntity.setOwnerName(houseTemplateVo.getCustomerName());
						houseEntity.setOwnerMobile(houseTemplateVo.getPhone());
						houseEntity.setOwnerCard(houseTemplateVo.getCertificatesNumber());
						houseEntity.setStatus(Constants.Status.ENABLE);
						houseEntity.setTenantId(entityVo.getTenantId());
						houseEntity.setCreateUser(entityVo.getTenantId());
						houseEntity.setCreateDate(new Date());
						houseEntity.setDelFlag(0);
						houseEntityArrayList.add(houseEntity);
					}
				}else{
					return apiResponseResult;
				}
			}
			if(CollectionUtils.isNotEmpty(customerEntityList)){
				integer = customerDao.addList(customerEntityList);
			}
			if(CollectionUtils.isNotEmpty(houseEntityArrayList)){
				integer = houseDao.addList(houseEntityArrayList);
			}
			if(CollectionUtils.isNotEmpty(buildEntities)){
				integer = buildDao.addList(buildEntities);
			}
			if(CollectionUtils.isNotEmpty(cellEntities)){
				integer = cellDao.addList(cellEntities);
			}
			if (integer > 0) {
				result.setCode(MessageCode.SUCCESS);
				result.setMsg("成功");
			} else {
				result.setCode(MessageCode.FAIL);
				result.setMsg("新增失败");
			}
			return result;
		}else{
			result.setCode(MessageCode.FAIL);
			result.setMsg("请填写导入的数据");
			result.setData(null);
			return result;
		}
	}

	/**
	 * @Desc: 详情
	 * @param entity
	 * @Author:邓磊
	 * @UpdateDate:2020/5/13 10:15
	 * @return: 返回
	 */
	@Override
	public HouseEntity queryHouseInfo(HouseEntity entity) throws Exception {
		return  houseDao.queryHouseInfo(entity);
	}

	/**
	 * @Desc: 房屋审核确认房屋信息
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/5/13 10:15
	 * @return: 返回
	 */
	@Override
	public ApiResponseResult queryOwnerNameMobile(HouseEntity vo) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		HouseEntity houseEntity = houseDao.queryOwnerNameMobile(vo);
		result.setCode(MessageCode.SUCCESS);
		result.setMsg(MessageCode.SUCCESS_TEXT);
		result.setData(houseEntity);
		return result;
	}

	/**
	 * @Desc: 房产导出数据信息
	 * @param houseEntity
	 * @Author:邓磊
	 * @UpdateDate:2020/5/15 10:17
	 * @return: 返回
	 */
	@Override
	public void exportHouseEntityInfo(HouseEntity houseEntity) throws Exception {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletRequestAttributes.getResponse();
		if(null != houseEntity){
			List<HouseEntity> houseList = houseDao.queryList(houseEntity);
			excelDownload(response,houseList);
		}
	}

	public void excelDownload(HttpServletResponse response,List<HouseEntity>  list) throws Exception {
		//表头数据
		String[] header = {"小区名称", "所属楼栋", "单元", "楼层", "房屋编号","房屋类型","房屋状态","建筑面积(m2)","业主名字"};
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("房产信息");
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
			row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getBuildName()));
			row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getCellName()));
			row1.createCell(3).setCellValue(new HSSFRichTextString(list.get(i).getFloor()));
			row1.createCell(4).setCellValue(new HSSFRichTextString(list.get(i).getHouseCode()));
			if(null != list.get(i).getHouseType()){
				if(list.get(i).getHouseType() == 1){
					row1.createCell(5).setCellValue(new HSSFRichTextString("住宅"));
				}else if(list.get(i).getHouseType() == 2){
					row1.createCell(5).setCellValue(new HSSFRichTextString("商业"));
				}else if(list.get(i).getHouseType() == 3){
					row1.createCell(5).setCellValue(new HSSFRichTextString("别墅"));
				}else if(list.get(i).getHouseType() == 4){
					row1.createCell(5).setCellValue(new HSSFRichTextString("高层"));
				}else if(list.get(i).getHouseType() == 5){
					row1.createCell(5).setCellValue(new HSSFRichTextString("平层"));
				}else if(list.get(i).getHouseType() == 6){
					row1.createCell(5).setCellValue(new HSSFRichTextString("联排"));
				}else{
					row1.createCell(5).setCellValue(new HSSFRichTextString("独栋"));
				}
			}
			if(null != list.get(i).getHouseStatus()){
				if(list.get(i).getHouseStatus() == 1){
					row1.createCell(6).setCellValue(new HSSFRichTextString("未售"));
				}else if(list.get(i).getHouseStatus() == 2){
					row1.createCell(6).setCellValue(new HSSFRichTextString("未装修"));
				}else{
					row1.createCell(6).setCellValue(new HSSFRichTextString("已入住"));
				}
			}
			row1.createCell(7).setCellValue(new HSSFRichTextString(list.get(i).getArchitectureArea()));
			row1.createCell(8).setCellValue(new HSSFRichTextString(list.get(i).getOwnerName()));
		}
		String name = "房产信息";
		response.setHeader("content-Type","application/ms-excel");
		String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}

	/**
	 * @Desc: 房产信息导入校验
	 * @param communityVO,buildEntities,cellEntities
	 * @Author:邓磊
	 * @UpdateDate:2020/4/27 15:39
	 * @return: 返回
	 */
	public ApiResponseResult checkCarExecl(int count,HouseTemplateVo houseTemplateVo,CommunityEntity communityVO,CommunityEntity entityVo){
		ApiResponseResult result = new ApiResponseResult();
		result.setCode(MessageCode.SUCCESS);
		result.setMsg(MessageCode.SUCCESS_TEXT);
		result.setData(1);
		if(communityVO  != null){
			if(StringUtils.isNotEmpty(houseTemplateVo.getCommunityName()) && entityVo.getCommunityName().equals(houseTemplateVo.getCommunityName())){
				CommunityEntity comm = new CommunityEntity();
				comm.setCommunityName(houseTemplateVo.getCommunityName());
				comm.setId(communityVO.getId());
				CommunityEntity entity = communityDao.queryCommunity(comm);
				if(entity == null){
					result.setCode(MessageCode.FAIL);
					result.setMsg("第"+count+"行"+"填写的所属小区,与下拉选择的公司下的小区匹配不上,请填写下拉选择的公司小区");
					result.setData(null);
					return result;
				}
			}else{
				result.setCode(MessageCode.FAIL);
				result.setMsg("填写的所属小区,与下拉选择的公司下的小区匹配不上,请填写下拉选择的公司小区");
				result.setData(null);
				return result;
			}
		}else{
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"填写的所属小区,与下拉选择的公司下的小区匹配不上,请填写下拉选择的公司小区");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(houseTemplateVo.getBuildName())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"楼栋不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(houseTemplateVo.getCellName())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"单元不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(houseTemplateVo.getFloor())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"楼层不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(houseTemplateVo.getHouseNo())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"门牌号不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(houseTemplateVo.getHouseCode())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"房屋编码不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(houseTemplateVo.getArchitectureArea())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"建筑面积不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(houseTemplateVo.getCustomerName())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"客户姓名不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(houseTemplateVo.getSex())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"客户性别不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(houseTemplateVo.getCustomerType())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"客户类别不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(houseTemplateVo.getPhone())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"客户电话不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(houseTemplateVo.getCertificatesType())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"客户证件类型不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(houseTemplateVo.getCertificatesNumber())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"客户身份证号码不能为空");
			result.setData(null);
			return result;
		}
		return result;
	}

	/**
	 * 根据条件分页查询房产
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult queryHousePage(RequestPageVO<HouseEntity> requestPageVO) {
		//获取当前登录者所拥有的小区信息
		List<String> communityIds = currentUserInfoService.queryCurrentUserCommunityInfo();

		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		requestPageVO.getEntity().setCommunityIds(communityIds);

		log.info("queryHousePage:tenantId:"+requestPageVO.getEntity().getTenantId()+",companyId:"+requestPageVO.getEntity().getCompanyId()+",communityId:"+requestPageVO.getEntity().getCommunityId());
		List<HouseEntity> houseList = houseDao.queryHouseList(requestPageVO.getEntity());
		PageInfo<HouseEntity> pageInfo = new PageInfo<>(houseList);
		return createSuccessResult(pageInfo);
	}

	@Override
	public List<LinkedHashMap<String, String>> queryExportList(Map<String, Object> params) {
		return houseDao.queryExportList(params);
	}

	@Override
	public ApiResponseResult addHouseUser(MyHouseEntity myHouseEntity) {
		HouseEntity houseEntity = new HouseEntity();
		QueryWrapper<HouseEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(HouseEntity :: getId,myHouseEntity.getHouseId());
		HouseEntity queryHouseEntity = houseDao.selectOne(queryWrapper);
		BeanUtils.copyProperties(queryHouseEntity,houseEntity);
		houseEntity.setId(UUIDFactory.createId());
		houseEntity.setOwnerName(myHouseEntity.getOwnerName());
		houseEntity.setOwnerMobile(myHouseEntity.getOwnerPhone());
		houseEntity.setOwnerCard(myHouseEntity.getCertificateNo());
		houseDao.insert(houseEntity);
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setId(UUIDFactory.createId());
		customerEntity.setCommunityId(myHouseEntity.getCommunityId());
		customerEntity.setCommunityName(myHouseEntity.getCommunityName());
		customerEntity.setCustomerType(myHouseEntity.getIdentityType()+"");
		customerEntity.setCustomerName(myHouseEntity.getOwnerName());
		customerEntity.setSex(myHouseEntity.getSex());
		customerEntity.setCertificatesType(myHouseEntity.getCertificateType());
		customerEntity.setCertificatesNumber(myHouseEntity.getCertificateNo());
		customerEntity.setPhone(myHouseEntity.getOwnerPhone());
		customerEntity.setCompanyId(myHouseEntity.getCompanyId());
		customerDao.insert(customerEntity);
		return createSuccessResult(null);
	}

	@Override
	public ApiResponseResult deleteHouseUser(HouseDeleteParamVo houseDeleteParamVo) {
		houseDao.updateHouserUserStatus(houseDeleteParamVo);

		return createSuccessResult(null);
	}
}

