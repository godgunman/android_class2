package com.example.simpleui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.SaveCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MessageActivity extends Activity {

	private ListView listView;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_message);

		listView = (ListView) findViewById(R.id.listView1);
		progressDialog = new ProgressDialog(this);
		
		String text = getIntent().getStringExtra("text");
		boolean checked = getIntent().getBooleanExtra("checked", false);

		progressDialog.setTitle("Loading ...");
		progressDialog.show();
		saveToParse(text, checked);
	}

	private void saveToParse(String text, boolean checked) {

		ParseObject messageObject = new ParseObject("Message");
		messageObject.put("text", text);
		messageObject.put("checked", checked);
		messageObject.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				loadMessageFromParse();
			}
		});
	}

	private void loadMessageFromParse() {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> messages, ParseException e) {
				setListView(messages);
				progressDialog.dismiss();
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
