package com.example.connectnetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static String SAMPLE_URL = "http://tw.yahoo.com/";
	private TextView textView;
	private ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.textView1);
		progress = new ProgressDialog(this);
		
		/*
		 * StrictMode.ThreadPolicy policy = new
		 * StrictMode.ThreadPolicy.Builder() .permitAll().build();
		 * StrictMode.setThreadPolicy(policy);
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View view) {
		String content = null;
		if (view.getId() == R.id.button1) {
			fetchMethod1(SAMPLE_URL);
		} else if (view.getId() == R.id.button2) {
			content = fetchMethod2();
		}
		textView.setText(content);
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

	private void fetchMethod1(String url) {
		AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
			@Override
			protected void onPreExecute() {
				progress.show();
			}
			
			@Override
			protected String doInBackground(String... params) {
				try {
					URL url = new URL(params[0]);
					URLConnection urlConnection = url.openConnection();
					return readStreamToString(urlConnection.getInputStream());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				textView.setText(result);
				progress.dismiss();
			}
		};
		task.execute(url);

	}

	private String fetchMethod2() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet target = new HttpGet(SAMPLE_URL);
		try {
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String content = httpClient.execute(target, responseHandler);
			return content;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
