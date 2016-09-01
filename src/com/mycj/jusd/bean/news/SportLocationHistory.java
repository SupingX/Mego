//package com.mycj.jusd.bean.news;
//
//import org.litepal.crud.DataSupport;
//
///**
// * 历史轨迹坐标
// * 每三秒产生一个坐标数据 
// * 
// * @author Administrator
// *
// */
//public class SportLocationHistory extends DataSupport{
//	private String sportDate;
//	private String sportTime;
//	private int sportIndex;
//	private int sportNo;
//	private int signed;//00:未签订；10：已签到 。
//	private long lat;//实际值的10^12倍
//	private long lng;//实际值的10^12倍
//	public float getDistance() {
//		return distance;
//	}
//
//	public void setDistance(float distance) {
//		this.distance = distance;
//	}
//
//	public int getPace() {
//		return pace;
//	}
//
//	public void setPace(int pace) {
//		this.pace = pace;
//	}
//
//	public int getDuration() {
//		return duration;
//	}
//
//	public void setDuration(int duration) {
//		this.duration = duration;
//	}
//
//	private float distance;
//	private int pace;
//	private int duration;
//	public String getSportDate() {
//		return sportDate;
//	}
//	
//	public SportLocationHistory(String sportDate, String sportTime,
//			int sportIndex, int sportNo, int signed, long lat, long lng) {
//		super();
//		this.sportDate = sportDate;
//		this.sportTime = sportTime;
//		this.sportIndex = sportIndex;
//		this.sportNo = sportNo;
//		this.signed = signed;
//		this.lat = lat;
//		this.lng = lng;
//	}
//
//	public String getSportTime() {
//		return sportTime;
//	}
//
//	public void setSportTime(String sportTime) {
//		this.sportTime = sportTime;
//	}
//
//	public int getSportIndex() {
//		return sportIndex;
//	}
//
//	public void setSportIndex(int sportIndex) {
//		this.sportIndex = sportIndex;
//	}
//
//	public int getSportNo() {
//		return sportNo;
//	}
//
//	public void setSportNo(int sportNo) {
//		this.sportNo = sportNo;
//	}
//
//	public int getSigned() {
//		return signed;
//	}
//
//	public void setSigned(int signed) {
//		this.signed = signed;
//	}
//
//	public long getLat() {
//		return lat;
//	}
//
//	public void setLat(long lat) {
//		this.lat = lat;
//	}
//
//	public long getLng() {
//		return lng;
//	}
//
//	public void setLng(long lng) {
//		this.lng = lng;
//	}
//
//	public void setSportDate(String sportDate) {
//		this.sportDate = sportDate;
//	}
//
//	public SportLocationHistory() {
//		super();
//	}
//	
//	
//}
