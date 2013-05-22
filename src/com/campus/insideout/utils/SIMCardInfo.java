package com.campus.insideout.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * class name：SIMCardInfo<BR>
 * class description：读取Sim卡信�?BR>
 * PS�?必须在加入各种权�?<BR>
 * Date:2012-3-12<BR>
 * 
 * @version 1.00
 * @author CODYY)peijiangping
 */
public class SIMCardInfo {
	/**
	 * TelephonyManager提供设备上获取�?讯服务信息的入口�?应用程序可以使用这个类方法确定的电信服务商和国家 以及某些类型的用户访问信息�?
	 * 应用程序也可以注册一个监听器到电话收状�?的变化�?不需要直接实例化这个�?
	 * 使用Context.getSystemService(Context.TELEPHONY_SERVICE)来获取这个类的实例�?
	 */
	private TelephonyManager telephonyManager;
	/**
	 * 国际移动用户识别�?
	 */
	private String IMSI;

	public SIMCardInfo(Context context) {
		telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	}

	/**
	 * Role:获取当前设置的电话号�?<BR>
	 * Date:2012-3-12 <BR>
	 * @author CODYY)peijiangping
	 */
	public String getNativePhoneNumber() {
		String NativePhoneNumber = "";
		NativePhoneNumber = telephonyManager.getLine1Number();
		return NativePhoneNumber;
	}

	/**
	 * Role:Telecom service providers获取手机服务商信�?<BR>
	 * �?��加入权限<uses-permission
	 * android:name="android.permission.READ_PHONE_STATE"/> <BR>
	 * Date:2012-3-12 <BR>
	 * 
	 * @author CODYY)peijiangping
	 */
	public String getProvidersName() {
		String ProvidersName = null;
		// 返回唯一的用户ID;就是这张卡的编号神马�?
		IMSI = telephonyManager.getSubscriberId();
		// IMSI号前�?�?60是国家，紧接�?���?�?0 02是中国移动，01是中国联通，03是中国电信�?
		System.out.println(IMSI);
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
			ProvidersName = "中国移动";
		} else if (IMSI.startsWith("46001")) {
			ProvidersName = "中国联�?";
		} else if (IMSI.startsWith("46003")) {
			ProvidersName = "中国电信";
		}
		return ProvidersName;
	}
}