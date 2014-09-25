package com.example.takephoto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends ActionBarActivity {

	private static final int TAKE_PHOTO_REQUEST_CODE = 0;
	private static final int OPEN_GALLERY_REQUEST_CODE = 1;

	private ImageView imageView;
	private LinearLayout linearLayout;
	private Uri outputFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Parse.initialize(this, "6GIweBfY6S45aUHHhzAkw4cgo6Cb7PlvUyYYwJFs",
				"nEFIK6PmEiidO3qnyvPa04WCi9rJCECOvN8qg5vf");
		imageView = (ImageView) findViewById(R.id.imageView1);
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);

		loadFromParse();
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
		} else if (id == R.id.action_takephoto) {
			// Toast.makeText(this, "take photo", Toast.LENGTH_SHORT).show();

			outputFile = getOutputFile();

			Intent intent = new Intent();
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFile);
			startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE);

			return true;
		} else if (id == R.id.action_gallery) {

			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent, OPEN_GALLERY_REQUEST_CODE);

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == TAKE_PHOTO_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Bitmap bitmap = data.getParcelableExtra("data");
				// imageView.setImageBitmap(bitmap);
				// saveToParse(bitmap);
				imageView.setImageURI(outputFile);
			}
		} else if (requestCode == OPEN_GALLERY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Uri selectedImageUri = data.getData();
				imageView.setImageURI(selectedImageUri);
				saveToParse(selectedImageUri);

				Log.d("debug", selectedImageUri.toString());
			}
		}
	}

	private void loadFromParse() {

		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Photo");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (objects != null && e == null) {
					for (ParseObject object : objects) {
						ParseFile file = object.getParseFile("file");
						try {
							byte[] data = file.getData();
							Bitmap bitmap = BitmapFactory.decodeByteArray(data,
									0, data.length);
							ImageView imageView = new ImageView(
									MainActivity.this);
							imageView.setImageBitmap(bitmap);
							linearLayout.addView(imageView);

						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});

	}

	private void saveToParse(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 90, baos);
		byte[] bytes = baos.toByteArray();

		final ParseFile file = new ParseFile("photo.png", bytes);
		ParseObject object = new ParseObject("Photo");

		object.put("file", file);
		object.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if (e == null) {
					String url = file.getUrl();
					Log.d("debug", url);
				}
			}
		});
	}

	private void saveToParse(Uri uri) {
		try {
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(
					getContentResolver(), uri);
			saveToParse(bitmap);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Uri getOutputFile() {

		File dcimDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		if (dcimDir.exists() == false) {
			dcimDir.mkdirs();
		}

		File file = new File(dcimDir, "photo.png");
		return Uri.fromFile(file);
	}

}