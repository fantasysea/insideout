package com.campus.insideout.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import android.os.Environment;
import android.util.Log;
/**
 * 打印log都请使用这个类
 * @author Administrator
 *
 */
public class ILog {

	private final static String tag = "moxian";

	/**
	 * 是否启用debug模式，true为有日志输出
	 */
	private static boolean isDebug = false;

	public static boolean isDebug() {
		return isDebug;
	}

	private static void setDebug(boolean isDebug) {
		ILog.isDebug = isDebug;
	}

	public static void v(String str) {
		if (isDebug)
			Log.v(tag, str);
	}

	public static void i(String str) {

		if (isDebug)
			Log.i(tag, "i:" + str);
	}

	public static void w(String str) {

		if (isDebug)
			Log.w(tag, "w:" + str);
	}

	public static void e(String str) {

		if (isDebug)
			Log.e(tag, "err:" + str);
	}

	public static void d(String str) {

		if (isDebug)
			Log.d(tag, "d:" + str);
	}

	public static void logFile(String result) {
		if (isDebug) {
			try {
				
				int maxLogSize = 1000;
				File file  = new File(
						Environment.getExternalStorageDirectory()
						+ File.separator + "log.txt");
				if (file.exists()) {
					file.delete();
				}
				FileWriter fw = new FileWriter(file);
				BufferedWriter bufWriter = new BufferedWriter(fw);  
	           
				for (int i = 0; i <= result.length() / maxLogSize; i++) {
					int start = i * maxLogSize;
					int end = (i + 1) * maxLogSize;
					end = end > result.length() ? result.length() : end;
					bufWriter.write(result.substring(start, end));
				}
				 bufWriter.close();  
		            fw.close();  
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				
			}
		}
	}
	
	public static void outException(Exception e){
		if (isDebug) {
			e.printStackTrace();
		}
	}
}
