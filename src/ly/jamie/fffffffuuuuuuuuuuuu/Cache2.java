package ly.jamie.fffffffuuuuuuuuuuuu;

import java.io.Serializable;
import android.util.LruCache;

public class Cache2 {
	LruCache<String, Serializable> _cache;
	public static final Cache2 Shared = new Cache2(); 
	private Cache2() {
		_cache = new LruCache<String, Serializable>(1 * 1024 * 1024); // 1MB
	}
	public Serializable get(String key) {
		return _cache.get(key);
	}
	public Serializable set(String key, Serializable value) {
		return _cache.put(key, value);
	}
}
