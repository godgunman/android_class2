package com.example.simpleui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MessageActivity extends Activity {

	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		textView = (TextView) findViewById(R.id.textView1);
		String text = getIntent().getStringExtra("text");

		ParseObject obj = new ParseObject("message3");
		obj.put("text", text);
		obj.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					Log.d("debug", "done");
				} else {
					e.printStackTrace();
				}
			}
		});

		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("message3");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					String all = "";
					for (ParseObject obj : objects) {
						all += obj.getString("text");
					}
					textView.setText(all);
				} else {
					e.printStackTrace();
				}

			}
		});

	}

}
