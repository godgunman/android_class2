package com.example.simpleui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class MessageActivity extends ActionBarActivity {

	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		textView = (TextView) findViewById(R.id.textView1);
		
		String text = getIntent().getStringExtra("text");
		textView.setText(text);
	}
}
