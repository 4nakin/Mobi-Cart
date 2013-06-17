package com.touchmenotapps.mobicart.fragments;

import java.util.ArrayList;

import com.touchmenotapps.mobicart.R;
import com.touchmenotapps.mobicart.ShoppingListActivity;
import com.touchmenotapps.mobicart.adapters.CategoriesListAdapter;
import com.touchmenotapps.mobicart.model.CategoryData;
import com.touchmenotapps.mobicart.model.ShopData;
import com.touchmenotapps.mobicart.util.StoreDataLoader;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class CategoriesFragment extends ListFragment implements LoaderCallbacks<ArrayList<CategoryData>> {

	private final int LOADER_ID = 2;
	private CategoriesListAdapter mAdapter;
	public static ArrayList<ShopData> mShoppingData = new ArrayList<ShopData>();
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAdapter = new CategoriesListAdapter(getActivity());
		setListAdapter(mAdapter);
		setEmptyText(getString(R.string.error_server_data_fetching));
		setListShown(false);
		getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getListView().setPadding(10, 0, 10, 0);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if(mAdapter.getItem(position) != null) {
			Intent intent = new Intent(getActivity(), ShoppingListActivity.class);
			intent.putExtra("categoryName", mAdapter.getItem(position).getCategory());
			mShoppingData = mAdapter.getItem(position).getShopData();
			startActivity(intent);
		}
	}

	@Override
	public Loader<ArrayList<CategoryData>> onCreateLoader(int arg0, Bundle arg1) {
		return new StoreDataLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<CategoryData>> arg0,
			ArrayList<CategoryData> data) {
		mAdapter.setListData(data);
    	if(isResumed())
    		setListShown(true);
    	else
    		setListShownNoAnimation(true);
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<CategoryData>> arg0) {
		mAdapter.setListData(new ArrayList<CategoryData>());
	}
}
