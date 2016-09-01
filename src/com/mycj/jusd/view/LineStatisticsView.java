package com.mycj.jusd.view;

import com.mycj.jusd.R;
import com.mycj.jusd.util.DisplayUtil;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public abstract class LineStatisticsView extends View {
	protected final static String TAG = LineStatisticsView.class
			.getSimpleName();// dp
	protected final static int DEFAULT_TEXT_SIZE = 16;// dp
	protected final static int DEFAULT_SPACE = 20;// dp
	protected int DEEP = Color.RED;
	protected int LIGHT = Color.GREEN;

	/** 坐标轴的paint **/
	protected Paint paintXY;
	/** 坐标轴的宽度 **/
	protected int widthXY = 4;
	/** 坐标轴的颜色 **/
	protected int colorXY = Color.BLACK;

	/** 坐标文字的paint **/
	protected Paint paintTextX;
	/** 坐标文字的paint **/
	protected Paint paintTextY;

	/** x轴文字大小 **/
	protected int textSizeX = DEFAULT_TEXT_SIZE;
	/** x轴坐标文字 按下标从左到右 **/
	protected String[] markerXs = new String[] { "1", "2", "3", "4", "5", "6","7","8","9","10" };
	/** x轴坐标文字 每个坐标间的个数 **/
	protected int perMarkerX = 1;

	/** y轴文字大小 **/
	protected int textSizeY = DEFAULT_TEXT_SIZE;
	/** y轴坐标文字 按下标从下到上 **/
	protected String[] markerYs = new String[] { "2", "4", "6", "8" };
	/** x轴坐标文字 每个坐标间的个数 **/
	protected int perMarkerY = 1;

	/** 坐标左侧空余空间距离 单位dp **/
	protected int spaceLeft = 0;
	/** 坐标下侧空余空间距离 单位dp **/
	protected int spaceBottom = 0;
	/** 坐标左侧空余空间距离 单位dp **/
	protected int spaceTop = 0;
	/** 坐标下侧空余空间距离 单位dp **/
	protected int spaceRight = 0;

	/** Y轴标注 **/
	protected String topText = "速度（公里/时）";
	protected boolean beginXAt0 = true;
	protected boolean beginYAt0 = true;

	/** X轴标注 **/
	protected String rightText = "时刻";

	/** 刻度的长度 像素 **/
	protected int markerWidth = 10;

	/** Y轴上面的空余 dp **/
	protected int deffTop = 20;
	/** X轴右面的空余 dp **/
	protected int deffRight = 20;
	/** X轴左面的空余 dp **/
	protected int deffLeft = 20;
	/** Y轴下面的空余 dp **/
	protected int deffBottom = 20;

//	// x轴
//	protected float[] datas = new float[] { 1.0f, 2.0f, 3.0f, 2.0f, 3.0f, 2.0f,
//			3.0f, 2.0f, 3.0f };

	// 整个统计图的rect
	protected Rect rectStatistics = new Rect();
	protected Rect rectLeftText;
	protected Rect rectBottomText;
	protected Rect rectRightText;
	protected Rect rectTopText;
	protected int offsetLeftTextX = 0;
	protected int offsetLeftTextY = 0;
	protected int offsetRightTextX = 0;
	protected int offsetRightTextY = 0;
	protected int offsetBottomTextX = 0;
	protected int offsetBottomTextY = 0;
	protected int offsetTopTextX = 0;
	protected int offsetTopTextY = 0;

	protected int paddingLeft;
	protected int paddingRight;
	protected int paddingTop;
	protected int paddingBottom;
	protected int width;
	protected int height;
	protected int top;
	protected int right;
	protected int left;
	protected int bottom;
	protected int spanX;

	public LineStatisticsView(Context context) {
		super(context);
		init(context, null);
	}

	public LineStatisticsView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	public LineStatisticsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
		int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);

		int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
		int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
		if (modeHeight == MeasureSpec.AT_MOST) {
			Log.e("modeHeight", "==AT_MOST");
		} else if (modeHeight == MeasureSpec.EXACTLY) {
			Log.e("modeHeight", "==EXACTLY" + DisplayUtil.px2dip(getContext(), sizeHeight));
			setMeasuredDimension(sizeWidth,
					 sizeHeight-2*getPaddingBottom()-2*getPaddingTop());
			return;
		}
		else {
			Log.e("modeHeight", "==NOT_AT_MOST");
		}
		
		
		if (modeWidth == MeasureSpec.AT_MOST) {
			Log.e("modeWidth", "==AT_MOST");
		} else {
			Log.e("modeWidth", "==NOT_AT_MOST");
		}

