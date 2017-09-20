package com.norra.cloud.http.frame;

/**
 * 
 * @author charliu
 * 
 * @param <T>
 *            缓存对象
 */
public abstract class Cache<T> {
	public Cache() {
	}

	abstract void put(String cacheKey, T value);

	abstract T get(String cacheKey);

	abstract void clear();
}
