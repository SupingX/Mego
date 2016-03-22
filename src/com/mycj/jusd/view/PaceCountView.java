package com.mycj.jusd.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.database.CursorJoiner.Result;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.Path.Direction;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class PaceCountView extends View {

	private Paint paintXY;
	/** 开始结束时间 **/
	private int[] startTime, endTime;
	private int totalMinute;
	/** 左右上下的空间 **/
	private int spaceX, spaceY;
	/** 时间点的线段高度 **/
	private int shortLine = 10;
	private Paint paintText;
	private Rect rectText;
	private List<Float> datas = new ArrayList<Float>();
	private Path path;
	private Paint p;
	private float MAX = 100;
	private List<PointF> ps;
	private PointTypeEvaluator typeE;

	public PaceCountView(Context context) {
		super(context);
		init(context, null);
	}

	public PaceCountView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	public PaceCountView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	@Override
	protected void onDraw(final Canvas canvas) {
		super.onDraw(canvas);

		int width = getWidth();
		int height = getHeight();

		drawXY(canvas, width, height);

		drawTimePoint(canvas, width, height);

		// ValueAnimator va = ValueAnimator.ofObject(typeE,new PointF(),new
		// PointF());
		// va.addUpdateListener(new AnimatorUpdateListener() {
		// @Override
		// public void onAnimationUpdate(ValueAnimator animation) {
		// PointF px = (PointF) animation.getAnimatedValue();
		// canvas.drawPoint(px.x, px.y, p);
		// }
		// });
		// va.setDuration(10);
		// va.start();

	}

	private void drawXY(Canvas canvas, int width, int height) {
		int x_startX = spaceX;
		int x_startY = height - spaceY;
		int x_stopX = width - spaceX;
		int x_stopY = x_startY;
		canvas.drawLine(x_startX, x_startY, x_stopX, x_stopY, paintXY);
		int y_startX = spaceX;
		int y_startY = spaceY;
		int y_stopX = y_startX;
		int y_stopY = height - spaceY;
		canvas.drawLine(y_startX, y_startY, y_stopX, y_stopY, paintXY);
	}

	private void drawTimePoint(Canvas canvas, int width, int height) {
		if (startTime != null && endTime != null) {
			int startHour = startTime[0];
			int startMin = startTime[1];
			int endHour = endTime[0];
			int endMin = endTime[1];
			int total = getTotalMinute(startHour, startMin, endHour, endMin);
			Log.e("PaceCountView", "total : " + total);
			int spanX = (int) ((width - spaceX * 2) * 1.0f / total);

			for (int i = 0; i < total; i++) {
				datas.add(new Random().nextInt(100) * 1.0f);
				PointF pointF = new PointF();
				pointF.x = spaceX + spanX * (i + 1);
				pointF.y = getDataHeight(i);
				points.add(pointF);
			}

			for (int i = 0; i < total; i++) {
				// 每隔2个点画一个刻度
				if (i % 2 == 0) {
					int startX = spaceX + spanX * (i + 1);
					int startY = height - spaceY;
					int stopX = startX;
					int stopY = startY - shortLine;
					canvas.drawLine(startX, startY, stopX, stopY, paintXY);
				}
				// 虚线
//				Paint paint = new Paint();
//				paint.setStyle(Paint.Style.STROKE);
//				paint.setColor(Color.GREEN);
//				Path path = new Path();
//				path.moveTo(spaceX + spanX * (i + 1), getDataHeight(total));
//				path.lineTo(spaceX + spanX * (i + 1), height - spaceY);
//				PathEffect effects = new DashPathEffect(new float[] { 5, 5, 5, 5 }, 1);
//				paint.setPathEffect(effects);
//				canvas.drawPath(path, paint);

			}

			// 画曲线
//			CornerPathEffect e = new CornerPathEffect(20);
//			p.setPathEffect(e);
//			function_Catmull_Rom(points, 1000, save, path);
//			canvas.drawPath(path, p);
			
			Path ltPath = new Path();
			ltPath.moveTo(points.get(0).x, points.get(0).y);
			for (int i = 1; i < points.size(); i++) {
				ltPath.lineTo(points.get(i).x, points.get(i).y);
			}
			CornerPathEffect e = new CornerPathEffect(80);
//			DashPathEffect e1 = new DashPathEffect(new float[]{12,2},1f);
//			ComposePathEffect ee = new ComposePathEffect(e,e1);
			p.setPathEffect(e);
			canvas.drawPath(ltPath, p);

			String startTimeText = getTimeText(startHour, startMin);
			String endTimeText = getTimeText(endHour, endMin);
			paintText.getTextBounds(startTimeText, 0, startTimeText.length(), rectText);
			int start_x = spaceX - rectText.width() / 2;
			int start_y = height - rectText.height() / 2;
			canvas.drawText(startTimeText, start_x, start_y, paintText);

			paintText.getTextBounds(endTimeText, 0, endTimeText.length(), rectText);
			int end_x = (total - 1) * spanX + spaceX - rectText.width() / 2;
			int end_y = height - rectText.height() / 2;
			canvas.drawText(endTimeText, end_x, end_y, paintText);
		}

	}

	private void init(Context context, AttributeSet attrs) {
		if (attrs != null) {
		} else {

		}

		spaceX = 40;
		spaceY = 40;
		startTime = new int[] { 11, 1 };
		// endTime = new int[] { 11, 58 };
		endTime = new int[] { 13, 58 };

		paintXY = new Paint();
		paintXY.setAntiAlias(true);
		paintXY.setDither(true);
		paintXY.setStyle(Paint.Style.FILL);
		paintXY.setStrokeWidth(2);
		paintXY.setStrokeCap(Paint.Cap.ROUND);
		paintXY.setColor(Color.RED);

		paintText = new Paint();
		paintText.setAntiAlias(true);
		paintText.setDither(true);
		paintText.setStyle(Paint.Style.FILL);
		paintText.setStrokeWidth(2);
		paintText.setStrokeCap(Paint.Cap.ROUND);
		paintText.setColor(Color.WHITE);
		paintText.setTextSize(22.0f);

		p = new Paint();
		p.setAntiAlias(true);
		p.setDither(true);
		p.setColor(Color.YELLOW);
		p.setStrokeCap(Paint.Cap.ROUND);
		p.setStyle(Paint.Style.STROKE);
		path = new Path();

		rectText = new Rect();

		ps = new ArrayList<PointF>();
		ps.add(new PointF(100, 200));
		ps.add(new PointF(140, 300));
		ps.add(new PointF(160, 100));
		ps.add(new PointF(220, 400));
		typeE = new PointTypeEvaluator(ps);
	}

	private int getTotalMinute(int startHour, int startMin, int endHour, int endMin) {
		int total = 0;
		// 这里考虑到隔天的问题。
		if (startHour > endHour) {
			total = (23 - startHour) * 60 + startMin + endHour * 60 + endMin;
		} else if (startHour == endHour) {
			total = endMin - startMin;
		} else {
			total = (endHour - startHour) * 60 + 60 - startMin + endMin;
		}
		return total + 1;
	}

	private String getTimeText(int hour, int min) {
		StringBuffer sb = new StringBuffer();
		String hourStr = String.valueOf(hour);
		if (hourStr.length() == 1) {
			sb.append("0").append(hourStr);
		} else {
			sb.append(hourStr);
		}
		sb.append(":");
		String minStr = String.valueOf(min);
		if (minStr.length() == 1) {
			sb.append("0").append(minStr);
		} else {
			sb.append(minStr);
		}
		return sb.toString();

	}

	private float getDataHeight(int index) {
		if (datas.size() > index) {
			float data = datas.get(index);
			float result = (getHeight() - 2 * spaceY) * (1 - data * 1.0f / MAX) + spaceY;
			return result;
		}
		return 0;
	}

	private class PointTypeEvaluator implements TypeEvaluator<PointF> {
		private List<PointF> datas = new ArrayList<PointF>();

		public PointTypeEvaluator(List<PointF> datas) {
			super();
			this.datas = datas;
		}

		@Override
		public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
			PointF p = new PointF();
			final float t = fraction;
			p.x = (1 - t) * (1 - t) * (1 - t) * datas.get(0).x + 3 * datas.get(1).x * (1 - t) * (1 - t) * t + 3 * datas.get(2).x * (1 - t) * t * t + datas.get(3).x * t * t * t;
			p.y = (1 - t) * (1 - t) * (1 - t) * datas.get(0).y + 3 * datas.get(1).y * (1 - t) * (1 - t) * t + 3 * datas.get(2).y * (1 - t) * t * t + datas.get(3).y * t * t * t;

			return p;
		}

	}

	private ArrayList<PointF> points = new ArrayList<PointF>();
	private ArrayList<PointF> save = new ArrayList<PointF>();

	public void function_Catmull_Rom(ArrayList<PointF> point, int cha, ArrayList<PointF> save, Path path) {
		if (point.size() < 4) {
			return;
		}
		path.moveTo(point.get(0).x, point.get(0).y);
		save.add(point.get(0));

		for (int index = 0; index < point.size() - 3; index++) {
			PointF p0 = point.get(index );
			PointF p1 = point.get(index+1);
			PointF p2 = point.get(index + 2);
			PointF p3 = point.get(index + 3);
			for (int i = 1; i <= cha; i++) {
				float t = i * (1.0f / cha);
				float tt = t * t;
				float ttt = tt * t;
				PointF pi = new PointF(); // intermediate point
				pi.x = (float) (0.5 * (2 * p1.x + (p2.x - p0.x) * t + (2 * p0.x - 5 * p1.x + 4 * p2.x - p3.x) * tt + (3 * p1.x - p0.x - 3 * p2.x + p3.x) * ttt));
				pi.y = (float) (0.5 * (2 * p1.y + (p2.y - p0.y) * t + (2 * p0.y - 5 * p1.y + 4 * p2.y - p3.y) * tt + (3 * p1.y - p0.y - 3 * p2.y + p3.y) * ttt));
				path.lineTo(pi.x, pi.y);
				save.add(pi);
				pi = null;
			}
		}
		path.lineTo(point.get(point.size() - 1).x, point.get(point.size() - 1).y);
		save.add(point.get(point.size() - 1));
	}
}
