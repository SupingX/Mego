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

public class PaceLineStatisticsView extends LineStatisticsView {
	private float[] datas;
	private Paint paintLine;

	private int max = 100;
	private Paint paintInfoTime;
	private String sportTime;

	public PaceLineStatisticsView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public PaceLineStatisticsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PaceLineStatisticsView(Context context) {
		super(context);
	}

	@Override
	public String[] getmarkerYs() {
		return new String[] { "20", "40", "60", "80" };
	}

	@Override
	public String[] getmarkerXs() {
		if (datas==null) {
			String[] ss = new String[12];
			for (int i = 0; i < 12; i++) {
				ss[i] = String.valueOf(i);
			}
			return ss;
		}else{
			String[] ss = new String[datas.length];
			for (int i = 0; i < datas.length; i++) {
				ss[i] = String.valueOf(i);
//				ss[i] = getTime(i);
			}
			return ss;
		}

	}
	
	public void setDatas(float[] datas ,String sportTime){
		this.sportTime = sportTime;
		this.datas = datas;
		invalidate();
	}
	private int pointWidth =10;
	@Override
	public void onInit() {
		setClickable(true);
		perMarkerX = 1;
		perMarkerY = 1;
		paintLine = new Paint();
		paintLine.setAntiAlias(true);
		paintLine.setDither(true);
		paintLine.setColor(DEEP);
		paintLine.setStrokeCap(Paint.Cap.ROUND);
		paintLine.setStyle(Paint.Style.STROKE);

		paintPoint = new Paint();
		paintPoint.setAntiAlias(true);
		paintPoint.setDither(true);
		paintPoint.setColor(Color.WHITE);
		paintPoint.setStrokeWidth(pointWidth);
		paintPoint.setStrokeCap(Paint.Cap.ROUND);
		paintPoint.setStyle(Paint.Style.FILL);
		
		paintInfo = new Paint();
		paintInfo.setTextSize(18);
		paintInfo.setAntiAlias(true);
		paintInfo.setDither(true);
		paintInfo.setColor(Color.WHITE);
		paintInfo.setStrokeCap(Paint.Cap.ROUND);
		paintInfo.setStyle(Paint.Style.FILL);
		paintInfoTime = new Paint();
		paintInfoTime.setAntiAlias(true);
		paintInfoTime.setTextSize(18);
		paintInfoTime.setDither(true);
		paintInfoTime.setColor(Color.WHITE);
		paintInfoTime.setStrokeCap(Paint.Cap.ROUND);
		paintInfoTime.setStyle(Paint.Style.FILL);
		
		infoTimeRect = new Rect();
		infoRect = new Rect();
			
		
		/*Random r = new Random();
		datas = new float[24];
		for (int i = 0; i < 24; i++) {
			datas[i] = r.nextInt(100);
		}*/
		ltPath = new Path();
	}

