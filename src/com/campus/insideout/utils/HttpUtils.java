package com.campus.insideout.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.Header;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.yunxun.moxian.MoXianApp;

public class HttpUtils {

	/**
	 * 新的接口请求向服务器发�?Http请求
	 * 
	 * @param url
	 * @param key
	 * @param value
	 * @return
	 */
	public static String newRequestServer(MoXianApp mApp,String url, Map<String, Object> params) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();//构建post给php的参�?		for(String name : params.keySet()){
	        nameValuePairs.add(new BasicNameValuePair(name,String.valueOf(params.get(name))));
	     }
		String result = "";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpClientParams.setCookiePolicy(client.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
		HttpPost post = new HttpPost(url);
		post.setHeader("Cookie", "cookie="+mApp.cookie+"");
//		post.setHeader("PHPSESSID", "PHPSESSID"+mApp.PHPSESSID+"");
		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
			HttpResponse httpResponse = client.execute(post);
//			org.apache.http.Header[] headers = httpResponse.getHeaders("Set-Cookie");
//	    	String headerstr=headers.toString();
//	    	YXLog.e("header:" + headerstr);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(httpResponse.getEntity());
				YXLog.i("HttpUtils--newRequestServer--result:" + result);
				
				CookieStore cookieStore = client.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				 String tmpmxcookies = "";
				  String tmpPHPSESSIDcookies = "";
		            for (Cookie ck : cookies) {
		            	if("mx".equalsIgnoreCase(ck.getName())){
		            		tmpmxcookies= ck.getValue();
		            	}
//		            	if("PHPSESSID".equalsIgnoreCase(ck.getName())){
//		            		tmpPHPSESSIDcookies= ck.getValue();
//		            	}
		            }
		            //保存cookie   
	        		if(mApp != null && (tmpmxcookies != ""||tmpPHPSESSIDcookies!="")){
	        			if(mApp.cookie!=tmpmxcookies){
	        				mApp.cookie = tmpmxcookies;
//	        				mApp.PHPSESSID = tmpPHPSESSIDcookies;
	        				mApp.saveCookie();
	        			}
	        		}
			}
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			System.gc();
			e.printStackTrace();
		} finally {
			client.getConnectionManager().closeIdleConnections(0, TimeUnit.SECONDS);
		}
		return result;
	}
	/**
	 * 向服务器发�?Http请求
	 * 
	 * @param url
	 * @param key
	 * @param value
	 * @return
	 */
	public static String requestServer(MoXianApp mApp,String url, String key, String value) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();//构建post给php的参�?		nameValuePairs.add(new BasicNameValuePair(key,value));
		String result = "";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpClientParams.setCookiePolicy(client.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
		HttpPost post = new HttpPost(url);
		post.setHeader("Cookie", "cookie="+mApp.cookie+"");
//		post.setHeader("PHPSESSID", "PHPSESSID"+mApp.PHPSESSID+"");
		
		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
			HttpResponse httpResponse = client.execute(post);
//			org.apache.http.Header[] headers = httpResponse.getHeaders("Set-Cookie");
//	    	String headerstr=headers.toString();
//	    	YXLog.e("header:" + headerstr);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(httpResponse.getEntity());
				
				
				CookieStore cookieStore = client.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				 String tmpmxcookies = "";
//				  String tmpPHPSESSIDcookies = "";
		            for (Cookie ck : cookies) {
		            	if("mx".equalsIgnoreCase(ck.getName())){
		            		tmpmxcookies= ck.getValue();
		            	}
//		            	if("PHPSESSID".equalsIgnoreCase(ck.getName())){
//		            		tmpPHPSESSIDcookies= ck.getValue();
//		            	}
		            }
		            //保存cookie   
		            if(mApp != null && tmpmxcookies != ""){
//	        		if(mApp != null && (tmpmxcookies != ""||tmpPHPSESSIDcookies!="")){
	        			if(mApp.cookie!=tmpmxcookies){
	        				mApp.cookie = tmpmxcookies;
//	        				mApp.PHPSESSID = tmpPHPSESSIDcookies;
	        				mApp.saveCookie();
	        			}
	        		}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			System.gc();
			e.printStackTrace();
		} finally {
			client.getConnectionManager().closeIdleConnections(0, TimeUnit.SECONDS);
		}
		return result;
	}




	private static final String mimeType = "application/octet-stream";

	public static String uploadImage(MoXianApp mApp,String url, String data, byte[] bitmap,String picName) {


		DefaultHttpClient client = new DefaultHttpClient();
		HttpClientParams.setCookiePolicy(client.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
		HttpPost post = new HttpPost(url);
		// post.addHeader(HTTP.USER_AGENT, "android");//此处设置请求的浏览器参数
//		post.setHeader("mx", "mx="+mApp.cookie+"");
		post.setHeader("Cookie", "cookie="+mApp.cookie+"");
//		post.setHeader("PHPSESSID", "PHPSESSID"+mApp.PHPSESSID+"");
		MultipartEntity entity = new MultipartEntity();
		try {
			if(bitmap!=null){
				String fileName = picName;
				entity.addPart("file", new ByteArrayBody(bitmap, mimeType, fileName));
			}
			entity.addPart("data", new StringBody(data, Charset.forName(HTTP.UTF_8)));
			post.setEntity(entity);

			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				
				CookieStore cookieStore = client.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				  String tmpmxcookies = "";
//				  String tmpPHPSESSIDcookies = "";
		            for (Cookie ck : cookies) {
		            	if("mx".equalsIgnoreCase(ck.getName())){
		            		tmpmxcookies= ck.getValue();
		            	}
//		            	if("PHPSESSID".equalsIgnoreCase(ck.getName())){
//		            		tmpPHPSESSIDcookies= ck.getValue();
//		            	}
		            }
		            //保存cookie   
	        		if(mApp != null && tmpmxcookies != ""){
	        			if(mApp.cookie!=tmpmxcookies){
	        				mApp.cookie = tmpmxcookies;
//	        				mApp.PHPSESSID = tmpPHPSESSIDcookies;
	        				mApp.saveCookie();
	        			}
	        		}
	        		return EntityUtils.toString(response.getEntity());
			}

		} catch (Exception e) {
			YXLog.e(e.getMessage());
			return null;
		} finally {
			client.getConnectionManager().closeIdleConnections(0, TimeUnit.SECONDS);
		}
		return null;
	}
	
	public static String modefyInfo(MoXianApp mApp,String url, String data, byte[] bitmap,String picName) {
		
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
		HttpPost post = new HttpPost(url);
		post.setHeader("Cookie", "cookie="+mApp.cookie+"");
//		post.setHeader("PHPSESSID", "PHPSESSID"+mApp.PHPSESSID+"");
		MultipartEntity entity = new MultipartEntity();
		try {
			if(bitmap!=null){
				String fileName = picName;
				entity.addPart("upimg", new ByteArrayBody(bitmap, mimeType, fileName));
			}
			entity.addPart("data", new StringBody(data, Charset.forName(HTTP.UTF_8)));
			post.setEntity(entity);
			
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				CookieStore cookieStore = httpClient.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				 String tmpmxcookies = "";
//				  String tmpPHPSESSIDcookies = "";
		            for (Cookie ck : cookies) {
		            	if("mx".equalsIgnoreCase(ck.getName())){
		            		tmpmxcookies= ck.getValue();
		            	}
//		            	if("PHPSESSID".equalsIgnoreCase(ck.getName())){
//		            		tmpPHPSESSIDcookies= ck.getValue();
//		            	}
		            }
		            //保存cookie   
	        		if(mApp != null && tmpmxcookies != ""){
	        			if(mApp.cookie!=tmpmxcookies){
	        				mApp.cookie = tmpmxcookies;
//	        				mApp.PHPSESSID = tmpPHPSESSIDcookies;
	        				mApp.saveCookie();
	        			}
	        		}
				return EntityUtils.toString(response.getEntity());
			}
			
		} catch (Exception e) {
			YXLog.e(e.getMessage());
			return null;
		} finally {
			httpClient.getConnectionManager().closeIdleConnections(0, TimeUnit.SECONDS);
		}
		return null;
	}

	
	/**
	 * 下载文件 Post方式
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static void getFileFromServer(String urlStr, String filePath) throws Exception{  
		int count = 0;
            URL url = new URL(urlStr);
            URLConnection conexion = url.openConnection();
            conexion.connect();
            int lenghtOfFile = conexion.getContentLength();
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(filePath);
            byte data[] = new byte[1024];
            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
    }  
	
	/**
	 * 上传文件
	 * @param url
	 * @param data
	 * @param bitmap
	 * @param picName
	 * @return
	 */
