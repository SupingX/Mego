package com.mycj.jusd;

import com.mycj.jusd.base.BaseActivity;
import com.mycj.jusd.bean.StaticValue;
import com.mycj.jusd.ui.activity.SettingPersonalActivity;
import com.mycj.jusd.util.SharedPreferenceUtil;
import com.mycj.jusd.view.AlphaImageView;
import com.mycj.jusd.view.DoubleTimeWheelDialog;
import com.mycj.jusd.view.FangCheckBox;
import com.mycj.jusd.view.FangRadioButton;
import com.mycj.jusd.view.FangTextView;
import com.mycj.jusd.view.LaputaInputDialog;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
/**
 * Setting运动计划
 * @author Administrator
 *
 */
public class SettingSportPlanActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {

	private FangCheckBox cbSportPlan;
	private FangTextView tvSportPlanTime;
	private FangCheckBox cbSportGoal;
	private FangTextView tvSportGoalStep;
	private FangTextView tvSportGoalDistance;
	private LinearLayout llSportGoalDistance;
	private View lineSportGoalDistance;
	private FangTextView tvAutoSign;
	private FangCheckBox cbAutoSignOnoff;
	private FangCheckBox cbPaceOnoff;
	private FangTextView tvPaceMax;
	private FangTextView tvPaceMin;
	private RadioGroup rgSportStyle;
	private FangRadioButton rbWalkStyle;
	private FangRadioButton rbRunStyle;
	private FangRadioButton rbCycleStyle;
	private AlphaImageView imgBack;
	private AlphaImageView imgSyncSetting;
	private DoubleTimeWheelDialog sportPlanTimeDialog;
	private LaputaInputDialog dialogInput;
	private DoubleTimeWheelDialog sportGoalDistanceDialog;
	
