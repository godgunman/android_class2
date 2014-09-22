package com.example.simpleui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parse.ParseObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MessageActivity extends Activity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_message);

		listView = (ListView) findViewById(R.id.listView1);

		String text = getIntent().getStringExtra("text");
		boolean checked = getIntent().getBooleanExtra("checked", false);

		writeFile(text, checked);
		saveToParse(text, checked);

		String[] rawData = readFile().split("\n");
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();

		for (int i = 0; i < rawData.length - 1; i++) {
			String[] d = rawData[i].split(",");

			Map<String, String> item = new HashMap<String, String>();
			item.put("text", d[0]);
			item.put("checked", d[1]);

			data.add(item);
		}

		String[] from = new String[] { "text", "checked" };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

		SimpleAdapter adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_2, from, to);

		listView.setAdapter(adapter);

	}

	private void saveToParse(String text, boolean checked) {

		ParseObject messageObject = new ParseObject("Message");
		messageObject.put("text", text);
		messageObject.put("checked", checked);
		messageObject.saveInBackground();
	}

	// file location: data/data/com.example.simpleui/files/histroy.txt
	private void writeFile(String text, boolean checked) {

		text += "," + checked + "\n";

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
