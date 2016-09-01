package com.mycj.jusd.adapter;

import java.util.ArrayList;

import com.mycj.jusd.R;
import com.mycj.jusd.bean.news.SportHistory;
import com.mycj.jusd.util.DataUtil;
import com.mycj.jusd.view.FangTextView;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class SportPathHistoryAdapter extends BaseAdapter {
	
	private ArrayList<SportHistory> datas ;
	private String distanceUnit;
	private String month;
	private String day;
	
	public SportPathHistoryAdapter(Context context,ArrayList<SportHistory> datas){
		this.datas = datas;
		Resources resources = context.getResources();
		distanceUnit = resources.getString(R.string.km);
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
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_path, parent,false);
			holder.tvPathDate = (FangTextView) convertView.findViewById(R.id.tv_path_date);
			holder.tvPathDistance = (FangTextView) convertView.findViewById(R.id.tv_path_distance);
			holder.tvPathDistanceUnit = (FangTextView) convertView.findViewById(R.id.tv_path_distance_unit);
			holder.tvPathStartTime = (FangTextView) convertView.findViewById(R.id.tv_path_start_time);
			holder.tvPathTime = (FangTextView) convertView.findViewById(R.id.tv_path_time);
			holder.tvPathType = (FangTextView) convertView.findViewById(R.id.tv_path_type);
			holder.imgPathType = (ImageView) convertView.findViewById(R.id.img_path_type);
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		SportHistory item = datas.get(position);
		String sportDate = item.getSportDate();
		int distance = item.getDistance();
		int consuming = item.getConsuming();
		int type = item.getType();
		String sportTime = item.getSportTime();
		
		String pathDate = sportDate.substring(4, 6) +month + sportDate.substring(6, 8)  + day;
		String pathDistance = DataUtil.format(distance *1.0 / 100);
		String pathDistanceUnit = distanceUnit;
		String pathStartTime =sportTime.substring(0, 2)+":"+sportTime.substring(2, 4)+":"+sportTime.substring(4, 6);
		String pathTime = formateTime(consuming);
		int resId = R.drawable.ic_sport_steps;
		if (type==2) {
			 resId = R.drawable.ic_sport_cycling;
		}else if(type == 1){
			 resId = R.drawable.ic_sport_steps;
		}
		holder.tvPathDate.setText(pathDate);
		holder.tvPathDistance.setText(pathDistance);
		holder.tvPathDistanceUnit.setText(pathDistanceUnit);	
		holder.tvPathStartTime.setText(pathStartTime);
		holder.tvPathTime.setText(pathTime);
		holder.imgPathType.setImageResource(resId);
		

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
	
	public String getString(int value){
		String valueOf = String.valueOf(value);
		if (valueOf.length()==1) {
			return "0"+valueOf;
		}
		return valueOf;
	}
	
	class ViewHolder {
		FangTextView tvPathDate;
		FangTextView tvPathDistance;
		FangTextView tvPathDistanceUnit;
		FangTextView tvPathStartTime;
		FangTextView tvPathTime;
		FangTextView tvPathType;
		ImageView imgPathType;
	}

}
