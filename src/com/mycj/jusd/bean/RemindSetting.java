package com.mycj.jusd.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class RemindSetting implements Parcelable {
	private int incomingOnoff;
	private int alarmOnoff;
	private String alarmTime; // 00:00
	private int longSitOnoff;
	private int longSitValue;
	private int birthdayOnoff;
//	private String birthdayDate; //11/31
	private String birthdayTime; //22:00
	private int nowtimeOnoff;

	
	
	public RemindSetting(int incomingOnoff, int alarmOnoff, String alarmTime,
			int longSitOnoff, int longSitValue, int birthdayOnoff,
			String birthdayTime, int nowtimeOnoff) {
		super();
		this.incomingOnoff = incomingOnoff;
		this.alarmOnoff = alarmOnoff;
		this.alarmTime = alarmTime;
		this.longSitOnoff = longSitOnoff;
		this.longSitValue = longSitValue;
		this.birthdayOnoff = birthdayOnoff;
		this.birthdayTime = birthdayTime;
		this.nowtimeOnoff = nowtimeOnoff;
	}
	public RemindSetting() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getAlarmTime() {
		return alarmTime;
	}
	
	public int getAlarmHour(){
		try {
			return Integer.valueOf(alarmTime.split(":")[0]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getAlarmMin(){
		try {
			return Integer.valueOf(alarmTime.split(":")[1]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
	public int getLongSitValue() {
		return longSitValue;
	}
	public void setLongSitValue(int longSitValue) {
		this.longSitValue = longSitValue;
	}
	
	
	public String getBirthdayTime() {
		return birthdayTime;
	}
	public void setBirthdayTime(String birthdayTime) {
		this.birthdayTime = birthdayTime;
	}
	
	public int getBirthdayHour(){
		
		try {
			return Integer.valueOf(birthdayTime.split(":")[0]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public int getBirthdayMin(){
		
		try {
			return Integer.valueOf(birthdayTime.split(":")[1]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	public int getIncomingOnoff() {
		return incomingOnoff;
	}
	public void setIncomingOnoff(int incomingOnoff) {
		this.incomingOnoff = incomingOnoff;
	}
	public int getAlarmOnoff() {
		return alarmOnoff;
	}
	public void setAlarmOnoff(int alarmOnoff) {
		this.alarmOnoff = alarmOnoff;
	}
	public int getLongSitOnoff() {
		return longSitOnoff;
	}
	public void setLongSitOnoff(int longSitOnoff) {
		this.longSitOnoff = longSitOnoff;
	}
	public int getBirthdayOnoff() {
		return birthdayOnoff;
	}
	public void setBirthdayOnoff(int birthdayOnoff) {
		this.birthdayOnoff = birthdayOnoff;
	}
	public int getNowtimeOnoff() {
		return nowtimeOnoff;
	}
	public void setNowtimeOnoff(int nowtimeOnoff) {
		this.nowtimeOnoff = nowtimeOnoff;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		/**
		 * 	private int incomingOnoff;
	private int alarmOnoff;
	private String alarmTime; // 00:00
	private int longSitOnoff;
	private int longSitValue;
	private int birthdayOnoff;
	private String birthdayDate; //11/31
	private String birthdayTime; //22:00
	private int nowtimeOnoff;
		 */
		
		dest.writeInt(incomingOnoff);
		dest.writeInt(alarmOnoff);
		dest.writeString(alarmTime);
		dest.writeInt(longSitOnoff);
		dest.writeInt(longSitValue);
		dest.writeInt(birthdayOnoff);
		dest.writeString(birthdayTime);
		dest.writeInt(nowtimeOnoff);
	}
	
	public static final Parcelable.Creator<RemindSetting> CREATOR = new Creator<RemindSetting>() {

		@Override
		public RemindSetting createFromParcel(Parcel source) {
			int incomingOnoff = source.readInt();
			int alarmOnoff = source.readInt();
			String alarmTime = source.readString();
			int longSitOnoff = source.readInt();
			int longSitValue = source.readInt();
			int birthdayOnoff = source.readInt();
			String birthdayTime = source.readString();
			int nowtimeOnoff = source.readInt();
			return new RemindSetting(incomingOnoff, alarmOnoff, alarmTime, longSitOnoff, longSitValue, birthdayOnoff, birthdayTime, nowtimeOnoff);
		}

		@Override
		public RemindSetting[] newArray(int size) {
			return new RemindSetting[size];
		}
	};
}
