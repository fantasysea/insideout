package com.campus.insideout.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * �?���?��手机摇晃的监听器
 */
public class ShakeListener implements SensorEventListener {
	// 速度阈�?，当摇晃速度达到这�?后产生作�?
	private static final int SPEED_SHRESHOLD = 3000;
	// 两次�?��的时间间�?
	private static final int UPTATE_INTERVAL_TIME = 70;
	// 传感器管理器
	private SensorManager sensorManager;
	// 传感�?
	private Sensor sensor;
	// 重力感应监听�?
	private OnShakeListener onShakeListener;
	// 上下�?
	private Context mContext;
	// 手机上一个位置时重力感应坐标
	private float lastX;
	private float lastY;
	private float lastZ;
	// 上次�?��时间
	private long lastUpdateTime;

	// 构�?�?
	public ShakeListener(Context c) {
		// 获得监听对象
		mContext = c;
		start();
	}

	// �?��
	public void start() {
		// 获得传感器管理器
		sensorManager = (SensorManager) mContext
				.getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager != null) {
			// 获得重力传感�?
			sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}
		// 注册
		if (sensor != null) {
			sensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_GAME);
		}

	}

	// 停止�?��
	public void stop() {
		sensorManager.unregisterListener(this);
	}

	// 设置重力感应监听�?
	public void setOnShakeListener(OnShakeListener listener) {
		onShakeListener = listener;
	}

	// 重力感应器感应获得变化数�?
	public void onSensorChanged(SensorEvent event) {
		// 现在�?��时间
		long currentUpdateTime = System.currentTimeMillis();
		// 两次�?��的时间间�?
		long timeInterval = currentUpdateTime - lastUpdateTime;
		// 判断是否达到了检测时间间�?
		if (timeInterval < UPTATE_INTERVAL_TIME)
			return;
		// 现在的时间变成last时间
		lastUpdateTime = currentUpdateTime;

		// 获得x,y,z坐标
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];

		// 获得x,y,z的变化�?
		float deltaX = x - lastX;
		float deltaY = y - lastY;
		float deltaZ = z - lastZ;

		// 将现在的坐标变成last坐标
		lastX = x;
		lastY = y;
		lastZ = z;

		double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
				* deltaZ)
				/ timeInterval * 10000;
		Log.v("thelog", "===========log===================");
		// 达到速度�??，发出提�?
		if (speed >= SPEED_SHRESHOLD) {
			onShakeListener.onShake();
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	// 摇晃监听接口
	public interface OnShakeListener {
		public void onShake();
	}

}