	private float preiousX = 0;
	private float preiousY = 0;
	private ArrayList<PointF> save = new ArrayList<PointF>();
	private ArrayList<PointF> points = new ArrayList<PointF>();
	private int touchPosition = -1;
	private Paint paintPoint;
	private Paint paintInfo;
	private Rect infoTimeRect;
	private Rect infoRect;
	private Path ltPath;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawPath1(canvas);
		drawText(canvas);
	}

	private void drawPath1(Canvas canvas) {
		if (datas == null) {
			return ;
		}
		PointF p = getPointF(0, datas[0]);
		ltPath.moveTo(p.x, p.y);
		CornerPathEffect e = new CornerPathEffect(20);
		paintLine.setPathEffect(e);
		for (int i = 1; i < datas.length; i++) {
			PointF pf1 = getPointF(i, datas[i]);
//			canvas.drawLine(pf1.x, pf1.y, pf1.x, rectStatistics.bottom,
//					paintLine);
	
			ltPath.lineTo(pf1.x, pf1.y);
		}
		canvas.drawPath(ltPath, paintLine);
	}
	
	private void drawText(Canvas canvas){
		if (touchPosition !=-1) {
			String time = getTime(touchPosition);
			String info ="配速"+String.valueOf(datas[touchPosition])+"次/公里 ";
			PointF pf1 = getPointF(touchPosition, datas[touchPosition]);
//			canvas.drawPoint(pf1.x, pf1.y, paintPoint);
//			canvas.drawLine(pf1.x, pf1.y, pf1.x, rectStatistics.bottom, paintPoint);
			canvas.drawCircle(pf1.x, pf1.y, 8, paintPoint);
			
			paintInfoTime.getTextBounds(time, 0, time.length(), infoTimeRect);
			paintInfo.getTextBounds(time, 0, time.length(), infoRect);
			canvas.drawText(time, pf1.x+infoTimeRect.width()/2, pf1.y - infoTimeRect.height() - infoRect.height()- pointWidth-10, paintInfoTime);
			canvas.drawText(info, pf1.x+infoTimeRect.width()/2, pf1.y - infoRect.height()-pointWidth, paintInfo);
		}
	}
	
	
	
	private String getTime(int i){
		if (sportTime==null ||sportTime.equals("")) {
			return "";
		}
		Date date = DateUtil.stringToDate(sportTime, "hhmmss");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND,i*5 );

		return DateUtil.dateToString(c.getTime(), "hh:mm:ss");
		
		
		/*String time = "";;
		if (i<10) {
			time = "0"+String.valueOf(i);
		}else{
			time = String.valueOf(i);
		}
		return time + ":00";*/
	}	
	
	private void drawPath(Canvas canvas) {
		Random r = new Random();
		Path ltPath = new Path();
		ArrayList<PointF> points = new ArrayList<PointF>();
		for (int i = 0; i < 24; i++) {
			points.add(getPointF(i, r.nextInt(100)));
		}
		function_Catmull_Rom(points, 1000, save, ltPath);
		canvas.drawPath(ltPath, paintLine);
	}
	
	

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			float downX = event.getX();
			float downY = event.getY();
			
			click(downX, downY);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			float moveX = event.getX();
			float moveY = event.getY();
			
			click(moveX, moveY);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			
			break;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	private PointF getPointF(int i, float value) {

		int x = rectStatistics.left + deffLeft + i * spanX;
		double rate = value * 1.0 / max;
		float y = (float) (rectStatistics.height() * (1 - rate) + rectStatistics.top);
		return new PointF(x, y);
	}

	public void function_Catmull_Rom(ArrayList<PointF> point, int cha,
			ArrayList<PointF> save, Path path) {
		if (point.size() < 4) {
			return;
		}
		path.moveTo(point.get(0).x, point.get(0).y);
		save.add(point.get(0));
		for (int index = 0; index < point.size(); index++) {
			PointF p0;
			PointF p2;
			PointF p3;
			PointF p1 = point.get(index);
			if (index - 1 < 0) {
				float x0 = point.get(0).x - spanX;
				float y0 = 0;
				p0 = new PointF(x0, y0);
			} else {
				p0 = point.get(index - 1);
			}

			if (index + 1 > point.size() - 1) {
				float x2 = point.get(point.size() - 1).x + spanX;
				float y2 = 0;
				p2 = new PointF(x2, y2);
			} else {
				p2 = point.get(index + 1);
			}

			if (index + 1 > point.size() - 2) {

				float x3 = point.get(point.size() - 1).x + 2 * spanX;
				float y3 = 0;
				p3 = new PointF(x3, y3);
			} else {
				p3 = point.get(index + 2);
			}

			for (int i = 1; i <= cha; i++) {
				float t = i * (1.0f / cha);
				float tt = t * t;
				float ttt = tt * t;
				PointF pi = new PointF(); // intermediate point
				pi.x = (float) (0.5 * (2 * p1.x + (p2.x - p0.x) * t
						+ (2 * p0.x - 5 * p1.x + 4 * p2.x - p3.x) * tt + (3
						* p1.x - p0.x - 3 * p2.x + p3.x)
						* ttt));
				pi.y = (float) (0.5 * (2 * p1.y + (p2.y - p0.y) * t
						+ (2 * p0.y - 5 * p1.y + 4 * p2.y - p3.y) * tt + (3
						* p1.y - p0.y - 3 * p2.y + p3.y)
						* ttt));
				path.lineTo(pi.x, pi.y);
				save.add(pi);
				pi = null;
			}
		}
		path.lineTo(point.get(point.size() - 1).x,
				point.get(point.size() - 1).y);
		save.add(point.get(point.size() - 1));
	}
	
	
	private void click(float downX, float downY){
		Log.e(TAG, "down :" +downX+","+downY );
		if (datas!= null && datas.length>0) {
			for (int i = 0; i < datas.length; i++) {
				left = rectStatistics.left + deffLeft + (i * spanX);
				right = left + spanX;
				if (downX >=left && downX <= right 
	//					&& downY <=bottom && downY >= top
						) {
					touchPosition = i;
				}
			}
		}
	}
	
	
	
		
}
