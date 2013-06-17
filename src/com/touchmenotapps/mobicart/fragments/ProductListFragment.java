package com.touchmenotapps.mobicart.fragments;

import java.util.ArrayList;

import com.touchmenotapps.mobicart.DetailsActivity;
import com.touchmenotapps.mobicart.R;
import com.touchmenotapps.mobicart.adapters.ShopListAdapter;
import com.touchmenotapps.mobicart.model.ShopData;
import com.touchmenotapps.mobicart.util.TopDealsDataLoader;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ProductListFragment extends ListFragment  implements LoaderCallbacks<ArrayList<ShopData>>{

	private final int LOADER_ID = 3;
	private ShopListAdapter mAdapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAdapter = new ShopListAdapter(getActivity());
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
			Intent intent = new Intent(getActivity(), DetailsActivity.class);
			intent.putExtra(DetailsActivity.TAG_ITEM_IS_WISHLIST, mAdapter.getItem(position).isWishlist());
			intent.putExtra(DetailsActivity.TAG_ITEM_NAME, mAdapter.getItem(position).getTitle());
			intent.putExtra(DetailsActivity.TAG_ITEM_VENDOR, mAdapter.getItem(position).getVendor());
			intent.putExtra(DetailsActivity.TAG_ITEM_PRICE, mAdapter.getItem(position).getPrice());
			intent.putExtra(DetailsActivity.TAG_ITEM_CURRENCY, mAdapter.getItem(position).getPriceCurrency());
			intent.putExtra(DetailsActivity.TAG_ITEM_DESCRIPTION, mAdapter.getItem(position).getDescription());
			intent.putExtra(DetailsActivity.TAG_ITEM_CATEGORY, mAdapter.getItem(position).getCategory());
			intent.putExtra(DetailsActivity.TAG_ITEM_IMAGE_URLS, mAdapter.getItem(position).getURLS());
			intent.putExtra(DetailsActivity.TAG_ITEM_AVAILABLE, mAdapter.getItem(position).getAvailable());
			intent.putExtra(DetailsActivity.TAG_ITEM_AVAILABLE_QUATITY, mAdapter.getItem(position).getMaxQuantity());
			intent.putExtra(DetailsActivity.TAG_ITEM_CODE, mAdapter.getItem(position).getItemCode());
			startActivity(intent);
		}
	}

	@Override
	public Loader<ArrayList<ShopData>> onCreateLoader(int arg0, Bundle arg1) {
		return new TopDealsDataLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<ShopData>> arg0,
			ArrayList<ShopData> data) {
		mAdapter.setListData(data);
    	if(isResumed())
    		setListShown(true);
    	else
    		setListShownNoAnimation(true);
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<ShopData>> arg0) {
		mAdapter.setListData(new ArrayList<ShopData>());
	}
}
