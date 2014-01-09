package com.example.takephoto;

import java.io.ByteArrayOutputStream;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private static final int REQUESTCODE_TAKE_PHOTO = 0;

	private ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Parse.initialize(this, "hCJ3YM593qsoFGt3CNVa0XRECus3Vbrz56HdyUvD",
				"WRUIsQkcyj0fgv8inoF6hSeo0rftbr2WTKPWLE09");
		ParseAnalytics.trackAppOpened(getIntent());

		setContentView(R.layout.activity_main);

		image = (ImageView) findViewById(R.id.imageView1);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		case R.id.action_take_photo:
			Intent intent = new Intent();
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, REQUESTCODE_TAKE_PHOTO);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUESTCODE_TAKE_PHOTO) {
			if (resultCode == RESULT_OK) {
				Bitmap bitmap = data.getParcelableExtra("data");
				image.setImageBitmap(bitmap);
				save(bitmap);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void save(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos);
		byte[] bytes = baos.toByteArray();

		ParseFile file = new ParseFile("photo.png", bytes);
		try {
			file.save();
			Log.d("debug", file.getUrl());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
