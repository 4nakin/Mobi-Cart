package com.touchmenotapps.mobicart;

import com.touchmenotapps.mobicart.fragments.CategoriesFragment;
import com.touchmenotapps.mobicart.fragments.FeaturedFragment;
import com.touchmenotapps.mobicart.fragments.ProductListFragment;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements
		ActionBar.TabListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_action_bar_bg));
		//Tabbed main menu only for phones
		if(findViewById(R.id.container) != null) {			
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);			
			// For each of the sections in the app, add a tab to the action bar.
			for (int i = 0; i < getResources().getStringArray(R.array.tab_names).length; i++) {
				actionBar.addTab(actionBar.newTab()
						.setText(getResources().getStringArray(R.array.tab_names)[i])
						.setTabListener(this));
			}
			//Init with the featured Items page
			actionBar.setSelectedNavigationItem(1);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_search:
			startActivity(new Intent(this, SearchActivity.class));
			break;
		case R.id.menu_main_cart:
			startActivity(new Intent(this, CartActivity.class));
			break;
		case R.id.menu_my_account:
			startActivity(new Intent(this, MyAccountActivity.class));
			break;
		case R.id.menu_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			break;
		}
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		Fragment fragment = null;
		switch(tab.getPosition()) {
		case 0:
			fragment = new CategoriesFragment();
			break;
		case 1:
			fragment = new FeaturedFragment();
			break;
		case 2:
			fragment = new ProductListFragment();
			break;
		}
		getFragmentManager().beginTransaction()
			.replace(R.id.container, fragment).commit();
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
}
