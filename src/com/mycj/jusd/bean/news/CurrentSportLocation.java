package com.mycj.jusd.bean.news;
/**
 * 
 * 当前运动坐标	
 * 
 * @author zeej
 *
 */
public class CurrentSportLocation {
	// 实际值的10^12倍
	private long lng;
	// 实际值的10^12倍
	private long lat;
	
	public long getLng() {
		return lng;
	}
	public void setLng(long lng) {
		this.lng = lng;
	}
	public long getLat() {
		return lat;
	}
	public void setLat(long lat) {
		this.lat = lat;
	}
	
	public double getSportLng(){
		return (this.lng *1.0) / (10^12);
	}
	
	public double getSportLat(){
		return (this.lat *1.0) / (10^12);
	}
	
	
}
