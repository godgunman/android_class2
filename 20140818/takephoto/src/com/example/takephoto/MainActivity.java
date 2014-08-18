package com.example.takephoto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class MainActivity extends ActionBarActivity {

	private static final int REQUEST_CODE_TAKE_PHOTO = 100;
	private ImageView imageView;
	private Uri outputFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "6GIweBfY6S45aUHHhzAkw4cgo6Cb7PlvUyYYwJFs",
				"nEFIK6PmEiidO3qnyvPa04WCi9rJCECOvN8qg5vf");

		setContentView(R.layout.activity_main);

		imageView = (ImageView) findViewById(R.id.imageView1);
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
		} else if (id == R.id.action_take_photo) {

			outputFile = Uri.fromFile(getTargetFile());

			Intent intent = new Intent();
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFile);
			startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
			Log.d("debug", "camera back");
			if (resultCode == RESULT_OK) {
				// Bitmap bitmap = data.getParcelableExtra("data");
				// imageView.setImageBitmap(bitmap);
				// saveToParse(bitmap);
				imageView.setImageURI(outputFile);

				File file = getTargetFile();
				saveToParse(file);
				Log.d("debug", file.getPath());
			}
		}

		super.onActivityResult(requestCode, requestCode, data);
	}

	private File getTargetFile() {
		File dcimDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		if (dcimDir.exists() == false) {
			dcimDir.mkdirs();
		}
		return new File(dcimDir, "photo.png");
	}

	private void saveToParse(File file) {
		byte[] data = new byte[(int) file.length()];

		try {
			FileInputStream fis = new FileInputStream(file);

			int offset = 0;
			int numRead = 0;

			while (true) {
				numRead = fis.read(data, offset, data.length - offset);
				if (numRead == -1 || numRead == 0) {
					break;
				}
				offset += numRead;
			}
			fis.close();

			final ParseFile parseFile = new ParseFile("photo.png", data);
			ParseObject parseObject = new ParseObject("file");
			parseObject.put("file", parseFile);
			parseObject.saveInBackground();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void saveToParse(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos);
		byte[] bytes = baos.toByteArray();

		final ParseFile file = new ParseFile("photo.png", bytes);
		file.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				String url = file.getUrl();
				Log.d("debug", url);
			}
		});

	}
}
