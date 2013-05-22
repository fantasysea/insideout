package com.campus.insideout.utils;

import java.util.HashMap;



import android.os.Environment;

/**
 * @author xk
 *
 */
public class CommonData {
	
	 
	public static String xmppHost = "134.159.152.218";
//	public static String xmppHost = "10.10.10.66";//线上
//	public static String xmppHost = "10.10.10.66";
//	public static String xmppHost = "10.10.10.66";
//	public static String xmppHost = "10.5.0.14";
//	public static String xmppHost = "10.5.0.22";//群聊
//	public static String xmppHost = "10.5.0.15";//mypc群聊
//	public static String xmppHost = "im.moxian.com";//moxian spark
	public static int xmppPort = 5225;//
//	public static int xmppPort = 5222;//群聊
//	public static int xmppPort = 2222;//moxian spark
//	public static String xmppHost = "10.5.0.22";
//	public static int xmppPort = 5225;//
//	public static int xmppPort = 5222;//
	public static String xmppervice="@";
	public static String xmpperviceName="lh1";
	public final static String XMPP_DOMAIN="android";
//	public static String xmpperviceName="jabber.dsd22.cn";
//	public static String xmpperviceName="admin-pc";
	/*
	 * 聊天室域
	 */
//	public final static String XMPP_CHATROOM_DOMAIN = "conference.m41s.com";	 //moxian spark
//	public final static String XMPP_CHATROOM_DOMAIN = "conference.lh1";	 
	public final static String XMPP_CHATROOM_DOMAIN = "conference.hk";	 
//	public final static String XMPP_CHATROOM_DOMAIN = "conference.jabber.dsd22.cn";	 
//	public final static String XMPP_CHATROOM_DOMAIN = "conference.admin-pc";	 
	 ///充�?
	 
	
	 
	public static final int TEXT_FOL=5;
	public static final float TEXT_FOL_S=5;
	public static final String XML_VERSION = "1.0";
	public static final String TOKEN = "moxian";
	public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().toString();
	
	public final static String SDCARD_DOWNLOAD_IMAGES = SDCARD_PATH + "/moxian/downloadImgs/";
	
	public final static int DF_MaxGifSize = 90 * 1024;
	public static final String MOPAI_SETTING = "moxiansetting";
	public static final String MOPAI_PREF = "moxianpref";
	public static final String MOPAI_ADDRESS = "moxianaddress";
	public static final String SEESION_NAME = "moxian_session";
	public final static String DCIM_FOLDER = SDCARD_PATH + "/DCIM/";
	public final static String SDCARD_CACHE_PATH = SDCARD_PATH + "/moxian/cache/";
	public final static String SDCARD_BIGIMG_CACHE_PATH = SDCARD_PATH + "/moxian/bigimgcache/";
	public final static String SSCARD_PCM_PATH=SDCARD_PATH+"/moxian/pcm/";	
	public final static String SSCARD_PCM_DCIM_PATH=SDCARD_PATH+"/moxian/pcm/dcim/";	
	
//	public static String URL = "http://10.6.0.6/api/mx_mobile/index.php";
//	public final static String URL = "http://yx1.moxian.com/api/mx_mobile/index.php";//yx.moxian
//	public static String URL = "http://rl1.moxian.com/api/mx_mobile/index.php";//rl.moxian
//	public final static String URL = "http://yxgz.moxian.com/api/mx_mobile/index.php";//gz.moxian
//	public final static String URL = "http://moxian.com/api/mx_mobile/index.php";//moxian
//	public  static String URL = "http://10.8.8.9/api/mx_mobile/index.php";//rl.moxian
//	public static String URL = "http://yxgz.moxian.com/api/mx_mobile/index.php";//gz.moxian
	public  static String URL = "http://moxian.com/api/mx_mobile/index.php";//moxian

		
	public final static String KEY = "data";
	public final static String SDCARD_TEMP_PATH = SDCARD_PATH + "/moxian/temp/";
	
	 
//	public static final String DOWNLOAD_DEFAULT_URL = "http://10.6.0.14/moxiancms/index.php?m=mobile&c=manage&a=filedown&url=";
	public static final String DOWNLOAD_DEFAULT_URL = "http://rs.hk.moxian.com/";
	
	
	public static final String DEFAULT_HEADIMG_MALE = "http://rs.moxian.com/hk/upload/user/default_head_ico/head_ico_male_50.jpg";
	public static final String DEFAULT_HEADIMG_FEMALE = "http://rs.moxian.com/hk/upload/user/default_head_ico/head_ico_female_50.jpg";
	 
	 public static final int MOXIAN_MOYOU=0;
	 public static final int MOXIAN_FANS=1;
	 
	 public static final int FANS_STATUS_0=0;   //非好�?无关�?
	 public static final int FANS_STATUS_1=1;	//关注对方
	 public static final int FANS_STATUS_2=2;   //相互关注
	 public static final int FANS_STATUS_3=3;   //魔粉 TODO 这个是新加的状�?
	 
	 public static final int ADDFANS_STATUS_0=0;   //添加好友成功
	 public static final int ADDFANS_STATUS_4=4;	//添加好友失败
	 public static final int ADDFANS_STATUS_2=2;   //用户不存�?
	 public static final int ADDFANS_STATUS_3=3;   //已经是好友了
	 
