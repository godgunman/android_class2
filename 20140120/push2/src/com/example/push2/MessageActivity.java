package com.example.push2;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;

public class MessageActivity extends Activity {

	private EditText editText;
	private Spinner spinner;
	public static LinearLayout linearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editText = (EditText) findViewById(R.id.editText1);
		spinner = (Spinner) findViewById(R.id.spinner1);

		linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

		loadDeviceId();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void loadDeviceId() {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("info");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (objects != null && objects.size() != 0) {
					List<String> ids = new ArrayList<String>();
					for (ParseObject object : objects) {
						String content = object.getString("nickName") + ","
								+ object.getString("deviceId");
						ids.add(content);
					}
					setSpinner(ids);
				}
			}
		});
	}

	public void sendMessage(View view) {
		String content = editText.getText().toString();
		String deviceId = ((String) spinner.getSelectedItem()).split(",")[1];
		String channel = "device_id_" + deviceId;

		JSONObject object = new JSONObject();
		try {
			object.put("action", "com.example.UPDATE_STATUS");
			object.put("alert", content);
			object.put("message", content);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		ParsePush push = new ParsePush();
		push.setData(object);
		push.setChannel(channel);
		push.sendInBackground();
	}

	private void setSpinner(List<String> ids) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, ids);
		spinner.setAdapter(adapter);
	}

}
