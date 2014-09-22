package com.example.simpleui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MessageActivity extends Activity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_message);

		listView = (ListView) findViewById(R.id.listView1);

		String text = getIntent().getStringExtra("text");
		boolean checked = getIntent().getBooleanExtra("checked", false);

		saveToParse(text, checked);
		loadMessageFromParse();
	}

	private void saveToParse(String text, boolean checked) {

		ParseObject messageObject = new ParseObject("Message");
		messageObject.put("text", text);
		messageObject.put("checked", checked);
		messageObject.saveInBackground();
	}

	private void loadMessageFromParse() {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> messages, ParseException e) {
				setListView(messages);
			}
		});

	}

	private void setListView(List<ParseObject> messages) {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();

		for (ParseObject message : messages) {

			Map<String, String> item = new HashMap<String, String>();
			item.put("text", message.getString("text"));
			item.put("checked", "" + message.getBoolean("checked"));

			data.add(item);
		}

		String[] from = new String[] { "text", "checked" };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

		SimpleAdapter adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_2, from, to);

		listView.setAdapter(adapter);
	}
}
