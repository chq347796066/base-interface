package com.spring.baseinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.base.entity.baseinfo.CustomerEntity;
import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.entity.pay.PayBillsEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.customer.CustomerAddVo;
import com.spring.base.vo.baseinfo.customer.CustomerUpdateVo;
import com.spring.base.vo.baseinfo.customer.CustomerVo;
import com.spring.base.vo.baseinfo.housingcertification.CertificationHouseAddVo;
import com.spring.baseinfo.dao.ICarDao;
import com.spring.baseinfo.dao.ICommunityDao;
import com.spring.baseinfo.dao.ICustomerDao;
import com.spring.baseinfo.dao.IHouseDao;
import com.spring.baseinfo.service.ICurrentUserInfoService;
import com.spring.baseinfo.service.ICustomerService;
import com.spring.common.constants.MessageCode;
import com.spring.common.feign.client.PayFeignClient;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.excel.EasyExcelUtils;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
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
 * @date : 创建时间：2020-04-03 17:35:27
 * @Desc类说明: 客户信息业务接口实现类
 */
@Slf4j
@Service("customerService")
public class CustomerServiceImpl extends BaseServiceImpl<CustomerEntity, String> implements ICustomerService {
	
	@Autowired
	private ICustomerDao customerDao;

	 @Autowired
	 private ICarDao carDao;

	 @Autowired
	 private IHouseDao houseDao;

	@Autowired
	private ICommunityDao communityDao;

	@Autowired
	private PayFeignClient payFeignClient;

	@Autowired
	private ICurrentUserInfoService currentUserInfoService;

	@Override
	public BaseDao getBaseMapper() {
		return customerDao;
	}
	
