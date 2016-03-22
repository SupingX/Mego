package com.mycj.jusd.bean;

public class WatchSetting {
	private int unit;
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
	
	
	
	public WatchSetting() {
		super();
	}
	
	
	public WatchSetting(int unit, int height, int weight, int age, int sex, int walkLength, int runLength, int hrOnoff, int hrMin, int hrMax, int sleepOnoff, String sleepTime, String awakTime) {
		super();
		this.unit = unit;
		this.height = height;
		this.weight = weight;
		this.age = age;
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


	@Override
	public String toString() {
		return "WatchSetting [unit=" + unit + ", height=" + height + ", weight=" + weight + ", age=" + age + ", sex=" + sex + ", walkLength=" + walkLength + ", runLength=" + runLength + ", hrOnoff="
				+ hrOnoff + ", hrMin=" + hrMin + ", hrMax=" + hrMax + ", sleepOnoff=" + sleepOnoff + ", sleepTime=" + sleepTime + ", awakTime=" + awakTime + "]";
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
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
	
}
