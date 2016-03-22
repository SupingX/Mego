package com.mycj.jusd.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.mycj.jusd.R;
import com.mycj.jusd.base.BaseApp;
import com.mycj.jusd.base.CustomTypeface;

public class FangRadioButton extends RadioButton {

	public FangRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context,attrs);
	}

	public FangRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context,attrs);
	}

	public FangRadioButton(Context context) {
		super(context);
		init(context,null);
	}

	private void init(Context context, AttributeSet attrs) {
		if (attrs != null) {
			TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FangtRadioButton);
			String typeface = ta.getString(R.styleable.FangtRadioButton_f_typeface);
			try {
				if (typeface!=null) {
					if (typeface.equals(CustomTypeface.JIAN.getName())) {
						setTypeface(BaseApp.TYPEFACE_JIAN);
					}else if (typeface.equals(CustomTypeface.FAN.getName())) {
						setTypeface(BaseApp.TYPEFACE_FAN);
					}else if (typeface.equals(CustomTypeface.NUM.getName())) {
						setTypeface(BaseApp.TYPEFACE_NUM);
					}
				}
			
			} catch (Exception e) {
			}
			ta.recycle();
		}
	}

}
