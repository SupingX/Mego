package com.mycj.jusd.util;

/**
 * 平均配速 -->每公里多少分钟
 * @author Administrator
 *
 */
public class PaceUtil {
	public static String getAvgPace(float distance , long second){
		if (distance == 0) {
			return "00" + "\"" + "00" + "'" ;
		}
		float pace = (second *1.0f / 60) / distance;
		int min = (int) Math.floor(pace);
		int sec = (int) ((pace - min ) * 60);
		return min + "\"" + sec + "'" ;
	}
}
