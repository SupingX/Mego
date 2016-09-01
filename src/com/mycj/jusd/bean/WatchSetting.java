package com.mycj.jusd.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 手表设置
 *
 */
public class WatchSetting implements Parcelable {
	private int unit; //00 公制_01 英制
	private int height;
	private int weight;
	private int stepOnoff;
	private String birthday;//yyyy-MM-dd
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	private int sex;
	private int walkLength;
	private int runLength;
	private int hrOnoff;
	private int hrMin;
	private int hrMax;
	private int sleepOnoff;
	private String sleepTime;// 22:00
	private String awakTime;// 03:00

	public int getSleepHour() {
		try {
			if (sleepTime != null && !sleepTime.equals("")) {
				return Integer.valueOf(sleepTime.split(":")[0]);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	public int getSleepMin() {
		try {
			if (sleepTime != null && !sleepTime.equals("")) {

				return Integer.valueOf(sleepTime.split(":")[1]);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	public int getAwakHour() {
		try {
			if (awakTime != null && !awakTime.equals("")) {
				return Integer.valueOf(awakTime.split(":")[0]);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	
	public int getAwakMin() {
		try {
			if (awakTime != null && !awakTime.equals("")) {
				return Integer.valueOf(awakTime.split(":")[1]);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	public WatchSetting() {
		super();
	}

	

	public WatchSetting(int unit, int height, int weight, int stepOnoff,
			String birthday, int sex, int walkLength, int runLength,
			int hrOnoff, int hrMin, int hrMax, int sleepOnoff,
			String sleepTime, String awakTime) {
		super();
		this.unit = unit;
		this.height = height;
		this.weight = weight;
		this.stepOnoff = stepOnoff;
		this.birthday = birthday;
		this.sex = sex;
		this.walkLength = walkLength;
		this.runLength = runLength;
		this.hrOnoff = hrOnoff;
		this.hrMin = hrMin;
		this.hrMax = hrMax;
		this.sleepOnoff = sleepOnoff;
		this.sleepTime = sleepTime;
		this.awakTime = awakTime;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}


	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getWalkLength() {
		return walkLength;
	}

	public void setWalkLength(int walkLength) {
		this.walkLength = walkLength;
	}

	public int getRunLength() {
		return runLength;
	}

	public void setRunLength(int runLength) {
		this.runLength = runLength;
	}

	public int getHrOnoff() {
		return hrOnoff;
	}

	public void setHrOnoff(int hrOnoff) {
		this.hrOnoff = hrOnoff;
	}

	public int getHrMin() {
		return hrMin;
	}

	public void setHrMin(int hrMin) {
		this.hrMin = hrMin;
	}

	public int getHrMax() {
		return hrMax;
	}

	public void setHrMax(int hrMax) {
		this.hrMax = hrMax;
	}

	public int getSleepOnoff() {
		return sleepOnoff;
	}

	public void setSleepOnoff(int sleepOnoff) {
		this.sleepOnoff = sleepOnoff;
	}

	public String getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(String sleepTime) {
		this.sleepTime = sleepTime;
	}

	public String getAwakTime() {
		return awakTime;
	}

	public void setAwakTime(String awakTime) {
		this.awakTime = awakTime;
	}

	
	
	@Override
	public int describeContents() {
		return 0;
	}
	/**
	 * 	private int unit;
	private int height;
	private int weight;
	private int age;
	private int sex;
	private int walkLength;
	private int runLength;
	private int hrOnoff;
	private int hrMin;
	private int hrMax;
	private int sleepOnoff;
	private String sleepTime;
	private String awakTime;
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.unit);
		dest.writeInt(this.height);
		dest.writeInt(this.weight);
		dest.writeInt(this.sex);
		dest.writeString(this.birthday);
		dest.writeInt(this.stepOnoff);
		dest.writeInt(this.walkLength);
		dest.writeInt(this.runLength);
		dest.writeInt(this.hrOnoff);
		dest.writeInt(this.hrMin);
		dest.writeInt(this.hrMax);
		dest.writeInt(this.sleepOnoff);
		dest.writeString(this.sleepTime);
		dest.writeString(this.awakTime);
	}
	
	public int getStepOnoff() {
		return stepOnoff;
	}

	public void setStepOnoff(int stepOnoff) {
		this.stepOnoff = stepOnoff;
	}

	public static final Parcelable.Creator<WatchSetting> CREATOR = new Creator<WatchSetting>() {

		@Override
		public WatchSetting createFromParcel(Parcel source) {
			int unit = source.readInt();
			int height = source.readInt();
			int weight = source.readInt();
			int sex = source.readInt();
			String birthday = source.readString();
			int stepOnoff = source.readInt();
			int walkLength = source.readInt();
			int runLength = source.readInt();
			int hrOnoff = source.readInt();
			int hrMin = source.readInt();
			int hrMax = source.readInt();
			int sleepOnoff = source.readInt();
			String sleepTime = source.readString();
			String awakTime = source.readString();
			return new WatchSetting(unit, height, weight,  sex,birthday, stepOnoff,walkLength, runLength, hrOnoff, hrMin, hrMax, sleepOnoff, sleepTime, awakTime);
		}

		@Override
		public WatchSetting[] newArray(int size) {
			return new WatchSetting[size];
		}
	};
}
