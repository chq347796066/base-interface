package com.spring.common.importExcel.util;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 根据键值在hashmap中取值。
 *
 */
public class MapUtil {

	private static Logger logger = LoggerFactory.getLogger(MapUtil.class);

	/**
	 * 根据字段取得map中的字段字符串值,如果值为空，则返回 ""空串
	 * 
	 * @param paraTypeMap
	 * @param field
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getString(Map map, String field) {
		field = field.toLowerCase();
		Set set = map.keySet();
		Iterator it = set.iterator();
	   ConcurrentHashMap<String, String> ht = new ConcurrentHashMap<String, String>();
		while (it.hasNext()) {
			String key = (String) it.next();
			ht.put(key.toLowerCase(), key);
		}
		field = ht.get(field);
		Object obj = map.get(field);
		return (obj != null) ? obj.toString().trim() : "";
	}

	/**
	 * 取得map中字段的长整型值。取不到值返回-1.
	 * 
	 * @param paraTypeMap
	 * @param field
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static long getLong(Map map, String field) {
		String value = (String) getString(map, field);
		if (value.equals(""))
			return -1;
		return Long.parseLong(value);
	}

	/**
	 * 取得map中字段的整型值。取不到值返回-1.
	 * 
	 * @param paraTypeMap
	 * @param field
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static int getInt(Map map, String field) {
		String value = (String) getString(map, field);
		if (value.equals(""))
			return -1;
		return Integer.parseInt(value);
	}

	/**
	 * 取得map中字段的浮点数值。取不到值返回-1.
	 * 
	 * @param paraTypeMap
	 * @param field
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static float getFloat(Map map, String field) {
		String value = (String) getString(map, field);
		if (value.equals(""))
			return -1;
		return Float.parseFloat(value);
	}

	/**
	 * 取得map中字段的double数值。取不到值返回-1.
	 * 
	 * @param paraTypeMap
	 * @param field
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static double getDouble(Map map, String field) {
		String value = (String) getString(map, field);
		if (value.equals(""))
			return -1;
		return Double.parseDouble(value);
	}

	public static byte[] toByteArray(Object map) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(map);
			oos.flush();
			bytes = bos.toByteArray();
		} catch (IOException ex) {
			logger.error("流处理出错", ex);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					logger.error("关闭流出错", e);
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("关闭流出错", e);
				}
			}
		}
		return bytes;
	}

	public static Object byteToMap(byte[] b) {
		Object obj = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bis = new ByteArrayInputStream(b);
			ois = new ObjectInputStream(bis);
			obj = ois.readObject();
		} catch (Exception ex) {
			logger.error("流处理出错", ex);
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					logger.error("关闭流出错", e);
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					logger.error("关闭流出错", e);
				}
			}
		}

		if (obj != null && obj instanceof Map) {
			return obj;
		}
		return null;
	}

	/**
	 * @Description: ben转换Map格式,obj传入实体名称
	 * @return 返回值
	 * @author 肖伟
	 */
	public static Map<String, Object> getBeanToMap(Object obj) {
		if (null == obj) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>(0);
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!"class".equals(name)) {
					params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
				}
			}
		} catch (Exception e) {
			logger.error("将javaBeam转成Map出错", e);
		}
		return params;
	}

	public static Map<String, Object> getBeanToMap(Object obj, String[] ignoreProperties) {
		if (null == obj) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>(0);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!"class".equals(name) && !ignoreList.contains(name)) {
					params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
				}
			}
		} catch (Exception e) {
			logger.error("将对象转化为Map时出错", e);
		}
		return params;
	}

	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	public static boolean isEmpty(Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}
}
