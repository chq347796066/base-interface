//
//package com.spring.common.util.thread;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
//* @author 作者：赵进华
//* @version 创建时间：2019年3月21日 上午9:36:54
//* @Desc类说明:线程池工具类
//*/
//public class ThreadPool {
//
//	private int workNum=0;
//
//	private ExecutorService executorService;
//
//	private static ThreadPool instantce=getInstance();
//
//	private ThreadPool(){
//		workNum=Runtime.getRuntime().availableProcessors()+1;
//		executorService=Executors.newFixedThreadPool(workNum);
//	}
//
//	public static synchronized ThreadPool getInstance(){
//		if(instantce==null){
//			return new ThreadPool();
//		}
//		return instantce;
//	}
//
//	public void addTask(ThreadTask newTask){
//		executorService.execute(newTask);
//	}
//
//	public synchronized void destroy(){
//		executorService.shutdown();
//	}
//}
//
