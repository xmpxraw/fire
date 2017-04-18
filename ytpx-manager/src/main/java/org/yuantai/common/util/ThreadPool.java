package org.yuantai.common.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public class ThreadPool {
	
	private static int threadNum=5;
	private static ExecutorService executor;
	
	static {
		executor=Executors.newFixedThreadPool(threadNum,new ThreadFactory() {
			public Thread newThread(Runnable run) {
				Thread t=new Thread(run,"threadpool");
				t.setDaemon(true);
				return t;
			}
		});
	}
	
	public static void execute(Runnable run) {
		executor.execute(run);
	}
	
	public static <T> Future<T> submit(Callable<T> call) {
		return executor.submit(call);
	}
}