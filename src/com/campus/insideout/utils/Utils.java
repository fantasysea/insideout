package com.campus.insideout.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yunxun.moxian.AppManager;
import com.yunxun.moxian.MoXianApp;
import com.yunxun.moxian.R;

public class Utils {
	/**
	 * 定义�?��的错�?
	 * 
	 * @author sea
	 * 
	 */
	private static final long DAY = 1000 * 60 * 60 * 24;
	private static final long HOUR = 1000 * 60 * 60;
	private static final long MINUTE = 1000 * 60;
	private static final long SECOND = 1000 * 1;

	public final class ERROR {
		public static final String WRONG_PASSWORD = "WRONG_PASSWORD";

		public static final String USERNAME_TAKEN = "USERNAME_TAKEN";

		public static final String BAD_USERNAME = "BAD_USERNAME";

		public static final String BAD_EMAILS = "BAD_EMAILS";

		public static final String TOO_SHORT = "TOO_SHORT";

		public static final String USER_REQUIRED = "USER_REQUIRED";

		public static final String USER_UNEXIST = "1";
		public static final String WEONG_PASSWORD = "2";
		public static final String USER_FORBIDDEN_LOGIN = "3";
		public static final String WRONG_PARAMETER = "4";
		public static final String BED_EMAIL = "5";
	}

	/**
	 * 显示消息
	 * 
	 * @param message
	 */
	/**
	 * 时间替换
	 * 
	 * @param context
	 * @param message
	 */
	public static String im_Date(MoXianApp mApp, String date) {
		String is_yesterday, is_today;
		String is_month;
		if (mApp.date_month < 10)
			is_month = "0" + mApp.date_month + mApp.cn_month;
		else
			is_month = mApp.date_month + mApp.cn_month;
		if (mApp.date_day < 10)
			is_today = is_month + "0" + mApp.date_day + mApp.cn_day;
		else
			is_today = is_month + mApp.date_day + mApp.cn_day;
		if (mApp.date_day < 11)
			is_yesterday = is_month + "0" + (mApp.date_day - 1) + mApp.cn_day;
		else
			is_yesterday = is_month + (mApp.date_day - 1) + mApp.cn_day;
		date = date.replace(is_today, mApp.im_today).replace(is_yesterday, mApp.im_yesterday);

		is_month = "";
		if (mApp.date_month < 10)
			is_month = "0" + mApp.date_month + "-";
		else
			is_month = mApp.date_month + "-";
		if (mApp.date_day < 10)
			is_today = is_month + "0" + mApp.date_day + "-";
		else
			is_today = is_month + mApp.date_day + "-";
		if (mApp.date_day < 11)
			is_yesterday = is_month + "0" + (mApp.date_day - 1) + "-";
		else
			is_yesterday = is_month + (mApp.date_day - 1) + "-";
		date = date.replace(is_today, mApp.im_today).replace(is_yesterday, mApp.im_yesterday);

		date = date.replace(mApp.cn_am, mApp.im_am).replace("AM", mApp.im_am).replace(mApp.cn_pm, mApp.im_pm).replace("PM", mApp.im_pm).replace(mApp.cn_noon, mApp.im_noon)
				.replace("noon", mApp.im_noon);

		if (mApp.mLanguage == 0)
			date = date.replace(mApp.cn_month, mApp.im_month).replace(mApp.cn_day, mApp.im_day);
		else
			date = date.replace("-", mApp.im_month).replace("-", mApp.im_day);
		return date;
	}

	public static void tolMessage(Context context, String message) {

		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		View view = LayoutInflater.from(context).inflate(R.layout.toast_view, null);
		((TextView) view).setText(message);
		toast.setDuration(1000);
		toast.setView(view);
		toast.show();
	}

	public static void toastMessage(Context context, int resId) {
		View view = LayoutInflater.from(context).inflate(R.layout.toast_view, null);
		((TextView) view).setText(resId);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(1000);
		toast.setView(view);
		toast.show();
	}

	public static void toastImg(Context context, int resId) {
		ImageView img = new ImageView(context);
		img.setImageResource(resId);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(1000);
		toast.setView(img);
		toast.show();
	}

