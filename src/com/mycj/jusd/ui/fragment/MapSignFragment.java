package com.mycj.jusd.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.laputa.blue.util.XLog;
import com.mycj.jusd.R;
import com.mycj.jusd.adapter.SignAdapter;
import com.mycj.jusd.base.BaseFragment;
import com.mycj.jusd.bean.LitePalManager;
import com.mycj.jusd.bean.SignInfo;
import com.mycj.jusd.bean.news.SportHistory;

public class MapSignFragment extends BaseFragment{
	private ArrayList<SignInfo> signInfos;
	private SportHistory sportHistory;
	private SignAdapter adapter;
	private ListView lvSign;
	private Handler mHandler = new Handler(){};
	
	public MapSignFragment (SportHistory sportHistory){
		this.sportHistory = sportHistory;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map_sign, container,false);
		signInfos = new ArrayList<SignInfo>();
//		loadData();
		lvSign = (ListView) view.findViewById(R.id.lv_sign);
		adapter = new SignAdapter(getActivity(), signInfos);
		lvSign.setAdapter(adapter);
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
				
				int sportIndex = sportHistory.getSportIndex();
				// 所有的坐标
				List<SportHistory> list2 = LitePalManager.instance().getSportLocationHistoryByDateTimeWhenSigned(date, sportIndex);
				XLog.e("——————————所有的坐标数量为：" +list2);
				if (list2!=null && list2.size()>0) {
					int size = list2.size();
					XLog.e("——————————所有的坐标数量为：" +size);
					for (int i = 0; i < size; i++) {
						SportHistory sportLocationHistory = list2.get(i);
						String signDate = sportLocationHistory.getSportDate();
						int status =i;
						if (i==0) {
							 status = 0;
						}else if(i==size-1){
							 status = -1;
						}
					
						float distance = sportLocationHistory.getDistance();
						long durationTime = sportLocationHistory.getConsuming();
						float pace = sportLocationHistory.getPace();
						SignInfo info = new SignInfo(signDate, status, distance, durationTime, pace);
						signInfos.add(info);
					}
					mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							adapter.notifyDataSetChanged();
						}
					});

				}else{
					Log.e("zeej", "list2 为空");
				}
				
			
			}
		}).start();
	}

	private void loadData() {
		SignInfo info1  = new SignInfo("12:10",0, 0f, 0, 0);		
		SignInfo info2 = new SignInfo("12:14",1,1.13f, 123, 123);		
		SignInfo info3  = new SignInfo("12:17",2, 1.15f, 213, 67);		
		SignInfo info4  = new SignInfo("12:21",3, 1.16f, 123, 232);		
		SignInfo info5  = new SignInfo("12:24",-1, 1.21f, 123, 213);		
//		SignInfo info6  = new SignInfo("12:31",5, 1.33f, 12, pace);		
//		SignInfo info7  = new SignInfo("12:32",6, 1.41f, 123, pace);		
//		SignInfo info8  = new SignInfo("12:34",7, 1.52f, 123, pace);		
//		SignInfo info9  = new SignInfo("12:37",8, 1.61f, 123, pace);		
//		SignInfo info10  = new SignInfo("12:41",9, 1.73f, 123, pace);		
//		SignInfo info11  = new SignInfo("12:44",10, 1.81f, 123, pace);		
//		SignInfo info12  = new SignInfo("12:49",11, 2.11f, 13, pace);		
//		SignInfo info13  = new SignInfo("12:51",12, 3.11f, 13, pace);		
//		SignInfo info14  = new SignInfo("12:53",13, 3.31f, 13, pace);		
//		SignInfo info15  = new SignInfo("12:57",-1, 4.11f, 131, pace);		
		
		
		signInfos.add(info1);
		signInfos.add(info2);
		signInfos.add(info3);
		signInfos.add(info4);
		signInfos.add(info5);
//		signInfos.add(info6);
//		signInfos.add(info7);
//		signInfos.add(info8);
//		signInfos.add(info9);
//		signInfos.add(info10);
//		signInfos.add(info11);
//		signInfos.add(info12);
//		signInfos.add(info13);
//		signInfos.add(info14);
//		signInfos.add(info15);

	}
}
