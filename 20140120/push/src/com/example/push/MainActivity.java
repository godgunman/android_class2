package com.example.push;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.PushService;

public class MainActivity extends Activity {

	private EditText editText;
	private Button button;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Parse.initialize(this, "5ess4RyqXAhsTcMD9nYrLIbiUtcTn7ZzUoH1zqPV",
				"mtEDB3PHhq7pRs6aGgEZoeQC900McDYBZxQ8UVd1");

		PushService.setDefaultPushCallback(this, MainActivity.class);
		PushService.subscribe(this, "all", MainActivity.class);
		PushService.subscribe(this, "device_id_" + getDeviceId(),
				MainActivity.class);

		editText = (EditText) findViewById(R.id.editText1);
		textView = (TextView) findViewById(R.id.textView1);
		textView.setText(getDeviceId());

		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = editText.getText().toString();
				ParsePush push = new ParsePush();
				push.setMessage(content);
				push.setChannel("all");
				push.sendInBackground();
			}
		});

		ParseObject object = new ParseObject("info");
		object.put("device_id", getDeviceId());
		object.saveInBackground();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private String getDeviceId() {
		return Secure.getString(getContentResolver(), Secure.ANDROID_ID);
	}

}
