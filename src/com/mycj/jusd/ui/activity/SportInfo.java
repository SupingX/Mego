package com.mycj.jusd.ui.activity;

public class SportInfo {
	private int type;
	private int step;
	private int distance;
	private int time; 
	private int calorie;
	private int hrAvg;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getCalorie() {
		return calorie;
	}
	public void setCalorie(int calorie) {
		this.calorie = calorie;
	}
	public int getHrAvg() {
		return hrAvg;
	}
	public void setHrAvg(int hrAvg) {
		this.hrAvg = hrAvg;
	}
	public SportInfo(int type, int step, int distance, int time, int calorie, int hrAvg) {
		super();
		this.type = type;
		this.step = step;
		this.distance = distance;
		this.time = time;
		this.calorie = calorie;
		this.hrAvg = hrAvg;
	}
	public SportInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "SportInfo [type=" + type + ", step=" + step + ", distance=" + distance + ", time=" + time + ", calorie=" + calorie + ", hrAvg=" + hrAvg + "]";
	}
	
}
