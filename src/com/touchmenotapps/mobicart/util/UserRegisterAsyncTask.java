package com.touchmenotapps.mobicart.util;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.touchmenotapps.mobicart.interfaces.OnRegisterSuccessListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class UserRegisterAsyncTask extends AsyncTask<String, Void, Integer>{

	private static final int SUCCESS = 1;
	private static final int ERROR = 2;
	private static final int  FAILED = 3;
	private static final int NO_NETWORK = 4;
	private final String URL = "http://appztiger.com/demo/magento/register.php";
	
	private ProgressDialog mDialog;
	private OnRegisterSuccessListener mCallback;
	private NetworkUtil mNetworkUtils;
	private Context mContext;
	
	public UserRegisterAsyncTask(Context context, OnRegisterSuccessListener callback) {
		mCallback = callback;
		mContext = context;
		mNetworkUtils = new NetworkUtil();
		mDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
		mDialog.setMessage("Please Wait...");
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mDialog.show();
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		if(mNetworkUtils.isNetworkAvailable(mContext)) 
			try {
				ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
				/** Add the list of items to be posted **/
				data.add(new BasicNameValuePair("firstname", params[0]));
				data.add(new BasicNameValuePair("lastname", params[1]));
				data.add(new BasicNameValuePair("email", params[2]));
				data.add(new BasicNameValuePair("telephone", params[3]));
				data.add(new BasicNameValuePair("address", params[4]));
				data.add(new BasicNameValuePair("city", params[5]));
				data.add(new BasicNameValuePair("state", params[6]));
				data.add(new BasicNameValuePair("zipcode", params[7]));
				data.add(new BasicNameValuePair("country", params[8]));
				data.add(new BasicNameValuePair("password", params[9]));
				data.add(new BasicNameValuePair("confirmpassword", params[10]));
				HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost(URL);		        
		        httppost.setEntity(new UrlEncodedFormEntity(data));
		        HttpResponse response = httpclient.execute(httppost);
		        String response_string = EntityUtils.toString(response.getEntity());
		        Log.i("postData", response_string);
		        if(response_string.contains("register-error"))
		        	if(response_string.contains("Email Already Exists"))
		        			return SUCCESS;
		        	else
		        		return FAILED;
		        else
		        	return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		else
			return NO_NETWORK;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		mDialog.dismiss();
		switch(result) {
		case SUCCESS:
			mCallback.onRegistrationCompleteListener();
			break;
		case ERROR:
			Toast.makeText(mContext, "An error occurred during the registration process. Please try again.", Toast.LENGTH_LONG).show();
			break;
		case FAILED:
			Toast.makeText(mContext, "Registration failed. Please try again.", Toast.LENGTH_LONG).show();
			break;
		case NO_NETWORK:
			mNetworkUtils.showNetworkErrorDialog(mContext);
			break;
		}
	}
}
