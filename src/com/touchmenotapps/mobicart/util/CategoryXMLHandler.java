package com.touchmenotapps.mobicart.util;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.touchmenotapps.mobicart.model.CategoryData;
import com.touchmenotapps.mobicart.model.ShopData;

public class CategoryXMLHandler extends DefaultHandler {
	
	private final String TAG_RESPONSE = "response";
	private final String TAG_CATEGORY = "category";
	private final String ATTR_NAME = "name";
	private final String TAG_SHOP_ITEM = "shop-item";
	private final String TAG_TITLE = "title";
	private final String TAG_VENDOR = "vendor";
	private final String TAG_DESCRIPTION = "description";
	private final String TAG_PRICE = "price";
	private final String TAG_AVAILABLE = "available";
	private final String TAG_MAX_QUANTITY = "max-quantity";
	private final String TAG_ITEM_CODE = "item-code";
	private final String TAG_ITEM_CATEGORY = "item-category";
	private final String TAG_IMAGE_URL = "images";
	private final String TAG_IMAGE = "image";
	private final String ATTR_CURRENCY = "currency";
	
	String elementValue = null;
	Boolean elementOn = false;
	private String mCurrency;
	private CategoryData mCategoryData;
	private ShopData mShopData;
	private ArrayList<CategoryData> mCategoryItems = new ArrayList<CategoryData>();
	private ArrayList<String> mImages = new ArrayList<String>();
	
	public ArrayList<CategoryData> getData() {
		return mCategoryItems;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		elementOn = true;
		if(localName.equals(TAG_RESPONSE)) 
			mCurrency = attributes.getValue(ATTR_CURRENCY);
		else if(localName.equals(TAG_CATEGORY)) {
			mCategoryData = new CategoryData();
			mCategoryData.setCategory(attributes.getValue(ATTR_NAME));
		} else if(localName.equals(TAG_SHOP_ITEM)) {
			mShopData = new ShopData();
			mShopData.setPriceCurrency(mCurrency);
		} else if(localName.equals(TAG_IMAGE_URL))
			mImages.clear();
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		elementOn = false;     
		if(localName.equals(TAG_TITLE))
			mShopData.setTitle(elementValue);
		else if(localName.equals(TAG_VENDOR))
			mShopData.setVendor(elementValue);
		else if(localName.equals(TAG_DESCRIPTION))
			mShopData.setDescription(elementValue);
		else if(localName.equals(TAG_PRICE))
			mShopData.setPrice(Float.valueOf(elementValue));
		else if(localName.equals(TAG_AVAILABLE))
			mShopData.setAvailable(elementValue);
		else if(localName.equals(TAG_MAX_QUANTITY))
			mShopData.setMaxQuantity(Integer.valueOf(elementValue));
		else if(localName.equals(TAG_ITEM_CODE))
			mShopData.setItemCode(elementValue);
		else if(localName.equals(TAG_ITEM_CATEGORY))
			mShopData.setCategory(elementValue);
		else if(localName.equals(TAG_IMAGE))
			mImages.add(elementValue);
		else if(localName.equals(TAG_IMAGE_URL)) 
			mShopData.setURLS(mImages.toArray(new String[mImages.size()]));
		else if (localName.equals(TAG_SHOP_ITEM))
			mCategoryData.setShopData(mShopData);
		else if (localName.equals(TAG_CATEGORY)) 
			mCategoryItems.add(mCategoryData);
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (elementOn) {
			elementValue = new String(ch, start, length);
			elementOn = false;
		}
	}
}
