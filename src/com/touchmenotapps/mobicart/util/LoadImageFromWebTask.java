package com.touchmenotapps.mobicart.util;

import java.io.InputStream;
import java.net.URL;

import com.touchmenotapps.mobicart.R;
import com.touchmenotapps.mobicart.interfaces.OnImageDownloadComplete;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class LoadImageFromWebTask extends AsyncTask<String, Void, Drawable> {

	private OnImageDownloadComplete mCallback;
	private Context mContext;
	private NetworkUtil mNetworkUtil;
	
	public LoadImageFromWebTask(Context context, OnImageDownloadComplete callback) {
		mCallback = callback;
		mContext = context;
		mNetworkUtil = new NetworkUtil();
	}
	
	@Override
	protected Drawable doInBackground(String... urls) {
		if(mNetworkUtil.isNetworkAvailable(mContext)) {
			try {
		        InputStream is = (InputStream) new URL(urls[0]).getContent();
		        Drawable d = Drawable.createFromStream(is, "image");
		        return d;
		    } catch (Exception e) {
		        e.printStackTrace();
		        return mContext.getResources().getDrawable(R.drawable.ic_action_alert);
		    }
		} else 
			return mContext.getResources().getDrawable(R.drawable.ic_action_alert);
	}
	
	@Override
	protected void onPostExecute(Drawable result) {
		mCallback.onImageDownloadCompleted(result);
	}

}
