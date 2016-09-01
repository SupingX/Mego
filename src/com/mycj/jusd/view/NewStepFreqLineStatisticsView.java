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

public class NewStepFreqLineStatisticsView extends ShowView {

	private Paint paintInfoDistance;
	private Rect infoDistanceRect;


	public NewStepFreqLineStatisticsView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public NewStepFreqLineStatisticsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NewStepFreqLineStatisticsView(Context context) {
		super(context);
	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		super.onInit();
		
		paintInfoDistance = new Paint();
		paintInfoDistance.setAntiAlias(true);
		paintInfoDistance.setTextSize(18);
		paintInfoDistance.setDither(true);
		paintInfoDistance.setColor(Color.WHITE);
		paintInfoDistance.setStrokeCap(Paint.Cap.ROUND);
		paintInfoDistance.setStyle(Paint.Style.FILL);
		
		infoDistanceRect = new Rect();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		topText = "步频 （步/分钟）";
		max = 400;
		rightText="";
		markerYs = new String[]{"100","200","300","400"};
		super.onDraw(canvas);
	}

	
	@Override
	protected void drawText(Canvas canvas){
		if (touchPosition !=-1) {
			String time = getTime(touchPosition);
			String info ="步数"+String.valueOf(showDatas[touchPosition]);
			String infoDistance ="步长"+String.valueOf(showDatas[touchPosition] *18) +"M";
			
			PointF pf1 = getPointF(touchPosition, showDatas[touchPosition]);
//			canvas.drawPoint(pf1.x, pf1.y, paintPoint);
			canvas.drawCircle(pf1.x, pf1.y, 8, paintPoint);
			
			paintInfoTime.getTextBounds(time, 0, time.length(), infoTimeRect);
			paintInfo.getTextBounds(info, 0, info.length(), infoRect);
			paintInfoDistance.getTextBounds(infoDistance, 0, infoDistance.length(), infoDistanceRect);
			canvas.drawText(infoDistance, pf1.x-infoTimeRect.width()/2, pf1.y - infoDistanceRect.height()-pointWidth, paintInfo);
			canvas.drawText(info, pf1.x-infoTimeRect.width()/2, pf1.y - infoRect.height()-pointWidth-infoDistanceRect.height(), paintInfo);
			canvas.drawText(time, pf1.x-infoTimeRect.width()/2, pf1.y - infoTimeRect.height() - infoRect.height()- pointWidth-infoDistanceRect.height()-10, paintInfoTime);
		}
	}
	
	
}
