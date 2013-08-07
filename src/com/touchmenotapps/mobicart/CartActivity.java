package com.touchmenotapps.mobicart;

import java.util.ArrayList;

import com.touchmenotapps.mobicart.db.AppDBAdapter;
import com.touchmenotapps.mobicart.interfaces.OnPurchaseSuccessListener;
import com.touchmenotapps.mobicart.model.ShopData;
import com.touchmenotapps.mobicart.util.AppPreferences;
import com.touchmenotapps.mobicart.util.CartPurchaseAsyncTask;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CartActivity extends Activity implements OnPurchaseSuccessListener {

	private ViewGroup mContainerView;
	private float mTotalPrice = 0;
	private String mCurrency = null;
	private AppDBAdapter dbAdapter;
	private ArrayList<ShopData> mCartItems = new ArrayList<ShopData>();
	private AppPreferences mPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_cart);
		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.shape_action_bar_bg));
		getActionBar().setDisplayHomeAsUpEnabled(true);

		mPrefs = new AppPreferences(this);
		dbAdapter = new AppDBAdapter(this);
		mContainerView = (ViewGroup) findViewById(R.id.cart_container);

		findViewById(R.id.cart_remove_all_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						clearCart();
						Toast.makeText(CartActivity.this,
								R.string.msg_remove_all_cart,
								Toast.LENGTH_SHORT).show();
					}
				});
		
		findViewById(R.id.cart_payment_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mPrefs.isRegistrationComplete())
					new CartPurchaseAsyncTask(CartActivity.this, CartActivity.this)
						.execute(mCartItems.toArray(new ShopData[mCartItems.size()]));
				else
					startActivity(new Intent(CartActivity.this, RegisterActivity.class));
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mContainerView.removeAllViews();
		dbAdapter.open();
		Cursor mCursor = null;
		mCursor = dbAdapter.getAllCartItems();
		if (mCursor != null) {
			if(mCursor.getCount() > 0) {
				if (mCursor.moveToFirst()) {
					do {
						ShopData data = new ShopData();
						data.setDBRowNum(mCursor.getInt(0));
						data.setItemCode(mCursor.getString(1));
						data.setTitle(mCursor.getString(2));
						data.setMaxQuantity(mCursor.getInt(3));
						data.setPrice(mCursor.getFloat(4));
						mTotalPrice += mCursor.getFloat(4);
						data.setPriceCurrency(mCursor.getString(5));
						mCurrency = mCursor.getString(5);
						addItem(data);
					} while (mCursor.moveToNext());
				}
				((TextView) findViewById(R.id.cart_total_text)).setText(String
						.valueOf(mTotalPrice) + " " + mCurrency);
			} else {
				findViewById(R.id.cart_empty_text).setVisibility(View.VISIBLE);
				mTotalPrice = 0;
				((TextView) findViewById(R.id.cart_total_text)).setText(String
						.valueOf(mTotalPrice) + " USD");
			}
		} 
		mCursor.close();
		dbAdapter.close();
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

	/**
	 * 
	 * @param data
	 */
	private void addItem(final ShopData data) {
		mCartItems.add(data);
		final ViewGroup mViewCartItemHolder = (ViewGroup) LayoutInflater.from(
				this).inflate(R.layout.layout_cart_item, mContainerView, false);
		((TextView) mViewCartItemHolder.findViewById(R.id.cart_item_title))
				.setText(data.getTitle());
		((TextView) mViewCartItemHolder.findViewById(R.id.cart_item_price))
				.setText(String.valueOf(data.getPrice()) + " "
						+ data.getPriceCurrency());
		mViewCartItemHolder.findViewById(R.id.cart_item_delete_button)
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						mTotalPrice -= data.getPrice();
						// Update the price
						((TextView) findViewById(R.id.cart_total_text))
								.setText(String.valueOf(mTotalPrice) + " "
										+ mCurrency);
						Toast.makeText(CartActivity.this, R.string.msg_remove_from_cart, Toast.LENGTH_SHORT).show();
						dbAdapter.open();
						dbAdapter.deleteCartItem(data.getDBRowNum());
						dbAdapter.close();
						mCartItems.remove(data);
						mContainerView.removeView(mViewCartItemHolder);
						if (mContainerView.getChildCount() == 0) {
							findViewById(R.id.cart_empty_text).setVisibility(
									View.VISIBLE);
						}
					}
				});
		mContainerView.addView(mViewCartItemHolder, 0);
	}
		
	private void clearCart() {
		if (mContainerView.getChildCount() > 0) {
			mContainerView.removeAllViews();
			findViewById(R.id.cart_empty_text).setVisibility(
					View.VISIBLE);
			mTotalPrice = 0;
			dbAdapter.open();
			dbAdapter.deleteAllCartItems();
			dbAdapter.close();
			((TextView) findViewById(R.id.cart_total_text))
					.setText(String.valueOf(mTotalPrice) + " "
							+ mCurrency);
		}
	}

	@Override
	public void onPurchaseSuccess() {
		clearCart();
		Toast.makeText(this, R.string.msg_order_success, Toast.LENGTH_LONG).show();
	}
}
