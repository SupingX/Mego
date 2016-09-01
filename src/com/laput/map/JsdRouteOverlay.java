package com.laput.map;

import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;

public class JsdRouteOverlay extends OverlayManager{
	/** 线路 **/
	private JsdRouteLine line;
	public JsdRouteOverlay(BaiduMap baiduMap) {
		super(baiduMap);
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		return false;
	}

	@Override
	public boolean onPolylineClick(Polyline line) {
		return false;
	}

	@Override
	public List<OverlayOptions> getOverlayOptions() {
		return null;
	} 
	
	
	/**------------------------------------**/
	public void setData(JsdRouteLine line){
		this.line = line;
	}
	
	public JsdRouteLine getLine(){
		return this.line;
	}
	
	
}
