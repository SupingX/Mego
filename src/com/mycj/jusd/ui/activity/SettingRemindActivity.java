package com.mycj.jusd.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

import com.mycj.jusd.R;
import com.mycj.jusd.SettingSportPlanActivity;
import com.mycj.jusd.base.BaseActivity;
import com.mycj.jusd.bean.ProtolWrite;
import com.mycj.jusd.bean.StaticValue;
import com.mycj.jusd.service.JunsdaXplBluetoothService;
import com.mycj.jusd.util.SharedPreferenceUtil;
import com.mycj.jusd.view.DoubleTimeWheelDialog;
import com.mycj.jusd.view.FangTextView;
import com.mycj.jusd.view.LaputaAlertDialog;
import com.mycj.jusd.view.LaputaInputDialog;
import com.mycj.jusd.view.XplAlertDialog;

public class SettingRemindActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {

	private ImageView imgBack;
	private FangTextView tvRemindAlarm;
	private FangTextView tvRemindBirthdayDate;
	private FangTextView tvRemindBirthdayTime;
	private CheckBox cbRemindIncoming;
	private CheckBox cbRemindAlarm;
	private CheckBox cbRemindLongsit;
	private CheckBox cbRemindBirthday;
	private CheckBox cbRemindPointTime;
	private DoubleTimeWheelDialog alarmDialog;
	private DoubleTimeWheelDialog birthdayDialog;
	private XplAlertDialog dialog;
	private Handler mHandler = new Handler() {
	};
	private JunsdaXplBluetoothService xplBluetoothService;
	private XplAlertDialog dialogDisconnect;
	private ImageView imgSyncToWatch;

