package com.campus.insideout.utils;

import com.campus.insideout.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
/**
 * 使用universal-image-loader-1.8.2-SNAPSHOT-with-sources.jar  作为图片管理内存
 * 使用方法可google universal-image-loader-1.8.2-SNAPSHOT-with-sources.jar
 * @author Administrator
 *
 */
public class DisplayImageConfig {
	public static DisplayImageOptions mHeadViewoptionsoptions = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.default_avatar)
		.showImageForEmptyUri(R.drawable.default_avatar)
		.showImageOnFail(R.drawable.default_avatar)
		.cacheInMemory()
		.cacheOnDisc()
		.displayer(new RoundedBitmapDisplayer(15))
		.build();
	public static DisplayImageOptions mSmallHeadViewoptionsoptions = new DisplayImageOptions.Builder()
	.showStubImage(R.drawable.default_avatar)
	.showImageForEmptyUri(R.drawable.default_avatar)
	.showImageOnFail(R.drawable.default_avatar)
	.cacheInMemory()
	.cacheOnDisc()
	.displayer(new RoundedBitmapDisplayer(10))
	.build();
	public static DisplayImageOptions mPhotoOptionsoptions = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.placeholder_130)
		.showImageForEmptyUri(R.drawable.placeholder_130)
		.showImageOnFail(R.drawable.placeholder_130)
		.cacheInMemory()
		.cacheOnDisc()
		.build();
	
}
