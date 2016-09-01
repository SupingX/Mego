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
import com.mycj.jusd.view.HeartRateLineStatisticsView;
import com.mycj.jusd.view.NewHeartRateLineStatisticsView;
import com.mycj.jusd.view.StepFreqLineStatisticsView;

public class MapHeartRateFragment extends BaseFragment{
	private SportHistory sportHistory;
	private Handler mHandler = new Handler(){};
	private String sportTime;
	private NewHeartRateLineStatisticsView ls;
	
	public MapHeartRateFragment (SportHistory sportHistory){
		this.sportHistory = sportHistory;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map_hr, container,false);
	ls = (NewHeartRateLineStatisticsView) view.findViewById(R.id.sl_speed);
		
		show(sportHistory);

		return view;
	}
	private List<Float> dataList = new ArrayList<Float>();;
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
			
				final List<SportHistory> list2 = LitePalManager.instance().getSportInfoHistoryByDateTime(date, sportIndex);
			
				if (list2!=null && list2.size()>0) {
					
					int size = list2.size();
					final float [] datas = new float[size];
					Log.e("zeej", "list2 -- size :" +size);
					dataList = new ArrayList<Float>();
					for (int i = 0; i <size ; i++) {
						Log.e("zeej", list2.get(i).toString());
						datas[i] = list2.get(i).getHr();
						dataList.add ((float)(list2.get(i).getHr()));
						mHandler.post(new Runnable() {
							
							@Override
							public void run() {
//								ls.setDatas(datas,sportTime);
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
