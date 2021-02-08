package com.spring.baseinfo.service.impl;

import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.spring.base.entity.baseinfo.DocumentEntity;
import com.spring.base.vo.baseinfo.document.DocumentShowVo;
import com.spring.baseinfo.dao.IDocumentDao;
import com.spring.baseinfo.service.IUploadService;
import com.spring.common.config.FdfsConfig;
import com.spring.common.constants.MessageCode;
import com.spring.common.fastdfs.CommonFastDfsUtil;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * @description:上传接口实现类
 * @author: 熊锋
 * @time: 2020/5/15 16:36
 */
@Slf4j
@Service("fileService")
public class UploadServiceImpl implements IUploadService {

    @Autowired
    private CommonFastDfsUtil commonFastDfsUtil;

    @Autowired
    private FdfsConfig fdfsConfig;

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private IDocumentDao documentDao;

    @Override
    public ApiResponseResult uploadFile(MultipartFile file) throws Exception {
        ApiResponseResult result=new ApiResponseResult();
        try {
            //得到文件上传路径
            String fastPath=commonFastDfsUtil.uploadFile(file);
            //返回给前端路径带url
            String path=fdfsConfig.getResHost()+":"+fdfsConfig.getStoragePort()+"/"+fastPath;
            // 存储数据库
            DocumentEntity entity = new DocumentEntity();
            String id= UUIDFactory.createId();
            entity.setId(id);
            entity.setDocName(file.getOriginalFilename());
            entity.setDocPath(fastPath);
            entity.setFullPath(path);
            entity.setExtend(FilenameUtils.getExtension(file.getOriginalFilename()));
            entity.setSize((int) file.getSize());
            entity.setUploadName(path.substring(path.lastIndexOf("/") + 1));
            documentDao.insert(entity);

            log.info("upload file save db success name:"+file.getOriginalFilename()+",docId:"+id);
            DocumentShowVo showVo = new DocumentShowVo();
            showVo.setId(id);
            showVo.setName(file.getOriginalFilename());
            showVo.setUrl(path);
            result.setCode(MessageCode.SUCCESS);
            result.setMsg("文件上传成功！");
            result.setData(showVo);
        }catch (Exception e){
            throw new Exception("文件上传失败");
        }
        return result;
    }

    @Override
    public ApiResponseResult uploadBatch(MultipartFile[] files) throws Exception {
        ApiResponseResult result=new ApiResponseResult();
        try{
            List<DocumentEntity> documentEntityList=new ArrayList<>();
            List<DocumentShowVo> documentShowVoList=new ArrayList<>();
            for (MultipartFile multipartFile:files) {
                DocumentEntity entity = new DocumentEntity();
                DocumentShowVo showVo = new DocumentShowVo();
                // 存储数据库
                String id= UUIDFactory.createId();
                entity.setId(id);
                entity.setDocName(multipartFile.getOriginalFilename());
                entity.setExtend(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
                entity.setSize((int) multipartFile.getSize());
                //得到文件上传路径
                String fastPath=commonFastDfsUtil.uploadFile(multipartFile);
                //返回给前端路径带url
                String path=fdfsConfig.getResHost()+fastPath;
                entity.setDocPath(fastPath);
                entity.setFullPath(path);
                entity.setUploadName(entity.getFullPath().substring(entity.getFullPath().lastIndexOf("/") + 1));
                documentEntityList.add(entity);

                log.info("upload file save db success name:"+multipartFile.getOriginalFilename()+",docId:"+id);
                showVo.setId(id);
                showVo.setName(multipartFile.getOriginalFilename());
                showVo.setUrl(path);
                documentShowVoList.add(showVo);
            }
            if(CollectionUtils.isNotEmpty(documentEntityList)){
                documentDao.addList(documentEntityList);
            }
            result.setCode(MessageCode.SUCCESS);
            result.setMsg("文件上传成功！");
            result.setData(documentShowVoList);
        }catch (Exception e){
            throw new Exception("文件上传失败");
        }
        return result;
    }

    @Override
    public ApiResponseResult deleteFile(String id) throws Exception {
        ApiResponseResult result=new ApiResponseResult();
        DocumentEntity documentEntity=documentDao.selectById(id);
        String filePath="";
        if(documentEntity!=null){
           filePath=documentEntity.getFullPath();
        }
        commonFastDfsUtil.deleteFile(filePath);
        documentDao.deleteById(id);
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("文件删除成功！");
        return result;
    }

    @Override
    public void downloadFile(HttpServletResponse response, String id)
            throws Exception {
        try {
            // 文件路径
            String filePath = "";
            // 文件名
            String fileName = "";
            //根据文件id查询文件对象
            DocumentEntity entity = documentDao.selectById(id);
            if (entity != null) {
                filePath = entity.getFullPath();
                fileName = entity.getDocName();
            }
            String group = commonFastDfsUtil.getGroupFormFilePath(filePath);
            String file = commonFastDfsUtil.getFilePath(filePath);
            DownloadByteArray downloadByteArray = new DownloadByteArray();
            byte[] content =storageClient.downloadFile(group,file,downloadByteArray);
            String headStr = "attachment; filename=\"" + URLEncoder.encode(fileName,"UTF-8") + "\"";
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", headStr);
            OutputStream out = response.getOutputStream();
            out.write(content);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
