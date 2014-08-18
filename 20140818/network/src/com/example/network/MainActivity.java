package com.example.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class MainActivity extends ActionBarActivity {

	private TextView textView;
	private ProgressDialog progress;
	final static private String CLIENT_ID = "S1LC42PP1ZRDU5VWZIIZBIVOVACP4DXX0R5SVSXBQHJS3UP1";
	final static private String CLIENT_SECRET = "FCB500VP5QGJEH3GYNHRICNMLZMWXLEOOM0FK30ET5RHTIUC";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.textView1);
		progress = new ProgressDialog(this);

		runAsyncTaskWithGoogleAPI("http://maps.googleapis.com/maps/api/geocode/json?address=%E5%8F%B0%E5%8C%97%E5%B8%82%E7%BE%85%E6%96%AF%E7%A6%8F%E8%B7%AF%E5%9B%9B%E6%AE%B5%E4%B8%80%E8%99%9F&sensor=true");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void runAsyncTaskWithGoogleAPI(final String urlStr) {

		AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPreExecute() {
				progress.setTitle("laoding...");
				progress.show();
			}

			@Override
			protected String doInBackground(Void... params) {
				return fetchGoogleAPI(urlStr);
			}

			@Override
			protected void onPostExecute(String result) {
				textView.setText(result);
				progress.dismiss();
				String url = String
						.format("https://api.foursquare.com/v2/venues/search?client_id=%s&client_secret=%s&v=20130815&ll=%s&query=%s",
								CLIENT_ID, CLIENT_SECRET, result, "sushi");
				runAsyncTaskWithFourSquareAPI(url);
			}
		};
		task.execute();
	}

	private void runAsyncTaskWithFourSquareAPI(final String urlStr) {

		AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPreExecute() {
				progress.setTitle("laoding...");
				progress.show();
			}

			@Override
			protected String doInBackground(Void... params) {
				return fetchFourSquareAPI(urlStr);
			}

			@Override
			protected void onPostExecute(String result) {
				textView.setText(result);
				progress.dismiss();
			}
		};
		task.execute();
	}

	private String fetchGoogleAPI(String urlStr) {

		try {
			URL url = new URL(urlStr);
			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream()));

			String content = "", line;
			while ((line = bufferReader.readLine()) != null) {
				content += line;
			}
			JSONObject object = new JSONObject(content);
			JSONObject location = object.getJSONArray("results")
					.getJSONObject(0).getJSONObject("geometry")
					.getJSONObject("location");

			String lat = location.getString("lat");
			String lng = location.getString("lng");

			return lat + "," + lng;

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

	private String fetchFourSquareAPI(String urlStr) {

		try {
			URL url = new URL(urlStr);
			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream()));

			String content = "", line;
			while ((line = bufferReader.readLine()) != null) {
				content += line;
			}
			String result = "";
			JSONObject object = new JSONObject(content);
			JSONArray venues = object.getJSONObject("response").getJSONArray(
					"venues");
			for (int i = 0; i < venues.length(); i++) {
				result += venues.getJSONObject(i).getString("name") + ",";
			}

			return result;

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
