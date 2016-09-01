package com.mycj.jusd;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.laput.map.offline.OfflineMapCityBean;
import com.laput.map.offline.OfflineMapCityBean.Flag;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class OffLineActivity extends Activity {
	private List<Integer> mCityCodes = new ArrayList<Integer>();
	private MKOfflineMap mkOfflineMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_off_line);
		
		/**
		 * 初始化离线地图
		 */
		initOffLineMap();
		/**
		 * 初始化ListView数据
		 */
		initData();
		/**
		 * 初始化ListView
		 */
		initListView();
		
	}

	private void initOffLineMap() {
		mkOfflineMap = new MKOfflineMap();
		mkOfflineMap.init(new MKOfflineMapListener() {
			
			@Override
			public void onGetOfflineMapState(int type, int state) {
				switch (type) {
				case MKOfflineMap.TYPE_DOWNLOAD_UPDATE:
					MKOLUpdateElement update = mkOfflineMap.getUpdateInfo(state);
					  Log.e("xpl", update.cityName + " ," + update.ratio);  
					  for (OfflineMapCityBean bean : mDatas) {
						if (bean.getCityCode() == state) {
							bean.setProgress(update.ratio);
							bean.setFlag(Flag.DOWNLOADING);
						}
					}
					  adapter.notifyDataSetChanged();
					  
					  
					break;

				default:
					break;
				}
			}
		});
	}
	private ArrayList<OfflineMapCityBean> mDatas = new ArrayList<OfflineMapCityBean>();
	private OffLineCityBeanAdapter adapter;
	private void initData(){
		// 获得所有热门城市
		ArrayList<MKOLSearchRecord> offLineCityList = mkOfflineMap.getHotCityList();
		// 手动添加了西安
		MKOLSearchRecord xian = new MKOLSearchRecord();
		xian.cityID=233;
		xian.cityName ="西安市";
		offLineCityList.add(xian);
		// 获得所有已经下载的城市列表
		ArrayList<MKOLUpdateElement> allUpdateInfo = mkOfflineMap.getAllUpdateInfo();
		//设置所有数据的状态
		for (MKOLSearchRecord record : offLineCityList) {
			OfflineMapCityBean cityBean = new OfflineMapCityBean();
			cityBean.setCityName(record.cityName);
			cityBean.setCityCode(record.cityID);
			if (allUpdateInfo!=null) {
				for (MKOLUpdateElement ele : allUpdateInfo) {
					if (ele.cityID == record.cityID) {
						cityBean.setProgress(ele.ratio);
					}
				}
			}
			mDatas.add(cityBean);
		}
	}
	
	private void initListView(){
		ListView mListView = (ListView) findViewById(R.id.lv);
		adapter = new OffLineCityBeanAdapter();
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int cityId = mDatas.get(position).getCityCode();
				if (mCityCodes.contains(cityId)) {
					removeTaskFromQueue(position,cityId);
				}else{
					addToDownloadQueue(position, cityId);
				}
			}
		});
	}
	
	protected void addToDownloadQueue(int position, int cityId) {
		mCityCodes.add(cityId);
		mkOfflineMap.start(cityId);
		mDatas.get(position).setFlag(Flag.PAUSE);
		adapter.notifyDataSetChanged();
	}

	protected void removeTaskFromQueue(int position, int cityId) {
		mkOfflineMap.pause(cityId);
		mDatas.get(position).setFlag(Flag.NO_STATUS);
		adapter.notifyDataSetChanged();
		
	}

	private class OffLineCityBeanAdapter extends BaseAdapter{
		
		
		@Override
		public boolean isEnabled(int position) {
			if (mDatas.get(position).getProgress() == 100) {
				return false;
			}
			return super.isEnabled(position);
		}

		@Override
		public int getCount() {
			return mDatas.size();
		}

		@Override
		public Object getItem(int position) {
			return mDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			OffLineViewHolder holder = null;
			if (convertView == null) {
				holder = new OffLineViewHolder();
				convertView = LayoutInflater.from(OffLineActivity.this).inflate(R.layout.item_offline_city,parent, false);
				holder.tv = (TextView) convertView.findViewById(R.id.tv);
				holder.tvPb = (TextView) convertView.findViewById(R.id.tv_pb);
				holder.pb = (ProgressBar) convertView.findViewById(R.id.pb);
				convertView.setTag(holder);
						
			}else{
				holder = (OffLineViewHolder) convertView.getTag();
			}
			OfflineMapCityBean bean = mDatas.get(position);
			holder.tv.setText(bean.getCityName());
			int progress = bean.getProgress();
			String progressMsg = "";
			if (progress==0) {
				progressMsg = "未下载";
			}else if(progress == 100){
				bean.setFlag(Flag.NO_STATUS);
				progressMsg = "已下载";
			}else{
				progressMsg = progress + "%";
			}
			
			switch (bean.getFlag()) {
			case PAUSE:
				progressMsg +="[等待下载]";
				break;
			case DOWNLOADING:
				progressMsg +="[正在下载]";
				break;

			default:
				break;
			}
			holder.tvPb.setText(progressMsg);
			holder.pb.setProgress(progress);
			
			return convertView;
		}
		
		public class OffLineViewHolder{
			TextView tv;
			TextView tvPb;
			ProgressBar pb;
		}
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mkOfflineMap.destroy();
	}
	
}
