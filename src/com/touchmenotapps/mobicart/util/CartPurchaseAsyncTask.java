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

import com.touchmenotapps.mobicart.R;
import com.touchmenotapps.mobicart.interfaces.OnPurchaseSuccessListener;
import com.touchmenotapps.mobicart.model.ShopData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class CartPurchaseAsyncTask extends AsyncTask<ShopData, Void, Integer>{
	private final int ERROR_NO_CONNECTION = 1;
	private final int ERROR_EXCEPTION = ERROR_NO_CONNECTION + 1;
	private final int SUCCESS = ERROR_NO_CONNECTION + 2;
	private final String URL = "http://appztiger.com/demo/magento/responed.php";
	
	private ProgressDialog mProgressDialog;
	private NetworkUtil mNetworkUtils;
	private Context mContext;
	private OnPurchaseSuccessListener mCallback;
	private AppPreferences mPrefs;
	
	public CartPurchaseAsyncTask(Context context, OnPurchaseSuccessListener callback) {
		mContext = context;
		mCallback = callback;
		mPrefs = new AppPreferences(context);
		mNetworkUtils = new NetworkUtil();
		mProgressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setMessage(context.getText(R.string.please_wait));
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(ShopData... params) {
		if(mNetworkUtils.isNetworkAvailable(mContext)) {
			try {
				ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
				/** Add the list of items to be posted **/
				data.add(new BasicNameValuePair("email", mPrefs.getRegisteredEmail()));
				for(int count = 0; count < params.length; count++) {
					data.add(new BasicNameValuePair("code",params[count].getItemCode()));
					data.add(new BasicNameValuePair("qty",String.valueOf(params[count].getMaxQuantity())));
				}
				HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost(URL);		        
		        httppost.setEntity(new UrlEncodedFormEntity(data));
		        HttpResponse response = httpclient.execute(httppost);
		        String response_string = EntityUtils.toString(response.getEntity());
		        Log.i("postData", response_string);
		        if(response_string.contains("order-id"))
		        	return SUCCESS;
		        else
		        	return ERROR_EXCEPTION;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR_EXCEPTION;
			}
		} else
			return ERROR_NO_CONNECTION;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		mProgressDialog.dismiss();
		switch (result) {
		case ERROR_NO_CONNECTION:
			mNetworkUtils.showNetworkErrorDialog(mContext);
			break;
		case ERROR_EXCEPTION:
			Toast.makeText(mContext, R.string.error_upload_purchase_item, Toast.LENGTH_LONG).show();
			break;
		case SUCCESS:
			mCallback.onPurchaseSuccess();
			break;
		}
	}
	
}
