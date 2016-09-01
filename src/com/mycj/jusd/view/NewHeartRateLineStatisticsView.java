package com.mycj.jusd.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.mycj.jusd.util.DateUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class NewHeartRateLineStatisticsView extends ShowView {
	public NewHeartRateLineStatisticsView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public NewHeartRateLineStatisticsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NewHeartRateLineStatisticsView(Context context) {
		super(context);
		
		
	}


	
	@Override
	public void onInit() {
		super.onInit();
		Random random = new Random();
//		for (int i = 0; i < 30; i++) {
//			int value = random.nextInt(240);
//			datas.add((float)value);
//		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		topText = "心率 （次/分钟）";
		max = 240;
		rightText="";
		markerYs = new String[]{"60","120","180","240"};
	
		super.onDraw(canvas);
	}
	

	@Override
	protected void drawText(Canvas canvas){
		if (touchPosition !=-1) {
			String time = getTime(touchPosition);
			String info ="心率"+String.valueOf(showDatas[touchPosition])+"次/分钟";
			
			PointF pf1 = getPointF(touchPosition, showDatas[touchPosition]);
//			canvas.drawPoint(pf1.x, pf1.y, paintPoint);
			canvas.drawCircle(pf1.x, pf1.y, 8, paintPoint);
			
			//xuxian 
			xuxianPath.reset();
			xuxianPath.moveTo(pf1.x, pf1.y);
			xuxianPath.lineTo(pf1.x, rectStatistics.bottom);
			canvas.drawPath(xuxianPath, paintXuxian);
			canvas.drawLine(pf1.x, pf1.y, pf1.x, rectStatistics.bottom, paintXuxian);
			
			paintInfoTime.getTextBounds(time, 0, time.length(), infoTimeRect);
			paintInfo.getTextBounds(info, 0, info.length(), infoRect);
			canvas.drawText(info, pf1.x-infoTimeRect.width()/2, pf1.y - infoRect.height()-pointWidth, paintInfo);
			canvas.drawText(time, pf1.x-infoTimeRect.width()/2, pf1.y - infoTimeRect.height() - infoRect.height()- pointWidth-10, paintInfoTime);
		}
	}
	
}
