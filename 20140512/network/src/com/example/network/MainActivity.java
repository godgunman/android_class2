package com.example.network;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		// StrictMode.ThreadPolicy policy = new
		// StrictMode.ThreadPolicy.Builder()
		// .permitAll().build();
		// StrictMode.setThreadPolicy(policy);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private EditText editText;
		private Button button;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			editText = (EditText) rootView.findViewById(R.id.editText1);
			button = (Button) rootView.findViewById(R.id.button1);

			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
						@Override
						protected String doInBackground(Void... params) {
							return fetch(editText.getText().toString());
						}

						@Override
						protected void onPostExecute(String result) {
							Toast.makeText(getActivity(), result,
									Toast.LENGTH_SHORT).show();
						}
					};
					task.execute();

				}
			});

			return rootView;
		}

		private String fetch(String address) {

			String urlString = "http://maps.googleapis.com/maps/api/geocode/json?address="
					+ address + "&sensor=false";
			try {
				URL url = new URL(urlString);
				URLConnection urlConnection = url.openConnection();

				InputStream is = urlConnection.getInputStream();
				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(is));

				String result = "", line;
				while ((line = buffer.readLine()) != null) {
					result += line;
				}

				JSONObject object = new JSONObject(result);
				JSONArray results = object.getJSONArray("results");
				String addressResult = "";
				for (int i = 0; i < results.length(); i++) {
					String formattedAddress = results.getJSONObject(i)
							.getString("formatted_address");
					addressResult += formattedAddress + ",";
				}
				return addressResult;

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		private String fetch2() {

			String urlString = "http://www.ntu.edu.tw/";
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet get = new HttpGet(urlString);

			try {
				ResponseHandler<String> reponseHandler = new BasicResponseHandler();
				String result = httpClient.execute(get, reponseHandler);
				return result;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;

		}
	}

}
