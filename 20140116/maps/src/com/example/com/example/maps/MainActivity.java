package com.example.com.example.maps;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {

	private MapFragment mapFragment;
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mapFragment = (MapFragment) getFragmentManager().findFragmentById(
				R.id.map);

		// 25.050302,121.535297

		map = mapFragment.getMap();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(25.050302,
				121.535297)), 15));
		map.addMarker(new MarkerOptions().position(new LatLng(25.050302,
				121.535297)));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
