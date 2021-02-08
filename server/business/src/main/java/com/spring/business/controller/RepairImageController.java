package com.spring.business.controller;

import com.spring.base.controller.BaseController;
import com.spring.business.service.IRepairImageService;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 报修图片控制器
 */
@RestController
@Api(value = "报修图片", tags = {"报修图片接口"})
@RequestMapping("repairImage")
@Slf4j
public class RepairImageController extends BaseController{

	@Autowired
	private IRepairImageService repairImageService;

	/**
	 * 图片上传
	 * @param files
	 * @return
	 * @throws Exception
	 */
	 @ApiOperation(value = "批量上传文件", notes = "批量上传文件")
	 @PostMapping("/uploadBatch")
	 public ApiResponseResult uploadBatch(@RequestParam("files") MultipartFile[] files) throws Exception {
		 ApiResponseResult result=new ApiResponseResult();
		 log.info("文件名称:"+ files );
		 if(files==null ||files.length==0){
			 result.setCode(MessageCode.FAIL);
			 result.setMsg("请选择图片！");
			 return result;
		 }
		 return repairImageService.uploadBatch(files);
	 }

    @ApiOperation(value = "删除图片", notes = "删除图片")
    @GetMapping("/deleteFile")
	public ApiResponseResult deleteFile(
            @ApiParam(value = "文件路径", required = true) @RequestParam(value = "picUrl") String picUrl) throws Exception {
		log.info("文件id："+ picUrl );
        return repairImageService.deleteFile(picUrl);
    }
}
