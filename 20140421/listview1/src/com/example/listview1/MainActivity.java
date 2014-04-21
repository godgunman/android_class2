package com.example.listview1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private ListView listView;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			listView = (ListView) rootView.findViewById(R.id.listView1);

			String[] text = new String[] { "1", "2", "3", "4", "5", "6", "7",
					"8", "9", "10" };

			int[] imageIds = new int[] { R.drawable.img1, R.drawable.img2,
					R.drawable.img3, R.drawable.img4, R.drawable.img5,
					R.drawable.img6, R.drawable.img7, R.drawable.img8,
					R.drawable.img9, R.drawable.img10, };

			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

			for (int i = 0; i < text.length; i++) {
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("text", text[i]);
				item.put("image", imageIds[i]);
				data.add(item);
			}

			String[] from = new String[] { "text", "image" };
			int[] to = new int[] { R.id.textView1, R.id.imageView1 };

			SimpleAdapter adapter = new SimpleAdapter(getActivity(), data,
					R.layout.list_item, from, to);

			listView.setAdapter(adapter);
			return rootView;
		}
	}

}
