package com.example.simpleui;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MessageActivity extends Activity {

	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		String text = getIntent().getStringExtra("text");
		boolean isChecked = getIntent().getBooleanExtra("checkbox", false);

		textView = (TextView) findViewById(R.id.textView1);
		textView.setText(text);

		writeFile(text);
	}

	private void writeFile(String text) {
		try {
			FileOutputStream fos = openFileOutput("history.txt",
					Context.MODE_APPEND);
			text += "\n";
			fos.write(text.getBytes());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
