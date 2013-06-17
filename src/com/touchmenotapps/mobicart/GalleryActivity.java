package com.touchmenotapps.mobicart;

import java.io.InputStream;
import java.net.URL;

import com.touchmenotapps.mobicart.util.NetworkUtil;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GalleryActivity extends Activity {

	private String[] mImages = null;
	private int mTotalImages = 0, mCurrentImage = 0;
	private LinearLayout mContainerView;
	private NetworkUtil mNetwokUtil;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_action_bar_bg));
		mContainerView = (LinearLayout) findViewById(R.id.gallery_container_view);
		mNetwokUtil = new NetworkUtil();
		
		if(getIntent().getStringArrayExtra(DetailsActivity.TAG_ITEM_IMAGE_URLS) != null) {
			mImages = getIntent().getStringArrayExtra(DetailsActivity.TAG_ITEM_IMAGE_URLS);
			mTotalImages = mImages.length;
			showImage(mImages[mCurrentImage]);
			((TextView) findViewById(R.id.current_image_num)).setText(String.valueOf(mCurrentImage + 1));
			((TextView) findViewById(R.id.total_image_num)).setText(String.valueOf(mTotalImages));
		} else {
			((TextView) findViewById(R.id.current_image_num)).setText(String.valueOf(mCurrentImage));
			((TextView) findViewById(R.id.total_image_num)).setText(String.valueOf(mTotalImages));
		}
		
		findViewById(R.id.gallery_next_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mCurrentImage < (mTotalImages-1)) {
					mCurrentImage++;
					((TextView) findViewById(R.id.current_image_num)).setText(String.valueOf(mCurrentImage + 1));
					mContainerView.removeAllViews();
					showImage(mImages[mCurrentImage]);
				}
			}
		});
		
		findViewById(R.id.gallery_prev_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mCurrentImage > 0 ) {
					mCurrentImage--;
					((TextView) findViewById(R.id.current_image_num)).setText(String.valueOf(mCurrentImage + 1));
					mContainerView.removeAllViews();
					showImage(mImages[mCurrentImage]);
				}
			}
		});
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
	
	private void showImage(String URL) {
		final ViewGroup mGalleryItemHolder = (ViewGroup) LayoutInflater.from(
				this).inflate(R.layout.layout_gallery_image, mContainerView, false);
		if(mNetwokUtil.isNetworkAvailable(this)) {
			LoadImageFromWebOperations mImageLoader = new LoadImageFromWebOperations(
					((ImageView) mGalleryItemHolder.findViewById(R.id.gallery_image)), 
					((ProgressBar) mGalleryItemHolder.findViewById(R.id.gallery_image_loading_progress)));
			mImageLoader.execute(URL);
		} else {
			((ImageView) mGalleryItemHolder.findViewById(R.id.gallery_image)).setImageResource(R.drawable.ic_action_alert);
			Toast.makeText(this, R.string.msg_no_internet_connection, Toast.LENGTH_LONG).show();
		}
		mContainerView.addView(mGalleryItemHolder);
	}
	
	class LoadImageFromWebOperations extends AsyncTask<String, Void, Drawable> {
		
		private ImageView mGalleryImage;
		private ProgressBar mLoader;
		
		public LoadImageFromWebOperations(ImageView galleryImage, ProgressBar loader) {
			mGalleryImage = galleryImage;
			mLoader = loader;
		}
		
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
			mLoader.setVisibility(View.GONE);
			mGalleryImage.setVisibility(View.VISIBLE);
        	if(drawable != null) 
        		mGalleryImage.setImageDrawable(drawable);
        	else
        		mGalleryImage.setImageResource(R.drawable.ic_action_alert);
        }
     }
}
