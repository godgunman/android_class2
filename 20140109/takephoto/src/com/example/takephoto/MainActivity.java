package com.example.takephoto;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private static final int REQUESTCODE_TAKE_PHOTO = 0;

	private ImageView image;
	private LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Parse.initialize(this, "hCJ3YM593qsoFGt3CNVa0XRECus3Vbrz56HdyUvD",
				"WRUIsQkcyj0fgv8inoF6hSeo0rftbr2WTKPWLE09");
		ParseAnalytics.trackAppOpened(getIntent());

		setContentView(R.layout.activity_main);

		image = (ImageView) findViewById(R.id.imageView1);
		ll = (LinearLayout) findViewById(R.id.ll);
		
		getPhotos();
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
		ParseObject object = new ParseObject("Photo");
		object.put("file", file);
		object.saveInBackground();

	}

	private void addImage(Bitmap bitmap) {
		ImageView image = new ImageView(this);
		image.setImageBitmap(bitmap);
		ll.addView(image);
	}

	private void getPhotos() {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Photo");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				for (ParseObject obj : objects) {
					ParseFile file = obj.getParseFile("file");
					try {
						byte[] bytes = file.getData();
						Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,
								bytes.length);
						addImage(bitmap);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
}
