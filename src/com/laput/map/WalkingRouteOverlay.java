package com.laput.map;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine.WalkingStep;

/**
 * 用于显示步行路线的overlay，自3.4.0版本起可实例化多个添加在地图中显示
 * @author Administrator
 *
 */
public class WalkingRouteOverlay extends OverlayManager{
	private WalkingRouteLine mRouteLine = null;
	public WalkingRouteOverlay (BaiduMap map){
		super(map);
	}
	
	
	
	public void setData(WalkingRouteLine line){
		this.mRouteLine = line;
	}
	@Override
	public boolean onMarkerClick(Marker arg0) {
		return false;
	}
	@Override
	public boolean onPolylineClick(Polyline arg0) {
		return false;
	}
	@Override
	public List<OverlayOptions> getOverlayOptions() {
		if (mRouteLine == null) {
			return null;
		}
		List<OverlayOptions> overlayOptionsList = new ArrayList<OverlayOptions>();
		
		//通过传入参数mRouteLine获取所有步数信息allStep
		List<WalkingStep> allStep = mRouteLine.getAllStep();
		
		//画指示箭头？
		if (allStep != null && allStep.size()>0) {
			for (WalkingStep step : allStep) {
				Bundle b = new Bundle();
				b.putInt("index", allStep.indexOf(step));
				
				if (step.getEntrance() != null ) {
					overlayOptionsList.add(
							new MarkerOptions()
							.position(step.getEntrance().getLocation())
							.rotate((360-step.getDirection()))
							.zIndex(10)
							.anchor(0.5f, 0.5f)
							.extraInfo(b)
							.icon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_line_node.png"))
//							.icon(BitmapDescriptorFactory.fromResource(R.drawable.popup_down))
					);
				}
				//最后路段绘制出口点
				if (allStep.indexOf(step) == allStep.size()-1 && step.getExit() !=null) {
					overlayOptionsList.add(
							new MarkerOptions()
							.position(step.getExit().getLocation())
							.anchor(0.5f, 0.5f)
							.zIndex(10)
							.icon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_line_node.png"))
					);
				}
			}
			
		}
		
		//starting
		if (mRouteLine.getStarting() != null) {
			overlayOptionsList.add(
					new MarkerOptions()
					.position(mRouteLine.getStarting().getLocation())
					.zIndex(10)
					.icon(getStartMarker() !=null ?getStartMarker() : BitmapDescriptorFactory
                            .fromAssetWithDpi("Icon_start.png"))
			);
		}
		
		//terminal
		if (mRouteLine.getTerminal() != null) {
			overlayOptionsList.add(
					new MarkerOptions()
					.position(mRouteLine.getTerminal().getLocation())
					.zIndex(10)
					.icon(getTerminalMarker() !=null ? getTerminalMarker() : BitmapDescriptorFactory
                                            .fromAssetWithDpi("Icon_end.png"))
			);
		}
		
		// polu line list
		if (allStep !=null && allStep.size() > 0 ) {
			LatLng lastStepLastPoint = null;
			for (WalkingStep step : allStep) {
				List<LatLng> wayPoints = step.getWayPoints();
				if (wayPoints != null) {
					List<LatLng> points = new ArrayList<LatLng>();
					if (lastStepLastPoint !=null) {
						points.add(lastStepLastPoint);
					}
					points.addAll(wayPoints);
					overlayOptionsList.add(
							new PolylineOptions().points(points)
							.width(10)
							.color(getLineColor() !=0 ? getLineColor() : Color.argb(178, 255, 0, 0))
							.zIndex(0)
							
					);
					lastStepLastPoint = wayPoints.get(wayPoints.size() - 1);
				}
			}
		}
		
		return overlayOptionsList;
	}
	
	
	  /**
     * 覆写此方法以改变默认起点图标
     * 
     * @return 起点图标
     */
    public BitmapDescriptor getStartMarker() {
        return null;
    }
    
    public int getLineColor() {
        return 0;
    }
    
    /**
     * 覆写此方法以改变默认终点图标
     * 
     * @return 终点图标
     */
    public BitmapDescriptor getTerminalMarker() {
        return null;
    }
}
