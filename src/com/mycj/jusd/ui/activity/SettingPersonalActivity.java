package com.mycj.jusd.ui.activity;

import java.util.Calendar;
import java.util.Date;
import java.util.IllegalFormatCodePointException;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.laputa.blue.util.XLog;
import com.mycj.jusd.R;
import com.mycj.jusd.base.BaseActivity;
import com.mycj.jusd.bean.JunConstant;
import com.mycj.jusd.bean.Unit;
import com.mycj.jusd.bean.WatchSetting;
import com.mycj.jusd.protocol.ProtocolWriteManager;
import com.mycj.jusd.service.BroadSender;
import com.mycj.jusd.util.DataUtil;
import com.mycj.jusd.util.DateUtil;
import com.mycj.jusd.util.JunUtil;
import com.mycj.jusd.util.SharedPreferenceUtil;
import com.mycj.jusd.view.AbstraceDialog;
import com.mycj.jusd.view.DoubleTimeWheelDialog;
import com.mycj.jusd.view.FangCheckBox;
import com.mycj.jusd.view.FangRadioButton;
import com.mycj.jusd.view.FangTextView;
import com.mycj.jusd.view.LaputaInputDialog;
import com.mycj.jusd.view.NumberDialog;
import com.mycj.jusd.view.SingleWheelDialog;
import com.mycj.jusd.view.XplAlertDialog;

/**
 * 
 * 个人手表设置
 * 1.公制/英制 
 * 2.身高,体重,年龄,性别
 * 3.走步步长,跑步步长
 * 4.心率报警,心率上限,心率下限
 * 5.睡眠检测,开始时间,结束时间
 *
 */
public class SettingPersonalActivity extends BaseActivity implements OnClickListener {

