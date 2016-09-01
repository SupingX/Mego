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
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class NewSpeedLineStatisticsView extends ShowView {

	public NewSpeedLineStatisticsView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public NewSpeedLineStatisticsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NewSpeedLineStatisticsView(Context context) {
		super(context);
	}


	


	@Override
	protected void onDraw(Canvas canvas) {
		topText = "速度 （公里/时）";
		max = 80;
		rightText="";
		markerYs = new String[]{"20","40","60","80"};
		
		super.onDraw(canvas);
	}

	
	@Override
	protected void drawText(Canvas canvas){
		if (touchPosition !=-1) {
			String time = getTime(touchPosition);
			String info ="速度"+String.valueOf(showDatas[touchPosition])+"公里/时";
			PointF pf1 = getPointF(touchPosition, showDatas[touchPosition]);
//			canvas.drawPoint(pf1.x, pf1.y, paintPoint);
//			canvas.drawLine(pf1.x, pf1.y, pf1.x, rectStatistics.bottom, paintPoint);
			canvas.drawCircle(pf1.x, pf1.y, 8, paintPoint);
			
			//xuxian 
			xuxianPath.reset();
			xuxianPath.moveTo(pf1.x, pf1.y);
			xuxianPath.lineTo(pf1.x, rectStatistics.bottom);
			canvas.drawPath(xuxianPath, paintXuxian);
			canvas.drawLine(pf1.x, pf1.y, pf1.x, rectStatistics.bottom, paintXuxian);
			
			
			paintInfoTime.getTextBounds(time, 0, time.length(), infoTimeRect);
			paintInfo.getTextBounds(time, 0, time.length(), infoRect);
			canvas.drawText(time, pf1.x+infoTimeRect.width()/2, pf1.y - infoTimeRect.height() - infoRect.height()- pointWidth-10, paintInfoTime);
			canvas.drawText(info, pf1.x+infoTimeRect.width()/2, pf1.y - infoRect.height()-pointWidth, paintInfo);
		}
	}
	
	
	
	
	

	

}
