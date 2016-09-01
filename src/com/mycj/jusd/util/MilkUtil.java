package com.mycj.jusd.util;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.text.format.DateUtils;
import android.util.Log;

import com.mycj.jusd.bean.news.InfoA;
import com.mycj.jusd.bean.news.InfoB;
import com.mycj.jusd.bean.news.SleepHistory;
import com.mycj.jusd.bean.news.SportHistory;

/**
 * 提供模拟数据的类
 * 
 * @author Administrator
 *
 */
public class MilkUtil {
	private static Random random;
	private static Calendar c;

	static {
		c = Calendar.getInstance();
		random = new Random();
	}
	
	//shouxian dingyigexiaode mubiao  biru wo yao zuo qingfengzui qiangde guangeren.
	
	
	public void wuliao(){
		
//		DateUtils.formatDateTime(context, millis, flags)
		//1.ji
	
	}

	private static void setDate() {
		c.clear();
		Date date = new Date();
		c.setTime(date);
	}

	public static List<SleepHistory> getHistorySleepInfoList() {
		List<SleepHistory> list = new ArrayList<>();
		setDate();
		for (int i = 0; i < 30; i++) {
			c.set(Calendar.DAY_OF_MONTH, i + 1);    
			int sleepIndex = i;
			String sleepDate = DateUtil.dateToString(c.getTime());
			String startTime = "2200";
			String endTime = "0700";
			int sleepDeep = random.nextInt(12 * 60);
			int sleepLight = random.nextInt(12 * 60);
			SleepHistory sleep = new SleepHistory(sleepIndex, sleepDate,
					startTime, endTime, sleepDeep, sleepLight);
			list.add(sleep);
		}

		return list;
	}

	public static void saveHistorySleepInfoList() {
		setDate();
		for (int i = 0; i < 30; i++) {
			c.set(Calendar.DAY_OF_MONTH, i + 1);
			String sleepDate = DateUtil.dateToString(c.getTime());
			for (int j = 0; j < 2; j++) {
				int sleepIndex = j;
				String startTime = "220" + j;
				String endTime = "0" + j + "00";
				int sleepDeep = random.nextInt(6 * 60);
				int sleepLight = random.nextInt(5 * 60);
				SleepHistory sleep = new SleepHistory(sleepIndex, sleepDate,
						startTime, endTime, sleepDeep, sleepLight);
				sleep.save();
			}
		}
	}

	private static String getSportDate(Calendar c, int j) {
		c.set(Calendar.HOUR_OF_DAY, 2);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.add(Calendar.SECOND, j * 3);
		String result = DateUtil.dateToString(c.getTime(), "yyyyMMdd hh:mm:ss");
		Log.i("zeej", "__________日期  :" + result);
		return result;
	}

	private static String getSportTime(Calendar c, int j) {
		c.set(Calendar.HOUR_OF_DAY, 2);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.add(Calendar.SECOND, j * 3600);
		String result = DateUtil.dateToString(c.getTime(), "hhmmss");
		Log.i("zeej", "__________日期  :" + result);
		return result;
	}

	private static String getSportTime2(Calendar c, int j, int k) {
		c.set(Calendar.HOUR_OF_DAY, 2);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.add(Calendar.SECOND, j * 3600 + k * 5);
		String result = DateUtil.dateToString(c.getTime(), "hhmmss");
		Log.i("zeej", "__________日期  :" + result);
		return result;
	}

	/**
	 * 模拟保存运动信息 30天每一天运动5次每次的时间是0点后 1小时后 2小时后 3小时后 4小时后。
	 */
	public static void saveSportHistoryList() {
		setDate();
		for (int i = 0; i < 30; i++) {
			c.set(Calendar.DAY_OF_MONTH, i + 1);
			String sportDate = DateUtil.dateToString(c.getTime(), "yyyyMMdd");
			int type = random.nextInt(3);
			for (int j = 0; j < 5; j++) {
				// SportHistory
				int sportIndex = j;
				for (int k = 0; k < random.nextInt(100); k++) {
					int step = 0;
					if (type != 2) {
						step = random.nextInt(50);
					} else {
						step = 0;
					}
					int calorie = random.nextInt(2);
					int distance = random.nextInt(2);
					int consuming = random.nextInt(2);
					int sportNo = k;
					int pace = random.nextInt(100); ;
					float freq = random.nextInt(100);;
					int hr_ = random.nextInt(100);;
					int speed = random.nextInt(100);;
					String sportTimeNum = getSportTime2(c, j,k);
					int signed = k%2;
					long lat=random.nextInt(100);;
					long lng = random.nextInt(100);;
					
					SportHistory sportHistory = new SportHistory(sportDate, sportTimeNum, type, sportIndex, sportNo, step, distance, calorie, consuming, hr_,
							pace, speed, freq, signed, lat, lng);
					sportHistory.save();
					
					
//					// SportInfoHistory
//					SportInfoHistory sportInfoHistory = new SportInfoHistory(sportDate, sportTimeNum, sportIndex, sportNo, pace,speed, freq, hr_);
//					sportInfoHistory.save();
//					

//					// SportLocationHistroy
//					SportLocationHistory sportLocationHistroy = new SportLocationHistory(sportDate, sportTimeNum, sportIndex, sportNo, signed, lat, lng);
//					sportLocationHistroy.save();
				}
				
				
			}
		}
	}

	/**
	 * 模拟运动轨迹信息 30天每一天运动5次每次的时间是0点后 1小时后 2小时后 3小时后 4小时后。
	 */
	public static void saveSportLocationList() {
		/*setDate();
		for (int i = 0; i < 30; i++) {
			c.set(Calendar.DAY_OF_MONTH, i + 1);
			String sportDate = DateUtil.dateToString(c.getTime(), "yyyyMMdd");
			for (int j = 0; j < 3; j++) {
				int sportNo = j;
				for (int k = 0; k < 10; k++) {
					String sportTime = getSportTime2(c, j, k);
					int sportIndex = k;
					int signed = k % 2;
					long lat = random.nextLong();
					long lng = random.nextLong();
					SportLocationHistory sportLocationHistory = new SportLocationHistory(
							sportDate, sportTime, sportNo, sportIndex, signed,
							lat, lng);
					sportLocationHistory.save();
				}
			}
		}*/
	}
	
	
	public static void loadData() {
		Date date = new Date();
		int monthMaxDay = DateUtil.getMonthMaxDay(date);
		ArrayList<InfoA> infoAs = new ArrayList<InfoA>();
		ArrayList<InfoB> infoBs = new ArrayList<InfoB>();
		Random random = new Random();
		// 一个月
		for (int i = 0; i < monthMaxDay; i++) {
			long time = date.getTime();
			//一天运动10次
			for (int j = 0; j < 5; j++) {
				int index = j;
				// 一次有20个记录
				int step = 0;
				int distance = 0;
				int calorie = 0;
				for (int k = 0; k < 5; k++) {
					int type = random.nextInt(3);
					int no = k;
					long datetime = time + j *60 *60 + k*5;//每次间隔1小时，每个数据间隔5秒。
					step += random.nextInt(200);
					distance += random.nextInt(20);
					calorie+=random.nextInt(20);
					int hr = random.nextInt(220);
					InfoA infoA = new InfoA(datetime, index, no, type, step, distance, calorie, 5, hr);
//					infoAs.add(infoA);
					infoA.save();
					long lat = random.nextInt(20000);
					long lng = random.nextInt(20000);
					int signed = random.nextInt(2);
					InfoB infoB = new InfoB(datetime, index, no, lat, lng, signed);
//					infoBs.add(infoB);
					infoB.save();
				}
			}
		}
	}

}
