package com.campus.insideout;

import java.util.UUID;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.campus.insideout.utils.DisplayImageConfig;
import com.campus.insideout.utils.MD5Util;
import com.campus.insideout.utils.SIMCardInfo;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public abstract class BaseApplication extends Application {
	public ImageLoader imageLoader = ImageLoader.getInstance();
	public int displayWith = 0;
	public int displayHeight = 0;
	public String deviceid = null;
	public int versionCode;
	public String versionName = null;
	public static String CACHE_PATH = null;

	/**
	 * 填充你想收集的一些用户信息。比如屏幕长宽<br>
	 */
	public static String AGENT;

	// 收集的一些信息，包括版本，语言，手机设备号，手机系统版本，屏幕长宽，壁纸长宽
	public static String WE_NEED;
	public static String KEY;


	public String mPhoneNum = "+86";
	public String IMSI = "460";

	public float density;
	@Override	
	public void onCreate() {
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display d = wm.getDefaultDisplay();
		this.displayWith = d.getWidth();
		this.displayHeight = d.getHeight();
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		density = dm.density;
		// 拿到电话的服务
		TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		deviceid = mTelephonyMgr.getDeviceId();// 手机设备ID，这个ID会被用为用户访问统计
		if(deviceid==null){
			deviceid = UUID.randomUUID().toString();
		}
		SIMCardInfo siminfo = new SIMCardInfo(this);
		initMetaData();
		initImageLoader(getApplicationContext());
	}

	public static void initImageLoader(Context context) {
		int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 8);

		MemoryCacheAware<String, Bitmap> memoryCache;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			memoryCache = new LruMemoryCache(memoryCacheSize);
		} else {
			memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
		}

		// This configuration tuning is custom. You can tune every option, you may tune some of them, 
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCache(memoryCache)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.enableLogging() // Not necessary in common
				.defaultDisplayImageOptions(DisplayImageConfig.mPhotoOptionsoptions)
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
	//@SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
	private void initMetaData() {
		PackageManager manager = getPackageManager();

		try {
			PackageInfo packageInfo = manager.getPackageInfo(getPackageName(), 0);
			versionCode = packageInfo.versionCode;// 设置系统版本号
			versionName = packageInfo.versionName;// 设置系统版本名
			CACHE_PATH = getCacheDir().getAbsolutePath();


			TelephonyManager phoneMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//			mPhoneNum = phoneMgr.getLine1Number()==null?"+86":phoneMgr.getLine1Number();
			IMSI = phoneMgr.getSubscriberId();
			if(IMSI!=null){
				if(IMSI.startsWith("460")){
					mPhoneNum = "+86";
				}else{
					mPhoneNum = "+88";
				}
			}else{
				mPhoneNum = "+86";
			}

		} catch (NameNotFoundException e) {
		}
		
		StringBuilder stringBuilder = new StringBuilder("moxian:");//应用名字
		stringBuilder.append("android&");//平台
		stringBuilder.append(versionName+"&");//应用的版本名
		stringBuilder.append(deviceid+"&");//手机的唯一id  可能是IMEI
		stringBuilder.append(android.os.Build.VERSION.RELEASE+"&");//手机系统版本
		stringBuilder.append(android.os.Build.MODEL);//手机型号
		
		WE_NEED = stringBuilder.toString();
		KEY = MD5Util.MD52(WE_NEED, "eurjmsdrf$%^&@DER4515");
	}



}
