package com.campus.insideout.utils;

import java.util.HashMap;



import android.os.Environment;

/**
 * 配置文件
 * 可用来配置常用的URL 地址
 * @author sea
 *
 */
public class CommonData {
	
	 
	public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().toString();
	public static final String CONFIG = "config";
	public static final String SESSION = "session";
	public final static int DF_MaxGifSize = 90 * 1024;

}
