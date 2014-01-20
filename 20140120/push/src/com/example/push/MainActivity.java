package com.example.push;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;

public class MainActivity extends Activity {

	private EditText editText;
	private Button button;
	private TextView textView;
	private Spinner spinner;

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
		spinner = (Spinner) findViewById(R.id.spinner1);

		textView = (TextView) findViewById(R.id.textView1);
		textView.setText(getDeviceId());

		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = editText.getText().toString();
				String channel = "device_id_"
						+ (String) spinner.getSelectedItem();

				JSONObject object = new JSONObject();
				try {
					object.put("action", "com.example.UPDATE_STATUS");
					object.put("mykey", content);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				ParsePush push = new ParsePush();
//				push.setMessage(content);
				push.setData(object);
				push.setChannel(channel);
				push.sendInBackground();
			}
		});

		ParseObject object = new ParseObject("info");
		object.put("device_id", getDeviceId());
		object.saveInBackground();

		loadDeviceId();
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

	private void loadDeviceId() {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("info");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {

				String[] ids = new String[objects.size()];
				for (int i = 0; i < objects.size(); i++) {
					ids[i] = objects.get(i).getString("device_id");
				}
				setSpinner(ids);
			}
		});
	}

	private void setSpinner(String[] ids) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, ids);
		spinner.setAdapter(adapter);
	}

}
