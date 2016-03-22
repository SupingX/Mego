package com.mycj.jusd.base;


import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.laput.service.XBlueService;
import com.mycj.jusd.MainActivity;
import com.mycj.jusd.bean.StaticValue;
import com.mycj.jusd.broadcast.BluetoothEnableBroadcastReceiver;
import com.mycj.jusd.service.JunsdaXplBluetoothService;
import com.mycj.jusd.service.XplBluetoothService;
import com.mycj.jusd.util.FileUtil;
import com.mycj.jusd.util.ScreenShot;
import com.mycj.jusd.view.LaputaAlertDialog;



/**
 * Created by Administrator on 2015/11/20.
 */
public class BaseActivity extends FragmentActivity {
	protected static final int REQUEST_ENABLE_BLUETOOTH = 0x123;
	private BluetoothEnableBroadcastReceiver receiver = new BluetoothEnableBroadcastReceiver(){

		@Override
		public void doBluetoothEnable() {
			toast("请开启蓝牙");
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(intent, REQUEST_ENABLE_BLUETOOTH);
		}
		
	};
	
	
	public LaputaAlertDialog laputaAlertDialog(String msg){
		LaputaAlertDialog dialog = new LaputaAlertDialog(this).builder(msg);
		dialog.show();
		return dialog;
	}
//	private BluetoothStateBroadcastReceiver stateReceiver = new BluetoothStateBroadcastReceiver(){
//
//		@Override
//		public void onBluetoothChange(int state, int previousState) {
//			if (state == BluetoothAdapter.STATE_ON) {
//				XplBluetoothService xplBluetoothService = getXplBluetoothService();
//				if (xplBluetoothService!=null) {
//					xplBluetoothService.scanDevice(true);
//				}
//			}
//		}
//
//		@Override
//		public void onBluetoothDisconnect() {
//			
//		}
//		
//	};
	
	
	protected void onCreate(android.os.Bundle arg0) {
		super.onCreate(arg0);
		registerReceiver(receiver, new IntentFilter(XplBluetoothService.ACTION_BLUETOOTH_ENABLE));
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
			if (resultCode==RESULT_OK) {
				//
			}
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}
	
	
	
//    public JunsdaXplBluetoothService getXplBluetoothService(){
//        BaseApp app = (BaseApp)getApplication();
//        return app.getXplBluetoothService();
//    }

    public boolean isConnected(XplBluetoothService xplBluetoothService){
        if (xplBluetoothService==null){
            return false;
        }
        return xplBluetoothService.isBluetoothConnected();
    }
    
    protected void toast(String text){
    	Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
    
    public XBlueService getXBlueService(){
		 BaseApp app = (BaseApp)getApplication();
      return app.getXBlueService();
	}
    
    protected String getStringForTwo(int value) {
		String result = String.valueOf(value);
		if (result.length() == 1) {
			result = "0" + result;
		}
		return result;
	}
    
public void share(Handler mHandler){
	Bitmap bitmap = ScreenShot.takeScreenShot(this);
	String path = FileUtil.getandSaveCurrentImage(this, bitmap);
	if (path != null) {
		Message msg = new Message();
		msg.what = StaticValue.MSG_SHARE;
		msg.obj = path;
		mHandler.sendMessage(msg);
	}
}
    
protected ProgressDialog showProgressDialog(String msg) {
	ProgressDialog pDialog;
	pDialog = new ProgressDialog(this);
	pDialog.setCancelable(false);
	pDialog.setMessage(msg);
	pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	pDialog.show();
	return pDialog;
}
//    /**
//	 * 字体:简体方正
//	 * @return
//	 */
//	public Typeface getTypefaceForJianti(){
//		 BaseApp app = (BaseApp)getApplication();
//		return app.getTypefaceForJianti();
//	}
//	/**
//	 * 字体:繁体方正
//	 * @return
//	 */
//	public Typeface getTypefaceForFanti(){
//		 BaseApp app = (BaseApp)getApplication();
//		return app.getTypefaceForFanti();
//	}
//	/**
//	 * 字体:数字字母 方正
//	 * @return
//	 */
//	public Typeface getTypefaceForNumber(){
//		 BaseApp app = (BaseApp)getApplication();
//		return app.getTypefaceForNumber();
//	}
    
}
