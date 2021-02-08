package com.spring.common.util.id;

import java.util.UUID;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:UUID工厂类
*/
public class UUIDFactory {
	
	private final static char[] DIGITS16 = "0123456789abcdef".toCharArray();

	/**
	 * 生产UUID
	 * 部署前须校准服务器时钟 *
	 * 
	 * @return
	 */
	public static String createId(){
		String s = UUID.randomUUID().toString(); 
		return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
	}
	public static String newUuid2(){
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "");
	}
	
	public static String nextId() {
        UUID u = UUID.randomUUID();
        return toIdString(u.getMostSignificantBits()) + toIdString(u.getLeastSignificantBits());
    }
	
    private static String toIdString(long l) {
        char[] buf = "0000000000000000".toCharArray();
        int length = 16;
        long least = 15L;
        do {
            buf[--length] = DIGITS16[(int) (l & least)];
            l >>>= 4;
        } while (l != 0);
        return new String(buf);
    }
	
	/*
	 * 单元测试方法
	 * @param args
	 */
	public static void main(String[] arg){
		//正确性测试
		
		long l1 = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			System.out.println(newUuid2());
        }
		System.out.println(System.currentTimeMillis()-l1);
		
		long l3 = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			System.out.println(createId());
		}
		System.out.println(System.currentTimeMillis()-l3);
		
		long l5 = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			nextId();
		}
		System.out.println(System.currentTimeMillis()-l5);
		
		//并发测试
		java.util.concurrent.ExecutorService pool = java.util.concurrent.Executors.newFixedThreadPool(5);
		for (int i = 0; i < 3; i++) {
			pool.execute(new Runnable(){
				@Override
				public void run() {
					int j = 1;
					do{
						System.out.println(j+"-"+createId());
					}while(7 > j++);
				}
			});
		}
		pool.shutdown();
		System.err.println(System.currentTimeMillis());
	}

}
