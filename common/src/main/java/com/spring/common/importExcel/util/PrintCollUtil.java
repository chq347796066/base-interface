package com.spring.common.importExcel.util;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Iterator;

public class PrintCollUtil {

	private static Logger logger = LoggerFactory.getLogger(PrintCollUtil.class);

	public static void printList(Collection<?> list) {
		if (CollectionUtils.isEmpty(list))
			return;
		StringBuffer buffer = new StringBuffer();
		buffer.append(
				"\n--------------------------------------------------------------------------------------------------------------------");
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object obj = (Object) iterator.next();
			buffer.append("\n").append(obj);
		}
		buffer.append(
				"\n--------------------------------------------------------------------------------------------------------------------");
		logger.info(buffer.toString());
	}
}
