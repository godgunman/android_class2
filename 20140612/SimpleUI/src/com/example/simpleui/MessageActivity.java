package com.example.simpleui;

import android.app.Activity;
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
		
	}
}
