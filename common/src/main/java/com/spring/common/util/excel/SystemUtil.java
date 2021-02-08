package com.spring.common.util.excel;

import com.spring.common.util.string.StrUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:系统工具类
*/
public class SystemUtil
{

	private static final Log log = LogFactory.getLog(SystemUtil.class);

	private static final String filePro = "/config/base.properties";
	private static Map<String, String> map = null;

	public static String getBaseValueByBaseKey(String key)
	{
		map = SystemUtil.readProperties(filePro);
		return map.get(key);
	}

	/**
	 * 【获取配置值】(info)
	 * 
	 * @param key
	 * @param defaultValue
	 *            默认的value，当配置文件值不存在，返回该值
	 * @return
	 */
	public static String getBaseValueByBaseKey(String key, String defaultValue)
	{
		String valstr = getBaseValueByBaseKey(key);
		if (StrUtil.isEmpty(valstr))
		{
			valstr = defaultValue;
		}
		return valstr;
	}

	public static Map<String, String> readProperties(String filePath)
	{
		if (filePath == null || "filePath".equals(filePath)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>(16);
		String key = "", property = "";
		Properties props = readPropertiesP(filePath);
		Enumeration<?> en = props.propertyNames();
		while (en.hasMoreElements())
		{
			key = (String) en.nextElement();
			property = props.getProperty(key);
			map.put(key, property);
			log.debug(">>>>>>>>>>>>" + key + " = " + property);
		}
		return map;
	}

	public static Properties readPropertiesP(String filePath)
	{
		if (filePath == null || "filePath".equals(filePath)) {
			return null;
		}
		Properties props = new Properties();
		InputStreamReader in = null;
		try
		{
			in = new InputStreamReader(SystemUtil.class.getResourceAsStream(filePath), StandardCharsets.UTF_8);
			try
			{
				props.load(in);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		} finally
		{
			try
			{
				in.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return props;
	}
}