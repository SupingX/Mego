package com.mycj.jusd.service;

import com.mycj.jusd.bean.RemindSetting;
import com.mycj.jusd.bean.WatchSetting;
import com.mycj.jusd.bean.news.SleepHistory;
import com.mycj.jusd.bean.news.SportHistory;
import com.mycj.jusd.bean.news.SportPlanSetting;
import com.mycj.jusd.ui.activity.HistorySportActivity;
import com.mycj.jusd.ui.activity.SportInfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 发送广播工具
 * @author zeej
 */
public class BroadSender {
	public static IntentFilter getIntentFilter(){
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_SUPPORT_FEATURE);
		filter.addAction(ACTION_WATCH_SETTING);
		filter.addAction(ACTION_SPORT_PLAN);
		filter.addAction(ACTION_REMIND);
		filter.addAction(ACTION_DELETE);
		filter.addAction(ACTION_START_SYNC_SPORT);
		filter.addAction(ACTION_END_SYNC_SPORT);
		filter.addAction(ACTION_START_SYNC_SLEEP);
		filter.addAction(ACTION_END_SYNC_SLEEP);
		filter.addAction(ACTION_STEP_OR_PATH_INFO);
		filter.addAction(ACTION_STEP_OR_PATH_INFO_STATUS);
		filter.addAction(ACTION_SYNC_HISTORY_STEP_STATUS);
		filter.addAction(ACTION_SYNC_HISTORY_STEP);
		filter.addAction(ACTION_SYNC_HISTORY_SLEEP_STATUS);
		filter.addAction(ACTION_SYNC_HISTORY_SLEEP_);
		return filter;
	}
	public final static String ACTION_SUPPORT_FEATURE = "ACTION_SUPPORT_FEATURE" ;
	public static final String ACTION_WATCH_SETTING = "ACTION_WATCH_SETTING";
	public static final String ACTION_SPORT_PLAN = "ACTION_SPORT_PLAN";
	public static final String ACTION_REMIND = "ACTION_REMIND";
	public static final String ACTION_DELETE = "ACTION_DELETE";
	public static final String ACTION_START_SYNC_SPORT = "ACTION_START_SYNC_SPORT";
	public static final String ACTION_END_SYNC_SPORT = "ACTION_END_SYNC_SPORT";
	public static final String ACTION_START_SYNC_SLEEP = "ACTION_START_SYNC_SLEEP";
	public static final String ACTION_END_SYNC_SLEEP = "ACTION_END_SYNC_SLEEP";
	public static final String ACTION_STEP_OR_PATH_INFO = "ACTION_STEP_OR_PATH_INFO";
	public static final String ACTION_STEP_OR_PATH_INFO_STATUS = "ACTION_STEP_OR_PATH_INFO_STATUS";
	public static final String ACTION_SYNC_HISTORY_STEP_STATUS = "ACTION_SYNC_HISTORY_STEP_STATUS";
	public static final String ACTION_SYNC_HISTORY_STEP = "ACTION_SYNC_HISTORY_STEP";
