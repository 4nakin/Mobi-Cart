package com.touchmenotapps.mobicart.util;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

	private SharedPreferences mAppPrefs;
	
	public AppPreferences(Context context) {
		mAppPrefs = context.getSharedPreferences("MobiCartPreferences", 0); // 0 is private
	}
	
	public void setRegistrationComplete() {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putBoolean("MobiCartRegistrationComplete", true);
		edit.commit();
	}
	
	public boolean isRegistrationComplete() {
		return mAppPrefs.getBoolean("MobiCartRegistrationComplete", false);
	}
	
	public void setRegistrationDetials(String firstName, String lastName, 
			String emailID, String phoneNumber, String streetAddress, String city, 
			String state, String zipCode, String country, String password) {
		SharedPreferences.Editor edit = mAppPrefs.edit();
		edit.putString("MobiCartFirstName", firstName);
		edit.putString("MobiCartLastName", lastName);
		edit.putString("MobiCartEmail", emailID);
		edit.putString("MobiCartPhone", phoneNumber);
		edit.putString("MobiCartStreetAddress", streetAddress);
		edit.putString("MobiCartCity", city);
		edit.putString("MobiCartState", state);
		edit.putString("MobiCartZipCode", zipCode);
		edit.putString("MobiCartCountry", country);
		edit.putString("MobiCartPassword", password);
		edit.commit();
	}
	
	public String getRegisteredEmail() {
		return mAppPrefs.getString("MobiCartEmail", null);
	}
}
