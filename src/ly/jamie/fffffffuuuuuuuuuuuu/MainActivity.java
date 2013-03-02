package ly.jamie.fffffffuuuuuuuuuuuu;

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
	private List<RSSItem> rssItems = null;
	private List<Comic> comics = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.setupListView();
		
		new DownloadRSSTask().execute("http://www.reddit.com/r/fffffffuuuuuuuuuuuu.rss");
	}
	
	private void handleSelection(int position) {
		if(rssItems == null) return;
		
		this.gotoComic(comics.get(position));
	}
	
	private void gotoComic(Comic item) {
		Intent i = new Intent(this, ComicActivity.class);
		i.putExtra("comic", item);
		this.startActivity(i);
	}
	
	private void setupListView() {
		this.setListAdapterUsingData(this.listData());
		this.listView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView parentView, View childView, int position,
					long id) {
				handleSelection(position);
			}
		});
	}
	
	private void setListAdapterUsingData(List<HashMap<String, String>> data) {
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
		protected List<HashMap<String, String>> doInBackground(String... feedURIs) {
			if(feedURIs.length > 0) { 
				RSSProcessor processor = new RSSProcessor(feedURIs[0]);
				processor.load();
				rssItems = processor.getRssItems();
				comics = new ArrayList<Comic>();
				for(RSSItem item: rssItems) {
					comics.add(ComicFactory.Shared.getComicFromRedditRSSItem(item));
				}
				return processor.getRssMapList();
			}
			return new ArrayList<HashMap<String, String>>();
		}
		
		protected void onPostExecute(List<HashMap<String, String>> data) {
			setListAdapterUsingData(data);
		}
	}
}
