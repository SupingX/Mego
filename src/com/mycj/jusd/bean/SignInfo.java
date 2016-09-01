package com.mycj.jusd.bean;
/**
 *  签到数据
 *	每个日期，每个时间点的签到数据。
 */
public class SignInfo {
	public final static String SDF = "yyyyMMdd hh:mm:ss";
	private String signDate;//签到时期  YYYYMMDD hh:mm:ss
	private int status; //分段 
	private float distance; //距离
	private long durationTime;//用时  分钟秒 
	private float pace;//配速 分钟 /公里
	public SignInfo( String signDate,int status, float distance,
			long durationTime, float pace) {
		super();
		this.status = status;
		this.distance = distance;
		this.durationTime = durationTime;
		this.pace = pace;
		this.signDate = signDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public long getDurationTime() {
		return durationTime;
	}
	public void setDurationTime(long durationTime) {
		this.durationTime = durationTime;
	}
	public float getPace() {
		return pace;
	}
	public void setPace(float pace) {
		this.pace = pace;
	}
	public SignInfo() {
		super();
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	
}
	
