package com.mycj.jusd.bean;

public class JsdProtolWrite extends AbstractProtolWrite {

	@Override
	public byte[] getByteForTimeSync(long dateTime) {
		return null;
	}

	@Override
	public byte[] getByteForWatchSetting(int unit, int height, int weight, int age, int sex, int walkLength, int runLength, int hrOnoff, int hrMax, int hrMin, int sleepOnoff, int sleepHour,
			int sleepMin, int awakHour, int awakMin) {
		return null;
	}

	@Override
	public byte[] getByteForWatchSetting(WatchSetting setting) {
		return null;
	}

	@Override
	public byte[] getByteForSportPlanSetting(int sportPlanOnoff, int startHour, int startMin, int sportGoal, int sportGoalDistance, int autoSignOnoff, int signDistance, int paceOnoff, int paceMin,
			int paceMax, int sportStyleOnoff, int sportStyle) {
		return null;
	}

	@Override
	public byte[] getByteForSportPlanSetting(SportPlanSetting setting) {
		return null;
	}

	@Override
	public byte[] getByteForRemindSetting(int incomingOnoff, int alarmOnoff, int alarmHour, int alarmMin, int longSitOnoff, int longsitValue, int birthdayOnoff, int birthdayMonth, int birthdayDay,
			int birthdayHour, int birthdayMin, int nowTimeOnoff) {
		return null;
	}

	@Override
	public byte[] getByteForRemindSetting(RemindSetting setting) {
		return null;
	}

	@Override
	public byte[] getByteForRequstWatchSetting(int type) {
		return null;
	}

	@Override
	public byte[] getByteForRequstSportInfo(int type) {
		return null;
	}

	@Override
	public byte[] getByteForRequestStep(int type) {
		return null;
	}

	@Override
	public byte[] getByteForRequestPath(int type) {
		return null;
	}

	@Override
	public byte[] getByteForRequestSleep(int type) {
		return null;
	}

	@Override
	public byte[] getByteForRequestDelete(int type, long startDate, long endDate) {
		return null;
	}

	@Override
	public byte[] getByteForRequestResetFactorySetting() {
		return null;
	}

}
