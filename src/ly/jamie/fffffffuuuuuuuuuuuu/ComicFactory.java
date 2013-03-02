package ly.jamie.fffffffuuuuuuuuuuuu;

import org.mcsoxford.rss.RSSItem;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class ComicFactory {
	public static ComicFactory Shared = new ComicFactory();
	private ComicFactory(){}
	public Comic getComicFromRedditRSSItem(RSSItem item) {
		Comic comic = getComicFromGenericRSSItem(item);
		comic.setDescription(item.getDescription());
		return comic;
	}
	public Comic getComicFromImgurRSSItem(RSSItem item) {
		return getComicFromGenericRSSItem(item);
	}
	public Comic getComicFromGenericRSSItem(RSSItem item) {
		return new Comic(item.getTitle(), uriToUrl(item.getLink()));
	}
	private static URL uriToUrl(Uri uri) {
		try {
			return new URL(uri.toString());
		}
		catch(MalformedURLException ex) {
			return null;
		}
	}
}
