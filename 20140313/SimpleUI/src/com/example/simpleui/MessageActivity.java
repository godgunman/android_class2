package com.example.simpleui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class MessageActivity extends Activity {

	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		textView = (TextView) findViewById(R.id.message);
		getData();
	}

	private void getData() {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("message");
		try {
			
			String result = "";
			List<ParseObject> messages = query.find();
			for (ParseObject message : messages) {
				String text = message.getString("text");
				boolean isEncrypt = message.getBoolean("isEncrypt");
				result += text + "," + isEncrypt + "\n";
			}
			textView.setText(result);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
