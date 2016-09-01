package com.mycj.jusd.bean.news;

import org.litepal.crud.DataSupport;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * 轨迹模式 带gps
 * 包括3种模式 步行、跑步和骑行，其中骑行没有步数
 * 
 * 当初与跑步和骑行时，这个时候会产生坐标数据 ：SportLocationHistory 这个数据的日期和次数对应起来，形成
 * 一天内第sportIndex次的运动轨迹。
 * 
 * @author zeej
 *
 */
public class SportHistory extends DataSupport implements Parcelable{
	private String sportDate;// 20010910 yyyyMMdd
	private String sportTime;// 122222 hhmmss
	private int type;// 步行：0；or跑步：1；or骑行：2； 
	private int sportIndex;// 一天的第几次运动
	private int sportNo;// 一次运动的第几次 从0开始 
	private int step;// 步数
	private int distance;// 实际值的100倍 距离 单位 km
	private int calorie;// 卡路里
	private int consuming;// 实际值的100倍 卡路里 单位千卡
	private int hr;
	private int pace;//配速
	private int speed;//速度
	private float freq;//步频
	private int signed;//00:未签订；10：已签到 。
	private long lat;
	private long lng;
	
	public int getPace() {
		return pace;
	}

	public void setPace(int pace) {
		this.pace = pace;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public float getFreq() {
		return freq;
	}

	public void setFreq(float freq) {
		this.freq = freq;
	}

	public int getSigned() {
		return signed;
	}

	public void setSigned(int signed) {
		this.signed = signed;
	}

	public long getLat() {
		return lat;
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

	@Override
	public String toString() {
		String result = ""
				+"["
				+"日期"+sportDate
				+"时间"+sportTime
				+"类型"+type
				+"运动序号"+sportIndex
				+"步数"+step
				+"距离"+distance
				+"卡路里"+calorie
				+"耗时"+consuming
				+"心率"+hr
				+"]"
				;
		
		return result;
	}
	
	public SportHistory() {
		super();
	}
	
	
	

	public SportHistory(String sportDate, String sportTime, int type,
			int sportIndex, int sportNo, int step, int distance, int calorie,
			int consuming, int hr, int pace, int speed, float freq, int signed,
			long lat, long lng) {
		super();
		this.sportDate = sportDate;
		this.sportTime = sportTime;
		this.type = type;
		this.sportIndex = sportIndex;
		this.sportNo = sportNo;
		this.step = step;
		this.distance = distance;
		this.calorie = calorie;
		this.consuming = consuming;
		this.hr = hr;
		this.pace = pace;
		this.speed = speed;
		this.freq = freq;
		this.signed = signed;
		this.lat = lat;
		this.lng = lng;
	}

	public int getSportNo() {
		return sportNo;
	}

	public void setSportNo(int sportNo) {
		this.sportNo = sportNo;
	}

	public String getSportDate() {
		return sportDate;
	}
	public void setSportDate(String sportDate) {
		this.sportDate = sportDate;
	}
	public String getSportTime() {
		return sportTime;
	}
	public void setSportTime(String sportTime) {
		this.sportTime = sportTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSportIndex() {
		return sportIndex;
	}
	public void setSportIndex(int sportIndex) {
		this.sportIndex = sportIndex;
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
	public int getConsuming() {
		return consuming;
	}
	public void setConsuming(int consuming) {
		this.consuming = consuming;
	}
	public int getHr() {
		return hr;
	}
	public void setHr(int hr) {
		this.hr = hr;
	}
	/*
	 * private String sportDate;// 20010910
	private String sportTime;// 12:22:22
	private int type;// 步行：0；or跑步：1；or骑行：2； 
	private int sportIndex;// 一天内第几次 运动 
	private int step;// 步数
	private int distance;// 实际值的100倍 距离 单位 km
	private int calorie;// 卡路里
	private int consuming;// 实际值的100倍 卡路里 单位千卡
	private int hr;// 心率
	
		private int pace;//配速
	private int speed;//速度
	private float freq;//步频
	private int signed;//00:未签订；10：已签到 。
	private long lat;
	private long lng;
	 */
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.sportDate);
		dest.writeString(this.sportTime);
		dest.writeInt(type);
		dest.writeInt(sportIndex);
		dest.writeInt(sportNo);
		dest.writeInt(step);
		dest.writeInt(distance);
		dest.writeInt(calorie);
		dest.writeInt(consuming);
		dest.writeInt(hr);
		
		dest.writeInt(pace);
		dest.writeInt(speed);
		dest.writeFloat(freq);
		dest.writeInt(signed);
		dest.writeLong(lat);
		dest.writeLong(lng);
		
	}
	
public static final Parcelable.Creator<SportHistory> CREATOR = new Creator<SportHistory>() {
		
		@Override
		public SportHistory[] newArray(int size) {
			return new SportHistory[size];
		}
		
		@Override
		public SportHistory createFromParcel(Parcel source) {
			String sportDate = source.readString();
			String sportTime = source.readString();
			int type = source.readInt();
			int sportIndex = source.readInt();
			int sportNo = source.readInt();
			int step = source.readInt();
			int distance = source.readInt();
			int calorie = source.readInt();
			int consuming = source.readInt();
			int hr = source.readInt();
			int pace = source.readInt();
			int speed = source.readInt();
			float freq = source.readFloat();
			int signed = source.readInt();
			long lat = source.readLong();
			long lng = source.readLong();
			
			return new SportHistory(sportDate, sportTime, type, sportIndex,sportNo,step, distance, calorie, consuming,hr
					,pace,speed,freq,signed,lat,lng
					);
		}
	};

	
	 
}
