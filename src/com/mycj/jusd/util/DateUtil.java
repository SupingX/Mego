package com.mycj.jusd.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;







import com.laputa.blue.util.XLog;
import com.mycj.jusd.R;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

public class DateUtil {

	public static final int SECONDS_IN_DAY = 60 * 60 * 24;
	public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

	public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
		final long interval = ms1 - ms2;
		return interval < MILLIS_IN_DAY && interval > -1L * MILLIS_IN_DAY && toDay(ms1) == toDay(ms2);
	}

	/**
	 * 获取当前手机的时间格式
	 * 
	 * @param context
	 * @return 24小时制/12小时制
	 */
	public static int getTimeFormat(Context context) {
//		String timeFormat = android.provider.Settings.System.getString(context.getContentResolver(), android.provider.Settings.System.TIME_12_24);
//		//Log.e("DateUtil", "timeFormat :" + timeFormat);
//		if (timeFormat.equals("24")) {
//			return 0;
//		} else if (timeFormat.equals("12")) {
//			return 1;
//		}
//		return -1;
		boolean is24HourFormat = android.text.format.DateFormat.is24HourFormat(context);
		if (is24HourFormat) {
			Log.v("xpl", "24小时");
			return 0;
		}else{
			Log.v("xpl", "12小时");
			return 1;
		}
	}

	private static long toDay(long millis) {
		return (millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY;
	}

//	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static String DEFAULT_SDF = "yyyyMMdd";

	public static boolean isSameDay(String date1, Date date2) {
		 SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_SDF);
		String dateStr2 = sdf.format(date2);
		return date1.equals(dateStr2);
	}

	public static boolean isSameMonth(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		return c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);

	}

	public static String dateToString(Date date) {
		return dateToString(date, "yyyyMMdd");
	}

	public static String dateToHourStr(long date) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date);
		SimpleDateFormat s = new SimpleDateFormat("mm:ss");
		return s.format(c.getTime());
	}

	public static Date stringToDate(String date) {
		return stringToDate(date, DEFAULT_SDF);
	}

	public static int getDayOfMonth(Date date) {
		// 获取一个月的天
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		;
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static int getDayOfMonth(String date) {
		// 获取一个月的天
		return getDayOfMonth(stringToDate(date));
	}

	// 当前的前DIFF天
	public static Date getDateOfDiffDay(Date date, int diff) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		;
		c.add(Calendar.DATE, diff);
		return c.getTime();
	}

	// 当前的前DIFF月
	public static Date getDateOfDiffMonth(Date date, int diff) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		;
		c.add(Calendar.MONTH, diff);
		//Log.v("", "偏移后的月份 ：" + c.get(Calendar.MONTH));
		return c.getTime();
	}

	// 当前月的所有天数
