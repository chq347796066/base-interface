package com.spring.common.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:下载工具类
*/
public class FileDownUtils {
	
	/***
	 * 批量下载
	 * 
	 * @param response
	 * @param bathDownloadVos
	 * @param zipFileName 需要带文件后最 eg:adb.zip
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
    public static void downloadZip(HttpServletResponse response, List<File> files, String zipFileName)
    {
        response.reset();
        ZipOutputStream out = null;
        InputStream in = null;
        try
        {
            String fileName = new String(zipFileName.getBytes(), "ISO8859-1");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setCharacterEncoding(Charset.defaultCharset().toString());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);

            out = new ZipOutputStream(response.getOutputStream());
            for (File file : files)
            {
                // 如果没有传入显示的文件名，则用源文件名
                String name = file.getName();
                ZipEntry zipEntry = new ZipEntry(name);
                zipEntry.setSize(file.length());
                zipEntry.setTime(file.lastModified());
                out.putNextEntry(zipEntry);
                in = new FileInputStream(file);
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = in.read(buffer, 0, 8192)) != -1)
                {
                    out.write(buffer, 0, bytesRead);
                }
                StreamUtils.close(in);
            }
            out.finish();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        	StreamUtils.close(out);
        }
    }
    
    /***
     * 描述: 下载 
     * 参数: @param response
     * 参数: @param file
     * 参数: @param fileName
     * 返回值类型: void
     * 作者: 肖国军
     * 日期: 2018年1月29日 下午4:04:58
     */
    public static void download(HttpServletResponse response, File file, String fileName)
	{
		response.reset();
		OutputStream out = null;
		InputStream in = null;
		try {
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setCharacterEncoding(Charset.defaultCharset().toString());
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
					"attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));

			out = response.getOutputStream();
			in = new FileInputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			StreamUtils.close(in, out);
		}
	}
}
