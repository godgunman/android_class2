package com.example.simpleui;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MessageActivity extends Activity {

	private TextView textView;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		textView = (TextView) findViewById(R.id.textView1);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);

		String text = getIntent().getStringExtra("text");
		boolean isChecked = getIntent().getBooleanExtra("checkBox", false);

		saveData(text, isChecked);
		
	}

	private void loadData() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> messages, ParseException e) {
				if (e == null) {
					String content = "";
					for (ParseObject message : messages) {
						content += message.getString("text") + "\n";
					}
					textView.setText(content);
					progressBar.setVisibility(View.GONE);
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
