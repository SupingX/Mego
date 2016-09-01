package com.mycj.jusd.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Sampler.Value;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView.Validator;

import com.laputa.blue.util.XLog;
import com.mycj.jusd.R;
import com.mycj.jusd.base.BaseFragment;
import com.mycj.jusd.bean.LitePalManager;
import com.mycj.jusd.bean.news.SportHistory;
import com.mycj.jusd.util.DataUtil;
import com.mycj.jusd.util.DateUtil;
import com.mycj.jusd.view.FangTextView;

public class MapDataFragment extends BaseFragment {
	private FangTextView tvStep;
	private FangTextView tvConsuming;
	private FangTextView tvDistance;
	private FangTextView tvAvgSpeed;
	private FangTextView tvCalorie;
	private FangTextView tvFreqStep;
	private FangTextView tvAvgHeartRate;
	private SportHistory history;
	private Handler mHandler = new Handler(){};
	public MapDataFragment(SportHistory history){
		this.history = history;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		show(history);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map_data, container,
				false);

		// 步数
		tvStep = (FangTextView) view.findViewById(R.id.tv_sport_info_step);
		// 用时
		tvConsuming = (FangTextView) view.findViewById(R.id.tv_sport_info_time);
		// 距离
		tvDistance = (FangTextView) view
				.findViewById(R.id.tv_sport_info_distance);
		// 平均速度
		tvAvgSpeed = (FangTextView) view.findViewById(R.id.tv_sport_avg_speed);
		// 能量消耗
		tvCalorie = (FangTextView) view.findViewById(R.id.tv_sport_info_cal);
		// 平均步频
		tvFreqStep = (FangTextView) view
				.findViewById(R.id.tv_sport_avg_step_freq);
		// 平均心率
		tvAvgHeartRate = (FangTextView) view.findViewById(R.id.tv_sport_hr_avg);

//		freshUi(history);
		
		return view;
	}

//	public void freshUi(SportHistory sport) {
//		String step = "";
//		String consuming = "";
//		String distance = "";
//		String speed = "";
//		String calorie = "";
//		String freqStep = "";
//		String hr = "";
//
//
//		if (sport != null) {
//			int _step = sport.getStep();
//			int _consuming = sport.getConsuming();
//			float _distance = sport.getDistance()*1.0f /100;
//			step = String.valueOf(_step);
//			consuming = DateUtil.formateTime(_consuming);
//			distance = String.valueOf(_distance) ;
//			speed =  getAvgSpeed(_distance,_consuming);
//			calorie = String.valueOf(sport.getCalorie());
//			freqStep = getAvgStepFreq(_step,_consuming) ;
//			hr = String.valueOf(sport.getHr()) ; ;
//		} else {
//			step = "0";
//			consuming = "00:00:00";
//			distance = "0.00" ;
//			speed = "0.00" ;
//			calorie = "0.00" ;
//			freqStep = "0.0" ;
//			hr = "0.0" ;
//
//		}
//		// 单位
//		String stepPerSecond = getString(R.string.step_per_sencod);
//		String km = getString(R.string.km);
//		String km_per_hour = getString(R.string.km_per_hour);
//		String kcal = getString(R.string.kcal);
//		String bpm = getString(R.string.bpm);
////		String bpm = "bpm?";
//		tvStep.setText(step);
//		tvConsuming.setText(consuming);
//		tvDistance.setText(distance+ km);
//		tvAvgSpeed.setText(speed+ km_per_hour);
//		tvCalorie.setText(calorie+ kcal);
//		tvFreqStep.setText(freqStep+ stepPerSecond);
//		tvAvgHeartRate.setText(hr+ bpm);
//	}

//	private String getAvgStepFreq(int _step, int _consuming) {
//		if (_consuming == 0) {
//			return String.valueOf(0);
//		}
//		return DataUtil.format(_step / _consuming);
//	}
//
//	private String getAvgSpeed(float _distance, int _consuming) {
//		if (_consuming == 0) {
//			return String.valueOf(0);
//		}
//		return DataUtil.format(_distance *60*60 / _consuming);
//	}
	

	
	public void show(final SportHistory sportHistory){
		new Thread(new Runnable() {
	

			@Override
			public void run() {
				if (sportHistory== null) {
					return ;
				}
				String date = sportHistory.getSportDate();
				int sportIndex = sportHistory.getSportIndex();
				final double avgHrByDateTime = LitePalManager.instance().getAvgHrByDateTime(date, sportIndex);
				final double avgSpeedByDateTime = LitePalManager.instance().getAvgSpeedByDateTime(date, sportIndex);
				final double avgStepFreqByDateTime = LitePalManager.instance().getAvgStepFreqByDateTime(date, sportIndex);
				final int totalConsumingByDateTime = LitePalManager.instance().getTotalConsumingByDateTime(date, sportIndex);
				final int totalCalorieByDateTime = LitePalManager.instance().getTotalCalorieByDateTime(date, sportIndex);
				final int totalStepByDateTime = LitePalManager.instance().getTotalStepByDateTime(date, sportIndex);
				final int totalDistanceByDateTime = LitePalManager.instance().getTotalDistanceByDateTime(date, sportIndex);
				// group by 
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
//						..jibu zenm wan 
						
						
						String step = "";
						String consuming = "";
						String distance = "";
						String speed = "";
						String calorie = "";
						String freqStep = "";
						String hr = "";
						step = String.valueOf(totalStepByDateTime);
						consuming = DateUtil.formateTime(totalConsumingByDateTime);
						distance = String.valueOf(totalDistanceByDateTime *1.0f/100) ;
						speed =  String.valueOf(DataUtil.format(avgSpeedByDateTime));
						calorie = String.valueOf(totalCalorieByDateTime*1.0f/100);
						freqStep = String.valueOf(DataUtil.format(avgStepFreqByDateTime)) ;
						hr = String.valueOf(DataUtil.format(avgHrByDateTime));
						// 单位
						if (isAdded()) {
							String stepPerSecond = getString(R.string.step_per_sencod);
							String km = getString(R.string.km);
							String km_per_hour = getString(R.string.km_per_hour);
							String kcal = getString(R.string.kcal);
							String bpm = getString(R.string.bpm);
	//						String bpm = "bpm?";
							tvStep.setText(step);
							tvConsuming.setText(consuming);
							tvDistance.setText(distance+ km);
							tvAvgSpeed.setText(speed+ km_per_hour);
							tvCalorie.setText(calorie+ kcal);
							tvFreqStep.setText(freqStep+ stepPerSecond);
							tvAvgHeartRate.setText(hr+ bpm);
						}
					}
				});
			}
		}).start();
		
	}
}
