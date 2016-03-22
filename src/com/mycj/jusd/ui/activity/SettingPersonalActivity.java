package com.mycj.jusd.ui.activity;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.mycj.jusd.R;
import com.mycj.jusd.base.BaseActivity;
import com.mycj.jusd.bean.ProtolWrite;
import com.mycj.jusd.bean.StaticValue;
import com.mycj.jusd.bean.Unit;
import com.mycj.jusd.service.JunsdaXplBluetoothService;
import com.mycj.jusd.ui.fragment.SettingFragment;
import com.mycj.jusd.util.DataUtil;
import com.mycj.jusd.util.SharedPreferenceUtil;
import com.mycj.jusd.view.AbstraceDialog;
import com.mycj.jusd.view.DateUtil;
import com.mycj.jusd.view.DoubleTimeWheelDialog;
import com.mycj.jusd.view.FangCheckBox;
import com.mycj.jusd.view.FangRadioButton;
import com.mycj.jusd.view.FangTextView;
import com.mycj.jusd.view.LaputaInputDialog;
import com.mycj.jusd.view.NumberDialog;
import com.mycj.jusd.view.SingleWheelDialog;
import com.mycj.jusd.view.XplAlertDialog;

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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_personal);
		initViews();
		// xplBluetoothService = getXplBluetoothService();

	}

	@Override
	protected void onResume() {
		super.onResume();

	}



	@Override
	public void onClick(View v) {
		String unit = (String) getSettings(StaticValue.SHARE_SETTING_UNIT, Unit.ZH.getName());
		String title = "";
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.img_sync_setting:
			toast("同步设置中...");
			
			
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
						save(StaticValue.SHARE_SETTING_HEIGHT, number);
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
						save(StaticValue.SHARE_SETTING_WEIGHT, number);
					}
					dialogInput.dismiss();
				}
			})
	
			.setNumber(tvWeight.getText().toString()).setTitle(title);
			dialogInput.show();
			break;
		case R.id.tv_age_value:
			String birthday = (String) SharedPreferenceUtil.get(this, StaticValue.SHARE_SETTING_BIRTHDAY, "2000-1-1");
			Date date = DateUtil.stringToDate(birthday, "yyyy-MM-dd");
			if (date == null) {
				date = new Date();
			}
			birthdayDialog = new NumberDialog(this, date).builder().setNegativeButton("确定", new OnClickListener() {
				@Override
				public void onClick(View v) {
					Calendar c = birthdayDialog.getCalendar();
					String date = DateUtil.dateToString(c.getTime(), "yyyy-MM-dd");
					int age = getAgeToday(c);
					if (age < 0) {
						toast("日期选择错误");
					} else {
						String text = String.valueOf(age);
						tvAge.setText(text);
						SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_SETTING_BIRTHDAY, date);
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
					SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_SETTING_SEX, 0);
				}
			}).setNegativeButton("男", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					tvSex.setText("男");
					SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_SETTING_SEX, 1);
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
						save(StaticValue.SHARE_SETTING_SETP_LENGTH, number);
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
						save(StaticValue.SHARE_SETTING_RUN_LENGTH, number);
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
						save(StaticValue.SHARE_SETTING_HR_MAX, number);
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
						save(StaticValue.SHARE_SETTING_HR_MIN, number);
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
					SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_SETTING_SLEEP_TIME, sleepTime);
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
					SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_SETTING_AWAK_TIME, sleepTime);
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
		tvHeartRateMax = (FangTextView) findViewById(R.id.tv_hr_max_value);
		tvHeartRateMin = (FangTextView) findViewById(R.id.tv_hr_min_value);
		cbSleepOnoff = (FangCheckBox) findViewById(R.id.cb_remind_sleep);
		tvSleepTime = (FangTextView) findViewById(R.id.tv_sleep_time);
		tvWakeTime = (FangTextView) findViewById(R.id.tv_wake_time);
		// 初始化设置
		initSettings();
		// 设置Listeners
		setListener();
		

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
				save(StaticValue.SHARE_SETTING_UNIT, value);
			}
		});
		/**
		 * 2.心率报警
		 */
		cbHearRateOnoff.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				save(StaticValue.SHARE_SETTING_HR_ON_OFF, isChecked);
			}
		});

		/**
		 * 3.睡眠检测
		 */
		cbSleepOnoff.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				save(StaticValue.SHARE_SETTING_SLEEP_ON_OFF, isChecked);
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

	private void initSettings() {
		
		/** 单位 **/
		String unit  = (String) getSettings(StaticValue.SHARE_SETTING_UNIT, Unit.ZH.getName());
		if (unit.equals(Unit.ZH.getName())) {
			rbUnitZh.setChecked(true);
		}else{
			rbUnitEn.setChecked(true);
		}
		/** 身高**/
		int height = (Integer) getSettings(StaticValue.SHARE_SETTING_HEIGHT, 170);
		tvHeight.setText(String.valueOf(height));
		/** 体重**/
		int weight = (Integer) getSettings(StaticValue.SHARE_SETTING_WEIGHT, 100);
		tvWeight.setText(String.valueOf(weight));
		/** 年龄**/
		String date = (String) getSettings(StaticValue.SHARE_SETTING_BIRTHDAY, "2016-01-01");
		Date d = DateUtil.stringToDate(date, "yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int age = getAgeToday(c);
		if (age<-1) {
			
		}else{
			tvAge.setText(String.valueOf(age));
		}
		/** 性别**/
		int sex = (Integer) getSettings(StaticValue.SHARE_SETTING_SEX, 1);
		tvSex.setText(sex==0?"女":"男");
		/** 走步步长**/
		int stepLength = (Integer) getSettings(StaticValue.SHARE_SETTING_SETP_LENGTH, 10);
		tvStepLength.setText(String.valueOf(stepLength));
		/** 跑步步长**/
		int runLength = (Integer) getSettings(StaticValue.SHARE_SETTING_RUN_LENGTH, 20);
		tvRunLength.setText(String.valueOf(runLength));
		/** 心率报警**/
		boolean hrOnoff = (Boolean) getSettings(StaticValue.SHARE_SETTING_HR_ON_OFF, false);
		cbHearRateOnoff.setChecked(hrOnoff);
		/** 心率上限**/
		int heartRateMax = (Integer) getSettings(StaticValue.SHARE_SETTING_HR_MAX, 220);
		tvHeartRateMax.setText(String.valueOf(heartRateMax));
		/** 心率下限**/
		int heartRateMin = (Integer) getSettings(StaticValue.SHARE_SETTING_HR_MIN, 20);
		tvHeartRateMin.setText(String.valueOf(heartRateMin));
		/** 睡眠检测**/
		boolean sleepOnoff = (Boolean) getSettings(StaticValue.SHARE_SETTING_SLEEP_ON_OFF, false);
		cbSleepOnoff.setChecked(sleepOnoff);
		/** 睡觉时间**/
		String sleepTime = (String) getSettings(StaticValue.SHARE_SETTING_SLEEP_TIME, "22:00");
		tvSleepTime.setText(sleepTime);
		/** 起床时间**/
		String awakTime = (String) getSettings(StaticValue.SHARE_SETTING_AWAK_TIME, "08:00");
		tvWakeTime.setText(awakTime);
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
	
	private int getAgeToday(Calendar c){
		Calendar toC = Calendar.getInstance();
		toC.setTime(new Date());
		int age = toC.get(Calendar.YEAR) - c.get(Calendar.YEAR);
		return age;
	}
	
}