public static String uploadFile(MoXianApp mApp,String url, String data,String picName, String path) {
		
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.setHeader("Cookie", "cookie="+mApp.cookie+"");
//		post.setHeader("PHPSESSID", "PHPSESSID"+mApp.PHPSESSID+"");
		MultipartEntity entity = new MultipartEntity();
		YXLog.i("--uploadFile--0");
		try {
			 FileInputStream stream = new FileInputStream(new File(path));
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            
            int bytesAvailable, bufferSize;
			int maxBufferSize = 1 * 1024;
			byte[] buffer;
			bytesAvailable = stream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];
			for (int n;(n = stream.read(buffer)) != -1;) {
				out.write(buffer, 0, n);
			}
			
            byte[] buff = out.toByteArray();
			if(buff!=null){
				String temp = picName;
				entity.addPart("upimg", new ByteArrayBody(buff, mimeType, temp));
			}
			entity.addPart("data", new StringBody(data, Charset.forName(HTTP.UTF_8)));
			post.setEntity(entity);
			
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				YXLog.i("EntityUtils.toString(response.getEntity()):");
				CookieStore cookieStore = httpClient.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				 String tmpmxcookies = "";
//				  String tmpPHPSESSIDcookies = "";
		            for (Cookie ck : cookies) {
		            	if("mx".equalsIgnoreCase(ck.getName())){
		            		tmpmxcookies= ck.getValue();
		            	}
//		            	if("PHPSESSID".equalsIgnoreCase(ck.getName())){
//		            		tmpPHPSESSIDcookies= ck.getValue();
//		            	}
		            }
		            //保存cookie   
	        		if(mApp != null && tmpmxcookies != ""){
	        			if(mApp.cookie!=tmpmxcookies){
	        				mApp.cookie = tmpmxcookies;
//	        				mApp.PHPSESSID = tmpPHPSESSIDcookies;
	        				mApp.saveCookie();
	        			}
	        		}
				return EntityUtils.toString(response.getEntity());
			}
			
		} catch (Exception e) {
			YXLog.e("uploadFile--Exception:" + e.getMessage());
			return null;
		} finally {
			httpClient.getConnectionManager().closeIdleConnections(0, TimeUnit.SECONDS);
		}
		return null;
	}


}

