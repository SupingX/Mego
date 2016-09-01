package com.mycj.jusd.protocol;


import java.util.Calendar;
import java.util.Date;

import android.util.Log;

import com.laputa.blue.util.DataUtil;
import com.laputa.blue.util.XLog;
import com.mycj.jusd.bean.RemindSetting;
import com.mycj.jusd.bean.WatchSetting;
import com.mycj.jusd.bean.news.SportPlanSetting;
import com.mycj.jusd.util.JunUtil;

public class ProtocolWriteManager extends AbstractProtolWrite {
	private  String getMissingCallSmsHexString(int value) {
		String result = "";
		String hex = Integer.toHexString(value);
		if (hex.length() == 1) {
			result = "000" + hex;
		} else if (hex.length() == 2) {
			result = "00" + hex;
		} else if (hex.length() == 3) {
			result = "0" + hex;
		}
		Log.e("", "未接电话result  : " + result);
		return result;
	}
	/**
	 * 未接短信和电话
	 * @param phone
	 * @param sms
	 * @return
	 */
	public  byte[] writeForPhoneAndSmsCount(int phone,int sms) {
		StringBuffer sb = new StringBuffer();
		sb.append("AAF7");
		sb.append(getMissingCallSmsHexString(sms));
		sb.append(getMissingCallSmsHexString(phone));
		return DataUtil.hexStringToByte(sb.toString());
	}
	private final static String startDate = "2000/01/01 00:00:00";
	private final static String WRITE_TIME = "02";
	private final static String WRITE_WATCH_SETTING = "03";
	private final static String WRITE_SPORT_PLAN = "04";
	private final static String WRITE_REMIND = "05";
	private final static String WRITE_REQUEST_WATCH_CONFIGRATION = "06";
	private final static String WRITE_REQUEST_STEP_OR_PATH = "07";
	private final static String WRITE_REQUEST_HISTORY_STEP = "09";
	private final static String WRITE_REQUEST_HISTORY_PATH = "08";
	private final static String WRITE_REQUEST_HISTORY_SLEEP = "08";
	private final static String WRITE_REQUEST_HISTORY_DELETE = "0B";
	private final static String WRITE_REQUEST_RESET = "0C";
		
