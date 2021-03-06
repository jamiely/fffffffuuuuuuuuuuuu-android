package ly.jamie.fffffffuuuuuuuuuuuu;

import java.io.Serializable;
import android.util.LruCache;

public class Cache {
	LruCache<String, Serializable> _cache;
	public static final Cache Shared = new Cache(); 
	private Cache() {
		_cache = new LruCache<String, Serializable>(1 * 1024 * 1024); // 1MB
	}
	public Serializable get(String key) {
		return get(key, true);
	}
	// Avoid doing this on the main thread
	public Serializable get(String key, Boolean checkMemoryOnly) {
		Serializable value = _cache.get(key);
		return value;
	}
	// Avoid doing this on the main thread
	public Serializable set(String key, Serializable value) {
		_cache.put(key, value);
		return value;
	}
}
