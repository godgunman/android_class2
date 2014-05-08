package com.example.activityintro;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Activity1 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("debug", "onCreate");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("debug", "onResume");
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("debug", "onPause");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("debug", "onDestroy");
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("debug", "onStart");
	}
}
