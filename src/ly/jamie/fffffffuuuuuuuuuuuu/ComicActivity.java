package ly.jamie.fffffffuuuuuuuuuuuu;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;

public class ComicActivity extends Activity {
	private ImageView imageView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comic);
	
		imageView = (ImageView)findViewById(R.id.imageView);
		loadIntent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_comic, menu);
		return true;
	}
	
	private void loadComic(Comic comic) {
		if(comic == null) return;
		
		setTitle(comic.getTitle());
		
		if(comic.getURL() == null) return;
		
		new DownloadImageTask().execute(comic.getURL());
	}
	
	private void loadIntent() {
		Intent intent = getIntent();
		loadComic((Comic) intent.getSerializableExtra("comic"));
	}
	
	private void setBitmap(Bitmap bitmap) {
		if(bitmap == null) return;
		
		imageView.setImageBitmap(bitmap);
	}
		
	private class DownloadImageTask extends AsyncTask<URL, Integer, Bitmap> {
		@Override
		protected Bitmap doInBackground(URL... params) {
			return bitmapForURL(params[0]);
		}
		
		protected void onPostExecute(Bitmap bitmap) {
			setBitmap(bitmap);
		}
		
		private Bitmap bitmapForURL(URL linkURL) {
			try {
				InputStream stream = linkURL.openConnection().getInputStream();
				Bitmap image = BitmapFactory.decodeStream(stream);
				return image;
			}
			catch(IOException ex) {
				// pass
			}
			return null;
		}
	}
}
