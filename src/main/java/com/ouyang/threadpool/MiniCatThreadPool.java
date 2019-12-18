package com.ouyang.threadpool;


import com.ouyang.config.ServerConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MiniCatThreadPool {


	public static final ExecutorService  HTTP_POOL =  new ThreadPoolExecutor(100, ServerConfig.HTTP_THREAD_NUM,
	          10,TimeUnit.SECONDS,
	          new LinkedBlockingQueue<Runnable>());

	public static final ExecutorService  MINICAT_POOL =  new ThreadPoolExecutor(5,ServerConfig.MINICAT_THREAD_NUM,
	          10,TimeUnit.SECONDS,
	          new LinkedBlockingQueue<Runnable>());

}
