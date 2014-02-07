package com.ourcodeguys.actionbarexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.actionbarexample.R;

public class MainActivity extends ActionBarActivity {
	
	public static final String EXTRA_MESSAGE = "com.ourcodeguys.actionbarexample.MESSAGE";
	
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_search:
			openSearch();
			return true;
		case R.id.action_settings:
			openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void openSettings() {
		Context context = getApplicationContext();
		CharSequence text = "com.ourcodeguys.actionbarexample.MainActivity.openSetting()";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		// start SettingsActivity
		intent = new Intent(this, SettingsActivity.class);
		intent.putExtra(EXTRA_MESSAGE, "Call SettingsActivity");
		startActivity(intent);
	}

	private void openSearch() {
		Context context = getApplicationContext();
		CharSequence text = "com.ourcodeguys.actionbarexample.MainActivity.openSearch()";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

}
