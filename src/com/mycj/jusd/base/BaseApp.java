package com.mycj.jusd.base;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.IBinder;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.laputa.blue.util.XLog;
import com.mycj.jusd.service.BlueService;

public class BaseApp extends Application {

	private BlueService xBlueService;

	public BlueService getXBlueService() {
		log("Baseapp xBlueService:" + xBlueService);
		return this.xBlueService;
	}

	private ServiceConnection xBlueConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			xBlueService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			if (service instanceof BlueService.XBlueBinder) {
				BlueService.XBlueBinder binder = (BlueService.XBlueBinder) service;
				xBlueService = binder.getXBlueService();
				log("Baseapp xBlueService:" + xBlueService);
			}
		}
	};

	public static Typeface TYPEFACE_JIAN;
	public static Typeface TYPEFACE_NUM;
	public static Typeface TYPEFACE_FAN;

	@Override
	public void onCreate() {
		super.onCreate();
		Intent xplIntent = new Intent(this, BlueService.class);
		bindService(xplIntent, xBlueConnection, Context.BIND_AUTO_CREATE);
		// 系统字体
		TYPEFACE_JIAN = Typeface.createFromAsset(getAssets(),
				CustomTypeface.JIAN.getPath());
		TYPEFACE_NUM = Typeface.createFromAsset(getAssets(),
				CustomTypeface.NUM.getPath());
		TYPEFACE_FAN = Typeface.createFromAsset(getAssets(),
				CustomTypeface.FAN.getPath());
		// baidu地图
		SDKInitializer.initialize(getApplicationContext());

		//
		XLog.DEV_MODE = true;
	}

	private boolean isDebug = false;

	private void log(String msg) {
		if (isDebug) {
			Log.e("BaseApp", msg);
		}
	}

}
