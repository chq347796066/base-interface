package com.spring.baseinfo.service;

import com.spring.common.response.ApiResponseResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @description:上传接口类
 * @return:
 * @author: 赵进华
 * @time: 2020/4/3 16:36
 */
public interface IUploadService {

    /***
     * @param file
     * @return {@link {@link ApiResponseResult }}
     * @throws
     * @author 熊锋
     * @date 2020/5/15
     * @DESE 单文件上传
     */
     ApiResponseResult uploadFile(MultipartFile file) throws Exception;

    /***
     * @param files
     * @return {@link {@link ApiResponseResult }}
     * @throws
     * @author 熊锋
     * @date 2020/5/15
     * @DESE 多文件文件上传
     */
    ApiResponseResult uploadBatch(MultipartFile[] files) throws Exception;

    /**
     * @description:删除文件
     * @return:
     * @author: 熊锋
     * @time: 2020/4/3 17:23
     */
     ApiResponseResult deleteFile(String id) throws Exception;

    /**
     * 文件下载
     * @param id
     * @return
     * @throws Exception
     * @author 作者：熊锋
     * @version 创建时间：2019年6月28日 下午3:32:09
     */
     void downloadFile(HttpServletResponse response, String id) throws Exception;
}
