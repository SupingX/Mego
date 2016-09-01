package com.mycj.jusd.util;

public class TimeUtil {
	public static String getTimeBySecond(int second){
		
		String result = "";
		if (second < 0) {
			return result;
		}
		if (second < 60) {
			result +="00'";
			result +=getString(second) + "\"";
		} else{
			int min = second / 60;
			int secondnew = second -min*60;
			result+=getString(min) + "\'";
			result+=getString(secondnew) + "\"";
		}
		return result;
	}
	
	public static int[] getTimesBySecond(int second){
		
		if (second < 0) {
			return null;
		}

		if (second < 60) {
			return new int[]{0,second};
		} else{
			int min = second / 60;
			int secondnew = second -min*60;
			return new int[]{min,secondnew};
		}
	}
	
	public static String getString(int value){
		String valueOf = String.valueOf(value);
		if (valueOf.length()==1) {
			return "0"+valueOf;
		}
		return valueOf;
	}
}
