package com.example.push;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;

import android.support.v7.app.ActionBarActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private EditText editText;
	private Spinner spinner;
	private LinearLayout linearlayout;
	private MyCustomReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "6GIweBfY6S45aUHHhzAkw4cgo6Cb7PlvUyYYwJFs",
				"nEFIK6PmEiidO3qnyvPa04WCi9rJCECOvN8qg5vf");
		PushService.setDefaultPushCallback(this, MainActivity.class);
		PushService.subscribe(this, "all", MainActivity.class);
		PushService.subscribe(this, "device_" + getDeviceId(),
				MainActivity.class);

		setContentView(R.layout.activity_main);

		editText = (EditText) findViewById(R.id.editText1);
		spinner = (Spinner) findViewById(R.id.spinner1);
		linearlayout = (LinearLayout) findViewById(R.id.linearlayout);

		saveDeviceId();
		getDeviceIds();

		receiver = new MyCustomReceiver();

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.example.UPDATE_STATUS");
		registerReceiver(receiver, intentFilter);
	}

	private void saveDeviceId() {
		ParseObject object = new ParseObject("DeviceId");
		object.put("deviceId", getDeviceId());
		object.saveInBackground();
	}

	private void getDeviceIds() {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("DeviceId");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				List<String> ids = new ArrayList<String>();
				for (ParseObject obj : objects) {
					if (obj.has("deviceId")
							&& obj.getString("deviceId") != null) {
						ids.add(obj.getString("deviceId"));
					}
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						MainActivity.this,
						android.R.layout.simple_spinner_item, ids);
				spinner.setAdapter(adapter);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void send(View view) {

		String text = editText.getText().toString();
		String channel = "device_" + (String) spinner.getSelectedItem();

		JSONObject data = new JSONObject();
		try {
			data.put("action", "com.example.UPDATE_STATUS");
			data.put("text", text);
			data.put("newItem", "newItem");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ParsePush push = new ParsePush();
		push.setChannel(channel);
		push.setData(data);
		// push.setMessage(text);
		push.sendInBackground();
	}

	public void send2(View view) {
		Intent intent = new Intent();
		intent.setAction("com.example.UPDATE_STATUS");
		sendBroadcast(intent);
	}

	private String getDeviceId() {
		return Secure.getString(getContentResolver(), Secure.ANDROID_ID);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class MyCustomReceiver extends BroadcastReceiver {
		private static final String TAG = "MyCustomReceiver";

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("debug", "in MyCustomReceiver");
			try {
				String action = intent.getAction();
				String channel = intent.getExtras().getString(
						"com.parse.Channel");
				JSONObject json = new JSONObject(intent.getExtras().getString(
						"com.parse.Data"));

				Log.d(TAG, "got action " + action + " on channel " + channel
						+ " with:");
				Iterator itr = json.keys();
				while (itr.hasNext()) {
					String key = (String) itr.next();
					Log.d(TAG, "..." + key + " => " + json.getString(key));
					if ("text".equals(key)) {
						String text = json.getString(key);
						TextView textView = new TextView(context);
						textView.setText(text);
						linearlayout.addView(textView);
					}
				}
			} catch (JSONException e) {
				Log.d(TAG, "JSONException: " + e.getMessage());
			}
		}
	}

}
