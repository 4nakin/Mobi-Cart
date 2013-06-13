package com.touchmenotapps.mobicart.util;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.touchmenotapps.mobicart.R;
import com.touchmenotapps.mobicart.model.CategoryData;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class StoreDataLoader extends AsyncTaskLoader<ArrayList<CategoryData>>{

	private NetworkUtil mNetwokUtil;
	private Context mContext;
	
	public StoreDataLoader(Context context) {
		super(context);
		mContext = context;
		mNetwokUtil = new NetworkUtil();
	}
	
	@Override
	public ArrayList<CategoryData> loadInBackground() {
		if(mNetwokUtil.isNetworkAvailable(mContext)) {
			try {
	            //URL url= new URL(urls[0]);
	            SAXParserFactory factory =SAXParserFactory.newInstance();
	            SAXParser parser=factory.newSAXParser();
	            XMLReader xmlreader=parser.getXMLReader();
	            
	            CategoryXMLHandler mResponseHandler=new CategoryXMLHandler();
	            xmlreader.setContentHandler(mResponseHandler);
	            //InputSource is=new InputSource(url.openStream());
	            InputStream is = mContext.getResources().openRawResource(R.raw.dummy);
	            xmlreader.parse(new InputSource(is));
	            return mResponseHandler.getData();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new ArrayList<CategoryData>();
	        }
		} else 
			return new ArrayList<CategoryData>();
	}
}