//		setMeasuredDimension(sizeWidth,
//				Math.min(DisplayUtil.dip2px(getContext(), 240), sizeHeight));
		 super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// canvas.drawColor(Color.GRAY);

		markerXs = getmarkerXs()==null ? this.markerXs : getmarkerXs();
		markerYs = getmarkerYs()==null ? this.markerYs : getmarkerYs();
		
		
		paintTextX.getTextBounds(topText, 0, topText.length(), rectTopText);
		paintTextY.getTextBounds(rightText, 0, rightText.length(),
				rectRightText);
		paintTextX.getTextBounds(markerYs[0], 0, markerYs[0].length(),
				rectLeftText);
		paintTextY.getTextBounds(markerXs[0], 0, markerXs[0].length(),
				rectBottomText);
		paddingLeft = getPaddingLeft();
		paddingRight = getPaddingRight();
		paddingTop = getPaddingTop();
		paddingBottom = getPaddingBottom();
		width = getWidth();
		height = getHeight();

		// 获取数据的显示区域
		rectStatistics.left = paddingLeft
				+ Math.max(spaceLeft, rectLeftText.width()) + widthXY / 2;
		rectStatistics.right = width - paddingRight
				- Math.max(spaceRight, rectRightText.width());
		rectStatistics.top = paddingTop
				+ Math.max(spaceTop, rectTopText.height());
		rectStatistics.bottom = height - paddingBottom
				- Math.max(spaceBottom, rectBottomText.height()) - widthXY / 2;
		drawY(canvas);
		drawX(canvas);
		drawTopText(canvas);
		drawRightText(canvas);
		drawUnitYAndMarker(canvas, perMarkerY);
		drawUnitXAndMarker(canvas, perMarkerX);

	}

	protected void drawTopText(Canvas canvas) {
		int center = rectStatistics.left;
//		int x = center - rectTopText.width() / 2 + offsetLeftTextX;
		int x =  offsetLeftTextX ;
		int y = getPaddingTop() + rectTopText.height() + offsetLeftTextY;
		canvas.drawText(topText, x, y, paintTextX);
	}

	protected void drawRightText(Canvas canvas) {
		int center = rectStatistics.bottom;
		int x = rectStatistics.right + rectRightText.width() / 2
				+ offsetRightTextX;
		int y = center + rectRightText.height() / 2 + offsetRightTextY;
		canvas.drawText(rightText, x, y, paintTextY);
	}

	protected void drawUnitYAndMarker(Canvas canvas, int perMarkerSize) {
		int statisticsHeight = rectStatistics.height();
		// 每段之间的间隔 = 每段之间的刻度数量 * 坐标数量
		float span = ((statisticsHeight - deffTop - deffBottom) * 1.0f
				/ (markerYs.length * perMarkerSize) + 0.5f);

		int spanY = Math.round(span);
		for (int i = 0; i < markerYs.length * perMarkerSize; i++) {

			// 画坐标　每隔perMarkerSize画一个坐标
			if (i % perMarkerSize == 0) {
				Rect rect = new Rect();
				String text = markerYs[i / perMarkerSize];
				paintTextX.getTextBounds(text, 0, text.length(), rect);
				int center = rectStatistics.left
						- Math.max(spaceLeft, rectLeftText.width()) / 2;
				int x = center - rect.width() / 2;
				int y = rectStatistics.bottom - spanY * (i + perMarkerSize)
						+ rect.height() / 2 - deffBottom;
				canvas.drawText(text, x, y, paintTextX);
			}

			// 画刻度
			int startX = rectStatistics.left;
			int startY = rectStatistics.bottom - spanY * (i + 1) - deffBottom;
			int stopX = startX + markerWidth;
			int stopY = startY;

			canvas.drawLine(startX, startY, stopX, stopY, paintXY);
		}
		// 画0刻度
		int startX = rectStatistics.left;
		int startY = rectStatistics.bottom - deffBottom;
		int stopX = startX + markerWidth;
		int stopY = startY;
		canvas.drawLine(startX, startY, stopX, stopY, paintXY);

	}

	protected void drawUnitXAndMarker(Canvas canvas, int perMarkerSize) {
		int statisticsWidth = rectStatistics.width();
		if (!beginXAt0) {
			// 每段之间的间隔 = 每段之间的刻度数量 * 坐标数量
			float span = ((statisticsWidth - deffRight - deffLeft) * 1.0f
					/ (markerXs.length * perMarkerSize) + 0.5f);
			spanX = Math.round(span);
			for (int i = 0; i < markerXs.length * perMarkerSize; i++) {
				// 画坐标　每隔perMarkerSize画一个坐标
				if (i % perMarkerSize == 0) {
					Rect rect = new Rect();
					String text = markerXs[i / perMarkerSize];
					paintTextX.getTextBounds(text, 0, text.length(), rect);
					int x = rectStatistics.left + (i + perMarkerSize) * spanX
							- rect.width() / 2 + deffLeft;
					int center = rectStatistics.bottom
							+ Math.max(spaceBottom, rectBottomText.height())
							/ 2;
					int y = center + rect.height() / 2;
					canvas.drawText(text, x, y, paintTextX);
				}

				// 画刻度
				int startX = rectStatistics.left + (i + 1) * spanX + deffLeft;
				int startY = rectStatistics.bottom;
				int stopX = startX;
				int stopY = startY - markerWidth;
				canvas.drawLine(startX, startY, stopX, stopY, paintXY);

			}
			// 画0刻度
			int startX = rectStatistics.left + deffLeft;
			int startY = rectStatistics.bottom;
			int stopX = startX;
			int stopY = startY - markerWidth;
			canvas.drawLine(startX, startY, stopX, stopY, paintXY);
		} else {
			// 每段之间的间隔 = 每段之间的刻度数量 * 坐标数量
			float span = ((statisticsWidth - deffRight - deffLeft) * 1.0f
					/ ((markerXs.length - 1) * perMarkerSize) + 0.5f);
			spanX = Math.round(span);
			for (int i = 0; i < (markerXs.length) * perMarkerSize; i++) {
				// 画坐标　每隔perMarkerSize画一个坐标
				if (i % perMarkerSize == 0) {
					Rect rect = new Rect();
					String text = markerXs[i / perMarkerSize];
					paintTextX.getTextBounds(text, 0, text.length(), rect);
					int x = rectStatistics.left + (i) * spanX // marker
							- rect.width() / 2 + deffLeft;
					int center = rectStatistics.bottom
							+ Math.max(spaceBottom, rectBottomText.height())
							/ 2;
					int y = center + rect.height() / 2;
					canvas.drawText(text, x, y, paintTextX);
				}
				if (i / perMarkerSize == markerXs.length - 1) {
					continue;
				}
				// 画刻度
				int startX = rectStatistics.left + (i) * spanX + deffLeft;
				int startY = rectStatistics.bottom;
				int stopX = startX;
				int stopY = startY - markerWidth;
				canvas.drawLine(startX, startY, stopX, stopY, paintXY);

			}
			// 画0刻度
			int startX = rectStatistics.left + deffLeft;
			int startY = rectStatistics.bottom;
			int stopX = startX;
			int stopY = startY - markerWidth;
			canvas.drawLine(startX, startY, stopX, stopY, paintXY);
			// 画最后一个刻度
			int startXEnd = rectStatistics.left + deffLeft + perMarkerX
					* (markerXs.length - 1) * spanX;
			int startYEnd = rectStatistics.bottom;
			int stopXEnd = startXEnd;
			int stopYEnd = startYEnd - markerWidth;
			canvas.drawLine(startXEnd, startYEnd, stopXEnd, stopYEnd, paintXY);
		}
	}

	private int mLastX;
	private int mLastY;

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			int x = (int) event.getX();
			int y = (int) event.getY();
			int deltaX = x - mLastX;
			int deltaY = y - mLastY;
			if (Math.abs(deltaX) < Math.abs(deltaY)) {
				getParent().requestDisallowInterceptTouchEvent(false);
			} else {
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			mLastX = x;
			mLastY = y;
			break;

		default:
			break;
		}

		return super.dispatchTouchEvent(event);

	}

	private void drawY(Canvas canvas) {

		int startX = rectStatistics.left;
		int startY = rectStatistics.top;
		int stopX = startX;
		int stopY = rectStatistics.bottom;
		canvas.drawLine(startX, startY, stopX, stopY, paintXY);
	}

	private void drawX(Canvas canvas) {

		int startX = rectStatistics.left;
		int startY = rectStatistics.bottom;
		int stopX = rectStatistics.right;
		int stopY = startY;

		canvas.drawLine(startX, startY, stopX, stopY, paintXY);
	}

	private void init(Context context, AttributeSet attrs) {
		// 获取数据
		Resources resources = context.getResources();
		DEEP = resources.getColor(R.color.circle_ring);
		LIGHT = resources.getColor(R.color.circle_bg);
		colorXY = resources.getColor(R.color.circle_ring);

		onInit();

		// 初始化画笔
		paintTextX = new Paint();
		paintTextX.setAntiAlias(true);
		paintTextX.setDither(true);
		paintTextX.setStrokeCap(Paint.Cap.ROUND);
		paintTextX.setStrokeJoin(Paint.Join.ROUND);
		paintTextX.setTextSize(textSizeX);
		paintTextX.setColor(Color.WHITE);
		paintTextY = new Paint();
		paintTextY.setAntiAlias(true);
		paintTextY.setDither(true);
		paintTextY.setStrokeCap(Paint.Cap.ROUND);
		paintTextY.setStrokeJoin(Paint.Join.ROUND);
		paintTextY.setTextSize(textSizeY);
		paintTextY.setColor(Color.WHITE);
		paintXY = new Paint();
		paintXY.setAntiAlias(true);
		paintXY.setDither(true);
		paintXY.setStrokeCap(Paint.Cap.ROUND);
		paintXY.setStrokeJoin(Paint.Join.ROUND);
		paintXY.setStrokeWidth(widthXY);
		paintXY.setColor(colorXY);
		// 初始化空包区域
		spaceLeft = DisplayUtil.dip2px(context, DEFAULT_SPACE);
		spaceTop = DisplayUtil.dip2px(context, DEFAULT_SPACE);
		spaceRight = DisplayUtil.dip2px(context, DEFAULT_SPACE);
		spaceBottom = DisplayUtil.dip2px(context, DEFAULT_SPACE);
		// 初始化文字显示区域
		rectTopText = new Rect();
		rectRightText = new Rect();
		rectLeftText = new Rect();
		rectBottomText = new Rect();


	}

	public void setSpaceLeft(int left) {
		this.spaceLeft = DisplayUtil.dip2px(getContext(), left);
		invalidate();
	}

	public abstract String[] getmarkerYs();

	public abstract String[] getmarkerXs();

	public abstract void onInit();

	public void setTopText(String text){
		this.topText = text;
		
	}
	public void setRightText(String text){
		this.rightText = text;
	}
}
