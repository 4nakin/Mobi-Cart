package com.touchmenotapps.mobicart.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.touchmenotapps.mobicart.R;
import com.touchmenotapps.mobicart.adapters.CategoriesListAdapter;
import com.touchmenotapps.mobicart.interfaces.OnCategorySelectedListener;
import com.touchmenotapps.mobicart.model.CategoryData;

public class MyAccountCategoriesFragment extends ListFragment {
	
	private CategoriesListAdapter mAdapter;
	private OnCategorySelectedListener mCallback;
		
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAdapter = new CategoriesListAdapter(getActivity());
		ArrayList<CategoryData> mData = new ArrayList<CategoryData>();
		for(String data : getResources().getStringArray(R.array.my_account_list)) 
			mData.add(new CategoryData(data, null));
		mAdapter.setListData(mData);
		setListAdapter(mAdapter);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getListView().setPadding(18, 0, 18, 0);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnCategorySelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		mCallback.onCategorySelected(position);
	}
}
