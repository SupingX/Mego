package com.mycj.jusd.ui.activity;

import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.mycj.jusd.R;
import com.mycj.jusd.base.BaseActivity;
import com.mycj.jusd.bean.HistorySleep;
import com.mycj.jusd.bean.HistorySport;
import com.mycj.jusd.bean.LitePalManager;
import com.mycj.jusd.bean.StaticValue;
import com.mycj.jusd.util.DataUtil;
import com.mycj.jusd.util.ShareUtil;
import com.mycj.jusd.view.CountView;
import com.mycj.jusd.view.CountView.FormatType;
import com.mycj.jusd.view.DateUtil;
import com.mycj.jusd.view.FangRadioButton;
import com.mycj.jusd.view.FangTextView;
import com.mycj.jusd.view.PopMoreView;
import com.mycj.jusd.view.PopMoreView.OnPopClickListener;

public class CountActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {

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
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case StaticValue.MSG_SHARE:
				String path  = (String) msg.obj;
				ShareUtil.shareImage(path, CountActivity.this,"分享");
				break;
			default:
				break;
			}
			
		};
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

	private void initValues(Date date) {
		tvDate.setText(DateUtil.dateToString(date, "yyyy/MM"));
		// 运动数据更新
		initSportValues(date);
		// 睡眠数据更新
		initSleepValues(date);
	}

	private void initSleepValues(Date date) {
		// 1.一个月的天数
		int monthMaxDay = DateUtil.getMonthMaxDay(date);
		// 2.新建数据
		float[] sleepDatas = new float[monthMaxDay];
		// 3.确定radioButton对应得值
		float sleepMaxValue = StaticValue.MAX_SLEEP;// 最大值
		String sleeptUnit = "分钟";// 单位
		FormatType f = FormatType.FORMAT_0;// 格式化数字
		// 4.查询数据
		List<HistorySleep> histroySleeps = LitePalManager.instance().findHistroySleepByDate(date);
		// 5.解析数据库的值，解析为float[]
		if (histroySleeps != null && histroySleeps.size() > 0) {
			for (int i = 0; i < monthMaxDay; i++) {
				if (i < histroySleeps.size()) {
					HistorySleep historySleep = histroySleeps.get(i);
					switch (rgCountSleep.getCheckedRadioButtonId()) {
					case R.id.rb_sleep_deep:
						sleepDatas[i] = historySleep.getDeep()/60L;
						break;
					case R.id.rb_sleep_light:
						sleepDatas[i] = historySleep.getLight()/60L;
						break;
					}
				}
			}
		}
		// 6.设置图标
		countSleep.setMaxValue(sleepMaxValue/60L);
		countSleep.setUnit(sleeptUnit);
		countSleep.setFormat(f);
		countSleep.setDatas(sleepDatas);
		// 7.设置平均统计
		String sleepAvgValue = getAvgValue(sleepDatas);
		String sleepAvgUnit = sleeptUnit + "/天";
		tvSleepValue.setText(sleepAvgValue);
		tvSleepUnit.setText(sleepAvgUnit);
	}

	private void initSportValues(Date date) {
		// 1.一个月的天数
		int monthMaxDay = DateUtil.getMonthMaxDay(date);
		// 2.新建数据
		float[] sportDatas = new float[monthMaxDay];
		// 3.确定radioButton对应得值
		float sportMaxValue = 100;// 最大值
		String sportUnit = "";// 单位
		FormatType f = FormatType.FORMAT_0;// 格式化数字
		switch (rgCountSport.getCheckedRadioButtonId()) {
		case R.id.rb_sport_step:
			sportUnit = "步";
			sportMaxValue = StaticValue.MAX_STEP;
			break;
		case R.id.rb_sport_time:
			sportUnit = "分钟";
			sportMaxValue = StaticValue.MAX_TIME;
			break;
		case R.id.rb_sport_distance:
			sportUnit = "千米";
			f = FormatType.FORMAT_2;
			sportMaxValue = DataUtil.getDistanceValue(StaticValue.MAX_STEP);
			break;
		case R.id.rb_sport_cal:
			sportUnit = "千卡";
			sportMaxValue = (int) DataUtil.getKalValue(StaticValue.MAX_STEP);
			f = FormatType.FORMAT_2;

		}
		// 4.查询数据
		List<HistorySport> historySports = LitePalManager.instance().findHistroySportByDate(date);
		// 5.解析数据库的值，解析为float[]
		if (historySports != null && historySports.size() > 0) {
			for (int i = 0; i < monthMaxDay; i++) {
				if (i < historySports.size()) {
					HistorySport historySport = historySports.get(i);
					int step = historySport.getStep();
					int sportTime = historySport.getSportTime();
					switch (rgCountSport.getCheckedRadioButtonId()) {
					case R.id.rb_sport_step:
						sportDatas[i] = step;
						break;
					case R.id.rb_sport_time:
						sportDatas[i] = sportTime;
						break;
					case R.id.rb_sport_distance:
						sportDatas[i] = DataUtil.getDistanceValue(step);
						break;
					case R.id.rb_sport_cal:
						sportDatas[i] = DataUtil.getKalValue(step);
						break;

					}
				}
			}
		}
		// 6.设置图标
		countSport.setMaxValue(sportMaxValue);
		countSport.setUnit(sportUnit);
		countSport.setFormat(f);
		countSport.setDatas(sportDatas);
		// 7.设置平均统计
		String sportAvgValue = getAvgValue(sportDatas);
		String sportAvgUnit = sportUnit + "/天";
		tvSportValue.setText(sportAvgValue);
		tvSportUnit.setText(sportAvgUnit);

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
		if (pop==null) {
			
		
		pop = new PopMoreView()
		.build(this,new OnPopClickListener() {
			
			@Override
			public void onShareClick(View v) {
//				toast("分享");
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
		switch (group.getId()) {
		case R.id.rg_count_sport:
			initSportValues(monthDate);
			break;
		case R.id.rg_count_sleep:
			initSleepValues(monthDate);
			break;
		default:
			break;
		}
	}
}
