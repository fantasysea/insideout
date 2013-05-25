package com.campus.insideout.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 常用的一些操作
 * @author Administrator
 *
 */
public class Utils {


	public static void tolMessage(Context context, String message) {

		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(1000);
		toast.setText(message);
		toast.show();
	}

	public static void toastMessage(Context context, int resId) {
		Toast toast = new Toast(context);
//		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(1000);
		toast.setText(resId);
		toast.show();
	}


	public static String url2Path(String url) {
		return url.replaceAll(":", "").replaceAll("/", "").replaceAll("\\.", "");
	}

	/**
	 * TODO 按图片大小(字节大小)缩放图片
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap fitSizeImg(String path) {
		if (path == null || path.length() < 1)
			return null;
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		Bitmap resizeBmp = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 数字越大读出的图片占用的heap越小 不然总是溢出
		if (file.length() < 102400) { // 100k
			opts.inSampleSize = 1;
		} else if (file.length() < 204800) { // 2000k
			opts.inSampleSize = 2;
		} else if (file.length() < 307200) { // 200-300k
			opts.inSampleSize = 4;
		} else if (file.length() < 819200) { // 300-800k
			opts.inSampleSize = 6;
		} else if (file.length() < 1048576) { // 800-1024k
			opts.inSampleSize = 8;
		} else {
			opts.inSampleSize = 10;
		}
		resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
		return resizeBmp;
	}

	public static byte[] bitmap2byte(String path) {
		if (path == null || path.length() < 1)
			return null;
		File file = new File(path);
		Bitmap resizeBmp = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 数字越大读出的图片占用的heap越小 不然总是溢出
		if (file.length() < 20480) { // 0-20k
			opts.inSampleSize = 1;
		} else if (file.length() < 51200) { // 20-50k
			opts.inSampleSize = 2;
		} else if (file.length() < 307200) { // 50-300k
			opts.inSampleSize = 4;
		} else if (file.length() < 819200) { // 300-800k
			opts.inSampleSize = 6;
		} else if (file.length() < 1048576) { // 800-1024k
			opts.inSampleSize = 8;
		} else {
			opts.inSampleSize = 10;
		}
		resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
		ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
		resizeBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
		return baos1.toByteArray();
	}

	public static String getLocalIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			ILog.e(ex.toString());
		}
		return null;
	}

	/**
	 * 判断网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean ifNetWorkIsOK(Context context) {

		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();

		if (info == null || !info.isAvailable()) {

			return false;

		}
		if (info.isRoaming()) {

			return true;

		}
		return true;

	}

	/**
	 * 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			ILog.e(e.getMessage());
		}
		return versionName;
	}


	/**
	 * 使用include@layout的方式引用edit_box输入框进行界面的布局
	 * 
	 * @param et
	 * @param btn
	 * @param hint
	 *            引用的是res中string定义的提示语
	 */
	public static void setEditTextStyle2(final EditText et, final Button delete, final int hint) {
		et.setHint(hint);
		et.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					et.setHint("");
					// v.setBackgroundResource(R.drawable.lodin_etidtext);
					if (et.getText().toString().trim().length() > 0) {
						delete.setVisibility(View.VISIBLE);
					}
				} else {
					et.setHint(hint);
					// v.setBackgroundResource(R.drawable.lodin_edittext_2);
					delete.setVisibility(View.GONE);
				}
			}
		});

		et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String str = s.toString().trim();
				if (str.length() == 0) {
					delete.setVisibility(View.GONE);
				} else {
					delete.setVisibility(View.VISIBLE);

				}
			}
		});
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et.setText("");
			}
		});
	}
	/**
	 *  为一个Edittext提供填写之后可以一次性删除功能
	 * @param et
	 * @param delete
	 */
	public static void setEditTextStyle(final EditText et, final View delete) {
		et.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if (et.getText().toString().trim().length() > 0) {
						delete.setVisibility(View.VISIBLE);
					}
				} else {
					delete.setVisibility(View.GONE);
				}
			}
		});

		et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String str = s.toString().trim();
				if (str.length() == 0) {
					delete.setVisibility(View.GONE);
				} else {
					delete.setVisibility(View.VISIBLE);

				}
			}
		});
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et.setText("");
			}
		});
	}

	public static boolean isApp(Context context, String name) {
		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> list = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
		for (PackageInfo packageInfo : list) {
			if (name.equals(packageInfo.packageName))
				return true;

		}
		return false;
	}


	public static Bitmap getAssetsBitmap(Context context, String path) {
		InputStream is;
		Bitmap bitmap = null;
		try {
			AssetManager assetManager = context.getAssets();
			is = assetManager.open(path);
			bitmap = BitmapFactory.decodeStream(is);
		} catch (Exception e) {
		}
		return bitmap;

	}

	/**
	 * 分割字符 从给定字符到结尾
	 * 
	 * @param str
	 * @param constraint
	 */
	public static String subStr(String str, String constraint) {
		return String.valueOf(str.subSequence(str.lastIndexOf(constraint) + 1, str.length()));
	}

	/**
	 * 分割字符 从开头到给定字符
	 * 
	 * @param str
	 * @param constraint
	 */
	public static String subStr2(String str, String constraint) {
		return String.valueOf(str.subSequence(0, str.indexOf(constraint)));
	}


	
}
