package com.example.simpleui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MessageActivity extends Activity {

	private static final String FILE_NAME = "text.txt";
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		textView = (TextView) findViewById(R.id.textView1);

		String text = getIntent().getStringExtra("text");
		writeFile(text);
		writeFile2(text);
		textView.setText(readFile());
	}

	private void writeFile(String text) {
		try {
			text += "\n";

			FileOutputStream fos = openFileOutput(FILE_NAME,
					Context.MODE_APPEND);
			fos.write(text.getBytes());
			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeFile2(String text) {

		File docDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
		if (docDir.exists() == false) {
			docDir.mkdirs();
		}
		
		File file = new File(docDir, FILE_NAME);

		try {
			text += "\n";

			FileOutputStream fos = new FileOutputStream(file);
			fos.write(text.getBytes());
			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readFile() {

		try {
			FileInputStream fis = openFileInput(FILE_NAME);
			byte[] buffer = new byte[1024];
			fis.read(buffer);
			fis.close();

			return new String(buffer);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
