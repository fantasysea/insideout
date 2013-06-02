package com.campus.insideout;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.campus.insideout.fragments.ContactFragment;
import com.campus.insideout.fragments.DialogFragment;
import com.campus.insideout.fragments.NearbyFragment;
import com.campus.insideout.fragments.SettingFragment;

/**
 * 主类
 * 使用了FragmentTabHost +  fragment 组合  
 */
public class MainActivity extends SherlockFragmentActivity {
	private FragmentTabHost mTabHost;
	private RadioGroup radioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		mTabHost.getTabWidget().setVisibility(View.GONE);
		mTabHost.addTab(mTabHost.newTabSpec("dialog").setIndicator("dialog"), DialogFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("nearby").setIndicator("nearby"), NearbyFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("contact").setIndicator("contact"), ContactFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("setting").setIndicator("setting"), SettingFragment.class, null);
		radioGroup = (RadioGroup) findViewById(R.id.menu_group);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.tab_dialog:
					mTabHost.setCurrentTabByTag("dialog");
					break;
				case R.id.tab_nearby:
					mTabHost.setCurrentTabByTag("nearby");
					
					break;
				case R.id.tab_contact:
					mTabHost.setCurrentTabByTag("contact");
					
					break;
				case R.id.tab_setting:
					mTabHost.setCurrentTabByTag("setting");
					
					break;

				default:
					break;
				}

			}
		});
	}
}