	private Runnable closeRunnable ;
	private LaputaAlertDialog laputaAlertDialog;
	private FangTextView tvLongSit;
	private LaputaInputDialog longSitDialog;
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_setting_remind);
	//		xplBluetoothService = getXplBluetoothService();
			initViews();
			initSettings();
			setListener();
			
		}

	public void mCheckBoxClick(View v){
		final CheckBox cb = (CheckBox) v;
		final boolean checked = cb.isChecked();
		if (1==2) {//连接状态
			
		}else{
			if (closeRunnable!=null) {
				mHandler.removeCallbacks(closeRunnable);
			}
			laputaAlertDialog = laputaAlertDialog("请先链接JUNSD运动表");
			closeRunnable = new Runnable() {
				@Override
				public void run() {
					if (laputaAlertDialog!=null && laputaAlertDialog.isShowing()) {
						laputaAlertDialog.dismiss();
						cb.setChecked(!checked);
					}
				}
			};
			mHandler.postDelayed(closeRunnable, 1000);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.img_sync_setting:
			toast("同步设置中...");
			break;
	case R.id.tv_long_sit_value:
			
			String step = tvLongSit.getText().toString();
			longSitDialog = ((LaputaInputDialog) new LaputaInputDialog(SettingRemindActivity.this).builder().setCanceledOnTouchOutside(false)).setNegativeListener("", new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					longSitDialog.dismiss();
				}
			}).setPositiveListener("", new OnClickListener() {
				@Override
				public void onClick(View v) {
					int number = longSitDialog.getNumber();
					if (number == -1) {
	
					} else {
						tvLongSit.setText(String.valueOf(number)+"m");
						SharedPreferenceUtil.put(SettingRemindActivity.this, StaticValue.SHARE_REMIND_LONG_SIT_VALUE, number);
					}
					longSitDialog.dismiss();
				}
			})
	
			.setNumber(step.equals(StaticValue.DEFAULT_GOAL)?"0":step.substring(0, step.length()-1)).setTitle("时长（m）");
			longSitDialog.show();
			break;
		case R.id.tv_remind_alarm:
			String alarmTime = (String) SharedPreferenceUtil.get(this, StaticValue.SHARE_REMIND_ALARM_TIME, "06:00");
			String[] split = alarmTime.split(":");
			int hour = Integer.valueOf(split[0]);
			int min = Integer.valueOf(split[1]);
			alarmDialog = new DoubleTimeWheelDialog(this, hour, min).builder().setPositiveButton("取消", new OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			}).setNegativeButton("确定", new OnClickListener() {

				@Override
				public void onClick(View v) {

					int hour = alarmDialog.getLeftValue();
					int min = alarmDialog.getRightValue();
					String hourStr = getStringForTwo(hour);
					String minStr = getStringForTwo(min);

					String alarmTime = hourStr + ":" + minStr;
					tvRemindAlarm.setText(alarmTime);
					SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_REMIND_ALARM_TIME, alarmTime);
				}
			});
			alarmDialog.show();

			break;
		case R.id.tv_remind_birthday_day:
			// String birthdayDate = (String) SharedPreferenceUtil.get(this,
			// StaticValue.SHARE_BIRTHDAY,"2000-01-01");
			// String[] split2 = birthdayDate.split("-");
			// String month = split2[1];
			// String day = split2[2];
			// tvRemindBirthdayDate.setText(month + "/" + day);

			// String birthdayDate = (String) SharedPreferenceUtil.get(this,
			// StaticValue.SHARE_REMIND_BIRTHDAY_DATE, "01/01");
			// String[] spliktBirhtdayDate = birthdayDate.split("/");
			// int month = Integer.valueOf(spliktBirhtdayDate[0]);
			// int day = Integer.valueOf(spliktBirhtdayDate[1]);
			// birthdayDateDialog = new DoubleDateWheelDialog(this, month,
			// day).builder().setTitle("提醒日期")
			// .setNegativeButton("取消", new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			//
			// }
			// }).setPositiveButton("确定", new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// int month = birthdayDateDialog.getLeftValue();
			// int day = birthdayDateDialog.getRightValue();
			// String monthStr = getStringForTwo(month);
			// String dayStr = getStringForTwo(day);
			// String birthday = monthStr + "/" + dayStr;
			// tvRemindBirthdayDate.setText(birthday);
			// SharedPreferenceUtil.put(getApplicationContext(),
			// StaticValue.SHARE_REMIND_BIRTHDAY_DATE, birthday);
			// }
			// });
			// birthdayDateDialog.show();

			break;
		case R.id.tv_remind_birthday_time:
			String remindBirthdayTime = (String) SharedPreferenceUtil.get(this, StaticValue.SHARE_REMIND_BIRTHDAY_TIME, "08:00");
			String[] splitRemindBirthdayTime = remindBirthdayTime.split(":");
			int hourBirthday = Integer.valueOf(splitRemindBirthdayTime[0]);
			int minBirthday = Integer.valueOf(splitRemindBirthdayTime[1]);
			birthdayDialog = new DoubleTimeWheelDialog(this, hourBirthday, minBirthday).builder().setPositiveButton("取消", new OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			}).setNegativeButton("确定", new OnClickListener() {
				@Override
				public void onClick(View v) {
					int hour = birthdayDialog.getLeftValue();
					int min = birthdayDialog.getRightValue();
					String hourStr = getStringForTwo(hour);
					String minStr = getStringForTwo(min);
					String birthday = hourStr + ":" + minStr;
					tvRemindBirthdayTime.setText(birthday);
					SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_REMIND_BIRTHDAY_TIME, birthday);
				}
			});
			birthdayDialog.show();

			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {


		switch (buttonView.getId()) {
			case R.id.cb_remind_incoming:
				SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_REMIND_INCOMING, isChecked);
				if (xplBluetoothService!=null && xplBluetoothService.isBluetoothConnected()) {
					try {
						showDialogAtSetting();
					
						byte[] writeIncoming = ProtolWrite.instance().writeIncoming(isChecked?1:0);
						xplBluetoothService.writeCharacteristic(writeIncoming);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					showDialogDisconnect();
				}
				break;
			case R.id.cb_remind_alarm:
				SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_REMIND_ALARM, isChecked);
				if (xplBluetoothService!=null && xplBluetoothService.isBluetoothConnected()) {
					try {
						showDialogAtSetting();
						String alarm = tvRemindAlarm.getText().toString();
						String[] alarmSplit = alarm.split(":");
						byte[] writeAlarm = ProtolWrite.instance().writeAlarm(isChecked?1:0, Integer.valueOf(alarmSplit[0]), Integer.valueOf(alarmSplit[1]));
						xplBluetoothService.writeCharacteristic(writeAlarm);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					showDialogDisconnect();
				}
				break;
			case R.id.cb_remind_long_sit:
				SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_REMIND_LONG_SIT, isChecked);
				if (xplBluetoothService!=null && xplBluetoothService.isBluetoothConnected()) {
					try {
						showDialogAtSetting();
						byte[] writeLongSit = ProtolWrite.instance().writeLongSit(isChecked?1:0);
						xplBluetoothService.writeCharacteristic(writeLongSit);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					showDialogDisconnect();
				}
				break;
			case R.id.cb_remind_birthday:
				SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_REMIND_BIRTHDAY, isChecked);
				if (xplBluetoothService!=null && xplBluetoothService.isBluetoothConnected()) {
					try {
						showDialogAtSetting();
						String date = tvRemindBirthdayDate.getText().toString();
						String time = tvRemindBirthdayTime.getText().toString();
						String[] dateSplit = date.split("/");
						String[] timeSplit = time.split(":");
						int month = Integer.valueOf(dateSplit[0]);
						int day = Integer.valueOf(dateSplit[1]);
						int hour = Integer.valueOf(timeSplit[0]);
						int min = Integer.valueOf(timeSplit[1]);
						byte[] writeBirthday = ProtolWrite.instance().writeBirthday(isChecked?1:0, month, day, hour, min);
						xplBluetoothService.writeCharacteristic(writeBirthday);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					showDialogDisconnect();
				}
				break;
			case R.id.cb_remind_point_time:
				SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_REMIND_POINT_TIME, isChecked);
				if (xplBluetoothService!=null && xplBluetoothService.isBluetoothConnected()) {
					try {
						showDialogAtSetting();
						byte[] writeLongPointTime = ProtolWrite.instance().writeLongPointTime(isChecked?1:0);
						xplBluetoothService.writeCharacteristic(writeLongPointTime);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					showDialogDisconnect();
				}
				break;
	
			default:
				break;
		}
	}
	
	
	private void setListener() {
		imgBack.setOnClickListener(this);
		imgSyncToWatch.setOnClickListener(this);
		tvRemindAlarm.setOnClickListener(this);
		tvRemindBirthdayTime.setOnClickListener(this);
		tvLongSit.setOnClickListener(this);
//		cbRemindLongsit.setOnCheckedChangeListener(this);
//		cbRemindIncoming.setOnCheckedChangeListener(this);
//		cbRemindAlarm.setOnCheckedChangeListener(this);
//		cbRemindBirthday.setOnCheckedChangeListener(this);
//		cbRemindPointTime.setOnCheckedChangeListener(this);
	}




	private void initSettings() {
		boolean isIncoming = (Boolean) SharedPreferenceUtil.get(this, StaticValue.SHARE_REMIND_INCOMING, false);
		boolean isAlarm = (Boolean) SharedPreferenceUtil.get(this, StaticValue.SHARE_REMIND_ALARM, false);
		boolean isLongSit = (Boolean) SharedPreferenceUtil.get(this, StaticValue.SHARE_REMIND_LONG_SIT, false);
		boolean isBirthday = (Boolean) SharedPreferenceUtil.get(this, StaticValue.SHARE_REMIND_BIRTHDAY, false);
		boolean isPointTime = (Boolean) SharedPreferenceUtil.get(this, StaticValue.SHARE_REMIND_POINT_TIME, false);
		String alarmTime = (String) SharedPreferenceUtil.get(this, StaticValue.SHARE_REMIND_ALARM_TIME, "06:00");
		String birthdayTime = (String) SharedPreferenceUtil.get(this, StaticValue.SHARE_REMIND_BIRTHDAY_TIME, "08:00");
		String birthdayDate = (String) SharedPreferenceUtil.get(this, StaticValue.SHARE_SETTING_BIRTHDAY, "2000-01-01");
		int longSit = (Integer) SharedPreferenceUtil.get(this, StaticValue.SHARE_REMIND_LONG_SIT_VALUE, 0);
		tvLongSit.setText(longSit==0?StaticValue.DEFAULT_GOAL:longSit+"m");
		tvRemindAlarm.setText(alarmTime);
		tvRemindBirthdayTime.setText(birthdayTime);
		
		Log.e("xpl", "birthdayDate :"  +birthdayDate);
		String month ="01"; 
		String day = "01";
		try {
			String[] split2 = birthdayDate.split("-");
			 month = split2[1];
			 day = split2[2];
		} catch (Exception e) {
		}
		tvRemindBirthdayDate.setText(month + "/" + day);
		
		cbRemindIncoming.setChecked(isIncoming);
		cbRemindAlarm.setChecked(isAlarm);
		cbRemindLongsit.setChecked(isLongSit);
		cbRemindBirthday.setChecked(isBirthday);
		cbRemindPointTime.setChecked(isPointTime);
	}




	private void initViews() {
		imgBack = (ImageView) findViewById(R.id.img_back);
		imgSyncToWatch = (ImageView) findViewById(R.id.img_sync_setting);
		tvRemindAlarm = (FangTextView) findViewById(R.id.tv_remind_alarm);
		tvRemindBirthdayDate = (FangTextView) findViewById(R.id.tv_remind_birthday_day);
		tvRemindBirthdayTime = (FangTextView) findViewById(R.id.tv_remind_birthday_time);
		tvLongSit = (FangTextView) findViewById(R.id.tv_long_sit_value);
		cbRemindIncoming = (CheckBox) findViewById(R.id.cb_remind_incoming);
		cbRemindAlarm = (CheckBox) findViewById(R.id.cb_remind_alarm);
		cbRemindLongsit = (CheckBox) findViewById(R.id.cb_remind_long_sit);
		cbRemindBirthday = (CheckBox) findViewById(R.id.cb_remind_birthday);
		cbRemindPointTime = (CheckBox) findViewById(R.id.cb_remind_point_time);
	
	}




	private void showDialogAtSetting(){
		if (dialog==null) {
			dialog = new XplAlertDialog(SettingRemindActivity.this).builder2("设置完成");
		}
		dialog.show();
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				dialog.cancel();
			}
		}, 2000);
	}
	
	private void showDialogDisconnect(){
		laputaAlertDialog("请先链接JUNSD运动表");
	}
}
