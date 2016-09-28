package com.mycj.jusd.ui.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.laputa.blue.util.XLog;
import com.mycj.jusd.R;
import com.mycj.jusd.base.BaseActivity;
import com.mycj.jusd.bean.LitePalManager;
import com.mycj.jusd.bean.JunConstant;
import com.mycj.jusd.bean.news.SleepHistory;
import com.mycj.jusd.bean.news.SportHistory;
import com.mycj.jusd.util.DataUtil;
import com.mycj.jusd.util.DateUtil;
import com.mycj.jusd.util.ShareUtil;
import com.mycj.jusd.view.CountView;
import com.mycj.jusd.view.CountView.FormatType;
import com.mycj.jusd.view.FangRadioButton;
import com.mycj.jusd.view.FangTextView;
import com.mycj.jusd.view.PopMoreView;
import com.mycj.jusd.view.PopMoreView.OnPopClickListener;
import com.mycj.jusd.view.XplAlertDialog;

public class CountActivity extends BaseActivity implements OnClickListener,
		OnCheckedChangeListener {
	private final static String TAG = CountActivity.class.getSimpleName();
	private final static int MSG_SLEEP_DATA = 0x01;
	private final static int MSG_SPORT_DATA = 0x02;
	private final static int MSG_SPORT_DATA_NEW = 0xEE;
	private CountView countSport;
	private ImageView imgBack;
	private ImageView imgMore;
	
	private FangTextView tvUp;
	private FangTextView tvDown;
	private TextView tvSportValue;
	private FangTextView tvSportUnit;
	private RadioGroup rgCountSport;
	private FangRadioButton rbSportStep;
	private FangRadioButton rbSportTime;
	private FangRadioButton rbSportDistance;
	private FangRadioButton rbSportCal;
	private TextView tvSleepValue;
	private FangTextView tvSleepUnit;
	private RadioGroup rgCountSleep;
	private FangRadioButton rbSleepDeep;
	private FangRadioButton rbSleepLight;
	private CountView countSleep;
	private Date monthDate;
	private FangTextView tvDate;
	private PopMoreView pop;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case JunConstant.MSG_SHARE:
				String path = (String) msg.obj;
				ShareUtil.shareImage(path, CountActivity.this, "分享");
				break;
			case MSG_SLEEP_DATA:
				isLoadSleepOver =true;
				closeLoadingDialog();
				float[] sleepDatas = (float[]) msg.obj;
				countSleep.setDatas(sleepDatas);
				String sleepUnit = msg.getData().getString("sleeptUnit");
				// 7.设置平均统计
				String sleepAvgValue = getAvgValue(sleepDatas);
				String sleepAvgUnit = sleepUnit + "/天";
				tvSleepValue.setText(sleepAvgValue);
				tvSleepUnit.setText(sleepAvgUnit);
				break;
			case MSG_SPORT_DATA:
				float[] sportDatas = (float[]) msg.obj;
				Log.e(TAG, "获取运动信息成功，更新UI --" + sportDatas.length);
				countSport.setDatas(sportDatas);
				Bundle data = msg.getData();
				String sportAvgValue = data.getString("sportAvgValue");
				String sportAvgUnit = data.getString("sportAvgUnit");
				tvSportValue.setText(sportAvgValue);
				tvSportUnit.setText(sportAvgUnit);
				break;
				
			case MSG_SPORT_DATA_NEW:
				isLoadSportOver = true;
				closeLoadingDialog();
				Bundle bundle = msg.getData();
				float[] steps = bundle.getFloatArray("steps");
				float[] consumings = bundle.getFloatArray("consumings");
				float[] distances = bundle.getFloatArray("distances");
				float[] calories = bundle.getFloatArray("calories");
				XLog.e("CountActivity"," steps : "+ steps.length);
				XLog.e("CountActivity"," consumings : "+ consumings.length);
				XLog.e("CountActivity"," distances : "+ distances.length);
				XLog.e("CountActivity"," calories : "+ calories.length);
				float[] datas = null;
				// 3.确定radioButton对应得值
				float sportMaxValue = 100;// 最大值
				String sportUnit = "";// 单位
				FormatType f = FormatType.FORMAT_0;// 格式化数字
				switch (rgCountSport.getCheckedRadioButtonId()) {
				case R.id.rb_sport_step:
					sportUnit = "步";
					sportMaxValue = JunConstant.MAX_STEP;
					datas = steps;
					float maxDataStep = getMaxData(datas);
					if (maxDataStep <=1000 ) {
						sportMaxValue = 1000;
					}else if (maxDataStep>1000 && maxDataStep<=5000) {
						sportMaxValue = 5000;
					}else if (maxDataStep>5000 && maxDataStep<=10000) {
						sportMaxValue = 10000;
					}else if (maxDataStep>10000 && maxDataStep<=30000) {
						sportMaxValue = 30000;
					}
					break;
					// wifif ijifi ijija alafo
					// 
					
				case R.id.rb_sport_time:
					sportUnit = "分钟";
					sportMaxValue = JunConstant.MAX_TIME;
					datas = consumings;
					float maxDataTime = getMaxData(datas);
					if (maxDataTime <=30 ) {
						sportMaxValue = 30;
					}else if (maxDataTime>30 && maxDataTime<=120) {
						sportMaxValue = 120;
					}else if (maxDataTime>120 && maxDataTime<=360) {
						sportMaxValue = 360;
					}else if (maxDataTime>360 && maxDataTime<=720) {
						sportMaxValue = 720;
					}
					break;
				case R.id.rb_sport_distance:
					sportUnit = "千米";
					f = FormatType.FORMAT_2;
					datas = distances;
					float maxData = getMaxData(datas);
					sportMaxValue = JunConstant.MAX_DISTANCE;
					if (maxData <=1 ) {
						sportMaxValue = 1;
					}else if (maxData>1 && maxData<=5) {
						sportMaxValue = 5;
					}else if (maxData>5 && maxData<=20) {
						sportMaxValue = 20;
					}else if (maxData>20 && maxData<=100) {
						sportMaxValue = 100;
					}
					break;
				case R.id.rb_sport_cal:
					sportUnit = "千卡";
					sportMaxValue = JunConstant.MAX_CALORIE;
					datas = calories;
					float maxDataCal = getMaxData(datas);
					if (maxDataCal <=1 ) {
						sportMaxValue = 1;
					}else if (maxDataCal>1 && maxDataCal<=5) {
						sportMaxValue = 5;
					}else if (maxDataCal>5 && maxDataCal<=20) {
						sportMaxValue = 20;
					}else if (maxDataCal>20 && maxDataCal<=100) {
						sportMaxValue = 100;
					}

				}
				
				// 设置图标
				countSport.setMaxValue(sportMaxValue);
				countSport.setUnit(sportUnit);
				countSport.setFormat(f);
				countSport.setDatas(datas);
		
//				tvSportValue.setText(sportAvgValue);
//				tvSportUnit.setText(sportAvgUnit);
				break;
			default:
				break;
			}

		}

	
	};
	private RadioButton rbStep;
	private RadioButton rbPath;
	private FangRadioButton rbSleepTotal;
	private long old;
	private XplAlertDialog loadingDialog;
	private boolean isLoadSportOver;
	private boolean isLoadSleepOver;
	private float getMaxData(float[] datas) {
		float max = 0;
		for (int i = 0; i < datas.length; i++) {
			max = Math.max(max, datas[i]);
		}
		return max;
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_count);
		monthDate = new Date();
		initViews();
		initValues(monthDate);
	}

	private void initViews() {

		imgBack = (ImageView) findViewById(R.id.img_back);
		imgMore = (ImageView) findViewById(R.id.img_more);
		tvUp = (FangTextView) findViewById(R.id.up);
		tvDown = (FangTextView) findViewById(R.id.down);
		tvDate = (FangTextView) findViewById(R.id.tv_date);
		tvSportValue = (TextView) findViewById(R.id.tv_sport_value);
		tvSportUnit = (FangTextView) findViewById(R.id.tv_sport_unit);

		rgCountSport = (RadioGroup) findViewById(R.id.rg_count_sport);
		rbSportStep = (FangRadioButton) findViewById(R.id.rb_sport_step);
		rbSportTime = (FangRadioButton) findViewById(R.id.rb_sport_time);
		rbSportDistance = (FangRadioButton) findViewById(R.id.rb_sport_distance);
		rbSportCal = (FangRadioButton) findViewById(R.id.rb_sport_cal);
		countSport = (CountView) findViewById(R.id.count_sport);
		tvSleepValue = (TextView) findViewById(R.id.tv_sleep_value);
		tvSleepUnit = (FangTextView) findViewById(R.id.tv_sleep_unit);
		rgCountSleep = (RadioGroup) findViewById(R.id.rg_count_sleep);
		rbSleepTotal = (FangRadioButton) findViewById(R.id.rb_sleep_total);
		rbSleepDeep = (FangRadioButton) findViewById(R.id.rb_sleep_deep);
		rbSleepLight = (FangRadioButton) findViewById(R.id.rb_sleep_light);
		countSleep = (CountView) findViewById(R.id.count_sleep);
		tvUp.setOnClickListener(this);
		imgBack.setOnClickListener(this);
		imgMore.setOnClickListener(this);
		tvDown.setOnClickListener(this);
		rgCountSport.setOnCheckedChangeListener(this);
		rgCountSleep.setOnCheckedChangeListener(this);
		rbSportStep.setChecked(true);
		rbSleepDeep.setChecked(true);

	}

	private void showLoadingDialog(){
		old = System.currentTimeMillis();
		if (loadingDialog==null) {
			loadingDialog = showXplDialog("正在加载...");
		}
		if (loadingDialog!=null && !loadingDialog.isShowing()) {
			loadingDialog.show();
		}
	}
	
	private void closeLoadingDialog() {
		if (isLoadSportOver && isLoadSleepOver) {
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
	
	private void initValues(Date date) {
		showLoadingDialog();
		tvDate.setText(DateUtil.dateToString(date, "yyyy/MM"));
		new Thread(loadSleepDataRunnable).start();
		new Thread(loadSportDataRunnable).start();
	}
	
	// 加载睡眠数据
	private Runnable loadSleepDataRunnable = new Runnable() {
		@Override
		public void run() {
			initSleepValues(monthDate);
		}
	};
    
	// 加载运动数据
	private Runnable loadSportDataRunnable = new Runnable() {
		@Override
		public void run() {
			initSportValuesNew(monthDate);
		}
	};

	private void initSleepValues(final Date date) {
/*		new Thread(new Runnable() {
			@Override
			public void run() {*/
				// TODO Auto-generated method stub
				// 1.一个月的天数
				int monthMaxDay = DateUtil.getMonthMaxDay(date);
				// 2.新建数据
				final float[] sleepDatas = new float[monthMaxDay];
				// 3.确定radioButton对应得值
				float sleepMaxValue = JunConstant.MAX_SLEEP;// 最大值
				final String sleeptUnit = "分钟";// 单位
				FormatType f = FormatType.FORMAT_0;// 格式化数字
				// 4.查询数据
				List<SleepHistory> histroySleeps = LitePalManager.instance()
						.findHistroySleepByDateNew(date);
				// 5.解析数据库的值，解析为float[]
				if (histroySleeps != null && histroySleeps.size() > 0) {
					for (int i = 0; i < histroySleeps.size(); i++) {
						SleepHistory sleepHistory = histroySleeps.get(i);
						String sleepDate = sleepHistory.getSleepDate();
						int day = Integer.valueOf(sleepDate.substring(6,8));
						int sleepDeep = sleepHistory.getSleepDeep();
						int sleepLight = sleepHistory.getSleepLight();
						switch (rgCountSleep.getCheckedRadioButtonId()) {
						case R.id.rb_sleep_deep:
							sleepDatas[day] += sleepDeep ;
							break;
						case R.id.rb_sleep_light:
							sleepDatas[day] += sleepLight ;
							break;
						case R.id.rb_sleep_total:
							sleepDatas[day] += (sleepDeep+sleepLight)
									;
							break;
						}
					}
				}
				/*if (histroySleeps != null && histroySleeps.size() > 0) {
					for (int i = 0; i < monthMaxDay; i++) {
						if (i < histroySleeps.size()) {
							SleepHistory historySleep = histroySleeps.get(i);
							switch (rgCountSleep.getCheckedRadioButtonId()) {
							case R.id.rb_sleep_deep:
								sleepDatas[i] = historySleep.getSleepDeep() ;
								break;
							case R.id.rb_sleep_light:
								sleepDatas[i] = historySleep.getSleepLight() ;
								break;
							case R.id.rb_sleep_total:
								sleepDatas[i] = historySleep.getSleepLight()
										 + historySleep.getSleepDeep()
										;
								break;
							}
						}
					}
				}*/
				
				
				// 6.设置图标
				countSleep.setMaxValue(sleepMaxValue );
				countSleep.setUnit(sleeptUnit);
				countSleep.setFormat(f);
				 Message msg = mHandler.obtainMessage();
				 Bundle b = new Bundle();
				 b.putString("sleeptUnit", sleeptUnit);
				 msg.setData(b);
				 msg.what=MSG_SLEEP_DATA;
				 msg.obj = sleepDatas;
				 mHandler.sendMessage(msg);
				
				/*mHandler.post(new Runnable() {
					@Override
					public void run() {
						countSleep.setDatas(sleepDatas);
						// 7.设置平均统计
						String sleepAvgValue = getAvgValue(sleepDatas);
						String sleepAvgUnit = sleeptUnit + "/天";
						tvSleepValue.setText(sleepAvgValue);
						tvSleepUnit.setText(sleepAvgUnit);
					}
				});*/
	/*		}
		}).start();*/
	}

	private void initSportValues(final Date date) {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
				// 1.一个月的天数
				final int monthMaxDay = DateUtil.getMonthMaxDay(date);
				Log.e(TAG, "____________________monthMaxDay : " + monthMaxDay);
				// 2.新建数据
				final float[] sportDatas = new float[monthMaxDay];
				// 3.确定radioButton对应得值
				float sportMaxValue = 100;// 最大值
				String sportUnit = "";// 单位
				FormatType f = FormatType.FORMAT_0;// 格式化数字
				switch (rgCountSport.getCheckedRadioButtonId()) {
				case R.id.rb_sport_step:
					sportUnit = "步";
					sportMaxValue = JunConstant.MAX_STEP;
					break;
				case R.id.rb_sport_time:
					sportUnit = "分钟";
					sportMaxValue = JunConstant.MAX_TIME;
					break;
				case R.id.rb_sport_distance:
					sportUnit = "千米";
					f = FormatType.FORMAT_2;
					sportMaxValue = JunConstant.MAX_DISTANCE;
					break;
				case R.id.rb_sport_cal:
					sportUnit = "千卡";
					sportMaxValue = JunConstant.MAX_CALORIE;

				}
				// 4.查询数据
				// 需要在新线程查询数据库
				// 查询 每天的运动信息
//				List<SportHistory> list = LitePalManager.instance().getSportHistoryListByDay
				select(date);
				final List<SportHistory> historySportPaths = LitePalManager
						.instance().getSportHistoryListByMonthForAll(date);
				List<SportHistory> listDatas = new ArrayList<SportHistory>();
				if (historySportPaths != null && historySportPaths.size()>0) {
					SportHistory sh = new SportHistory();
					int step = 0;
					
					for (int i = 0; i < monthMaxDay; i++) {
						SportHistory sport = historySportPaths.get(i);
						Calendar c = Calendar.getInstance();
						c.setTime(date);
						c.set(Calendar.DAY_OF_MONTH, i+1);
						String dateStr = DateUtil.dateToString(c.getTime(), "yyyyMMdd");
						if (sport.getSportDate().equals(dateStr)) {
							step +=sport.getStep();
						}
					}
					
					
					sh.setStep(step);
				}
				
				
				
				// 6.设置图标
				countSport.setMaxValue(sportMaxValue);
				countSport.setUnit(sportUnit);
				countSport.setFormat(f);
				// 5.解析数据库的值，解析为float[]
				if (historySportPaths != null && historySportPaths.size()>0) {
					Log.i(TAG, "~~~~~-->大小："+historySportPaths.size() +"<--~~~~~~~~~");
					for (int i = 0; i < historySportPaths.size(); i++) {
						SportHistory sportHistory = historySportPaths.get(i);
						String sportDate = sportHistory.getSportDate();
						int day = Integer.valueOf(sportDate.substring(6,8));
						int step = sportHistory.getStep();
						int sportTime = sportHistory.getConsuming();
						int distance = sportHistory.getDistance();
						int calorie = sportHistory.getCalorie();
						switch (rgCountSport
								.getCheckedRadioButtonId()) {
						case R.id.rb_sport_step:
							sportDatas[day] += step;
							break;
						case R.id.rb_sport_time:
							sportDatas[day] += sportTime;
							break;
						case R.id.rb_sport_distance:
							sportDatas[day] += (distance*1.0f/100);
							break;
						case R.id.rb_sport_cal:
							sportDatas[day] += (calorie*1.0f/100);
							break;
						}
					}
				}
				
				
				
				
				
				
//				mHandler.post(new Runnable() {
//
//					@Override
//					public void run() {
						// 5.解析数据库的值，解析为float[]
						/*if (historySportPaths != null
								&& historySportPaths.size() > 0) {
							for (int i = 0; i < monthMaxDay; i++) {
								if (i < historySportPaths.size()) {
									SportHistory historySport = historySportPaths
											.get(i);
							
									int step = historySport.getStep();
									int sportTime = historySport.getConsuming();
									int distance = historySport.getDistance();
									int calorie = historySport.getCalorie();
									switch (rgCountSport
											.getCheckedRadioButtonId()) {
									case R.id.rb_sport_step:
										sportDatas[i] = step;
										break;
									case R.id.rb_sport_time:
										sportDatas[i] = sportTime;
										break;
									case R.id.rb_sport_distance:
										sportDatas[i] = distance;
										break;
									case R.id.rb_sport_cal:
										sportDatas[i] = calorie;
										break;
									}
								}
							}
						}*/

						// 7.设置平均统计
						String sportAvgValue = getAvgValue(sportDatas);
						String sportAvgUnit = sportUnit + "/天";
		
					for (int i = 0; i < sportDatas.length; i++) {
						Log.e(TAG, "sportDatas :" + "==="+sportDatas[i]+"===");
					}
						
						 Message msg = mHandler.obtainMessage();
						 Bundle b = new Bundle();
						 msg.setData(b);
						 b.putString("sportAvgValue", sportAvgValue);
						 b.putString("sportAvgUnit", sportAvgUnit);
						 msg.what=MSG_SPORT_DATA;
						 msg.obj = sportDatas;
						 mHandler.sendMessage(msg);

//					}
//				});
//			}
//		}).start();

	}

	private void initSportValuesNew(final Date date) {
		select(date);
	}

	
	public void select(final Date date){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					final int monthMaxDay = DateUtil.getMonthMaxDay(date);
					float [] steps = new float[monthMaxDay];
					float [] distances = new float[monthMaxDay];
					float [] calories = new float[monthMaxDay];
					float [] consumings = new float[monthMaxDay];
					double [] hrs = new double[monthMaxDay];
					for (int i = 0; i < monthMaxDay; i++) {
						Calendar c = Calendar.getInstance();
						c.setTime(date);
						c.set(Calendar.DAY_OF_MONTH, i+1);
						String dateStr = DateUtil.dateToString(c.getTime(), "yyyyMMdd");
						int sumStep = DataSupport.where("sportDate like ? ", dateStr).sum(SportHistory.class, "step", Integer.class);
						steps[i] = sumStep;
						Thread.sleep(20);
						int sumDistance = DataSupport.where("sportDate like ? ", dateStr).sum(SportHistory.class, "distance", Integer.class);
						distances[i] =sumDistance*1.0f/100;
						Thread.sleep(20);
						int sumCalorie = DataSupport.where("sportDate like ? ", dateStr).sum(SportHistory.class, "calorie", Integer.class);
						calories[i] = sumCalorie*1.0f/100;
						Thread.sleep(20);
						int sumConsuming = DataSupport.where("sportDate like ? ", dateStr).sum(SportHistory.class, "consuming", Integer.class);
						consumings[i] = sumConsuming;
						Thread.sleep(20);
						double avgHr = DataSupport.where("sportDate like ? ", dateStr).average(SportHistory.class, "hr");
						hrs[i] = avgHr;
						Thread.sleep(20);
					
					}
					
					
					 Message msg = mHandler.obtainMessage();
					 Bundle b = new Bundle();
					 b.putFloatArray("steps", steps);
					 b.putFloatArray("distances", distances);
					 b.putFloatArray("calories", calories);
					 b.putFloatArray("consumings", consumings);
					 b.putDoubleArray("hrs", hrs);
					 msg.setData(b);
					 msg.what = MSG_SPORT_DATA_NEW;
					 mHandler.sendMessage(msg);
				} catch (Exception e) {
				}
				
			}
		}).start();
	}
	
	private String getAvgValue(float[] sportDatas) {
		if (sportDatas != null && sportDatas.length > 0) {
			float total = 0f;
			int size = sportDatas.length;
			for (int i = 0; i < size; i++) {
				total += sportDatas[i];
			}
			return DataUtil.format(total / size);
		} else {
			return "0";
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.up:
			monthDate = DateUtil.getDateOfDiffMonth(monthDate, -1);
			initValues(monthDate);
			break;
		case R.id.down:
			
			monthDate = DateUtil.getDateOfDiffMonth(monthDate, 1);
			initValues(monthDate);
			break;
		case R.id.img_back:
			finish();
			break;
		case R.id.img_more:
			showMorePop(v);
			break;

		default:
			break;
		}
	}

	private void showMorePop(View v) {
		if (pop == null) {

			pop = new PopMoreView().build(this, new OnPopClickListener() {

				@Override
				public void onShareClick(View v) {
					// toast("分享");
					share(mHandler);
				}

				@Override
				public void onDeleteClick(View v) {
					toast("删除");
				}
			});

		}
		pop.showAsDropDown(v, 0, -18);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		showLoadingDialog();
		switch (group.getId()) {
		case R.id.rg_count_sport:
//			initSportValues(monthDate);
//			new Thread(loadSportDataRunnable).start();

			initSportValuesNew(monthDate);
			break;
		case R.id.rg_count_sleep:
//			initSportValuesNew(monthDate);
//			initSleepValues(monthDate);
			new Thread(loadSleepDataRunnable).start();
			break;
		default:
			break;
		}
	}
}
