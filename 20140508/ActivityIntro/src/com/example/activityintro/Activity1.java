package com.example.activityintro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Activity1 extends Activity {
	private static final int RESULT_CODE_DONE1 = 5566;
	private static final int RESULT_CODE_DONE2 = 7788;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity1);
		String message = getIntent().getStringExtra("message");
		Log.d("debug", "onCreate, message=" + message);
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

	public void finish(View view) {

		Intent intent = new Intent();

		switch (view.getId()) {
		case R.id.button1:
			intent.putExtra("whichButton", "button1");
			setResult(RESULT_CODE_DONE1, intent);
			break;
		case R.id.button2:
			intent.putExtra("whichButton", "button2");
			setResult(RESULT_CODE_DONE2, intent);
			break;
		}
		finish();
	}
}
