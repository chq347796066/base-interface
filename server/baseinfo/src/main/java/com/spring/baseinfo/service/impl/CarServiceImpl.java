package com.spring.baseinfo.service.impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.CarEntity;
import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.base.entity.baseinfo.CustomerEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.car.CarAddVo;
import com.spring.base.vo.baseinfo.car.CarUpdateVo;
import com.spring.base.vo.baseinfo.car.CarVo;
import com.spring.baseinfo.dao.ICarDao;
import com.spring.baseinfo.dao.ICommunityDao;
import com.spring.baseinfo.dao.ICustomerDao;
import com.spring.baseinfo.service.ICarService;
import com.spring.common.constants.Constants;
import com.spring.common.constants.MessageCode;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.excel.EasyExcelUtils;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
 * @Desc类说明:  车位信息业务接口实现类
 */
@Slf4j
@Service("carService")
public class CarServiceImpl extends BaseServiceImpl<CarEntity, String> implements ICarService {
	
	@Autowired
	private ICarDao carDao;

	 @Autowired
	 private ICustomerDao customerDao;

	@Autowired
	private ICommunityDao communityDao;


	@Override
	public BaseDao getBaseMapper() {
		return carDao;
	}
	
	/**
	 * 新增 车位信息
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-03-31 19:02:26
	 */
	@Override
	public ApiResponseResult addCar(CarAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		CarEntity entity = new CarEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setStatus(Constants.Status.ENABLE);
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		//赋值租户id,公司id
		CommunityEntity communityEntity=communityDao.selectById(vo.getCommunityId());
		if(communityEntity!=null)
		{
			entity.setTenantId(communityEntity.getTenantId());
			entity.setCompanyId(communityEntity.getCompanyId());
		}
		// 新增
		int no = carDao.insert(entity);
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
	 * 更新 车位信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-31 19:02:26
	 */
	@Override
	public ApiResponseResult updateCar(CarUpdateVo vo) throws Exception {
		CarEntity entity = new CarEntity();
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
		int no = carDao.updateById(entity);
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
				CarEntity carEntity = new CarEntity();
				carEntity.setId(id);
				carEntity.setTenantId(RequestUtils.getTenantId());
				carDao.deleteCar(carEntity);
				result.setCode(MessageCode.SUCCESS);
				result.setMsg(MessageCode.SUCCESS_TEXT);
				result.setData(1);
			}
		 return result;
	 }

