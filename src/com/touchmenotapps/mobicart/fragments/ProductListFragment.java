package com.touchmenotapps.mobicart.fragments;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.touchmenotapps.mobicart.DetailsActivity;
import com.touchmenotapps.mobicart.R;
import com.touchmenotapps.mobicart.adapters.ShopListAdapter;
import com.touchmenotapps.mobicart.util.CategoryXMLHandler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ProductListFragment extends ListFragment {

	private ShopListAdapter mAdapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAdapter = new ShopListAdapter(getActivity());
		new RetreiveFeedTask().execute("");
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
                InputStream is = getResources().openRawResource(R.raw.dummy_top);
                xmlreader.parse(new InputSource(is));
                mAdapter.setListData(mResponseHandler.getData().get(0).getShopData());
                return "";
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String feed) {
        	if(feed != null) 
        		if(mAdapter.getCount() > 0) 
        			setListAdapter(mAdapter);
        	else {
        		Toast.makeText(getActivity(), R.string.error_server_data_fetching, Toast.LENGTH_LONG).show();
        	}
        }
     }
}
