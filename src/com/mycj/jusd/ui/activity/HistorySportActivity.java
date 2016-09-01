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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.mycj.jusd.PathStatisticsActivity;
import com.mycj.jusd.R;
import com.mycj.jusd.StepStatisticsActivity;
import com.mycj.jusd.adapter.HistroyStepInfoAdapter;
import com.mycj.jusd.adapter.SportPathHistoryAdapter;
import com.mycj.jusd.base.BaseActivity;
import com.mycj.jusd.bean.LitePalManager;
import com.mycj.jusd.bean.JunConstant;
import com.mycj.jusd.bean.news.SportHistory;
import com.mycj.jusd.util.DataUtil;
import com.mycj.jusd.util.DateUtil;
import com.mycj.jusd.util.ShareUtil;
import com.mycj.jusd.view.FangTextView;
import com.mycj.jusd.view.XplAlertDialog;

public class HistorySportActivity extends BaseActivity implements OnClickListener {
	protected static final int MSG_SPORT_DATA = 0xb1;
	protected static final int MSG_HISTROY_STEP = 0xb2;
	protected static final int MSG_HISTROY_PATH = 0xb3;
	protected static final String KEY_SPORT_DATA = "KEY_SPORT_DATA";
	private ImageView imgBack, imgShare;
	private FangTextView tvUp, tvDown, tvSportDate, tvSportSteps,
			tvSportDistance, tvSportCount, tvSportTime, tvSportConsume;
	private LinearLayout llSportTime, llSportDistance, llSportConsume;
	private ListView lvHistoryStep;
	private Date toMonth;
	private LitePalManager litePalManager;
	private LinearLayout llSportSteps;
	private boolean isLoading1 = false, isLoading2 = false;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case JunConstant.MSG_SHARE:
				String path = (String) msg.obj;
				ShareUtil.shareImage(path, HistorySportActivity.this,
						getString(R.string.share));
				break;
			case MSG_SPORT_DATA:
				Bundle data = msg.getData();
				int[] sportData = data.getIntArray(KEY_SPORT_DATA);
				int count = sportData[0];
				int sportSteps = sportData[1];
				int time = sportData[2];// 单位：秒
				int sportDistance = sportData[3];
				int sportKal = sportData[4];
				String sportTime = DataUtil.format(time / (60f*60));
				tvSportCount.setText(String.valueOf(count)
						+ getString(R.string.times));
				tvSportSteps.setText(String.valueOf(sportSteps)
						+ getString(R.string.step));
				tvSportTime.setText(sportTime + getString(R.string.hour));
				tvSportDistance.setText(sportDistance*1.0f/100 + getString(R.string.km));
				tvSportConsume.setText(sportKal*1.0f/100 + getString(R.string.kcal));
				isLoading1 = false;
				break;
			case MSG_HISTROY_STEP:
				ArrayList<SportHistory> sports = (ArrayList<SportHistory>) msg.obj;
				if (sports == null || sports.size() == 0) {
					datasNew.clear();
					// getTestData();
				} else {
					datasNew.clear();
					datasNew.addAll(sports);
				}
				adapterNew.notifyDataSetChanged();
				isLoading2 = false;
				mPtrFrame.refreshComplete();
				break;
			case MSG_HISTROY_PATH:
				ArrayList<SportHistory> pathInfo = (ArrayList<SportHistory>) msg.obj;
				if (pathInfo == null || pathInfo.size() == 0) {
					datasPath.clear();
					// getTestData();
				} else {
					datasPath.clear();
					datasPath.addAll(pathInfo);
				}
				isLoading1 = false;
				// closeLoadingDialog();
				adapterPath.notifyDataSetChanged();
				mPtrFrame.refreshComplete();

				break;
			default:
				break;
			}

		};
	};
	private RadioButton rbStep;
	private RadioButton rbPath;
	private RadioGroup rgCountType;
	private ArrayList<SportHistory> datasNew;
	private HistroyStepInfoAdapter adapterNew;
	private SportPathHistoryAdapter adapterPath;
	private ListView lvHistoryPath;
	private ArrayList<SportHistory> datasPath;
	private long old;
	private XplAlertDialog loadingDialog;
	private PtrClassicFrameLayout mPtrFrame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_sport);
		litePalManager = LitePalManager.instance();
		initViews();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.img_share:
			// toast("分享");
			share(mHandler);
			break;
		case R.id.up:
			toMonth = DateUtil.getDateOfDiffMonth(toMonth, -1);
//			loadSportDataByDateNew(toMonth,
//					rgCountType.getCheckedRadioButtonId());
			mPtrFrame.autoRefresh();
			if (!isLoading1 && !isLoading2) {
			
			}
			break;
		case R.id.down:
			toMonth = DateUtil.getDateOfDiffMonth(toMonth, 1);
