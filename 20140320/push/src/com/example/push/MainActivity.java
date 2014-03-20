package com.example.push;

import com.parse.Parse;
import com.parse.PushService;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Parse.initialize(this, "TBj7tIT2KdaoddftugUeL9hKgIB925grhDKrWvCM",
				"oYOs2OPOZidrK5RLUwH6XhU6JGvwAK9v5ybMkc4Q");
		PushService.setDefaultPushCallback(this, MainActivity.class);

		editText = (EditText) findViewById(R.id.editText1);
	}

	public void click(View view) {
		String text = editText.getText().toString();
		Log.d("debug", text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