	private FangTextView tvSex;
	private FangTextView tvWeight;
	private FangTextView tvHeight;
	private FangTextView tvSleepTime;
	private FangTextView tvWakeTime;
	private RadioGroup rgUnit;
	private FangRadioButton rbUnitEn;
	private FangRadioButton rbUnitZh;
	private FangCheckBox cbSleepOnoff;
	private NumberDialog birthdayDialog;
	private DoubleTimeWheelDialog sleepDialog;
	private ImageView imgBack;
	private FangTextView tvAge;
	private FangTextView tvStepLength;
	private FangTextView tvRunLength;
	private FangCheckBox cbHearRateOnoff;
	private FangTextView tvHeartRateMax;
	private FangTextView tvHeartRateMin;
	private LaputaInputDialog dialogInput;
	private Handler mHandler = new Handler() {
	};
	private ImageView imgSyncSetting;
	private FangCheckBox cbStepLengthOnoff;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_personal);
		initViews();
		registerReceiver(receiver, BroadSender.getIntentFilter());
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	@Override
	public void onClick(View v) {
		String unit = (String) getSettings(JunConstant.SHARE_SETTING_UNIT, Unit.ZH.getName());
		String title = "";
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.img_sync_setting:
		
			if ( getXBlueService() != null &&  getXBlueService().isAllConnect()) {
//			if (true) {
				
//				if (showXplDialog==null) {
//					showXplDialog = showXplDialog("同步设置中");
//				}
//				showXplDialog.show();
				
				settingDialong = showProgressDialog("正在设置中 ...");
				mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						if (settingDialong != null && settingDialong.isShowing()) {
							settingDialong.dismiss();
							settingDialong=null;
						}
					}
				}, 5000);
				int unitInt  = unit.equals(Unit.ZH.getName())  ? 1 : 0;
				int height = (Integer) getSettings(JunConstant.SHARE_SETTING_HEIGHT, JunConstant.DEFAULT_HEIGHT);;
				int weight = (Integer) getSettings(JunConstant.SHARE_SETTING_WEIGHT, JunConstant.DEFAULT_WEIGHT);;
				String date = (String) getSettings(JunConstant.SHARE_SETTING_BIRTHDAY, JunConstant.DEFAULT_BIRTHDAY);
				int[] yearMonthDay = JunUtil.getYearMonthDay(date);
				int sex = (Integer) getSettings(JunConstant.SHARE_SETTING_SEX, 1);
				int stepOnoff= (Boolean) getSettings(JunConstant.SHARE_SETTING_STEP_LENGTH_ON_OFF, false)?1:0;
				int walkLength =(Integer) getSettings(JunConstant.SHARE_SETTING_SETP_LENGTH, 10);
				int runLength = (Integer) getSettings(JunConstant.SHARE_SETTING_RUN_LENGTH, 10);;
				int hrOnoff= (Boolean) getSettings(JunConstant.SHARE_SETTING_HR_ON_OFF, false)?1:0;
				int hrMax = (Integer) getSettings(JunConstant.SHARE_SETTING_HR_MAX, 220);
				int hrMin = (Integer) getSettings(JunConstant.SHARE_SETTING_HR_MIN, 220);
				int sleepOnoff = (Boolean) getSettings(JunConstant.SHARE_SETTING_SLEEP_ON_OFF, false)?1:0;
				String sleepTime = (String) getSettings(JunConstant.SHARE_SETTING_SLEEP_TIME, "22:00");
				String[] sleepTimes = sleepTime.split(":");
				String awakTime = (String) getSettings(JunConstant.SHARE_SETTING_AWAK_TIME, "08:00");
				String[] awakTimes = awakTime.split(":");
				int sleepHour = Integer.valueOf(sleepTimes[0]);
				int sleepMin= Integer.valueOf(sleepTimes[1]);
				int awakHour = Integer.valueOf(awakTimes[0]);
				int awakMin =Integer.valueOf(awakTimes[1]);
				byte[] data = ProtocolWriteManager.getInstance().getByteForWatchSetting(unitInt, height, weight, yearMonthDay[0],yearMonthDay[1],yearMonthDay[2], sex, stepOnoff,walkLength, runLength, hrOnoff, hrMax, hrMin
						, sleepOnoff, sleepHour, sleepMin, awakHour, awakMin);
				 getXBlueService().write(data);
			} else{
//				toast("请连接Migo...");
				dialogPlsConnectJSDWatchFirst();
			}
			// ... ... 
			break;
		case R.id.tv_height_value:
			if (unit.equals(Unit.ZH.getName())) {
				title = "身高(cm)";
			} else {
				title = "身高(inch)";
			}
			dialogInput = ((LaputaInputDialog) new LaputaInputDialog(SettingPersonalActivity.this).builder().setCanceledOnTouchOutside(false)).setNegativeListener("", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					dialogInput.dismiss();
				}
			}).setPositiveListener("", new OnClickListener() {
				@Override
				public void onClick(View v) {
					int number = dialogInput.getNumber();
					if (number == -1) {
	
					} else {
						tvHeight.setText(String.valueOf(number));
						save(JunConstant.SHARE_SETTING_HEIGHT, number);
					}
					dialogInput.dismiss();
				}
			})
			.setNumber(tvHeight.getText().toString()).setTitle(title);
			dialogInput.show();
			break;
		case R.id.tv_weight_value:
			if (unit.equals(Unit.ZH.getName())) {
				title = "体重(kg)";
			} else {
				title = "体重(lb)";
			}
			dialogInput = ((LaputaInputDialog) new LaputaInputDialog(SettingPersonalActivity.this).builder().setCanceledOnTouchOutside(false)).setNegativeListener("", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					dialogInput.dismiss();
				}
			}).setPositiveListener("", new OnClickListener() {
				@Override
				public void onClick(View v) {
					int number = dialogInput.getNumber();
					if (number == -1) {
	
					} else {
						tvWeight.setText(String.valueOf(number));
						save(JunConstant.SHARE_SETTING_WEIGHT, number);
					}
					dialogInput.dismiss();
				}
			})
	
			.setNumber(tvWeight.getText().toString()).setTitle(title);
			dialogInput.show();
			break;
		case R.id.tv_age_value:
			String birthday = (String) SharedPreferenceUtil.get(this, JunConstant.SHARE_SETTING_BIRTHDAY, JunConstant.DEFAULT_BIRTHDAY);
			Date date = DateUtil.stringToDate(birthday, "yyyy-MM-dd");
			if (date == null) {
				date = new Date();
			}
			birthdayDialog = new NumberDialog(this, date).builder().setNegativeButton("确定", new OnClickListener() {
				@Override
				public void onClick(View v) {
					Calendar c = birthdayDialog.getCalendar();
					String date = DateUtil.dateToString(c.getTime(), "yyyy-MM-dd");
					int age = JunUtil.getAge(date);
					if (age < 0) {
						toast("日期选择错误");
					} else {
						String text = String.valueOf(age);
						tvAge.setText(text);
						SharedPreferenceUtil.put(getApplicationContext(), JunConstant.SHARE_SETTING_BIRTHDAY, date);
					}
				}
			}).setPositiveButton("取消", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
	
				}
			});
			birthdayDialog.show();
	
			break;
		case R.id.tv_sex_value:
			AbstraceDialog dialogSex = new AbstraceDialog(this).builder().setTitle("性别").setPositiveButton("女", new OnClickListener() {
				@Override
				public void onClick(View v) {
					tvSex.setText("女");
					SharedPreferenceUtil.put(getApplicationContext(), JunConstant.SHARE_SETTING_SEX, 0);
				}
			}).setNegativeButton("男", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					tvSex.setText("男");
					SharedPreferenceUtil.put(getApplicationContext(), JunConstant.SHARE_SETTING_SEX, 1);
				}
			});
			dialogSex.show();
	
			break;
		case R.id.tv_step_length_value:
			if (unit.equals(Unit.ZH.getName())) {
				title = "走步步长(cm)";
			} else {
				title = "走步步长(inch)";
			}
			dialogInput = ((LaputaInputDialog) new LaputaInputDialog(SettingPersonalActivity.this).builder().setCanceledOnTouchOutside(false)).setNegativeListener("", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					dialogInput.dismiss();
				}
			}).setPositiveListener("", new OnClickListener() {
				@Override
				public void onClick(View v) {
					int number = dialogInput.getNumber();
					if (number == -1) {
	
					} else {
						tvStepLength.setText(String.valueOf(number));
						save(JunConstant.SHARE_SETTING_SETP_LENGTH, number);
					}
					dialogInput.dismiss();
				}
			})
	
			.setNumber(tvStepLength.getText().toString()).setTitle(title);
			dialogInput.show();
			break;
		case R.id.tv_run_length_value:
			if (unit.equals(Unit.ZH.getName())) {
				title = "跑步步长(cm)";
			} else {
				title = "跑步步长(inch)";
			}
			dialogInput = ((LaputaInputDialog) new LaputaInputDialog(SettingPersonalActivity.this).builder().setCanceledOnTouchOutside(false)).setNegativeListener("", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					dialogInput.dismiss();
				}
			}).setPositiveListener("", new OnClickListener() {
				@Override
				public void onClick(View v) {
					int number = dialogInput.getNumber();
					if (number == -1) {
	
					} else {
						tvRunLength.setText(String.valueOf(number));
						save(JunConstant.SHARE_SETTING_RUN_LENGTH, number);
					}
					dialogInput.dismiss();
				}
			})
	
			.setNumber(tvRunLength.getText().toString()).setTitle(title);
			dialogInput.show();
			break;
		case R.id.tv_hr_max_value:
			title = "心率上限(次/分钟)";
			dialogInput = ((LaputaInputDialog) new LaputaInputDialog(SettingPersonalActivity.this).builder().setCanceledOnTouchOutside(false)).setNegativeListener("", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					dialogInput.dismiss();
				}
			}).setPositiveListener("", new OnClickListener() {
				@Override
				public void onClick(View v) {
					int number = dialogInput.getNumber();
					if (number == -1) {
	
					} else {
						tvHeartRateMax.setText(String.valueOf(number));
						save(JunConstant.SHARE_SETTING_HR_MAX, number);
					}
					dialogInput.dismiss();
				}
			})
	
			.setNumber(tvHeartRateMax.getText().toString()).setTitle(title);
			dialogInput.show();
			break;
		case R.id.tv_hr_min_value:
			title = "心率下限(次/分钟)";
			dialogInput = ((LaputaInputDialog) new LaputaInputDialog(SettingPersonalActivity.this).builder().setCanceledOnTouchOutside(false)).setNegativeListener("", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					dialogInput.dismiss();
				}
			}).setPositiveListener("", new OnClickListener() {
				@Override
				public void onClick(View v) {
					int number = dialogInput.getNumber();
					if (number == -1) {
	
					} else {
						tvHeartRateMin.setText(String.valueOf(number));
						save(JunConstant.SHARE_SETTING_HR_MIN, number);
					}
					dialogInput.dismiss();
				}
			})
	
			.setNumber(tvHeartRateMin.getText().toString()).setTitle(title);
			dialogInput.show();
			break;
		case R.id.tv_sleep_time:
			int sleepHour = 6;
			int sleepMin = 0;
			String sleep = tvSleepTime.getText().toString();
			int[] times = stringToTime(sleep);
			if (times!=null) {
				sleepHour = times[0];
				sleepMin = times[1];
			}
			
			sleepDialog = new DoubleTimeWheelDialog(this, sleepHour, sleepMin).builder().setPositiveButton("取消", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
	
				}
			}).setNegativeButton("确定", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					int hour = sleepDialog.getLeftValue();
					int min = sleepDialog.getRightValue();
					String hourStr = getStringForTwo(hour);
					String minStr = getStringForTwo(min);
					String sleepTime = hourStr + ":" + minStr;
					tvSleepTime.setText(sleepTime);
					SharedPreferenceUtil.put(getApplicationContext(), JunConstant.SHARE_SETTING_SLEEP_TIME, sleepTime);
				}
			});
			sleepDialog.show();
			break;
			
		case R.id.tv_wake_time:
			int wakeHour = 6;
			int wakepMin = 0;
			String wake = tvWakeTime.getText().toString();
			int[] wakeTimes = stringToTime(wake);
			if (wakeTimes!=null) {
				sleepHour = wakeTimes[0];
				sleepMin = wakeTimes[1];
			}
			
			sleepDialog = new DoubleTimeWheelDialog(this, wakeHour, wakepMin).builder().setPositiveButton("取消", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
	
				}
			}).setNegativeButton("确定", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					int hour = sleepDialog.getLeftValue();
					int min = sleepDialog.getRightValue();
					String hourStr = getStringForTwo(hour);
					String minStr = getStringForTwo(min);
					String sleepTime = hourStr + ":" + minStr;
					tvWakeTime.setText(sleepTime);
					SharedPreferenceUtil.put(getApplicationContext(), JunConstant.SHARE_SETTING_AWAK_TIME, sleepTime);
				}
			});
			sleepDialog.show();
			break;
	
		
		default:
			break;
		}
	}

	private void initViews() {

		imgBack = (ImageView) findViewById(R.id.img_back);
		imgSyncSetting = (ImageView) findViewById(R.id.img_sync_setting);
		rgUnit = (RadioGroup) findViewById(R.id.rg_unit);
		rbUnitEn = (FangRadioButton) findViewById(R.id.rb_unit_en);
		rbUnitZh = (FangRadioButton) findViewById(R.id.rb_unit_zh);
		tvHeight = (FangTextView) findViewById(R.id.tv_height_value);
		tvWeight = (FangTextView) findViewById(R.id.tv_weight_value);
		tvAge = (FangTextView) findViewById(R.id.tv_age_value);
		tvSex = (FangTextView) findViewById(R.id.tv_sex_value);
		tvStepLength = (FangTextView) findViewById(R.id.tv_step_length_value);
		tvRunLength = (FangTextView) findViewById(R.id.tv_run_length_value);
		cbHearRateOnoff = (FangCheckBox) findViewById(R.id.cb_remind_hr);
		cbStepLengthOnoff = (FangCheckBox) findViewById(R.id.cb_remind_step_length);
		tvHeartRateMax = (FangTextView) findViewById(R.id.tv_hr_max_value);
		tvHeartRateMin = (FangTextView) findViewById(R.id.tv_hr_min_value);
		cbSleepOnoff = (FangCheckBox) findViewById(R.id.cb_remind_sleep);
		tvSleepTime = (FangTextView) findViewById(R.id.tv_sleep_time);
		tvWakeTime = (FangTextView) findViewById(R.id.tv_wake_time);
		
		// 初始化设置
		initSettingsDefault();
		// 设置Listeners
		setListener();
		// 获取手表设置
		requestWatchSetting();

	}
	
	private void requestWatchSetting() {
		if (getXBlueService()!=null && getXBlueService().isAllConnect()) {
			// 获取手环更新 
			
			getXBlueService().write(ProtocolWriteManager.getInstance().getByteForRequstWatchConfigration(04));
			requestSettingDialog = showProgressDialog("正在获取手表设置...");
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (requestSettingDialog != null && requestSettingDialog.isShowing()) {
						requestSettingDialog.dismiss();
						requestSettingDialog = null;
					}
				}
			}, 5000);
		}
	}

	private void updateWeihgtAndHeight(int weight, int height,int stepLength,int runLength) {

		String weightStr = "";
		String heightStr = "";
		String stepLengthStr = "";
		String runLengthStr = "";
		weightStr = String.valueOf(weight);
		heightStr = String.valueOf(height);
		stepLengthStr = String.valueOf(stepLength);
		runLengthStr = String.valueOf(runLength);
		tvWeight.setText(weightStr);
		tvHeight.setText(heightStr);
		tvStepLength.setText(stepLengthStr);
		tvRunLength.setText(runLengthStr);
	}
	

	private void setListener() {
		
		
		/**
		 * 1.公制/英制
		 */
		rgUnit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				String en = Unit.EN.getName();
				String zh = Unit.ZH.getName();
				String value = checkedId == R.id.rb_unit_en ? en : zh;
				save(JunConstant.SHARE_SETTING_UNIT, value);
				
				int weight = 0;
				int height = 0;
				int stepLength = 0;
				int runLength = 0;
				int weightOld = (Integer) SharedPreferenceUtil.get(getApplicationContext(), JunConstant.SHARE_SETTING_WEIGHT, JunConstant.DEFAULT_WEIGHT);
				int heightOld = (Integer) SharedPreferenceUtil.get(getApplicationContext(), JunConstant.SHARE_SETTING_HEIGHT, JunConstant.DEFAULT_HEIGHT);
				int stepLengthOld = (Integer) SharedPreferenceUtil.get(getApplicationContext(), JunConstant.SHARE_SETTING_SETP_LENGTH, JunConstant.DEFAULT_SETP_LENGTH);
				int runLengthOld = (Integer) SharedPreferenceUtil.get(getApplicationContext(), JunConstant.SHARE_SETTING_RUN_LENGTH, JunConstant.DEFAULT_RUN_LENGTH);
				if (checkedId == R.id.rb_unit_en) {//
					SharedPreferenceUtil.put(getApplicationContext(), JunConstant.SHARE_SETTING_UNIT, Unit.EN.getName());
					weight = DataUtil.kgToLb(weightOld);
					height = DataUtil.cmToInch(heightOld);
					stepLength = DataUtil.cmToInch(stepLengthOld);
					runLength = DataUtil.cmToInch(runLengthOld);

				} else {
					SharedPreferenceUtil.put(getApplicationContext(), JunConstant.SHARE_SETTING_UNIT, Unit.ZH.getName());
					weight = DataUtil.lbToKg(weightOld);
					height = DataUtil.inchToCm(heightOld);
					stepLength = DataUtil.inchToCm(stepLengthOld);
					runLength = DataUtil.inchToCm(runLengthOld);
				}
				updateWeihgtAndHeight(weight, height,stepLength,runLength);
				SharedPreferenceUtil.put(getApplicationContext(), JunConstant.SHARE_SETTING_WEIGHT, weight);
				SharedPreferenceUtil.put(getApplicationContext(), JunConstant.SHARE_SETTING_HEIGHT, height);
				SharedPreferenceUtil.put(getApplicationContext(), JunConstant.SHARE_SETTING_SETP_LENGTH, stepLength);
				SharedPreferenceUtil.put(getApplicationContext(), JunConstant.SHARE_SETTING_RUN_LENGTH, runLength);
				
				
			}
		});
		/**
		 * 2.心率报警
		 */
		cbHearRateOnoff.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				save(JunConstant.SHARE_SETTING_HR_ON_OFF, isChecked);
			}
		});

		/**
		 * 3.睡眠检测
		 */
		cbSleepOnoff.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				save(JunConstant.SHARE_SETTING_SLEEP_ON_OFF, isChecked);
			}
		});
		
		/**
		 * 4.步长
		 */
		cbStepLengthOnoff.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				save(JunConstant.SHARE_SETTING_STEP_LENGTH_ON_OFF, isChecked);
			}
		});
		
		/**
		 * 4.各个文本
		 */
		imgBack.setOnClickListener(this);
		imgSyncSetting.setOnClickListener(this);
		tvHeight.setOnClickListener(this);
		tvWeight.setOnClickListener(this);
		tvAge.setOnClickListener(this);
		tvSex.setOnClickListener(this);
		tvStepLength.setOnClickListener(this);
		tvRunLength.setOnClickListener(this);
		tvHeartRateMax.setOnClickListener(this);
		tvHeartRateMin.setOnClickListener(this);
		tvSleepTime.setOnClickListener(this);
		tvWakeTime.setOnClickListener(this);

	}

	private void save(String key, Object value) {
		SharedPreferenceUtil.put(this, key, value);
	}

	private Object getSettings(String key, Object value) {
		return SharedPreferenceUtil.get(this, key, value);
	}

	private void initSettingsDefault() {
		
		/** 单位 **/
		String unit  = (String) getSettings(JunConstant.SHARE_SETTING_UNIT, Unit.ZH.getName());
		if (unit.equals(Unit.ZH.getName())) {
			rbUnitZh.setChecked(true);
		}else{
			rbUnitEn.setChecked(true);
		}
		/** 身高**/
		int height = (Integer) getSettings(JunConstant.SHARE_SETTING_HEIGHT, 170);
		tvHeight.setText(String.valueOf(height));
		/** 体重**/
		int weight = (Integer) getSettings(JunConstant.SHARE_SETTING_WEIGHT, 100);
		tvWeight.setText(String.valueOf(weight));
		/** 年龄**/
		String date = (String) getSettings(JunConstant.SHARE_SETTING_BIRTHDAY, JunConstant.DEFAULT_BIRTHDAY);
		int age = JunUtil.getAge(date);
		tvAge.setText(String.valueOf(age));
		/** 性别**/
		int sex = (Integer) getSettings(JunConstant.SHARE_SETTING_SEX, 1);
		tvSex.setText(sex==0?"女":"男");
		/** 走步步长**/
		int stepLength = (Integer) getSettings(JunConstant.SHARE_SETTING_SETP_LENGTH, 10);
		tvStepLength.setText(String.valueOf(stepLength));
		/** 跑步步长**/
		int runLength = (Integer) getSettings(JunConstant.SHARE_SETTING_RUN_LENGTH, 20);
		tvRunLength.setText(String.valueOf(runLength));
		/** 心率报警**/
		boolean hrOnoff = (Boolean) getSettings(JunConstant.SHARE_SETTING_HR_ON_OFF, false);
		cbHearRateOnoff.setChecked(hrOnoff);
		/** 心率上限**/
		int heartRateMax = (Integer) getSettings(JunConstant.SHARE_SETTING_HR_MAX, 220);
		tvHeartRateMax.setText(String.valueOf(heartRateMax));
		/** 心率下限**/
		int heartRateMin = (Integer) getSettings(JunConstant.SHARE_SETTING_HR_MIN, 20);
		tvHeartRateMin.setText(String.valueOf(heartRateMin));
		/** 睡眠检测**/
		boolean sleepOnoff = (Boolean) getSettings(JunConstant.SHARE_SETTING_SLEEP_ON_OFF, false);
		cbSleepOnoff.setChecked(sleepOnoff);
		/** 睡觉时间**/
		String sleepTime = (String) getSettings(JunConstant.SHARE_SETTING_SLEEP_TIME, "22:00");
		tvSleepTime.setText(sleepTime);
		/** 起床时间**/
		String awakTime = (String) getSettings(JunConstant.SHARE_SETTING_AWAK_TIME, "08:00");
		tvWakeTime.setText(awakTime);
		/** 步长检测**/
		boolean stepOnoff = (Boolean) getSettings(JunConstant.SHARE_SETTING_STEP_LENGTH_ON_OFF, false);
		cbStepLengthOnoff.setChecked(stepOnoff);
	}
	
	
	private void initSettings(WatchSetting setting){
		if (setting == null) {
			return ;
		}
		int unit =setting.getUnit();
		int height = setting.getHeight();
		int weight = setting.getWeight();
		String birhtday = setting.getBirthday();
		int sex =  setting.getSex();
		int stepLength  = setting.getWalkLength();
		int runLength = setting.getRunLength();
		int stepOnoff = setting.getStepOnoff();
		int heartRateMax = setting.getHrMax();
		int heartRateMin = setting.getHrMin();
		int sleepOnoff =  setting.getSleepOnoff();
		String sleepTime = setting.getSleepTime();
		String awakTime = setting.getAwakTime();
		int hrOnoff =  setting.getHrOnoff();
		int age = JunUtil.getAge(birhtday);
		
		
		/** 单位 **/
		if (unit==0) {
			rbUnitZh.setChecked(true);
		}else if(unit==1){
			rbUnitEn.setChecked(true);
		}
		/** 身高**/
		tvHeight.setText(String.valueOf(height));
		/** 体重**/
		tvWeight.setText(String.valueOf(weight));
		/** 年龄**/
		if (age<-1) {
			
		}else{
			tvAge.setText(String.valueOf(age));
		}
		/** 性别**/
		tvSex.setText(sex==0?"女":"男");
		/** 走步步长**/
		tvStepLength.setText(String.valueOf(stepLength));
		/** 跑步步长**/
		tvRunLength.setText(String.valueOf(runLength));
		/** 心率报警**/
		cbHearRateOnoff.setChecked(hrOnoff==1 ? true : false);
		/** 心率上限**/
		tvHeartRateMax.setText(String.valueOf(heartRateMax));
		/** 心率下限**/
		tvHeartRateMin.setText(String.valueOf(heartRateMin));
		/** 睡眠检测**/
		cbSleepOnoff.setChecked(sleepOnoff==1?true:false);
		/** 睡觉时间**/
		tvSleepTime.setText(sleepTime);
		/** 起床时间**/
		tvWakeTime.setText(awakTime);
		/** 步长检测**/
		cbStepLengthOnoff.setChecked(stepOnoff==1?true:false);
		
		/** 本地保存 **/
		SharedPreferenceUtil.put(this, JunConstant.SHARE_SETTING_UNIT, unit);
		SharedPreferenceUtil.put(this, JunConstant.SHARE_SETTING_HEIGHT,height);
		SharedPreferenceUtil.put(this, JunConstant.SHARE_SETTING_WEIGHT,weight);
		SharedPreferenceUtil.put(this, JunConstant.SHARE_SETTING_SEX,sex);
		SharedPreferenceUtil.put(this, JunConstant.SHARE_SETTING_BIRTHDAY,birhtday);
		SharedPreferenceUtil.put(this, JunConstant.SHARE_SETTING_STEP_LENGTH_ON_OFF,stepOnoff==1);
		SharedPreferenceUtil.put(this, JunConstant.SHARE_SETTING_SETP_LENGTH,stepLength);
		SharedPreferenceUtil.put(this, JunConstant.SHARE_SETTING_RUN_LENGTH,runLength);
		SharedPreferenceUtil.put(this, JunConstant.SHARE_SETTING_HR_ON_OFF,hrOnoff==1);
		SharedPreferenceUtil.put(this, JunConstant.SHARE_SETTING_HR_MAX,heartRateMax);
		SharedPreferenceUtil.put(this, JunConstant.SHARE_SETTING_HR_MIN,heartRateMin);
		SharedPreferenceUtil.put(this, JunConstant.SHARE_SETTING_SLEEP_ON_OFF,sleepOnoff==1);
		SharedPreferenceUtil.put(this, JunConstant.SHARE_SETTING_SLEEP_TIME,sleepTime);
		SharedPreferenceUtil.put(this, JunConstant.SHARE_SETTING_AWAK_TIME,awakTime);
		
	}

	private int[] stringToTime(String value){
		int []times =null;
		try {
			times   = new int[2];
			String[] split = value.split(":");
			times[0] = Integer.valueOf(split[0]);
			times[1] = Integer.valueOf(split[1]);
		} catch (Exception e) {
			times = null;
		}
		return times;
	}
	
	/** receiver **/
	private BroadcastReceiver receiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, final Intent intent) {
			String action = intent.getAction();
			if (action.equals(BroadSender.ACTION_WATCH_SETTING)) {
					mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							if (requestSettingDialog!=null && requestSettingDialog.isShowing()) {
								requestSettingDialog.dismiss();
								requestSettingDialog = null;
							}
							
					
							if (settingDialong != null && settingDialong.isShowing()) {
								settingDialong.dismiss();
								settingDialong=null;
							}
							
							WatchSetting setting = intent.getExtras().getParcelable(BroadSender.EXTRA_WATCH_SETTING);
							XLog.e(SettingPersonalActivity.class,"手表设置 -->"+setting);
							initSettings(setting);
						}
					});

			}
		}
	};
//	private XplAlertDialog showXplDialog;
	private ProgressDialog requestSettingDialog;
	private ProgressDialog settingDialong;
	
	
}
