package com.ouyang.container;

import com.ouyang.config.ServerConfig;
import com.ouyang.http.HttpSession;
import com.ouyang.threadpool.MiniCatThreadPool;
import com.ouyang.utils.EncryptUtil;
import com.ouyang.utils.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * session容器
 *
 * @author Coody
 *
 */
public class SessionContainer {

	private static final Map<String, HttpSession> SYSTEM_SESSION_CONTAINER = new ConcurrentHashMap<String, HttpSession>();


	static{
		MiniCatThreadPool.MINICAT_POOL.execute(new Runnable() {
			public void run() {
				sessionGuard();
			}
		});
	}

	private static void sessionGuard(){
		while(true){
			try {
				if(StringUtil.isNullOrEmpty(SYSTEM_SESSION_CONTAINER)){
					return;
				}
				List<String> willCleanSessionIds=new ArrayList<String>();
				for(String key:SYSTEM_SESSION_CONTAINER.keySet()){
					HttpSession session=SYSTEM_SESSION_CONTAINER.get(key);
					if(System.currentTimeMillis()-session.getActiveTime().getTime()> ServerConfig.SESSION_TIMEOUT){
						willCleanSessionIds.add(key);
					}
				}
				for(String key:willCleanSessionIds){
					SYSTEM_SESSION_CONTAINER.remove(key);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					TimeUnit.SECONDS.sleep(1l);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static boolean containsSession(String sessionId) {
		return SYSTEM_SESSION_CONTAINER.containsKey(sessionId);
	}

	public static HttpSession getSession(String sessionId) {
		if(StringUtil.isNullOrEmpty(sessionId)){
			return null;
		}
		HttpSession session=SYSTEM_SESSION_CONTAINER.get(sessionId);
		if(session==null){
			return session;
		}
		session.setActiveTime(new Date());
		return session;
	}

	public static HttpSession setSession(String sessionId, HttpSession session) {
		return SYSTEM_SESSION_CONTAINER.put(sessionId, session);
	}

	public static HttpSession initSession(String sessionId) {
		if (SYSTEM_SESSION_CONTAINER.containsKey(sessionId)) {
			return SYSTEM_SESSION_CONTAINER.get(sessionId);
		}
		HttpSession session = new HttpSession();
		SYSTEM_SESSION_CONTAINER.put(sessionId, session);
		return session;
	}


	private static int sessionIndex = 0;

	public static String createSessionId() {
		Integer currentSessionIndex = 0;
		synchronized (SessionContainer.class) {
			sessionIndex++;
			currentSessionIndex = sessionIndex;
		}
		String key = System.currentTimeMillis() + currentSessionIndex.toString();
		return EncryptUtil.md5Code(key);
	}
}
