package ly.jamie.fffffffuuuuuuuuuuuu;

import java.util.*;

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
	private List<Comic> comics = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.comics = getInitialComics();
		
		setContentView(R.layout.activity_main);
		
		this.setupListView();
		
		new DownloadRSSTask().execute("http://www.reddit.com/r/fffffffuuuuuuuuuuuu.rss");
	}
	
	private List<Comic> getInitialComics() {
		@SuppressWarnings("unchecked")
		List<Comic> cached = (List<Comic>) Cache.Shared.get("comics");
		if(cached != null) return cached;
		return new ArrayList<Comic>();
	}
	
	private void handleSelection(int position) {
		if(getComics().isEmpty()) return;
		
		this.gotoComic(getComics().get(position));
	}
	
	private void gotoComic(Comic item) {
		Intent i = new Intent(this, ComicActivity.class);
		i.putExtra("comic", item);
		this.startActivity(i);
	}
	
	private void setupListView() {
		this.setListAdapterUsingData(this.listData());
		this.listView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View childView, int position,
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
		map1.put("title", "Downloading data");
		map1.put("link", "");
		
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("title", "...");
		map2.put("link", "");
		
		data.add(map1);
		data.add(map2);
		
		return data;
	}
	
	private ListAdapter listAdapter(List<HashMap<String, String>> data) {
		return new SimpleAdapter(
				this, data, R.layout.simple_list_item, 
				new String[]{"title"}, new int[]{R.id.simple_list_item_text});
	}

	
	private List<HashMap<String, String>> comicsToListData(List<Comic> comics) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for(Comic item: comics) {
			list.add(comicToMap(item));

		}

		return list;
	}

	private HashMap<String, String> comicToMap(Comic comic) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("title", comic.getTitle());
		map.put("link", comic.getURL().toString());
		return map;
	}

	private List<Comic> getComics() {
		return comics;
	}

	private void setComics(ArrayList<Comic> comics) {
		this.comics = comics;
		setListAdapterUsingData(comicsToListData(this.comics));
	}

	private class DownloadRSSTask extends AsyncTask<String, Integer, ArrayList<Comic>> {
		@Override
		protected ArrayList<Comic> doInBackground(String... feedURIs) {
			if(feedURIs.length > 0) { 
				return new RSSProcessor(feedURIs[0]).load().getComics();				
			}
			return new ArrayList<Comic>();
		}
		
		protected void onPostExecute(ArrayList<Comic> comics) {
			Cache.Shared.set("comics", comics);
			setComics(comics);
		}
	}
}