	public static String url2Path(String url) {
		return url.replaceAll(":", "").replaceAll("/", "").replaceAll("\\.", "");
	}

	/**
	 * TODO 按图片大�?字节大小)缩放图片
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

	// public static String getLocalIp(Context context) {
	// final ConnectivityManager connMgr = (ConnectivityManager)
	// context.getSystemService(Context.CONNECTIVITY_SERVICE);
	// final android.net.NetworkInfo wifi =
	// connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	// final android.net.NetworkInfo mobile =
	// connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	//
	// if (wifi.isAvailable()) {
	// WifiManager wifimanage = (WifiManager)
	// context.getSystemService(Context.WIFI_SERVICE);// 获取WifiManager
	// // �?��wifi是否�?��
	// if (!wifimanage.isWifiEnabled()) {
	// }
	// WifiInfo wifiinfo = wifimanage.getConnectionInfo();
	// // 将获取的int转为真正的ip地址,参�?的网上的，修改了�?
	// int i = wifiinfo.getIpAddress();
	// return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) +
	// "." + ((i >> 24) & 0xFF);
	// } else if (mobile.isAvailable()) {
	// try {
	// for (Enumeration<NetworkInterface> en =
	// NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	// NetworkInterface intf = en.nextElement();
	// for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
	// enumIpAddr.hasMoreElements();) {
	// InetAddress inetAddress = enumIpAddr.nextElement();
	// if (!inetAddress.isLoopbackAddress()) {
	// return inetAddress.getHostAddress().toString();
	// }
	// }
	// }
	// } catch (SocketException e) {
	// e.printStackTrace();
	// }
	//
	// }
	// return null;
	// }

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
			YXLog.e(ex.toString());
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
	 * 将long型转换为String型date
	 */
	public static String long2String(long date) {
		date = date * 1000;
		String formatedDate = "";
		SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm:ss");
		formatedDate = format.format(new Date(date));
		return formatedDate;
	}

