package com.campus.insideout;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.campus.insideout.fragments.ContactFragment;
import com.campus.insideout.fragments.DialogFragment;
import com.campus.insideout.fragments.NearbyFragment;
import com.campus.insideout.fragments.SettingFragment;

/**
 * This demonstrates how you can implement switching between the tabs of a
 * TabHost through fragments, using FragmentTabHost.
 */
public class MainActivity extends FragmentActivity {
	private FragmentTabHost mTabHost;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;// 单个水平动画位移
	private int two;
	private int three;
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
		Display currDisplay = getWindowManager().getDefaultDisplay();//获取屏幕当前分辨率
		int displayWidth = currDisplay.getWidth();
        int displayHeight = currDisplay.getHeight();
        one = displayWidth/4; //设置水平动画平移大小
        two = one*2;
        three = one*3;
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