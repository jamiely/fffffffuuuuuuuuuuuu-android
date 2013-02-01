package ly.jamie.fffffffuuuuuuuuuuuu;

import java.net.URL;
import java.util.*;

import org.mcsoxford.rss.*;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	private List<HashMap<String, String>> currentData;
	private List<RSSItem> rssItems = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.setupListView();
		
		new DownloadRSSTask().execute("");
	}
	
	private void handleSelection(int position) {
		if(rssItems == null) return;
		
		this.gotoComic(rssItems.get(position));
	}
	
	private void gotoComic(RSSItem item) {
		Intent i = new Intent(this, ComicActivity.class);
		i.putExtra("title", item.getTitle());
		i.putExtra("link", item.getLink().toString());
		i.putExtra("description", item.getDescription().toString());
		this.startActivity(i);
	}
	
	private void setupListView() {
		this.setListAdapterUsingData(this.listData());
		this.listView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parentView, View childView, int position,
					long id) {
				handleSelection(position);
			}
		});
	}
	
	private void setListAdapterUsingData(List<HashMap<String, String>> data) {
		this.currentData = data;
		this.listView().setAdapter(this.listAdapter(data));
	}
	
	private ListView listView() {
		return (ListView) this.findViewById(R.id.comicsListView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private List<HashMap<String, String>> listData() {
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("title", "value1");
		map1.put("link", "value2");
		
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("title", "V1");
		map2.put("link", "v2");
		
		data.add(map1);
		data.add(map2);
		
		return data;
	}
	
	private ListAdapter listAdapter(List<HashMap<String, String>> data) {
		return new SimpleAdapter(
				this, data, R.layout.simple_list_item, 
				new String[]{"title"}, new int[]{R.id.simple_list_item_text});
	}
	
	private class DownloadRSSTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
		@Override
		protected List<HashMap<String, String>> doInBackground(String... arg0) {
			return this.rssMapList();
		}
		
		protected void onPostExecute(List<HashMap<String, String>> data) {
			setListAdapterUsingData(data);
		}
		
		private RSSFeed retrieveFeed() {
			try {
				return new RSSReader().load(this.feedURI());
			}
			catch(RSSReaderException ex) {
				return null;
			}
		}
		
		private String feedURI() {
			return "http://www.reddit.com/r/fffffffuuuuuuuuuuuu.rss";
		}
		
		private List<HashMap<String, String>> rssMapList() {
			// belongs to outer class
			rssItems = this.rssItems();
			
			List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
			for(RSSItem item: rssItems) {
				list.add(this.rssItemToMap(item));
			}
			return list;
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
	}
	
	
	
}
