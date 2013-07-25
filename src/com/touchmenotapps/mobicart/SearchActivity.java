package com.touchmenotapps.mobicart;

import java.util.ArrayList;

import com.touchmenotapps.mobicart.adapters.ShopListAdapter;
import com.touchmenotapps.mobicart.model.ShopData;
import com.touchmenotapps.mobicart.util.SearchDataLoader;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchActivity extends Activity implements LoaderCallbacks<ArrayList<ShopData>> {
	
	private final int LOADER_ID = 1;
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
		mList.setAdapter(mAdapter);
		findViewById(R.id.search_go_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mSearchQuery.getText().toString().trim().length() > 0)
					getLoaderManager().initLoader(LOADER_ID, null, SearchActivity.this).forceLoad();
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
	
	@Override
	public Loader<ArrayList<ShopData>> onCreateLoader(int arg0, Bundle arg1) {
		Loader<ArrayList<ShopData>> loader = new SearchDataLoader(this);
		loader.forceLoad();
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<ShopData>> arg0,
			ArrayList<ShopData> data) {
		if(data.size() > 0) 
			mAdapter.setListData(data);
		else 
			Toast.makeText(SearchActivity.this, R.string.error_server_data_fetching, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<ShopData>> arg0) {
		mAdapter.setListData(new ArrayList<ShopData>());
	}
}
