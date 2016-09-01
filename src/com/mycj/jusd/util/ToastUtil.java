package com.mycj.jusd.util;


import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 * 
 * @author 思杭 QQ：363633726 TEL：13040815454 地址：深圳
 *
 */
public class ToastUtil {
	
	private static Toast toast;
	
	public static void showToast(Context context, String text, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, text, duration);
		} else {
			toast.setText(text);
			toast.setDuration(duration);
		}
		toast.show();
	}
	
	public static void showToast(Context context, String text) {
		if (toast == null) {
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			toast.setText(text);
			toast.setDuration(Toast.LENGTH_SHORT);
		}
		toast.show();
	}
	
	public static void showToast(Context context, int text, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, text, duration);
		} else {
			toast.setText(text);
			toast.setDuration(duration);
		}
		toast.show();
	}
	
	public static void showToast(Context context, int text) {
		if (toast == null) {
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			toast.setText(text);
			toast.setDuration(Toast.LENGTH_SHORT);
		}
		toast.show();
	}
}