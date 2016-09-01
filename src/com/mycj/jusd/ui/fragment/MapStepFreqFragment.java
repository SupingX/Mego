package com.mycj.jusd.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycj.jusd.R;
import com.mycj.jusd.base.BaseFragment;
import com.mycj.jusd.bean.LitePalManager;
import com.mycj.jusd.bean.news.SportHistory;
import com.mycj.jusd.view.NewStepFreqLineStatisticsView;
import com.mycj.jusd.view.SpeedLineStatisticsView;
import com.mycj.jusd.view.StepFreqLineStatisticsView;

public class MapStepFreqFragment extends BaseFragment{
	private SportHistory sportHistory;
	private Handler mHandler = new Handler(){};
	private String sportTime;
	private NewStepFreqLineStatisticsView ls;
	private List<Float> dataList = new ArrayList<Float>();;
	public MapStepFreqFragment (SportHistory sportHistory){
		this.sportHistory = sportHistory;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map_step_freq, container,false);
		ls = (NewStepFreqLineStatisticsView) view.findViewById(R.id.sl_speed);
		
		show(sportHistory);
		
		return view;
	}
	
	public void show(final SportHistory sportHistory){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if (sportHistory== null) {
					return ;
				}
				String date = sportHistory.getSportDate();
				 sportTime = sportHistory.getSportTime();
				int sportIndex = sportHistory.getSportIndex();
			
				List<SportHistory> list2 = LitePalManager.instance().getSportInfoHistoryByDateTime(date, sportIndex);
			
				if (list2!=null && list2.size()>0) {
					
					int size = list2.size();
					final float [] datas = new float[size];
					Log.e("zeej", "list2 -- size :" +size);
					for (int i = 0; i <size ; i++) {
						Log.e("zeej", list2.get(i).toString());
						datas[i] = list2.get(i).getFreq();
						
						dataList.add ((float)(list2.get(i).getFreq()));
						mHandler.post(new Runnable() {
							
							@Override
							public void run() {
								ls.setData(dataList,sportTime);
							}
						});
				
					}
				}else{
					Log.e("zeej", "list2 为空");
				}
				
			
			}
		}).start();
		
	}
}
