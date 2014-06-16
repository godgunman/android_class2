package com.example.simpleui;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MessageActivity extends ListActivity {

	private ProgressDialog progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		/*
		progress = new ProgressDialog(this);
		progress.setTitle("Loading...");
		progress.show();
		*/
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Message");
		
		/*
		try {
			List<ParseObject> result = query.find();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/

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
//				progress.dismiss();
			}
		});
	}
}
