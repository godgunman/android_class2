package com.example.listview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends ActionBarActivity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView1);

		int[] imageIds = new int[] { R.drawable.image1, R.drawable.image2,
				R.drawable.image3, R.drawable.image4, R.drawable.image5,
				R.drawable.image6 };

		String[] imageTexts = new String[] { "image1", "image2", "image3",
				"image4", "image5", "image6" };

		List<Map<String, Object>> data = new ArrayList<>();
		for (int i = 0; i < imageIds.length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("image", imageIds[i]);
			item.put("text", imageTexts[i]);
			data.add(item);
		}

		String[] from = new String[] { "image", "text" };
		int[] to = new int[] { R.id.imageView1, R.id.textView1 };
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.my_simple_list_item2, from, to);

		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
