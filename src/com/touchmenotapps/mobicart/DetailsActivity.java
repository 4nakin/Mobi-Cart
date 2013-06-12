package com.touchmenotapps.mobicart;

import java.io.InputStream;
import java.net.URL;

import com.touchmenotapps.mobicart.db.AppDBAdapter;
import com.touchmenotapps.mobicart.model.ShopData;
import com.touchmenotapps.mobicart.util.NetworkUtil;
import com.touchmenotapps.mobicart.widgets.TouchHighlightImageButton;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;
import android.widget.Toast;

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
	private NetworkUtil mNetwokUtil;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_action_bar_bg));
				
		mNetwokUtil = new NetworkUtil();
		dbAdapter = new AppDBAdapter(this);
		mPrice = (TextView) findViewById(R.id.details_price_text);
		mTitle = (TextView) findViewById(R.id.details_title_text);
		mVendor = (TextView) findViewById(R.id.details_vendor_text);
		mAvailable = (TextView) findViewById(R.id.details_available_text);
		mDescription = (TextView) findViewById(R.id.details_description_text);
		mGalleryButton = (TouchHighlightImageButton) findViewById(R.id.detials_header_image);
		
		initView();
		
		mGalleryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		findViewById(R.id.details_purchase_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(isAvailable)
					showPurchaseDialog();
				else
					Toast.makeText(DetailsActivity.this, R.string.msg_out_of_stock, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	private void initView() {
		if(getIntent().getStringExtra(TAG_ITEM_NAME) != null) {
			getActionBar().setTitle(getIntent().getStringExtra(TAG_ITEM_NAME));
			mTitle.setText(getIntent().getStringExtra(TAG_ITEM_NAME));
			mData.setTitle(getIntent().getStringExtra(TAG_ITEM_NAME));
		}
		if(getIntent().getStringExtra(TAG_ITEM_CATEGORY) != null) {
			getActionBar().setSubtitle(getIntent().getStringExtra(TAG_ITEM_CATEGORY));
			mData.setCategory(getIntent().getStringExtra(TAG_ITEM_CATEGORY));
		}
		if(getIntent().getStringExtra(TAG_ITEM_VENDOR) != null) {
			mVendor.setText(getIntent().getStringExtra(TAG_ITEM_VENDOR));
			mData.setVendor(getIntent().getStringExtra(TAG_ITEM_VENDOR));
		}
		if(getIntent().getStringExtra(TAG_ITEM_DESCRIPTION) != null) {
			mDescription.setText(getIntent().getStringExtra(TAG_ITEM_DESCRIPTION));
			mData.setDescription(getIntent().getStringExtra(TAG_ITEM_DESCRIPTION));
		}
		if(getIntent().getStringExtra(TAG_ITEM_CURRENCY) != null) {
			mCurrency = getIntent().getStringExtra(TAG_ITEM_CURRENCY);
			mData.setPriceCurrency(getIntent().getStringExtra(TAG_ITEM_CURRENCY));
		}
		if(getIntent().getStringArrayExtra(TAG_ITEM_IMAGE_URLS) != null) {
			mImages = getIntent().getStringArrayExtra(TAG_ITEM_IMAGE_URLS);
			//Load image from the web
			if(mNetwokUtil.isNetworkAvailable(this))
				new LoadImageFromWebOperations().execute(mImages[0]);
			else
				mGalleryButton.setImageResource(R.drawable.ic_action_alert);
		}
		if(getIntent().getStringExtra(TAG_ITEM_CODE) != null)
				mData.setItemCode(getIntent().getStringExtra(TAG_ITEM_CODE));
		isAvailable = getIntent().getBooleanExtra(TAG_ITEM_AVAILABLE, false);
		mDBRowID = getIntent().getLongExtra(TAG_ITEM_DB_ROW_ID, -1);
		isWishlist = getIntent().getBooleanExtra(TAG_ITEM_IS_WISHLIST, false);
		mItemPrice = getIntent().getFloatExtra(TAG_ITEM_PRICE, 0.0f);
		mData.setPrice(mItemPrice);
		mMaxQuantity = getIntent().getIntExtra(TAG_ITEM_AVAILABLE_QUATITY, 1);
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
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_details, menu);
		mWishlist = menu.findItem(R.id.menu_detials_add_wishlist);
		if(isWishlist)
			mWishlist.setIcon(R.drawable.ic_action_is_wishlist);
		return true;
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
			if(isWishlist) {
				isWishlist = false;
				mWishlist.setIcon(R.drawable.ic_action_add_wishlist);
				dbAdapter.open();
				dbAdapter.deleteWishlistItem(mDBRowID);
				dbAdapter.close();
				Toast.makeText(this, R.string.msg_remove_wishlist, Toast.LENGTH_SHORT).show();
			} else {
				isWishlist = true;
				mWishlist.setIcon(R.drawable.ic_action_is_wishlist);
				dbAdapter.open();
				mDBRowID = dbAdapter.insertWishlistItem(mData);
				dbAdapter.close();
				Toast.makeText(this, R.string.msg_added_wishlist, Toast.LENGTH_SHORT).show();
			}
			break;
		}
		return true;
	}
	
	private void showPurchaseDialog() {
		final AlertDialog mPurchaseDialog = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT).create();
		mPurchaseDialog.setTitle(R.string.select_quantity);
		mPurchaseDialog.setCanceledOnTouchOutside(false);
		View mViewHolder = (View) LayoutInflater.from(this).inflate(R.layout.dialog_place_order, null);
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
				dbAdapter.insertCartItem(mData, mItemPrice * mQuantity.getValue());
				dbAdapter.close();
				Toast.makeText(DetailsActivity.this, R.string.msg_added_to_cart, Toast.LENGTH_SHORT).show();
				mPurchaseDialog.dismiss();
			}
		});
		mPurchaseDialog.show();
	}
	
	class LoadImageFromWebOperations extends AsyncTask<String, Void, Drawable> {
        protected Drawable doInBackground(String... urls) {
            try {
    	        InputStream is = (InputStream) new URL(urls[0]).getContent();
    	        Drawable d = Drawable.createFromStream(is, "image");
    	        return d;
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        return null;
    	    }
        }

		protected void onPostExecute(Drawable drawable) {
        	if(drawable != null) 
        		mGalleryButton.setImageDrawable(drawable);
        	else
        		mGalleryButton.setImageResource(R.drawable.ic_action_alert);
        }
     }
}
