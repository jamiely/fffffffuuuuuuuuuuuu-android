package ly.jamie.fffffffuuuuuuuuuuuu;

import java.io.Serializable;
import java.net.URL;

@SuppressWarnings("serial")
public class Comic implements Serializable {
	private URL url;
	private String title;
	private String description;
	
	public Comic(URL url) {
		setURL(url);
	}
	
	public Comic(String title, URL url) {
		this(url);
		setTitle(title);
	}
	
	public Comic(String title, URL url, String description) {
		this(title, url);
		setDescription(description);
	}

	public URL getURL() {
		return url;
	}

	public void setURL(URL url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
