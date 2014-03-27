package com.example.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	protected static final String CLIENT_ID = "S1LC42PP1ZRDU5VWZIIZBIVOVACP4DXX0R5SVSXBQHJS3UP1";
	protected static final String CLIENT_SECRET = "FCB500VP5QGJEH3GYNHRICNMLZMWXLEOOM0FK30ET5RHTIUC";
	private TextView textView;
	private TextView latTextView, lngTextView;
	private EditText editText;
	private ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.textView1);
		editText = (EditText) findViewById(R.id.editText1);
		progress = new ProgressDialog(this);

		latTextView = (TextView) findViewById(R.id.textView2);
		lngTextView = (TextView) findViewById(R.id.textView3);

		// StrictMode.ThreadPolicy policy = new
		// StrictMode.ThreadPolicy.Builder()
		// .permitAll().build();
		// StrictMode.setThreadPolicy(policy);
	}

	public void fetch(View view) {
		String address = "";
		try {
			address = URLEncoder.encode(editText.getText().toString(), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		final String urlStr = String
				.format("http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=false",
						address);

		AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPreExecute() {
				progress.setTitle("Loading");
				progress.show();
			}

			@Override
			protected String doInBackground(Void... params) {
				try {
					URL url = new URL(urlStr);
					URLConnection connection = url.openConnection();

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(connection.getInputStream()));

					StringBuilder stringBuilder = new StringBuilder();
					String line;
					while ((line = buffer.readLine()) != null) {
						stringBuilder.append(line);
					}

					String result = stringBuilder.toString();

					JSONObject data = new JSONObject(result);
					JSONObject location = data.getJSONArray("results")
							.getJSONObject(0).getJSONObject("geometry")
							.getJSONObject("location");
					double lat = location.getDouble("lat");
					double lng = location.getDouble("lng");

					String urlstr2 = String
							.format("https://api.foursquare.com/v2/venues/search?client_id=%s&client_secret=%s&v=20130815&ll=%s&query=%s",
									CLIENT_ID, CLIENT_SECRET, lat + "," + lng,
									"sushi");

					url = new URL(urlstr2);
					connection = url.openConnection();

					buffer = new BufferedReader(new InputStreamReader(
							connection.getInputStream()));

					stringBuilder = new StringBuilder();
					while ((line = buffer.readLine()) != null) {
						stringBuilder.append(line);
					}

					String result2 = stringBuilder.toString();

					return result2;

					// latTextView.setText("lat:" + lat);
					// lngTextView.setText("lng:" + lng);

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {

				try {
					JSONObject object = new JSONObject(result);
					JSONArray array = object.getJSONObject("response")
							.getJSONArray("venues");
					String names = "";
					for (int i = 0; i < array.length(); i++) {
						String name = array.getJSONObject(i).getString("name");
						names += name + "\n";
					}
					textView.setText(names);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				progress.dismiss();
			}
		};

		task.execute();
	}

	public void fetch2(View view) {
		final String urlStr = "http://tw.yahoo.com/";

		AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPreExecute() {
			}

			@Override
			protected String doInBackground(Void... params) {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet get = new HttpGet(urlStr);

				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				try {
					return httpClient.execute(get, responseHandler);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return "";
			}

			@Override
			protected void onPostExecute(String result) {
				textView.setText(result);
			}
		};

		task.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
