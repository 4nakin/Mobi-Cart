package com.touchmenotapps.mobicart.model;

public class ShopData {

	private String mItemCode;
	private long mDBRowNum = -1;
	private boolean isWishlist = false;
	private String mTitle;
	private String mDescription;
	private String mVendor;
	private boolean mAvailable;
	private int mMaxQuantity;
	private float mPrice;
	private String mCategory;
	private String mPriceCurrency;
	private String[] mURLS;
			
	/**
	 * @return the mItemCode
	 */
	public String getItemCode() {
		return mItemCode;
	}

	/**
	 * @param mItemCode the mItemCode to set
	 */
	public void setItemCode(String mItemCode) {
		this.mItemCode = mItemCode;
	}

	/**
	 * @return the mDBRowNum
	 */
	public long getDBRowNum() {
		return mDBRowNum;
	}

	/**
	 * @param mDBRowNum the mDBRowNum to set
	 */
	public void setDBRowNum(long mDBRowNum) {
		this.mDBRowNum = mDBRowNum;
	}

	/**
	 * @return the isWishlist
	 */
	public boolean isWishlist() {
		return isWishlist;
	}

	/**
	 * @param isWishlist the isWishlist to set
	 */
	public void setWishlist(boolean isWishlist) {
		this.isWishlist = isWishlist;
	}

	/**
	 * @return the mTitle
	 */
	public String getTitle() {
		return mTitle;
	}
	
	/**
	 * @param mTitle the mTitle to set
	 */
	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	
	/**
	 * @return the mDescription
	 */
	public String getDescription() {
		return mDescription;
	}
	
	/**
	 * @param mDescription the mDescription to set
	 */
	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}
	
	/**
	 * @return the mPrice
	 */
	public float getPrice() {
		return mPrice;
	}
	
	/**
	 * @param mPrice the mPrice to set
	 */
	public void setPrice(float mPrice) {
		this.mPrice = mPrice;
	}
	
	/**
	 * @return the mPriceCurrency
	 */
	public String getPriceCurrency() {
		return mPriceCurrency;
	}
	
	/**
	 * @param mPriceCurrency the mPriceCurrency to set
	 */
	public void setPriceCurrency(String mPriceCurrency) {
		this.mPriceCurrency = mPriceCurrency;
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
	 * @return the mVendor
	 */
	public String getVendor() {
		return mVendor;
	}

	/**
	 * @param mVendor the mVendor to set
	 */
	public void setVendor(String mVendor) {
		this.mVendor = mVendor;
	}

	/**
	 * @return the mAvailable
	 */
	public boolean getAvailable() {
		return mAvailable;
	}

	/**
	 * @param mAvailable the mAvailable to set
	 */
	public void setAvailable(String mAvailable) {
		if(Integer.valueOf(mAvailable) == 1)
			this.mAvailable = true;
		else
			this.mAvailable = false;
	}

	/**
	 * @return the mMaxQuantity
	 */
	public int getMaxQuantity() {
		return mMaxQuantity;
	}

	/**
	 * @param mMaxQuantity the mMaxQuantity to set
	 */
	public void setMaxQuantity(int mMaxQuantity) {
		this.mMaxQuantity = mMaxQuantity;
	}

	/**
	 * @return the mURLS
	 */
	public String[] getURLS() {
		return mURLS;
	}

	/**
	 * @param mURLS the mURLS to set
	 */
	public void setURLS(String[] mURLS) {
		this.mURLS = mURLS;
	}
}
