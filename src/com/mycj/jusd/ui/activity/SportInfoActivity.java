package com.mycj.jusd.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mycj.jusd.R;
import com.mycj.jusd.base.BaseActivity;
import com.mycj.jusd.bean.JunConstant;
import com.mycj.jusd.util.DataUtil;
import com.mycj.jusd.util.ShareUtil;
import com.mycj.jusd.view.AlphaImageView;
import com.mycj.jusd.view.PopMoreView;
import com.mycj.jusd.view.PopMoreView.OnPopClickListener;

public class SportInfoActivity extends BaseActivity implements OnClickListener {

	private AlphaImageView imgBack;
	private AlphaImageView imgMore;
	private TextView tvSportInfoDate;
	private TextView tvSportInfoStep;
	private TextView tvSportInfoTime;
	private TextView tvSportInfoDistance;
	private TextView tvSportInfoAvgSpeed;
	private TextView tvSportInfoCal;
	private PopMoreView pop;
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case JunConstant.MSG_SHARE:
				String path  = (String) msg.obj;
				ShareUtil.shareImage(path, SportInfoActivity.this,"分享");
				break;
			default:
				break;
			}
			
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sport_info);
		initViews();
		Intent intent = getIntent();
	/*	StepHistorySport historySport = null;
		if (intent != null) {
			historySport = intent.getParcelableExtra("historySport");
		}
		initValues(historySport);*/

	}

	private void initViews() {
		imgBack = (AlphaImageView) findViewById(R.id.img_back);
		imgMore = (AlphaImageView) findViewById(R.id.img_more);
		tvSportInfoDate = (TextView) findViewById(R.id.tv_info_date);
		tvSportInfoStep = (TextView) findViewById(R.id.tv_sport_info_step);
		tvSportInfoTime = (TextView) findViewById(R.id.tv_sport_info_time);
		tvSportInfoDistance = (TextView) findViewById(R.id.tv_sport_info_distance);
		tvSportInfoAvgSpeed = (TextView) findViewById(R.id.tv_sport_avg_speed);
		tvSportInfoCal = (TextView) findViewById(R.id.tv_sport_info_cal);

		imgBack.setOnClickListener(this);
		imgMore.setOnClickListener(this);

	}

/*	private void initValues(StepHistorySport historySport) {
		String date = "--/--/--";
		int step = 0;
		int sportTime = 0;
		if (historySport != null) {
			date = historySport.getDate();
			step = historySport.getStep();
			sportTime = historySport.getSportTime();

		}
		tvSportInfoDate.setText(formateString(date));
		tvSportInfoStep.setText(String.valueOf(step));
		tvSportInfoTime.setText(DateUtil.formateTime(sportTime));
		tvSportInfoDistance.setText(DataUtil.getDistanceWithUnit(step, this));
		tvSportInfoAvgSpeed.setText(getAvgSpeed(step, sportTime));
		tvSportInfoCal.setText(DataUtil.getKalWithUnit(step, this));
	}*/

	private String getAvgSpeed(int step, int sportTime) {
		if (sportTime == 0) {
			return "0";
		}
		return DataUtil.format(DataUtil.getDistanceValue(step) * 60 * 60 / sportTime) + "公里/时";
	}

	private String formateString(String date) {
		String pad = "/";
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String day = date.substring(6, 8);
		return new StringBuffer().append(year).append(pad).append(month).append(pad).append(day).toString();
	}

	private void showMorePop(View v) {
		if (pop == null) {
			pop = new PopMoreView().build(this, new OnPopClickListener() {
				@Override
				public void onShareClick(View v) {
//					toast("分享");
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
	public void onClick(View v) {
		switch (v.getId()) {
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
}
