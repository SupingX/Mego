package com.mycj.jusd.base;





import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.laput.service.XBlueService;
import com.mycj.jusd.service.JunsdaXplBluetoothService;



public class BaseApp extends Application{
	
//	public JunsdaXplBluetoothService getXplBluetoothService(){
//		Log.e("", "==getXplBluetoothService() :" + xplBluetoothService);
//		return this.xplBluetoothService;
//	}
//	private JunsdaXplBluetoothService xplBluetoothService;
//	private ServiceConnection mXplServiceConnect = new ServiceConnection() {
//
//		@Override
//		public void onServiceDisconnected(ComponentName name) {
//			xplBluetoothService=null;
//		}
//
//		@Override
//		public void onServiceConnected(ComponentName name, IBinder service) {
//			Log.e("xpl", "service : " + service);
//			if (service instanceof JunsdaXplBluetoothService.XplBinder) {
//				JunsdaXplBluetoothService.XplBinder xplBinder = (JunsdaXplBluetoothService.XplBinder) service;
//				xplBluetoothService = (JunsdaXplBluetoothService) xplBinder.getXplBluetoothService();
//				Log.e("", "==xplBluetoothService :" + xplBluetoothService);
//				if (xplBluetoothService.isBluetoothEnable()) {
//		//				xplBluetoothService.scanDevice(true);
//				}else{
//					Toast.makeText(getApplicationContext(), "请开启蓝牙...", Toast.LENGTH_SHORT).show();
//				}
//			}else{
//				Log.e("xpl", "service : " + service.getClass().getSimpleName());
//			}
//		}
//	};
	private XBlueService xBlueService;
	public XBlueService getXBlueService(){
		Log.e("", "Baseapp xBlueService:" + xBlueService);
		return this.xBlueService;
	}
	private ServiceConnection xBlueConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			xBlueService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			if (service instanceof XBlueService.XBlueBinder) {
				XBlueService.XBlueBinder binder = (XBlueService.XBlueBinder) service;
				xBlueService = binder.getXBlueService();
				Log.e("", "Baseapp xBlueService:" + xBlueService);
			}
		}
	};
	
	public static Typeface TYPEFACE_JIAN;
	public static Typeface TYPEFACE_NUM;
	public static Typeface TYPEFACE_FAN;
	
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		Intent xplIntent = new Intent(this,XBlueService.class);
//		bindService(xplIntent, mXplServiceConnect, Context.BIND_AUTO_CREATE);
		bindService(xplIntent, xBlueConnection, Context.BIND_AUTO_CREATE);
		//系统字体
		TYPEFACE_JIAN = Typeface.createFromAsset(getAssets(), CustomTypeface.JIAN.getPath());
		TYPEFACE_NUM = Typeface.createFromAsset(getAssets(), CustomTypeface.NUM.getPath());
		TYPEFACE_FAN = Typeface.createFromAsset(getAssets(), CustomTypeface.FAN.getPath());
		//baidu地图
		SDKInitializer.initialize(getApplicationContext());
	}

	@Override
	public void onTerminate() {
//		xplBluetoothService.close();
//		unbindService(mXplServiceConnect);
		xBlueService.closeAll();
		unbindService(xBlueConnection);
		super.onTerminate();
	}

	
}
