package com.touchmenotapps.mobicart.util;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.touchmenotapps.mobicart.R;
import com.touchmenotapps.mobicart.model.ShopData;

public class TopDealsDataLoader extends AsyncTaskLoader<ArrayList<ShopData>>{

	private NetworkUtil mNetwokUtil;
	private Context mContext;
	
	public TopDealsDataLoader(Context context) {
		super(context);
		mContext = context;
		mNetwokUtil = new NetworkUtil();
	}

	@Override
	public ArrayList<ShopData> loadInBackground() {
		if(mNetwokUtil.isNetworkAvailable(mContext)) {
			try {
	            //URL url= new URL(urls[0]);
	            SAXParserFactory factory =SAXParserFactory.newInstance();
	            SAXParser parser=factory.newSAXParser();
	            XMLReader xmlreader=parser.getXMLReader();
	            
	            CategoryXMLHandler mResponseHandler=new CategoryXMLHandler();
	            xmlreader.setContentHandler(mResponseHandler);
	            //InputSource is=new InputSource(url.openStream());
	            InputStream is = mContext.getResources().openRawResource(R.raw.dummy_top);
	            xmlreader.parse(new InputSource(is));
	            return mResponseHandler.getData().get(0).getShopData();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new ArrayList<ShopData>();
	        }
		} else
			return new ArrayList<ShopData>();
	}
}
