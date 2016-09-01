package com.mycj.jusd.bean.news;

public class SportCountHistory {
	private String sportDate;// 20010910
	private int step;// 步数
	private int distance;// 实际值的100倍 距离 单位 km
	private int calorie;// 卡路里
	private int consuming;// 实际值的100倍 卡路里 单位千卡
	private int hr;
	public String getSportDate() {
		return sportDate;
	}
	public void setSportDate(String sportDate) {
		this.sportDate = sportDate;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getCalorie() {
		return calorie;
	}
	public void setCalorie(int calorie) {
		this.calorie = calorie;
	}
	public int getConsuming() {
		return consuming;
	}
	public void setConsuming(int consuming) {
		this.consuming = consuming;
	}
	public int getHr() {
		return hr;
	}
	public void setHr(int hr) {
		this.hr = hr;
	}
	public SportCountHistory(String sportDate, int step, int distance,
			int calorie, int consuming, int hr) {
		super();
		this.sportDate = sportDate;
		this.step = step;
		this.distance = distance;
		this.calorie = calorie;
		this.consuming = consuming;
		this.hr = hr;
	}
	
	
	
}
