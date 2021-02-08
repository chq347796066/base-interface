package com.spring.common.sequence;

import java.text.DecimalFormat;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Description: 生成序列主键
 * @author
 * @date 2016年3月8日 下午2:08:13
 * @update
 */
public class SequenceUtil {

	private static final String STR_FORMAT = "0000";
	
	private static final int INDEX = 1000;

	private static final Queue<String> QUEUE = new ConcurrentLinkedQueue<String>();

	/**
	 * @Description: 队列初始化
	 * @param
	 * @return void
	 * @throws
	 * @author
	 */
	public static synchronized void init() {
		for (int i = 0; i < INDEX; i++) {
			QUEUE.add(format(i + ""));
		}
	}

	public static String poll() {
		if (QUEUE.isEmpty()) {
			init();
		}
		String index = QUEUE.poll();
		return index;
	}

	public static String format(String index) {
		Integer intHao = Integer.parseInt(index);
		DecimalFormat df = new DecimalFormat(STR_FORMAT);
		return df.format(intHao);
	}

	/**
	* @Description: 获取序列 四位字母开头（BSBP)+时间搓+4位随机数
	* @param @param pre
	* @param @return   
	* @return String  
	* @throws
	* @author
	 */
	public static String getSequence(String pre) {
		return pre + System.currentTimeMillis() + poll();
	}

	/*public static void main(String[] args) {
		 init();
		 System.err.println(getSequence("BSBP"));

	}*/
}