	/**
	 * Gps未开启时，此处只有步数选项
	 * GPS开启时，选择一个选项时，另外一个选项值为---
	 */
	private boolean isGpsOn = true;
	private boolean isSelectGoalTypeStep = true;
	private DoubleTimeWheelDialog autoSignDialog;
	private DoubleTimeWheelDialog paceMaxDialog;
	private DoubleTimeWheelDialog paceMinDialog;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_sport_plan);
		initViews();
		initSettings();
		setListener();
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
		case R.id.tv_sport_plan_time_value:
			int sleepHour = 6;
			int sleepMin = 0;
			String sleep = tvSportPlanTime.getText().toString();
			int[] times = stringToTime(sleep);
			if (times!=null) {
				sleepHour = times[0];
				sleepMin = times[1];
			}
			
			sportPlanTimeDialog = new DoubleTimeWheelDialog(this, sleepHour, sleepMin).builder("时","分").setPositiveButton("取消", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
	
				}
			}).setNegativeButton("确定", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					int hour = sportPlanTimeDialog.getLeftValue();
					int min = sportPlanTimeDialog.getRightValue();
					String hourStr = getStringForTwo(hour);
					String minStr = getStringForTwo(min);
					String time = hourStr + ":" + minStr;
					tvSportPlanTime.setText(time);
					SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_SPORT_PLAN_TIME, time);
				}
			});
			sportPlanTimeDialog.show();
			break;
		case R.id.tv_sport_goal_step_value:
			
			String step = tvSportGoalStep.getText().toString();
			if (isGpsOn) {
				tvSportGoalDistance.setText(StaticValue.DEFAULT_GOAL);
				SharedPreferenceUtil.put(SettingSportPlanActivity.this, StaticValue.SHARE_SPORT_GOAL_DISTANCE, StaticValue.DEFAULT_GOAL);
			}
			dialogInput = ((LaputaInputDialog) new LaputaInputDialog(SettingSportPlanActivity.this).builder().setCanceledOnTouchOutside(false)).setNegativeListener("", new OnClickListener() {
				
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
						tvSportGoalStep.setText(String.valueOf(number));
						SharedPreferenceUtil.put(SettingSportPlanActivity.this, StaticValue.SHARE_SPORT_GOAL_STEP, number);
					}
					dialogInput.dismiss();
				}
			})
	
			.setNumber(step.equals(StaticValue.DEFAULT_GOAL)?"0":step).setTitle("步数（步）");
			dialogInput.show();
			break;
		case R.id.tv_sport_goal_distance_value:
			
			if (isGpsOn) {
				tvSportGoalStep.setText(StaticValue.DEFAULT_GOAL);
				SharedPreferenceUtil.put(SettingSportPlanActivity.this, StaticValue.SHARE_SPORT_GOAL_DISTANCE, 0);
			}
			
			int distanceInt = 5;
			int distanceFloat = 0;
			String distance = tvSportGoalDistance.getText().toString();
			int[] distances = stringToDistance(distance);
			if (distances!=null) {
				distanceInt = distances[0];
				distanceFloat = distances[1];
			}
			Log.e("xpl", "distanceInt :" + distanceInt +",distanceFloat :" + distanceFloat);
			
			sportGoalDistanceDialog = new DoubleTimeWheelDialog(this, distanceInt, distanceFloat).builder(9,9).setPositiveButton("取消", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
	
				}
			}).setNegativeButton("确定", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					int hour = sportGoalDistanceDialog.getLeftValue();
					int min = sportGoalDistanceDialog.getRightValue();
					String hourStr = String.valueOf(hour);
					String minStr = String.valueOf(min);
					String time = hourStr + "." + minStr;
					tvSportGoalDistance.setText(time);
					SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_SPORT_GOAL_DISTANCE, time);
				}
			});
			sportGoalDistanceDialog.show();
			break;
	
		case R.id.tv_setting_auto_value:
			int autoInt = 5;
			int autoFloat = 0;
			String autoDistance = tvAutoSign.getText().toString();
			int[] autoDistances = stringToDistance(autoDistance);
			if (autoDistances!=null) {
				autoInt = autoDistances[0];
				autoFloat = autoDistances[1];
			}
			
			autoSignDialog = new DoubleTimeWheelDialog(this, autoInt, autoFloat).builder(9,9).setPositiveButton("取消", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
	
				}
			}).setNegativeButton("确定", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					int hour = autoSignDialog.getLeftValue();
					int min = autoSignDialog.getRightValue();
					String hourStr = String.valueOf(hour);
					String minStr = String.valueOf(min);
					String time = hourStr + "." + minStr;
					tvAutoSign.setText(time);
					SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_SPORT_AUTO_SIGN, time);
				}
			});
			autoSignDialog.show();
			break;
			
		case R.id.tv_pace_max_value:
			int paceMaxLeftValue = 5;
			int paceMaxRightValue = 0;
			String paceMax = tvPaceMax.getText().toString();
			int[] paceMaxs = parsePaceString(paceMax);
			if (paceMaxs!=null) {
				paceMaxLeftValue = paceMaxs[0];
				paceMaxRightValue = paceMaxs[1];
			}
			
			paceMaxDialog = new DoubleTimeWheelDialog(this, paceMaxLeftValue, paceMaxRightValue).builder(9,"'",9,"\"").setPositiveButton("取消", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
	
				}
			}).setNegativeButton("确定", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					int hour = paceMaxDialog.getLeftValue();
					int min = paceMaxDialog.getRightValue();
					String hourStr = String.valueOf(hour);
					String minStr = String.valueOf(min);
					String time = hourStr + "'" + minStr+"\"";
					tvPaceMax.setText(time);
					SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_SPORT_PACE_MAX, time);
				}
			});
			paceMaxDialog.show();
			break;
		case R.id.tv_pace_min_value:
			int paceMinLeftValue = 5;
			int paceMinRightValue = 0;
			String paceMin = tvPaceMin.getText().toString();
			int[] paceMins = parsePaceString(paceMin);
			if (paceMins!=null) {
				paceMinLeftValue = paceMins[0];
				paceMinRightValue = paceMins[1];
			}
			
			paceMinDialog = new DoubleTimeWheelDialog(this, paceMinLeftValue, paceMinRightValue).builder(9,"'",9,"\"").setPositiveButton("取消", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
	
				}
			}).setNegativeButton("确定", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					int hour = paceMinDialog.getLeftValue();
					int min = paceMinDialog.getRightValue();
					String hourStr = String.valueOf(hour);
					String minStr = String.valueOf(min);
					String time = hourStr + "'" + minStr+"\"";
					tvPaceMin.setText(time);
					SharedPreferenceUtil.put(getApplicationContext(), StaticValue.SHARE_SPORT_PACE_MIN, time);
				}
			});
			paceMinDialog.show();
			break;
			
		default:
			break;
		}
	}

	

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		int id = buttonView.getId();
		String text ="";
		if (id == R.id.cb_sport_plan_on_off) {
			SharedPreferenceUtil.put(SettingSportPlanActivity.this, StaticValue.SHARE_SPORT_PLAN_ON_OFF, isChecked);
			text ="运动计划" ;
		}else if(id == R.id.cb_sport_goal){
			SharedPreferenceUtil.put(SettingSportPlanActivity.this, StaticValue.SHARE_SPORT_GOAL_ON_OFF, isChecked);
			text ="运动目标" ;
		}else if(id == R.id.cb_auto_sign_on_off){
			SharedPreferenceUtil.put(SettingSportPlanActivity.this, StaticValue.SHARE_SPORT_AUTO_SIGN_ON_OFF, isChecked);
			text ="自动签到" ;
		}else if(id == R.id.cb_pace_on_off){
			SharedPreferenceUtil.put(SettingSportPlanActivity.this, StaticValue.SHARE_SPORT_PACE_ON_OFF, isChecked);
			text ="配速设置" ;
		}
		toast(text +(isChecked?"：开":"：关"));
	}

	private void setListener() {
		tvSportPlanTime.setOnClickListener(this);
		tvSportGoalStep.setOnClickListener(this);
		tvSportGoalDistance.setOnClickListener(this);
		tvAutoSign.setOnClickListener(this);
		tvPaceMax.setOnClickListener(this);
		tvPaceMin.setOnClickListener(this);
		imgBack.setOnClickListener(this);
		imgSyncSetting.setOnClickListener(this);
		//
		cbAutoSignOnoff.setOnCheckedChangeListener(this);
		cbPaceOnoff.setOnCheckedChangeListener(this);
		cbSportGoal.setOnCheckedChangeListener(this);
		cbSportPlan.setOnCheckedChangeListener(this);
		//
		rgSportStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int sportStyle = 0;
				if (checkedId == R.id.rb_style_walk) {
//					rbWalkStyle.setChecked(true);
					toast("切换运动模式-->步行");
					sportStyle= 0;
				} else if (checkedId == R.id.rb_style_run) {
//					rbRunStyle.setChecked(true);
					toast("切换运动模式-->跑步");
					sportStyle=1;
				} else if (checkedId == R.id.rb_style_cycle) {
//					rbCycleStyle.setChecked(true);
					toast("切换运动模式-->骑行");
					sportStyle=2;
				}
				SharedPreferenceUtil.put(SettingSportPlanActivity.this, StaticValue.SHARE_SPORT_STYLE, sportStyle);
			}
		});
		
	}

	private void initSettings() {
		/** 运动计划  **/
		boolean sportPlanOnoff = (Boolean) SharedPreferenceUtil.get(this, StaticValue.SHARE_SPORT_PLAN_ON_OFF, false);
		cbSportPlan.setChecked(sportPlanOnoff);
		/** 起始时间  **/
		String sportPlanTime = (String) SharedPreferenceUtil.get(this, StaticValue.SHARE_SPORT_PLAN_TIME, StaticValue.DEFAULT_GOAL);
		tvSportPlanTime.setText(sportPlanTime);
		/** 运动目标  **/
		boolean sportGoalOnoff = (Boolean) SharedPreferenceUtil.get(this, StaticValue.SHARE_SPORT_GOAL_ON_OFF, false);
		cbSportGoal.setChecked(sportGoalOnoff);
		/** 计步步数  **/
		/** 轨迹距离  **/
		int goalStep = (Integer) SharedPreferenceUtil.get(this, StaticValue.SHARE_SPORT_GOAL_STEP, 0);
		String goalDistance = (String) SharedPreferenceUtil.get(this, StaticValue.SHARE_SPORT_GOAL_DISTANCE, StaticValue.DEFAULT_GOAL);
		tvSportGoalStep.setText(goalStep==0 ? StaticValue.DEFAULT_GOAL : String.valueOf(goalStep));
		tvSportGoalDistance.setText(goalDistance);
		hideWhenGpsChanged(isGpsOn);
		/** 自动签到  **/
		String autoSign = (String) SharedPreferenceUtil.get(this, StaticValue.SHARE_SPORT_AUTO_SIGN, StaticValue.DEFAULT_GOAL);
		tvAutoSign.setText(autoSign);
		boolean autoSignOnoff = (Boolean) SharedPreferenceUtil.get(this, StaticValue.SHARE_SPORT_AUTO_SIGN_ON_OFF, false);
		cbAutoSignOnoff.setChecked(autoSignOnoff);
		/** 配速设置  **/
		boolean paceOnoff = (Boolean) SharedPreferenceUtil.get(this, StaticValue.SHARE_SPORT_PACE_ON_OFF, false);
		cbPaceOnoff.setChecked(paceOnoff);
		/** 最慢配速  **/
		String paceMin = (String) SharedPreferenceUtil.get(this, StaticValue.SHARE_SPORT_PACE_MIN, StaticValue.DEFAULT_GOAL);
		tvPaceMin.setText(paceMin);
		/** 最快配速  **/
		String paceMax = (String) SharedPreferenceUtil.get(this, StaticValue.SHARE_SPORT_PACE_MAX, StaticValue.DEFAULT_GOAL);
		tvPaceMax.setText(paceMax);
		/** 运动类型  **/
		int sportStyle = (Integer) SharedPreferenceUtil.get(this, StaticValue.SHARE_SPORT_STYLE, 0);
		if (sportStyle == 0) {
			rbWalkStyle.setChecked(true);
		} else if (sportStyle == 1) {
			rbRunStyle.setChecked(true);
		}else if (sportStyle == 2) {
			rbCycleStyle.setChecked(true);
		}
		
	}
	
	private void hideWhenGpsChanged(boolean isGpsOn){
		if (isGpsOn) {
			llSportGoalDistance.setVisibility(View.VISIBLE);
			lineSportGoalDistance.setVisibility(View.VISIBLE);
		}else{
			llSportGoalDistance.setVisibility(View.GONE);
			lineSportGoalDistance.setVisibility(View.VISIBLE);
		}
	}
	
	private void initViews() {
		imgBack = (AlphaImageView) findViewById(R.id.img_back);
		imgSyncSetting = (AlphaImageView) findViewById(R.id.img_sync_setting);
		//第一格
		cbSportPlan = (FangCheckBox) findViewById(R.id.cb_sport_plan_on_off);
		tvSportPlanTime = (FangTextView) findViewById(R.id.tv_sport_plan_time_value);
		//第二格
		cbSportGoal = (FangCheckBox) findViewById(R.id.cb_sport_goal);
		tvSportGoalStep = (FangTextView) findViewById(R.id.tv_sport_goal_step_value);
		tvSportGoalDistance = (FangTextView) findViewById(R.id.tv_sport_goal_distance_value);
		llSportGoalDistance = (LinearLayout) findViewById(R.id.ll_sport_goal_distance);
		lineSportGoalDistance = findViewById(R.id.line_sport_goal_distance);
		//第三格
		tvAutoSign = (FangTextView) findViewById(R.id.tv_setting_auto_value);
		cbAutoSignOnoff = (FangCheckBox) findViewById(R.id.cb_auto_sign_on_off);
		//第四格
		cbPaceOnoff = (FangCheckBox) findViewById(R.id.cb_pace_on_off);
		tvPaceMax = (FangTextView) findViewById(R.id.tv_pace_max_value);
		tvPaceMin = (FangTextView) findViewById(R.id.tv_pace_min_value);
		//第五格
		rgSportStyle = (RadioGroup) findViewById(R.id.rg_sport_style);
		rbWalkStyle = (FangRadioButton) findViewById(R.id.rb_style_walk);
		rbRunStyle = (FangRadioButton) findViewById(R.id.rb_style_run);
		rbCycleStyle = (FangRadioButton) findViewById(R.id.rb_style_cycle);
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
	
	private int[] stringToDistance(String distance) {
		int []distances =null;
		try {
			distances   = new int[2];
			String[] split = distance.split(".");
			distances[0] = Integer.valueOf(split[0]);
			distances[1] = Integer.valueOf(split[1]);
		} catch (Exception e) {
			distances =null;
		}
		return distances;
	}
	
	private int[] parsePaceString(String pace){
		int []distances =null;
		try {
			distances   = new int[2];
			int one = pace.indexOf("'");
			int two = pace.indexOf("\"");
			distances[0] = Integer.valueOf(pace.substring(0,one));
			distances[1] = Integer.valueOf(pace.substring(one+1,two));
		} catch (Exception e) {
			distances =null;
		}
		return distances;
	}
}
