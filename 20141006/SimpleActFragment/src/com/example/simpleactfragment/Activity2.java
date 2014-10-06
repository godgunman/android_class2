package com.example.simpleactfragment;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Activity2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Toast.makeText(this, "Activity2", Toast.LENGTH_SHORT).show();
		setResult(RESULT_OK);
	}
}
