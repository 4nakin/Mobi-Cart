package com.touchmenotapps.mobicart;

import com.touchmenotapps.mobicart.fragments.ShoppingListFragment;
import com.touchmenotapps.mobicart.fragments.ShopItemDetialsFragment;
import com.touchmenotapps.mobicart.model.ShopData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class ShoppingListActivity extends Activity 
	implements ShoppingListFragment.OnShopListItemClickListener {
	
	private ShopItemDetialsFragment mFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_list);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_action_bar_bg));
		if(getIntent().getStringExtra("categoryName") != null)
			getActionBar().setTitle(getIntent().getStringExtra("categoryName"));
		
		mFragment = new ShopItemDetialsFragment();
		if(findViewById(R.id.shopping_list_single_pane_container) != null)
			getFragmentManager()
				.beginTransaction()
				.replace(R.id.shopping_list_single_pane_container, new ShoppingListFragment())
				.commit();
	}
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.menu_detials_goto_cart:
			startActivity(new Intent(this, CartActivity.class));
			break;
		case R.id.menu_detials_add_wishlist:
			if(mFragment.isWishlist()) {
				mFragment.setWishlist(false);
			} else {
				mFragment.setWishlist(true);
			}
			break;
		}
		return true;
	}

	@Override
	public void onShopListItemClick(ShopData data) {
		if(findViewById(R.id.shopping_list_double_pane_container) != null) {
			Bundle argBundle = new Bundle();
			argBundle.putBoolean(DetailsActivity.TAG_ITEM_IS_WISHLIST, data.isWishlist());
			argBundle.putString(DetailsActivity.TAG_ITEM_NAME, data.getTitle());
			argBundle.putString(DetailsActivity.TAG_ITEM_VENDOR, data.getVendor());
			argBundle.putFloat(DetailsActivity.TAG_ITEM_PRICE, data.getPrice());
			argBundle.putString(DetailsActivity.TAG_ITEM_CURRENCY, data.getPriceCurrency());
			argBundle.putString(DetailsActivity.TAG_ITEM_DESCRIPTION, data.getDescription());
			argBundle.putString(DetailsActivity.TAG_ITEM_CATEGORY, data.getCategory());
			argBundle.putStringArray(DetailsActivity.TAG_ITEM_IMAGE_URLS, data.getURLS());
			argBundle.putBoolean(DetailsActivity.TAG_ITEM_AVAILABLE, data.getAvailable());
			argBundle.putInt(DetailsActivity.TAG_ITEM_AVAILABLE_QUATITY, data.getMaxQuantity());
			argBundle.putString(DetailsActivity.TAG_ITEM_CODE, data.getItemCode());
			mFragment.setArguments(argBundle);			
			getFragmentManager()
				.beginTransaction()
				.replace(R.id.shopping_list_double_pane_container, mFragment)
				.commit();
		} else {
			Intent intent = new Intent(this, DetailsActivity.class);
			intent.putExtra(DetailsActivity.TAG_ITEM_IS_WISHLIST, data.isWishlist());
			intent.putExtra(DetailsActivity.TAG_ITEM_NAME, data.getTitle());
			intent.putExtra(DetailsActivity.TAG_ITEM_VENDOR, data.getVendor());
			intent.putExtra(DetailsActivity.TAG_ITEM_PRICE, data.getPrice());
			intent.putExtra(DetailsActivity.TAG_ITEM_CURRENCY, data.getPriceCurrency());
			intent.putExtra(DetailsActivity.TAG_ITEM_DESCRIPTION, data.getDescription());
			intent.putExtra(DetailsActivity.TAG_ITEM_CATEGORY, data.getCategory());
			intent.putExtra(DetailsActivity.TAG_ITEM_IMAGE_URLS, data.getURLS());
			intent.putExtra(DetailsActivity.TAG_ITEM_AVAILABLE, data.getAvailable());
			intent.putExtra(DetailsActivity.TAG_ITEM_AVAILABLE_QUATITY, data.getMaxQuantity());
			intent.putExtra(DetailsActivity.TAG_ITEM_CODE, data.getItemCode());
			startActivity(intent);
		}
	}
}
