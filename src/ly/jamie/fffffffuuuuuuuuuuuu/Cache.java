package ly.jamie.fffffffuuuuuuuuuuuu;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.jakewharton.DiskLruCache;

public class Cache {
	private DiskLruCache cache;
	public static Cache Shared = new Cache();
	
	private Cache() {}
	
	/// Sets up the cache
	public Boolean setup() {
		String javaTmpDir = System.getProperty("java.io.tmpdir");
        File cacheDir = new File(javaTmpDir, "fffffffuuuuuuuuuuuu-cache");
        int appVersion = 2;
        int valueCount = 2; // the number of values per cache entry
        long maxSizeBytes = 1048576; // 1 MB
        try {
			cache = DiskLruCache.open(cacheDir, appVersion, valueCount, maxSizeBytes);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	private DiskLruCache.Editor editor(String key) {
		try {
			return cache.edit(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean set(String key, Serializable value) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ObjectOutputStream objectStream;
		try {
			objectStream = new ObjectOutputStream(stream);
			objectStream.writeObject(value);
			String serializedValue = stream.toString("UTF-8");
			return setString(key, serializedValue);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public Object get(String key) {
		String value = getString(key);
		if(value == null) return null;
		
		ObjectInputStream objectStream;
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(value.getBytes());
			objectStream = new ObjectInputStream(is);
			return objectStream.readObject();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean setString(String key, String value) {
		DiskLruCache.Editor editor = editor(key);
		if(editor == null) return false;
				
		try {
			editor.set(0, value);
			editor.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public String getString(String key) {
		try {
			return editor(key).getString(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
