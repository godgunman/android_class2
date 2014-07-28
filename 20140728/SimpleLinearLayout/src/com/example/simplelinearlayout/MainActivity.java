package com.example.simplelinearlayout;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


public class MainActivity extends ActionBarActivity {

	private LinearLayout linearLayout;
	private Button button;
	private EditText editText1, editText2, editText3;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        
        linearLayout = new LinearLayout(this);
        button = new Button(this);
        editText1 = new EditText(this);
        editText2 = new EditText(this);
        editText3 = new EditText(this);
    
        linearLayout.addView(editText1);
        linearLayout.addView(editText2);
        linearLayout.addView(editText3);
        linearLayout.addView(button);

        setContentView(linearLayout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
