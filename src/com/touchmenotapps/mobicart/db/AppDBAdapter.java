package com.touchmenotapps.mobicart.db;

import com.touchmenotapps.mobicart.model.ShopData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDBAdapter {

	private final Context context;

	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	private static final String DATABASE_NAME = "MobiCartDB";
	private static final int DATABASE_VERSION = 1;
		
	private static final String DATABASE_TABLE_CART = "_mobiCart";
	private static final String DATABASE_TABLE_CART_ROWID = "_mobiCartRowID";
	private static final String DATABASE_TABLE_CART_ITEM_CODE = "_mobiCartItemCode";
	private static final String DATABASE_TABLE_CART_TITLE = "_mobiCartTitle";
	private static final String DATABASE_TABLE_CART_PRICE = "_mobiCartPrice";
	private static final String DATABASE_TABLE_CART_CURRENCY = "_mobiCartCurrency";
	
	private static final String DATABASE_TABLE_WISHLIST = "_mobiCartWishlist";
	private static final String DATABASE_TABLE_WISHLIST_ROWID = "_mobiCartWishlistRowID";
	private static final String DATABASE_TABLE_WISHLIST_ITEM_CODE = "_mobiCartWishlistItemCode";
	private static final String DATABASE_TABLE_WISHLIST_TITLE = "_mobiCartWishlistTitle";
	private static final String DATABASE_TABLE_WISHLIST_DESCRIPTION = "_mobiCartWishlistDescription";
	private static final String DATABASE_TABLE_WISHLIST_VENDOR = "_mobiCartWishlistVendor";
	private static final String DATABASE_TABLE_WISHLIST_AVAILABLE= "_mobiCartWishlistAvailable";
	private static final String DATABASE_TABLE_WISHLIST_MAX_QUANTITY = "_mobiCartWishlistMaxQuantity";
	private static final String DATABASE_TABLE_WISHLIST_PRICE = "_mobiCartWishlistPrice";
	private static final String DATABASE_TABLE_WISHLIST_CATEGORY = "_mobiCartWishlistCategory";
	private static final String DATABASE_TABLE_WISHLIST_CURRENCY = "_mobiCartWishlistCurrency";
	
	private static final String DATABASE_TABLE_IMAGES = "_mobiCartImages";
	private static final String DATABASE_TABLE_IMAGES_ROWID = "_mobiCartImagesRowID";
	private static final String DATABASE_TABLE_IMAGES_ITEM_CODE = "_mobiCartImagesItemCode";
	private static final String DATABASE_TABLE_IMAGES_URL = "_mobiCartImageURL";
	
	private static final String DATABASE_CREATE_TABLE_CART = "create table _mobiCart (_mobiCartRowID integer primary key autoincrement, _mobiCartItemCode text not null, _mobiCartTitle text not null, "
			+ "_mobiCartPrice float not null, _mobiCartCurrency text not null);";
	
	private static final String DATABASE_CREATE_TABLE_WISHLIST = "create table _mobiCartWishlist (_mobiCartWishlistRowID integer primary key autoincrement, _mobiCartWishlistItemCode text not null, _mobiCartWishlistTitle text not null, "
			+ "_mobiCartWishlistDescription text not null, _mobiCartWishlistVendor text not null, _mobiCartWishlistAvailable text not null, _mobiCartWishlistMaxQuantity integer not null, _mobiCartWishlistPrice float not null, _mobiCartWishlistCurrency text not null, _mobiCartWishlistCategory text not null);";
	
	private static final String DATABASE_CREATE_TABLE_IMAGES = "create table _mobiCartImages (_mobiCartImagesRowID integer primary key autoincrement, _mobiCartImagesItemCode text not null, _mobiCartImageURL text not null);";
	
	public AppDBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	/**
	 * Sqlite DBHelper class to create database and tables and manage it on db
	 * version update
	 */
	private class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			/** Create the database along with the version */
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			/** Create the tables */
			db.execSQL(DATABASE_CREATE_TABLE_CART);
			db.execSQL(DATABASE_CREATE_TABLE_WISHLIST);
			db.execSQL(DATABASE_CREATE_TABLE_IMAGES);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			/** Delete old table on version change */
			db.execSQL("DROP TABLE IF EXISTS titles");
			onCreate(db);
		}
	}
	
	public AppDBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	/**
	 * Closes the database
	 */
	public void close() {
		DBHelper.close();
	}
	
	public long insertCartItem(ShopData data, float price) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(DATABASE_TABLE_CART_ITEM_CODE, data.getItemCode());
		initialValues.put(DATABASE_TABLE_CART_TITLE, data.getTitle());
		initialValues.put(DATABASE_TABLE_CART_PRICE, price);
		initialValues.put(DATABASE_TABLE_CART_CURRENCY, data.getPriceCurrency());
		return db.insert(DATABASE_TABLE_CART, null, initialValues);
	}
	
	public long insertWishlistItem(ShopData data) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(DATABASE_TABLE_WISHLIST_ITEM_CODE, data.getItemCode());
		initialValues.put(DATABASE_TABLE_WISHLIST_TITLE, data.getTitle());
		initialValues.put(DATABASE_TABLE_WISHLIST_DESCRIPTION, data.getDescription());
		initialValues.put(DATABASE_TABLE_WISHLIST_VENDOR, data.getVendor());
		initialValues.put(DATABASE_TABLE_WISHLIST_AVAILABLE, (data.getAvailable()) ? "1" : "0");
		initialValues.put(DATABASE_TABLE_WISHLIST_MAX_QUANTITY, data.getMaxQuantity());
		initialValues.put(DATABASE_TABLE_WISHLIST_CATEGORY, data.getCategory());
		initialValues.put(DATABASE_TABLE_WISHLIST_PRICE, data.getPrice());
		initialValues.put(DATABASE_TABLE_WISHLIST_CURRENCY, data.getPriceCurrency());
		return db.insert(DATABASE_TABLE_WISHLIST, null, initialValues);
	}
	
	public long insertImageURLS(String itemCode, String URL) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(DATABASE_TABLE_IMAGES_ITEM_CODE, itemCode);
		initialValues.put(DATABASE_TABLE_IMAGES_URL, URL);
		return db.insert(DATABASE_TABLE_IMAGES, null, initialValues);
	}
	
	public Cursor getImageURLS(String itemCode) {
		return db.query(DATABASE_TABLE_IMAGES, new String[] {
				DATABASE_TABLE_IMAGES_ROWID, // 0
				DATABASE_TABLE_IMAGES_ITEM_CODE, // 1
				DATABASE_TABLE_IMAGES_URL}, // 2
				DATABASE_TABLE_IMAGES_ITEM_CODE + "='" + itemCode + "'", null, null, null, null);
	}
	
	public Cursor getAllWishlistItems() {
		return db.query(DATABASE_TABLE_WISHLIST,
				new String[] { DATABASE_TABLE_WISHLIST_ROWID, // 0
				DATABASE_TABLE_WISHLIST_ITEM_CODE, // 1
				DATABASE_TABLE_WISHLIST_TITLE, // 2
				DATABASE_TABLE_WISHLIST_DESCRIPTION,// 3
				DATABASE_TABLE_WISHLIST_VENDOR,// 4
				DATABASE_TABLE_WISHLIST_AVAILABLE,// 5
				DATABASE_TABLE_WISHLIST_MAX_QUANTITY,// 6
				DATABASE_TABLE_WISHLIST_CATEGORY,// 7
				DATABASE_TABLE_WISHLIST_PRICE,// 8
				DATABASE_TABLE_WISHLIST_CURRENCY}, // 9
				null, null, null, null,
				null, null);
	}
	
	public Cursor getAllCartItems() {
		return db.query(DATABASE_TABLE_CART,
				new String[] { DATABASE_TABLE_CART_ROWID, // 0
				DATABASE_TABLE_CART_ITEM_CODE, // 1
				DATABASE_TABLE_CART_TITLE, // 2
				DATABASE_TABLE_CART_PRICE,//3
				DATABASE_TABLE_CART_CURRENCY}, // 4
				null, null, null, null,
				null, null);
	}
	
	public void deleteAllCartItems() {
		db.delete(DATABASE_TABLE_CART, null, null);
	}
	
	public void deleteAllWishlistItems() {
		db.delete(DATABASE_TABLE_WISHLIST, null, null);
	}
	
	public boolean deleteCartItem(long rowID) {
		return db.delete(DATABASE_TABLE_CART, DATABASE_TABLE_CART_ROWID
				+ "=" + rowID, null) > 0;
	}
	
	public boolean deleteWishlistItem(long rowID) {
		return db.delete(DATABASE_TABLE_WISHLIST, DATABASE_TABLE_WISHLIST_ROWID
				+ "=" + rowID, null) > 0;
	}
	
	public boolean deleteImageItem(String itemID) {
		return db.delete(DATABASE_TABLE_IMAGES, DATABASE_TABLE_IMAGES_ITEM_CODE
				+ "='" + itemID + "'", null) > 0;
	}
}
