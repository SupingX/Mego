package com.mycj.jusd.protocol;

import android.util.Log;

import com.laput.map.BaiduMapUtil;
import com.laputa.blue.util.DataUtil;
import com.mycj.jusd.bean.JunConstant;
import com.mycj.jusd.bean.RemindSetting;
import com.mycj.jusd.bean.WatchSetting;
import com.mycj.jusd.bean.news.CurrentSportLocation;
import com.mycj.jusd.bean.news.SleepHistory;
import com.mycj.jusd.bean.news.SportHistory;
import com.mycj.jusd.bean.news.SportPlanSetting;
import com.mycj.jusd.util.JunUtil;

public class ProtocolNotifyManager {
	private static ProtocolNotifyManager mInstance;
	private static boolean isDebug = true;
	private static boolean isHistoryReceivering = true;
	private static boolean isCurrentReceivering = true;
	private static boolean isHistorySleepReceivering = true;
	private static SportHistory currentSport;
	private static SportHistory historySport;
	private static SleepHistory historySleep;

	private ProtocolNotifyManager() {
	}

	public static ProtocolNotifyManager newInstance() {
		if (null == mInstance) {
			mInstance = new ProtocolNotifyManager();
		}
		return mInstance;
	}

	/**
	 * 解析数据
	 * 
	 * @param data
	 */
	public synchronized void parse(byte[] data) {
	
		if (data == null || data.length <= 0) {
			if (mOnDataParseResuletListener != null) {
				mOnDataParseResuletListener.onError("parse() --> data为空");
			}else{
				Log.e("", "mOnDataParseResuletListener为空");
			}
			return;
		}
		String value = DataUtil.byteToHexString(data);
		int protocol = Integer.valueOf(value.substring(0, 2), 16);
		if (protocol == (Type.HISTORY_SYNC_START.getProtocol()) && data.length == 4) {
			String type = value.substring(2, 4);
			String status =  value.substring(4, 6);
				if (Integer.valueOf(status)==1) {
					if (mOnDataParseResuletListener != null) {
						mOnDataParseResuletListener
								.onHistorySyncStart("parse() --> 历史同步开始",Integer.valueOf(type));
					}else{
						if (mOnDataParseResuletListener != null) {
							mOnDataParseResuletListener
									.onHistorySyncEnd("parse() --> 历史同步结束",Integer.valueOf(type));
						}
					}
				}
				
		
		}  else if (protocol == Type.NOTIFY_SUPPORT_FEATURE.getProtocol()
				&& data.length == 3) {
			if (mOnDataParseResuletListener != null) {
				int[] supportFeatures = new int[4];
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
				mOnDataParseResuletListener.onSupportFeature(
						"parse() --> 支持功能:" + supportFeatures, supportFeatures);
			}
		} else if (protocol == Type.NOTIFY_WATCH_SETTING.getProtocol()
				&& data.length == 20) {
			if (mOnDataParseResuletListener != null) {

				int unit = Integer.valueOf(value.substring(2, 4), 16);
				int height = Integer.valueOf(value.substring(4, 8), 16);
				int weight = Integer.valueOf(value.substring(8, 12), 16);
				
				int birthdayYear = Integer.valueOf(value.substring(12, 14), 16);
				int birthdayMonth = Integer.valueOf(value.substring(14, 16), 16);
				int birthdayDay = Integer.valueOf(value.substring(16, 18), 16);
				String birthday = JunUtil.getDateStr(birthdayYear, birthdayMonth, birthdayDay);
				int walkLength = Integer.valueOf(value.substring(16, 20), 16);
				int runLength = Integer.valueOf(value.substring(20, 24), 16);
				// 心率/睡眠监测开关：0x00关；0x01开；bit0:心率；bit1:睡眠检测;bit2 设置步长；bit3 0-男；1-女
				
				int onoff = Integer.valueOf(value.substring(24, 26), 16);
				int sleepOnoff = ((onoff & 0b10) == 0b10) ? 1 : 0;
				int hrOnoff = ((onoff & 0b01) == 0b01) ? 1 : 0;
				int stepOnoff = ((onoff & 0b100) == 0b100) ? 1 : 0;
				int sex = ((onoff & 0b1000) == 0b1000) ? 1 : 0;
				int hrMin = Integer.valueOf(value.substring(26, 28), 16);
				int hrMax = Integer.valueOf(value.substring(28, 30), 16);
				String sleepStr = value.substring(30, 34);
				String awakStr = value.substring(34, 38);
				String sleepTime = Integer
						.valueOf(sleepStr.substring(0, 2), 16)
						+ ":"
						+ Integer.valueOf(sleepStr.substring(2, 4), 16);
				String awakTime = Integer.valueOf(awakStr.substring(0, 2), 16)
						+ ":" + Integer.valueOf(awakStr.substring(2, 4), 16);
				;
				WatchSetting setting = new WatchSetting(unit, height, weight,
						 sex,birthday,stepOnoff, walkLength, runLength, hrOnoff, hrMin, hrMax,
						sleepOnoff, sleepTime, awakTime);
				mOnDataParseResuletListener.onWatchSetting(
						"parse() --> 手表设置 : " + setting, setting);
			}
		} else if (protocol == Type.NOTIFY_SPORT_PLAN.getProtocol()
				&& data.length == 20) {
			if (mOnDataParseResuletListener != null) {
				// 运动计划/自动签到/配速设置/运动类型开关：0x00关；0x01开；bit0:运动计划；bit1:自动签到；bit2:配速设置；bit[4]:运动目标
				int onOff = Integer.valueOf(value.substring(2, 4), 16);
				int sportPlanOnoff = ((onOff & 0b0001) == 0b0001) ? 1 : 0;
				int autoSignOnoff = ((onOff & 0b0010) == 0b0010) ? 1 : 0;
				int paceSettingOnoff = ((onOff & 0b0100) == 0b0100) ? 1 : 0;
				int sportGoalOnoff = ((onOff & 0b1000) == 0b1000) ? 1 : 0;
				int startHour = Integer.valueOf(value.substring(4, 6), 16);
				int startMin = Integer.valueOf(value.substring(6, 8), 16);
				String startTime = String.valueOf(startHour) + ":"
						+ String.valueOf(startMin);
				int goalStep = Integer.valueOf(value.substring(8, 14), 16);
				int goalDistance = Integer.valueOf(value.substring(14, 18), 16);
				int autoSignDistance = Integer.valueOf(value.substring(18, 22),
						16);
				int paceMin = Integer.valueOf(value.substring(22, 26), 16);
				int paceMax = Integer.valueOf(value.substring(26, 30), 16);
				int sportStyle = Integer.valueOf(value.substring(30, 32), 16);
				SportPlanSetting setting = new SportPlanSetting(sportPlanOnoff,
						startTime, goalStep, goalDistance, autoSignOnoff,
						sportGoalOnoff, autoSignDistance, paceSettingOnoff,
						paceMin, paceMax, sportStyle);
				mOnDataParseResuletListener.onSportPlan("parse() --> 运动计划 : "
						+ setting, setting);
			}
		} else if (protocol == Type.NOTIFY_REMIND.getProtocol() && data.length == 10) {
			if (mOnDataParseResuletListener != null) {
				// 开关：0x00关；0x01开；bit0:来电提醒；bit1:每日闹铃；bit2:久坐提醒；bit3:/生日提醒；bit4:整点报时；
				int onOff = Integer.valueOf(value.substring(2, 4), 16);
				boolean incomingOnoff = ((onOff & 0b0001) == 0b0001);
				boolean alarmOnoff = ((onOff & 0b0010) == 0b0010);
				boolean longSitOnoff = ((onOff & 0b0100) == 0b0100);
				boolean birthdayOnoff = ((onOff & 0b1000) == 0b1000);
				boolean nowtimeOnoff = ((onOff & 0b10000) == 0b10000);

				String alarmTime = String.valueOf(Integer.valueOf(
						value.substring(4, 6), 16))
						+ ":"
						+ String.valueOf(Integer.valueOf(value.substring(6, 8),
								16));
				int longSitValue = Integer.valueOf(value.substring(8, 12), 16);

				String birthdayDate = String.valueOf(Integer.valueOf(
						value.substring(12, 14), 16))
						+ "/"
						+ String.valueOf(Integer.valueOf(
								value.substring(14, 16), 16));

				String birthdayTime = String.valueOf(Integer.valueOf(
						value.substring(16, 18), 16))
						+ ":"
						+ String.valueOf(Integer.valueOf(
								value.substring(18, 20), 16));

				RemindSetting setting = new RemindSetting(incomingOnoff?1:0,
						alarmOnoff?1:0, alarmTime, longSitOnoff?1:0, longSitValue,
						birthdayOnoff?1:0, birthdayTime,nowtimeOnoff?1:0);
				mOnDataParseResuletListener.onRemindSetting(
						"parse() --> 个人提醒  : " + setting, setting);
			}
		}

		// *******************************************************
		//
		// 当 前 运 动
		//
		// *******************************************************

		else if (protocol == Type.NOTIFY_CURRENT_SPORT_INFO_STATUS
				.getProtocol() && data.length == 4) {
			if (mOnDataParseResuletListener != null) {
				int status = Integer.valueOf(value.substring(2, 4), 16);
				if (status == 1) {
					isCurrentReceivering = true;
					currentSport = new SportHistory();
				} else {
					isCurrentReceivering = false;
					mOnDataParseResuletListener.onCurrentSportInfo(
							"parse() --> 当前运动信息" + currentSport, currentSport);
				}
				mOnDataParseResuletListener.onCurrentSportInfoStatus(
						"parse() --> 历史信息同步状态 : " + status, status);
			}

		} else if (protocol == Type.NOTIFY_CURRENT_SPORT_INFO.getProtocol()
				&& data.length == 20) {
			if (mOnDataParseResuletListener != null) {
				if (isCurrentReceivering) {
					log("解析中 当前运动信息1中  ...");
					int year = Integer.valueOf(value.substring(2, 4), 16);
					int month = Integer.valueOf(value.substring(4, 6), 16);
					int day = Integer.valueOf(value.substring(6, 8), 16);
					int hour = Integer.valueOf(value.substring(8, 10), 16);
					int minute = Integer.valueOf(value.substring(10, 12), 16);
					int second = Integer.valueOf(value.substring(12, 14), 16);
					int index = Integer.valueOf(value.substring(14, 16), 16);
					int no = Integer.valueOf(value.substring(16, 18), 16);
					int type = Integer.valueOf(value.substring(18, 20), 16);
					int step = Integer.valueOf(value.substring(20, 26), 16);
					int distance = Integer.valueOf(value.substring(26, 30), 16);
					int consuming = Integer
							.valueOf(value.substring(30, 34), 16);
					int calorie = Integer.valueOf(value.substring(34, 40), 16);
					currentSport.setSportDate(getDate(year, month, day));
					currentSport.setSportTime(getSportTime(hour, minute, second));
					currentSport.setSportIndex(index);
					currentSport.setSportNo(no);
					currentSport.setType(type);
					currentSport.setStep(step);
					currentSport.setDistance(distance);
					currentSport.setConsuming(consuming);
					currentSport.setCalorie(calorie);
				}

			}
		} else if (protocol == Type.NOTIFY_CURRENT_SPORT_LOCATION.getProtocol()
				&& data.length == 20) {
			if (mOnDataParseResuletListener != null) {
				// 解析过程 需完善
				if (isCurrentReceivering) {
					log("解析中 当前运动信息2中  ...");
					int latHour = Integer.valueOf(value.substring(2, 4), 16);
					int latMinute = Integer.valueOf(value.substring(4, 6), 16);
					int latSecond = Integer.valueOf(value.substring(6, 8), 16);
					int lngHour = Integer.valueOf(value.substring(8, 10), 16);
					int lngMinute = Integer
							.valueOf(value.substring(10, 12), 16);
					int lngSecond = Integer
							.valueOf(value.substring(12, 14), 16);
					int unit = Integer.valueOf(value.substring(14, 16), 16);
					int latUnit = ((unit & 0b0001) == 0b0001) ? 1 : 0;
					int lngUnit = ((unit & 0b0010) == 0b0010) ? 1 : 0;
					int signed = Integer.valueOf(value.substring(16, 18), 16);
					int speed = Integer.valueOf(value.substring(18, 22), 16);
					int stepFreq = Integer.valueOf(value.substring(22, 26), 16);
					int pace = Integer.valueOf(value.substring(26, 28), 16);
					int hr = Integer.valueOf(value.substring(28, 30), 16);

					currentSport.setLat(getLat(latHour, latMinute, latSecond,
							latUnit));
					currentSport.setLng(getLng(lngHour, lngMinute, lngSecond,
							lngUnit));
					currentSport.setSigned(signed);
					currentSport.setSpeed(speed);
					currentSport.setFreq(stepFreq);
					currentSport.setPace(pace);
					currentSport.setHr(hr);
				}

				// CurrentSportLocation info = new CurrentSportLocation();
				// mOnDataParseResuletListener.onCurrentSportLocation(
				// "parse() --> 当前运动坐标 : " + info, info);

			}
		}

		// *******************************************************

		// 历 史 睡 眠

		// *******************************************************

		else if (protocol == Type.NOTIFY_SYNC_HISTORY_SLEEP.getProtocol()
				&& data.length == 16) {
			if (mOnDataParseResuletListener != null && isHistorySleepReceivering) {
				int index = Integer.valueOf(value.substring(2, 4), 16);
				int year = Integer.valueOf(value.substring(4, 6), 16);
				int month = Integer.valueOf(value.substring(7, 8), 16);
				int day = Integer.valueOf(value.substring(8, 10), 16);
				int startHour = Integer.valueOf(value.substring(10, 12), 16);
				int startMin = Integer.valueOf(value.substring(12, 14), 16);
				int endHour = Integer.valueOf(value.substring(14, 16), 16);
				int endMin = Integer.valueOf(value.substring(16, 18), 16);
				int deep = Integer.valueOf(value.substring(18, 22), 16);
				int light = Integer.valueOf(value.substring(22, 26), 16);
				historySleep.setSleepDate(getDate(year, month, day));
				historySleep.setSleepIndex(index);
				historySleep.setStartTime(getSleepTime(startHour, startMin));
				historySleep.setEndTime(getSleepTime(endHour, endMin));
				historySleep.setSleepDeep(deep);
				historySleep.setSleepLight(light);
				/*
				 * SleepHistory info = new SleepHistory();
				 * mOnDataParseResuletListener.onSyncHistorySleep(
				 * "parse() --> 历史睡眠同步" + info, info);
				 */
			}
		} else if (protocol == Type.NOTIFY_SYNC_HISTORY_SLEEP_STATUS
				.getProtocol() && data.length == 4) {
			if (mOnDataParseResuletListener != null) {
				int status = Integer.valueOf(value.substring(2, 4), 16);
				if (status == 1) {
					isHistorySleepReceivering = true;
					historySleep = new SleepHistory();
				} else {
					isHistorySleepReceivering = false;
					mOnDataParseResuletListener.onSyncHistorySleep(
							"parse() --> 当前睡眠信息" + historySleep, historySleep);
				}

				mOnDataParseResuletListener.onSyncHistorySleepStatus(
						"parse() --> 历史睡眠同步状态", status);
			}
		}
		// *******************************************************
		//
		// 历 史 运 动
		//
		// *******************************************************
		else if (protocol == Type.NOTIFY_SYNC_HISTORY_SPORT_INFO_STATUS
				.getProtocol() && data.length == 4) {
			if (mOnDataParseResuletListener != null) {
				int status = Integer.valueOf(value.substring(2, 4), 16);
				if (status == 1) {
					isHistoryReceivering = true;
					historySport = new SportHistory();
					Log.e("", "--> 开始");
				} else {
					isHistoryReceivering = false;
					mOnDataParseResuletListener.onSyncHistorySportInfo(
							"parse() --> 历史运动信息" + historySport, historySport);
					Log.e("", "--> 结束");
				}

				mOnDataParseResuletListener.onSyncHistorySportInfoStatus(
						"parse() --> 历史信息同步状态 : " + status, status);
			}

		} else if (protocol == Type.NOTIFY_SYNC_HISTORY_SPORT_INFO
				.getProtocol() && data.length == 20) {
			if (mOnDataParseResuletListener != null) {
				if (isHistoryReceivering) {
					int year = Integer.valueOf(value.substring(2, 4), 16);
					int month = Integer.valueOf(value.substring(4, 6), 16);
					int day = Integer.valueOf(value.substring(6, 8), 16);
					int hour = Integer.valueOf(value.substring(8, 10), 16);
					int minute = Integer.valueOf(value.substring(10, 12), 16);
					int second = Integer.valueOf(value.substring(12, 14), 16);
					int index = Integer.valueOf(value.substring(14, 16), 16);
					int no = Integer.valueOf(value.substring(16, 18), 16);
					int type = Integer.valueOf(value.substring(18, 20), 16);
					int step = Integer.valueOf(value.substring(20, 26), 16);
					int distance = Integer.valueOf(value.substring(26, 30), 16);
					int consuming = Integer
							.valueOf(value.substring(30, 34), 16);
					int calorie = Integer.valueOf(value.substring(34, 40), 16);
					historySport.setSportDate(getDate(year, month, day));
					historySport.setSportTime(getSportTime(hour, minute, second));
					historySport.setSportIndex(index);
					historySport.setSportNo(no);
					historySport.setType(type);
					historySport.setStep(step);
					historySport.setDistance(distance);
					historySport.setConsuming(consuming);
					historySport.setCalorie(calorie);
					Log.e("", "--> 前段");
				}
			}
		} else if (protocol == Type.NOTIFY_SYNC_HISTORY_LOCATION.getProtocol()
				&& data.length == 20) {
			if (mOnDataParseResuletListener != null) {
				if (isHistoryReceivering) {

					int latHour = Integer.valueOf(value.substring(2, 4), 16);
					int latMinute = Integer.valueOf(value.substring(4, 6), 16);
					int latSecond = Integer.valueOf(value.substring(6, 8), 16);
					int lngHour = Integer.valueOf(value.substring(8, 10), 16);
					int lngMinute = Integer
							.valueOf(value.substring(10, 12), 16);
					int lngSecond = Integer
							.valueOf(value.substring(12, 14), 16);
					int unit = Integer.valueOf(value.substring(14, 16), 16);
					int latUnit = ((unit & 0b0001) == 0b0001) ? 1 : 0;
					int lngUnit = ((unit & 0b0010) == 0b0010) ? 1 : 0;
					int signed = Integer.valueOf(value.substring(16, 18), 16);
					int speed = Integer.valueOf(value.substring(18, 22), 16);
					int stepFreq = Integer.valueOf(value.substring(22, 26), 16);
					int pace = Integer.valueOf(value.substring(26, 28), 16);
					int hr = Integer.valueOf(value.substring(28, 30), 16);

					historySport.setLat(getLat(latHour, latMinute, latSecond,
							latUnit));
					historySport.setLng(getLng(lngHour, lngMinute, lngSecond,
							lngUnit));
					historySport.setSigned(signed);
					historySport.setSpeed(speed);
					historySport.setFreq(stepFreq);
					historySport.setPace(pace);
					historySport.setHr(hr);
					Log.e("", "--> 后段");
				}

			}
		}
		// *******************************************************
		//
		// 删 除 历史
		//
		// *******************************************************
		else if (protocol == Type.NOTIFY_DELETE.getProtocol()) {
			if (mOnDataParseResuletListener != null && data.length == 4) {
				// 待完善
				int status = 0;
				mOnDataParseResuletListener.onDelete("parse() --> 删除记录"
						+ status, status);
			}
		}
		
		else{
			Log.e("", "－－＞data 未匹配" );
		}

	}

