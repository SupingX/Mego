package com.mycj.jusd.bean;

import android.os.Parcel;

import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine.WalkingStep;

public class JsdWalkingRouteLine extends RouteLine<WalkingStep>{
	public JsdWalkingRouteLine() {
		super();
	}

	public JsdWalkingRouteLine(Parcel arg0) {
		super(arg0);
	}
}
