package com.touchmenotapps.mobicart.fragments;

import com.touchmenotapps.mobicart.DetailsActivity;
import com.touchmenotapps.mobicart.GalleryActivity;
import com.touchmenotapps.mobicart.R;
import com.touchmenotapps.mobicart.db.AppDBAdapter;
import com.touchmenotapps.mobicart.interfaces.OnImageDownloadComplete;
import com.touchmenotapps.mobicart.model.ShopData;
import com.touchmenotapps.mobicart.util.LoadImageFromWebTask;
import com.touchmenotapps.mobicart.widgets.TouchHighlightImageButton;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.NumberPicker.OnValueChangeListener;

public class ShopItemDetialsFragment extends Fragment {

	private View mViewHolder;
	
	private TextView mPrice, mTitle, mVendor, mAvailable, mDescription;
	private String mCurrency;
	private String[] mImages;
	private float mItemPrice;
	private int mMaxQuantity;
	
	private TouchHighlightImageButton mGalleryButton;
	private AppDBAdapter dbAdapter;
	private MenuItem mWishlist;
	private boolean isWishlist = false, isAvailable = false;
	private ShopData mData = new ShopData();
	private long mDBRowID = -1;
			
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		mViewHolder = inflater.inflate(R.layout.fragment_shop_item_details, null);
		dbAdapter = new AppDBAdapter(getActivity());
		mPrice = (TextView) mViewHolder.findViewById(R.id.details_price_text);
		mTitle = (TextView) mViewHolder.findViewById(R.id.details_title_text);
		mVendor = (TextView) mViewHolder.findViewById(R.id.details_vendor_text);
		mAvailable = (TextView) mViewHolder.findViewById(R.id.details_available_text);
		mDescription = (TextView) mViewHolder.findViewById(R.id.details_description_text);
		mGalleryButton = (TouchHighlightImageButton) mViewHolder.findViewById(R.id.detials_header_image);
		
		mGalleryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mImages != null) {
					Intent intent = new Intent(getActivity(), GalleryActivity.class);
					intent.putExtra(DetailsActivity.TAG_ITEM_IMAGE_URLS, mImages);
					startActivity(intent);
				} else 
					Toast.makeText(getActivity(), R.string.msg_no_gallery_image, Toast.LENGTH_LONG).show();
			}
		});
		
		mViewHolder.findViewById(R.id.details_purchase_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(isAvailable)
					showPurchaseDialog();
				else
					Toast.makeText(getActivity(), R.string.msg_out_of_stock, Toast.LENGTH_LONG).show();
			}
		});
		
		return mViewHolder;
	}
	
	private void setData() {
		if(getArguments().getString(DetailsActivity.TAG_ITEM_NAME) != null) {
			mData.setTitle(getArguments().getString(DetailsActivity.TAG_ITEM_NAME));
			mTitle.setText(mData.getTitle());
		}
		if(getArguments().getString(DetailsActivity.TAG_ITEM_CATEGORY) != null) {
			mData.setCategory(getArguments().getString(DetailsActivity.TAG_ITEM_CATEGORY));
		}
		if(getArguments().getString(DetailsActivity.TAG_ITEM_VENDOR) != null) {
			mData.setVendor(getArguments().getString(DetailsActivity.TAG_ITEM_VENDOR));
			mVendor.setText(mData.getVendor());
		}
		if(getArguments().getString(DetailsActivity.TAG_ITEM_DESCRIPTION) != null) {
			mData.setDescription(getArguments().getString(DetailsActivity.TAG_ITEM_DESCRIPTION));
			mDescription.setText(mData.getDescription());
		}
		if(getArguments().getString(DetailsActivity.TAG_ITEM_CURRENCY) != null) {
			mCurrency = getArguments().getString(DetailsActivity.TAG_ITEM_CURRENCY);
			mData.setPriceCurrency(getArguments().getString(DetailsActivity.TAG_ITEM_CURRENCY));
		}
		if(getArguments().getStringArray(DetailsActivity.TAG_ITEM_IMAGE_URLS) != null) {
			mImages = getArguments().getStringArray(DetailsActivity.TAG_ITEM_IMAGE_URLS);
			new LoadImageFromWebTask(getActivity(), new OnImageDownloadComplete() {			
				@Override
				public void onImageDownloadCompleted(Drawable drawable) {
					mGalleryButton.setImageDrawable(drawable);
				}
			}).execute(mImages);
		}
		if(getArguments().getString(DetailsActivity.TAG_ITEM_CODE) != null)
				mData.setItemCode(getArguments().getString(DetailsActivity.TAG_ITEM_CODE));
		
		isAvailable = getArguments().getBoolean(DetailsActivity.TAG_ITEM_AVAILABLE, false);
		mDBRowID = getArguments().getLong(DetailsActivity.TAG_ITEM_DB_ROW_ID, -1);
		mData.setDBRowNum(mDBRowID);
		isWishlist = getArguments().getBoolean(DetailsActivity.TAG_ITEM_IS_WISHLIST, false);
		mData.setWishlist(isWishlist);
		mItemPrice = getArguments().getFloat(DetailsActivity.TAG_ITEM_PRICE, 0.0f);
		mData.setPrice(mItemPrice);
		mMaxQuantity = getArguments().getInt(DetailsActivity.TAG_ITEM_AVAILABLE_QUATITY, 1);
		mData.setMaxQuantity(mMaxQuantity);
		
		mPrice.setText("Price: " + String.valueOf(mItemPrice) + " " + mCurrency);
		if(isAvailable) {
			mAvailable.setText(R.string.in_stock);
			mAvailable.setBackgroundResource(android.R.color.holo_green_dark);
			mData.setAvailable("1");
		} else {
			mAvailable.setText(R.string.out_of_stock);
			mData.setAvailable("0");
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setData();
	}
	
	@Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_details, menu);
        mWishlist = menu.findItem(R.id.menu_detials_add_wishlist);
		if(isWishlist)
			mWishlist.setIcon(R.drawable.ic_action_is_wishlist);
    }
		
	private void showPurchaseDialog() {
		final AlertDialog mPurchaseDialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT).create();
		mPurchaseDialog.setTitle(R.string.select_quantity);
		mPurchaseDialog.setCanceledOnTouchOutside(false);
		View mViewHolder = (View) LayoutInflater.from(getActivity()).inflate(R.layout.dialog_place_order, null);
		mPurchaseDialog.setView(mViewHolder);
		
		final NumberPicker mQuantity = (NumberPicker) mViewHolder.findViewById(R.id.place_order_number_picker);
		mQuantity.setMinValue(1);
		mQuantity.setMaxValue(mMaxQuantity); 
		final TextView mTotalText = (TextView) mViewHolder.findViewById(R.id.place_order_total_text);
		mTotalText.setText(String.valueOf(mItemPrice) + " " + mCurrency);
		mQuantity.setOnValueChangedListener(new OnValueChangeListener() {
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				mTotalText.setText(String.valueOf(mItemPrice * newVal) + " " + mCurrency);
			}
		});
		
		mPurchaseDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				mPurchaseDialog.dismiss();
			}
		});
		
		mPurchaseDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.add_to_cart), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				dbAdapter.open();
				dbAdapter.insertCartItem(mData, mQuantity.getValue(), mItemPrice);
				dbAdapter.close();
				Toast.makeText(getActivity(), R.string.msg_added_to_cart, Toast.LENGTH_SHORT).show();
				mPurchaseDialog.dismiss();
			}
		});
		mPurchaseDialog.show();
	}
	
	public boolean isWishlist() {
		return isWishlist;
	}
	
	public void setWishlist(boolean wishlist) {
		isWishlist = wishlist;
		if(wishlist) {
			mWishlist.setIcon(R.drawable.ic_action_is_wishlist);
			dbAdapter.open();
			mDBRowID = dbAdapter.insertWishlistItem(mData);
			dbAdapter.close();
			Toast.makeText(getActivity(), R.string.msg_added_wishlist, Toast.LENGTH_SHORT).show();
		} else {
			mWishlist.setIcon(R.drawable.ic_action_add_wishlist);
			dbAdapter.open();
			dbAdapter.deleteWishlistItem(mDBRowID);
			dbAdapter.close();
			Toast.makeText(getActivity(), R.string.msg_remove_wishlist, Toast.LENGTH_SHORT).show();
		}
	}
}
