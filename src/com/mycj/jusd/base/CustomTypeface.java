package com.mycj.jusd.base;

/**
 * 字体 
 * @author zeej
 *
 */
public enum CustomTypeface {
	JIAN("fonts/microgme.ttf", "JIAN"), FAN("fonts/microgme.ttf", "FAN"), NUM(
			"fonts/microgme.ttf", "NUM");
	private String path;
	private String name;

	CustomTypeface(String path, String name) {
		this.path = path;
		this.name = name;
	}

	public String getPath() {
		return this.path;
	}

	public String getName() {
		return this.name;
	}
}
