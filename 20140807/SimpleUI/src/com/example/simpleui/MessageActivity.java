package com.example.simpleui;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MessageActivity extends ActionBarActivity {

	private ProgressBar progressBar;
	private ProgressDialog progressDialog;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		listView = (ListView) findViewById(R.id.listView1);
		String text = getIntent().getStringExtra("text");

		progressDialog = new ProgressDialog(this);

		ParseObject messageObject = new ParseObject("Message");
		messageObject.put("text", text);
		messageObject.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				Log.d("debug", "save done");
				progressBar.setVisibility(View.GONE);
				loadMessagsFromParse();
			}
		});
		Log.d("debug", "after saveInbackground");
	}

	private void loadMessagsFromParse() {
		progressDialog.setTitle("Loading...");
		progressDialog.show();

		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Message");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> messages, ParseException e) {
				progressDialog.dismiss();

				List<String> texts = new ArrayList<String>();
				for (ParseObject message : messages) {
					texts.add(message.getString("text"));
				}

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						MessageActivity.this,
						android.R.layout.simple_list_item_1, texts);
				listView.setAdapter(adapter);
			}
		});

	}
}
