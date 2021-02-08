package com.spring.common.cache.util;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CacheClassUtil {
	
	private static final Log logger = LogFactory.getLog(CacheClassUtil.class);
	
	/**
	 *	根据类全路径获取类对象
	 */
	public static Class<?> getEntityClass(String fullClassName){
		Class<?> clazz = null;
		try{
			clazz = Class.forName(fullClassName);
		}catch(ClassNotFoundException e){
			logger.error(e.getMessage(),e);
		}
		return clazz;
	}

	public static Object getValueFromObject(String property, Object dest) {
		Object value = null;
		String feproperty = CacheStringUtil.clearFESymbolOfString(property, '/');
		String[] properties = feproperty.split("/");
		String nextproperty = "";
		if (feproperty.indexOf("/") > 0)
			nextproperty = feproperty.replace(properties[0] + "/", "").trim();
		else
			nextproperty = feproperty.replace(properties[0], "").trim();

		if (dest instanceof HashMap)
			value = getValueFromHashMap(properties[0], dest);
		else if (dest.getClass().isArray())
			value = getValueFromArray(properties[0], dest);
		else if (dest instanceof ArrayList) {
			value = ((ArrayList<?>) dest).get(Integer.parseInt(properties[0]));
		} else if (dest.getClass().isPrimitive() || dest instanceof String) {
			value = dest;
		} else {
			value = getValueFromBean(properties[0], dest);
		}
		if (!CacheStringUtil.isEmpty(nextproperty) && value != null) {
			value = getValueFromObject(nextproperty, value);
		}
		return value;

	}

	/**
	 * 从HashMap获取属性值
	 * 
	 * @param property
	 * @param dest
	 * @return
	 */
	public static Object getValueFromHashMap(String property, Object dest) {
		if (!(dest instanceof HashMap))
			return null;

		return ((HashMap<?, ?>) dest).get(property);
	}

	/**
	 * 获取数组值
	 * 
	 * @param property
	 * @param dest
	 * @return
	 */
	public static Object getValueFromArray(String property, Object dest) {
		if (!dest.getClass().isArray())
			return null;
		int index = -1;
		try {
			index = Integer.parseInt(property);
		} catch (NumberFormatException ex) {
			logger.error(ex.getMessage(),ex);
			return null;
		}
		Type componetType = null;
		if (((Type) dest.getClass()) instanceof GenericArrayType) {
			componetType = ((GenericArrayType) ((Type) dest.getClass())).getGenericComponentType();
		} else {
			componetType = ((Class<?>) ((Type) dest.getClass())).getComponentType();
		}

		if (!((Class<?>) componetType).isPrimitive())
			return ((Object[]) dest)[index];

		return objectToPrimaryArray(dest, (Class<?>) componetType);
	}

	/**
	 * 获取bean对象属性值
	 * 
	 * @param property
	 * @param dest
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getValueFromBean(String property, Object dest) {
		if (dest == ObjectUtils.NULL || CacheStringUtil.isEmpty(property))
			return null;
		try {
			Field field = dest.getClass().getDeclaredField(property);
			field.setAccessible(true);
			return field.get(dest);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回基本类型数值
	 * 
	 * @param property
	 * @param dest
	 * @return
	 */
	public static Object getValueFromPrimitive(String property, Object dest) {
		if (dest.getClass().isPrimitive() || dest instanceof String)
			return dest;
		return null;
	}

	/**
	 * Object 对象转换成基本类型数组
	 * 
	 * @param dest
	 *            带转换对象
	 * @param componetClass
	 *            基本类型
	 * @return 对象数组
	 */
	public static Object[] objectToPrimaryArray(Object dest, Class<?> componetClass) {

		if (componetClass.getName().startsWith("[I")) {
			int[] temp = (int[]) dest;
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < temp.length; i++) {
				list.add(new Integer(temp[i]));
			}
			return list.toArray();
		}
		if (componetClass.getName().startsWith("[L")) {
			long[] temp = (long[]) dest;
			ArrayList<Long> list = new ArrayList<Long>();
			for (int i = 0; i < temp.length; i++) {
				list.add(new Long(temp[i]));
			}
			return list.toArray();
		}
		return null;
	}

	public static void main(String[] args) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		// -----------------------------------------------------------------
		// 测试数组[index]\对象[property]
		/*
		 * 
		 * String property = "0/name//"; TestBean[] objs = new TestBean[2];
		 * 
		 * objs[0] = new TestBean("tttname", "age:20"); objs[1] = new
		 * TestBean("t001name", "age:30");
		 * 
		 * Object result = ClassUtil.getValueFromObject(property, objs);
		 * 
		 * Assert.isTrue(result.equals("tttname"), "tttname");
		 */

		// ----------------------------------------------------------------
		// 测试数组[index]\对象[property]\对象[property]
		/*
		 * String property = "0/sub/postcode"; TestBean[] objs = new
		 * TestBean[2]; objs[0] = new TestBean("tttname", "age:20"); objs[1] =
		 * new TestBean("t001name", "age:30"); objs[0].setSub(new
		 * TestSub("sub001", "subtc002"));
		 * 
		 * Object result = ClassUtil.getValueFromObject(property, objs);
		 * 
		 * Assert.isTrue(result.equals("sub001"), "sub001");
		 */

	}
}

/**
 * 测试类
 */
class TestBean {

	public TestBean(String n, String a) {
		this.name = n;
		this.age = a;
	}

	String name;

	TestSub sub;

	public TestSub getSub() {
		return sub;
	}

	public void setSub(TestSub sub) {
		this.sub = sub;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	String age;
}

/**
 * 测试类
 */
class TestSub {
	public TestSub(String pc, String tc) {
		this.postcode = pc;
		this.ttcode = tc;
	}

	String postcode;

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getTtcode() {
		return ttcode;
	}

	public void setTtcode(String ttcode) {
		this.ttcode = ttcode;
	}

	String ttcode;
}
