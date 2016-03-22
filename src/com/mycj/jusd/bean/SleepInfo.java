package com.mycj.jusd.bean;

public class SleepInfo {
	private int type;
	private long StartTime;
	private long endTime;
	private int lightTime;
	private int deepTime;
	public SleepInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SleepInfo(int type, long startTime, long endTime, int lightTime, int deepTime) {
		super();
		this.type = type;
		StartTime = startTime;
		this.endTime = endTime;
		this.lightTime = lightTime;
		this.deepTime = deepTime;
	}
	@Override
	public String toString() {
		return "SleepInfo [type=" + type + ", StartTime=" + StartTime + ", endTime=" + endTime + ", lightTime=" + lightTime + ", deepTime=" + deepTime + "]";
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getStartTime() {
		return StartTime;
	}
	public void setStartTime(long startTime) {
		StartTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public int getLightTime() {
		return lightTime;
	}
	public void setLightTime(int lightTime) {
		this.lightTime = lightTime;
	}
	public int getDeepTime() {
		return deepTime;
	}
	public void setDeepTime(int deepTime) {
		this.deepTime = deepTime;
	}

	
}
