
package com.campus.insideout;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;

import com.campus.insideout.utils.CommonData;
/**
 * 基本信息都在这里面
 * @author sea
 *
 */
public class IApp extends BaseApplication{
	//用户中心字段
	public String mNickName; // 昵称
	public String mEmail; // 邮箱
	public int mGender; // 0是女性1是男性
	public String mBirthdaty;// 格式为8位
	public int mUserId; // 用户id
	public String mAvatarUrl; // 头像网路地址
	public int mFansNum; // 好友数量
	//session 
	public String token;//需要用这个来做安全验证
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		CookieSyncManager.createInstance(this);
		initAPP();
		
	}
	public void saveUserAndSession() {
		SharedPreferences settings = getApplicationContext().getSharedPreferences(CommonData.SESSION, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		// Session
		if (!TextUtils.isEmpty(token)) {
			editor.putString("nickname", this.mNickName);
			editor.putString("email", this.mEmail);
			editor.putInt("gender", this.mGender);
			editor.putString("birthdaty", this.mBirthdaty);
			editor.putInt("userid", this.mUserId);
			editor.putString("avatarUrl", this.mAvatarUrl);
			editor.putInt("mFansNum", this.mFansNum);
		}
		editor.commit();
	}
	/**
	 * 用来保存服务器返回的token
	 */
	public void saveToken(){
		SharedPreferences settings = getApplicationContext().getSharedPreferences(CommonData.SESSION, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("token", this.token);
		editor.commit();
	}
	/**
	 * 登出用户
	 */
	public void logoutUser() {
		SharedPreferences settings = getApplicationContext().getSharedPreferences(CommonData.SESSION, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.remove("token");
		editor.commit();
	}

	/**
	 * 初始化一些用户的基本信息
	 */
	public void initAPP() {
		SharedPreferences settings = getApplicationContext().getSharedPreferences(CommonData.CONFIG, Context.MODE_PRIVATE);
		this.mNickName = settings.getString("nickname", "");
		this.mEmail = settings.getString("email", "");
		this.mGender = settings.getInt("gender", 0);
		this.mBirthdaty = settings.getString("birthdaty", "");
		this.mUserId = settings.getInt("userid", 0);
		this.mAvatarUrl = settings.getString("avatarUrl", "");
		this.mFansNum = settings.getInt("fansNum", 0);
		SharedPreferences cookies = getApplicationContext().getSharedPreferences(CommonData.SESSION, Context.MODE_PRIVATE);
		this.token = cookies.getString("token", "");
	}


	/**
	 * 用token判断用户是否登录
	 */
	public boolean isLogIn() {
		if (TextUtils.isEmpty(token)) {
			return false;
		} else {
			return true;
		}

	}
	
}
