package com.campus.insideout.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * ä¸?¸ªæ£?µææºææççå¬å¨
 */
public class ShakeListener implements SensorEventListener {
	// éåº¦éå?ï¼å½ææéåº¦è¾¾å°è¿å?åäº§çä½ç?
	private static final int SPEED_SHRESHOLD = 3000;
	// ä¸¤æ¬¡æ£?µçæ¶é´é´é?
	private static final int UPTATE_INTERVAL_TIME = 70;
	// ä¼ æå¨ç®¡çå¨
	private SensorManager sensorManager;
	// ä¼ æå?
	private Sensor sensor;
	// éåæåºçå¬å?
	private OnShakeListener onShakeListener;
	// ä¸ä¸æ?
	private Context mContext;
	// ææºä¸ä¸ä¸ªä½ç½®æ¶éåæåºåæ 
	private float lastX;
	private float lastY;
	private float lastZ;
	// ä¸æ¬¡æ£?µæ¶é´
	private long lastUpdateTime;

	// æé?å?
	public ShakeListener(Context c) {
		// è·å¾çå¬å¯¹è±¡
		mContext = c;
		start();
	}

	// å¼?§
	public void start() {
		// è·å¾ä¼ æå¨ç®¡çå¨
		sensorManager = (SensorManager) mContext
				.getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager != null) {
			// è·å¾éåä¼ æå?
			sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}
		// æ³¨å
		if (sensor != null) {
			sensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_GAME);
		}

	}

	// åæ­¢æ£?µ
	public void stop() {
		sensorManager.unregisterListener(this);
	}

	// è®¾ç½®éåæåºçå¬å?
	public void setOnShakeListener(OnShakeListener listener) {
		onShakeListener = listener;
	}

	// éåæåºå¨æåºè·å¾ååæ°æ?
	public void onSensorChanged(SensorEvent event) {
		// ç°å¨æ£?µæ¶é´
		long currentUpdateTime = System.currentTimeMillis();
		// ä¸¤æ¬¡æ£?µçæ¶é´é´é?
		long timeInterval = currentUpdateTime - lastUpdateTime;
		// å¤æ­æ¯å¦è¾¾å°äºæ£æµæ¶é´é´é?
		if (timeInterval < UPTATE_INTERVAL_TIME)
			return;
		// ç°å¨çæ¶é´åælastæ¶é´
		lastUpdateTime = currentUpdateTime;

		// è·å¾x,y,zåæ 
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];

		// è·å¾x,y,zçååå?
		float deltaX = x - lastX;
		float deltaY = y - lastY;
		float deltaZ = z - lastZ;

		// å°ç°å¨çåæ åælaståæ 
		lastX = x;
		lastY = y;
		lastZ = z;

		double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
				* deltaZ)
				/ timeInterval * 10000;
		Log.v("thelog", "===========log===================");
		// è¾¾å°éåº¦é??ï¼ååºæç¤?
		if (speed >= SPEED_SHRESHOLD) {
			onShakeListener.onShake();
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	// ææçå¬æ¥å£
	public interface OnShakeListener {
		public void onShake();
	}

}