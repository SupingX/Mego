package com.mycj.jusd.bean.news;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/**
 * 运动计划
 *
 */
public class SportPlanSetting implements Parcelable{
	private int sportPlanOnoff;
	private String startTime; // hh:mm
	private int goalStep;
	private int goalDistance;
	private int autoSignOnoff;
	private int sportGoalOnoff;
	private int autoSignDistance;
	private int paceSettingOnoff;
	private int paceMin;
	private int paceMax;
	private int sportStyle;
	
	public SportPlanSetting(int sportPlanOnoff, String startTime,
			int goalStep, int goalDistance, int autoSignOnoff,
			int sportGoalOnoff, int autoSignDistance,
			int paceSettingOnoff, int paceMin, int paceMax,
			 int sportStyle) {
		super();
		this.sportPlanOnoff = sportPlanOnoff;
		this.startTime = startTime;
		this.goalStep = goalStep;
		this.goalDistance = goalDistance;
		this.autoSignOnoff = autoSignOnoff;
		this.sportGoalOnoff = sportGoalOnoff;
		this.autoSignDistance = autoSignDistance;
		this.paceSettingOnoff = paceSettingOnoff;
		this.paceMin = paceMin;
		this.paceMax = paceMax;
		this.sportStyle = sportStyle;
	}

	public int getStartHour(){
		try {
			if (startTime != null && !startTime.equals("")) {
				return Integer.valueOf(startTime.split(":")[0]);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	
	public int getStartMin(){
		try {
			if (startTime != null && !startTime.equals("")) {
				return Integer.valueOf(startTime.split(":")[0]);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	
	public SportPlanSetting() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "SportPlanSetting [sportPlanOnoff=" + sportPlanOnoff + ", startTime=" + startTime + ", goalStep=" + goalStep + ", goalDistance=" + goalDistance + ", autoSignOnoff=" + autoSignOnoff
				+ ", autoSignDistance=" + autoSignDistance + ", paceSettingOnoff=" + paceSettingOnoff + ", paceMin=" + paceMin + ", paceMax=" + paceMax + ", sportStyle=" + sportStyle + "]";
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public int getGoalStep() {
		return goalStep;
	}
	public void setGoalStep(int goalStep) {
		this.goalStep = goalStep;
	}
	public int getGoalDistance() {
		return goalDistance;
	}
	public void setGoalDistance(int goalDistance) {
		this.goalDistance = goalDistance;
	}
	public int getAutoSignDistance() {
		return autoSignDistance;
	}
	public void setAutoSignDistance(int autoSignDistance) {
		this.autoSignDistance = autoSignDistance;
	}
	public int getPaceMin() {
		return paceMin;
	}
	public void setPaceMin(int paceMin) {
		this.paceMin = paceMin;
	}
	public int getPaceMax() {
		return paceMax;
	}
	public void setPaceMax(int paceMax) {
		this.paceMax = paceMax;
	}
	public int getSportStyle() {
		return sportStyle;
	}
	public void setSportStyle(int sportStyle) {
		this.sportStyle = sportStyle;
	}

	


	public int getSportPlanOnoff() {
		return sportPlanOnoff;
	}

	public void setSportPlanOnoff(int sportPlanOnoff) {
		this.sportPlanOnoff = sportPlanOnoff;
	}

	public int getAutoSignOnoff() {
		return autoSignOnoff;
	}

	public void setAutoSignOnoff(int autoSignOnoff) {
		this.autoSignOnoff = autoSignOnoff;
	}

	public int getSportGoalOnoff() {
		return sportGoalOnoff;
	}

	public void setSportGoalOnoff(int sportGoalOnoff) {
		this.sportGoalOnoff = sportGoalOnoff;
	}

	public int getPaceSettingOnoff() {
		return paceSettingOnoff;
	}

	public void setPaceSettingOnoff(int paceSettingOnoff) {
		this.paceSettingOnoff = paceSettingOnoff;
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		/**
		 * 	private int sportPlanOnoff;
	private String startTime; // hh:mm
	private int goalStep;
	private int goalDistance;
	private int autoSignOnoff;
	private int sportGoalOnoff;
	private int autoSignDistance;
	private int paceSettingOnoff;
	private int paceMin;
	private int paceMax;
	private int sportStyleOnoff;
	private int sportStyle;
		 */
		
		dest.writeInt(sportPlanOnoff);
		dest.writeString(startTime);
		dest.writeInt(goalStep);
		dest.writeInt(goalDistance);
		dest.writeInt(autoSignOnoff);
		dest.writeInt(sportGoalOnoff);
		dest.writeInt(autoSignDistance);
		dest.writeInt(paceSettingOnoff);
		dest.writeInt(paceMin);
		dest.writeInt(paceMax);
		dest.writeInt(sportStyle);
	}
	
public static final Parcelable.Creator<SportPlanSetting> CREATOR = new Creator<SportPlanSetting>() {
		
		@Override
		public SportPlanSetting[] newArray(int size) {
			return new SportPlanSetting[size];
		}
		
		@Override
		public SportPlanSetting createFromParcel(Parcel source) {
			int sportPlanOnoff = source.readInt();
			String startTime = source.readString();
			int goalStep = source.readInt();
			int goalDistance = source.readInt();
			int autoSignOnoff = source.readInt();
			int sportGoalOnoff = source.readInt();
			int autoSignDistance = source.readInt();
			int paceSettingOnoff = source.readInt();
			int paceMin = source.readInt();
			int paceMax = source.readInt();
			int sportStyle = source.readInt();
			return new SportPlanSetting(sportPlanOnoff, startTime, goalStep, goalDistance, autoSignOnoff, sportGoalOnoff, autoSignDistance, paceSettingOnoff, paceMin, paceMax, sportStyle);
		}
	};
	
	
}
