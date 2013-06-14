package com.touchmenotapps.mobicart.fragments;

import com.touchmenotapps.mobicart.adapters.ShopListAdapter;
import com.touchmenotapps.mobicart.model.ShopData;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ShoppingListFragment extends ListFragment {

	private ShopListAdapter mAdapter;
	
	private OnShopListItemClickListener mCallback;
	
	public interface OnShopListItemClickListener {
		public void onShopListItemClick(ShopData data);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAdapter = new ShopListAdapter(getActivity());
		mAdapter.setListData(CategoriesFragment.mShoppingData);
		setListAdapter(mAdapter);
		setEmptyText("No Data");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getListView().setPadding(10, 0, 10, 0);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnShopListItemClickListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		mCallback.onShopListItemClick(mAdapter.getItem(position));
	}
}