//	public static final String ACTION_SYNC_HISTORY_PATH_STATUS = "ACTION_SYNC_HISTORY_PATH_STATUS";
//	public static final String ACTION_SYNC_HISTORY_PATH = "ACTION_SYNC_HISTORY_PATH";
	public static final String ACTION_SYNC_HISTORY_SLEEP_STATUS = "ACTION_SYNC_HISTORY_SLEEP_STATUS";
	public static final String ACTION_SYNC_HISTORY_SLEEP_ = "ACTION_SYNC_HISTORY_SLEEP_";
	
	
	public final static String EXTRA_DELETE = "EXTRA_DELETE" ;
	public final static String EXTRA_SUPPORT_FEATURE = "EXTRA_SUPPORT_FEATURE" ;
	public static final String EXTRA_WATCH_SETTING = "EXTRA_WATCH_SETTING";
	public static final String EXTRA_SPORT_PLAN = "EXTRA_SPORT_PLAN";
	public static final String EXTRA_REMIND_SETTING = "EXTRA_REMIND_SETTING";
	public static final String EXTRA_CURRENT_SPORT_INFO = "EXTRA_CURRENT_SPORT_INFO";
	public static final String EXTRA_CURRENT_SPORT_INFO_STATUS = "EXTRA_CURRENT_SPORT_INFO_STATUS";
	public static final String EXTRA_SYNC_SPORT_HISTORY = "EXTRA_SYNC_SPORT_HISTORY";
	public static final String EXTRA_SYNC_SPORT_HISTORY_STATUS = "EXTRA_SYNC_SPORT_HISTORY_STATUS";
	public static final String EXTRA_SYNC_SLEEP_HISTORY = "EXTRA_SYNC_SLEEP_HISTORY";
	public static final String EXTRA_SYNC_SLEEP_HISTORY_STATUS = "EXTRA_SYNC_SLEEP_HISTORY_STATUS";
	
	
	
	
	
	/**
	 * 设备支持功能
	 * @param parseSupportFeature
	 * @param context
	 */
	public static void sendBroadcastForSupporFeature(int[] parseSupportFeature,Context context) {
		Intent intent = new Intent();
		intent.setAction(ACTION_SUPPORT_FEATURE);
		intent.putExtra(EXTRA_SUPPORT_FEATURE, parseSupportFeature);
		context.sendBroadcast(intent);
	}
	
	/**
	 * 设备设置
	 * @param parseWatchSetting
	 * @param blueService
	 */
	public static void sendBroadcastForWatchSetting(
			WatchSetting parseWatchSetting, Context context) {
		Intent intent = new Intent();
		intent.setAction(ACTION_WATCH_SETTING);
		intent.putExtra(EXTRA_WATCH_SETTING, parseWatchSetting);
		context.sendBroadcast(intent);
	}

	public static void sendBroadcastForSportPlan(
			SportPlanSetting parseSportPlanSetting, Context context) {
		Intent intent = new Intent();
		intent.setAction(ACTION_SPORT_PLAN);
		intent.putExtra(EXTRA_SPORT_PLAN, parseSportPlanSetting);
		context.sendBroadcast(intent);
	}

	public static void sendBroadcastForRemind(RemindSetting parseRemindSetting,
			 Context context) {
		Intent intent = new Intent();
		intent.setAction(ACTION_REMIND);
		intent.putExtra(EXTRA_REMIND_SETTING, parseRemindSetting);
		context.sendBroadcast(intent);
	}
	
	/**
	 * 广播历史运动信息.
	 * @param sport
	 * @param context
	 */
	public static void sendBroadcastForHistorySport(SportHistory sport,Context context) {
		Intent intent = new Intent();
		intent.setAction(ACTION_SYNC_HISTORY_STEP);
		intent.putExtra(EXTRA_SYNC_SPORT_HISTORY, sport);
		context.sendBroadcast(intent);
	}
	
	/**
	 * 广播每一条历史运动信息开始结束状态
	 * @param status
	 * @param context
	 */
	public static void sendBroadcastForHistorySportStatus(int status,Context context) {
		Intent intent = new Intent();
		intent.setAction(ACTION_SYNC_HISTORY_STEP_STATUS);
		intent.putExtra(EXTRA_SYNC_SPORT_HISTORY_STATUS, status);
		context.sendBroadcast(intent);
	}
	
	public static void sendBroadcastForHistorySleep(SleepHistory sleep,Context context) {
		Intent intent = new Intent();
		intent.setAction(ACTION_SYNC_HISTORY_SLEEP_);
		intent.putExtra(EXTRA_SYNC_SLEEP_HISTORY, sleep);
		context.sendBroadcast(intent);
	}
	
	public static void sendBroadcastForHistorySleepStatus(int status,Context context) {
		Intent intent = new Intent();
		intent.setAction(ACTION_SYNC_HISTORY_SLEEP_STATUS);
		intent.putExtra(EXTRA_SYNC_SLEEP_HISTORY_STATUS, status);
		context.sendBroadcast(intent);
	}
	
	public static void sendBroadcastForCurrentSportInfo(SportHistory sport,Context context) {
		Intent intent = new Intent();
		intent.setAction(ACTION_STEP_OR_PATH_INFO);
		intent.putExtra(EXTRA_CURRENT_SPORT_INFO, sport);
		context.sendBroadcast(intent);
	}
	
	public static void sendBroadcastForCurrentSportInfoStatus(int status,Context context) {
		Intent intent = new Intent();
		intent.setAction(ACTION_STEP_OR_PATH_INFO_STATUS);
		intent.putExtra(EXTRA_CURRENT_SPORT_INFO_STATUS,status);
		context.sendBroadcast(intent);
	}
	
	
	public static void sendBroadcastForDelete(int status ,Context context) {
		Intent intent = new Intent();
		intent.setAction(ACTION_DELETE);
		intent.putExtra(EXTRA_DELETE, status);
		context.sendBroadcast(intent);
	}
	
	public static void sendBroadcastForStartSyncSport(Context context) {
		Intent intent = new Intent();
		intent.setAction(ACTION_START_SYNC_SPORT);
		context.sendBroadcast(intent);
	}
	
	public static void sendBroadcastForEndSyncSport(Context context) {
		Intent intent = new Intent(ACTION_END_SYNC_SPORT);
		context.sendBroadcast(intent);
	}
	
	public static void sendBroadcastForStartSyncSleep(Context context) {
		Intent intent = new Intent();
		intent.setAction(ACTION_START_SYNC_SLEEP);
		context.sendBroadcast(intent);
	}
	
	public static void sendBroadcastForEndSyncSleep(Context context) {
		Intent intent = new Intent(ACTION_END_SYNC_SLEEP);
		context.sendBroadcast(intent);
	}
	
	
	
}
