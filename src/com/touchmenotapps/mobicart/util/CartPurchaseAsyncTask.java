package com.touchmenotapps.mobicart.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.touchmenotapps.mobicart.R;
import com.touchmenotapps.mobicart.interfaces.OnPurchaseSuccessListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class CartPurchaseAsyncTask extends AsyncTask<String, Void, Integer>{
	private final int ERROR_NO_CONNECTION = 1;
	private final int ERROR_EXCEPTION = ERROR_NO_CONNECTION + 1;
	private final int SUCCESS = ERROR_NO_CONNECTION + 2;
	private final String URL = "http://appztiger.com/demo/magento/request.php";
	
	private ProgressDialog mProgressDialog;
	private NetworkUtil mNetworkUtils;
	private Context mContext;
	private OnPurchaseSuccessListener mCallback;
	
	public CartPurchaseAsyncTask(Context context, OnPurchaseSuccessListener callback) {
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
	protected Integer doInBackground(String... params) {
		if(mNetworkUtils.isNetworkAvailable(mContext)) {
			HttpPost httpPost = new HttpPost(URL);
		    StringEntity entity;
		    String response_string = null;
			try {
				entity = new StringEntity(params[0], HTTP.UTF_8);
		        httpPost.setHeader("Content-Type","text/xml;charset=UTF-8");
		        httpPost.setEntity(entity);
		        HttpClient client = new DefaultHttpClient();
		        HttpResponse response = client.execute(httpPost);
		        response_string = EntityUtils.toString(response.getEntity());
		        if(response_string.equals("SUCCESS"))
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
