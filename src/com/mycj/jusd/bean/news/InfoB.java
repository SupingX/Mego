package com.mycj.jusd.bean.news;

import org.litepal.crud.DataSupport;

public class InfoB extends DataSupport{
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
	public long getLat() {
		return lat;
	}
	@Override
	public String toString() {
		return "InfoB [datetime=" + datetime + ", index=" + index + ", no="
				+ no + ", lat=" + lat + ", lng=" + lng + "]";
	}
	public InfoB() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InfoB(long datetime, int index, long no, long lat, long lng,
			int signed) {
		super();
		this.datetime = datetime;
		this.index = index;
		this.no = no;
		this.lat = lat;
		this.lng = lng;
		this.signed = signed;
	}
	public void setLat(long lat) {
		this.lat = lat;
	}
	public long getLng() {
		return lng;
	}
	public void setLng(long lng) {
		this.lng = lng;
	}
	public int getSigned() {
		return signed;
	}
	public void setSigned(int signed) {
		this.signed = signed;
	}
	private long datetime;  // 单位:秒
	private int index; // 一天内第几次运动
	private long no; // 一次运动内的第几次数据
	private long lat;
	private long lng;
	private int signed; // 是否签到
	 
}
