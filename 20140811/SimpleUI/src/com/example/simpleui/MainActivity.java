package com.example.simpleui;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	private EditText editText;
	private Button button, button3, button4;
	private CheckBox checkBox;
	private TextView textView;
	private Spinner spinner;

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			send();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Parse.initialize(this, "6GIweBfY6S45aUHHhzAkw4cgo6Cb7PlvUyYYwJFs",
				"nEFIK6PmEiidO3qnyvPa04WCi9rJCECOvN8qg5vf");
		PushService.setDefaultPushCallback(this, MainActivity.class);
		PushService.subscribe(this, "all", MainActivity.class);
		PushService.subscribe(this, "id_" + getDeviceId(), MainActivity.class);

		editText = (EditText) findViewById(R.id.editText1);
		button = (Button) findViewById(R.id.button1);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		checkBox = (CheckBox) findViewById(R.id.checkBox1);
		textView = (TextView) findViewById(R.id.textView1);
		spinner = (Spinner) findViewById(R.id.spinner1);

		textView.setText(getDeviceId());

		button.setText("submit");
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				send();
			}
		});

		button3.setOnClickListener(onClickListener);
		button4.setOnClickListener(this);

		editText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (keyCode == KeyEvent.KEYCODE_ENTER
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					send();
					return true;
				}

				return false;
			}
		});

		uploadDeviceId();
		loadDeviceId();
	}

	public void click(View view) {
		send();
	}

	private void loadDeviceId() {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("DeviceId");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				// TODO Auto-generated method stub
				List<String> ids = new ArrayList<String>();
				for (ParseObject object : objects) {
					ids.add(object.getString("device_id"));
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						MainActivity.this,
						android.R.layout.simple_spinner_item, ids);
				spinner.setAdapter(adapter);
			}
		});

	}

	private void uploadDeviceId() {
		ParseObject object = new ParseObject("DeviceId");
		object.put("device_id", getDeviceId());
		object.saveInBackground();
	}

	private String getDeviceId() {
		return Secure.getString(getContentResolver(), Secure.ANDROID_ID);
	}

	private void send() {
		String text = editText.getText().toString();
		if (checkBox.isChecked()) {
			text = "**********";
		}

		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
		editText.setText("");

		String channel = (String) spinner.getSelectedItem();

		ParsePush push = new ParsePush();
		push.setChannel("id_" + channel);
		push.setMessage(text);
		push.sendInBackground();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

	@Override
	public void onClick(View v) {
		send();
	}
}
