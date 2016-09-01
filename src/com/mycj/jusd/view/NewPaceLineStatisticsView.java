package com.mycj.jusd.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine.WalkingStep;
import com.laputa.blue.util.XLog;
import com.mycj.jusd.bean.JsdWalkingRouteLine;
import com.mycj.jusd.bean.JsdWalkingRouteOverlay;
import com.mycj.jusd.bean.LitePalManager;
import com.mycj.jusd.bean.news.SportHistory;
import com.mycj.jusd.util.DateUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class NewPaceLineStatisticsView extends ShowView {
	

	public NewPaceLineStatisticsView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public NewPaceLineStatisticsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NewPaceLineStatisticsView(Context context) {
		super(context);
	}


	


	@Override
	protected void onDraw(Canvas canvas) {
		topText = "配速 （分钟/公里）";
		max = 100;
		rightText="";
		markerYs = new String[]{"25","50","75","100"};
		super.onDraw(canvas);
	}

	
	@Override
	protected void drawText(Canvas canvas){
		if (touchPosition !=-1) {
			String time = getTime(touchPosition);
			String info ="配速"+String.valueOf(showDatas[touchPosition])+"分钟/公里 ";
			PointF pf1 = getPointF(touchPosition, showDatas[touchPosition]);
//			canvas.drawPoint(pf1.x, pf1.y, paintPoint);
//			canvas.drawLine(pf1.x, pf1.y, pf1.x, rectStatistics.bottom, paintPoint);
			canvas.drawCircle(pf1.x, pf1.y, 8, paintPoint);
			paintInfoTime.getTextBounds(time, 0, time.length(), infoTimeRect);
			paintInfo.getTextBounds(time, 0, time.length(), infoRect);
			canvas.drawText(time, pf1.x+infoTimeRect.width() / 2, pf1.y - infoTimeRect.height() - infoRect.height()- pointWidth-10, paintInfoTime);
			canvas.drawText(info, pf1.x+infoTimeRect.width() / 2, pf1.y - infoRect.height()-pointWidth, paintInfo);
		}
	}
	
	
	
	
	
	
		
}