	private String intToStr(int value){
		String result = "";
		if (value<100) {
			if (value<10) {
				result = "0"+String.valueOf(value);
			}else{
				result = String.valueOf(value);
			}
		}
		return result;
	}
	private String getSleepTime(int startHour, int endHour) {
		
		return intToStr(startHour) + intToStr(endHour);
	}

	private long getLng(int lngHour, int lngMinute, int lngSecond, int lngUnit) {
		
		return 0;
	}

	private long getLat(int latHour, int latMinute, int latSecond, int latUnit) {
		// TODO Auto-generated method stub
		return 0;
	}

	private String getSportTime(int hour, int minute, int second) {
		return intToStr(hour) + intToStr(minute) + intToStr(second);
	}

	private String getDate(int year, int month, int day) {
		return "20" + intToStr(year) + intToStr(month) + intToStr(day);
	}

	// 种类
	public enum Type {

		ERROR(0), HISTORY_SYNC_START(0xE1) // E1
		, HISTORY_SYNC_END(0xE0) // E0
		, NOTIFY_SUPPORT_FEATURE(0x01), NOTIFY_WATCH_SETTING(0x03), NOTIFY_SPORT_PLAN(
				0x04), NOTIFY_REMIND(0x05), NOTIFY_CURRENT_SPORT_INFO(0x07), NOTIFY_CURRENT_SPORT_INFO_STATUS(
				0xb7), NOTIFY_CURRENT_SPORT_LOCATION(0xA7), NOTIFY_SYNC_HISTORY_SLEEP(
				0x08), NOTIFY_SYNC_HISTORY_SLEEP_STATUS(0xA8), NOTIFY_SYNC_HISTORY_SPORT_INFO(
				0x09), NOTIFY_SYNC_HISTORY_SPORT_INFO_STATUS(0xA9), NOTIFY_SYNC_HISTORY_LOCATION(
				0x1A), NOTIFY_SYNC_HISTORY_LOCATION_STATUS(0xAA), NOTIFY_DELETE(
				0x0B);
		private int protocol;

