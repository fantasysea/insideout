package com.campus.insideout;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
/**
 * 最普通的actionbar Activity
 * @author sea
 *
 */
public class BaseActivity extends SherlockActivity {
	protected IApp iApp;
	public static int THEME = R.style.Theme_Sherlock;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(THEME);
		iApp = (IApp) getApplication();
		setTitle("周边");
//		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		ColorDrawable color = new ColorDrawable(Color.BLACK);
		color.setAlpha(128);
		getSupportActionBar().setBackgroundDrawable(color);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		this.getWindow().setBackgroundDrawableResource(android.R.color.black);
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
