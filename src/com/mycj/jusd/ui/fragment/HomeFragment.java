package com.mycj.jusd.ui.fragment;

import java.util.Date;
import java.util.Random;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mycj.jusd.R;
import com.mycj.jusd.bean.JunConstant;
import com.mycj.jusd.bean.news.SportHistory;
import com.mycj.jusd.ui.activity.SportInfo;
import com.mycj.jusd.util.DataUtil;
import com.mycj.jusd.util.DateUtil;
import com.mycj.jusd.util.JunUtil;
import com.mycj.jusd.util.PaceUtil;
import com.mycj.jusd.util.SharedPreferenceUtil;
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
	private FangTextView tvStep;
	private FangTextView tvPath;

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
		
		tvStep = (FangTextView) homeView.findViewById(R.id.tv_step);
		tvPath = (FangTextView) homeView.findViewById(R.id.tv_path);
		
		
		/* <!-- 
	    	计步及轨迹按钮，当智能手表此次运动设定为计步时，计步按钮呈
			加亮显示，本页面主要显示最近一次运动的计步数据；当智能手表
			没有GPS功能时，两个按钮均不显示
	     -->*/
		
	/*	rbStep.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (mOnHomeFragmentClickListener!=null) {
					mOnHomeFragmentClickListener.doSetStepUi(isChecked);
					freshHomeFragmentUi();
				}
			}
		});
		
		rbPath.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (mOnHomeFragmentClickListener!=null) {
					mOnHomeFragmentClickListener.doSetPathUi(isChecked);
					freshHomeFragmentUi();
				}
			}
		});*/
		
		
		
		
		
		freshHomeFragmentUi(new SportHistory());

		imgRefresh.setOnClickListener(this);
		imgShare.setOnClickListener(this);
		circleSport.setOnClickListener(this);

		Log.e("", "homeFragment--onCreateView()");
		return homeView;
	}
	
	private void freshCurrentSportInfo(SportHistory info) {
		if (info == null) {
			return ;
		}
		String valueTitle = ""; // 标题中间的字
		String unitTitle = ""; // 单位： 步  or 公里
		String menu1 = ""; // 距离 or 用时
		String menu2 = ""; // 用时 or 平均配速
		String value1 = ""; //第一行的值
		String value2 = ""; //第二行的值
		
		type = info.getType();
		long time = info.getConsuming();
		long distance = info.getDistance();
		if (type==0) { // 当为计步时
			tvStep.setSelected(true);
			tvPath.setSelected(false);
			valueTitle = String.valueOf(info.getStep());
			unitTitle = "步";
			menu1 = "距离";
			menu2 = "用时";
			value1 = String.valueOf(distance) + "公里";
			value2 = DateUtil.formateTime(time);
			
		} else if(type==1) { //当为轨迹时
			tvStep.setSelected(false);
			tvPath.setSelected(true);
			valueTitle = distance + "";
			unitTitle = "公里";
			menu1 = "用时";
			menu2 = "平均配速";
			value1 = DateUtil.formateTime(time);
			value2 = PaceUtil.getAvgPace(distance*1.0f/100,time); //平均配速 -->每公里多少分钟
		}
		tvCircleStep.setText(valueTitle);
		tvUnit.setText(unitTitle);
		tvInfo_1.setText(menu1);
		tvCircleDistance.setText(value1);
		tvInfo_2.setText(menu2);
		tvCircleTime.setText(value2);
		
		freshHeartRateInfo(info.getHr());
		tvInfoCal.setText(DataUtil.format(info.getCalorie()*1.0/100)+"Kcal") ;
		
	}
	
	

	/**
	 * for test 
	 */
	public void freshHomeFragmentUi(SportHistory info) {
		Random random = new Random();
		// 刷新时间信息
		freshTime();
		
		int max = 0;
		int progress = 0;
		if (info.getType() == 0) {
			progress = info.getStep();
			max =  (int) SharedPreferenceUtil.get(getContext(), JunConstant.SHARE_SPORT_GOAL_STEP, JunConstant.DEFAULT_SPORT_PLAN_GOAL);
		}else{
			progress= info.getDistance();
			max =  (int) SharedPreferenceUtil.get(getContext(), JunConstant.SHARE_SPORT_GOAL_DISTANCE, JunConstant.DEFAULT_SPORT_PLAN_GOAL_DISTANCE);
		}
		
		
		// 刷新圆环信息
//		freshCircleSport(20000, random.nextInt(20000));
		freshCircleSport(max, progress);

		// 刷新运动信息
		freshCurrentSportInfo(info);

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
	
	/*public void freshSportInfo(int step, int time) {
		
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
	}*/

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
			
			
			if (mOnHomeFragmentClickListener != null ) { // 当选择的是计步模式时
				mOnHomeFragmentClickListener.doSportClick(type);
			
			}
			
			break;
		}
	}
	
	private int type =0;
	
	public interface OnHomeFragmentClickListener {
		void doShare();
		void doRefresh();
		void doSetStepUi(boolean isChecked);
		void doSetPathUi(boolean isChecked);
		/**
		 * 0 :sport
		 * 1 :paths
		 * @param type
		 */
		void doSportClick(int type);
	}

	public OnHomeFragmentClickListener mOnHomeFragmentClickListener;
//	private FangRadioButton rbStep;
//	private FangRadioButton rbPath;

	public void setOnHomeFragmentClickListener(OnHomeFragmentClickListener mOnHomeFragmentClickListener) {
		this.mOnHomeFragmentClickListener = mOnHomeFragmentClickListener;
	}
	

}
