package com.mycj.jusd.bean;

public class StepInfo {
	private int type ;
	private long timeStamp;
	private int dataIndex;
	private long steps;
	private String distance;
	private int time;
	private String calorie;
	private int hr;
	public StepInfo(int type, long timeStamp, int dataIndex, long steps, String distance, int time, String calorie, int hr) {
		super();
		this.type = type;
		this.timeStamp = timeStamp;
		this.dataIndex = dataIndex;
		this.steps = steps;
		this.distance = distance;
		this.time = time;
		this.calorie = calorie;
		this.hr = hr;
	}
	public StepInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "StepInfo [type=" + type + ", timeStamp=" + timeStamp + ", dataIndex=" + dataIndex + ", steps=" + steps + ", distance=" + distance + ", time=" + time + ", calorie=" + calorie + ", hr="
				+ hr + "]";
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public int getDataIndex() {
		return dataIndex;
	}
	public void setDataIndex(int dataIndex) {
		this.dataIndex = dataIndex;
	}
	public long getSteps() {
		return steps;
	}
	public void setSteps(long steps) {
		this.steps = steps;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getCalorie() {
		return calorie;
	}
	public void setCalorie(String calorie) {
		this.calorie = calorie;
	}
	public int getHr() {
		return hr;
	}
	public void setHr(int hr) {
		this.hr = hr;
	}
	
	
}
