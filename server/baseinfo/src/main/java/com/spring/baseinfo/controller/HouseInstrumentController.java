package com.spring.baseinfo.controller;


import com.spring.base.controller.BaseController;
import com.spring.base.entity.baseinfo.HouseInstrumentEntity;
import com.spring.base.vo.baseinfo.houseinstrument.HouseInstrumentUpdateVo;
import com.spring.base.vo.baseinfo.houseinstrument.HouseInstrumentAddVo;
import com.spring.baseinfo.service.IHouseInstrumentService;
import com.spring.common.constants.MessageCode;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.excel.EasyExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-03 09:35:57
 * @Desc类说明: 房屋仪管理控制器
 */
@RestController
@Slf4j
@Api(value = "房屋仪管理", tags = "房屋仪管理接口")
@RequestMapping("houseinstrument")
public class HouseInstrumentController extends BaseController{
	@Autowired
	private IHouseInstrumentService houseInstrumentService;
	
	/**
	 * 新增
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增")
	@PostMapping(value = "/add")
	public ApiResponseResult add(@RequestBody @Validated HouseInstrumentAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return houseInstrumentService.addHouseInstrument(vo);
	}

	/**
	 * 更新
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "更新")
	@PostMapping(value = "/update")
	public ApiResponseResult update(@RequestBody @Validated HouseInstrumentUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return houseInstrumentService.updateHouseInstrument(vo);
	}

	 /**
	  * 关联删除
	  * @param id
	  * @return
	  */
	 @ApiOperation(value = "删除", notes = "删除")
	 @GetMapping(value = "/delete")
	 public ApiResponseResult deleteDetail(@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) Long id)
			 throws Exception {
		 return houseInstrumentService.deleteDetail(id);
	 }


	/**
	 * 根据条件查询详情
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询详情", response = HouseInstrumentEntity.class)
	@PostMapping(value = "/queryHouseInstrumentInfo")
	public ApiResponseResult queryHouseInstrumentInfo(@RequestBody HouseInstrumentEntity vo) throws Exception {
		return houseInstrumentService.queryHouseInstrumentInfo(vo);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询", response = HouseInstrumentEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody RequestPageVO<HouseInstrumentEntity> requestPageVO) throws Exception {
		return houseInstrumentService.queryPage(requestPageVO);
	}


	 /**
	  * @Desc:房屋仪表管理导出
	  * @param voEntity
	  * @Author:邓磊
	  * @UpdateDate:2020/6/2 13:52
	  * @return: 返回
	  */
	 @ApiOperation(value = "房屋仪表管理导出", response = HouseInstrumentEntity.class)
	 @GetMapping(value = "/exportHouseInstrumentEntityInfo")
	 public void  exportHouseInstrumentEntityInfo(HouseInstrumentEntity voEntity) throws Exception {
		 houseInstrumentService.exportHouseInstrumentEntityInfo(voEntity);
	 }

	/**
	 * @Desc:下载房屋仪表模板
	 * @Author:邓磊
	 * @UpdateDate:2020/6/2 16:02
	 * @return: 返回
	 */
	@ApiOperation(value = "下载房屋仪表模板", httpMethod = "GET", response = Object.class)
	@GetMapping("/expUserCarDemo")
	public void expUserDemo(HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		String fileName = "房屋仪表信息.xlsx";
		response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes(StandardCharsets.UTF_8),"iso8859-1"));
		try {
			InputStream inputStream =this.getClass().getClassLoader().getResourceAsStream("/excelModel/meter_template.xlsx");
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while((length = inputStream.read(b))>0){
				os.write(b,0,length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * @Desc:房屋仪表批量导入
	 * @param companyId，communityId
	 * @Author:邓磊
	 * @UpdateDate:2020/6/2 16:24
	 * @return: 返回
	 */
	@ApiOperation(value = "房屋仪表批量导入", httpMethod = "POST", response = Object.class)
	@PostMapping("/batchImportHouseInstrument")
	public ApiResponseResult impUser(MultipartFile file,
									 @ApiParam(value = "公司Id", required = true) @RequestParam(value = "companyId", required = true) String companyId,
									 @ApiParam(value = "小区Id", required = true) @RequestParam(value = "communityId", required = true) String communityId){
		ApiResponseResult result = new ApiResponseResult();
		if (file == null || file.isEmpty()) {
			result.setCode(MessageCode.FAIL);
			result.setMsg("上传文件为空！");
			return result;
		}
		List<HouseInstrumentAddVo> voList = EasyExcelUtils.importData(file, 1, HouseInstrumentAddVo.class);
		if(CollectionUtils.isNotEmpty(voList)){
			if(StringUtils.isEmpty(voList.get(0).getCommunityName())){
				result.setCode(MessageCode.FAIL);
				result.setMsg("导入时，请删除第一行说明单元格！");
				return result;
			}
		}
		ApiResponseResult apiResponseResult = houseInstrumentService.batchImportHouseInstrument(voList,companyId,communityId);
		return apiResponseResult;
	}

 }
