package com.campus.insideout.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.campus.insideout.BaseActivity;
import com.campus.insideout.R;
import com.campus.insideout.adapter.NearbyAdapter;

public class NearbyFragment extends SherlockFragment implements OnQueryTextListener {
	private SearchView searchView;
	private ViewPager mViewPager;
	private PagerAdapter mFragmentPagerAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.nearby, container, false);
		this.mViewPager = (ViewPager) v.findViewById(R.id.pager);
		mFragmentPagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager());
		mViewPager.setAdapter(mFragmentPagerAdapter);
		mViewPager.setCurrentItem(0);
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater menuinflater) {
		// Used to put dark icons on light action bar
		boolean isLight = BaseActivity.THEME == R.style.Theme_Sherlock_Light;

		searchView = new SearchView(getActivity());
		searchView.setQueryHint(getResources().getText(R.string.search_hint));
		searchView.setSubmitButtonEnabled(true);
		searchView.setOnQueryTextListener(this);

		menu.add("Search").setIcon(isLight ? R.drawable.ic_search_inverse : R.drawable.abs__ic_search).setActionView(searchView)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		menu.add("reflesh").setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		super.onCreateOptionsMenu(menu, menuinflater);

	}

	public static class PagerAdapter extends FragmentStatePagerAdapter {
		Fragment[] mFragments = new Fragment[2];
		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			if(i==0){
				if(mFragments[0]!=null){
					return mFragments[0];
				}else{
					Fragment fragment = new FriendsFragment();
					mFragments[0] = fragment;
					return fragment;
				}
			}else{
				if(mFragments[1]!=null){
					return mFragments[1];
				}else{
					Fragment fragment = new StrangerFragment();
					mFragments[1] = fragment;
					return fragment;
				}
			}
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "OBJECT " + (position + 1);
		}
	}

	public static class FriendsFragment extends SherlockFragment {
		ListView listView;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.listview, container, false);
			this.listView = (ListView) rootView.findViewById(R.id.listView);
			NearbyAdapter adapter = new NearbyAdapter(getActivity(), null, null);
			listView.setAdapter(adapter);
			return rootView;
		}
	}

	public static class StrangerFragment extends SherlockFragment {

		ListView listView;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.listview, container, false);
			this.listView = (ListView) rootView.findViewById(R.id.listView);
			NearbyAdapter adapter = new NearbyAdapter(getActivity(), null, null);
			listView.setAdapter(adapter);
			return rootView;
		}
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}
}