	/**
	 * func:将时间间隔变成天数，小时，分钟，�?
	 * 
	 * @param millisUntilFinished
	 * @return Map<String, Integer>
	 */
	public static Map<String, Integer> counter(long millisUntilFinished, Map<String, Integer> map) {
		int day = 0;
		int hour = 0;
		int minute = 0;
		int second = 0;
		int reminder = 0;
		int day_double = 0;
		int day_single = 0;
		int hour_double = 0;
		int hour_single = 0;
		int minute_double = 0;
		int minute_single = 0;
		int second_double = 0;
		int second_single = 0;
		try {
			day = (int) (millisUntilFinished / DAY); // 获取天时分秒
			reminder = (int) (millisUntilFinished % DAY);
			hour = (int) (reminder / HOUR);
			reminder = (int) (reminder % HOUR);
			minute = (int) (reminder / MINUTE);
			reminder = (int) (reminder % MINUTE);
			second = (int) (reminder / SECOND);

			if (day >= 10) {
				day_double = day / 10;
				day_single = day % 10;
			} else {
				day_double = 0;
				day_single = day;
			}

			if (hour >= 10) {
				hour_double = hour / 10;
				hour_single = hour % 10;
			} else {
				hour_double = 0;
				hour_single = hour;
			}

			if (minute >= 10) {
				minute_double = minute / 10;
				minute_single = minute % 10;

			} else {
				minute_double = 0;
				minute_single = minute;
			}

			if (second >= 10) {
				second_double = second / 10;
				second_single = second % 10;

			} else {
				second_double = 0;
				second_single = second;
			}

			map.put("1", day_double);
			map.put("2", day_single);
			map.put("3", hour_double);
			map.put("4", hour_single);
			map.put("5", minute_double);
			map.put("6", minute_single);
			map.put("7", second_double);
			map.put("8", second_single);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * func:将时间间隔变成天数，小时，分钟，�?
	 * 
	 * @param millisUntilFinished
	 * @return Map<String, Integer>
	 */
	public static int[] counter(long millisUntilFinished) {
		int minute = 0;
		int second = 0;
		int reminder = 0;
		int minute_double = 0;
		int minute_single = 0;
		int second_double = 0;
		int second_single = 0;
		int time[] = new int[4];
		try {
			minute = (int) (millisUntilFinished / MINUTE);
			reminder = (int) (millisUntilFinished % MINUTE);
			second = (int) (reminder / SECOND);

			if (minute >= 10) {
				minute_double = minute / 10;
				minute_single = minute % 10;

			} else {
				minute_double = 0;
				minute_single = minute;
			}

			if (second >= 10) {
				second_double = second / 10;
				second_single = second % 10;

			} else {
				second_double = 0;
				second_single = second;
			}

			time[0] = minute_double;
			time[1] = minute_single;
			time[2] = second_double;
			time[3] = second_single;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return time;
	}

	/**
	 * 将long型转化为�?�?
	 */
	public static String long2formatedate(long time) {
		String formatedate = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
		Date date = new Date(time);
		formatedate = dateFormat.format(date);
		return formatedate;
	}

	/**
	 * 将long型转化为yyyy/MM/dd hh:mm:ss
	 */
	public static String long2fullformateddate(long time) {
		String formatedate = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date(time * 1000);
		formatedate = dateFormat.format(date);
		return formatedate;
	}

	/**
	 * 格式化时�?
	 */
	public static String formatDate(long time) {
		Date date = new Date(time * 1000);
		// return DateFormat.format("a hh:mm", date).toString();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		return dateFormat.format(date);
	}

	/**
	 * 转换成date
	 */
	public static Date toDate(long time) {
		return new Date(time * 1000);
	}

	/**
	 * 将long型转化为MM/dd hh:mm:ss
	 */
	public static String long2date(long time) {
		String formatedate = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm:ss");
		Date date = new Date(time * 1000);
		formatedate = dateFormat.format(date);
		return formatedate;
	}

	/**
	 * 将long型转化为MM/dd hh:mm:ss
	 */
	public static String long2tl(long time) {
		String formatedate = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date date = new Date(time * 1000);
		formatedate = dateFormat.format(date);
		return formatedate;
	}

	/**
	 * 返回当前程序版本�?
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
			YXLog.e(e.getMessage());
		}
		return versionName;
	}

	// 判断是否为烂数据
	public static long changeTime(long time) {
		long temp = time / 60 + (time % 60 == 0 ? 0 : 1);
		long newtime = temp * 60;
		return newtime;
	}

	/**
	 * 汉字转换位汉语拼音首字母，英文字符不�?
	 * 
	 * @param chines
	 *            汉字
	 * @return 拼音
	 */
	public static String converterToFirstSpell(String chines) {
		String pinyinName = "";
		// String pinyinName = PingYinUtil.defaultPinyin;
		try {
			char[] nameChar = chines.toCharArray();
			HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
			defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
			defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			for (int i = 0; i < nameChar.length; i++) {
				if (nameChar[i] > 128) {// 是中�?
					try {
						if (PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat) != null) {// 解析出错,直接放到"#"里面
							pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);
						} else {
							pinyinName = PingYinUtil.defaultPinyin;
						}
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						pinyinName = PingYinUtil.defaultPinyin;
						YXLog.outException(e);
					}
				} else {// 是英�?
					pinyinName += nameChar[i];
				}
			}
		} catch (Exception e) {
			pinyinName = PingYinUtil.defaultPinyin;
			YXLog.outException(e);
		}
		return pinyinName;
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
	 *  为一个Edittext提供填写之后可以�?��性删除功�?
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
			// Log.d(""+name, "ssss "+packageInfo.packageName);
			if (name.equals(packageInfo.packageName))
				return true;

		}
		return false;
	}

	public static String getkong(String name) {
		int l = name.length() * 4 + 16;
		String a = "";
		for (int i = 0; i < l; i++)
			a += " ";

		return a;
	}

