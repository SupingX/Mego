//package com.mycj.jusd.bean;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
///**
// * 运动计划
// *
// */
//public class SportPlanSetting implements Parcelable{
//	private int sportPlanOnoff;
//	private String startTime; // hh:mm
//	private int goalStep;
//	private int goalDistance;
//	private int autoSignOnoff;
//	private int autoSignDistance;
//	private int paceSettingOnoff;
//	private String paceMin;
//	private String paceMax;
//	private int sportStyleOnoff;
//	private int sportStyle;
//	public int getSportPlanOnoff() {
//		return sportPlanOnoff;
//	}
//
//	public void setSportPlanOnoff(int sportPlanOnoff) {
//		this.sportPlanOnoff = sportPlanOnoff;
//	}
//
//	public int getAutoSignOnoff() {
//		return autoSignOnoff;
//	}
//
//	public void setAutoSignOnoff(int autoSignOnoff) {
//		this.autoSignOnoff = autoSignOnoff;
//	}
//
//	public int getPaceSettingOnoff() {
//		return paceSettingOnoff;
//	}
//
//	public void setPaceSettingOnoff(int paceSettingOnoff) {
//		this.paceSettingOnoff = paceSettingOnoff;
//	}
//
//	public int getSportStyleOnoff() {
//		return sportStyleOnoff;
//	}
//
//	public void setSportStyleOnoff(int sportStyleOnoff) {
//		this.sportStyleOnoff = sportStyleOnoff;
//	}
//
//	public SportPlanSetting(int sportPlanOnoff, String startTime, int goalStep, int goalDistance, int autoSignOnoff, int autoSignDistance, int paceSettingOnoff, String paceMin,
//			String paceMax,int sportStyleOnoff, int sportStyle) {
//		super();
//		this.sportPlanOnoff = sportPlanOnoff;
//		this.startTime = startTime;
//		this.goalStep = goalStep;
//		this.goalDistance = goalDistance;
//		this.autoSignOnoff = autoSignOnoff;
//		this.autoSignDistance = autoSignDistance;
//		this.paceSettingOnoff = paceSettingOnoff;
//		this.paceMin = paceMin;
//		this.paceMax = paceMax;
//		this.sportStyle = sportStyle;
//		this.sportPlanOnoff = sportPlanOnoff;
//	}
//	
//	public int getStartHour(){
//		try {
//			if (startTime != null && !startTime.equals("")) {
//				return Integer.valueOf(startTime.split(":")[0]);
//			}
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//			return 0;
//		}
//		return 0;
//	}
//	
//	public int getStartMin(){
//		try {
//			if (startTime != null && !startTime.equals("")) {
//				return Integer.valueOf(startTime.split(":")[0]);
//			}
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//			return 0;
//		}
//		return 0;
//	}
//	
//	public SportPlanSetting() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	@Override
//	public String toString() {
//		return "SportPlanSetting [sportPlanOnoff=" + sportPlanOnoff + ", startTime=" + startTime + ", goalStep=" + goalStep + ", goalDistance=" + goalDistance + ", autoSignOnoff=" + autoSignOnoff
//				+ ", autoSignDistance=" + autoSignDistance + ", paceSettingOnoff=" + paceSettingOnoff + ", paceMin=" + paceMin + ", paceMax=" + paceMax + ", sportStyle=" + sportStyle + "]";
//	}
//	public String getStartTime() {
//		return startTime;
//	}
//	public void setStartTime(String startTime) {
//		this.startTime = startTime;
//	}
//	public int getGoalStep() {
//		return goalStep;
//	}
//	public void setGoalStep(int goalStep) {
//		this.goalStep = goalStep;
//	}
//	public int getGoalDistance() {
//		return goalDistance;
//	}
//	public void setGoalDistance(int goalDistance) {
//		this.goalDistance = goalDistance;
//	}
//	public int getAutoSignDistance() {
//		return autoSignDistance;
//	}
//	public void setAutoSignDistance(int autoSignDistance) {
//		this.autoSignDistance = autoSignDistance;
//	}
//	public String getPaceMin() {
//		return paceMin;
//	}
//	public void setPaceMin(String paceMin) {
//		this.paceMin = paceMin;
//	}
//	public String getPaceMax() {
//		return paceMax;
//	}
//	public void setPaceMax(String paceMax) {
//		this.paceMax = paceMax;
//	}
//	public int getSportStyle() {
//		return sportStyle;
//	}
//	public void setSportStyle(int sportStyle) {
//		this.sportStyle = sportStyle;
//	}
//
//
//	@Override
//	public int describeContents() {
//		return 0;
//	}
//
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//		/**
//		 * 	private boolean sportPlanOnoff;
//	private String startTime; // hh:mm
//	private int goalStep;
//	private int goalDistance;
//	private boolean autoSignOnoff;
//	private int autoSignDistance;
//	private boolean paceSettingOnoff;
//	private String paceMin;
//	private String paceMax;
//	private boolean sportStyleOnoff;
//	private int sportStyle;
//		 */
//		
//		dest.writeInt(sportPlanOnoff);
//		dest.writeString(startTime);
//		dest.writeInt(goalStep);
//		dest.writeInt(goalDistance);
//		dest.writeInt(autoSignOnoff);
//		dest.writeInt(autoSignDistance);
//		dest.writeInt(paceSettingOnoff);
//		dest.writeInt(paceMin);
//		
//	}
//	
//	
//}
