package com.example.simpleui;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MessageActivity extends ActionBarActivity {

	private TextView textView;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		textView = (TextView) findViewById(R.id.textView1);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		String text = getIntent().getStringExtra("text");

		ParseObject messageObject = new ParseObject("Message");
		messageObject.put("text", text);
		messageObject.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				Log.d("debug", "save done");
				loadMessagsFromParse();
			}
		});
		Log.d("debug", "after saveInbackground");
	}

	private void loadMessagsFromParse() {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Message");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> messages, ParseException e) {
				progressBar.setVisibility(View.GONE);
				String text = "";
				for (ParseObject message : messages) {
					text += message.getString("text") + "\n";
				}
				textView.setText(text);
			}
		});

	}
}
