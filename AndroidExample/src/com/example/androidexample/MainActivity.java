package com.example.androidexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Reference {@link url http://developer.android.com/training/basics/firstapp/index.html}
 * @author khoahv and google :P
 *
 */
public class MainActivity extends Activity {

	/**
	 * It's generally a good practice to define keys for intent extras using
	 * your app's package name as a prefix. This ensures they are unique, in
	 * case your app interacts with other apps.
	 */
	public static final String EXTRA_MESSAGE = "com.example.androidexample.MESSAGE";

	private LinearLayout linearLayout;
	private TextView textView;
	private EditText editText;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
		textView = (TextView) findViewById(R.id.textView);
		editText = (EditText) findViewById(R.id.editText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** Called when the user clicks the Send button */
	public void sendMessage(View view) {
		// Tip: In Eclipse, press Ctrl + Shift + O to import missing classes
		// quickly.
		// Do something in response to button
		intent = new Intent(this, DisplayMessageActivity.class);
		String msg = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, msg);
		startActivity(intent);
	}

}
