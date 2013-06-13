package com.touchmenotapps.mobicart.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.touchmenotapps.mobicart.fragments.CategoriesFragment;
import com.touchmenotapps.mobicart.fragments.FeaturedFragment;
import com.touchmenotapps.mobicart.fragments.ProductListFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

	private final int TOTAL_NUM_PAGES = 3;
	
	public SectionsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		switch(position) {
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
		return fragment;
	}

	@Override
	public int getCount() {
		return TOTAL_NUM_PAGES;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "Categories";
		case 1:
			return "Featured";
		case 2:
			return "Top Deals";
		}
		return null;
	}
}
