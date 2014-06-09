package com.example.linearlayout;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
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

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// View rootView = inflater.inflate(R.layout.fragment_main,
			// container,
			// false);
			LinearLayout ll = new LinearLayout(getActivity());
			ll.setOrientation(LinearLayout.VERTICAL);
			ll.setPadding(15, 15, 15, 15);

			EditText toEditText = new EditText(getActivity());
			toEditText.setHint("To");

			EditText subjectEditText = new EditText(getActivity());
			subjectEditText.setHint("Subject");

			EditText messageEditText = new EditText(getActivity());
			messageEditText.setHint("Message");
			messageEditText.setGravity(Gravity.TOP);

			Button send = new Button(getActivity());
			send.setText("Send");

			/*
			 * LinearLayout.LayoutParams layoutParams = new
			 * LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
			 * 0); layoutParams.weight = 1;
			 * messageEditText.setLayoutParams(layoutParams);
			 */

			ll.addView(toEditText);
			ll.addView(subjectEditText);
			ll.addView(messageEditText);
			ll.addView(send);

			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) messageEditText
					.getLayoutParams();
			layoutParams.height = 0;
			layoutParams.weight = 1;

			LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) send
					.getLayoutParams();
			layoutParams2.width = LinearLayout.LayoutParams.WRAP_CONTENT;
			layoutParams2.gravity = Gravity.RIGHT;

			return ll;
		}
	}

}