	 /**
	  * @Desc:    车位删除
	  * @param id
	  * @Author:邓磊
	  * @UpdateDate:2020/4/18 11:36
	  * @return: ApiResponseResult
	  */
	@Override
	public ApiResponseResult deleteCra(String id) {
		CarEntity carEntity = new CarEntity();
		carEntity.setId(id);
		carEntity.setTenantId(RequestUtils.getTenantId());
		int no = carDao.deleteCar(carEntity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}


	/**
	 * @Desc:    java类作用描述
	 * @param users
	 * @Author:邓磊
	 * @UpdateDate:2020/4/21 10:47
	 * @return: 返回
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ApiResponseResult impUser(List<CarVo> users, String[] tenantCompanyArray, String communityId){
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
		if(CollectionUtils.isNotEmpty(users)){
			if(StringUtils.isNotEmpty(users.get(0).getCommunityName())
					&& entity1.getCommunityName().equals(users.get(0).getCommunityName())){
				String communityName = users.get(0).getCommunityName();
				CommunityEntity entity = new CommunityEntity();
				entity.setCommunityName(communityName);
				communityVO = communityDao.queryCommunity(entity);
			}else{
				result.setCode(MessageCode.FAIL);
				result.setMsg("填写的所属小区,与下拉选择的公司下的小区匹配不上,请填写下拉选择的公司小区");
				result.setData(null);
				return result;
			}
			List<CarEntity> carEntityList = new ArrayList<>();
			List<CustomerEntity> customerEntityList = new ArrayList<>();
			int count = 0;
			for(int i=0;i<users.size();i++){
				count++;
				CarVo carVo = users.get(i);
				ApiResponseResult apiResponseResult = checkCarExecl(count, carVo, communityVO,entity1);
				if(apiResponseResult.getCode().equals(MessageCode.SUCCESS)){
					 //查询客户是否存在
					CustomerEntity customerVo = new CustomerEntity();
					customerVo.setCertificatesNumber(carVo.getCertificatesNumber());
					customerVo.setPhone(carVo.getPhone());
					CustomerEntity customerEntity1 = customerDao.queryCustomerVo(customerVo);
					if(null != customerEntity1 && StringUtils.isNotEmpty(customerEntity1.getCertificatesNumber()) && StringUtils.isNotEmpty(customerEntity1.getPhone())){
						result.setCode(MessageCode.FAIL);
						result.setMsg("第"+count+"行"+"客户身份证号手机号码已存在,请核实后填写");
						result.setData(null);
						return result;
					}else {
						//插入客户信息
						CustomerEntity customerEntity = new CustomerEntity();
						final String id = UUIDFactory.createId();
						customerEntity.setId(id);
						customerEntity.setCommunityId(entityVo.getId());
						customerEntity.setCommunityName(carVo.getCommunityName());
						customerEntity.setCustomerName(carVo.getCustomerName());
						customerEntity.setCertificatesNumber(carVo.getCertificatesNumber());
						customerEntity.setPhone(carVo.getPhone());
						customerEntity.setCreateUser(entityVo.getTenantId());
						customerEntity.setTenantId(entityVo.getTenantId());
						customerEntity.setCreateDate(new Date());
						customerEntity.setDelFlag(0);
						customerEntity.setStatus("1");
						if ("男".equals(carVo.getSex())) {
							customerEntity.setSex(1);
						} else {
							customerEntity.setSex(2);
						}
						//该类型现在只有身份证
						if ("身份证".equals(carVo.getCertificatesType())) {
							customerEntity.setCertificatesType(1);
						} else {
							customerEntity.setCertificatesType(1);
						}
						if ("个人".equals(carVo.getCustomerType())) {
							customerEntity.setCustomerType("1");
						} else if ("单位".equals(carVo.getCustomerType())) {
							customerEntity.setCustomerType("2");
						} else {
							customerEntity.setCustomerType("3");
						}
						customerEntityList.add(customerEntity);

						//插入车位信息
						CarEntity entity = new CarEntity();
						entity.setId(UUIDFactory.createId());
						entity.setCustomerId(id);
						entity.setCommunityId(entityVo.getId());
						entity.setCarCode(carVo.getCarCode());
						if ("子母车位".equals(carVo.getCarType())) {
							entity.setCarType(1);
						} else if ("普通车位".equals(carVo.getCarType())) {
							entity.setCarType(2);
						} else {
							entity.setCarType(3);
						}
						if ("未售".equals(carVo.getCarStatus())) {
							entity.setCarStatus(1);
						} else if ("已售".equals(carVo.getCarStatus())) {
							entity.setCarStatus(2);
						} else {
							entity.setCarStatus(3);
						}
						entity.setArea(carVo.getArea());
						entity.setStatus(Constants.Status.ENABLE);
						entity.setTenantId(entityVo.getTenantId());
						entity.setCreateUser(entityVo.getTenantId());
						entity.setCreateDate(new Date());
						carEntityList.add(entity);
					}
				}else{
					return apiResponseResult;
				}
			}
			Integer integer =0;
			if(CollectionUtils.isNotEmpty(customerEntityList)){
				integer = customerDao.addList(customerEntityList);
			}
			if(CollectionUtils.isNotEmpty(carEntityList)){
				integer = carDao.addList(carEntityList);
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
	 * @Desc:   校验导入车位
	 * @param carVo
	 * @Author:邓磊
	 * @UpdateDate:2020/4/21 14:09
	 * @return: 返回
	 */
	public 	ApiResponseResult checkCarExecl(int count,CarVo carVo,CommunityEntity communityVO,CommunityEntity entity1){
		ApiResponseResult result = new ApiResponseResult();
		result.setCode(MessageCode.SUCCESS);
		result.setMsg(MessageCode.SUCCESS_TEXT);
		result.setData(1);
        if(communityVO  != null){
            if(StringUtils.isNotEmpty(carVo.getCommunityName()) && entity1.getCommunityName().equals(carVo.getCommunityName())){
                    CommunityEntity comm = new CommunityEntity();
                    comm.setCommunityName(carVo.getCommunityName());
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
		if(StringUtils.isNotEmpty(carVo.getCarCode())){
			CarEntity car = new CarEntity();
			car.setCarCode(carVo.getCarCode());
			car.setCommunityId(communityVO.getId());
			CarEntity carEntity = carDao.queryCar(car);
			if(null !=carEntity && StringUtils.isNotEmpty(carEntity.getCarCode())){
				result.setCode(MessageCode.FAIL);
				result.setMsg("第"+count+"行"+"车位编号已存在,请重新填写");
				result.setData(null);
				return result;
			}
		}else{
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"车位编号不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(carVo.getCustomerName())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"客户姓名不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(carVo.getSex())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"客户性别不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(carVo.getCustomerType())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"客户类型不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(carVo.getCustomerType())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"客户类型不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(carVo.getPhone())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"客户电话不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(carVo.getCertificatesType())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"证件类型不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(carVo.getCertificatesNumber())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"证件号码不能为空");
			result.setData(null);
			return result;
		}
		return result;
	}


	/**
	 * @Desc: 车位信息批量导入下载模板
	 * @param communityId
	 * @Author:邓磊
	 * @UpdateDate:2020/4/24 14:56
	 */
	@Override
	public void expUserDemo(HttpServletResponse response, String communityId) throws Exception {
		CommunityEntity vo = new CommunityEntity();
		vo.setId(communityId);
		CommunityEntity entity = communityDao.queryCommunity(vo);
		List<CarVo> carList = new ArrayList<>();
		CarVo carVo = new CarVo();
		carVo.setCommunityName(entity.getCommunityName());
		carVo.setCarCode("车位编码必填");
		carVo.setCarType("填写其一/普通车位/母子车位/人防车位/");
		carVo.setCarStatus("填写其一/已售/未售/出租");
		carVo.setArea("20");
		carVo.setCustomerName("客户姓名必填");
		carVo.setSex("必填写其一/男/女");
		carVo.setCustomerType("必填写其一/个人/单位/开发商");
		carVo.setPhone("客户电话必填");
		carVo.setCertificatesType("证件类型/填写身份证");
		carVo.setCertificatesNumber("证件号码必填");
		CarVo carVo1 = new CarVo();
		carVo1.setCommunityName(entity.getCommunityName());
		carVo1.setCarCode("T001");
		carVo1.setCarType("普通车位");
		carVo1.setCarStatus("已售");
		carVo1.setArea("30");
		carVo1.setCustomerName("张三");
		carVo1.setSex("男");
		carVo1.setCustomerType("个人");
		carVo1.setPhone("13410119497");
		carVo1.setCertificatesType("身份证");
		carVo1.setCertificatesNumber("500233199908192677");
		carList.add(carVo);
		carList.add(carVo1);
		if(carList != null && carList.size() > 0){
			EasyExcelUtils.exportExcel(carList, null, "车位数据", CarVo.class, "车位信息.xls", response);
		}
	}


	/**
	 * @Desc:车位导出信息数据
	 * @param requestVO
	 * @Author:邓磊
	 * @UpdateDate:2020/5/15 11:21
	 * @return: 返回
	 */
	@Override
	public void exportCarEntityInfo(CarEntity requestVO) throws Exception {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletRequestAttributes.getResponse();
		 if(null != requestVO){
		    List<CarEntity> carList = carDao.queryList(requestVO);
		    excelDownload(response,carList);
		 }
	}

	public void excelDownload(HttpServletResponse response,List<CarEntity>  list) throws Exception {
		//表头数据
		String[] header = {"小区名称", "车位编码", "车位面积(m2)", "车位类型", "车位状态","车牌号","业主名字","业主电话"};
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("车位信息");
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
			row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getCarCode()));
			row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getArea()));
			if(null !=list.get(i).getCarType()){
				if(list.get(i).getCarType() ==1){
					row1.createCell(3).setCellValue(new HSSFRichTextString("字母车位"));
				}else if(list.get(i).getCarType() ==2){
					row1.createCell(3).setCellValue(new HSSFRichTextString("普通车位"));
				}else{
					row1.createCell(3).setCellValue(new HSSFRichTextString("人防车位"));
				}
			}
			if(null != list.get(i).getCarStatus()){
				if(list.get(i).getCarStatus() ==1){
					row1.createCell(4).setCellValue(new HSSFRichTextString("未售"));
				}else if(list.get(i).getCarStatus() ==2){
					row1.createCell(4).setCellValue(new HSSFRichTextString("已售"));
				}else{
					row1.createCell(4).setCellValue(new HSSFRichTextString("已出租"));
				}
			}
			row1.createCell(5).setCellValue(new HSSFRichTextString(list.get(i).getCarNo()));
			row1.createCell(6).setCellValue(new HSSFRichTextString(list.get(i).getCustomerName()));
			row1.createCell(7).setCellValue(new HSSFRichTextString(list.get(i).getPhone()));
		}
		response.setHeader("content-Type","application/ms-excel");
		String name = "车位信息";
		String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}

	/**
	 * @description:按条件分页查询车位
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/6/10 17:53
	 */
	@Override
	public ApiResponseResult queryCarPage(RequestPageVO<CarEntity> requestPageVO) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		//log.info("queryCarPage userId:"+RequestUtils.getUserId()+",tenantId:"+requestPageVO.getEntity().getTenantId()+",companyId:"+requestPageVO.getEntity().getCompanyId()+",communityId:"+requestPageVO.getEntity().getCommunityId());
		List<CarEntity> list = carDao.queryList(requestPageVO.getEntity());
		PageInfo<CarEntity> pageInfo = new PageInfo<CarEntity>(list);
		return createSuccessResult(pageInfo);
	}

}
