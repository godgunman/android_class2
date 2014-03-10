package com.example.simpleui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText editText;
	private Button button;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sp = getSharedPreferences("settings", Context.MODE_PRIVATE);
		
		editText = (EditText) findViewById(R.id.editText1);
		editText.setHint("type something ...");
		editText.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				
				Editor editor = sp.edit();
				editor.putString("text", editText.getText().toString());
				editor.commit();
				
				Log.d("debug",
						"keyOcde =" + keyCode + "keyEvent=" + event.getAction());
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER) {
						sendMessage();
						return true;
					}
				}
				return false;
			}
		});

		editText.setText(sp.getString("text", ""));

		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMessage();
			}
		});
	}

	public void submit2(View view) {
		sendMessage();
	}

	private void sendMessage() {
		String text = editText.getText().toString();
		editText.getText().clear();
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();

		Intent intent = new Intent();
		intent.setClass(this, MessageActivity.class);
		intent.putExtra("text", text);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
