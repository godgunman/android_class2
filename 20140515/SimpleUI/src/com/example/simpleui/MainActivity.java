package com.example.simpleui;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		Parse.initialize(this, "6GIweBfY6S45aUHHhzAkw4cgo6Cb7PlvUyYYwJFs",
				"nEFIK6PmEiidO3qnyvPa04WCi9rJCECOvN8qg5vf");
		PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		PushService.subscribe(this, "all", MainActivity.class);
		PushService.subscribe(this, "id_" + getDeviceId(), MainActivity.class);

		register();
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

	private String getDeviceId() {
		return Secure.getString(getContentResolver(), Secure.ANDROID_ID);
	}

	private void register() {
		String id = getDeviceId();
		ParseObject object = new ParseObject("DeviceId");
		object.put("deviceId", id);
		object.saveInBackground();
	}

	//
	// public void send(View view) {
	// Log.d("debug", "click");
	// }

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private Button button;
		private EditText editText;
		private CheckBox checkBox;
		private Spinner spinner;
		private SharedPreferences sp;
		private SharedPreferences.Editor editor;

		public PlaceholderFragment() {
		}

		private void send() {
			String text = editText.getText().toString();
			if (checkBox.isChecked()) {
				text = "***********";
			}
			Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
			editText.setText("");

			ParsePush push = new ParsePush();
			push.setChannel("all");
			push.setMessage(text);
			push.sendInBackground();

			Intent intent = new Intent();
			intent.setClass(getActivity(), MessageActivity.class);
			intent.putExtra("text", text);
			intent.putExtra("checkBox", checkBox.isChecked());
			getActivity().startActivity(intent);
		}

		private void loadDeviceId() {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("DeviceId");
			query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					String[] deviceId = new String[objects.size()];
					for (int i = 0; i < objects.size(); i++) {
						deviceId[i] = objects.get(i).getString("deviceId");
					}
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							getActivity(),
							android.R.layout.simple_spinner_item, deviceId);
					spinner.setAdapter(adapter);
				}
			});
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			sp = getActivity().getSharedPreferences("settings",
					Context.MODE_PRIVATE);
			editor = sp.edit();

			checkBox = (CheckBox) rootView.findViewById(R.id.checkBox1);
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					editor.putBoolean("checkBox", isChecked);
					editor.commit();
				}
			});

			button = (Button) rootView.findViewById(R.id.button1);
			editText = (EditText) rootView.findViewById(R.id.editText1);
			editText.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {

					if (event.getAction() == KeyEvent.ACTION_DOWN
							&& keyCode == KeyEvent.KEYCODE_ENTER) {
						send();
						return true;
					} else {
						editor.putString("text", editText.getText().toString());
						editor.commit();
					}

					return false;
				}
			});

			button.setText("Send");
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					send();
				}
			});

			editText.setText(sp.getString("text", ""));
			checkBox.setChecked(sp.getBoolean("checkBox", false));

			spinner = (Spinner) rootView.findViewById(R.id.spinner1);
			loadDeviceId();

			return rootView;
		}

	}

}
