package com.mycj.jusd.ui.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mycj.jusd.R;
import com.mycj.jusd.adapter.HistroySleepInfoAdapter;
import com.mycj.jusd.base.BaseActivity;
import com.mycj.jusd.bean.LitePalManager;
import com.mycj.jusd.bean.JunConstant;
import com.mycj.jusd.bean.news.SleepHistory;
import com.mycj.jusd.util.DataUtil;
import com.mycj.jusd.util.DateUtil;
import com.mycj.jusd.util.MilkUtil;
import com.mycj.jusd.util.ShareUtil;
import com.mycj.jusd.view.FangTextView;

public class HistorySleepActivity extends BaseActivity implements OnClickListener {


	protected static final int MSG_SLEEP_DATA = 0x0a;
	protected static final String KEY_SLEEP_DATA = "KEY_SLEEP_DATA";
	protected static final int MSG_SLEEP_HISTROY = 0x0b;
	protected static final int MSG_SLEEP_HISTROY_NEW = 0x0c;
	private ImageView imgBack;
	private ImageView imgShare;
	private FangTextView tvUp;
	private FangTextView tvDown;
	private FangTextView tvSleepDate;
	private FangTextView tvSleepSteps;
	private FangTextView tvSleepTime;
	private FangTextView tvSleepDeep;
	private FangTextView tvSleepLight;
	private LinearLayout llSleepTime;
	private LinearLayout llSleepDeep;
	private LinearLayout llSleepLight;
	private ListView lvHistorySleep;
	private Date toMonth;
	private LitePalManager litePalManager;
	private boolean isLoading1 = false;
	private boolean isLoading2 = false;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case JunConstant.MSG_SHARE:
				String path = (String) msg.obj;
				ShareUtil.shareImage(path, HistorySleepActivity.this, getString(R.string.share));
				break;
			case MSG_SLEEP_DATA:
				// 设置头部数据<统计的次数、时间、深睡、浅睡>
				Bundle data = msg.getData();
				float[] sleepData = data.getFloatArray(KEY_SLEEP_DATA);
				String times = getString(R.string.times);
				String hour = getString(R.string.hour);
				tvSleepSteps.setText(String.valueOf((int)sleepData[0]) + times);
				tvSleepTime.setText(DataUtil.format(sleepData[1] / (60 )) + hour);
				tvSleepDeep.setText(DataUtil.format(sleepData[2] / (60)) + hour);
				tvSleepLight.setText(DataUtil.format(sleepData[3] / (60 )) + hour);
				isLoading1 = false;
				break;
			case MSG_SLEEP_HISTROY:
				/*
					 // 设置睡眠统计数据<listView>
					ArrayList<HistorySleep> findHistroySleepByDate = (ArrayList<HistorySleep>) msg.obj;
					if (findHistroySleepByDate == null || findHistroySleepByDate.size()==0) {
						datas.clear();
						getTestData();// 测试
						
					} else {
						datas.clear();
						datas.addAll(findHistroySleepByDate);
					}
					adapter.notifyDataSetChanged();
					isLoading2 = false;
				*/
			case MSG_SLEEP_HISTROY_NEW:
				// 设置睡眠统计数据<listView>
				ArrayList<SleepHistory> findHistroySleepByDateNew = (ArrayList<SleepHistory>) msg.obj;
				if (findHistroySleepByDateNew == null || findHistroySleepByDateNew.size()==0) {
					datasNew.clear();
//					getTestData();// 测试
					
				} else {
					datasNew.clear();
					datasNew.addAll(findHistroySleepByDateNew);
				}
				adapterNew.notifyDataSetChanged();
				mPtrFrame.refreshComplete();
				isLoading2 = false;
				break;
			default:
				break;
			}

		};
	};
	private ArrayList<SleepHistory> datasNew;
	private HistroySleepInfoAdapter adapterNew;
	private PtrClassicFrameLayout mPtrFrame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_sleep);
		litePalManager = LitePalManager.instance();
		initViews();
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.img_share:
			// toast("分享");
			share(mHandler);
			break;
		case R.id.up:
			if (!isLoading1 && !isLoading2) {
				toMonth = DateUtil.getDateOfDiffMonth(toMonth, -1);
//				mHandler.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						mPtrFrame.autoRefresh();
//						
//					}
//				}, 100);
				loadSleepDataByDateNew(toMonth);
			}
			
			break;
		case R.id.down:
			if (!isLoading1 && !isLoading2) {
				toMonth = DateUtil.getDateOfDiffMonth(toMonth, 1);
			/*	mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						mPtrFrame.autoRefresh();
						
					}
				}, 100);*/
				loadSleepDataByDateNew(toMonth);
			}
			break;
		case R.id.ll_sleep_time:
			intent = new Intent(HistorySleepActivity.this, CountActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_sleep_deep:
			intent = new Intent(HistorySleepActivity.this, CountActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_sleep_light:
			intent = new Intent(HistorySleepActivity.this, CountActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	private void initViews() {
		imgBack = (ImageView) findViewById(R.id.img_back);
		imgShare = (ImageView) findViewById(R.id.img_share);

		tvUp = (FangTextView) findViewById(R.id.up);
		tvDown = (FangTextView) findViewById(R.id.down);
		tvSleepDate = (FangTextView) findViewById(R.id.tv_date);
		tvSleepSteps = (FangTextView) findViewById(R.id.tv_sleep_steps);
		tvSleepTime = (FangTextView) findViewById(R.id.tv_sleep_time);
		tvSleepDeep = (FangTextView) findViewById(R.id.tv_sleep_deep);
		tvSleepLight = (FangTextView) findViewById(R.id.tv_sleep_light);
		llSleepTime = (LinearLayout) findViewById(R.id.ll_sleep_time);
		llSleepDeep = (LinearLayout) findViewById(R.id.ll_sleep_deep);
		llSleepLight = (LinearLayout) findViewById(R.id.ll_sleep_light);
		// listView
		lvHistorySleep = (ListView) findViewById(R.id.lv_history_sleep);
		// 日期
		toMonth = new Date();
		
	/*	datas = new ArrayList<HistorySleep>();
		adapter = new HistroySleepAdapter(datas);
		lvHistorySleep.setAdapter(adapter);
		loadSleepDataByDate(toMonth);*/
		
		datasNew = new ArrayList<SleepHistory>();
		adapterNew = new HistroySleepInfoAdapter(this,datasNew);
		lvHistorySleep.setAdapter(adapterNew);
//		loadSleepDataByDateNew(toMonth);
		initpullToRefreshUi();
		// listener
		imgBack.setOnClickListener(this);
		imgShare.setOnClickListener(this);
		tvUp.setOnClickListener(this);
		tvDown.setOnClickListener(this);
		llSleepTime.setOnClickListener(this);
		llSleepDeep.setOnClickListener(this);
		llSleepLight.setOnClickListener(this);

	}

	private void loadSleepDataByDate(final Date date) {
		/*tvSleepDate.setText(DateUtil.dateToString(date, "yyyy/MM"));
		// 数据库查询<放在一个Thread里> 头部信息
		Thread sleepThread = new Thread(new Runnable() {
			@Override
			public void run() {
				isLoading1 = true;
				float sleepSteps = litePalManager.findHistroySleepCount(date);// 翻身次数
				float sleepDeep = litePalManager.findHistroySleepDeepByDate(date);
				float sleepLight = litePalManager.findHistroySleepLightByDate(date);
				float sleepTime = sleepDeep + sleepLight;
				Message msg = mHandler.obtainMessage();
				msg.what = MSG_SLEEP_DATA;
				Bundle b = new Bundle();
				b.putFloatArray(KEY_SLEEP_DATA, new float[] { sleepSteps, sleepTime, sleepDeep, sleepLight });
				msg.setData(b);
				mHandler.sendMessage(msg);
			}
		});
		sleepThread.start();
		// listView
		Thread sleepHistroyThead = new Thread(new Runnable() {

			@Override
			public void run() {
				isLoading2 = true;
				List<HistorySleep> findHistroySleepByDate = litePalManager.findHistroySleepByDate(date);
				Message msg = mHandler.obtainMessage();
				msg.what = MSG_SLEEP_HISTROY;
				msg.obj = findHistroySleepByDate;
				mHandler.sendMessage(msg);
			}
		});
		sleepHistroyThead.start();*/
	}
	
	private void loadSleepDataByDateNew(final Date date) {
		mPtrFrame.autoRefresh();
		tvSleepDate.setText(DateUtil.dateToString(date, "yyyy/MM"));
		// 数据库查询<放在一个Thread里> 头部信息
		Thread sleepThread = new Thread(new Runnable() {
			@Override
			public void run() {
				isLoading1 = true;
				float sleepCount = litePalManager.findHistroySleepCountNew(date);// 翻身次数
				float sleepDeep = litePalManager.findHistroySleepDeepByDateNew(date);
				float sleepLight = litePalManager.findHistroySleepLightByDateNew(date);
				float sleepTime = sleepDeep + sleepLight;
				Message msg = mHandler.obtainMessage();
				msg.what = MSG_SLEEP_DATA;
				Bundle b = new Bundle();
				b.putFloatArray(KEY_SLEEP_DATA, new float[] { sleepCount, sleepTime, sleepDeep, sleepLight });
				msg.setData(b);
				mHandler.sendMessage(msg);
			}
		});
		sleepThread.start();
		// listView
		Thread sleepHistroyThead = new Thread(new Runnable() {

			@Override
			public void run() {
				isLoading2 = true;
				List<SleepHistory> findHistroySleepByDate = litePalManager.findHistroySleepByDateNew(date);
				Message msg = mHandler.obtainMessage();
				msg.what = MSG_SLEEP_HISTROY_NEW;
				msg.obj = findHistroySleepByDate;
				mHandler.sendMessage(msg);
			}
		});
		sleepHistroyThead.start();
	}
	
	
	public void openMoreInfo(View v){
		Intent intent = new Intent(HistorySleepActivity.this, CountActivity.class);
		startActivity(intent);
	}
	
//	// Test
//	private void getTestData() {
//		MilkUtil.saveHistorySleepInfoList();
//	}
	
	private void initpullToRefreshUi() {
		mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.list_view_with_empty_view_fragment_ptr_frame);
		mPtrFrame.postDelayed(new Runnable() {

			@Override
			public void run() {
				mPtrFrame.autoRefresh();
			}
		}, 100);

		/*
		 * mListView.setOnItemClickListener(new
		 * AdapterView.OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) { if (position >= 0) { final String url =
		 * mAdapter.getItem(position).optString("pic"); if
		 * (!TextUtils.isEmpty(url)) {
		 * getContext().pushFragmentToBackStack(MaterialStyleFragment.class,
		 * url); } } } });
		 * 
		 * // show empty view mPtrFrame.setVisibility(View.INVISIBLE);
		 * mTextView.setVisibility(View.VISIBLE);
		 * 
		 * mAdapter = new ListViewDataAdapter<JsonData>();
		 * mAdapter.setViewHolderClass(this, ViewHolder.class);
		 * mListView.setAdapter(mAdapter);
		 */

		mPtrFrame.setLastUpdateTimeRelateObject(this);
		mPtrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {

				// here check $mListView instead of $content
				return PtrDefaultHandler.checkContentCanBePulledDown(
						frame,
						lvHistorySleep, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				loadSleepDataByDateNew(toMonth);
			}
		});
		// the following are default settings
		mPtrFrame.setResistance(1.7f);
		mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
		mPtrFrame.setDurationToClose(200);
		mPtrFrame.setDurationToCloseHeader(1000);
		// default is false
		mPtrFrame.setPullToRefresh(false);
		// default is true
		mPtrFrame.setKeepHeaderWhenRefresh(true);
	}
}