		Type(int protocol) {
			this.protocol = protocol;
		}

		public int getProtocol() {
			return this.protocol;
		};

	}

	// 回调接口
	public interface OnDataParseResuletListener {
		/** 错误 **/
		public void onError(String msg);

		/** 同步历史开始 **/
		public void onHistorySyncStart(String msg,int type);

		/** 同步历史结束 **/
		public void onHistorySyncEnd(String msg,int type);

		/**
		 * 支持功能
		 * 
		 * @param msg
		 *            描述
		 * @param result
		 *            支持功能
		 */
		public void onSupportFeature(String msg, int[] result);

		/**
		 * 手表设置
		 * 
		 * @param msg
		 *            描述
		 * @param setting
		 *            手表设置
		 */
		public void onWatchSetting(String msg, WatchSetting setting);

		/**
		 * 运动计划
		 * 
		 * @param msg
		 *            描述
		 * @param setting
		 *            运动计划设置
		 */
		public void onSportPlan(String msg, SportPlanSetting setting);

		/**
		 * 提醒设置
		 * 
		 * @param msg
		 *            描述
		 * @param setting
		 *            提醒设置
		 */
		public void onRemindSetting(String msg, RemindSetting setting);

		/**
		 * 当前运动信息
		 * 
		 * @param msg
		 *            描述
		 * @param info
		 *            当前运动信息
		 */
		public void onCurrentSportInfo(String msg, SportHistory info);

