package com.example.connectnetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	private static String SAMPLE_URL = "http://tw.yahoo.com/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private String readStreamToString(InputStream is) {
		BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
		try {
			String line;
			String all = "";
			while ((line = buffer.readLine()) != null) {
				all += line;
			}
			return all;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String fetchMethod1() {
		try {
			URL url = new URL(SAMPLE_URL);
			URLConnection urlConnection = url.openConnection();
			return readStreamToString(urlConnection.getInputStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String fetchMethod2() {

		return null;
	}

}
