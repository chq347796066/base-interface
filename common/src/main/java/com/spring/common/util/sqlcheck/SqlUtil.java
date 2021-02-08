package com.spring.common.util.sqlcheck;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.compress.utils.Lists;
import org.springframework.cglib.beans.BeanMap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年6月17日 下午2:41:46
 * @Desc类说明:sql注入工具类
 */
public class SqlUtil {
	/**
	 * 效验
	 * 
	 * @param str
	 * @return
	 * @author 作者：赵进华
	 * @version 创建时间：2019年6月17日 下午3:18:12
	 */
	public static boolean sqlValidate(String str) {
		// 统一转为小写
		str = str.toLowerCase();
		// 过滤掉的sql关键字，可以手动添加
		String badStr = "'|exec|execute|insert|delete|update|drop|chr|mid|master|truncate|"
				+ "char|declare|sitename|xp_cmdshell|like'|exec|execute|create|drop|"
				+ "table|grant|group_concat|column_name|" + "information_schema.columns|table_schema|"
				+ "chr|mid|master|truncate|char|declare|like|//|/|#";
		String[] badStrs = badStr.split("\\|");
		for (int i = 0; i < badStrs.length; i++) {
			if (str.indexOf(badStrs[i]) >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 解析insert语句
	 * 
	 * @param table
	 * @param json
	 * @return
	 * @author 作者：赵进华
	 * @version 创建时间：2019年6月19日 上午10:41:00
	 */
	public static String parseInsertSql(String table,String id, String createBy, String json) {
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(json);
		String sql = "";
		sql += "INSERT INTO " + table + " ( id , ";
		Set<Entry<String, JsonElement>> entry = obj.entrySet();
		Iterator<Entry<String, JsonElement>> it = entry.iterator();
		String vs = " values ( \""+id+"\" , ";
		while (it.hasNext()) {
			Entry<String, JsonElement> elem = it.next();
			String key = elem.getKey();
			String val = elem.getValue().toString();
			sql += key + ", ";
			vs += val + ", ";
		}
		sql+=" create_by , create_date ";
		vs +="\""+createBy+"\" , now()";
		sql = sql.replaceAll(",\\s*$", "");
		vs = vs.replaceAll(",\\s*$", "");
		sql += ") " + vs + ")";
		return sql;
	}

	/**
	 * 解析update语句
	 * 
	 * @param table
	 * @param json
	 * @return
	 * @author 作者：赵进华
	 * @version 创建时间：2019年6月19日 上午10:41:00
	 */
	public static String parseUpdateSql(String table,String updateBy, String json) {
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(json);
		String sql = "";
		sql += "UPDATE " + table + " SET ";
		Set<Entry<String, JsonElement>> entry = obj.entrySet();
		Iterator<Entry<String, JsonElement>> it = entry.iterator();
		String idValue = "";
		while (it.hasNext()) {
			Entry<String, JsonElement> elem = it.next();
			String key = elem.getKey();
			String val = elem.getValue().toString();
			// 获取id的值
			if ("id".equals(key)) {
				idValue = val;
			} else {
				// 拼接除id外的set后面语句,
				sql += key + "=" + val + ", ";
			}
		}
		sql +="modify_by=\""+updateBy+"\" , "+"modify_date=now()";
		sql = sql.replaceAll(",\\s*$", "");
		sql += " WHERE id=" + idValue + "";
		sql = sql.replaceAll("\\s*AND\\s*$", "");
		return sql;
	}
	/**
	 * 将List<Map<String,Object>>转换为List<T>
	 *
	 * @param maps
	 * @param clazz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz)
			throws InstantiationException, IllegalAccessException {
		List<T> list = Lists.newArrayList();
		if (maps != null && maps.size() > 0) {
			Map<String, Object> map = null;
			T bean = null;
			for (int i = 0, size = maps.size(); i < size; i++) {
				map = maps.get(i);
				bean = clazz.newInstance();
				mapToBean(map, bean);
				list.add(bean);
			}
		}
		return list;
	}

	/**
	 * 将map装换为javabean对象
	 *
	 * @param map
	 * @param bean
	 * @return
	 */
	public static <T> T mapToBean(Map<String, Object> map, T bean) {
		BeanMap beanMap = BeanMap.create(bean);
		beanMap.putAll(map);
		return bean;
	}
	public static void main(String[] args) {

		String insert = "{\"CUST_CODE\":\"WILL\",\"ORDER_DATE\":\"1994-09-30:15:33:00\",\"PRODUCT_CODE\":\"CAR\",\"ORDER_ID\":\"144\",\"PRODUCT_PRICE\":17520.00,\"PRODUCT_AMOUNT\":3,\"TRANSACTION_ID\":\"100\"}";
		String update = "{\n" + "  \"id\": \"4353rerewr344\",\n" + "  \"address\": \"aa\",\n"
				+ "  \"companyCode\": \"bb\",\n" + "  \"companyName\": \"cc\",\n" + "  \"companyType\": \"dd\",\n"
				+ "  \"status\": 1\n" + "}";
		System.out.println(parseUpdateSql("t_user","001", update));

	}

}
