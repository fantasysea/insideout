package com.campus.insideout.adapter;

import java.util.ArrayList;

import com.campus.insideout.IApp;
import com.campus.insideout.R;
import com.campus.insideout.data.NearyData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NearbyAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<NearyData> arrayList;
	private IApp iApp;

	public NearbyAdapter(Context context, ArrayList<NearyData> arrayList, IApp iApp) {
		this.arrayList = arrayList;
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.iApp = iApp;
	}

	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int id) {
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		View_holder holder;
		if (view == null) {
			holder = new View_holder();
			view = mInflater.inflate(R.layout.item_nearby, null);
			holder.avatar = (ImageView) view.findViewById(R.id.avatar);
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.gender = (TextView) view.findViewById(R.id.gender);
			holder.age = (TextView) view.findViewById(R.id.age);
			holder.shool = (TextView) view.findViewById(R.id.school);
			holder.local = (TextView) view.findViewById(R.id.local);
			holder.status = (TextView) view.findViewById(R.id.status);
			view.setTag(holder);
		} else {
			holder = (View_holder) view.getTag();
		}
		return view;
	}
}

class View_holder {
	ImageView avatar;
	TextView name, gender, age, shool, local, status;
}
