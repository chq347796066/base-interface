package com.spring.common.importExcel.helper;

import com.spring.common.exception.ServiceException;
import com.spring.common.util.ArrayUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * 类对象生成器
 * 
 * @author Michael
 * 
 * @param <T>
 *            Class类实例
 */
public class BeanGenerator<T> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BeanGenerator.class);

	/**
	 * 获取类的属性列表，以字符串数组的形式返回，{"id", "name", "age"}
	 * 
	 * @param clazz
	 *            Class实例
	 * @return
	 */
	private String[] getPropertyNames(Class<T> clazz) {
		Field[] declaredFields = clazz.getDeclaredFields();
		if (ArrayUtils.isNotEmpty(declaredFields)) {
			int length = declaredFields.length;
			String[] propertyNames = new String[length];
			for (int i = 0; i < length; i++) {
				propertyNames[i] = declaredFields[i].getName();
			}
			return propertyNames;
		}
		return null;
	}

	/**
	 * 创建对象，传入的值数组的顺序需要与类的属性的顺序一致<br>
	 * {"id", "name", "age"}<br>
	 * {1, "Michael", new Integer(30)}
	 * 
	 * @param clazz
	 *            Class实例
	 * @param data
	 *            要设置的属性的值数组
	 * @return 利用反射机制创建的clazz类的对象，值数组为空时返回null<br>
	 *         创建失败时会抛出
	 *         <code>com.icip.framework.common.exception.SysException</code>异常
	 */
	public T generateBean(Class<T> clazz, Object[] data) throws ServiceException {
		if (ArrayUtils.isEmpty(data))
			return null;
		String[] propertyNames = getPropertyNames(clazz);
		T bean = null;
		try {
			if (ArrayUtils.isEmpty(propertyNames))
				return null;

			int length = propertyNames.length;
			Object[] newData = ArrayUtils.subarray(data, 0, length);

			if (!ArrayUtil.isAllElemEmpty(newData)) {
				bean = clazz.newInstance();
				int min = length < newData.length ? length : newData.length;
				for (int i = 0; i < min; i++)
					BeanUtils.setProperty(bean, propertyNames[i], newData[i]);
			}
		} catch (Exception e) {
			LOGGER.error("convert row data to a bean failed, ", e);
			throw new ServiceException("创建对象失败", e);
		}
		return bean;
	}

}
