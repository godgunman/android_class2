package com.example.simpleui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MessageActivity extends Activity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_message);

		listView = (ListView) findViewById(R.id.listView1);

		String text = getIntent().getStringExtra("text");

		writeFile(text);
		String data = readFile();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, data.split("\n"));
		listView.setAdapter(adapter);

	}

	// file location: data/data/com.example.simpleui/files/histroy.txt
	private void writeFile(String text) {

		text += "\n";

		try {
			FileOutputStream fos = openFileOutput("history.txt",
					Context.MODE_APPEND);
			fos.write(text.getBytes());
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readFile() {
		try {
			FileInputStream fis = openFileInput("history.txt");
			byte[] buffer = new byte[1024];
			fis.read(buffer);
			fis.close();
			return new String(buffer);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
