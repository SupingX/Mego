package com.mycj.jusd.adapter;

import java.util.List;

import com.mycj.jusd.R;
import com.mycj.jusd.bean.news.SleepHistory;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistroySleepInfoAdapter extends BaseAdapter {
	private List<SleepHistory> datas;
	private Context context;
	private String month = "";
	private String day = "";
	private String min;
	public HistroySleepInfoAdapter(Context context, List<SleepHistory> datas){
		this.datas = datas;
		this.context = context;
		Resources resources = context.getResources();
		month = resources.getString(R.string.month);
		day = resources.getString(R.string.day);
		min = resources.getString(R.string.minute);
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
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_history_sleep_new, parent,false);
			holder.tvDate = (TextView) convertView.findViewById(R.id.tv_sleep_date);
			holder.tvSleepTotal = (TextView) convertView.findViewById(R.id.tv_sleep_total);
			holder.tvSleepDeep = (TextView) convertView.findViewById(R.id.tv_sleep_deep);
			holder.tvSleepLight = (TextView) convertView.findViewById(R.id.tv_sleep_light);
			holder.tvSpaceTime = (TextView) convertView.findViewById(R.id.tv_sleep_time);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		bindData(position,holder);
		return convertView;
	}
	
	private void bindData(int position,ViewHolder holder) {
		if (datas != null) {
			SleepHistory info = datas.get(position);
			
			String sleepDate = "";
			String sleepTotal="";
			String sleepDeep = "";
			String sleepLight = "";
			String spaceTime = "";
			
			// 日期
			String sleepDateAll = info.getSleepDate();
			sleepDate = sleepDateAll.substring(4, 6) +month + sleepDateAll.substring(6, 8)  + day;
			// 深睡
			int deepTime = info.getSleepDeep();
			sleepDeep = String.valueOf(deepTime)+ min;
			// 浅睡
			int lightTime = info.getSleepLight();
			sleepLight = String.valueOf(lightTime)+ min;
			// 总的睡眠时间
			int totalTime = deepTime + lightTime;
			sleepTotal = String.valueOf(totalTime) + min;
			// 睡眠区间
	        String startTime = info.getStartTime();
			String endTime = info.getEndTime();
			String startTimeStr = startTime.substring(0, 2)+":"+startTime.substring(2, 4);
			String endTimeStr =endTime.substring(0, 2)+":"+endTime.substring(2, 4);
			spaceTime = startTimeStr + " - " + endTimeStr;
			
			holder.tvDate.setText(sleepDate);
			holder.tvSleepTotal.setText(sleepTotal);
			holder.tvSleepDeep.setText(sleepDeep);
			holder.tvSleepLight.setText(sleepLight);
			holder.tvSpaceTime.setText(spaceTime);
			
			
		}
	}

	class ViewHolder {
		TextView tvDate;
		TextView tvSleepTotal;
		TextView tvSleepDeep;
		TextView tvSleepLight;
		TextView tvSpaceTime;//区间
	}  

}
