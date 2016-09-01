package com.mycj.jusd.bean;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine.WalkingStep;
import com.laput.map.OverlayManager;
import com.mycj.jusd.R;

/**
 * 用于显示步行路线的overlay，自3.4.0版本起可实例化多个添加在地图中显示
 * 
 * @author Administrator
 *
 */
public class JsdWalkingRouteOverlay extends OverlayManager {
	private JsdWalkingRouteLine mRouteLine = null;

	public JsdWalkingRouteOverlay(BaiduMap map) {
		super(map);
	}

	public void setData(JsdWalkingRouteLine line) {
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

		// 通过传入参数mRouteLine获取所有步数信息allStep
		List<WalkingStep> allStep = mRouteLine.getAllStep();

		// 画指示箭头？
		if (allStep != null && allStep.size() > 0) {
			for (WalkingStep step : allStep) {
				Bundle b = new Bundle();
				b.putInt("index", allStep.indexOf(step));

				if (step.getEntrance() != null) {
					overlayOptionsList.add(new MarkerOptions()
							.position(step.getEntrance().getLocation())
							.rotate((360 - step.getDirection()))
							.zIndex(10)
							.anchor(0.5f, 0.5f)
							.extraInfo(b)
							.icon(BitmapDescriptorFactory
									.fromAssetWithDpi("Icon_line_node.png"))
					// .icon(BitmapDescriptorFactory.fromResource(R.drawable.popup_down))
							);
				}
				// 最后路段绘制出口点
				if (allStep.indexOf(step) == allStep.size() - 1
						&& step.getExit() != null) {
					overlayOptionsList.add(new MarkerOptions()
							.position(step.getExit().getLocation())
							.anchor(0.5f, 0.5f)
							.zIndex(10)
							.icon(BitmapDescriptorFactory
									.fromAssetWithDpi("Icon_line_node.png")));
				}
			}

		}

		// starting
		if (mRouteLine.getStarting() != null) {
			overlayOptionsList.add(new MarkerOptions()
					.position(mRouteLine.getStarting().getLocation())
					.zIndex(10)
					.icon(getStartMarker() != null ? getStartMarker()
							: BitmapDescriptorFactory
									.fromAssetWithDpi("Icon_start.png")));
		}

		// terminal
		if (mRouteLine.getTerminal() != null) {
			overlayOptionsList.add(new MarkerOptions()
					.position(mRouteLine.getTerminal().getLocation())
					.zIndex(10)
					.icon(getTerminalMarker() != null ? getTerminalMarker()
							: BitmapDescriptorFactory
									.fromAssetWithDpi("Icon_end.png")));
		}

		// polu line list
		if (allStep != null && allStep.size() > 0) {
			LatLng lastStepLastPoint = null;
			for (WalkingStep step : allStep) {

				List<LatLng> wayPoints = step.getWayPoints();
				Log.e("zeej", " --wayPoints  ：" + wayPoints);
				if (wayPoints != null) {

					List<LatLng> points = new ArrayList<LatLng>();

					if (lastStepLastPoint != null) {
						points.add(lastStepLastPoint);
					}
					points.addAll(wayPoints);
					overlayOptionsList.add(new PolylineOptions()
							.points(points)
							.width(10)
							.color(getLineColor() != 0 ? getLineColor() : Color
									.argb(178, 255, 0, 0)).zIndex(0)

					);

					lastStepLastPoint = wayPoints.get(wayPoints.size() - 1);
				}
			}

			if (allStep != null && allStep.size() > 0) {
				for (WalkingStep step : allStep) {
					List<LatLng> wayPoints = step.getWayPoints();
					if (wayPoints.size() > 0
							&& allStep.indexOf(step) != allStep.size() - 1) {
						// 添加marker
						// BitmapDescriptor bdA = BitmapDescriptorFactory
						// .fromResource(R.drawable.ic_sign);
						// MarkerOptions ooA = new
						// MarkerOptions().position(wayPoints.get(wayPoints.size()-1)).icon(bdA)
						// .zIndex(9).draggable(true);
						// overlayOptionsList.add(ooA);
						// 添加圆
						LatLng llCircle = wayPoints.get(wayPoints.size() - 1);
						OverlayOptions ooCircle = new CircleOptions()
								.fillColor(0xAAFF0000).center(llCircle)
								.stroke(new Stroke(5, 0xAAFF0000)).radius(10);
						overlayOptionsList.add(ooCircle);

						/*
						 * LatLng llDot = new LatLng(39.98923, 116.397428);
						 * OverlayOptions ooDot = new
						 * DotOptions().center(llDot).radius(6)
						 * .color(0xFFFF00FF);
						 * overlayOptionsList.addOverlay(ooDot);
						 */
						// 添加文字
						OverlayOptions ooText = new TextOptions()
								.bgColor(0x00000000).fontSize(24)
								.fontColor(0xFFFFFFFF)
								.text(String.valueOf(allStep.indexOf(step)+1))
								.rotate(0)
								.position(wayPoints.get(wayPoints.size() - 1));
						overlayOptionsList.add(ooText);
					}
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
		return Color.GREEN;
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
