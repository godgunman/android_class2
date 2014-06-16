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

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MessageActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		String text = getIntent().getStringExtra("text");
		boolean isChecked = getIntent().getBooleanExtra("checkbox", false);

		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Message");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> result, ParseException e) {
				// TODO Auto-generated method stub
				String[] data = new String[result.size()];
				for (int i = 0; i < result.size(); i++) {
					data[i] = result.get(i).getString("text");
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						MessageActivity.this,
						android.R.layout.simple_list_item_1, data);
				setListAdapter(adapter);
			}
		});
	}
}
