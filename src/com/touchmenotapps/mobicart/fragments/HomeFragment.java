package com.touchmenotapps.mobicart.fragments;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.touchmenotapps.mobicart.DetailsActivity;
import com.touchmenotapps.mobicart.R;
import com.touchmenotapps.mobicart.model.ShopData;
import com.touchmenotapps.mobicart.util.AnimationUtil;
import com.touchmenotapps.mobicart.util.CategoryXMLHandler;
import com.touchmenotapps.mobicart.util.NetworkUtil;
import com.touchmenotapps.mobicart.widgets.TouchHighlightImageButton;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

public class HomeFragment extends Fragment {

	private View mViewHolder;
	private ViewAnimator mFeaturedViewHolder;
	private ArrayList<ShopData> mData = new ArrayList<ShopData>();
	private AnimationUtil mAnimUtil;
	private int TOTAL_BANNER_COUNT = 0;
	private NetworkUtil mNetwokUtil;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mAnimUtil = new AnimationUtil();
		mNetwokUtil = new NetworkUtil();
		mViewHolder = inflater.inflate(R.layout.fragment_home, null);
		mFeaturedViewHolder = (ViewAnimator) mViewHolder.findViewById(R.id.home_fragment_view_holder);
		mViewHolder.findViewById(R.id.home_prev_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(mFeaturedViewHolder.getDisplayedChild() != 0) {
					mFeaturedViewHolder.setInAnimation(mAnimUtil.inFromRightAnimation(500));
					mFeaturedViewHolder.setOutAnimation(mAnimUtil.outToLeftAnimation(500));
					mFeaturedViewHolder.showPrevious();
				} 
			}
		});
		
		mViewHolder.findViewById(R.id.home_next_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mFeaturedViewHolder.getDisplayedChild() != (TOTAL_BANNER_COUNT - 1)) {
					mFeaturedViewHolder.setInAnimation(mAnimUtil.inFromLeftAnimation(500));
					mFeaturedViewHolder.setOutAnimation(mAnimUtil.outToRightAnimation(500));
					mFeaturedViewHolder.showNext();
				} 
			}
		});
		
		mViewHolder.findViewById(R.id.featured_banner_loading).setVisibility(View.VISIBLE);
		new RetreiveFeedTask().execute("");
		return mViewHolder;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	private void addBannerItem(final ShopData data, final int pos) {
		final View mBannerItemView = (View) LayoutInflater.from(getActivity()).inflate(R.layout.layout_featured_home_item_view, null); 
		final TouchHighlightImageButton mBannerBtn = (TouchHighlightImageButton) mBannerItemView.findViewById(R.id.featured_banner_image);
		mBannerBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
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
				startActivity(intent);
			}
		});
		((TextView) mBannerItemView.findViewById(R.id.featured_banner_title_text)).setText(data.getTitle());
		((TextView) mBannerItemView.findViewById(R.id.featured_banner_vendor_text)).setText(data.getVendor());
		((TextView) mBannerItemView.findViewById(R.id.featured_banner_price_text)).setText("Price: " + data.getPrice() + " " + data.getPriceCurrency());
		if(mNetwokUtil.isNetworkAvailable(getActivity()))
			mBannerBtn.setImageDrawable(loadImageFromWeb(data.getURLS()[0]));
		else
			mBannerBtn.setImageResource(R.drawable.ic_action_alert);
		mFeaturedViewHolder.addView(mBannerItemView, pos);
	}
	
	private Drawable loadImageFromWeb(String URL) {
		try {
	        InputStream is = (InputStream) new URL(URL).getContent();
	        Drawable d = Drawable.createFromStream(is, "image");
	        return d;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return getResources().getDrawable(R.drawable.ic_action_alert);
	    }
	}
		
	class RetreiveFeedTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            try {
                //URL url= new URL(urls[0]);
                SAXParserFactory factory =SAXParserFactory.newInstance();
                SAXParser parser=factory.newSAXParser();
                XMLReader xmlreader=parser.getXMLReader();
                
                CategoryXMLHandler mResponseHandler=new CategoryXMLHandler();
                xmlreader.setContentHandler(mResponseHandler);
                //InputSource is=new InputSource(url.openStream());
                InputStream is = getResources().openRawResource(R.raw.dummy_featured);
                xmlreader.parse(new InputSource(is));
                mData.clear();
                mData = mResponseHandler.getData().get(0).getShopData();
                return "";
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String feed) {
        	if(feed != null) 
        		if(mData.size() > 0) {
        			TOTAL_BANNER_COUNT = mData.size();
        			for(int i =0; i < mData.size(); i++) 
        				addBannerItem(mData.get(i), i);
        			mViewHolder.findViewById(R.id.featured_banner_loading).setVisibility(View.GONE);
        		}
        	else {
        		Toast.makeText(getActivity(), R.string.error_server_data_fetching, Toast.LENGTH_LONG).show();
        	}
        }
     }
}
