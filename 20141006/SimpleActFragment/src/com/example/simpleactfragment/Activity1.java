package com.example.simpleactfragment;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Activity1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Toast.makeText(this, "Activity1", Toast.LENGTH_SHORT).show();
		setResult(RESULT_OK);
	}
}
