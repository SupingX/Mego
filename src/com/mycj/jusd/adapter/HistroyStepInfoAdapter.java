package com.mycj.jusd.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mycj.jusd.R;
import com.mycj.jusd.bean.news.SportHistory;

public class HistroyStepInfoAdapter extends BaseAdapter{
	private List<SportHistory> datas = new ArrayList<SportHistory>();
	private String month;
	private String day; 
	public HistroyStepInfoAdapter(Context context,List<SportHistory> datas){
		this.datas = datas;
		Resources resources = context.getResources();
		month = resources.getString(R.string.month);
		day = resources.getString(R.string.day);
		
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView==null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_sport, parent,false);
			holder = new ViewHolder();
			holder.tvSportDate = (TextView) convertView.findViewById(R.id.tv_sport_date);
			holder.tvSportSteps = (TextView) convertView.findViewById(R.id.tv_path_step);
			holder.tvSportTime = (TextView) convertView.findViewById(R.id.tv_sport_time);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		//设置数据
		String sportDate = "";
		String sportStep = "";
		String sportConsuming = "";
		
		SportHistory sportStepHistory = datas.get(position);
		int sportIndex = sportStepHistory.getSportIndex();
		String sportDateAll = sportStepHistory.getSportDate();
		int step = sportStepHistory.getStep();
		int distance = sportStepHistory.getDistance();
		int consuming = sportStepHistory.getConsuming();
		
		sportDate =  sportDateAll.substring(4, 6) +month + sportDateAll.substring(6, 8)  + day;
		sportStep = String.valueOf(step);
		sportConsuming = formateTime(consuming);
		
		holder.tvSportDate.setText(sportDate);
		holder.tvSportSteps.setText(sportStep);
		holder.tvSportTime.setText(sportConsuming);
		
		return convertView;
	}
	
	
	
	private String formateTime(int sportTime) {
		StringBuffer sb = new StringBuffer();
		if (sportTime<60) {
			sb.append("00:00:");
			sb.append(getString(sportTime));
		}else{
			int min = (int) Math.floor(sportTime/60f);
			int second = sportTime%60;
			if (min<60) {
				sb.append("00:")
				.append(getString(min))
				.append(":")
				.append(getString(second));
			}else{
				int hour =(int) Math.floor(min/60f);
				int newMin = min%60;
				sb.append(getString(hour))
					.append(":")
					.append(getString(newMin))
					.append(":")
					.append(getString(second));
			}
		}
		return sb.toString();
	}

	private String formateStep(int step) {
		return String.valueOf(step);
	}

	private String formateDate(String date,Context context) {
		StringBuffer sb = new StringBuffer();
		String month = date.substring(4, 6);
		String day = date.substring(6, 8);
		Resources resources = context.getResources();
		sb.append(month)
		.append(resources.getString(R.string.month))
		.append(day)
		.append(resources.getString(R.string.day))
		;
		return sb.toString();
	}
	
	
	public String getString(int value){
		String valueOf = String.valueOf(value);
		if (valueOf.length()==1) {
			return "0"+valueOf;
		}
		return valueOf;
	}


	public class ViewHolder {
		TextView tvSportDate;
		TextView tvSportSteps;
		TextView tvSportTime;
	}

}