	/**
	 * 操作魔信，删除，转发或评�?
	 * 
	 * @param context
	 * @param flag
	 *            如果为true，则是转发评论魔信；如果为false，则是删除魔�?
	 * @param arg1
	 * @param arg2
	 */
	public static void moxinDialog(Context context, AlertDialog dialog, boolean flag, OnClickListener arg1, OnClickListener arg2, int resArg1, int resArg2) {
		// AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.setCanceledOnTouchOutside(true);
		View view = LayoutInflater.from(context).inflate(R.layout.moxin_dialog, null);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		dialog.getWindow().setContentView(view);
		Button comment = (Button) view.findViewById(R.id.comment);
		comment.setVisibility(View.GONE);
		comment.setText(context.getResources().getString(resArg1));
		Button forward = (Button) view.findViewById(R.id.forward);
		// forward.setText(context.getResources().getString(R.string.delete_moxin));
		forward.setText(context.getResources().getString(resArg2));
		if (flag) {
			comment.setVisibility(View.VISIBLE);
			comment.setOnClickListener(arg1);
			// forward.setText(context.getResources().getString(R.string.forward_moxin));
		}
		forward.setOnClickListener(arg2);
	}

	/**
	 * �?��对话�?
	 * 
	 * @param context
	 * @param dialog
	 * @param flag
	 * @param arg1
	 * @param resArg1
	 * @param resArg2
	 */
	public static void moxinDialog(Context context, AlertDialog dialog, OnClickListener arg1, int resArg1) {
		// AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.setCanceledOnTouchOutside(true);
		View view = LayoutInflater.from(context).inflate(R.layout.moxin_dialog, null);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		dialog.getWindow().setContentView(view);
		Button comment = (Button) view.findViewById(R.id.comment);
		comment.setVisibility(View.GONE);
		Button forward = (Button) view.findViewById(R.id.forward);
		forward.setText(context.getResources().getString(resArg1));
		forward.setOnClickListener(arg1);
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

	/**
	 * 解压文件
	 * 
	 * @param context
	 * @param zipFile
	 * @param targetDir
	 */
	public static void unzip(Context context, String zipFile, String targetDir) {
		AssetManager assetManager = context.getAssets();
		InputStream inputStream = null;
		try {
			inputStream = assetManager.open(zipFile);
		} catch (IOException e) {
			YXLog.outException(e);
			return;
		}
		int BUFFER = 4096; // 这里缓冲区我们使�?KB�?
		String strEntry; // 保存每个zip的条目名�?

		try {
			BufferedOutputStream dest = null; // 缓冲输出�?
			// FileInputStream fis = new FileInputStream(zipFile);
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(inputStream));
			ZipEntry entry; // 每个zip条目的实�?

			while ((entry = zis.getNextEntry()) != null) {

				try {
					int count;
					byte data[] = new byte[BUFFER];
					strEntry = entry.getName();

					File entryFile = new File(targetDir + strEntry);
					File entryDir = new File(entryFile.getParent());
					if (!entryDir.exists()) {
						entryDir.mkdirs();
					}

					FileOutputStream fos = new FileOutputStream(entryFile);
					dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = zis.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					dest.flush();
					dest.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			zis.close();
		} catch (Exception cwj) {
			cwj.printStackTrace();
		}
	}

	/**
	 * 创建�?��dialog，包含一个标题，内容，确定和取消按钮
	 * 
	 * @param context
	 * @param dialog
	 * @param arg0
	 *            确定事件
	 * @param titleId
	 *            标题
	 * @param contentId
	 *            内容
	 */
	public static void createDialog(Context context, final AlertDialog dialog, OnClickListener arg0, int titleId, int contentId) {
		// final AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.setCanceledOnTouchOutside(true);
		Resources res = context.getResources();
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_stop_setuser, null);
		dialog.getWindow().setContentView(view);
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(res.getString(titleId));
		// title.setTextAppearance(context,
		// android.R.style.TextAppearance_Medium);
		TextView content = (TextView) view.findViewById(R.id.content);
		content.setText(res.getString(contentId));
		Button confirm = (Button) view.findViewById(R.id.exit_determine);
		confirm.setOnClickListener(arg0);
		Button cancel = (Button) view.findViewById(R.id.exit_cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	/**
	 * 清除�?��Activity的历�?
	 * 
	 * @param what
	 *            xk 2013-2-1
	 */
	public static void clearClassHistory(Object what) {
		AppManager.getAppManager().finishActivity(what.getClass().getName());
	}

	/**
	 * 把图片转成s1�?
	 * @param what
	 *            sea 2013-2-1
	 */
	public static String transfromURL2S1(String original) {
		//魔商的图片和git图不处理
		if (!original.startsWith("www.mo-shop.com")) {
			//原图
			if (original.endsWith(".png") && !original.endsWith("_s1.png") && !original.endsWith("_s2.png") 
					&& !original.endsWith("_small.png") && !original.endsWith("_middle.png")) {
				original = original.replaceAll(".png", "_s1.png");
			}
			if (original.endsWith(".jpg") && !original.endsWith("_s1.jpg") && !original.endsWith("_s2.jpg")
					&& !original.endsWith("_small.jpg") && !original.endsWith("_middle.jpg")) {
				original = original.replaceAll(".jpg", "_s1.jpg");
			}
			if (original.endsWith(".jpeg") && !original.endsWith("_s1.jpeg") && !original.endsWith("_s2.jpeg")
					&& !original.endsWith("_small.jpeg") && !original.endsWith("_middle.jpeg")) {
				original = original.replaceAll(".jpeg", "_s1.jpeg");
			}
			//S2的图
			if(original.endsWith("_s2.png")){
				original = original.replaceAll("_s2.png", "_s1.png");
			}
			if(original.endsWith("_s2.jpg")){
				original = original.replaceAll("_s2.jpg", "_s1.jpg");
			}
			if(original.endsWith("_s2.jpeg")){
				original = original.replaceAll("_s2.jpeg", "_s1.jpeg");
			}
			//samll的图
			if(original.endsWith("_small.png")){
				original = original.replaceAll("_small.png", "_s1.png");
			}
			if(original.endsWith("_small.jpg")){
				original = original.replaceAll("_small.jpg", "_s1.jpg");
			}
			if(original.endsWith("_small.jpeg")){
				original = original.replaceAll("_small.jpeg", "_s1.jpeg");
			}
			//middle的图
			if(original.endsWith("_middle.png")){
				original = original.replaceAll("_middle.png", "_s1.png");
			}
			if(original.endsWith("_middle.jpg")){
				original = original.replaceAll("_middle.jpg", "_s1.jpg");
			}
			if(original.endsWith("_middle.jpeg")){
				original = original.replaceAll("_middle.jpeg", "_s1.jpeg");
			}
		}
		return original;
	}
	/**
	 * 把图片转成s2�?
	 * @param what
	 *            sea 2013-2-1
	 */
	public static String transfromURL2S2(String original) {
		//魔商的图片和git图不处理
		if (!original.startsWith("www.mo-shop.com")) {
			//原图
			if (original.endsWith(".png") && !original.endsWith("_s1.png") && !original.endsWith("_s2.png") 
					&& !original.endsWith("_small.png") && !original.endsWith("_middle.png")) {
				original = original.replaceAll(".png", "_s2.png");
			}
			if (original.endsWith(".jpg") && !original.endsWith("_s1.jpg") && !original.endsWith("_s2.jpg")
					&& !original.endsWith("_small.jpg") && !original.endsWith("_middle.jpg")) {
				original = original.replaceAll(".jpg", "_s2.jpg");
			}
			if (original.endsWith(".jpeg") && !original.endsWith("_s1.jpeg") && !original.endsWith("_s2.jpeg")
					&& !original.endsWith("_small.jpeg") && !original.endsWith("_middle.jpeg")) {
				original = original.replaceAll(".jpeg", "_s2.jpeg");
			}
			//S1的图
			if(original.endsWith("_s1.png")){
				original = original.replaceAll("_s1.png", "_s2.png");
			}
			if(original.endsWith("_s1.jpg")){
				original = original.replaceAll("_s1.jpg", "_s2.jpg");
			}
			if(original.endsWith("_s1.jpeg")){
				original = original.replaceAll("_s1.jpeg", "_s2.jpeg");
			}
			//samll的图
			if(original.endsWith("_small.png")){
				original = original.replaceAll("_small.png", "_s2.png");
			}
			if(original.endsWith("_small.jpg")){
				original = original.replaceAll("_small.jpg", "_s2.jpg");
			}
			if(original.endsWith("_small.jpeg")){
				original = original.replaceAll("_small.jpeg", "_s2.jpeg");
			}
			//middle的图
			if(original.endsWith("_middle.png")){
				original = original.replaceAll("_middle.png", "_s2.png");
			}
			if(original.endsWith("_middle.jpg")){
				original = original.replaceAll("_middle.jpg", "_s2.jpg");
			}
			if(original.endsWith("_middle.jpeg")){
				original = original.replaceAll("_middle.jpeg", "_s2.jpeg");
			}
		}
		return original;
	}
	/**
	 * 把图片转成middle�?
	 * @param what
	 *            sea 2013-2-1
	 */
	public static String transfromURL2Middle(String original) {
		//魔商的图片和git图不处理
		if (!original.startsWith("www.mo-shop.com")) {
			//原图
			if (original.endsWith(".png") && !original.endsWith("_s1.png") && !original.endsWith("_s2.png") 
					&& !original.endsWith("_small.png") && !original.endsWith("_middle.png")) {
				original = original.replaceAll(".png", "_middle.png");
			}
			if (original.endsWith(".jpg") && !original.endsWith("_s1.jpg") && !original.endsWith("_s2.jpg")
					&& !original.endsWith("_small.jpg") && !original.endsWith("_middle.jpg")) {
				original = original.replaceAll(".jpg", "_middle.jpg");
			}
			if (original.endsWith(".jpeg") && !original.endsWith("_s1.jpeg") && !original.endsWith("_s2.jpeg")
					&& !original.endsWith("_small.jpeg") && !original.endsWith("_middle.jpeg")) {
				original = original.replaceAll(".jpeg", "_middle.jpeg");
			}
			//S1的图
			if(original.endsWith("_s1.png")){
				original = original.replaceAll("_s1.png", "_middle.png");
			}
			if(original.endsWith("_s1.jpg")){
				original = original.replaceAll("_s1.jpg", "_middle.jpg");
			}
			if(original.endsWith("_s1.jpeg")){
				original = original.replaceAll("_s1.jpeg", "_middle.jpeg");
			}
			//S2的图
			if(original.endsWith("_s2.png")){
				original = original.replaceAll("_s2.png", "_middle.png");
			}
			if(original.endsWith("_s2.jpg")){
				original = original.replaceAll("_s2.jpg", "_middle.jpg");
			}
			if(original.endsWith("_s2.jpeg")){
				original = original.replaceAll("_s2.jpeg", "_middle.jpeg");
			}
			//samll的图
			if(original.endsWith("_small.png")){
				original = original.replaceAll("_small.png", "_middle.png");
			}
			if(original.endsWith("_small.jpg")){
				original = original.replaceAll("_small.jpg", "_middle.jpg");
			}
			if(original.endsWith("_small.jpeg")){
				original = original.replaceAll("_small.jpeg", "_middle.jpeg");
			}
			
		}
		return original;
	}
	/**
	 * 把图片转成small�?
	 * @param what
	 *            sea 2013-2-1
	 */
	public static void transfromURL2Small(String original) {
		//魔商的图片和git图不处理
		if (!original.startsWith("www.mo-shop.com")) {
			//原图
			if (original.endsWith(".png") && !original.endsWith("_s1.png") && !original.endsWith("_s2.png") 
					&& !original.endsWith("_small.png") && !original.endsWith("_middle.png")) {
				original = original.replaceAll(".png", "_small.png");
			}
			if (original.endsWith(".jpg") && !original.endsWith("_s1.jpg") && !original.endsWith("_s2.jpg")
					&& !original.endsWith("_small.jpg") && !original.endsWith("_middle.jpg")) {
				original = original.replaceAll(".jpg", "_small.jpg");
			}
			if (original.endsWith(".jpeg") && !original.endsWith("_s1.jpeg") && !original.endsWith("_s2.jpeg")
					&& !original.endsWith("_small.jpeg") && !original.endsWith("_middle.jpeg")) {
				original = original.replaceAll(".jpeg", "_small.jpeg");
			}
			//S1的图
			if(original.endsWith("_s1.png")){
				original = original.replaceAll("_s1.png", "_small.png");
			}
			if(original.endsWith("_s1.jpg")){
				original = original.replaceAll("_s1.jpg", "_small.jpg");
			}
			if(original.endsWith("_s1.jpeg")){
				original = original.replaceAll("_s1.jpeg", "_small.jpeg");
			}
			//S2的图
			if(original.endsWith("_s2.png")){
				original = original.replaceAll("_s2.png", "_small.png");
			}
			if(original.endsWith("_s2.jpg")){
				original = original.replaceAll("_s2.jpg", "_small.jpg");
			}
			if(original.endsWith("_s2.jpeg")){
				original = original.replaceAll("_s2.jpeg", "_small.jpeg");
			}
			//middle的图
			if(original.endsWith("_middle.png")){
				original = original.replaceAll("_middle.png", "_small.png");
			}
			if(original.endsWith("_middle.jpg")){
				original = original.replaceAll("_middle.jpg", "_small.jpg");
			}
			if(original.endsWith("_middle.jpeg")){
				original = original.replaceAll("_middle.jpeg", "_small.jpeg");
			}
			
		}
	}
	/**
	 * 把图片转成原�?
	 * @param what
	 *            sea 2013-2-1
	 */
	public static void transfromURL2Original(String original) {
		//魔商的图片和git图不处理
		if (!original.startsWith("www.mo-shop.com")) {
			//S1的图
			if(original.endsWith("_s1.png")){
				original = original.replaceAll("_s1.png", "_.png");
			}
			if(original.endsWith("_s1.jpg")){
				original = original.replaceAll("_s1.jpg", "_.jpg");
			}
			if(original.endsWith("_s1.jpeg")){
				original = original.replaceAll("_s1.jpeg", "_.jpeg");
			}
			//S2的图
			if(original.endsWith("_s2.png")){
				original = original.replaceAll("_s2.png", "_.png");
			}
			if(original.endsWith("_s2.jpg")){
				original = original.replaceAll("_s2.jpg", "_.jpg");
			}
			if(original.endsWith("_s2.jpeg")){
				original = original.replaceAll("_s2.jpeg", "_.jpeg");
			}
			//samll的图
			if(original.endsWith("_small.png")){
				original = original.replaceAll("_small.png", "_.png");
			}
			if(original.endsWith("_small.jpg")){
				original = original.replaceAll("_small.jpg", "_.jpg");
			}
			if(original.endsWith("_small.jpeg")){
				original = original.replaceAll("_small.jpeg", "_.jpeg");
			}
			//middle的图
			if(original.endsWith("_middle.png")){
				original = original.replaceAll("_middle.png", "_.png");
			}
			if(original.endsWith("_middle.jpg")){
				original = original.replaceAll("_middle.jpg", "_.jpg");
			}
			if(original.endsWith("_middle.jpeg")){
				original = original.replaceAll("_middle.jpeg", "_.jpeg");
			}
		}
	}
	

//	public static CharSequence addSmileysToMessage(String msg) {
//		SmileyParser parser = SmileyParser.getInstance();
//		return parser.addSmileySpans(msg);
//	   }

	
	public static void updateLanguageConfig(MoXianApp app, Context context) {
		Resources res = context.getResources();
		Configuration config = res.getConfiguration();
		DisplayMetrics dm = res.getDisplayMetrics();
		if (app.mLanguage == 0) {
			config.locale = Locale.ENGLISH;
		}else if(app.mLanguage == 1){
			config.locale = Locale.SIMPLIFIED_CHINESE;
		}
		res.updateConfiguration(config, dm);
	}
}