	 public static final int ADDFANS_YES=0;
	 public static final int ADDFANS_NO=1;
	 public static final int ADDFANS_ERROR=2;
	 
	 //个人信息修改  返回参数
	 public static final int UP_USERINFO_SUCCEED=0;
	 public static final int UP_USERINFO_WRONGFUL=1;
	 public static final int UP_USERINFO_REPEAT=2;
	 public static final int UP_USERINFO_ERROR=10;
	 
	 public final static String SDCARD_TEMP_IMG = SDCARD_PATH + "/moxian/temp/temp.jpg";
     
     /**
     *图片后缀：s1,png格式 
     */
    public static final String PICTURE_SUFFIX_S1_PNG = "_s1.png";
    /**
     *图片后缀：s1,jpg格式 
     */
     public static final String PICTURE_SUFFIX_S1_JPG = "_s1.jpg";
     /**
      *图片后缀：s1,jpeg格式 
      */
     public static final String PICTURE_SUFFIX_S1_JPEG = "_s1.jpeg";
     /**
      *图片后缀：s2,png格式 
      */
     public static final String PICTURE_SUFFIX_S2_PNG = "_s1.png";
     /**
      *图片后缀：s2,jpg格式 
      */
     public static final String PICTURE_SUFFIX_S2_JPG = "_s1.jpg";
     /**
     *图片后缀：s2,jpeg格式 
     */
     public static final String PICTURE_SUFFIX_S2_JPEG = "_s1.jpeg";
     public static int IM_TYPE_NORMAL=0;//文字发�?正常
     public static int IM_TYPE_RESEND=1;//文字发�?异常
     public static int IM_TYPE_NORMAL_AUDIO=3;//音频发�?正常
     public static int IM_TYPE_RESEND_AUDIO=4;//音频发�?异常
     public static int MOYOU_PAGE_SIZE =3000;
     
     /**
      * 用户中心的接�?
      * 
      */
// 	public  static String USER_CENTER = "http://10.6.0.6/user";//z.moxian
// 	public  static String USER_CENTER = "http://yx1.moxian.com/user";//yx.moxian
// 	public  static String USER_CENTER = "http://rl1.moxian.com/user";//rl.moxian
// 	public  static String USER_CENTER = "http://yxgz.moxian.com/user";//gz.moxian
 	public  static String USER_CENTER = "http://moxian.com/user";//xianshang.moxian
 	public  static String REGISTER = USER_CENTER+"/mobile/reg";//注册
 	public  static String VERIFYSTATE = USER_CENTER+"/mobile/verifystate";//用户验证状�?
 	public  static String SENDUSERMSG = USER_CENTER+"/mobile/sendusermsg";//验证用户发�?手机验证�?
 	public  static String SENDCHECKMAIL = USER_CENTER+"/mobile/sendcheckmail";//邮箱发�?验证�?
 	public  static String GET_BASEINFO = USER_CENTER+"/mobile/userinfo";//获取基本资料
 	public  static String MODIFY_NICKNAME = USER_CENTER+"/mobile/savenickname";//修改昵称
 	public  static String MODIFY_REALNAME = USER_CENTER+"/mobile/savetruename";//修改真实姓名
 	public  static String MODIFY_ACCOUNT = USER_CENTER+"/mobile/saveusername";//修改账号
 	public  static String MODIFY_WORK = USER_CENTER+"/mobile/savework";//修改工作地点
 	public  static String MODIFY_SCHOOL = USER_CENTER+"/mobile/saveschool";//修改学校
 	public  static String MODIFY_BIRTHADY = USER_CENTER+"/mobile/savebirth";//修改学校
 	public  static String CHECK_MAILCODE = USER_CENTER+"/mobile/valemail";//验证邮箱
 	public  static String CHECK_USERMSG = USER_CENTER+"/mobile/checkusermsg";//验证手机
 	/**
 	 *获取收货地址
 	 */
 	public final static String GET_SHIPPING_ADDRESS = USER_CENTER+"/mobile/getuseraddress";
 	/**
 	 *修改收货地址
 	 */
 	public final static String MODIFY_SHIPPING_ADDRESS = USER_CENTER+"/mobile/setuseraddress";
 	/**
 	 * 晒单列表
 	 */
 	public final static String SHOW_LIST_RECORD = USER_CENTER+"/mobile/getshaidanlist";
 	/**
 	 *晒单上传图片
 	 */
 	public final static String SHOW_UPLOAD_IMG_URL = USER_CENTER+"/mobile/uploadimg";
 	/**
 	 *手机晒单
 	 */
 	public final static String SUBMIT_SHOW_URL = USER_CENTER+"/mobile/shaidanadd";
 	/**
 	 *用户�?��地区 
 	 */
 	public final static String USER_AREA = USER_CENTER+"/mobile/getcityfather";
 	/**
 	 *获取子类地址
 	 */
 	public final static String USER_AREA_CHILD = USER_CENTER+"/mobile/getcitychild";
 	/**
 	 *保存地址
 	 */
 	public final static String SAVE_USER_AREA = USER_CENTER+"/mobile/savecity";
 	/**
 	 *晒单切图
 	 */
 	public final static String SHOW_CUT_IMG = USER_CENTER+"/mobile/imgcut";
}
