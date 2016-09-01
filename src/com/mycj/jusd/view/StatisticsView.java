package com.mycj.jusd.view;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Random;

import javax.crypto.spec.OAEPParameterSpec;

import com.laputa.blue.util.XLog;
import com.mycj.jusd.R;
import com.mycj.jusd.util.DisplayUtil;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class StatisticsView extends View {

	private int span;
	private final static int DEFAULT_SPAN = 40;// dp
	private final static int DEFAULT_SIZE = 24;// dp
	private final static int DEFAULT_TEXT_SIZE = 16;// dp
	private final static String TAG = StatisticsView.class.getSimpleName();// dp

	private Paint paintXY;
	private int widthXY = 4;
	private String unit = "步";

	private Paint paintRect;
	private int DEEP = Color.RED;
	private int LIGHT = Color.GREEN;
	private int colorXY = Color.BLACK;
	private float max = 2000f;
	
	private Paint paintText;
	private int size = DEFAULT_SIZE;
	private int textSize = DEFAULT_TEXT_SIZE;
	private boolean isTouching = false;
	private int touchPosition = -1;

	private List<Float> datas;

	public StatisticsView(Context context) {
		super(context);
		init(context, null);
	}

	public StatisticsView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	public StatisticsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}
	
	public void setDate(List<Float> datas){
		this.datas = datas;
		invalidate();
	}
	
	private void init(Context context, AttributeSet attrs) {
		this.setClickable(true);
		datas = new ArrayList<Float>();
		
//		loadTestData();

		span = DisplayUtil.dip2px(context, DEFAULT_SPAN);
		Resources resources = context.getResources();
		DEEP = resources.getColor(R.color.circle_ring);
		LIGHT = resources.getColor(R.color.circle_bg);
		colorXY = resources.getColor(R.color.circle_ring);
		
		paintText = new Paint();
		paintText.setAntiAlias(true);
		paintText.setDither(true);
		paintText.setStrokeCap(Paint.Cap.ROUND);
		paintText.setStrokeJoin(Paint.Join.ROUND);
		paintText.setTextSize(textSize);
		paintText.setColor(Color.WHITE);
		
		paintXY = new Paint();
		paintXY.setAntiAlias(true);
		paintXY.setDither(true);
		paintXY.setStrokeCap(Paint.Cap.ROUND);
		paintXY.setStrokeJoin(Paint.Join.ROUND);
		paintXY.setStrokeWidth(widthXY);
		paintXY.setColor(colorXY);
		
		paintRect = new Paint();
		paintRect.setAntiAlias(true);
		paintRect.setDither(true);
		paintRect.setStrokeCap(Paint.Cap.ROUND);
		paintRect.setStrokeJoin(Paint.Join.ROUND);
		paintRect.setStrokeWidth(widthXY);

	
	}

	private void loadTestData() {
		Random random = new Random();
		for (int i = 0; i < 24; i++) {
			float value = random.nextInt((int) max);
			datas.add(value);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		/*
		 * int modeWidth = MeasureSpec.getMode(widthMeasureSpec); int modeHeight
		 * = MeasureSpec.getMode(heightMeasureSpec); int sizeWidth =
		 * MeasureSpec.getSize(widthMeasureSpec); int sizeHeight =
		 * MeasureSpec.getSize(heightMeasureSpec); Point screenMetrics =
		 * DisplayUtil.getScreenMetrics(getContext()); if (modeWidth ==
		 * MeasureSpec.AT_MOST ) { }else if( modeHeight == MeasureSpec.AT_MOST){
		 * 
		 * }
		 */

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		canvas.drawText("舞低杨柳楼心月", getWidth()/2, getHeight()/2, paintText);
//		drawText(getWidth()/2,getHeight()/2,"舞低杨柳楼心月",canvas);
	
		drawY(canvas);
		drawX(canvas);
		// drawRect(canvas);
		drawRect(canvas, false);
		drawText(canvas,new String[]{"00:00","06:00","12:00","18:00","24:00",});
		drawUnit(canvas);
	}

	private void drawUnit(Canvas canvas) {
		Rect rect = new Rect();
		paintText.getTextBounds(unit, 0, unit.length(), rect);
		int x = getPaddingRight() + widthXY/2 +20-rect.width()/2 ;
		int y = getPaddingTop() ;
		canvas.drawText(unit, x, y, paintText);
	}

	private void drawRect(Canvas canvas, boolean canScroll) {
		
		int width = getWidth();
		int height = getHeight();
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		int paddingTop = getPaddingTop();
		int paddingBottom = getPaddingBottom();

		int top = 0;
		int left = 0;
		int right = 0;
		int bottom = 0;
		
		XLog.e("_________________________________---datas :" + datas );
		if (datas != null && datas.size() > 0) {
			if (canScroll) {
				for (int i = 0; i < datas.size(); i++) {
					int rightMax = (i + 1) * span + paddingLeft;
					// 差值不足一个span 也要将这个rect画出来
					if (rightMax - (width - paddingRight) < span) {
						if (i % 2 != 0) {
							paintRect.setColor(DEEP);
						} else {
							paintRect.setColor(LIGHT);
						}
						bottom = height - paddingBottom - widthXY / 2 - DisplayUtil.sp2px(textSize, 1);
						top = bottom
								- getHeightByValue(datas.get(i), max, height
										- paddingBottom - paddingTop - widthXY
										/ 2);
						left = paddingLeft + widthXY / 2 + i * span;
						right = left + span;
						canvas.drawRect(new Rect(left, top, right, bottom),
								paintRect);
					}
				}
			} else {
				
				int span = (int) ((1.0*(width-paddingLeft-paddingRight - widthXY/2)  / datas.size()) -0.5);
				
				for (int i = 0; i < datas.size(); i++) {
					if (i % 2 != 0) {
						paintRect.setColor(DEEP);
					} else {
						paintRect.setColor(LIGHT);
					}
					if (i != touchPosition) {
						bottom = height - paddingBottom - widthXY / 2 - DisplayUtil.sp2px(textSize, 1);
						top = bottom
								- getHeightByValue(datas.get(i), max, height
										- paddingBottom - paddingTop - widthXY / 2) + paddingTop;
						left = paddingLeft + widthXY / 2 + i * span;
						right = left + span;
						canvas.drawRect(new Rect(left, top, right, bottom), paintRect);
					}
				
				}
				
				//如果按了，就画按的那一个条子，并且显示数字
				if (isTouching && touchPosition != -1) {
					if (touchPosition % 2 != 0) {
						paintRect.setColor(DEEP);
					} else {
						paintRect.setColor(LIGHT);
					}
					bottom = height - paddingBottom - widthXY / 2 - DisplayUtil.sp2px(textSize, 1);
					top = bottom
							- getHeightByValue(datas.get(touchPosition), max, height
									- paddingBottom - paddingTop - widthXY / 2) + paddingTop;
					left = paddingLeft + widthXY / 2 + touchPosition * span-span/4;
					right = left + span/2+span;
					canvas.drawRect(new Rect(left, top, right, bottom), paintRect);
					
					
					Rect rect = new Rect();
					String text = String.valueOf(datas.get(touchPosition));
					paintText.getTextBounds(text, 0, text.length(), rect);
					int x = left + (span+span/2-rect.width())/2;
					int y = top-rect.height()/2;
					paintText.setColor(Color.GREEN);
					canvas.drawText(text, x, y, paintText);
					
					
					Rect rectTime = new Rect();
					String timeText = (String.valueOf(touchPosition).length()==1?("0"+String.valueOf(touchPosition)):(String.valueOf(touchPosition))) +":00";
					paintText.getTextBounds(timeText, 0, timeText.length(), rectTime);
					paintText.setColor(Color.WHITE);
					int xTime = left + (span+span/2-rectTime.width())/2;
					int yTime = top-rect.height()-rectTime.height()/2;
					canvas.drawText(timeText, xTime, yTime, paintText);
				}
			}
			
		} else {
//			drawText(100, 100, "NO Data", canvas);
		}
	}

	private void drawText(Canvas canvas,String [] texts) {
		int width = getWidth();
		int height = getHeight();
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		int paddingTop = getPaddingTop();
		int paddingBottom = getPaddingBottom();
		if (texts != null && texts.length >0) {
			int span = (int) ((width *1.0-paddingLeft-paddingRight) /texts.length -0.5);
			for (int i = 0; i < texts.length; i++) {
				Rect rect = new Rect();
				String text = texts[i];
				paintText.getTextBounds(text, 0, text.length(), rect);
				int left = span * i + paddingLeft + widthXY / 2;
				int x = left + (span-rect.width())/2;
				int y = height-rect.height();
				canvas.drawText(text, x, y, paintText);
			}
		}else{
			// 没有数据可以写
		}
		
		
	}

	/**
	 * 根据当前的值获取数据块rect的高度
	 * 
	 * @param value
	 *            当前值
	 * @param maxValue
	 *            最大值
	 * @param height
	 *            总的rect高度
	 * @return
	 */
	private int getHeightByValue(float value, float maxValue, int height) {
		double radius = value / maxValue * 1.0;
		return (int) (radius * height);
	}

	private void drawY(Canvas canvas) {
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		int paddingTop = getPaddingTop();
		int paddingBottom = getPaddingBottom();
		int width = getWidth();
		int height = getHeight();
		
		int startX = paddingLeft;
		int startY = paddingTop;
		int stopX = startX;
		int stopY = height - paddingBottom - DisplayUtil.sp2px(textSize, 1);

		canvas.drawLine(startX, startY, stopX, stopY, paintXY);
	}

	private void drawX(Canvas canvas) {
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		int paddingTop = getPaddingTop();
		int paddingBottom = getPaddingBottom();
		int width = getWidth();
		int height = getHeight();

		int startX = paddingLeft;
		int startY = height - paddingBottom - DisplayUtil.sp2px(textSize, 1);
		int stopX = width - paddingRight;
		int stopY = startY;

		canvas.drawLine(startX, startY, stopX, stopY, paintXY);
	}
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isTouching = true;
			float downX = event.getX();
			float downY = event.getY();
			click(downX,downY,false);
			break;
		case MotionEvent.ACTION_MOVE:
			float moveX = event.getX();
			float moveY = event.getY();
			click(moveX,moveY,false);
			break;
		case MotionEvent.ACTION_UP:
			isTouching = false;
			touchPosition = -1;
			break;

		}
		invalidate();
		
		return super.onTouchEvent(event);
	}
	
	
	private void click(float downX, float downY,boolean isScroll) {
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		int paddingTop = getPaddingTop();
		int paddingBottom = getPaddingBottom();
		int width = getWidth();
		int height = getHeight();
		int top = 0;
		int left = 0;
		int right = 0;
		int bottom = 0;
		if (isScroll) {
			
		}else{
			int span = (int) ((1.0*(width-paddingLeft-paddingRight - widthXY/2)  / datas.size()) -0.5);
			Log.e(TAG, "down :" +downX+","+downY );
			for (int i = 0; i < datas.size(); i++) {
				bottom = height - paddingBottom - widthXY / 2 - DisplayUtil.sp2px(textSize, 1);
				top = bottom
						- getHeightByValue(datas.get(i), max, height
								- paddingBottom - paddingTop - widthXY / 2) + paddingTop;
				left = paddingLeft + widthXY / 2 + i * span;
				right = left + span;
				if (downX >=left && downX <= right 
//						&& downY <=bottom && downY >= top
						) {
					Log.e(TAG, "你点击了 ：" +i);
					if (mOnStatisticsClickListener != null) {
						mOnStatisticsClickListener.onClick(this, i);
					}
					touchPosition = i;
				}
			}
		}
	}


	public interface OnStatisticsClickListener {
		void onClick(StatisticsView view , int position);
	}
	private OnStatisticsClickListener mOnStatisticsClickListener;
	public void setOnStatisticsClickListener(OnStatisticsClickListener mOnStatisticsClickListener){
		this.mOnStatisticsClickListener = mOnStatisticsClickListener;
	}
}
