package com.mycj.jusd.ui.activity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 当前的运动信息
 * 可能为计步or轨迹
 *
 */
public class SportInfo implements Parcelable{
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
	}
	@Override
	public String toString() {
		return "当前运动信息计步or轨迹SportInfo [type=" + type + ", step=" + step + ", distance=" + distance + ", time=" + time + ", calorie=" + calorie + ", hrAvg=" + hrAvg + "]";
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.type);
		dest.writeInt(this.step);
		dest.writeInt(this.distance);
		dest.writeInt(this.time);
		dest.writeInt(this.calorie);
		dest.writeInt(this.hrAvg);
	}
public static final Parcelable.Creator<SportInfo> CREATOR = new Creator<SportInfo>() {
		
		@Override
		public SportInfo[] newArray(int size) {
			return new SportInfo[size];
		}
		
		@Override
		public SportInfo createFromParcel(Parcel source) {
			int type = source.readInt();
			int step = source.readInt();
			int distance = source.readInt();
			int time = source.readInt();
			int calorie = source.readInt();
			int hrAvg = source.readInt();
			return new SportInfo(type, step, distance, time, calorie, hrAvg);
		}
	};
}
