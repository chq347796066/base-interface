package com.spring.baseinfo.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.*;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.houseinstrument.HouseInstrumentAddVo;
import com.spring.base.vo.baseinfo.houseinstrument.HouseInstrumentUpdateVo;
import com.spring.baseinfo.dao.*;
import com.spring.baseinfo.service.IHouseInstrumentService;
import com.spring.common.constants.MessageCode;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.SnowflakeIdWorker;
import org.apache.commons.collections.CollectionUtils;
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
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-03 09:35:57
 * @Desc类说明: 房屋仪管理业务接口实现类
 */

@Service("houseInstrumentService")
public class HouseInstrumentServiceImpl extends BaseServiceImpl<HouseInstrumentEntity, Long> implements IHouseInstrumentService {
	@Autowired
	private IHouseInstrumentDao houseInstrumentDao;

	 @Autowired
	 private IInstrumentDetailDao instrumentDetailDao;

	@Autowired
	private ICommunityDao communityDao;

	@Autowired
	private IBuildDao buildDao;

	@Autowired
	private IHouseDao houseDao;

	@Autowired
	private IInstrumentTypeDao iInstrumentTypeDao;

	@Override
	public BaseDao getBaseMapper() {
		return houseInstrumentDao;
	}
	
	/**
	 * 新增房屋仪管理
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-03 09:35:57
	 */
	@Override
	public ApiResponseResult addHouseInstrument(HouseInstrumentAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		HouseInstrumentEntity entity = new HouseInstrumentEntity();
		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
		BeanUtils.copyProperties(vo, entity);
		entity.setId(idWorker.nextId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setTenantId(RequestUtils.getTenantId());
		entity.setDelFlag(0);
		entity.setCreateDate(new Date());
		// 新增
		int no = houseInstrumentDao.insert(entity);
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
	 * 更新房屋仪管理
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-03 09:35:57
	 */
	@Override
	public ApiResponseResult updateHouseInstrument(HouseInstrumentUpdateVo vo) throws Exception {
		HouseInstrumentEntity entity = new HouseInstrumentEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = houseInstrumentDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	 /**
	  * 关联删除
	  * @param id
	  * @return 仪表明细
	  * @throws Exception
	  */
	 @Override
	 public ApiResponseResult deleteDetail(Long id) throws Exception {
		 ApiResponseResult result = new ApiResponseResult();
		 HouseInstrumentEntity houseEntity = new HouseInstrumentEntity();
		 houseEntity.setId(id);
		 houseInstrumentDao.modifyDelFlag(houseEntity);
		 result.setCode(MessageCode.SUCCESS);
		 result.setMsg(MessageCode.SUCCESS_TEXT);
		 result.setData(1);
		 return result;
	 }

	 /**
	  * @Desc:查看房屋仪表详情
	  * @param vo
	  * @Author:邓磊
	  * @UpdateDate:2020/6/2 15:37
	  * @return: 返回
	  */
	@Override
	public ApiResponseResult queryHouseInstrumentInfo(HouseInstrumentEntity vo) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		HouseInstrumentEntity houseInstrumentEntity = houseInstrumentDao.queryHouseInstrumentInfo(vo);
		result.setCode(MessageCode.SUCCESS);
		result.setMsg(MessageCode.SUCCESS_TEXT);
		result.setData(houseInstrumentEntity);
		return result;
	}

	/**
	 * @Desc:房屋仪表批量导入
	 * @param voList
	 * @Author:邓磊
	 * @UpdateDate:2020/6/2 16:42
	 * @return: 返回
	 */
	@Override
	public ApiResponseResult batchImportHouseInstrument(List<HouseInstrumentAddVo> voList, String companyId, String communityId) {
		ApiResponseResult result = new ApiResponseResult();
		CommunityEntity communityVO= new CommunityEntity();
		CommunityEntity entityVo = new CommunityEntity();
		entityVo.setId(communityId);
		entityVo.setCompanyId(companyId);
		CommunityEntity entity1 = communityDao.queryCommunity(entityVo);
		if(CollectionUtils.isNotEmpty(voList)){
			if(StringUtils.isNotEmpty(voList.get(0).getCommunityName()) && entity1.getCommunityName().equals(voList.get(0).getCommunityName())){
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
			List<HouseInstrumentEntity> houseInstrumentList = new ArrayList<>();
			int count =0;
			SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
			for(int i=0;i<voList.size();i++){
				count++;
				HouseInstrumentAddVo houseInstrumentVo = voList.get(i);
				ApiResponseResult apiResponseResult = checkCarExecl(count, houseInstrumentVo, communityVO,entity1);
				if(apiResponseResult.getCode().equals(MessageCode.SUCCESS)){
					Map<String,String> map = (LinkedHashMap) apiResponseResult.getData();
					HouseInstrumentEntity entity = new HouseInstrumentEntity();
					entity.setId(idWorker.nextId());
					entity.setCommunityId(communityVO.getId());
					entity.setBuildId(map.get("buildId"));
					entity.setHouseId(map.get("houseId"));
					entity.setInstrumentId(map.get("instrumentId"));
					entity.setInstrumentName(houseInstrumentVo.getInstrumentName());
					boolean ratio=houseInstrumentVo.getRatio().matches("[0-9]+");
					if(ratio ==true){
						entity.setRatio(Integer.parseInt(houseInstrumentVo.getRatio()));
					}else{
						entity.setRatio(0);
					}
					BigDecimal minReading = new BigDecimal(houseInstrumentVo.getMinReading());
					minReading=minReading.setScale(2, BigDecimal.ROUND_DOWN);
					entity.setMinReading(minReading);
					BigDecimal maxReading = new BigDecimal(houseInstrumentVo.getMaxReading());
					maxReading=maxReading.setScale(2, BigDecimal.ROUND_DOWN);
					entity.setMaxReading(maxReading);
					entity.setInitialDate(houseInstrumentVo.getInitialDate());
					entity.setCreateUser(RequestUtils.getUserId());
					entity.setTenantId(RequestUtils.getTenantId());
					entity.setDelFlag(0);
					entity.setCreateDate(new Date());
					houseInstrumentList.add(entity);
				}else{
					return apiResponseResult;
				}
			}
			int integer = 0;
			if(CollectionUtils.isNotEmpty(houseInstrumentList)){
				integer = houseInstrumentDao.addList(houseInstrumentList);
			}
			if (integer > 0) {
				result.setCode(MessageCode.SUCCESS);
				result.setMsg("成功");
			} else {
				result.setCode(MessageCode.FAIL);
				result.setMsg("新增失败");
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
	 * @Desc:导入校验
	 * @param houseInstrumentVo
	 * @Author:邓磊
	 * @UpdateDate:2020/6/2 17:09
	 * @return: 返回
	 */
	public 	ApiResponseResult checkCarExecl(int count, HouseInstrumentAddVo houseInstrumentVo, CommunityEntity communityVO, CommunityEntity entity1){
		ApiResponseResult result = new ApiResponseResult();
		Map<String,String> map = new HashMap<>(16);
		result.setCode(MessageCode.SUCCESS);
		result.setMsg(MessageCode.SUCCESS_TEXT);
		result.setData(map);
		if(communityVO  != null){
			if(StringUtils.isNotEmpty(houseInstrumentVo.getCommunityName()) && entity1.getCommunityName().equals(houseInstrumentVo.getCommunityName())){
				CommunityEntity comm = new CommunityEntity();
				comm.setCommunityName(houseInstrumentVo.getCommunityName());
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
		if(StringUtils.isNotEmpty(houseInstrumentVo.getBuildName())){
			BuildEntity buildVo = new  BuildEntity();
			buildVo.setBuildName(houseInstrumentVo.getBuildName());
			buildVo.setCommunityId(communityVO.getId());
			List<BuildEntity> buildEntityList = buildDao.queryBuildName(buildVo);
			if(CollectionUtils.isEmpty(buildEntityList)){
				result.setCode(MessageCode.FAIL);
				result.setMsg("第"+count+"行"+"填写的小区下没有匹配到当前楼栋,请核实后重新填写");
				result.setData(null);
				return result;
			}else{
				map.put("buildId",buildEntityList.get(0).getId());
			}
		}else{
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"楼栋不能为空");
			result.setData(null);
			return result;
		}
		if(StringUtils.isEmpty(houseInstrumentVo.getHouseCode())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"所属房号不能为空");
			result.setData(null);
			return result;
		}else{
			HouseEntity houseEntity = new HouseEntity();
			houseEntity.setCommunityId(communityVO.getId());
			houseEntity.setBuildId(map.get("buildId"));
			houseEntity.setHouseCode(houseInstrumentVo.getHouseCode());
			List<HouseEntity> houseEntityList = houseDao.queryHouseCodeList(houseEntity);
			if(CollectionUtils.isEmpty(houseEntityList)){
				result.setCode(MessageCode.FAIL);
				result.setMsg("第"+count+"行"+"填写的小区楼栋下没有匹配到当前所属房屋号,请核实后重新填写");
				result.setData(null);
				return result;
			}else{
				map.put("houseId",houseEntityList.get(0).getId());
			}
		}
		if(StringUtils.isEmpty(houseInstrumentVo.getInstrumentType())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"仪表类型不能为空");
			result.setData(null);
			return result;
		}else{
			InstrumentTypeEntity instrumnetVo = new InstrumentTypeEntity();
			instrumnetVo.setInstrumentType(houseInstrumentVo.getInstrumentType());
			List<InstrumentTypeEntity> instrumentTypeEntities = iInstrumentTypeDao.queryInstrumentType(instrumnetVo);
			if(CollectionUtils.isEmpty(instrumentTypeEntities)){
				result.setCode(MessageCode.FAIL);
				result.setMsg("第"+count+"行"+"填写的仪表类型没有匹配到，请核实后重新填写");
				result.setData(null);
				return result;
			}else{
				map.put("instrumentId",instrumentTypeEntities.get(0).getId().toString());
			}
		}
		if(StringUtils.isEmpty(houseInstrumentVo.getMinReading())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"初始度数不能为空");
			result.setData(null);
			return result;
		}else{
			boolean minReading = houseInstrumentVo.getMinReading().matches("[0-9]+");
			if(minReading == false){
				result.setCode(MessageCode.FAIL);
				result.setMsg("第"+count+"行"+"初始度数只能填写数字,不能包含中文字母或者其他符号");
				result.setData(null);
				return result;
			}
		}
		if(StringUtils.isEmpty(houseInstrumentVo.getMaxReading())){
			result.setCode(MessageCode.FAIL);
			result.setMsg("第"+count+"行"+"最大度数不能为空");
			result.setData(null);
			return result;
		}else{
			boolean maxReading = houseInstrumentVo.getMaxReading().matches("[0-9]+");
			if(maxReading == false){
				result.setCode(MessageCode.FAIL);
				result.setMsg("第"+count+"行"+"最大度数只能填写数字,不能包含中文字母或者其他符号");
				result.setData(null);
				return result;
			}
		}
		return result;
	}

	/**
	 * @Desc:房屋仪表管理导出
	 * @param voEntity
	 * @Author:邓磊
	 * @UpdateDate:2020/6/2 15:45
	 * @return: 返回
	 */
	@Override
	public void exportHouseInstrumentEntityInfo(HouseInstrumentEntity voEntity) throws Exception {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletRequestAttributes.getResponse();
		List<HouseInstrumentEntity> houseInstrumentEntities = houseInstrumentDao.queryList(voEntity);
		excelDownload(response,houseInstrumentEntities);

	}

	public void excelDownload(HttpServletResponse response,List<HouseInstrumentEntity>  list) throws Exception {
		//表头数据
		String[] header = {"小区名称", "房屋编码", "楼栋","仪表名称","仪表类型","倍率","最大读数","最小读数","初始日期"};
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("房屋仪表管理信息");
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
			row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getHouseCode()));
			row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getBuildName()));
			row1.createCell(3).setCellValue(new HSSFRichTextString(list.get(i).getInstrumentName()));
			row1.createCell(4).setCellValue(new HSSFRichTextString(list.get(i).getInstrumentType()));
			row1.createCell(5).setCellValue(new HSSFRichTextString(""+list.get(i).getRatio()));
			row1.createCell(6).setCellValue(new HSSFRichTextString(list.get(i).getMaxReading() == null?"":list.get(i).getMaxReading().toString()));
			row1.createCell(7).setCellValue(new HSSFRichTextString(list.get(i).getMinReading() ==null?"":list.get(i).getMinReading().toString()));
			row1.createCell(8).setCellValue(new HSSFRichTextString(list.get(i).getInitialDate()));
		}
		response.setHeader("content-Type","application/ms-excel");
		String name = "房屋仪表管理信息";
		String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}


}
