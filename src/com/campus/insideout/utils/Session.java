package com.campus.insideout.utils;

import java.util.HashMap;
import java.util.Map;

public class Session {

	private Map<Object, Object> container;

	private static Session session;

	private Session() {
		container = new HashMap<Object, Object>();
	}

	public static Session getSession() {

		if (session == null) {
			session = new Session();
			return session;
		} else {
			return session;
		}
	}

	public void put(Object key, Object value) {

		container.put(key, value);
	}

	public Object get(Object key) {

		return container.get(key);
	}

	public void cleanUpSession() {
		container.clear();
	}

	public void remove(Object key) {
		container.remove(key);
	}
}
