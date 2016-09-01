/*package com.mycj.jusd.protocol;


import com.laputa.blue.util.DataUtil;
import com.laputa.blue.util.XLog;
import com.mycj.jusd.bean.RemindSetting;
import com.mycj.jusd.bean.WatchSetting;
import com.mycj.jusd.bean.news.SleepHistory;
import com.mycj.jusd.bean.news.SportPlanSetting;
import com.mycj.jusd.ui.activity.SportInfo;
import com.mycj.jusd.util.DateUtil;

public class JsdProtolNotify extends AbstractProtolNotify {
	public static final int NOTIFY_SUPPORT_FEATURE = 1;
	public static final int NOTIFY_WATCH_SETTING = 3;
	public static final int NOTIFY_SPORT_PLAN = 4;
	public static final int NOTIFY_REMIND = 5;
	public static final int NOTIFY_STEP_OR_PATH_INFO = 0xB7;
	public static final int NOTIFY_SYNC_HISTORY_STEP_STATUS = 0xB7;
	public static final int NOTIFY_SYNC_HISTORY_STEP = 0xA7;
	public static final int NOTIFY_SYNC_HISTORY_PATH_STATUS = 0xF8;
	public static final int NOTIFY_SYNC_HISTORY_PATH = 0x08;
	public static final int NOTIFY_SYNC_HISTORY_SLEEP_STATUS = 0xFA;
	public static final int NOTIFY_SYNC_HISTORY_SLEEP_ = 0x0A;

	private static JsdProtolNotify notify;

	private JsdProtolNotify() {

	}

	public static JsdProtolNotify instance() {
		if (notify == null) {
			notify = new JsdProtolNotify();
		}
		return notify;
	}

	public int getProtolNotifyType(byte[] data) {
		if (data == null) {
			return -1;
		}
		String value = DataUtil.byteToHexString(data);
		String type = value.substring(0, 2);
		return Integer.valueOf(type);
	}

	@Override
	public int[] parseSupportFeature(byte[] data) {
		if (data == null) {
			XLog.e(getClass(), "数据为空");
			return null;
		}
		if (data[0] != NOTIFY_SUPPORT_FEATURE || data.length != 2) {
			XLog.e(getClass(), "数据格式不支持0x01");
			return null;
		}
		int[] supportFeatures = new int[4]; //
		String value = DataUtil.byteToHexString(data);
		String supportFeature = value.substring(2, 4);
		int supportFeatureInteger = Integer.valueOf(supportFeature, 16);
		supportFeatures[0] = ((supportFeatureInteger & 0b0001) == 0b0001) ? 1
				: 0;
		supportFeatures[1] = ((supportFeatureInteger & 0b0010) == 0b0010) ? 1
				: 0;
		supportFeatures[2] = ((supportFeatureInteger & 0b0100) == 0b0100) ? 1
				: 0;
		supportFeatures[3] = ((supportFeatureInteger & 0b1000) == 0b1000) ? 1
				: 0;
		XLog.i(getClass(), "==>支持功能 ：" + supportFeatures[0] + ","
				+ supportFeatures[1] + "," + supportFeatures[2] + ","
				+ supportFeatures[3]);
		return supportFeatures;
	}

	@Override
	public WatchSetting parseWatchSetting(byte[] data) {
		if (data == null) {
			XLog.e(getClass(), "数据为空");
			return null;
		}
		if (data[0] != NOTIFY_WATCH_SETTING || data.length != 18) {
			XLog.e(getClass(), "数据格式不支持0x03");
			return null;
		}

		String value = DataUtil.byteToHexString(data);
		int unit = Integer.valueOf(value.substring(2, 4), 16);
		int height = Integer.valueOf(value.substring(4, 8), 16);
		int weight = Integer.valueOf(value.substring(8, 12), 16);
		int age = Integer.valueOf(value.substring(12, 14), 16);
		int sex = Integer.valueOf(value.substring(14, 16), 16);
		int walkLength = Integer.valueOf(value.substring(16, 18), 16);
		int runLength = Integer.valueOf(value.substring(18, 20), 16);
		int hrOnoff = Integer.valueOf(value.substring(20, 22), 16);
		int hrMin = Integer.valueOf(value.substring(22, 24), 16);
		int hrMax = Integer.valueOf(value.substring(24, 26), 16);
		int sleepOnoff = Integer.valueOf(value.substring(26, 28), 16);
		String sleepStr = value.substring(28, 32);
		String awakStr = value.substring(32, 36);
		String sleepTime = Integer.valueOf(sleepStr.substring(0, 2), 16) + ":"
				+ Integer.valueOf(sleepStr.substring(2, 4), 16);
		String awakTime = Integer.valueOf(awakStr.substring(0, 2), 16) + ":"
				+ Integer.valueOf(awakStr.substring(2, 4), 16);
		;
		WatchSetting setting = new WatchSetting(unit, height, weight, age, sex,
				1,walkLength, runLength, hrOnoff, hrMin, hrMax, sleepOnoff,
				sleepTime, awakTime);
		XLog.i(getClass(), "==>手表设置 ：" + setting.toString());
		return setting;
	}

	@Override
	public SportPlanSetting parseSportPlanSetting(byte[] data) {
		if (data == null) {
			XLog.e(getClass(), "数据为空");
			return null;
		}
		if (data[0] != NOTIFY_SPORT_PLAN || data.length != 17) {
			XLog.e(getClass(), "数据格式不支持0x04");
			return null;
		}
		// 解析
		String value = DataUtil.byteToHexString(data);
		// new SportPlanSetting()
		int sportPlanOnoffInt = Integer.valueOf(value.substring(2, 4), 16);
		boolean sportPlanOnoff = sportPlanOnoffInt == 1 ? true : false;
		int startHour = Integer.valueOf(value.substring(4, 6), 16);
		int startMin = Integer.valueOf(value.substring(6, 8), 16);
		String startTime = String.valueOf(startHour) + ":"
				+ String.valueOf(startMin);
		int goalStep = Integer.valueOf(value.substring(8, 12), 16);
		int goalDistance = Integer.valueOf(value.substring(12, 16), 16);
		boolean autoSignOnoff = Integer.valueOf(value.substring(16, 18), 16) == 1 ? true
				: false;
		int autoSignDistance = Integer.valueOf(value.substring(18, 20), 16);
		boolean paceSettingOnoff = Integer.valueOf(value.substring(20, 22), 16) == 1 ? true
				: false;
		String paceMin = String.valueOf(Integer.valueOf(
				value.substring(22, 26), 16));
		String paceMax = String.valueOf(Integer.valueOf(
				value.substring(26, 30), 16));
		boolean sportStyleOnoff = Integer.valueOf(value.substring(30, 32), 16) == 1 ? true
				: false;
		int sportStyle = Integer.valueOf(value.substring(32, 34), 16);
		SportPlanSetting setting ;
//		= new SportPlanSetting(sportPlanOnoff,
//				startTime, goalStep, goalDistance, autoSignOnoff,
//				autoSignDistance, paceSettingOnoff, paceMin, paceMax,
//				sportStyleOnoff, sportStyle);
//		XLog.i(getClass(), "==>运动计划 ：" + setting.toString());
		return setting = null;
	}

	@Override
	public RemindSetting parseRemindSetting(byte[] data) {
		if (data == null) {
			XLog.e(getClass(), "数据为空");
			return null;
		}
		if (data[0] != NOTIFY_REMIND || data.length != 14) {
			XLog.e(getClass(), "数据格式不支持0x05");
			return null;
		}
		// 解析
		String value = DataUtil.byteToHexString(data);
		// 个人提醒 ：05 01 01 0d 16 01 0021 01 05 0d 16 17 01
		boolean incomingOnoff = Integer.valueOf(value.substring(2, 4), 16) == 1 ? true
				: false;
		boolean alarmOnoff = Integer.valueOf(value.substring(4, 6), 16) == 1 ? true
				: false;
		;
		String alarmTime = String.valueOf(Integer.valueOf(
				value.substring(6, 8), 16))
				+ ":"
				+ String.valueOf(Integer.valueOf(value.substring(8, 10), 16));
		boolean longSitOnoff = Integer.valueOf(value.substring(10, 12), 16) == 1 ? true
				: false;
		int longSitValue = Integer.valueOf(value.substring(12, 16), 16);
		boolean birthdayOnoff = Integer.valueOf(value.substring(16, 18), 16) == 1 ? true
				: false;
		;
		String birthdayDate = String.valueOf(Integer.valueOf(
				value.substring(18, 20), 16))
				+ "/"
				+ String.valueOf(Integer.valueOf(value.substring(20, 22), 16));
		;
		String birthdayTime = String.valueOf(Integer.valueOf(
				value.substring(22, 24), 16))
				+ ":"
				+ String.valueOf(Integer.valueOf(value.substring(24, 26), 16));
		;
		boolean nowtimeOnoff = Integer.valueOf(value.substring(26, 28), 16) == 1 ? true
				: false;
		;
		RemindSetting setting = new RemindSetting(incomingOnoff, alarmOnoff,
				alarmTime, longSitOnoff, longSitValue, birthdayOnoff,
				birthdayDate, birthdayTime, nowtimeOnoff);
		XLog.i(getClass(), "==>个人提醒 ：" + setting.toString());
		return setting;
	}

	@Override
	public SportInfo parseSportInfo(byte[] data) {
		if (data == null) {
			XLog.e(getClass(), "数据为空");
			return null;
		}
		if (data[0] != NOTIFY_STEP_OR_PATH_INFO || data.length != 15) {
			XLog.e(getClass(), "数据格式不支持0x07");
			return null;
		}
		// 解析
		String value = DataUtil.byteToHexString(data);
		int type = Integer.valueOf(value.substring(2, 4), 16);
		int step = Integer.valueOf(value.substring(4, 10), 16);
		int distance = Integer.valueOf(value.substring(10, 16), 16);
		int time = Integer.valueOf(value.substring(16, 22), 16);
		int calorie = Integer.valueOf(value.substring(22, 28), 16);
		int hrAvg = Integer.valueOf(value.substring(28, 30), 16);
		SportInfo info = new SportInfo(type, step, distance, time, calorie,hrAvg);
		XLog.i(getClass(), "==>当前运动数据 ：" + info.toString());
		return info;
	}

	@Override
	public int[] parseSyncHistoryStepStatus(byte[] data) {
		if (data == null) {
			XLog.e(getClass(), "数据为空");
			return null;
		}
		if (data[0] != NOTIFY_SYNC_HISTORY_STEP_STATUS || data.length != 4) {
			XLog.e(getClass(), "数据格式不支持0xF7");
			return null;
		}
		// 解析
		String value = DataUtil.byteToHexString(data);
		int type = Integer.valueOf(value.substring(2, 4), 16);
		int count = Integer.valueOf(value.substring(4, 8), 16);
		XLog.i(getClass(), "==>历史计步数据同步状态  type ：" + type + ",count :" + count);
		return new int[] { type, count };
	}

	@Override
	public Object parseHistoryStep(byte[] data) {
		if (data == null) {
			XLog.e(getClass(), "数据为空");
			return null;
		}
		// 解析
		String value = DataUtil.byteToHexString(data);
		int protocl = Integer.valueOf(value.substring(0, 2), 16);
		if (protocl != NOTIFY_SYNC_HISTORY_STEP || data.length != 20) {
			XLog.e(getClass(), "数据格式不支持0xA7");
			return null;
		}

		int type = Integer.valueOf(value.substring(2, 4), 16);
		long timeStamp = Integer.valueOf(value.substring(4, 12), 16);
		;
		int dataIndex = Integer.valueOf(value.substring(12, 16), 16);
		long steps = Integer.valueOf(value.substring(16, 22), 16);
		float distance = Integer.valueOf(value.substring(22, 28), 16) / 100F;
		int time = Integer.valueOf(value.substring(28, 32), 16);
		;
		float calorie = Integer.valueOf(value.substring(32, 38), 16) / 100F;
		int hr = Integer.valueOf(value.substring(38, 40), 16);
		String timeStampStr = DateUtil.dateToString(
				DateUtil.getDateFromTimeMillons(timeStamp), "yyyyMMdd");
		return null;
	}

	@Override
	public int[] parseSyncHistoryPathStatus(byte[] data) {
		if (data == null) {
			XLog.e(getClass(), "数据为空");
			return null;
		}
		if (data[0] != NOTIFY_SYNC_HISTORY_PATH_STATUS || data.length != 4) {
			XLog.e(getClass(), "数据格式不支持0xF8");
			return null;
		}
		// 解析
		String value = DataUtil.byteToHexString(data);
		int type = Integer.valueOf(value.substring(2, 4), 16);
		int count = Integer.valueOf(value.substring(4, 8), 16);
		XLog.i(getClass(), "==>历史计步数据同步状态  type ：" + type + ",count :" + count);
		return new int[] { type, count };
	}

	@Override
	public Object parseHistoryPath(byte[] data) {
		// 114.0506371740731
		if (data == null) {
			XLog.e(getClass(), "数据为空");
			return null;
		}
		if (data[0] != NOTIFY_SYNC_HISTORY_PATH || data.length != 28) {
			XLog.e(getClass(), "数据格式不支持0x08");
			return null;
		}
		// 解析
		String value = DataUtil.byteToHexString(data);

		long timeStamp = Long.valueOf(value.substring(2, 10), 16);
		int dataIndex = Integer.valueOf(value.substring(10, 12), 16);
		int markerIndex = Integer.valueOf(value.substring(12, 14), 16);
		long latAll = Long.valueOf(value.substring(14, 28), 16);
		long lngAll = Long.valueOf(value.substring(28, 42), 16);
		double latLong = getValue(latAll) * 1.0 / 10000000000000f;
		double lngLong = getValue(lngAll) * 1.0 / 10000000000000f;
		String lat = String.valueOf(latLong);
		String lng = String.valueOf(lngLong);
		int distance = Integer.valueOf(value.substring(42, 46), 16);
		long time = Long.valueOf(value.substring(46, 50), 16);
		int cal = Integer.valueOf(value.substring(50, 54), 16);
		int hr = Integer.valueOf(value.substring(54, 56), 16);
		return null;
	}

	*//**
	 * 最高位为符号位
	 * 
	 * @param value
	 * @return
	 *//*
	private long getValue(long value) {
		// value :4822678189205111
		// result :4822678189205111
		// 比如 int a = 0xXX;
		// 去掉最高位就是 a & 0x7F;
		// 拿最高位就是 (a >> 7) & 0x01
		long result = 0L;
		long deff = 0x7FFFFFFFFFFFFFL;
		long unit = (value >> 55) & 0b01;
		result = value & deff;
		// Log.e("laputa", "value :" + value);
		// Log.e("laputa", "unit :" + unit);
		// Log.e("laputa", "result :" + result);
		return unit == 1 ? -result : result;
	}

	@Override
	public int[] parseHistorySleepStatus(byte[] data) {
		// 解析
		String value = DataUtil.byteToHexString(data);
		if (data == null) {
			XLog.e(getClass(), "数据为空");
			return null;
		}
		int protocl = Integer.valueOf(value.substring(0, 2), 16);
		if (protocl != NOTIFY_SYNC_HISTORY_SLEEP_STATUS || data.length != 20) {
			XLog.e(getClass(), "数据格式不支持0xFA");
			return null;
		}

		int type = Integer.valueOf(value.substring(2, 4), 16);
		int count = Integer.valueOf(value.substring(4, 8), 16);
		XLog.i(getClass(), "==>历史睡眠数据同步状态  type ：" + type + ",count :" + count);
		return new int[] { type, count };
	}

	@Override
	public SleepHistory parseHistorySleep(byte[] data) {
		// 114.0506371740731
		if (data == null) {
			XLog.e(getClass(), "数据为空");
			return null;
		}
		if (data[0] != NOTIFY_SYNC_HISTORY_SLEEP_ || data.length != 20) {
			XLog.e(getClass(), "数据格式不支持0x0a");
			return null;
		}
		// 解析
		String value = DataUtil.byteToHexString(data);
		int type = Integer.valueOf(value.substring(2, 4), 16);

		long dateTime = Long.valueOf(value.substring(4, 12), 16);
		long startTime = Long.valueOf(value.substring(12, 20), 16);
		long endTime = Long.valueOf(value.substring(20, 28), 16);
		int lightTime = Integer.valueOf(value.substring(28, 32), 16);
		int deepTime = Integer.valueOf(value.substring(32, 36), 16);
		String dateTimeStr = DateUtil.dateToString(
				DateUtil.getDateFromTimeMillons(dateTime), "yyyyMMdd");

		SleepHistory info = new SleepHistory();
		XLog.i(getClass(), "==>当前历史睡眠数据 ：" + info.toString());
		return info;
	}

	// ==onCharacteristicChanged==C5:4C:BB:B0:BE:77 value :
	// 070005010022B80005A12A30004E4C6000000000
	// ==onCharacteristicChanged==C5:4C:BB:B0:BE:77 value :
	// 07010549F7F77483044C49F7F77483044C000000
	
	public synchronized CurrentSportData parseCurrentSportData(byte[] data) {
		// 114.0506371740731
		if (data == null) {
			XLog.e(getClass(), "数据为空");
			return null;
		}
		if (data[0] != 0x07 || data.length != 20) {
			XLog.e(getClass(), "数据格式不支持0x07");
			return null;
		}
		Log.i("zeej", "开始解析当前运动 " + sport);
		// 解析
		String value = DataUtil.byteToHexString(data);
		int type = Integer.valueOf(value.substring(2, 4), 16);
		int index = Integer.valueOf(value.substring(4, 6), 16);
		synchronized (sport) {
			if (sport != null) {
				// 是同一条数据 说明是后半段数据
				Log.i("zeej", "开始解析当前运动 index" + sport.getIndex());
				if (sport.getIndex() == index && type == 0x01) {
					Log.i("parseCurrentSportData", "后段");
					// 只有在后段数据完成接受才返回
					sport = null;
					CurrentSportData sportNew = new CurrentSportData();
					String lng = value.substring(6, 20);
					String lat = value.substring(20, 34);
					// long lng = 0;
					// long lat = 0;
					Log.i("zeej", "返回数据！！！");
					int sportType = sport.getType();
					long step = sport.getStep();
					long distance = sport.getDistance();
					long timeConsuming = sport.getTimeConsuming();
					long calorie = sport.getCalorie();
					int heartRate = sport.getHeartRate();
					sportNew = new CurrentSportData(sportType, step, distance,
							timeConsuming, calorie, heartRate, lng, lat);
					return sportNew;
				}
			} else {
				// 前半段数据
				if (type == 0x00) {
					Log.i("zeej", "前段");
					sport = new CurrentSportData();
					int sportType = Integer.valueOf(value.substring(6, 8), 16);
					long step = Long.valueOf(value.substring(8, 14), 16);
					long dsitance = Long.valueOf(value.substring(14, 20), 16);
					long timeConsuming = Long.valueOf(value.substring(20, 24),
							16);
					long calorie = Long.valueOf(value.substring(24, 30), 16);
					int hr = Integer.valueOf(value.substring(30, 32), 16);
					sport.setIndex(index);
					sport.setType(sportType);
					sport.setStep(step);
					sport.setDistance(dsitance);
					sport.setTimeConsuming(timeConsuming);
					sport.setCalorie(calorie);
					sport.setHeartRate(hr);
				}
			}
		}
		return null;
	}

}
*/