package com.spring.common.util.excel;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: 赵进华
 * @time: 2020/4/3 15:45
 */

public class ExceUtils {

    /**
     * @description:导入数据
     * @return:
     * @author: 赵进华
     * @time: 2020/4/2 17:50
     */
    public static List<List<String[]>> importExcel(HttpServletRequest request, MultipartFile file) throws Exception {
        // 文件本地存储路径
        String filePath = request.getServletContext().getRealPath("TempFile/");
        // 原文件名
        String fileName = file.getOriginalFilename();
        // 新文件名
        String newName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("."));
        // 保存的全文件名
        String fullFileName = filePath + newName;
        File targetFile = new File(filePath);
        // 目录不存在创建目录
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out;
        try {
            // 保存文件
            out = new FileOutputStream(fullFileName);
            out.write(file.getBytes());
            out.flush();
            out.close();

            // 解析excel文件
            List<List<String[]>> list = Excel2007Util.impexc(fullFileName);
            return list;
        } catch (FileNotFoundException e) {
            throw new Exception("文件上传失败(1)");
        } catch (IOException e) {
            throw new Exception("文件上传失败(2)");
        } finally {
            File newFile = new File(fullFileName);
            if (newFile.exists() && newFile.isFile()) {
                newFile.delete();
            }
        }
    }
}
