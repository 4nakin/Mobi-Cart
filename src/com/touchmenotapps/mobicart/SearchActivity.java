package com.touchmenotapps.mobicart;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.touchmenotapps.mobicart.adapters.ShopListAdapter;
import com.touchmenotapps.mobicart.util.CategoryXMLHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchActivity extends Activity {
	
	private ListView mList;
	private EditText mSearchQuery;
 	private ShopListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_action_bar_bg));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mAdapter = new ShopListAdapter(this);
		mSearchQuery = (EditText) findViewById(R.id.search_edittext);
		mList = (ListView) findViewById(R.id.search_result_list);
		
		findViewById(R.id.search_go_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mSearchQuery.getText().toString().trim().length() > 0)
					new RetreiveFeedTask().execute("");
				else 
					Toast.makeText(SearchActivity.this, R.string.msg_enter_search, Toast.LENGTH_LONG).show();
			}
		});
		
		mList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
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
                InputStream is = getResources().openRawResource(R.raw.dummy_search);
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
        		if(mAdapter.getCount() > 0) {
        			mList.setAdapter(mAdapter);
        		}
        	else {
        		Toast.makeText(SearchActivity.this, R.string.error_server_data_fetching, Toast.LENGTH_LONG).show();
        	}
        }
     }
}
