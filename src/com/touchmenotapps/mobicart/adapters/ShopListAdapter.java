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
		if (convertView == null) {
			convertView = mInflator.inflate(R.layout.adapter_shop_list, null);
			TextView mTitleText = (TextView) convertView.findViewById(R.id.shop_list_title_text);
			TextView mDescriptionText = (TextView) convertView.findViewById(R.id.shop_list_description_text);
			TextView mPriceText = (TextView) convertView.findViewById(R.id.shop_list_price_text);
			mTitleText.setText(mListData.get(position).getTitle());
			mDescriptionText.setText(mListData.get(position).getVendor());
			mPriceText.setText(String.valueOf(mListData.get(position).getPrice()) + " " + mListData.get(position).getPriceCurrency());
		}
		return convertView;
	}
}
