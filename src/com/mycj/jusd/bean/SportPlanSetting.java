package com.mycj.jusd.bean;

public class SportPlanSetting {
	private boolean sportPlanOnoff;
	private String startTime;
	private int goalStep;
	private int goalDistance;
	private boolean autoSignOnoff;
	private int autoSignDistance;
	private boolean paceSettingOnoff;
	private String paceMin;
	private String paceMax;
	private int sportStyle;
	public SportPlanSetting(boolean sportPlanOnoff, String startTime, int goalStep, int goalDistance, boolean autoSignOnoff, int autoSignDistance, boolean paceSettingOnoff, String paceMin,
			String paceMax, int sportStyle) {
		super();
		this.sportPlanOnoff = sportPlanOnoff;
		this.startTime = startTime;
		this.goalStep = goalStep;
		this.goalDistance = goalDistance;
		this.autoSignOnoff = autoSignOnoff;
		this.autoSignDistance = autoSignDistance;
		this.paceSettingOnoff = paceSettingOnoff;
		this.paceMin = paceMin;
		this.paceMax = paceMax;
		this.sportStyle = sportStyle;
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
	public boolean isSportPlanOnoff() {
		return sportPlanOnoff;
	}
	public void setSportPlanOnoff(boolean sportPlanOnoff) {
		this.sportPlanOnoff = sportPlanOnoff;
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
	public boolean isAutoSignOnoff() {
		return autoSignOnoff;
	}
	public void setAutoSignOnoff(boolean autoSignOnoff) {
		this.autoSignOnoff = autoSignOnoff;
	}
	public int getAutoSignDistance() {
		return autoSignDistance;
	}
	public void setAutoSignDistance(int autoSignDistance) {
		this.autoSignDistance = autoSignDistance;
	}
	public boolean isPaceSettingOnoff() {
		return paceSettingOnoff;
	}
	public void setPaceSettingOnoff(boolean paceSettingOnoff) {
		this.paceSettingOnoff = paceSettingOnoff;
	}
	public String getPaceMin() {
		return paceMin;
	}
	public void setPaceMin(String paceMin) {
		this.paceMin = paceMin;
	}
	public String getPaceMax() {
		return paceMax;
	}
	public void setPaceMax(String paceMax) {
		this.paceMax = paceMax;
	}
	public int getSportStyle() {
		return sportStyle;
	}
	public void setSportStyle(int sportStyle) {
		this.sportStyle = sportStyle;
	}
	
	
}
