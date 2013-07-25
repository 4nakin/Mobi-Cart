package com.touchmenotapps.mobicart.util;

import java.util.ArrayList;
import java.net.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.touchmenotapps.mobicart.model.ShopData;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class FeaturedDataLoader extends AsyncTaskLoader<ArrayList<ShopData>> {

	private final String URL = "http://appztiger.com/demo/magento/category_featured.php";
	private NetworkUtil mNetwokUtil;
	private Context mContext;
	
	public FeaturedDataLoader(Context context) {
		super(context);
		mContext = context;
		mNetwokUtil = new NetworkUtil();
	}

	@Override
	public ArrayList<ShopData> loadInBackground() {
		if(mNetwokUtil.isNetworkAvailable(mContext)) {
			try {
	            URL url= new URL(URL);
	            SAXParserFactory factory =SAXParserFactory.newInstance();
	            SAXParser parser=factory.newSAXParser();
	            XMLReader xmlreader=parser.getXMLReader();
	            
	            CategoryXMLHandler mResponseHandler=new CategoryXMLHandler();
	            xmlreader.setContentHandler(mResponseHandler);
	            InputSource is=new InputSource(url.openStream());
	            xmlreader.parse(is);
	            //InputStream is = mContext.getResources().openRawResource(R.raw.dummy_featured);
	            //xmlreader.parse(new InputSource(is));
	            return mResponseHandler.getData().get(0).getShopData();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new ArrayList<ShopData>();
	        }
		} else
			return new ArrayList<ShopData>();
	}

}
