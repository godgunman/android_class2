package com.example.push2;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.PushService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	private TextView deviceIdContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parseInit();

		setContentView(R.layout.activity_login);
		deviceIdContent = (TextView) findViewById(R.id.deviceIdContent);
		deviceIdContent.setText(getDeviceId());
	}

	private void parseInit() {
		Parse.initialize(this, "7zLbRcU5bAj7PRg85dO8GuiHf7Hp0WJFTgDxk5Kh",
				"l9jXZ7g18KFYrVek6kFWj6si5uGAXl9cBy2t31FI");

		PushService.setDefaultPushCallback(this, MessageActivity.class);
		PushService.subscribe(this, "all", MessageActivity.class);
		PushService.subscribe(this, "device_id_" + getDeviceId(),
				MessageActivity.class);
	}

	public void enterMessageActivity(View view) {
		String nickName = ((EditText) findViewById(R.id.editText1)).getText()
				.toString();
		register(nickName);

		Intent intent = new Intent();
		intent.setClass(this, MessageActivity.class);
		intent.putExtra("nickName", nickName);
		startActivity(intent);
	}

	private void register(Object nickName) {
		ParseObject object = new ParseObject("info");
		object.put("nickName", nickName);
		object.put("deviceId", getDeviceId());
		try {
			object.save();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private String getDeviceId() {
		return Secure.getString(getContentResolver(), Secure.ANDROID_ID);
	}

}
