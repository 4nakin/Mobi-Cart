package com.touchmenotapps.mobicart.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageHandlerUtil {

	public  Bitmap getRoundedCornerImage(Context context, int resID) {
        int radius = 0;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID);
        if(bitmap.getWidth() > bitmap.getHeight()) 
        	radius = bitmap.getHeight()/2;
        else 
        	radius = bitmap.getWidth()/2;
        Bitmap output = Bitmap.createBitmap(radius * 2, radius * 2, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        final Rect rect = new Rect(0, 0, radius * 2, radius * 2);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(radius, radius, radius - 15.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

	@SuppressWarnings("deprecation")
	public Drawable getRoundedCornerBg(Context context, int resID) {
        int radius = 0;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID);
        if(bitmap.getWidth() > bitmap.getHeight()) 
        	radius = bitmap.getHeight()/2;
        else 
        	radius = bitmap.getWidth()/2;
        Bitmap output = Bitmap.createBitmap(radius * 2, radius * 2, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(context.getResources().getColor(android.R.color.white));
        canvas.drawCircle(radius, radius, radius - 10.0f, paint);
        paint.setShadowLayer((float) 3 * context.getResources().getDisplayMetrics().density, 0.0f, 0.0f, Color.BLACK); 
        canvas.drawCircle(radius, radius, radius - 8.0f, paint);
        return new BitmapDrawable(output);
    }
}
