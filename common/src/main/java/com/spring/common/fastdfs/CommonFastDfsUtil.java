package com.spring.common.fastdfs;

import com.github.tobato.fastdfs.domain.MateData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author dell
 */
@Component
public class CommonFastDfsUtil {

    private final Logger logger = LoggerFactory.getLogger(CommonFastDfsUtil.class);

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     *	MultipartFile类型的文件上传ַ(单文件上传)
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return getResAccessUrl(storePath);
    }

    /**
     *	MultipartFile类型的文件上传ַ（多文件上传）
     * @param files
     * @return
     * @throws IOException
     */
    public List<String> uploadBatch(MultipartFile[] files) throws IOException {
        List<StorePath> storePathList=new ArrayList<>();
        for (MultipartFile multipartFile:files) {
            StorePath storePath=storageClient.uploadFile(multipartFile.getInputStream(), multipartFile.getSize(), FilenameUtils.getExtension(multipartFile.getOriginalFilename()), null);
            storePathList.add(storePath);
        }
        return getResAccessUrlList(storePathList);
    }

    /**
     * 普通的文件上传
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        StorePath path = storageClient.uploadFile(inputStream, file.length(),
                FilenameUtils.getExtension(file.getName()), null);
        return getResAccessUrl(path);
    }

    /**
     * 带输入流形式的文件上传
     * @param is
     * @param size
     * @param fileName
     * @return
     */
    public String uploadFileStream(InputStream is, long size, String fileName) {
        StorePath path = storageClient.uploadFile(is, size, fileName, null);
        return getResAccessUrl(path);
    }

    /**
     * 将一段文本文件写到fastdfs的服务器上
     * @param content
     * @param fileExtension
     * @return
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath path = storageClient.uploadFile(stream, buff.length, fileExtension, null);
        return getResAccessUrl(path);
    }

    /**
     * 返回文件上传成功后的地址名称ַ
     * @param storePath
     * @return
     */
    private String getResAccessUrl(StorePath storePath) {
        String fileUrl = storePath.getFullPath();
        return fileUrl;
    }

    /**
     * 返回文件上传成功后的地址名称ַ（集合）
     * @param storePathList
     * @return
     */
    private List<String> getResAccessUrlList(List<StorePath> storePathList) {
        List<String> fileUrlList=new ArrayList<>();
        for (StorePath storePath:storePathList) {
            String fileUrl = storePath.getFullPath();
            fileUrlList.add(fileUrl);
        }
        return fileUrlList;
    }

    /**
     * 删除文件
     * @param fileUrl
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            logger.warn(e.getMessage());
        }
    }

    public String upfileImage(InputStream is, long size, String fileExtName, Set<MateData> metaData) {
        StorePath path = storageClient.uploadImageAndCrtThumbImage(is, size, fileExtName, metaData);
        return getResAccessUrl(path);
    }

    /**
     * 根据fastDFS返回的path得到文件的组名
     * @param path
     * fastDFS返回的path
     * @return
     */
    public String getGroupFormFilePath(String path) {
        StorePath storePath = StorePath.praseFromUrl(path);
        return storePath.getGroup();
    }

    /**
     * 根据fastDFS返回的path得到文件的路径
     * @param path
     * fastDFS返回的path
     * @return
     */
    public String getFilePath(String path) {
        StorePath storePath = StorePath.praseFromUrl(path);
        return storePath.getPath();
    }

}
