//package com.mycj.jusd.bean.news;
//
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
///**
// * 当前运动信息
// * 
// * @author zeej
// *
// */
//public class CurrentSportInfo implements Parcelable {
//	private int type;
//	private int idx;
//	private int step;
//	private long distance;
//	private long consuming;
//	private long calorie;
//	private int hr;
//	public int getType() {
//		return type;
//	}
//	public void setType(int type) {
//		this.type = type;
//	}
//	public int getStep() {
//		return step;
//	}
//	public void setStep(int step) {
//		this.step = step;
//	}
//	public long getDistance() {
//		return distance;
//	}
//	public void setDistance(long distance) {
//		this.distance = distance;
//	}
//	public long getConsuming() {
//		return consuming;
//	}
//	public void setConsuming(long consuming) {
//		this.consuming = consuming;
//	}
//	public long getCalorie() {
//		return calorie;
//	}
//	public void setCalorie(long calorie) {
//		this.calorie = calorie;
//	}
//	public int getHr() {
//		return hr;
//	}
//	public void setHr(int hr) {
//		this.hr = hr;
//	}
///*	public CurrentSportInfo(int type, int step, long distance, long consuming,
//			long calorie, int hr) {
//		super();
//		this.type = type;
//		this.step = step;
//		this.distance = distance;
//		this.consuming = consuming;
//		this.calorie = calorie;
//		this.hr = hr;
//	}*/
//	
//	public CurrentSportInfo() {
//		super();
//	}
//	public CurrentSportInfo(int type, int index, int step, long distance,
//		long consuming, long calorie, int hr) {
//	super();
//	this.type = type;
//	this.idx = index;
//	this.step = step;
//	this.distance = distance;
//	this.consuming = consuming;
//	this.calorie = calorie;
//	this.hr = hr;
//}
//	@Override
//	public int describeContents() {
//		return 0;
//	}
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//		dest.writeInt(this.type);
//		dest.writeInt(this.idx);
//		dest.writeInt(this.step);
//		dest.writeLong(this.distance);
//		dest.writeLong(this.consuming);
//		dest.writeLong(this.calorie);
//		dest.writeInt(this.hr);
//		
//	}
//	
//
//	public int getIdx() {
//		return idx;
//	}
//	public void setIdx(int idx) {
//		this.idx = idx;
//	}
//
//	public static final Parcelable.Creator<CurrentSportInfo> CREATOR = new Creator<CurrentSportInfo>() {
//		
//		@Override
//		public CurrentSportInfo[] newArray(int size) {
//			return new CurrentSportInfo[size];
//		}
//		
//		@Override
//		public CurrentSportInfo createFromParcel(Parcel source) {
//			int type = source.readInt();
//			int idx = source.readInt();
//			int step = source.readInt();
//			long distance = source.readLong();
//			long time = source.readLong();
//			long calorie = source.readLong();
//			int hrAvg = source.readInt();
//			
//			return new CurrentSportInfo(type, idx,step, distance, time, calorie, hrAvg);
//		}
//	};
//	
//}
