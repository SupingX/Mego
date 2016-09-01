package com.mycj.jusd;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.laputa.blue.util.XLog;
import com.mycj.jusd.base.BaseActivity;
import com.mycj.jusd.bean.JunConstant;
import com.mycj.jusd.bean.LitePalManager;
import com.mycj.jusd.bean.news.SportHistory;
import com.mycj.jusd.ui.fragment.MapDataFragment;
import com.mycj.jusd.ui.fragment.MapHeartRateFragment;
import com.mycj.jusd.ui.fragment.MapPaceFragment;
import com.mycj.jusd.ui.fragment.MapSignFragment;
import com.mycj.jusd.ui.fragment.MapSpeedFragment;
import com.mycj.jusd.ui.fragment.MapStepFreqFragment;
import com.mycj.jusd.util.DateUtil;
import com.mycj.jusd.view.FangRadioButton;
import com.mycj.jusd.view.FangTextView;
import com.mycj.jusd.view.PopMoreView;
import com.mycj.jusd.view.PopMoreView.OnPopClickListener;
import com.mycj.jusd.view.StatisticsView;

/**
 * 当前是计步时点击进来的页面。。。
 * 
 * @author Administrator
 *
 */
public class StepStatisticsActivity extends BaseActivity implements
		OnCheckedChangeListener {
	private final static String TAG = "StepStatisticsActivity";
	private FangRadioButton tvData; // 数据
	private FangRadioButton tvSpeed; // 速度
	private FangRadioButton tvPerStep; // 步频
	private FangRadioButton tvHeartRate; // 心率
	// private FangRadioButton tvUp;
	private TextView tvNetInfo;
	private MapDataFragment dataFragment;
	private FragmentManager mFragmentManager;
	StringBuffer info = new StringBuffer();
	private Handler mHandler = new Handler() {

	};

	private MapSpeedFragment paceFragment;
	private MapStepFreqFragment stepFreqFragment;
	private MapHeartRateFragment heartRateFragment;
	private MapSignFragment signFragment;
	protected boolean isFirstLocation = true;
	private int directionX;
	// private JsdOrientationListener jsdOrientationListener;
	private String typeDescription;
	private int i;
	private PopMoreView pop;
	private SportHistory sportHistory;
	private FangTextView tvDateTime;
	private StatisticsView sv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_step_statistics);

		mFragmentManager = getSupportFragmentManager();
		loadData();
		initView();
		initDateTime();
	}

	private void initDateTime() {
		String dateTime = "--/--/--";
		if (sportHistory != null) {
			dateTime = getDateTimeString(sportHistory.getSportDate());
		} else {
			dateTime = DateUtil.dateToString(new Date(), "yyyy/MM/dd");
		}
		tvDateTime.setText(dateTime);
	}

	private String getDateTimeString(String value) {
		return DateUtil.dateToString(DateUtil.stringToDate(value, "yyyyMMdd"),
				"yyyy/MM/dd");
	}

	private void loadData() {
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			sportHistory = extras
					.getParcelable(JunConstant.INTENT_SPORT_HISTORY);
			if (sportHistory != null) {
				XLog.e(sportHistory.toString());
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		switch (buttonView.getId()) {

		case R.id.tv_t_data:
			if (dataFragment == null) {
				dataFragment = new MapDataFragment(sportHistory);
			}
			if (dataFragment.isAdded()) {
				transaction.show(dataFragment);
			} else {
				transaction.replace(R.id.frame, dataFragment,
						MapDataFragment.class.getSimpleName());
			}
			if (dataFragment.isAdded()) {
				// 更新UI
//				dataFragment.freshUi(this.sportHistory);
				dataFragment.show(this.sportHistory);
			}
			break;
		case R.id.tv_t_speed:
			if (paceFragment == null) {
				paceFragment = new MapSpeedFragment(this.sportHistory);
			}
			if (paceFragment.isAdded()) {
				transaction.show(paceFragment);
			} else {
				transaction.replace(R.id.frame, paceFragment,
						MapPaceFragment.class.getSimpleName());
			}
			
			if (paceFragment.isAdded()) {
				// 更新UI
//				paceFragment
			}
			
			toast("速度！！！！！");
			break;
		case R.id.tv_t_per_step:
			if (stepFreqFragment == null) {
				stepFreqFragment = new MapStepFreqFragment(this.sportHistory);
			}
			if (stepFreqFragment.isAdded()) {
				transaction.show(stepFreqFragment);
			} else {
				transaction.replace(R.id.frame, stepFreqFragment,
						MapStepFreqFragment.class.getSimpleName());
			}

			toast("步频！！！！！");
			break;
		case R.id.tv_t_hr:
			if (heartRateFragment == null) {
				heartRateFragment = new MapHeartRateFragment(this.sportHistory);

			}
			if (heartRateFragment.isAdded()) {
				transaction.show(heartRateFragment);
			} else {
				transaction.replace(R.id.frame, heartRateFragment,
						MapHeartRateFragment.class.getSimpleName());
			}
			toast("心率 ！！！！！");
			break;
		case R.id.tv_t_up:
			if (signFragment == null) {
				signFragment = new MapSignFragment(this.sportHistory);
			}
			if (signFragment.isAdded()) {
				transaction.show(signFragment);
			} else {
				transaction.replace(R.id.frame, signFragment,
						MapSignFragment.class.getSimpleName());
			}

			break;
		default:
			break;
		}

		if (isChecked) {
			buttonView.setTextColor(Color.WHITE);
			buttonView.animate().setDuration(200)
			// .alpha(255)
					.scaleX(1.2f).scaleY(1.2f);
		} else {
			buttonView.setTextColor(Color.parseColor("#77ffffff"));
			buttonView.animate().setDuration(200)
			// .alpha(70)
					.scaleX(1.0f).scaleY(1.0f);
		}
		transaction.commit();
	}

	private void initView() {
		ImageView imgBack = (ImageView) findViewById(R.id.img_back);
		sv = (StatisticsView) findViewById(R.id.sv);
		tvDateTime = (FangTextView) findViewById(R.id.tv_info_date);
		tvData = (FangRadioButton) findViewById(R.id.tv_t_data);
		tvSpeed = (FangRadioButton) findViewById(R.id.tv_t_speed);
		tvPerStep = (FangRadioButton) findViewById(R.id.tv_t_per_step);
		tvHeartRate = (FangRadioButton) findViewById(R.id.tv_t_hr);
		// tvUp = (FangRadioButton) findViewById(R.id.tv_t_up);
		tvNetInfo = (TextView) findViewById(R.id.tv_net_info);

		tvData.setAlpha(70);
		tvSpeed.setAlpha(70);
		tvPerStep.setAlpha(70);
		tvHeartRate.setAlpha(70);
		// tvUp.setAlpha(70);

		imgBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tvData.setOnCheckedChangeListener(this);
		tvSpeed.setOnCheckedChangeListener(this);
		tvPerStep.setOnCheckedChangeListener(this);
		tvHeartRate.setOnCheckedChangeListener(this);
		// tvUp.setOnCheckedChangeListener(this);
		tvData.setChecked(true);
		showData(sportHistory);
	}

	public void setOffLineMap(View v) {
		// startActivity(new Intent(this,OffLineActivity.class));
		showMorePop(v);
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

	public void showData(final SportHistory sportHistory){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (sportHistory== null) {
					return ;
				}
				String date = sportHistory.getSportDate();
				Date _date = DateUtil.stringToDate(date, "yyyyMMdd");
			
				final List<Float> datas = new ArrayList<Float>();
				for (int i = 0; i < 24; i++) {
					String hour = getHourString(i);
					datas.add(1.0f*LitePalManager.instance().getSportHistoryListByHour(_date,hour));
				}
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						sv.setDate(datas);
					}
				});
			}
		}).start();
		
	}
	
	public String getHourString(int value) {
		return value < 10 ? ("0" + value) : ("" + value);
	}

}
