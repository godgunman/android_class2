package com.example.simpleui;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyCustomReceiver extends BroadcastReceiver {
	private static final String TAG = "MyCustomReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			String action = intent.getAction();
			String channel = intent.getStringExtra("com.parse.Channel");
			JSONObject json = new JSONObject(
					intent.getStringExtra("com.parse.Data"));

			Log.d(TAG, "got action " + action + " on channel " + channel
					+ " with:");
			Iterator itr = json.keys();
			String message = "";
			while (itr.hasNext()) {
				String key = (String) itr.next();
				Log.d(TAG, "..." + key + " => " + json.getString(key));
				if (key.equals("text")) {
					message += " text:" + json.getString(key);
				}
				if (key.equals("from")) {
					message += " from:" + json.getString(key);
				}
			}

			MainActivity.textView2.setText(MainActivity.textView2.getText().toString()
					+ "\n" + message);

		} catch (JSONException e) {
			Log.d(TAG, "JSONException: " + e.getMessage());
		}
	}
}