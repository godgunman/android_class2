package com.example.simpleui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MessageActivity extends Activity {

	private ProgressDialog progressDialog;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		listView = (ListView) findViewById(R.id.listView1);

		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("SimpeUI");
		progressDialog.setMessage("Loading ...");
		progressDialog.setCancelable(false);
		progressDialog.show();

		String text = getIntent().getStringExtra("text");
		boolean isChecked = getIntent().getBooleanExtra("checkBox", false);

		saveData(text, isChecked);

	}

	private void loadData() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> messages, ParseException e) {
				if (e == null) {

					List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
					String[] from = new String[] { "text", "checkBox" };
					int[] to = new int[] { android.R.id.text1,
							android.R.id.text2 };

					for (ParseObject message : messages) {
						Map<String, Object> item = new HashMap<String, Object>();
						item.put("text", message.getString("text"));
						item.put("checkBox", message.getBoolean("checkBox"));
						data.add(item);
					}

					SimpleAdapter adapter = new SimpleAdapter(
							MessageActivity.this, data,
							android.R.layout.simple_list_item_2, from, to);

					listView.setAdapter(adapter);
					
					progressDialog.dismiss();
				} else {
					e.printStackTrace();
				}
			}
		});
	}

	private void saveData(String text, boolean isChecked) {
		ParseObject testObject = new ParseObject("Message");
		testObject.put("text", text);
		testObject.put("checkBox", isChecked);
		testObject.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					Log.d("debug", "OK");
				} else {
					e.printStackTrace();
				}
				ok();
				loadData();
			}
		});
	}

	private void ok() {
		Toast.makeText(this, "done.", Toast.LENGTH_SHORT).show();
	}
}
