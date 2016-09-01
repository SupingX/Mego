package com.mycj.jusd.util;

import java.util.Calendar;
import java.util.Date;

import android.util.TimeUtils;

public class JunUtil {
	public static final String SDF = "yyyy-MM-dd";
	public static final String DEFAULT_BIRTHDAY = "2000-01-01";
	
	public static String getDateStr(int year,int month,int day){
		try {
			Calendar c = Calendar.getInstance();
			c.clear();
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, month-1);
			c.set(Calendar.DAY_OF_MONTH, day);
			return DateUtil.dateToString(c.getTime(), SDF);
		} catch (Exception e) {
		}
		return DEFAULT_BIRTHDAY;
	}
	
	private static Date getDate(String date){
		Date d = null;
		try {
			d = DateUtil.stringToDate(date, SDF);
		} catch (Exception e) {
			d= DateUtil.stringToDate(DEFAULT_BIRTHDAY, SDF);
		}
		return  d;
	}
	
	public static int[] getYearMonthDay(String date){
		Calendar c = Calendar.getInstance();
		c.setTime(getDate(date));
		return  new int []{c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)};
	}
	
	
	
	public static int getAge(String date){
		Date  d = getDate(date);
		Calendar cTo = Calendar.getInstance();
		cTo.setTime(d);
		Calendar cNow = Calendar.getInstance();
		cNow.setTime(new Date());
		int deff = cNow.get(Calendar.YEAR) - cTo.get(Calendar.YEAR);
//		long time = d.getTime();
//		long deff = System.currentTimeMillis() - time;
		if (deff <0) {
			return 20;
		}
//		return (int)((deff * 1.0) / (1000 * 60 * 60 * 24 * 365));
		return deff;
	}
}