	/**
	 * 新增客户信息
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-03 17:35:27
	 */
	@Override
	public ApiResponseResult addCustomer(CustomerAddVo vo) {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		CustomerEntity entity = new CustomerEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		entity.setDelFlag(0);
		//赋值租户id,公司id
		CommunityEntity communityEntity=communityDao.selectById(vo.getCommunityId());
		if(communityEntity!=null)
		{
			entity.setTenantId(communityEntity.getTenantId());
			entity.setCompanyId(communityEntity.getCompanyId());
		}
		// 新增
		int no = customerDao.insert(entity);
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
	 * 更新客户信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-03 17:35:27
	 */
	@Override
	public ApiResponseResult updateCustomer(CustomerUpdateVo vo) {
		CustomerEntity entity = new CustomerEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		//赋值租户id,公司id
		CommunityEntity communityEntity=communityDao.selectById(vo.getCommunityId());
		if(communityEntity!=null)
		{
			entity.setTenantId(communityEntity.getTenantId());
			entity.setCompanyId(communityEntity.getCompanyId());
		}
		// 更新
		int no = customerDao.updateById(entity);
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
	 public ApiResponseResult deleteCarHouse(String id) throws Exception {
		 ApiResponseResult result = new ApiResponseResult();
	 	if(StringUtils.isNotEmpty(id)){
			HouseEntity houseEntity = new HouseEntity();
			houseEntity.setCustomerId(id);
			List<HouseEntity> housList = houseDao.queryList(houseEntity);
			if(CollectionUtils.isNotEmpty(housList)){
				for(int i=0;i<housList.size();i++){
					PayBillsEntity payBills = new PayBillsEntity();
					payBills.setHid(housList.get(i).getId());
					ApiResponseResult result1 = payFeignClient.queryList(payBills);
					if(result1.getData() != null) {
						List<LinkedMap> mapList = (List<LinkedMap>) result1.getData();
						if (mapList.size() > 0) {
							result.setCode(MessageCode.FAIL);
							result.setMsg("房产已生成账单，不能删除该客户");
							result.setData(null);
							return result;
						}
					}
				}
			}
			CustomerEntity customerEntity = new CustomerEntity();
			customerEntity.setTenantId(RequestUtils.getTenantId());
			customerEntity.setId(id);
			customerDao.deleteCustomer(customerEntity);
			result.setCode(MessageCode.SUCCESS);
			result.setMsg(MessageCode.SUCCESS_TEXT);
			result.setData(1);
		}
		 return result;
	 }

	@Override
	public ApiResponseResult viewCustomInfo(String id) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		CustomerEntity entity=new CustomerEntity();
		entity.setId(id);
		CustomerEntity customerEntity=customerDao.queryCustomerInfo(entity);
		if(customerEntity!=null){
			result.setCode(MessageCode.SUCCESS);
			result.setMsg(MessageCode.SUCCESS_TEXT);
			result.setData(customerEntity);
		}else{
			result.setCode(MessageCode.FAIL);
			result.setMsg(MessageCode.FAIL_TEXT);
			result.setData(null);
		}
		return result;
	}

    @Override
    public ApiResponseResult queryCustormAppUserPage(RequestPageVO<CustomerEntity> requestPageVO) throws Exception {
        PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
        List<CustomerEntity> list = customerDao.queryCustormAppUserPage(requestPageVO.getEntity());
        PageInfo<CustomerEntity> pageInfo = new PageInfo<CustomerEntity>(list);
        return createSuccessResult(pageInfo);
    }

    /**
     * @Desc:   客户信息导入
     * @param customerVos
     * @Author:邓磊
     * @UpdateDate:2020/4/21 17:33
     * @return: 返回
     */
	@Override
	public ApiResponseResult impUser(List<CustomerVo> customerVos, String[] tenantCompanyArray, String communityId) {
		ApiResponseResult result = new ApiResponseResult();
		CommunityEntity communityVO= new CommunityEntity();
		CommunityEntity entityVo = new CommunityEntity();
		entityVo.setId(communityId);
		if (tenantCompanyArray.length==1){
			entityVo.setTenantId(RequestUtils.getTenantId());
		}
		if (tenantCompanyArray.length==2){
			entityVo.setTenantId(RequestUtils.getTenantId());
			entityVo.setCompanyId(tenantCompanyArray[1]);
		}
		CommunityEntity entity1 = communityDao.queryCommunity(entityVo);
		if(CollectionUtils.isNotEmpty(customerVos)){
			if(StringUtils.isNotEmpty(customerVos.get(0).getCommunityName()) && entity1.getCommunityName().equals(customerVos.get(0).getCommunityName())){
				String communityName = customerVos.get(0).getCommunityName();
				CommunityEntity entity = new CommunityEntity();
				entity.setCommunityName(communityName);
				communityVO = communityDao.queryCommunity(entity);
			}else{
				result.setCode(MessageCode.FAIL);
				result.setMsg("填写的所属小区,与下拉选择的公司下的小区匹配不上,请填写下拉选择的公司小区");
				result.setData(null);
				return result;
			}
			List<CustomerEntity> customerEntityList = new ArrayList<>();
			int count = 0;
			for(int i=0;i<customerVos.size();i++){
				CustomerVo customerVo = customerVos.get(i);
				count++;
				ApiResponseResult apiResponseResult = checkCustomerExecl(count, customerVo, communityVO,entity1);
				if(apiResponseResult.getCode().equals(MessageCode.SUCCESS)){
					CustomerEntity custVo = new CustomerEntity();
					custVo.setCertificatesNumber(customerVo.getCertificatesNumber());
					custVo.setPhone(customerVo.getPhone());
					CustomerEntity customerEntity = customerDao.queryCustomerVo(custVo);
					if(null != customerEntity && StringUtils.isNotEmpty(customerEntity.getCertificatesNumber()) && StringUtils.isNotEmpty(customerEntity.getPhone())){
						result.setCode(MessageCode.FAIL);
						result.setMsg("第"+count+"行"+"客户身份证号手机号码已存在,请核实后填写");
						result.setData(null);
						return result;
					}else{
						//插入客户信息
						CustomerEntity vo = new CustomerEntity();
						vo.setId(UUIDFactory.createId());
						vo.setCommunityId(communityVO.getId());
						vo.setCommunityName(communityVO.getCommunityName());
						vo.setCustomerName(customerVo.getCustomerName());
						vo.setCertificatesNumber(customerVo.getCertificatesNumber());
						vo.setPhone(customerVo.getPhone());
						vo.setNativePlace(customerVo.getNativePlace());
						vo.setEducation(customerVo.getEducation());
						vo.setOccupation(customerVo.getOccupation());
						vo.setCommunityAddress(customerVo.getCommunityAddress());
						vo.setCreateUser(RequestUtils.getUserId());
						vo.setTenantId(RequestUtils.getTenantId());
						vo.setCreateDate(new Date());
						vo.setDelFlag(0);
						vo.setStatus("1");
						if ("男".equals(customerVo.getSex())) {
							vo.setSex(1);
						} else {
							vo.setSex(2);
						}
						if ("身份证".equals(customerVo.getCertificatesType())) {
						} else {
							vo.setCertificatesType(2);
						}
						if ("个人".equals(customerVo.getCustomerType())) {
							vo.setCustomerType("1");
						} else if ("单位".equals(customerVo.getCustomerType())) {
							vo.setCustomerType("2");
						} else {
							vo.setCustomerType("3");
						}
						customerEntityList.add(vo);
					}
				}else{
					return apiResponseResult;
				}
			}
			if(CollectionUtils.isNotEmpty(customerEntityList)){
				Integer integer = customerDao.addList(customerEntityList);
				if (integer > 0) {
					result.setCode(MessageCode.SUCCESS);
					result.setMsg("成功");
				} else {
					result.setCode(MessageCode.FAIL);
					result.setMsg("新增失败");
				}
				return result;
			}
		}else{
			result.setCode(MessageCode.FAIL);
			result.setMsg("请填写导入的数据");
			result.setData(null);
			return result;
		}
		return result;
	}

	/**
	 * @Desc: 批量导入客户信息校验
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/4/21 18:41
	 * @return: 返回
	 */
	public 	ApiResponseResult checkCustomerExecl(int count, CustomerVo vo, CommunityEntity communityVO,CommunityEntity entity1) {
		ApiResponseResult result = new ApiResponseResult();
		result.setCode(MessageCode.SUCCESS);
		result.setMsg(MessageCode.SUCCESS_TEXT);
		result.setData(1);
		if(communityVO  != null){
			if(StringUtils.isNotEmpty(vo.getCommunityName()) && entity1.getCommunityName().equals(vo.getCommunityName())){
				CommunityEntity comm = new CommunityEntity();
				comm.setCommunityName(vo.getCommunityName());
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
		if(StringUtils.isEmpty(vo.getCustomerName())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"客户名称不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(vo.getCustomerType())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"客户类别不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(vo.getCertificatesType())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"证件类型不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(vo.getCertificatesNumber())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"证件号码不能为空");
			result.setData(null);
			return result;
		}
        if(StringUtils.isEmpty(vo.getPhone())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"联系电话不能为空");
			result.setData(null);
			return result;
		}
		return result;
	}

  /**
   * @Desc: 客户信息批量导入下载模板
   * @param communityId
   * @Author:邓磊
   * @UpdateDate:2020/4/24 16:21
   * @return: 返回
   */
	@Override
	public void expUserDemo(HttpServletResponse response, String communityId)  {
		CommunityEntity vo = new CommunityEntity();
		vo.setId(communityId);
		CommunityEntity entity = communityDao.queryCommunity(vo);
		List<CustomerVo> customerList = new ArrayList<>();
		CustomerVo customerVo  = new CustomerVo();
		customerVo.setCommunityName(entity.getCommunityName());
		customerVo.setCustomerName("客户姓名必填");
		customerVo.setCustomerType("必填写其一/个人/单位/开发商");
		customerVo.setSex("必填写其一/男/女");
        customerVo.setCertificatesType("证件类型/填写身份证");
		customerVo.setCertificatesNumber("证件号码必填");
		customerVo.setPhone("联系电话必填");
		customerVo.setNativePlace("籍贯");
		customerVo.setEducation("学历");
		customerVo.setCommunityAddress("客户地址");
		CustomerVo customerVo1  = new CustomerVo();
		customerVo1.setCommunityName(entity.getCommunityName());
		customerVo1.setCustomerName("张三");
		customerVo1.setCustomerType("个人");
		customerVo1.setSex("男");
		customerVo1.setCertificatesType("身份证");
		customerVo1.setCertificatesNumber("500233199908192677");
		customerVo1.setPhone("13410119497");
		customerVo1.setNativePlace("广东省深圳市宝安区");
		customerVo1.setEducation("博士");
		customerVo1.setCommunityAddress("广东省深圳市宝安区劳动路2号");
		customerList.add(customerVo);
		customerList.add(customerVo1);
		if(customerList != null && customerList.size() > 0){
			EasyExcelUtils.exportExcel(customerList, null, "客户数据", CustomerVo.class, "客户信息.xls", response);
		}
	}

	/**
	 * @Desc:客户导出信息数据
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/5/15 14:09
	 * @return: 返回
	 */
	@Override
	public void exportCustomerEntityInfo(CustomerEntity vo) throws Exception {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletRequestAttributes.getResponse();
		if(null != vo){
			List<CustomerEntity> list = customerDao.queryList(vo);
			excelDownload(response,list);
		}
	}

	public void excelDownload(HttpServletResponse response,List<CustomerEntity> list) throws Exception {
		//表头数据
		String[] header = {"小区名称", "客户名字", "客户性别", "客户类型", "证件类型","证件号码","客户电话","客户地址"};
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("客户信息");
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
			row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getCustomerName()));
			if(null !=list.get(i).getSex()){
				if(list.get(i).getSex() ==1){
					row1.createCell(2).setCellValue(new HSSFRichTextString("男"));
				}else {
					row1.createCell(2).setCellValue(new HSSFRichTextString("女"));
				}
			}
			if(StringUtils.isNotEmpty(list.get(i).getCustomerType())){
				if("1".equals(list.get(i).getCustomerType())){
					row1.createCell(3).setCellValue(new HSSFRichTextString("个人"));
				}else if("2".equals(list.get(i).getCustomerType())){
					row1.createCell(3).setCellValue(new HSSFRichTextString("单位"));
				}else{
					row1.createCell(3).setCellValue(new HSSFRichTextString("开发商"));
				}
			}
			if(null !=list.get(i).getCertificatesType()){
				if(list.get(i).getCertificatesType() ==1){
					row1.createCell(4).setCellValue(new HSSFRichTextString("身份证"));
				}else{
					row1.createCell(4).setCellValue(new HSSFRichTextString("社会组织统一信用码"));
				}
			}
			row1.createCell(5).setCellValue(new HSSFRichTextString(list.get(i).getCertificatesNumber()));
			row1.createCell(6).setCellValue(new HSSFRichTextString(list.get(i).getPhone()));
			row1.createCell(7).setCellValue(new HSSFRichTextString(list.get(i).getCommunityAddress()));
		}
		response.setHeader("content-Type","application/ms-excel");
		String name = "客户信息";
		String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}

	@Override
	public ApiResponseResult queryCustomerPage(RequestPageVO<CustomerEntity> requestPageVO) throws Exception {
		//获取当前登录者所拥有的小区信息
		List<String> communityIds = currentUserInfoService.queryCurrentUserCommunityInfo();

		requestPageVO.getEntity().setCommunityIds(communityIds);
		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		log.info("queryCustomerPage userId:"+RequestUtils.getUserId()+",tenantId:"+requestPageVO.getEntity().getTenantId()+",companyId:"+requestPageVO.getEntity().getCompanyId()+",communityId:"+requestPageVO.getEntity().getCommunityId());
		List<CustomerEntity> customerList = customerDao.queryList(requestPageVO.getEntity());
		PageInfo<CustomerEntity> pageInfo = new PageInfo<CustomerEntity>(customerList);
		return createSuccessResult(pageInfo);
	}

	@Override
	public ApiResponseResult addCustomerHouseInfo(CertificationHouseAddVo vo) throws Exception {

		QueryWrapper<CustomerEntity> queryCustomerWrapper = new QueryWrapper<>();
		queryCustomerWrapper.lambda().eq(CustomerEntity :: getPhone,vo.getMobile()).ne(CustomerEntity :: getCertificatesNumber,vo.getCertificateNo());
		CustomerEntity queryCustomerEntity = customerDao.selectOne(queryCustomerWrapper);

		String customerId = queryCustomerEntity == null ? UUIDFactory.createId() : queryCustomerEntity.getId();
		if (queryCustomerEntity == null) {
			CustomerEntity customerEntity = new CustomerEntity();
			customerEntity.setId(customerId);
			customerEntity.setCommunityId(vo.getCommunityId());
			customerEntity.setCommunityName(vo.getCommunityName());
			customerEntity.setCustomerType(vo.getIdentityType()+"");
			customerEntity.setCustomerName(vo.getUserName());
			customerEntity.setCertificatesType(Integer.valueOf(vo.getCertificateType()));
			customerEntity.setCertificatesNumber(vo.getCertificateNo());
			customerEntity.setPhone(vo.getMobile());
			customerEntity.setCreateDate(new Date());
			customerEntity.setCreateUser(vo.getUserId());
			customerDao.insert(customerEntity);
		}

		QueryWrapper<HouseEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(HouseEntity :: getId,vo.getHouseId());
		HouseEntity queryHouseEntity = houseDao.selectOne(queryWrapper);
		queryHouseEntity.setCustomerId(customerId);
		queryHouseEntity.setOwnerMobile(vo.getMobile());
		queryHouseEntity.setOwnerName(vo.getUserName());
		queryHouseEntity.setOwnerCard(vo.getCertificateNo());
		queryHouseEntity.setModifyDate(new Date());
		queryHouseEntity.setModifyUser(vo.getUserId());
		houseDao.updateById(queryHouseEntity);
		return createSuccessResult(null);
	}
}
