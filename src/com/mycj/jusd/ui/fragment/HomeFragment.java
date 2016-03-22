package com.mycj.jusd.ui.fragment;

import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycj.jusd.R;
import com.mycj.jusd.util.DataUtil;
import com.mycj.jusd.view.DateUtil;
import com.mycj.jusd.view.FangRadioButton;
import com.mycj.jusd.view.FangTextView;
import com.mycj.jusd.view.SportCircleView;

/**
 * 显示计步/轨迹 圈圈图的Fragment
 * @author Administrator
 *
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

	private SportCircleView circleSport;
	private FangTextView tvInfoDate;
	private FangTextView tvInfoTime;
	private FangTextView tvCircleDistance;
	private FangTextView tvCircleTime;
	private FangTextView tvCircleStep;
	private FangTextView tvInfoCal;
//	private FangTextView tvInfoSpeed;
	private FangTextView tvInfoHeartRate;
	private ImageView imgRefresh;
	private ImageView imgShare;
	private FangTextView tvInfo_1;
	private FangTextView tvInfo_2;
	private FangTextView tvUnit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View homeView = inflater.inflate(R.layout.fragment_home, container, false);
		circleSport = (SportCircleView) homeView.findViewById(R.id.circle_sport);
		tvInfoDate = (FangTextView) homeView.findViewById(R.id.tv_info_date);
		tvInfoTime = (FangTextView) homeView.findViewById(R.id.tv_info_time);
		tvCircleDistance = (FangTextView) homeView.findViewById(R.id.tv_circle_distance);
		tvCircleTime = (FangTextView) homeView.findViewById(R.id.tv_circle_hour);
		tvCircleStep = (FangTextView) homeView.findViewById(R.id.tv_circle_step);
		tvInfoCal = (FangTextView) homeView.findViewById(R.id.tv_info_cal);
//		tvInfoSpeed = (FangTextView) homeView.findViewById(R.id.tv_info_speed);
		tvInfoHeartRate = (FangTextView) homeView.findViewById(R.id.tv_info_heart_rate);
		tvInfo_1= (FangTextView) homeView.findViewById(R.id.tv_info_1);
		tvInfo_2 = (FangTextView) homeView.findViewById(R.id.tv_info_2);
		tvUnit = (FangTextView) homeView.findViewById(R.id.tv_unit);
		imgRefresh = (ImageView) homeView.findViewById(R.id.img_refresh);
		imgShare = (ImageView) homeView.findViewById(R.id.img_share);
		
		rbStep = (FangRadioButton) homeView.findViewById(R.id.rb_step);
		rbPath = (FangRadioButton) homeView.findViewById(R.id.rb_path);
		rbStep.setChecked(true);
		
		
		/* <!-- 
	    	计步及轨迹按钮，当智能手表此次运动设定为计步时，计步按钮呈
			加亮显示，本页面主要显示最近一次运动的计步数据；当智能手表
			没有GPS功能时，两个按钮均不显示
	     -->*/
		
		rbStep.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (mOnHomeFragmentClickListener!=null) {
					mOnHomeFragmentClickListener.doSetStepUi(isChecked);
				}
			}
		});
		
		rbPath.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (mOnHomeFragmentClickListener!=null) {
					mOnHomeFragmentClickListener.doSetPathUi(isChecked);
				}
			}
		});

		freshHomeFragmentUi();

		imgRefresh.setOnClickListener(this);
		imgShare.setOnClickListener(this);
		circleSport.setOnClickListener(this);

		Log.e("", "homeFragment--onCreateView()");
		return homeView;
	}

	public void freshHomeFragmentUi() {
		// 刷新时间信息
		freshTime();

		// 刷新圆环信息
		freshCircleSport(100, 20);

		// 刷新运动信息
		freshSportInfo(0, 0);

		// 刷新心率信息
		freshHeartRateInfo(0);
	}
	
	
	
	
	public void freshTime() {
		Date date = new Date();
		tvInfoDate.setText(DateUtil.dateToString(date, "yyyy/MM/dd"));
		tvInfoTime.setText(DateUtil.dateToString(date, "HH:mm"));
	}

	public void freshCircleSport(int max, int progress) {
		circleSport.setMax(max);
		circleSport.setProgressWithAnimation(progress, 1000);
	}

	public void freshInfos(String info1,String info2,String unit){
		tvInfo_1.setText(info1);
		tvInfo_2.setText(info2);
		tvUnit.setText(unit);
	}
	
	public void freshSportInfo(int step, int time) {
		
		String valueTitle = ""; // 标题中间的字
		String unitTitle = ""; // 单位： 步  or 公里
		String menu1 = ""; // 距离 or 用时
		String menu2 = ""; // 用时 or 平均配速
		String value1 = ""; //第一行的值
		String value2 = ""; //第二行的值
		
		if (rbStep.isChecked()) { // 当为计步时
			valueTitle = String.valueOf(step);
			unitTitle = "步";
			menu1 = "距离";
			menu2 = "用时";
			value1 = DataUtil.getDistanceWithUnit(step,getActivity()) + "";
			value2 = DateUtil.formateTime(time);
			
		} else { //当为轨迹时
			valueTitle = DataUtil.getDistance(step) + "";
			unitTitle = "公里";
			menu1 = "用时";
			menu2 = "平均配速";
			value1 = DateUtil.formateTime(time);
			value2 = DataUtil.getAvgSpeed(step, time);
		}
		tvCircleStep.setText(valueTitle);
		tvUnit.setText(unitTitle);
		tvInfo_1.setText(menu1);
		tvCircleDistance.setText(value1);
		tvInfo_2.setText(menu2);
		tvCircleTime.setText(value2);
	}

	public void freshHeartRateInfo(int hr) {
		String heartRateStr = String.valueOf(hr) + "次/分钟";
		tvInfoHeartRate.setText(heartRateStr);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.img_refresh:
			
			if (mOnHomeFragmentClickListener != null) {
				mOnHomeFragmentClickListener.doRefresh();
			}
			
			break;
		case R.id.img_share:
			
			if (mOnHomeFragmentClickListener != null) {
				mOnHomeFragmentClickListener.doShare();
			}
			
			break;
		case R.id.circle_sport:
			
			if (mOnHomeFragmentClickListener != null) {
				mOnHomeFragmentClickListener.doSportClick();
			}
			
			break;
		}
	}

	public interface OnHomeFragmentClickListener {
		void doShare();
		void doRefresh();
		void doSetStepUi(boolean isChecked);
		void doSetPathUi(boolean isChecked);
		void doSportClick();
	}

	public OnHomeFragmentClickListener mOnHomeFragmentClickListener;
	private FangRadioButton rbStep;
	private FangRadioButton rbPath;

	public void setOnHomeFragmentClickListener(OnHomeFragmentClickListener mOnHomeFragmentClickListener) {
		this.mOnHomeFragmentClickListener = mOnHomeFragmentClickListener;
	}
	

}
