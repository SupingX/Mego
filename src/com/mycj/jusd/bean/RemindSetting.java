package com.mycj.jusd.bean;

public class RemindSetting {
	private boolean incomingOnoff;
	private boolean alarmOnoff;
	private String alarmTime;
	private boolean longSitOnoff;
	private int longSitValue;
	private boolean birthdayOnoff;
	private String birthdayDate;
	private String birthdayTime;
	private boolean nowtimeOnoff;
	public RemindSetting(boolean incomingOnoff, boolean alarmOnoff, String alarmTime, boolean longSitOnoff, int longSitValue, boolean birthdayOnoff, String birthdayDate, String birthdayTime,
			boolean nowtimeOnoff) {
		super();
		this.incomingOnoff = incomingOnoff;
		this.alarmOnoff = alarmOnoff;
		this.alarmTime = alarmTime;
		this.longSitOnoff = longSitOnoff;
		this.longSitValue = longSitValue;
		this.birthdayOnoff = birthdayOnoff;
		this.birthdayDate = birthdayDate;
		this.birthdayTime = birthdayTime;
		this.nowtimeOnoff = nowtimeOnoff;
	}
	public RemindSetting() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "RemindSetting [incomingOnoff=" + incomingOnoff + ", alarmOnoff=" + alarmOnoff + ", alarmTime=" + alarmTime + ", longSitOnoff=" + longSitOnoff + ", longSitValue=" + longSitValue
				+ ", birthdayOnoff=" + birthdayOnoff + ", birthdayDate=" + birthdayDate + ", birthdayTime=" + birthdayTime + ", nowtimeOnoff=" + nowtimeOnoff + "]";
	}
	public boolean isIncomingOnoff() {
		return incomingOnoff;
	}
	public void setIncomingOnoff(boolean incomingOnoff) {
		this.incomingOnoff = incomingOnoff;
	}
	public boolean isAlarmOnoff() {
		return alarmOnoff;
	}
	public void setAlarmOnoff(boolean alarmOnoff) {
		this.alarmOnoff = alarmOnoff;
	}
	public String getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
	public boolean isLongSitOnoff() {
		return longSitOnoff;
	}
	public void setLongSitOnoff(boolean longSitOnoff) {
		this.longSitOnoff = longSitOnoff;
	}
	public int getLongSitValue() {
		return longSitValue;
	}
	public void setLongSitValue(int longSitValue) {
		this.longSitValue = longSitValue;
	}
	public boolean isBirthdayOnoff() {
		return birthdayOnoff;
	}
	public void setBirthdayOnoff(boolean birthdayOnoff) {
		this.birthdayOnoff = birthdayOnoff;
	}
	public String getBirthdayDate() {
		return birthdayDate;
	}
	public void setBirthdayDate(String birthdayDate) {
		this.birthdayDate = birthdayDate;
	}
	public String getBirthdayTime() {
		return birthdayTime;
	}
	public void setBirthdayTime(String birthdayTime) {
		this.birthdayTime = birthdayTime;
	}
	public boolean isNowtimeOnoff() {
		return nowtimeOnoff;
	}
	public void setNowtimeOnoff(boolean nowtimeOnoff) {
		this.nowtimeOnoff = nowtimeOnoff;
	}

}
