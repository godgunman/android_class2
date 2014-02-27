package com.example.simpleui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText editText; 
	private Button button;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        editText = (EditText) findViewById(R.id.editText1);
        editText.setHint("type something ...");
        
        button = (Button) findViewById(R.id.button1);
        button.setText("send");
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String text = editText.getText().toString();
				Log.d("debug", "[button1]" + text);
				editText.getText().clear();
			}
		});//alt + /
    }

    public void submit2(View view) {
		String text = editText.getText().toString();
		Log.d("debug", "[button2]" + text);
		editText.getText().clear();    	    
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
