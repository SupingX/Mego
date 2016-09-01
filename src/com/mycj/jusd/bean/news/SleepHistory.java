package com.mycj.jusd.bean.news;

import org.litepal.crud.DataSupport;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 睡觉历史
 * 
 * @author zeej
 *
 */
public class SleepHistory extends DataSupport implements Parcelable{
	private int sleepIndex;
	private String sleepDate;//格式20121201yyyyMMdd
	private String startTime;//格式2210 --> hhmm
	private String endTime;//格式0701 --> hhmm
	private int sleepDeep;//单位：分钟
	private int sleepLight;//单位：分钟

	public int getSleepIndex() {
		return sleepIndex;
	}

	public void setSleepIndex(int sleepIndex) {
		this.sleepIndex = sleepIndex;
	}

	public String getSleepDate() {
		return sleepDate;
	}

	public void setSleepDate(String sleepDate) {
		this.sleepDate = sleepDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getSleepDeep() {
		return sleepDeep;
	}

	public void setSleepDeep(int sleepDeep) {
		this.sleepDeep = sleepDeep;
	}

	public int getSleepLight() {
		return sleepLight;
	}

	public void setSleepLight(int sleepLight) {
		this.sleepLight = sleepLight;
	}

	public SleepHistory(int sleepIndex, String sleepDate, String startTime,
			String endTime, int sleepDeep, int sleepLight) {
		super();
		this.sleepIndex = sleepIndex;
		this.sleepDate = sleepDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.sleepDeep = sleepDeep;
		this.sleepLight = sleepLight;
	}

	public SleepHistory() {
		super();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.sleepIndex);
		dest.writeString(sleepDate);
		dest.writeString(startTime);
		dest.writeString(endTime);
		dest.writeInt(sleepDeep);
		dest.writeInt(sleepLight);
	}
	
	
	public static final Parcelable.Creator<SleepHistory> CREATOR = new Creator<SleepHistory>() {
		
		@Override
		public SleepHistory[] newArray(int size) {
			return new SleepHistory[size];
		}
		
		@Override
		public SleepHistory createFromParcel(Parcel source) {
			int type = source.readInt();
			String step = source.readString();
			String distance = source.readString();
			String time = source.readString();
			int calorie = source.readInt();
			int hrAvg = source.readInt();
			return new SleepHistory(type, step, distance, time, calorie, hrAvg);
		}
	};
}
