package com.spring.common.util.file;

import java.io.*;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年7月10日 下午2:56:50
* @Desc类说明:文件相关工具类
*/
public class FileUtils {

	/**
	 * 输入流转字节数组
	 * @param input
	 * @return
	 * @throws IOException
	 * @author 作者：赵进华 
	 * @version 创建时间：2019年7月10日 上午11:19:15
	 */
	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output.toByteArray();
	}

	/**
	 * 把一个文件转化为byte字节数组。
	 *
	 * @return
	 */
	public static byte[] fileConvertToByteArray(File file) {
		byte[] data = null;

		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			int len;
			byte[] buffer = new byte[1024];
			while ((len = fis.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}

			data = baos.toByteArray();

			fis.close();
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * 获取文件扩展名
	 *
	 * @return
	 */
	public static String getFileExt(String filename) {
		int index = filename.lastIndexOf(".");
		if (index == -1) {
			return null;
		}
		String result = filename.substring(index + 1);
		return result;
	}
}
