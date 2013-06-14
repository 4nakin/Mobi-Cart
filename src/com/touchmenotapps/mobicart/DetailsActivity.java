package com.touchmenotapps.mobicart;

import com.touchmenotapps.mobicart.fragments.ShopItemDetialsFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class DetailsActivity extends Activity {

	public static final String TAG_ITEM_NAME = "itemName";
	public static final String TAG_ITEM_CODE = "itemCode";
	public static final String TAG_ITEM_VENDOR = "itemVendor";
	public static final String TAG_ITEM_PRICE = "itemPrice";
	public static final String TAG_ITEM_AVAILABLE = "itemAvailable";
	public static final String TAG_ITEM_CURRENCY = "itemCurrency";
	public static final String TAG_ITEM_DESCRIPTION = "itemDescription";
	public static final String TAG_ITEM_CATEGORY = "itemCategory";
	public static final String TAG_ITEM_IMAGE_URLS = "itemImageURLS";
	public static final String TAG_ITEM_AVAILABLE_QUATITY = "itemAvailableQuantity";
	public static final String TAG_ITEM_IS_WISHLIST = "itemWishlist";
	public static final String TAG_ITEM_DB_ROW_ID = "itemDBRowID";
	
	private ShopItemDetialsFragment mFragment;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_action_bar_bg));
		if(getIntent().getStringExtra(TAG_ITEM_NAME) != null) {
			getActionBar().setTitle(getIntent().getStringExtra(TAG_ITEM_NAME));
		}
		if(getIntent().getStringExtra(TAG_ITEM_CATEGORY) != null) {
			getActionBar().setSubtitle(getIntent().getStringExtra(TAG_ITEM_CATEGORY));
		}
		mFragment = new ShopItemDetialsFragment();
		initView();
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
	
	private void initView() {
		Bundle argBundle = new Bundle();
		argBundle.putBoolean(TAG_ITEM_IS_WISHLIST, getIntent().getBooleanExtra(TAG_ITEM_IS_WISHLIST, false));
		argBundle.putString(TAG_ITEM_NAME, getIntent().getStringExtra(TAG_ITEM_NAME));
		argBundle.putString(TAG_ITEM_VENDOR, getIntent().getStringExtra(TAG_ITEM_VENDOR));
		argBundle.putFloat(TAG_ITEM_PRICE, getIntent().getFloatExtra(TAG_ITEM_PRICE, 0.0f));
		argBundle.putString(TAG_ITEM_CURRENCY, getIntent().getStringExtra(TAG_ITEM_CURRENCY));
		argBundle.putString(TAG_ITEM_DESCRIPTION, getIntent().getStringExtra(TAG_ITEM_DESCRIPTION));
		argBundle.putString(TAG_ITEM_CATEGORY, getIntent().getStringExtra(TAG_ITEM_CATEGORY));
		argBundle.putStringArray(TAG_ITEM_IMAGE_URLS, getIntent().getStringArrayExtra(TAG_ITEM_IMAGE_URLS));
		argBundle.putBoolean(TAG_ITEM_AVAILABLE, getIntent().getBooleanExtra(TAG_ITEM_AVAILABLE, false));
		argBundle.putInt(TAG_ITEM_AVAILABLE_QUATITY, getIntent().getIntExtra(TAG_ITEM_AVAILABLE_QUATITY, 1));
		argBundle.putString(TAG_ITEM_CODE, getIntent().getStringExtra(TAG_ITEM_CODE));
		mFragment.setArguments(argBundle);		
		getFragmentManager()
			.beginTransaction()
			.replace(R.id.details_fragment_holder, mFragment)
			.commit();
	}
}
