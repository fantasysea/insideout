package com.campus.insideout.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class ImageOperation {

	public interface LoadingProgress{
		public void doUpdatePercent(int newPersent);
	}
	static Handler percentHandler = new Handler() {
		public void handleMessage(Message msg) {
			((LoadingProgress) msg.obj).doUpdatePercent(msg.arg1);
		}
	};
	public static boolean loadImageFromUrl(Context context, String imageUrl,
			String saveUrl,LoadingProgress interfaceLoading) {
		InputStream is = null;

		File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),
					saveUrl);

		if (!cacheDir.exists() && !cacheDir.isDirectory()) {
			cacheDir.mkdirs();
		}
		 
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(imageUrl);
		HttpResponse response;
		try {
			response = client.execute(get);

			HttpEntity entity = response.getEntity();
			float length = entity.getContentLength();

			is = entity.getContent();
			FileOutputStream fos = null;
			if (is != null) {
				fos = new FileOutputStream(cacheDir + "/" + imageUrl.hashCode());
				byte[] buf = new byte[1024];
				int ch = -1;
				float count = 0;
				int newPersent = 0, oldPersent = 0;
				while ((ch = is.read(buf)) != -1) {
					fos.write(buf, 0, ch);
					count += ch;
					newPersent = (int) (count * 100 / length);
					if (newPersent > oldPersent){
						Message msg = percentHandler.obtainMessage();
						msg.obj = interfaceLoading;msg.arg1 = newPersent;
						percentHandler.sendMessage(msg);
						//percentHandler.sendEmptyMessage(newPersent);
					}
					oldPersent = newPersent;
				}
			}
			fos.flush();
			if (fos != null) {
				fos.close();
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}