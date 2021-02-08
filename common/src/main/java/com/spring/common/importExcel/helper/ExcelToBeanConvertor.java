package com.spring.common.importExcel.helper;

import com.spring.common.exception.ServiceException;
import com.spring.common.importExcel.exception.InvalidExcelTemplateException;
import com.spring.common.importExcel.util.ExcelUtil;
import com.spring.common.importExcel.util.MapUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel行数据转对象实例转换器
 * 
 * @author Michael
 * 
 * @param <T>
 *            要转换的对象实例的类型
 */
public class ExcelToBeanConvertor<T> {

	/**
	 * 按照指定的模板格式读取excel数据，封装至Map<String, List<T>>对象集合中，所有符合模板格式的sheet页的数据都会被读入<br>
	 * map的键值对的格式是：<sheet页名称, List<T>>
	 * 
	 * @param input
	 *            输入流
	 * @param titles
	 *            模板格式信息
	 * @param clazz
	 *            类实例
	 * @return 当excel数据不符合指定的模板的格式时将抛出
	 *         <code>com.icip.framework.service.importData.exception.InvalidExcelTemplateException</code>
	 *         异常
	 */
	public Map<String, List<T>> readToBeanMap(InputStream input,
			String[] titles, Class<T> clazz) throws ServiceException {
		if (ArrayUtils.isEmpty(titles))
			return null;

		Map<String, List<Object[]>> dataMap = ExcelUtil.readToMap(input);
		Map<String, List<T>> map = readToBeanMap(titles, clazz, dataMap);

		return map;
	}



	public Map<String, List<T>> readToBeanMapForBank(InputStream input,
											  String[] titles, Class<T> clazz) throws ServiceException {
		if (ArrayUtils.isEmpty(titles)){
			return null;
		}
		Map<String, List<Object[]>> dataMap = ExcelUtil.readToMapForBank(input);
		Map<String, List<T>> map = readToBeanMap(titles, clazz, dataMap);

		return map;
	}


	
	/**
	 * 按照指定的模板格式读取excel数据，封装至Map<String, List<T>>对象集合中，所有符合模板格式的sheet页的数据都会被读入<br>
	 * map的键值对的格式是：<sheet页名称, List<T>>
	 * 
	 * @param input
	 *            输入流
	 * @param titles
	 *            模板格式信息
	 * @param clazz
	 *            类实例
	 * @return 当excel数据不符合指定的模板的格式时将抛出
	 *         <code>com.icip.framework.service.importData.exception.InvalidExcelTemplateException</code>
	 *         异常
	 */
	public Map<String, List<T>> readToBeanMap(InputStream input, int sheetIndex, String[] titles, Class<T> clazz) throws ServiceException {
		if (ArrayUtils.isEmpty(titles))
			return null;
		Map<String, List<Object[]>> dataMap = ExcelUtil.readSheetIndexToList(input, sheetIndex);
		return readToBeanMap(titles, clazz, dataMap);
	}

	/**
	 *  新增对员工的批量导入的模板处理
	 * @param input
	 * @param sheetIndex
	 * @param titles
	 * @param clazz
	 * @return
	 */
	public Map<String, List<T>> readToBeanMap2(InputStream input, int sheetIndex, String[] titles, Class<T> clazz) throws ServiceException {
		if (ArrayUtils.isEmpty(titles)) {
			return null;
		}
		Map<String, List<Object[]>> dataMap = ExcelUtil.readSheetIndexToList(input, sheetIndex);
		if(MapUtil.isNotEmpty(dataMap)){
			for (Map.Entry<String, List<Object[]>> entry: dataMap.entrySet()){
				String sheetName = entry.getKey();
				List<Object[]> dataList = entry.getValue();
				if(CollectionUtils.isNotEmpty(dataList)){
					if(dataList.size() > 2){
						/*从第三行开始截取*/
						List<Object[]> subList = dataList.subList(2, dataList.size());
						dataMap.put(sheetName, subList);
					}
				}
			}
		}
		return readToBeanMap(titles, clazz, dataMap);
	}


	/**
	 * 获取符合模板格式的sheet页名称列表
	 * 
	 * @param dataMap
	 *            excel文件数据Map集合(键值对形式：<sheet名称, List<Object[]>>)
	 * @param titles
	 *            模板格式信息
	 * @return 所有的sheet页都不符合模板格式时返回null
	 */
	@SuppressWarnings("deprecation")
	private List<String> validSheet(Map<String, List<Object[]>> dataMap,
			String[] titles) {
		List<String> validSheetList = null;
		if (MapUtils.isNotEmpty(dataMap)) {
			validSheetList = new ArrayList<String>();
			for (Map.Entry<String, List<Object[]>> entry : dataMap.entrySet()) {
				List<Object[]> rowDatas = entry.getValue();
				if (CollectionUtils.isNotEmpty(rowDatas)) {
					Object[] arr = ArrayUtils.subarray(rowDatas.get(0), 0,
							titles.length);
					if (ArrayUtils.isEquals(titles, arr))
						validSheetList.add(entry.getKey());
				}
			}
		}
		return validSheetList;
	}
	
	private Map<String, List<T>> readToBeanMap(String[] titles, Class<T> clazz,
			Map<String, List<Object[]>> dataMap) throws ServiceException {
		List<String> validSheets = validSheet(dataMap, titles);

		if (CollectionUtils.isEmpty(validSheets))
			throw new InvalidExcelTemplateException();

		Map<String, List<T>> map = new HashMap<String, List<T>>();

		List<T> list = null;

		BeanGenerator<T> beanGenerator = new BeanGenerator<>();

		List<Object[]> dataList = null;
		T bean = null;
		for (Map.Entry<String, List<Object[]>> entry : dataMap.entrySet()) {
			String sheetName = entry.getKey();
			if (validSheets.contains(sheetName)) {
				dataList = entry.getValue();
				dataList.remove(0); // 去掉头信息
				list = new ArrayList<>();
				for (Object[] rowData : dataList) {
					bean = beanGenerator.generateBean(clazz, rowData);
					if (bean != null)
						list.add(bean);
				}
				map.put(sheetName, list);
			}
		}
		return map;
	}

}
