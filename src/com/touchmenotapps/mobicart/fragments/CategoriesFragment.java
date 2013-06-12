package com.touchmenotapps.mobicart.fragments;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.touchmenotapps.mobicart.R;
import com.touchmenotapps.mobicart.ShoppingListActivity;
import com.touchmenotapps.mobicart.adapters.CategoriesListAdapter;
import com.touchmenotapps.mobicart.model.ShopData;
import com.touchmenotapps.mobicart.util.CategoryXMLHandler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class CategoriesFragment extends ListFragment {

	private CategoriesListAdapter mAdapter;
	public static ArrayList<ShopData> mShoppingData = new ArrayList<ShopData>();
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAdapter = new CategoriesListAdapter(getActivity());
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
			Intent intent = new Intent(getActivity(), ShoppingListActivity.class);
			intent.putExtra("categoryName", mAdapter.getItem(position).getCategory());
			mShoppingData = mAdapter.getItem(position).getShopData();
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
                InputStream is = getResources().openRawResource(R.raw.dummy);
                xmlreader.parse(new InputSource(is));
                mAdapter.setListData(mResponseHandler.getData());
                return "";
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String feed) {
        	if(feed != null) 
        		if(mAdapter.getCount() > 0) {
        			setListAdapter(mAdapter);
        		}
        	else {
        		Toast.makeText(getActivity(), R.string.error_server_data_fetching, Toast.LENGTH_LONG).show();
        	}
        }
     }
}
