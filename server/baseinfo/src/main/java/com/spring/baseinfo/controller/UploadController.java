package com.spring.baseinfo.controller;

import com.spring.baseinfo.service.IUploadService;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author dell
 */
@Api(value = "fastDfs文件接口", tags = "fastDfs文件接口")
@RestController
@RequestMapping(value = "/file")
public class UploadController {

    @Autowired
    private IUploadService fileService;

    private final static Logger logger = LoggerFactory.getLogger(UploadController.class);

    //fastDfs单文件上传
    @ApiOperation(value = "上传单个文件", notes = "上传单个文件")
    @PostMapping("/upload")
    public ApiResponseResult uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        ApiResponseResult result=new ApiResponseResult();
        if(file.isEmpty()){
            result.setCode(MessageCode.FAIL);
            result.setMsg("上传文件为空！");
            return result;
        }
        return fileService.uploadFile(file);
    }

    //使用fastDfs实现多图上传
    @ApiOperation(value = "批量上传文件", notes = "批量上传文件")
    @PostMapping("/uploadBatch")
    public ApiResponseResult uploadBatch(@RequestParam("files") MultipartFile[] files) throws Exception {
        ApiResponseResult result=new ApiResponseResult();
        logger.info("文件名称："+ files );
        if(files==null ||files.length==0){
            result.setCode(MessageCode.FAIL);
            result.setMsg("上传文件为空！");
            return result;
        }
        return fileService.uploadBatch(files);
    }

    //使用fastDfs实现文件删除
    @ApiOperation(value = "删除文件", notes = "删除文件")
    @GetMapping("/deleteFile")
    synchronized public ApiResponseResult deleteFile(
            @ApiParam(value = "文件id", required = true) @RequestParam(value = "documentId") String documentId) throws Exception {
        ApiResponseResult result=new ApiResponseResult();
        logger.info("文件id："+ documentId );
        return fileService.deleteFile(documentId);
    }

    /**
     * @description:文件下载
     * @return:
     * @author: 赵进华
     * @time: 2020/4/3 20:00
     */
    @ApiOperation(value = "文件下载", notes = "文件下载")
    @GetMapping(value = "/download")
    public void downloadFile(
            HttpServletResponse response,
            @ApiParam(value = "id", required = true) @RequestParam(value = "id", required = true) String id)
            throws Exception {
        fileService.downloadFile(response, id);
    }
}