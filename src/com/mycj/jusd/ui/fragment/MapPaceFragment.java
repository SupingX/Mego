package com.mycj.jusd.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.laputa.blue.util.XLog;
import com.mycj.jusd.R;
import com.mycj.jusd.base.BaseFragment;
import com.mycj.jusd.bean.LitePalManager;
import com.mycj.jusd.bean.news.SportHistory;
import com.mycj.jusd.view.LineStatisticsView;
import com.mycj.jusd.view.NewPaceLineStatisticsView;
import com.mycj.jusd.view.PaceLineStatisticsView;
import com.mycj.jusd.view.SpeedLineStatisticsView;

public class MapPaceFragment extends BaseFragment{
	private NewPaceLineStatisticsView ls;
private SportHistory sportHistory;
private Handler mHandler = new Handler(){};
private String sportTime;
private List<Float> dataList = new ArrayList<Float>();;

	public MapPaceFragment(SportHistory sportHistory){
		this.sportHistory = sportHistory;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map_pace, container,false);
		ls = (NewPaceLineStatisticsView) view.findViewById(R.id.sl_speed);
		show(sportHistory);
		XLog.e("sportHistory : " + sportHistory);
	
		return view;
	}
	
	public void show(final SportHistory sportHistory){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if (sportHistory== null) {
					return ;
				}
				sportTime = sportHistory.getSportTime();
				String date = sportHistory.getSportDate();
				int sportIndex = sportHistory.getSportIndex();
				List<SportHistory> list2 = LitePalManager.instance().getSportInfoHistoryByDateTime(date, sportIndex);
				if (list2!=null && list2.size()>0) {
					int size = list2.size();
					final float [] datas = new float[size];
					Log.e("zeej", "list2 -- size :" +size);
					dataList = new ArrayList<Float>();
					for (int i = 0; i <size ; i++) {
						Log.e("zeej", list2.get(i).toString());
						datas[i] = list2.get(i).getPace();
						dataList.add ((float)(list2.get(i).getPace()));
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
