package com.touchmenotapps.mobicart.fragments;

import com.touchmenotapps.mobicart.DetailsActivity;
import com.touchmenotapps.mobicart.R;
import com.touchmenotapps.mobicart.db.AppDBAdapter;
import com.touchmenotapps.mobicart.model.ShopData;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class WishlistFragment extends Fragment {

	private View mViewHolder;
	private ViewGroup mContainerView;
	private AppDBAdapter dbAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		dbAdapter = new AppDBAdapter(getActivity());
		mViewHolder = inflater.inflate(R.layout.fragment_wishlist, null);
		mContainerView = (ViewGroup) mViewHolder.findViewById(R.id.wishlist_container);
		mViewHolder.findViewById(R.id.wishlist_remove_all_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mContainerView.getChildCount() > 0) {
					mContainerView.removeAllViews();
					mViewHolder.findViewById(R.id.wishlist_empty_text).setVisibility(View.VISIBLE);
					Toast.makeText(getActivity(), R.string.msg_remove_all_cart, Toast.LENGTH_SHORT).show();
					dbAdapter.open();
					dbAdapter.deleteAllWishlistItems();
					dbAdapter.close();
				}
			}
		});
		return mViewHolder;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mContainerView.removeAllViews();
		dbAdapter.open();
		Cursor mCursor = null;
		mCursor = dbAdapter.getAllWishlistItems();
		if (mCursor != null) {
			if(mCursor.getCount() > 0) {
				if (mCursor.moveToFirst()) {
					do {
						ShopData data = new ShopData();
						data.setDBRowNum(mCursor.getInt(0));
						data.setItemCode(mCursor.getString(1));
						data.setTitle(mCursor.getString(2));
						data.setDescription(mCursor.getString(3));
						data.setVendor(mCursor.getString(4));
						data.setAvailable(mCursor.getString(5));
						data.setMaxQuantity(mCursor.getInt(6));
						data.setCategory(mCursor.getString(7));
						data.setPrice(mCursor.getFloat(8));
						data.setPriceCurrency(mCursor.getString(9));
						data.setWishlist(true);
						addWishlistItem(data);
					} while (mCursor.moveToNext());
				}
			} else {
				mViewHolder.findViewById(R.id.wishlist_empty_text).setVisibility(View.VISIBLE);
			}
		} 
		mCursor.close();
		dbAdapter.close();
	}
	
	private void addWishlistItem(final ShopData data) {
		final ViewGroup mViewCartItemHolder = (ViewGroup) LayoutInflater.from(
				getActivity()).inflate(R.layout.layout_wishlist_item, mContainerView, false);
		((TextView) mViewCartItemHolder.findViewById(R.id.wishlist_item_title))
				.setText(data.getTitle());
		((TextView) mViewCartItemHolder.findViewById(R.id.wishlist_item_price))
				.setText(String.valueOf(data.getPrice()) + " "
						+ data.getPriceCurrency());
		
		mViewCartItemHolder.findViewById(R.id.wishlist_item_view_button)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), DetailsActivity.class);
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
				intent.putExtra(DetailsActivity.TAG_ITEM_DB_ROW_ID, data.getDBRowNum());
				startActivity(intent);
			}
		});
		
		mViewCartItemHolder.findViewById(R.id.wishlist_item_delete_button)
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						dbAdapter.open();
						dbAdapter.deleteWishlistItem(data.getDBRowNum());
						dbAdapter.close();
						mContainerView.removeView(mViewCartItemHolder);
						if (mContainerView.getChildCount() == 0) {
							mViewHolder.findViewById(R.id.wishlist_empty_text).setVisibility(
									View.VISIBLE);
						}
					}
				});
		mContainerView.addView(mViewCartItemHolder, 0);
	}
}
