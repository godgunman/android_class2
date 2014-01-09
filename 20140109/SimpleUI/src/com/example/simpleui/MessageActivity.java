package com.example.simpleui;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MessageActivity extends Activity {

	private TextView textView;
	private ProgressDialog progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		textView = (TextView) findViewById(R.id.textView1);
		progress = new ProgressDialog(this);
		progress.show();
		
		String text = getIntent().getStringExtra("text");

		ParseObject obj = new ParseObject("message3");
		obj.put("text", text);
		obj.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					Log.d("debug", "done");
					setData();
				} else {
					e.printStackTrace();
				}
			}
		});
	}

	private void setData() {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("message3");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					String all = "";
					for (ParseObject obj : objects) {
						all += obj.getString("text") + "\n";
					}
					textView.setText(all);
					progress.dismiss();
				} else {
					e.printStackTrace();
				}

			}
		});
	}

}
