package com.example.push;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.TextureView;
import android.widget.TextView;

public class MyCustomReceiver extends BroadcastReceiver {
	private static final String TAG = "MyCustomReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("debug", "in MyCustomReceiver");
		try {
			String action = intent.getAction();
			String channel = intent.getExtras().getString("com.parse.Channel");
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
					MainActivity.linearlayout.addView(textView);
				}
			}
		} catch (JSONException e) {
			Log.d(TAG, "JSONException: " + e.getMessage());
		}
	}
}