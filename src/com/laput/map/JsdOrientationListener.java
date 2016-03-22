package com.laput.map;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class JsdOrientationListener implements SensorEventListener {

	private Context context;
	private SensorManager sensorManager;
	private Sensor sensor;
	private float lastX;

	public JsdOrientationListener(Context context) {
		this.context = context;
	}
	
	
	@SuppressWarnings("deprecation")
	public void start(){
		Log.e("baidu", "start()");
		// 获得传感器管理器
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager != null) {
			// 获得方向传感器
			sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
//			sensor = sensorManager.getDefaultSensor(SensorManager.getOrientation(R, values));
			
			
		}
		
		Log.e("xpl", "sensor : " + sensor);
		//注册
		if (sensor != null) {
			sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_FASTEST);
		}
		
	}
	
	public void stop(){
		sensorManager.unregisterListener(this);
	}


	@SuppressWarnings("deprecation")
	@Override
	public void onSensorChanged(SensorEvent event) {
		Log.e("xpl", "onSensorChanged");
		// 接受方向感应器的类型
		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
			// 这里我们可以得到数据，然后根据数据需要来处理
			float x = event.values[SensorManager.DATA_X];
			if (Math.abs(x-lastX) > 1.0) {
				if (mJsdOnOrientationListener!=null) {
					mJsdOnOrientationListener.onOrientation(x);
				}
			}
			Log.e("baidu", "DATA_X : " + x);
			lastX = x;
					
		}
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		Log.e("xpl", "onAccuracyChanged");
		
	}
	
	
	public interface JsdOnOrientationListener {
		public void onOrientation(float x);
	}
	public void setOnOrientationListener(JsdOnOrientationListener l){
		this.mJsdOnOrientationListener = l;
	}
	private JsdOnOrientationListener mJsdOnOrientationListener;
	
}
