package com.touchmenotapps.mobicart;

import com.touchmenotapps.mobicart.adapters.ShopListAdapter;
import com.touchmenotapps.mobicart.fragments.CategoriesFragment;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class ShoppingListActivity extends ListActivity {
	
	private ShopListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_action_bar_bg));
		if(getIntent().getStringExtra("categoryName") != null)
			getActionBar().setTitle(getIntent().getStringExtra("categoryName"));
		mAdapter = new ShopListAdapter(this);
		mAdapter.setListData(CategoriesFragment.mShoppingData);
		setListAdapter(mAdapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getListView().setPadding(10, 0, 10, 0);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, DetailsActivity.class);
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return true;
	}
}