//			Log.e("", "toMonth :" + DateUtil.dateToString(toMonth, "yyyy-MM-dd"));
/*			loadSportDataByDateNew(toMonth,
					rgCountType.getCheckedRadioButtonId());*/
			mPtrFrame.autoRefresh();
			if (!isLoading1 && !isLoading2) {
			
			}
			break;
		case R.id.ll_sport_steps:
		case R.id.ll_sport_time:
		case R.id.ll_sport_distance:
		case R.id.ll_sport_consume:
			intent = new Intent(HistorySportActivity.this, CountActivity.class);
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
		tvSportDate = (FangTextView) findViewById(R.id.tv_date);
		tvSportCount = (FangTextView) findViewById(R.id.tv_sport_count);
		tvSportSteps = (FangTextView) findViewById(R.id.tv_path_step);
		tvSportTime = (FangTextView) findViewById(R.id.tv_sport_time);
		tvSportDistance = (FangTextView) findViewById(R.id.tv_sport_distance);
		tvSportConsume = (FangTextView) findViewById(R.id.tv_sport_consume);

		// 计步 or 轨迹
		rgCountType = (RadioGroup) findViewById(R.id.rg_count_type);
		rbStep = (RadioButton) findViewById(R.id.rb_step);
		rbPath = (RadioButton) findViewById(R.id.rb_path);
		rbStep.setChecked(true);

		llSportSteps = (LinearLayout) findViewById(R.id.ll_sport_steps);
		llSportTime = (LinearLayout) findViewById(R.id.ll_sport_time);
		llSportDistance = (LinearLayout) findViewById(R.id.ll_sport_distance);
		llSportConsume = (LinearLayout) findViewById(R.id.ll_sport_consume);
		// listView
		lvHistoryStep = (ListView) findViewById(R.id.lv_history_sport);
		lvHistoryPath = (ListView) findViewById(R.id.lv_history_path);
		// //日期
		toMonth = new Date();

		/*
		 * datas = new ArrayList<StepHistorySport>(); adapter = new
		 * HistroySportAdapter(datas); lvHistorySport.setAdapter(adapter);
		 * loadSportDataByDate(toMonth);
		 */

		datasNew = new ArrayList<SportHistory>();
		adapterNew = new HistroyStepInfoAdapter(this, datasNew);
		lvHistoryStep.setAdapter(adapterNew);

		datasPath = new ArrayList<SportHistory>();
		adapterPath = new SportPathHistoryAdapter(this, datasPath);
		lvHistoryPath.setAdapter(adapterPath);
		initpullToRefreshUi();
