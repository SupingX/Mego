package com.mycj.jusd.bean;

import com.mycj.jusd.ui.activity.SportInfo;

public class JsdProtolNotify extends AbstractProtolNotify{

	@Override
	public int parseSupportFeature(byte[] data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public WatchSetting parseWatchSetting(byte[] data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SportPlanSetting parseSportPlanSetting(byte[] data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemindSetting parseRemindSetting(byte[] data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SportInfo parseSportInfo(byte[] data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int parseStepStatus(byte[] data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public StepInfo parseStep(byte[] data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int parsePathStatus(byte[] data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PathInfo parsePath(byte[] data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int parseSleepStatus(byte[] data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SleepInfo parseSleep(byte[] data) {
		// TODO Auto-generated method stub
		return null;
	}

}
