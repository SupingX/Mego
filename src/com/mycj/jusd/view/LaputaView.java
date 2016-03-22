package com.mycj.jusd.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class LaputaView extends View{

	public LaputaView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public LaputaView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LaputaView(Context context) {
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setStrokeJoin(Paint.Join.ROUND);
		p.setStyle(Paint.Style.STROKE);
		p.setColor(Color.RED);
		
		PathEffect[]es = new PathEffect[6];
		Path mPath = new Path();
		mPath.moveTo(0, 0);
		for (int i = 0; i < 30; i++) {
			mPath.lineTo(i*35, (float)(Math.random()*100));
		}
		
		es[0] = null;
		es[1] = new CornerPathEffect(20);
		es[2] = new DiscretePathEffect(3.0f, 5.0f);
		es[3] = new DashPathEffect(new float[]{10,0},0);
		Path path = new Path();
		path.addRect(0, 0,8,8,Path.Direction.CCW);
		es[4] = new PathDashPathEffect(path, 12, 0, PathDashPathEffect.Style.ROTATE);
		es[5] = new ComposePathEffect(es[3], es[1]);
		
		
		for (int i = 0; i < es.length; i++) {
			p.setPathEffect(es[i]);
			canvas.drawPath(mPath, p);
			canvas.translate(0, 200);
		}
	}

}