//		loadSportDataByDateNew(toMonth, rgCountType.getCheckedRadioButtonId());

		rgCountType.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.rb_step) {
					toast("计步统计中...");

				} else if (checkedId == R.id.rb_path) {
					toast("轨迹统计中...");
				}
				loadSportDataByDateNew(toMonth, checkedId);
			}
		});

		rbStep.setChecked(true);
		lvHistoryPath.setVisibility(View.GONE);

		// listener
		imgBack.setOnClickListener(this);
		imgShare.setOnClickListener(this);
		tvUp.setOnClickListener(this);
		tvDown.setOnClickListener(this);
		llSportSteps.setOnClickListener(this);
		llSportTime.setOnClickListener(this);
		llSportDistance.setOnClickListener(this);
		llSportConsume.setOnClickListener(this);
		lvHistoryStep.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(HistorySportActivity.this,
						StepStatisticsActivity.class);
				SportHistory historySport = datasNew.get(arg2);
				Bundle data = new Bundle();
		
				data.putParcelable(	JunConstant.INTENT_SPORT_HISTORY, historySport);
				intent.putExtras(data);
				startActivity(intent);
			}
		});
		lvHistoryPath.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(HistorySportActivity.this,
						PathStatisticsActivity.class);
				SportHistory historySport = datasPath.get(arg2);
				Bundle data = new Bundle();
				data.putParcelable(	JunConstant.INTENT_SPORT_HISTORY, historySport);
				intent.putExtras(data);
				startActivity(intent);
			}
		});
	}

	/*
	 * private void loadSportDataByDate(final Date date) {
	 * tvSportDate.setText(DateUtil.dateToString(date, "yyyy/MM")); //
	 * 数据库查询<放在一个Thread里>查询头部信息 Thread sportThread = new Thread(new Runnable() {
	 * 
	 * @Override public void run() { isLoading1 = true; int count =
	 * litePalManager.findHistroySportCountByDate(date); int sportSteps =
	 * litePalManager.findHistroySportStepsByDate(date); int time =
	 * litePalManager.findHistroySportTimeByDate(date);// 单位秒 Message msg =
	 * mHandler.obtainMessage(); msg.what = MSG_SPORT_DATA; Bundle b = new
	 * Bundle(); b.putIntArray(KEY_SPORT_DATA, new int[] { count, sportSteps,
	 * time }); msg.setData(b); mHandler.sendMessage(msg); } });
	 * sportThread.start(); // 查询listview Thread sportHistroyThead = new
	 * Thread(new Runnable() {
	 * 
	 * @Override public void run() { isLoading2 = true; List<StepHistorySport>
	 * result = litePalManager.findHistroySportByDate(date); Message msg =
	 * mHandler.obtainMessage(); msg.what = MSG_SPORT_HISTROY; msg.obj = result;
	 * mHandler.sendMessage(msg); } }); sportHistroyThead.start(); }
	 */
	private void loadSportDataByDateNew(final Date date, int checkedId) {
		mPtrFrame.autoRefresh();
		tvSportDate.setText(DateUtil.dateToString(date, "yyyy/MM"));
		
		// 《计步数据》
		if (rbStep.getId() == checkedId) {
			llSportSteps.setVisibility(View.VISIBLE);
			lvHistoryStep.setVisibility(View.VISIBLE);
			lvHistoryPath.setVisibility(View.GONE);

			// 数据库查询<放在一个Thread里>查询头部信息
			Thread sportThread = new Thread(new Runnable() {
				@Override
				public void run() {
					isLoading1 = true;
					int count = litePalManager
							.getSportHistoryTotalCountByMonth(date);
					int sportSteps = litePalManager
							.getSportHistoryTotalStepByMonth(date);
					int time = litePalManager
							.getSportHistoryTotalConsumingByMonth(date);// 单位秒
					int distance = litePalManager
							.getSportHistoryTotalDistanceByMonth(date);// 单位qianmi
					int calorie = litePalManager
							.getSportHistoryTotalCalorieByMonth(date);// 单位qianka

					Message msg = mHandler.obtainMessage();
					msg.what = MSG_SPORT_DATA;
					Bundle b = new Bundle();
					b.putIntArray(KEY_SPORT_DATA, new int[] { count,
							sportSteps, time, distance, calorie });
					msg.setData(b);
					mHandler.sendMessage(msg);
				}
			});
			sportThread.start();
			// 查询listview
			Thread sportHistroyThead = new Thread(new Runnable() {
				@Override
				public void run() {
					isLoading2 = true;
					List<SportHistory> result = litePalManager
							.getSportHistoryListByMonth(date);
					Message msg = mHandler.obtainMessage();
					msg.what = MSG_HISTROY_STEP;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			});
			sportHistroyThead.start();

			// 《轨迹数据》
		} else if (rbPath.getId() == checkedId) {

			llSportSteps.setVisibility(View.VISIBLE);
			lvHistoryStep.setVisibility(View.GONE);
			lvHistoryPath.setVisibility(View.VISIBLE);

			// 数据库查询<放在一个Thread里>查询头部信息
			// showLoadingDialog();
			isLoading1 = true;
			Thread sportThread = new Thread(new Runnable() {
				@Override
				public void run() {
					int count = litePalManager
							.getSportHistoryTotalCountByMonthForPath(date);
					int sportSteps = litePalManager
							.getSportHistoryTotalStepByMonthForPath(date);
					int time = litePalManager
							.getSportHistoryTotalConsumingByMonthForPath(date);// 单位秒
					int distance = litePalManager
							.getSportHistoryTotalDistanceByMonthForPath(date);// 单位qianmi
					int calorie = litePalManager
							.getSportHistoryTotalCalorieByMonthForPath(date);// 单位qianka

					Message msg = mHandler.obtainMessage();
					msg.what = MSG_SPORT_DATA;
					Bundle b = new Bundle();
					b.putIntArray(KEY_SPORT_DATA, new int[] { count,
							sportSteps, time, distance, calorie });
					msg.setData(b);
					mHandler.sendMessage(msg);
				}
			});
			sportThread.start();
			// 查询listview
			Thread sportHistroyThead = new Thread(new Runnable() {

				@Override
				public void run() {
					// 需要分页 加载 ！！！！！！！！！
					List<SportHistory> result = litePalManager
							.getSportHistoryListByMonthForPath(date);
					if (result != null) {
						Log.e("zeej",
								"________________result 大小 " + result.size());
					}
					Message msg = mHandler.obtainMessage();
					msg.what = MSG_HISTROY_PATH;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			});
			sportHistroyThead.start();
		}

	}

	private void showLoadingDialog() {
		old = System.currentTimeMillis();
		if (loadingDialog == null) {
			loadingDialog = showXplDialog("正在加载...");
		}
		if (loadingDialog != null && !loadingDialog.isShowing()) {
			loadingDialog.show();
		}
	}

	private void closeLoadingDialog() {
		if (!isLoading1) {
			long now = System.currentTimeMillis();
			long deff = now - old;
			if (deff < 1000) {

				if (loadingDialog != null) {
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							loadingDialog.dismiss();
						}
					}, 1000);
				}
			} else {
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
			}
		}
	}

	public void openMoreInfo(View v) {
		Intent intent = new Intent(HistorySportActivity.this,
				CountActivity.class);
		startActivity(intent);
	}

	// Test
	private void getTestData() {
		/*
		 * for (int i = 0; i < Math.random() * 100; i++) { datas.add(new
		 * StepHistorySport()); }
		 */
	}

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
						rgCountType.getCheckedRadioButtonId() == R.id.rb_step ? lvHistoryStep
								: lvHistoryPath, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				loadSportDataByDateNew(toMonth,
						rgCountType.getCheckedRadioButtonId());
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
