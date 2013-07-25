package com.touchmenotapps.mobicart.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.touchmenotapps.mobicart.R;
import com.touchmenotapps.mobicart.model.ShopData;

public class ShopListAdapter extends BaseAdapter {
	
	private ArrayList<ShopData> mListData = new ArrayList<ShopData>();
	private LayoutInflater mInflator;
	
	public ShopListAdapter(Context context) {
		mInflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setListData( ArrayList<ShopData> data) {
		mListData = data;
		notifyDataSetChanged();
	}
		
	@Override
	public int getCount() {
		return mListData.size();
	}

	@Override
	public ShopData getItem(int pos) {
		return mListData.get(pos);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflator.inflate(R.layout.adapter_shop_list, null);
			holder = new ViewHolder();
			holder.mTitleText = (TextView) convertView.findViewById(R.id.shop_list_title_text);
			holder.mDescriptionText = (TextView) convertView.findViewById(R.id.shop_list_description_text);
			holder.mPriceText = (TextView) convertView.findViewById(R.id.shop_list_price_text);
			convertView.setTag(holder);
		} else 
			holder = (ViewHolder) convertView.getTag();
		
		holder.mTitleText.setText(mListData.get(position).getTitle());
		holder.mDescriptionText.setText(mListData.get(position).getVendor());
		holder.mPriceText.setText(String.valueOf(mListData.get(position).getPrice()) + " " + mListData.get(position).getPriceCurrency());
		return convertView;
	}
	
	static class ViewHolder {
		TextView mTitleText, mDescriptionText, mPriceText;
	}
}