		/**
		 * 当前运动信息
		 * 
		 * @param msg
		 *            描述
		 * @param info
		 *            当前运动信息
		 */
		public void onCurrentSportInfoStatus(String msg, int status);

		/**
		 * 当前位置信息
		 * 
		 * @param msg
		 *            描述
		 * @param location
		 *            当前运动位置信息
		 */
//		public void onCurrentSportLocation(String msg,
//				CurrentSportLocation location);

		/**
		 * 同步睡觉历史
		 * 
		 * @param msg
		 *            描述
		 * @param info
		 *            睡眠信息
		 */
		public void onSyncHistorySleep(String msg, SleepHistory info);

		/**
		 * 同步睡觉历史状态
		 * 
		 * @param msg
		 *            描述
		 * @param status
		 *            状态
		 */
		public void onSyncHistorySleepStatus(String msg, int status);

		/**
		 * 同步运动历史
		 * 
		 * @param msg
		 *            描述
		 * @param info
		 *            运动信息
		 */
		public void onSyncHistorySportInfo(String msg, SportHistory info);

		/**
		 * 同步运动历史状态
		 * 
		 * @param msg
		 *            描述
		 * @param status
		 *            状态
		 */
		public void onSyncHistorySportInfoStatus(String msg, int status);

		/**
		 * 同步运动历史位置
		 * 
		 * @param msg
		 *            描述
		 * @param location
		 *            经纬度运动信息
		 */
//		public void onSyncHistorySportLocation(String msg, SportHistory location);

		/**
		 * 同步运动历史位置状态
		 * 
		 * @param msg
		 *            描述
		 * @param status
		 *            状态
		 */
//		public void onSyncHistorySportLocationStatus(String msg, int status);

		/**
		 * 删除历史
		 * 
		 * @param msg
		 *            描述
		 * @param status
		 *            状态
		 */
		public void onDelete(String msg, int status);

	}

	private OnDataParseResuletListener mOnDataParseResuletListener;

	public ProtocolNotifyManager setOnDataParseResuletListener(
			OnDataParseResuletListener l) {
		this.mOnDataParseResuletListener = l;
		return this;
	}

	private void log(String msg) {
		if (isDebug) {
			Log.e("ProtocolNotifyManager", "==>" + msg + "<==");
		}
	}
}
