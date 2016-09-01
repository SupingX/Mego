package com.mycj.jusd.base;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.mycj.jusd.bean.JunConstant;
import com.mycj.jusd.service.BlueService;
import com.mycj.jusd.util.FileUtil;
import com.mycj.jusd.util.ScreenShot;
import com.mycj.jusd.view.LaputaAlertDialog;
import com.mycj.jusd.view.XplAlertDialog;



/**
 * Created by Administrator on 2015/11/20.
 */
public class BaseActivity extends FragmentActivity {
	
	protected boolean isDebug = false;
	protected void e(String tag,String msg){
		if (isDebug) {
			Log.e(tag, msg);
		}
	}
	protected void i(String tag,String msg){
		if (isDebug) {
			Log.e(tag, msg);
		}
	}
	public LaputaAlertDialog dialogPlsConnectJSDWatchFirst(){
		return this.laputaAlertDialog("请先链接JUNSD运动表");
	}
	
	public XplAlertDialog showXplDialog(String msg){
		XplAlertDialog dialog = new XplAlertDialog(this).builder2(msg);
		return dialog;
		
	}
	
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
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	
	
	
//    public JunsdaXplBluetoothService getXplBluetoothService(){
//        BaseApp app = (BaseApp)getApplication();
//        return app.getXplBluetoothService();
//    }

  /*  public boolean isConnected(XplBluetoothService xplBluetoothService){
        if (xplBluetoothService==null){
            return false;
        }
        return xplBluetoothService.isBluetoothConnected();
    }*/
    
    protected void toast(String text){
    	Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
    
    public BlueService getXBlueService(){
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
		msg.what = JunConstant.MSG_SHARE;
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
