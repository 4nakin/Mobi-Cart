package com.touchmenotapps.mobicart.model;

import java.util.ArrayList;

public class CategoryData {

	private String mCategory;
	private ArrayList<ShopData> mShopData = new ArrayList<ShopData>();
	
	public CategoryData() { }
		
	/**
	 * @param mCategory
	 * @param mShopData
	 */
	public CategoryData(String mCategory, ArrayList<ShopData> mShopData) {
		super();
		this.mCategory = mCategory;
		this.mShopData = mShopData;
	}

	/**
	 * @return the mCategory
	 */
	public String getCategory() {
		return mCategory;
	}
	
	/**
	 * @param mCategory the mCategory to set
	 */
	public void setCategory(String mCategory) {
		this.mCategory = mCategory;
	}
	
	/**
	 * @return the mShopData
	 */
	public ArrayList<ShopData> getShopData() {
		return mShopData;
	}
	
	/**
	 * @param mShopData the mShopData to set
	 */
	public void setShopData(ShopData mShopData) {
		this.mShopData.add(mShopData);
	}
}