/*	public static int getMonthMaxDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int size = c.getMaximum(Calendar.MONTH);
		Log.i("DateUtil", "getMonthMaxDay size : " + size);
		return size;

	}*/
	// 当前月的所有天数
	public static int getMonthMaxDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public static int getMonthMaxDay(int year,int month){
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month-1);
		return  c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	
	
	public static String dateToString(Date date, String sdf) {
		SimpleDateFormat sdf1 = new SimpleDateFormat(sdf);
		return sdf1.format(date);
	}

	public static Date stringToDate(String date, String sdf) {
		SimpleDateFormat sdf1 = new SimpleDateFormat(sdf);
		try {
			return sdf1.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据传入的毫秒数，转换成MM:SS时间串形式返回
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String getMMSS(long timeMillis) {
		StringBuffer sb = new StringBuffer();
		long mm = (timeMillis % (1000 * 60 * 60)) / 1000 / 60;
		long ss = ((timeMillis % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

		if (mm < 0) {
			sb.append("00:");
		} else if (mm < 10) {
			sb.append("0" + mm + ":");
		} else {
			sb.append(mm + ":");
		}
		if (ss < 0) {
			sb.append("00");
		} else if (ss < 10) {
			sb.append("0" + ss);
		} else {
			sb.append(ss);
		}
		return sb.toString();
	}

	/**
	 * 根据传入的耗秒数, 转换成为HH:MM:SS的字符串返回
	 */
	public static String getHHMMSS(long time) {
		String hhmmss = "00:00:00";
		StringBuffer bf = new StringBuffer();
		if (time == 0) {
			return hhmmss;
		}
		long hh = time / 1000 / 60 / 60;
		long mm = (time % (1000 * 60 * 60)) / 1000 / 60;
		long ss = ((time % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

		if (hh < 0) {
			bf.append("00:");
		} else if (hh < 10) {
			bf.append("0" + hh + ":");
		} else {
			bf.append(hh + ":");
		}
		if (mm < 0) {
			bf.append("00:");
		} else if (mm < 10) {
			bf.append("0" + mm + ":");
		} else {
			bf.append(mm + ":");
		}
		if (ss < 0) {
			bf.append("00");
		} else if (ss < 10) {
			bf.append("0" + ss);
		} else {
			bf.append(ss);
		}
		hhmmss = bf.toString();
		System.out.println(hhmmss);
		return hhmmss;
	}

	/**
	 * 比较2个时间的大小
	 */
	public static boolean compareTime(Date time1, Date time2) {
		return time1.before(time2);
	}

	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH);
	}

	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取某个日期距离2000 年1月1日的毫秒数
	 * @param date
	 * @return
	 */
	private final static String ZERO = "2000/01/01 00:00:00";
	public static long getTimeMillonsFrom2000(Date date){
		return getTimeMillonsFrom2000(date.getTime());
	}
	
	public static long getTimeMillonsFrom2000(long date){
		Calendar cStart = Calendar.getInstance();
		cStart.clear();
		cStart.set(2000, 0, 1, 0, 0, 0);
//		Date start = DateUtil.stringToDate(ZERO, "yyyy/MM/dd hh:mm:ss");
//		cStart.setTime(start);
		long time = (date- cStart.getTimeInMillis());
		return time;
	}

	
	
	/**
	 * 
	 * @param now
	 * @return
	 */
	public static Calendar getCalendarFromTimeMillons(long now){
		Calendar cZero = Calendar.getInstance();
		cZero.set(2000, 0, 1, 0, 0, 0);
		Calendar cNow = Calendar.getInstance();
		cNow.setTimeInMillis(now  + cZero.getTimeInMillis());
		System.out.print("星期" + cNow.get(Calendar.DAY_OF_WEEK));
		return cNow;
	}
	
	public static long getDeffLongFromTimeMillons(long now){
		Calendar cZero = Calendar.getInstance();
		cZero.set(2000, 0, 1, 0, 0, 0);
		return now  - cZero.getTimeInMillis();
	}
	
	public static long getLongFromTimeMillons(long now){
		Calendar cZero = Calendar.getInstance();
		cZero.set(2000, 0, 1, 0, 0, 0);
		return cZero.getTimeInMillis() + now ;
	}
	
	public static Date getDateFromTimeMillons(long now){
		XLog.e("==================星期" + getCalendarFromTimeMillons(now).getTime().toLocaleString());
		return  getCalendarFromTimeMillons(now).getTime();
	}

	public static String getDateStringFromTimeMilloons(long now,String sdf){
		return dateToString(getDateFromTimeMillons(now), sdf);
	}
	
	public static String getDateStringFromTimeMilloons(long now){
		return dateToString(getDateFromTimeMillons(now), 	"yyyyMMdd HHmmss");
	}


	
	
	/**
	 * 根据秒得到 hh:mm:ss数据
	 * @param seconds
	 * @return
	 */
	public static String formateTime(long seconds) {
		StringBuffer sb = new StringBuffer();
		if (seconds<60) {
			sb.append("00:00:");
			sb.append(getString(seconds));
		}else{
			int min = (int) Math.floor(seconds/60f);
			long second = seconds%60;
			if (min<60) {
				sb.append("00:")
				.append(getString(min))
				.append(":")
				.append(getString(second));
			}else{
				int hour =(int) Math.floor(min/60f);
				int newMin = min%60;
				sb.append(getString(hour))
					.append(":")
					.append(getString(newMin))
					.append(":")
					.append(getString(second));
			}
		}
		return sb.toString();
	}
	
	
	public static String formateTime3(long seconds,Context context) {
		 String hourUnit = context.getString(R.string.hour);
		 String minUnit = context.getString(R.string.minute);
		 
		StringBuffer sb = new StringBuffer();
		if (seconds<60) {
			sb.append("00"+hourUnit+"00"+minUnit);
			sb.append(getString(seconds));
		}else{
			int min = (int) Math.floor(seconds/60f);
			long second = seconds%60;
			if (min<60) {
				sb.append("00"+hourUnit)
				.append(getString(min))
				.append(minUnit)
				.append(getString(second));
			}else{
				int hour =(int) Math.floor(min/60f);
				int newMin = min%60;
				sb.append(getString(hour))
					.append(hourUnit)
					.append(getString(newMin))
					.append(minUnit)
					.append(getString(second));
			}
		}
		return sb.toString();
	}
	
	public static String formateTime2(long seconds,Context context){
		StringBuffer sb = new StringBuffer();
		String hourUnit = context.getString(R.string.hour);
		String minuteUnit = context.getString(R.string.minute);
		if (seconds < 60) {
			sb.append("00").append(hourUnit).append("00")
			.append(minuteUnit);
		}else{
			int min = (int) Math.floor(seconds/60f);
			if (min <60) {
				sb.append("00").append(hourUnit)
					.append(getString(min))
					.append(minuteUnit);
			}else{
				int hour =(int) Math.floor(min/60f);
				int newMin = min%60;
				sb.append(getString(hour))
					.append(hourUnit)
					.append(getString(newMin))
					.append(minuteUnit)
				;
			}
		}
		
		return sb.toString();
	}
	public static String getString(long value){
		String valueOf = String.valueOf(value);
		if (valueOf.length()==1) {
			return "0"+valueOf;
		}
		return valueOf;
	}
	
	
}
