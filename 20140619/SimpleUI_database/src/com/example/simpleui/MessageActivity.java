package com.example.simpleui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MessageActivity extends Activity {

	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		String text = getIntent().getStringExtra("text");
		boolean isChecked = getIntent().getBooleanExtra("checkbox", false);

		MessageDBHelper messageDBHelper = new MessageDBHelper(this);
		messageDBHelper.insert(new Message(text, isChecked));

		List<Message> messages = messageDBHelper.getMessages();
		String result = "";
		for (Message message : messages) {
			result += message.text + "," + message.isEncrypt + "\n";
		}

		textView = (TextView) findViewById(R.id.textView1);
		textView.setText(result);
	}
}
