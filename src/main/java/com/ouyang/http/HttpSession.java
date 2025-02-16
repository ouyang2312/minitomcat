package com.ouyang.http;

import java.util.*;

@SuppressWarnings("unchecked")
public class HttpSession {

	private final Map<String, Object> SESSION_CONTAINER = new HashMap<String, Object>();

	private Date activeTime = new Date();

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public int size() {
		return SESSION_CONTAINER.size();
	}

	public boolean isEmpty() {
		return SESSION_CONTAINER.isEmpty();
	}

	public boolean containsKey(Object key) {
		return SESSION_CONTAINER.containsKey(key);
	}

	public <T> T get(Object key) {
		return (T) SESSION_CONTAINER.get(key);
	}

	public Object put(String key, Object value) {
		return SESSION_CONTAINER.put(key, value);
	}

	public Object remove(Object key) {
		return SESSION_CONTAINER.remove(key);
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		SESSION_CONTAINER.putAll(m);
	}

	public Set<String> keySet() {
		return SESSION_CONTAINER.keySet();
	}

	public Collection<Object> values() {
		return SESSION_CONTAINER.values();
	}

	public Set<Map.Entry<String, Object>> entrySet() {
		return SESSION_CONTAINER.entrySet();
	}

	public boolean containsValue(Object value) {
		return SESSION_CONTAINER.containsValue(value);
	}

	public void clear() {
		SESSION_CONTAINER.clear();
	}

	public Map<String, Object> getMap() {
		return SESSION_CONTAINER;
	}

}
