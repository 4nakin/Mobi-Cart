package com.touchmenotapps.mobicart;

import com.touchmenotapps.mobicart.fragments.MyAccountCategoriesFragment;
import com.touchmenotapps.mobicart.fragments.UserAccountFragment;
import com.touchmenotapps.mobicart.fragments.WishlistFragment;
import com.touchmenotapps.mobicart.interfaces.OnCategorySelectedListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

public class MyAccountActivity extends FragmentActivity implements OnCategorySelectedListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_account);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_action_bar_bg));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		if(findViewById(R.id.my_account_single_pane_container) != null)
			getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.my_account_single_pane_container, new MyAccountCategoriesFragment())
				.commit();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if(findViewById(R.id.my_account_single_pane_container) != null)
				if(getSupportFragmentManager().getBackStackEntryCount() > 0) 
					getSupportFragmentManager().popBackStack();
				else
					finish();
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

	@Override
	public void onCategorySelected(int which) {
		switch(which) {
		case 0:
			if(findViewById(R.id.my_account_single_pane_container) != null)
				getSupportFragmentManager()
					.beginTransaction()
					.addToBackStack(null)
					.replace(R.id.my_account_single_pane_container, new UserAccountFragment())
					.commit();
			else
				getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.my_account_details_container, new UserAccountFragment())
					.commit();
			break;
		case 1:
			if(findViewById(R.id.my_account_single_pane_container) != null)
				getSupportFragmentManager()
					.beginTransaction()
					.addToBackStack(null)
					.replace(R.id.my_account_single_pane_container, new WishlistFragment())
					.commit();
			else
				getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.my_account_details_container, new WishlistFragment())
					.commit();
			break;
		case 2:
			Toast.makeText(this, R.string.feature_not_available, Toast.LENGTH_SHORT).show();
			break;
		case 3:
			if(findViewById(R.id.my_account_single_pane_container) != null)
				getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.my_account_single_pane_container, new MyAccountHelpFragment())
					.commit();
			else
				getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.my_account_details_container, new MyAccountHelpFragment())
					.commit();
			break;
		}
	}
}