	private static ProtocolWriteManager mInstance;
	private ProtocolWriteManager(){}
	public static ProtocolWriteManager getInstance(){
		if (null == mInstance) {
			mInstance = new ProtocolWriteManager();
		}
		return mInstance;
	}
	
//	@Override
//	public byte[] getByteForTimeSync(long dateTime) {
//		try {
//			long now  = com.mycj.jusd.util.DateUtil.getDeffLongFromTimeMillons(dateTime)/1000L;
//			String nowStr = String.valueOf(Long.toHexString(now));
//			String zero = "";
//			int num = 8-nowStr.length();
//			for (int i = 0; i < num; i++) {
//				zero+="0";
//			}
//			StringBuffer sb = new StringBuffer();
//			sb.append(WRITE_TIME);
//			sb.append(zero);
//			sb.append(nowStr);
//			sb.append("00");
//			XLog.i(JsdProtolWrite.class,"--> 时间同步："+ sb.toString());
//			return DataUtil.hexStringToByte(sb.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public byte[] getByteForTimeSync(int year,int month,int day ,int hour,int minute,int second) {
		StringBuffer sb = new StringBuffer();
		sb.append(WRITE_TIME);
		sb.append(toHexString(year-2000<=0?0:year-2000, 2));
		sb.append(toHexString(month, 2));
		sb.append(toHexString(day, 2));
		sb.append(toHexString(hour, 2));
		sb.append(toHexString(minute, 2));
		sb.append(toHexString(second, 2));
		sb.append(toHexString(0, 2));
		sb.append(toHexString(0, 2));
		XLog.i(ProtocolWriteManager.class,"--> 时间同步："+ sb.toString());
		return DataUtil.hexStringToByte(sb.toString());
	}
	public byte[] getByteForTimeSync() {
		Calendar c = Calendar.getInstance();
		c.clear();
		Date date = new Date();
		c.setTime(date);
		//02 0d 1b 01 0a 08 1b 0000
		return this.getByteForTimeSync(c.get(Calendar.YEAR)
				, c.get(Calendar.MONTH+1)
				, c.get(Calendar.DAY_OF_MONTH)
				, c.get(Calendar.HOUR_OF_DAY)
				, c.get(Calendar.MINUTE)
				, c.get(Calendar.SECOND));
	}
	
	
	private String toHexString(long value , int size){
		String result = "";
		String nowStr = String.valueOf(Long.toHexString(value));
		String zero = "";
		int num = size-nowStr.length();
		for (int i = 0; i < num; i++) {
			zero+="0";
		}
		result = zero + nowStr;
		return result;
	}
	
	@Override
	public byte[] getByteForWatchSetting(int unit, int height, int weight, int year,int month,int day, int sex, int stepOnoff,int walkLength, int runLength, int hrOnoff, int hrMax, int hrMin, int sleepOnoff, int sleepHour,
			int sleepMin, int awakHour, int awakMin) {
		//  030100AA00780200000A000A0015D21600080000
		//  0301005200780200000A000A0015D21600080000
		
		try {
			String uintStr = toHexString(unit, 2);
			String heightStr = toHexString(height, 4);
			String weightStr = toHexString(weight, 4);
			String yearStr = toHexString(year, 2);
			String monthStr = toHexString(month, 2);
			String dayStr = toHexString(day, 2);
			String walkLengthStr = toHexString(walkLength, 4);
			String runLengthStr = toHexString(runLength, 4);
			//获得0b111;
			int onOff = 0b0000;
			onOff ^= stepOnoff==1?0b100:0b000;
			onOff ^= hrOnoff==1?0b001:0b000;
			onOff ^= sleepOnoff==1?0b010:0b000;
			onOff ^= sex==1?0b1000:0b000;
			String onoffStr = toHexString(onOff, 2);
			Log.e("", "-->"+onOff);
			String hrMaxStr = toHexString(hrMax, 2);
			String hrMinStr = toHexString(hrMin, 2);
			String sleepHourStr = toHexString(sleepHour, 2);
			String sleepMinStr = toHexString(sleepMin, 2);
			String awakHourStr = toHexString(awakHour, 2);
			String awakMinStr = toHexString(awakMin, 2);
			
			StringBuffer sb = new StringBuffer();
			sb.append(WRITE_WATCH_SETTING)
				.append(uintStr).append(heightStr).append(weightStr).append(yearStr)
				.append(monthStr).append(dayStr).append(walkLengthStr).append(runLengthStr).append(onoffStr)
				.append(hrMinStr).append(hrMaxStr).append(sleepHourStr)
				.append(sleepMinStr).append(awakHourStr).append(awakMinStr)
				.append("00")
				;
			XLog.i(getClass(),"--> 手表设置：" + sb.toString());
			return DataUtil.hexStringToByte(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public byte[] getByteForWatchSetting(WatchSetting setting) {
		int[] yearMonthDay = JunUtil.getYearMonthDay(setting.getBirthday());
		return this.getByteForWatchSetting(setting.getUnit(), setting.getHeight(), setting.getWeight(), yearMonthDay[0], yearMonthDay[1], yearMonthDay[2],
				 setting.getSex(),setting.getStepOnoff(), setting.getWalkLength(), setting.getRunLength(), setting.getHrOnoff()
				, setting.getHrMax(), setting.getHrMin(), setting.getSleepOnoff()
				, setting.getSleepHour(), setting.getSleepMin(), setting.getAwakHour(), setting.getAwakMin());
	}

	@Override
	public byte[] getByteForSportPlanSetting(int sportPlanOnoff, int startHour, int startMin, int sportGoal, int sportGoalDistance, int autoSignOnoff, int signDistance, int paceOnoff, int paceMin,
			int paceMax, int sportStyle,int goalOnoff) {
		try {
			int onOff = 0b0000;
			onOff ^= paceOnoff==1?0b100:0b000;
			onOff ^= sportPlanOnoff==1?0b001:0b000;
			onOff ^= autoSignOnoff==1?0b010:0b000;
			onOff ^= autoSignOnoff==1?0b1000:0b000;
			Log.e("", "-->"+onOff);
			String onoffStr = toHexString(onOff, 2);
			String startStr = toHexString(startHour, 2) + toHexString(startMin, 2);
			String sportGoalStr = toHexString(sportGoal, 6);
			String sportGoalDistanceStr = toHexString(sportGoalDistance, 4);
			String signDistanceStr = toHexString(signDistance, 4);
			String paceMinStr = toHexString(paceMin, 4);
			String paceMaxStr = toHexString(paceMax, 4);
			String sportStyleStr = toHexString(sportStyle, 2);
			
			StringBuffer sb = new StringBuffer();
			sb.append(WRITE_SPORT_PLAN);
			sb.append(onoffStr).append(startStr).append(sportGoalStr).append(sportGoalDistanceStr)
				.append(signDistanceStr)
				.append(paceMinStr)
				.append(paceMaxStr).append(sportStyleStr);
			sb.append("00");
			sb.append("00");
			sb.append("00");
			sb.append("00");
			XLog.i(getClass(),"--> 运动计划：" + sb.toString());
			return DataUtil.hexStringToByte(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public byte[] getByteForSportPlanSetting(SportPlanSetting setting) {
		return this.getByteForSportPlanSetting(setting.getSportPlanOnoff(),setting.getStartHour() , setting.getStartMin(), setting.getGoalStep(), setting.getGoalDistance()
				, setting.getAutoSignOnoff(), setting.getAutoSignDistance(), setting.getPaceSettingOnoff() , Integer.valueOf(setting.getPaceMin())
				, Integer.valueOf(setting.getPaceMax()), setting.getSportStyle(),setting.getSportGoalOnoff());
	}

	@Override
	public byte[] getByteForRemindSetting(int incomingOnoff, int alarmOnoff, int alarmHour, int alarmMin, int longSitOnoff, int longsitValue, int birthdayOnoff,
			int birthdayHour, int birthdayMin, int nowTimeOnoff) {
		int onOff = 0b0000;
		//开关：来电提醒/每日闹铃/久坐提醒/生日提醒/整点报时
		onOff ^= incomingOnoff==1?0b00001:0;
		onOff ^= incomingOnoff==1?0b00010:0;
		onOff ^= incomingOnoff==1?0b00100:0;
		onOff ^= incomingOnoff==1?0b01000:0;
		onOff ^= incomingOnoff==1?0b10000:0;
		String onoffStr = toHexString(onOff, 2);
		Log.e("", "-->"+onOff);
		String alarmHourStr = toHexString(alarmHour, 2);
		String alarmMinStr = toHexString(alarmMin, 2);
		String longsitValueStr = toHexString(longsitValue, 4);
		String birthdayMonthStr = toHexString(0, 2);
		String birthdayDayStr = toHexString(0, 2);
		String birthdayHourStr = toHexString(birthdayHour, 2);
		String birthdayMinStr = toHexString(birthdayMin, 2);
		
		StringBuffer sb = new StringBuffer();
		sb.append(WRITE_REMIND);
		sb.append(onoffStr)
			.append(alarmHourStr)
			.append(alarmMinStr)
			.append(longsitValueStr)
			.append(birthdayMonthStr)
			.append(birthdayDayStr)
			.append(birthdayHourStr)
			.append(birthdayMinStr)
			;
		XLog.i(getClass(),"--> 个人提醒 ：" + sb.toString());
		return DataUtil.hexStringToByte(sb.toString());
	}

	@Override
	public byte[] getByteForRemindSetting(RemindSetting setting) {
		int incomingOnoff = setting.getIncomingOnoff();
		int alarmOnoff = setting.getAlarmOnoff();
		int alarmHour = setting.getAlarmHour();
		int alarmMin = setting.getAlarmMin();
		
		int longSitOnoff = setting.getLongSitOnoff();
		int longsitValue = setting.getLongSitValue();
		int birthdayOnoff = setting.getBirthdayOnoff() ;
//		int birthdayMonth = setting.getBirthdayMonth();
//		int birthdayDay = setting.getBirthdayDay();
		int birthdayHour = setting.getBirthdayHour();
		int birthdayMin = setting.getBirthdayMin();
		int nowTimeOnoff = setting.getNowtimeOnoff();
		return this.getByteForRemindSetting(incomingOnoff, alarmOnoff, alarmHour, alarmMin, longSitOnoff, longsitValue, birthdayOnoff, birthdayHour, birthdayMin, nowTimeOnoff);
	}

	@Override
	public byte[] getByteForRequstWatchConfigration(int type) {
//		0x01——请求支持的功能；0x03——请求手表设置；0x04——请求计划运动设置；0x05——请求个人提醒设置
		String typeStr = toHexString(type, 2);
		StringBuffer sb = new StringBuffer();
		sb.append(WRITE_REQUEST_WATCH_CONFIGRATION);
		sb.append(typeStr);
		sb.append("00");
		XLog.i(getClass(),"--> 请求手表配置信息 ：" + sb.toString());
		return DataUtil.hexStringToByte(sb.toString());
	}

	@Override
	public byte[] getByteForRequstCurrentSportInfo() {

		StringBuffer sb = new StringBuffer();
		sb.append(WRITE_REQUEST_STEP_OR_PATH);
		sb.append("00");
		sb.append("00");
		sb.append("00");
		XLog.i(getClass(),"--> 请求当前计步或轨迹数据 ：" + sb.toString());
		return DataUtil.hexStringToByte(sb.toString());
	}
	

	
	@Override
	public byte[] getByteForRequestHistorySportInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append(WRITE_REQUEST_HISTORY_STEP);
		sb.append("00");
		sb.append("00");
		sb.append("00");
		XLog.i(getClass(),"--> 请求历史计步：" + sb.toString());
		return DataUtil.hexStringToByte(sb.toString());
		
	}

/*	@Override
	public byte[] getByteForRequestHistoryPath(int type,long date) {
		String typeStr = toHexString(type, 2);
		long now  =com.mycj.jusd.util.DateUtil.getTimeMillonsFrom2000(date)/1000L;
		StringBuffer sb = new StringBuffer();
		sb.append(WRITE_REQUEST_HISTORY_PATH);
		sb.append(typeStr);
		sb.append(toHexString(now, 8));
		XLog.i(getClass(),"--> 请求历史轨迹：" + sb.toString());
		return DataUtil.hexStringToByte(sb.toString());
	}*/

	@Override
	public byte[] getByteForRequestHistorySleep() {
		StringBuffer sb = new StringBuffer();
		sb.append(WRITE_REQUEST_HISTORY_SLEEP);
		sb.append("00");
		sb.append("00");
		sb.append("00");
		XLog.i(getClass(),"--> 请求历史睡眠：" + sb.toString());
		return DataUtil.hexStringToByte(sb.toString());
	}

	@Override
	public byte[] getByteForRequestDelete(int type, int subType,long startDate, long endDate) {
		String typeStr = toHexString(type, 2);
		String sbTypeStr = toHexString(subType, 2);
		long start  =com.mycj.jusd.util.DateUtil.getTimeMillonsFrom2000(startDate)/1000L;
		long end  =com.mycj.jusd.util.DateUtil.getTimeMillonsFrom2000(startDate)/1000L;
		StringBuffer sb = new StringBuffer();
		sb.append(WRITE_REQUEST_HISTORY_DELETE);
		sb.append(typeStr);
		sb.append(sbTypeStr);
		sb.append(toHexString(start, 8));
		sb.append(toHexString(end, 8));
		XLog.i(getClass(),"--> 请求历史删除：" + sb.toString());
		return DataUtil.hexStringToByte(sb.toString());
	}
	
	/**
	 * 删除所有
	 * @param type 02:睡面 其他计步
	 * @return
	 */
	public byte[] getByteForRequestDelete(int type){
		return getByteForRequestDelete(type, 01, 1, 1);
	}

	@Override
	public byte[] getByteForRequestResetFactorySetting() {
		StringBuffer sb = new StringBuffer();
		sb.append(WRITE_REQUEST_RESET);
		sb.append("00");
		sb.append("00");
		sb.append("00");
		XLog.i(getClass(),"--> 清楚所有设置：" + sb.toString());
		return DataUtil.hexStringToByte(sb.toString());
	}
	
	
	public  byte[] writeForIncomingNumber(String number,boolean open){
		StringBuffer sb = new StringBuffer();
		sb.append("0D");
		sb.append(open?"80":"00");
		sb.append("00");
		Log.i("xpl", "通知来电 : " + number);
		int length = number.length();
		sb.append(DataUtil.toHexString(length));
		Log.i("xpl", "通知来电数量 : " + DataUtil.toHexString(length));
	/*	for (int i = 0; i < number.length(); i++) {
			int num = Integer.valueOf(number.charAt(i));
			sb.append(DataUtil.toHexString(num));
		}
		*/
		if (length %2 !=0) {
			sb.append(number+"0");
		}else{
			sb.append(number);
		}
		Log.i("xpl", "通知来电 : " + sb.toString());
//		F1 00 00 0B 130408154540
		return DataUtil.hexStringToByte(sb.toString());
	}
	
	
}
