package com.touchmenotapps.mobicart.adapters;

import java.util.ArrayList;

import com.touchmenotapps.mobicart.R;
import com.touchmenotapps.mobicart.model.CategoryData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoriesListAdapter extends BaseAdapter {
	
	private LayoutInflater mInflator;
	private ArrayList<CategoryData> mData = new ArrayList<CategoryData>();
	
	public CategoriesListAdapter(Context context) {
		mInflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setListData(ArrayList<CategoryData> data) {
		mData = data;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public CategoryData getItem(int pos) {
		return mData.get(pos);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflator.inflate(R.layout.adapter_categories_list, null);
			holder = new ViewHolder();
			holder.mCategoryText = (TextView) convertView.findViewById(R.id.adapter_categories_title_text);
			convertView.setTag(holder);
		} else 
			holder = (ViewHolder) convertView.getTag();
		
		holder.mCategoryText.setText(mData.get(position).getCategory());
		return convertView;
	}
	
	static class ViewHolder {
		TextView mCategoryText;
	}
}
