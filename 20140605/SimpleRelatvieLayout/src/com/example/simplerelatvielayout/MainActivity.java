package com.example.simplerelatvielayout;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.os.Build;

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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private Spinner spinner;
		private Spinner spinner2;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			spinner = (Spinner) rootView.findViewById(R.id.spinner1);
			spinner2 = (Spinner) rootView.findViewById(R.id.spinner2);
			// String[] months = new String[] { "Jan", "Feb" };

			String[] months = getResources().getStringArray(R.array.months);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_spinner_item, months);
			spinner.setAdapter(adapter);
			spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> adapter, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					int[] dayLimit = getResources().getIntArray(R.array.days);
					setDays(dayLimit[position]);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				}
			});

			return rootView;
		}

		private void setDays(int limit) {
			String[] days = new String[limit];
			for (int i = 0; i < days.length; i++) {
				days[i] = String.valueOf(i + 1);
			}

			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_spinner_item, days);
			spinner2.setAdapter(adapter2);
		}
	}

}
