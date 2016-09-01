package com.mycj.jusd.bean.news;

import org.litepal.crud.DataSupport;

public class InfoA extends DataSupport {
	private long datetime; // 单位:秒
	private int index; // 一天内第几次运动
	private long no; // 一次运动内的第几次数据
	private int type; // 类型
	private int step; //计步 
	private int distance; // 距离
	private int calorie; // 卡路里
	private int time; // 耗时 
	private int hr; //心率
	public long getDatetime() {
		return datetime;
	}
	public void setDatetime(long datetime) {
		this.datetime = datetime;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
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
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getHr() {
		return hr;
	}
	public void setHr(int hr) {
		this.hr = hr;
	}
	
	public InfoA(long datetime, int index, long no, int type, int step,
			int distance, int calorie, int time, int hr) {
		super();
		this.datetime = datetime;
		this.index = index;
		this.no = no;
		this.type = type;
		this.step = step;
		this.distance = distance;
		this.calorie = calorie;
		this.time = time;
		this.hr = hr;
	}
	public InfoA() {
		super();
	}
	@Override
	public String toString() {
		return "InfoA [datetime=" + datetime + ", index=" + index + ", no="
				+ no + ", step=" + step + ", distance=" + distance
				+ ", calorie=" + calorie + ", time=" + time + ", hr=" + hr
				+ "]";
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
