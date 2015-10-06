package com.yxhuang.imageloader;

import java.io.FileDescriptor;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 *  图片压缩类
 * @author admin
 *
 */
public class ImageResizer {
	private static final String TAG = "imageResizer";

	public ImageResizer() {
	}
	
	/**
	 *  压缩图片
	 * @param resources  图片源
	 * @param resId         图片的id
	 * @param reqWidth   要求的宽度
	 * @param reqHeight   要求的高度
	 * @return         bitmap
	 */
	public Bitmap decodeSampledBitmapFromResource(Resources resources, int resId, int reqWidth, int reqHeight){
		
		// 1、
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 当inJustDecodeBounds= true 时，BitmapFactory只会解析图片原始宽高信息，并不会真正地加载图片。
		options.inJustDecodeBounds = true;            
		
		BitmapFactory.decodeResource(resources, resId, options);
		
		// 2 计算压缩率
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		
		//
		options.inJustDecodeBounds = true;
		return BitmapFactory.decodeResource(resources, resId, options);
	}
	
	
	public Bitmap decodeSampleBitmapFromFileDescriptor(FileDescriptor fd, int reqWidth, int reqHeight){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFileDescriptor(fd, null, options);
		
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFileDescriptor(fd, null, options);
		
	}
	
	// 计算图片的压缩率
	private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
		if (reqWidth == 0 || reqHeight == 0)  return 1;
		
		int height = options.outHeight;
		int width = options.outWidth;
		int inSampleSize = 1;
		
		if (height > reqHeight || width > reqWidth) {
			int halfHeight = height / 2;
			int halfWidth = width / 2;
			
			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width
			while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
				inSampleSize *= 2;
			}
		}
		
		Log.i(TAG, "inSampleSize: " + inSampleSize);
		return inSampleSize;
	}
	

}
