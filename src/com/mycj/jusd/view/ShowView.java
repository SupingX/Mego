package com.mycj.jusd.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.laputa.blue.util.XLog;
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
import android.view.MotionEvent;

public class ShowView extends LineStatisticsView {

	protected List<Float> datas;
	protected float showDatas[] = new float[10];
	protected int deff = 0;
	protected int max = 8;
	protected Paint paintLine;
	protected Paint paintPoint;
	protected int pointWidth = 10;
	protected Paint paintInfo;
	protected Paint paintInfoTime;
	protected Paint paintXuxian;
	protected Path xuxianPath;
	protected Rect infoTimeRect;
	protected Rect infoRect;
	protected Path ltPath;
	protected Runnable run;
	protected String[] text = new String[] { "1", "2", "3", "4", "5", "6", "7",
			"8", "9", "10" };

	public ShowView(Context context) {
		super(context);
	}

	public ShowView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ShowView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public String[] getmarkerYs() {
		return this.markerYs;
	}

	@Override
	public String[] getmarkerXs() {
		return text;
	}

	@Override
	public void onInit() {
		datas = new ArrayList<Float>();

		setClickable(true);
		perMarkerX = 1;
		perMarkerY = 1;
		topText = "心率 （次/分钟）";
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

		paintXuxian = new Paint();
		paintXuxian.setAntiAlias(true);
		paintXuxian.setDither(true);
		paintXuxian.setColor(Color.WHITE);
		paintXuxian.setStrokeCap(Paint.Cap.ROUND);
		paintXuxian.setStyle(Paint.Style.STROKE);
		PathEffect effects = new DashPathEffect(new float[] { 5, 5, 5, 5 }, 1);
		paintXuxian.setPathEffect(effects);
		xuxianPath = new Path();

		infoTimeRect = new Rect();
		infoRect = new Rect();
		ltPath = new Path();

		// changDeff();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		for (int i = 0; i < text.length; i++) {
			text[i] = String.valueOf(deff + i + 1);
		}
		super.onDraw(canvas);
		drawPath1(canvas);
		drawText(canvas);
	}

	private void drawPath1(Canvas canvas) {
		if (datas == null) {
			return;
		}
		ltPath.reset();
		updateShowDatasByDeff();
		PointF p = getPointF(0, showDatas[0]);

		ltPath.moveTo(p.x, p.y);
		CornerPathEffect e = new CornerPathEffect(20);
		paintLine.setPathEffect(e);
		for (int i = 1; i < showDatas.length; i++) {
			PointF pf1 = getPointF(i, showDatas[i]);
			// canvas.drawLine(pf1.x, pf1.y, pf1.x, rectStatistics.bottom,
			// paintLine);

			ltPath.lineTo(pf1.x, pf1.y);
		}
		canvas.drawPath(ltPath, paintLine);
	}

	protected int touchPosition = 5;

	protected void drawText(Canvas canvas) {
		if (touchPosition != -1) {
			String time = getTime(touchPosition);
			String info = "心率" + String.valueOf(showDatas[touchPosition])
					+ "次/分钟";

			PointF pf1 = getPointF(touchPosition, showDatas[touchPosition]);
			// canvas.drawPoint(pf1.x, pf1.y, paintPoint);
			canvas.drawCircle(pf1.x, pf1.y, 8, paintPoint);

			// xuxian
			xuxianPath.reset();
			xuxianPath.moveTo(pf1.x, pf1.y);
			xuxianPath.lineTo(pf1.x, rectStatistics.bottom);
			canvas.drawPath(xuxianPath, paintXuxian);
			canvas.drawLine(pf1.x, pf1.y, pf1.x, rectStatistics.bottom,
					paintXuxian);

			paintInfoTime.getTextBounds(time, 0, time.length(), infoTimeRect);
			paintInfo.getTextBounds(info, 0, info.length(), infoRect);
			canvas.drawText(info, pf1.x - infoTimeRect.width() / 2, pf1.y
					- infoRect.height() - pointWidth, paintInfo);
			canvas.drawText(time, pf1.x - infoTimeRect.width() / 2, pf1.y
					- infoTimeRect.height() - infoRect.height() - pointWidth
					- 10, paintInfoTime);
		}
	}

	protected PointF getPointF(int i, float value) {

		int x = rectStatistics.left + deffLeft + i * spanX;
		double rate = value * 1.0 / max;
		float y = (float) (rectStatistics.height() * (1 - rate) + rectStatistics.top);
		return new PointF(x, y);
	}

	private void updateShowDatasByDeff() {
		for (int i = 0; i < showDatas.length; i++) {
			if (datas.size() >= 10 && deff < datas.size() - 10) {
				showDatas[i] = datas.get(i + deff);
			} else {
				if (i < datas.size()) {
					showDatas[i] = datas.get(i);
				}
			}
		}

	}

	public void changDeff() {
		run = new Runnable() {

			@Override
			public void run() {
				if (deff < datas.size() - showDatas.length) {
					deff++;

					postInvalidate();
					postDelayed(run, 1000);
				}
			}
		};
		postDelayed(run, 2000);
	}

	protected String sportTime = "19910815";

	protected String getTime(int i) {
		if (sportTime == null || sportTime.equals("")) {
			return "";
		}
		Date date = DateUtil.stringToDate(sportTime, "hhmmss");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, i * 5);

		return DateUtil.dateToString(c.getTime(), "hh:mm:ss");

		/*
		 * String time = "";; if (i<10) { time = "0"+String.valueOf(i); }else{
		 * time = String.valueOf(i); } return time + ":00";
		 */
	}

	private float lastX;
	private boolean isTouchPo = false;// 是否是按虚线
	private final int RADIUS_TOUCH_POINT = 50;
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			float xDown = event.getX();
			float xTouch = deffLeft + paddingLeft + spanX * (touchPosition);
			if (xDown < xTouch + RADIUS_TOUCH_POINT && xDown > xTouch - RADIUS_TOUCH_POINT) {
				isTouchPo = true;
				paintPoint.setColor(Color.parseColor("#FFEAAB44"));
				invalidate();
			} else {
				isTouchPo = false;
				paintPoint.setColor(Color.WHITE);
				invalidate();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			float x = event.getX();
			if (isTouchPo) {
				if (x - lastX < 0 && lastX - x > spanX) {
					touchPosition--;
					if (touchPosition < 0) {
						touchPosition = 0;
					}
					XLog.e(" <==== <===== <===== <====左移动  " + touchPosition);
					lastX = x;
					invalidate();
				}
				if (x - lastX > 0 && x - lastX > spanX) {
					touchPosition++;

					if (touchPosition > 9) {
						touchPosition = 9;
					}
					XLog.e("右移动  ====> =====> =====> ====>" + touchPosition);
					lastX = x;
					invalidate();
				}
			} else {
				if (datas.size()<=10) {
					return false;
				}
				if (x - lastX < 0 && lastX - x > spanX) {

					deff++;
					if (deff > datas.size() - showDatas.length) {
						setDeff(datas.size() - showDatas.length);
						// deff = datas.size()-showDatas.length;
					}
					if (deff < 0) {
						return false;
					}
					invalidate();
					lastX = x;
					return false;
				}

				if (x - lastX > 0 && x - lastX > spanX) {
					deff--;
					if (deff < 0) {
						setDeff(0);
						// deff=0;
					}
					invalidate();
					lastX = x;
					return false;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			isTouchPo = false;
			paintPoint.setColor(Color.WHITE);
			invalidate();
			lastX = event.getX();
			break;

		default:
			break;
		}

		return super.onTouchEvent(event);
	}

	public synchronized void setDeff(int deff) {
		this.deff = deff;
	}

	public void setData(List<Float> data, String time) {
		this.datas = data;
		this.sportTime = time;
		invalidate();
	}

	public void setMax(int max) {
		this.max = max;
	}

	public void setTextY(String[] text) {
		this.markerYs = text;
	}

	public int getMax() {
		return this.max;
	}

}
