package com.mycj.jusd.bean;

public class PathInfo {
	
	private long timeStamp;
	private int dataIndex;
	private int markerIndex;
	private String lng;
	private String lat;
	private int distance;
	private long time;
	private int cal;
	private int hr;
	@Override
	public String toString() {
		return "PathInfo [timeStamp=" + timeStamp + ", dataIndex=" + dataIndex + ", markerIndex=" + markerIndex + ", lng=" + lng + ", lat=" + lat + ", distance=" + distance + ", time=" + time
				+ ", cal=" + cal + ", hr=" + hr + "]";
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
	public int getMarkerIndex() {
		return markerIndex;
	}
	public void setMarkerIndex(int markerIndex) {
		this.markerIndex = markerIndex;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getCal() {
		return cal;
	}
	public void setCal(int cal) {
		this.cal = cal;
	}
	public int getHr() {
		return hr;
	}
	public void setHr(int hr) {
		this.hr = hr;
	}
	public PathInfo(long timeStamp, int dataIndex, int markerIndex, String lng, String lat, int distance, long time, int cal, int hr) {
		super();
		this.timeStamp = timeStamp;
		this.dataIndex = dataIndex;
		this.markerIndex = markerIndex;
		this.lng = lng;
		this.lat = lat;
		this.distance = distance;
		this.time = time;
		this.cal = cal;
		this.hr = hr;
	}
	public PathInfo() {
		super();
	}
	

}
