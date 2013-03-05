package ly.jamie.fffffffuuuuuuuuuuuu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;
import org.mcsoxford.rss.RSSReader;
import org.mcsoxford.rss.RSSReaderException;

public class RSSProcessor {
	private String feedURI = "";
	private List<RSSItem> rssItems = null;
	private List<HashMap<String, String>> rssMapList = null;
	
	public RSSProcessor(String feedURI) {
		this.feedURI = feedURI;
		this.rssItems = new ArrayList<RSSItem>();
		this.setRssMapList(new ArrayList<HashMap<String, String>>());
	}
	private RSSFeed retrieveFeed() {
		try {
			return new RSSReader().load(this.feedURI);
		}
		catch(RSSReaderException ex) {
			return null;
		}
	}
	
	public RSSProcessor load() {
		setRssMapList(this.retrieveRSSMapList());
		return this;
	}
	
	private List<HashMap<String, String>> retrieveRSSMapList() {
		// belongs to outer class
		setRssItems(this.rssItems());
		
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for(RSSItem item: getRssItems()) {
			list.add(this.rssItemToMap(item));
		}
		return list;
	}
	
	public ArrayList<Comic> getComics() {
		List<RSSItem> rssItems = getRssItems();
		
		ArrayList<Comic> comics = new ArrayList<Comic>();
		for(RSSItem item: rssItems) {
			comics.add(ComicFactory.Shared.getComicFromRedditRSSItem(item));
		}
		return comics;
	}
	
	private HashMap<String, String> rssItemToMap(RSSItem item) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("title", item.getTitle());
		map.put("link", item.getLink().toString());
		map.put("thumbs", item.getThumbnails().toString());
		return map;
	}
	
	private List<RSSItem> rssItems() {
		return this.retrieveFeed().getItems();
	}
	public List<RSSItem> getRssItems() {
		return rssItems;
	}
	public void setRssItems(List<RSSItem> rssItems) {
		this.rssItems = rssItems;
	}
	public List<HashMap<String, String>> getRssMapList() {
		return rssMapList;
	}
	public void setRssMapList(List<HashMap<String, String>> rssMapList) {
		this.rssMapList = rssMapList;
	}
}
