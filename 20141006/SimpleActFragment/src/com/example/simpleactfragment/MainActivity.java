package com.example.simpleactfragment;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Fragment fragment = getSupportFragmentManager().findFragmentById(
				R.id.container);
		fragment.onActivityResult(requestCode, resultCode, data);

		Log.d("debug", "[in activity] request code:" + requestCode);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private Button button1, button2, button3;

		private OnClickListener onClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();

				if (v.getId() == R.id.button1) {
					intent.setClass(getActivity(), Activity1.class);
					startActivityForResult(intent, 1);
				} else if (v.getId() == R.id.button2) {
					intent.setClass(getActivity(), Activity2.class);
					startActivityForResult(intent, 2);
				} else if (v.getId() == R.id.button3) {
					intent.setClass(getActivity(), Activity3.class);
					startActivityForResult(intent, 3);
				}
			}
		};

		public PlaceholderFragment() {
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			Log.d("debug", "[in fragment] request code:" + requestCode);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			button1 = (Button) rootView.findViewById(R.id.button1);
			button2 = (Button) rootView.findViewById(R.id.button2);
			button3 = (Button) rootView.findViewById(R.id.button3);

			button1.setOnClickListener(onClickListener);
			button2.setOnClickListener(onClickListener);
			button3.setOnClickListener(onClickListener);

			return rootView;
		}
	}
}
