package com.spring.common.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 
 * @ClassName: ParamUtils
 * @Description: 参数获取工具类
 * @author yc
 * @date 2015年7月24日 下午5:19:24
 */
public class ParamUtils {

	public final static String LASTEST_CACHED_KEY = "__LASTEST_CACHED_PARAMETER";

	/**
	 * constructor
	 */
	private ParamUtils() {
	}

	/**
	 * 从Request中获取参数值。
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param name
	 *            参数名
	 * @return
	 * 
	 */
	public static String getParameter(HttpServletRequest request, String name) {
		return getParameter(request, name, true);
	}

	/**
	 * 从Request中获取参数值。如果未传入该参数，则从cache中获取上次请求的参数值
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param name
	 *            参数名
	 * @param useCache
	 *            如果未传入该参数，是否从缓存中获取上次请求时的参数值
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name, boolean useCache) {
		String str = request.getParameter(name);
		if (str != null) {
			return StringUtils.trim(str);
		}
		if (useCache) {
			@SuppressWarnings("unchecked")
			Map<String, String[]> cached = (Map<String, String[]>) request.getAttribute(LASTEST_CACHED_KEY);
			if (cached != null && !cached.isEmpty()) {
				String[] cachedValue = cached.get(name);
				if (ArrayUtils.isNotEmpty(cachedValue)) {
					return StringUtils.trim(cachedValue[0]);
				}
			}
		}
		return "";
	}

	/**
	 * 取得一个字符串数组
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param name
	 *            参数名
	 * @return
	 */
	public static String[] getParameters(HttpServletRequest request, String name) {
		return getParameters(request, name, true);
	}

	/**
	 * 取得一个字符串数组
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param name
	 *            参数名
	 * @param useCache
	 *            如果未传入该参数，是否从缓存中获取上次请求时的参数值
	 *
	 * @return
	 */
	public static String[] getParameters(HttpServletRequest request, String name, boolean useCache) {
		String[] paramValues = request.getParameterValues(name);
		if (paramValues != null) {
			return paramValues;
		}
		if (useCache) {
			@SuppressWarnings("unchecked")
			Map<String, String[]> cached = (Map<String, String[]>) request.getAttribute(LASTEST_CACHED_KEY);
			if (cached != null && !cached.isEmpty()) {
				String[] cachedValues = cached.get(name);
				if (cachedValues != null) {
					return cachedValues;
				}
			}
		}
		return new String[] {};
	}

	/**
	 * 获取分页的参数
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	public static Integer getPageNumParameter(HttpServletRequest request) {
		return getPageNumParameter(request, true);
	}

	/**
	 * 获取分页的参数,如果未传入则从Cache中获取上次请求时的参数
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param useCache
	 *            是否从Cache中获取上次请求的参数
	 * @return
	 */
	public static Integer getPageNumParameter(HttpServletRequest request, boolean useCache) {
		String str = getParameter(request, "pageNum", useCache);
		if (StringUtils.isNotBlank(str) && NumberUtils.isCreatable(str)) {
			return Integer.valueOf(str);
		} else {
			return new Integer(1);
		}
	}

	/**
	 * 获取分页的单页显示的数量
	 * 
	 * @param request
	 * @return
	 */
	public static Integer getPageSizeParameter(HttpServletRequest request) {
		return getPageSizeParameter(request, true);
	}

	/**
	 * 获取分页的单页显示的数量,如果未传入则从Cache中获取上次请求时的参数
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param useCache
	 *            是否从Cache中获取上次请求的参数
	 * @return
	 */
	public static Integer getPageSizeParameter(HttpServletRequest request, boolean useCache) {
		String str = getParameter(request, "pageSize", useCache);
		if (StringUtils.isNotBlank(str) && NumberUtils.isCreatable(str)) {
			return Integer.valueOf(str);
		}
		return null;
	}
}