package com.example.simpleui;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private EditText editText;
	private Button button;
	private CheckBox checkBox;

	private SharedPreferences sp;
	private Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editText = (EditText) findViewById(R.id.editText1);
		button = (Button) findViewById(R.id.button1);
		checkBox = (CheckBox) findViewById(R.id.checkBox1);

		sp = getSharedPreferences("settings", Context.MODE_PRIVATE);
		editor = sp.edit();

		button.setText("Send");
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("debug", "click from button1");
				sendText();
			}
		});

		editText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				Log.d("debug", "keyCode: " + keyCode);

				String text = editText.getText().toString();
				editor.putString("text", text);
				editor.commit();

				if (keyCode == KeyEvent.KEYCODE_ENTER
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					sendText();
					return true;
				}
				return false;
			}
		});
		editText.setText(sp.getString("text", ""));

	}

	public void button2Click(View view) {
		int id = view.getId();

		if (id == R.id.button2)
			Log.d("debug", "click from button2");
		else if (id == R.id.button3)
			Log.d("debug", "click from button3");

		sendText();
	}

	public void sendText() {
		String text = editText.getText().toString();
		if (checkBox.isChecked())
			text = "********";

		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
		editText.setText("");
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
}
