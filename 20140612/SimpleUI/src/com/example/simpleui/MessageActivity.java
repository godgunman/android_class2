package com.example.simpleui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MessageActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		String text = getIntent().getStringExtra("text");
		boolean isChecked = getIntent().getBooleanExtra("checkbox", false);
		
		Log.d("debug", text + "," + isChecked);
	}
}
