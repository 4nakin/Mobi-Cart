package com.touchmenotapps.mobicart;

import java.util.ArrayList;

import com.touchmenotapps.mobicart.adapters.CategoriesListAdapter;
import com.touchmenotapps.mobicart.fragments.UserAccountFragment;
import com.touchmenotapps.mobicart.fragments.WishlistFragment;
import com.touchmenotapps.mobicart.model.CategoryData;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.Toast;

public class MyAccountActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_account);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_action_bar_bg));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.my_account_single_pane_container, new MyAccountCategoriesFragment())
			.commit();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if(getSupportFragmentManager().getBackStackEntryCount() > 0) 
				getSupportFragmentManager().popBackStack();
			else
				finish();
			break;
		}
		return true;
	}
	
	public static class MyAccountHelpFragment extends Fragment {
		private final String URL = "file:///android_asset/help.html";
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			WebView mWebView = new WebView(getActivity());
			mWebView.getSettings().setSupportZoom(false);
			mWebView.loadUrl(URL);
			return mWebView;
		}
	}
	
	public static class MyAccountCategoriesFragment extends ListFragment {
		private CategoriesListAdapter mAdapter;
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			mAdapter = new CategoriesListAdapter(getActivity());
			ArrayList<CategoryData> mData = new ArrayList<CategoryData>();
			for(String data : getResources().getStringArray(R.array.my_account_list)) 
				mData.add(new CategoryData(data, null));
			mAdapter.setListData(mData);
			setListAdapter(mAdapter);
		}
		
		@Override
		public void onResume() {
			super.onResume();
			getListView().setPadding(18, 0, 18, 0);
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			super.onListItemClick(l, v, position, id);
			switch(position) {
			case 0:
				getFragmentManager()
					.beginTransaction()
					.addToBackStack(null)
					.replace(R.id.my_account_single_pane_container, new UserAccountFragment())
					.commit();
				break;
			case 1:
				getFragmentManager()
					.beginTransaction()
					.addToBackStack(null)
					.replace(R.id.my_account_single_pane_container, new WishlistFragment())
					.commit();
				break;
			case 2:
				Toast.makeText(v.getContext(), R.string.feature_not_available, Toast.LENGTH_SHORT).show();
				break;
			case 3:
				getFragmentManager()
					.beginTransaction()
					.addToBackStack(null)
					.replace(R.id.my_account_single_pane_container, new MyAccountHelpFragment())
					.commit();
				break;
			}
		}
	}
}
