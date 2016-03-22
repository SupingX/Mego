package com.mycj.jusd;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.laput.map.MapActivity;
import com.laput.service.XBlueBroadcastReceiver;
import com.laput.service.XBlueService;
import com.mycj.jusd.base.BaseActivity;
import com.mycj.jusd.bean.HistorySleep;
import com.mycj.jusd.bean.HistorySport;
import com.mycj.jusd.bean.ProtolWrite;
import com.mycj.jusd.bean.StaticValue;
import com.mycj.jusd.ui.activity.DeviceAcitivy;
import com.mycj.jusd.ui.activity.HistorySleepActivity;
import com.mycj.jusd.ui.activity.HistorySportActivity;
import com.mycj.jusd.ui.activity.SettingPersonalActivity;
import com.mycj.jusd.ui.activity.SettingRemindActivity;
import com.mycj.jusd.ui.fragment.HomeFragment;
import com.mycj.jusd.ui.fragment.MeFragment;
import com.mycj.jusd.ui.fragment.SettingFragment;
import com.mycj.jusd.util.ShareUtil;
import com.mycj.jusd.view.ActionSheetDialog;
import com.mycj.jusd.view.ActionSheetDialog.OnSheetItemClickListener;
import com.mycj.jusd.view.ActionSheetDialog.SheetItemColor;
import com.mycj.jusd.view.DateUtil;
import com.mycj.jusd.view.FangRadioButton;
import com.mycj.jusd.view.LaputaAlertDialog;
import com.mycj.jusd.view.LaputaLoadingAlertDialog;


/**
 * Created by zeej on 2015/11/19.
 */
public class MainActivity extends BaseActivity {

	private RadioGroup rgTab;
	private FangRadioButton rbHome;
	private FangRadioButton rbMe;
	private FangRadioButton rbSetting;
//	private List<Fragment> fragments;
	private HomeFragment homeFragment;
	private MeFragment meFragment;
	private SettingFragment settingFragment;
	private FragmentManager fragmentManager;
	private final int MAX_STEP = 2000;
	private int currentStep;
	private int currentTime;
	private XBlueService xBlueService;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case StaticValue.MSG_SHARE:
				String path = (String) msg.obj;
				ShareUtil.shareImage(path, MainActivity.this, "分享");
				break;

			default:
				break;
			}

		};
	};
//	private ImpXplBroadcastReceiver mReceiver = new ImpXplBroadcastReceiver() {
//
//		public void doServiceDisCovered(BluetoothDevice device) {
//			runOnUiThread(new Runnable() {
//				public void run() {
//					toast("已连接");
//					rgTab.setBackgroundColor(Color.YELLOW); // for test code
//				}
//			});
//		};
//
//		public void doConnectStateChange(BluetoothDevice device, int state) {
//			switch (state) {
//			case BluetoothGatt.STATE_DISCONNECTED:
//				rgTab.setBackgroundResource(R.color.bg_main_tab); // for test
//				break;
//			default:
//				break;
//			}
//		};
//
//		public void doSportChanged(final HistorySport sport) {
//			mHandler.post(new Runnable() {
//				@Override
//				public void run() {
//					if (sport != null) {
//						int sportTime = sport.getSportTime();
//						int step = sport.getStep();
//
//						if (homeFragment != null) {
//							homeFragment.freshCircleSport(MAX_STEP, step);
//							homeFragment.freshSportInfo(step, sportTime);
//						}
//					}
//				}
//			});
//		};
//
//		public void doHeartRateChanged(final int hr) {
//			mHandler.post(new Runnable() {
//
//				@Override
//				public void run() {
//					if (homeFragment != null) {
//						homeFragment.freshHeartRateInfo(hr);
//					}
//				}
//			});
//		};
//
//	};
	
	private XBlueBroadcastReceiver xReceiver = new XBlueBroadcastReceiver() {

		@Override
		public void doSportSyncStateChanged(int state) {

		}

		@Override
		public void doSportChanged(final HistorySport sport) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {

					Log.e("", "______________sport : " + sport);
					if (sport != null) {
						int sportTime = sport.getSportTime();
						int step = sport.getStep();

						if (homeFragment != null) {
							homeFragment.freshCircleSport(MAX_STEP, step);
							currentStep = step;
							currentTime = sportTime;
							homeFragment.freshSportInfo(step, sportTime);
						}
					}
				}
			});
		}

		@Override
		public void doSleepSyncStateChanged(int state) {

		}

		@Override
		public void doSleepChanged(HistorySleep sleep) {

		}

		@Override
		public void doServiceDiscovered(BluetoothDevice device) {
			runOnUiThread(new Runnable() {
				public void run() {
					toast(getString(R.string.connected));
					// rgTab.setBackgroundColor(Color.YELLOW); // for test code
					if (settingFragment != null) {
						settingFragment.updateSyncText(true);
					}
				}
			});
		}

		@Override
		public void doHeartRateChanged(final int hr) {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					if (homeFragment != null) {
						homeFragment.freshHeartRateInfo(hr);
					}
				}
			});
		}

		@Override
		public void doDeviceFound(ArrayList<BluetoothDevice> devices) {

		}

		@Override
		public void doConnectStateChange(BluetoothDevice device, final int state) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					switch (state) {
					case BluetoothGatt.STATE_DISCONNECTED:
						rgTab.setBackgroundResource(R.color.bg_main_tab); // for
						if (settingFragment != null) {
							settingFragment.updateSyncText(false);
						}
						break;
					default:
						break;
					}
				}
			});
		}

		@Override
		public void doBluetoothEnable() {

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		xBlueService = getXBlueService();
		fragmentManager = getSupportFragmentManager();
		initFragments();
		initViews();
		setListener();
		rgTab.check(R.id.rb_tab_home);
	}

	@Override
	protected void onStart() {
		super.onStart();
//		registerReceiver(mReceiver, XplBluetoothService.getIntentFilter());
	}

	@Override
	protected void onResume() {
		super.onResume();
		startScan();
	}

	private void startScan() {
		/*if (xplBluetoothService == null) {
			return;
		}
		if (xplBluetoothService.getCurrentAddress() == null || xplBluetoothService.getCurrentAddress().equals("")) {
			return;
		}
		if (!xplBluetoothService.isBluetoothConnected()) {
			xplBluetoothService.scanDevice(true);
		}*/
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		unregisterReceiver(mReceiver);
	}

	private void initFragments() {
		homeFragment = new HomeFragment();

		homeFragment.setOnHomeFragmentClickListener(new HomeFragment.OnHomeFragmentClickListener() {
			@Override
			public void doShare() {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						share(mHandler);
					}
				});

				// List<HistorySport> findAll =
				// DataSupport.findAll(HistorySport.class);
				//
				// if (findAll != null) {
				// Log.e("", "------------------------");
				// for (HistorySport s : findAll) {
				//
				// Log.e("", s.toString());
				// }
				// Log.e("", "------------------------");
				// }

			}

			@Override
			public void doRefresh() {
				Date date = new Date();
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				for (int i = 0; i < 30; i++) {
					c.set(Calendar.DAY_OF_MONTH, i + 1);
					String dateStr = DateUtil.dateToString(c.getTime(), "yyyyMMdd");
					HistorySport sport = new HistorySport();
					int time = (int) (Math.random() * 60 * 24);
					int steps = (int) (Math.random() * 1000);
					sport.setDate(dateStr);
					sport.setSportTime(time);
					sport.setStep(steps);
					sport.save();
				}

				for (int i = 0; i < 30; i++) {
					c.set(Calendar.DAY_OF_MONTH, i + 1);
					String dateStr = DateUtil.dateToString(c.getTime(), "yyyyMMdd");
					HistorySleep sleep = new HistorySleep();
					int deep = (int) (Math.random() * 60 * 12);
					int light = (int) (Math.random() * 60 * 12);
					sleep.setDate(dateStr);
					sleep.setDeep(deep);
					sleep.setLight(light);
					sleep.save();
				}

//				Toast.makeText(getApplicationContext(), "刷新", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void doSetStepUi(boolean isChecked) {
//				Toast.makeText(getApplicationContext(), "stepUI", Toast.LENGTH_SHORT).show();
//				if (isChecked) {
//					homeFragment.freshInfos("距离", "用时", "步");
//				}
				
				homeFragment.freshSportInfo(currentStep, currentTime);
			}

			@Override
			public void doSetPathUi(boolean isChecked) {
//				if (isChecked) {
//					homeFragment.freshInfos("用时", "平均配速", "公里");
//				}
//				Toast.makeText(getApplicationContext(), "pathUI", Toast.LENGTH_SHORT).show();
				homeFragment.freshSportInfo(currentStep, currentTime);
			}

			@Override
			public void doSportClick() {
				Intent intent  = new Intent(MainActivity.this,MapActivity.class);
				startActivity(intent);
			}
		});
		meFragment = new MeFragment();
		meFragment.setOnMeFragmentClickListener(new MeFragment.OnMeFragmentClickListener() {
			@Override
			public void doLookSleepHistory() {
				Intent intent = new Intent(MainActivity.this, HistorySleepActivity.class);
				startActivity(intent);
			}

			@Override
			public void doLookSportHistory() {
				Intent intent = new Intent(MainActivity.this, HistorySportActivity.class);
				startActivity(intent);
			}
		});

		settingFragment = new SettingFragment();
		settingFragment.setOnSettingFragmentListener(new SettingFragment.OnSettingFragmentListener() {
			private LaputaLoadingAlertDialog loadDialog;

			@Override
			public void onClick(View v) {
				Intent intent = null;
				switch (v.getId()) {
				case R.id.rl_setting_device:
					intent = new Intent(getApplicationContext(), DeviceAcitivy.class);
					break;
				case R.id.rl_setting_information:
					intent = new Intent(getApplicationContext(), SettingPersonalActivity.class);
					break;
				case R.id.rl_setting_sync:
					// intent = new Intent(getApplicationContext(),
					// SettingSyncDataActivity.class);
					/*
					 * 判断是否连接，决定是否加载同步
					 */
//					if (xBlueService != null && xBlueService.isAllConnected()) {
					if (true) {
						xBlueService.write(ProtolWrite.instance().writeForSyncStep());
						loadDialog = new LaputaLoadingAlertDialog(MainActivity.this)
								.builder("")
								.max(200)
								.setListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										loadDialog.dismiss();
									}
								}).duration(5000);
						loadDialog.show();
//						loadDialog.start();
						xBlueService.write(new byte[][]{ProtolWrite.instance().writeForSyncSleep(),ProtolWrite.instance().writeForSyncStep()});
					}else{
						LaputaAlertDialog dialog = new LaputaAlertDialog(MainActivity.this).builder("请先链接JUNSD运动表");
						dialog.show();
					}
					break;
				case R.id.rl_setting_sport_plan:
//					Toast.makeText(getApplicationContext(), "个人提醒", Toast.LENGTH_SHORT).show();
					intent = new Intent(getApplicationContext(), SettingSportPlanActivity.class);
					break;
				case R.id.rl_setting_remind_person:
//					Toast.makeText(getApplicationContext(), "个人提醒", Toast.LENGTH_SHORT).show();
					intent = new Intent(getApplicationContext(), SettingRemindActivity.class);
					break;
				case R.id.rl_setting_about:
//					Toast.makeText(getApplicationContext(), "关于产品", Toast.LENGTH_SHORT).show();
					
					intent = new Intent(getApplicationContext(), AboutActivity.class);
//					intent = new Intent(getApplicationContext(), TestActivity.class);
					
					break;
				}
				if (intent != null) {
					startActivity(intent);
				}
			}

		});

//		fragments = new ArrayList<Fragment>();
//		fragments.add(homeFragment);
//		fragments.add(meFragment);
//		fragments.add(settingFragment);
	}

	private void initViews() {
		rgTab = (RadioGroup) findViewById(R.id.rg_tab);
		rbHome = (FangRadioButton) findViewById(R.id.rb_tab_home);
		rbMe = (FangRadioButton) findViewById(R.id.rb_tab_me);
		rbSetting = (FangRadioButton) findViewById(R.id.rb_tab_setting);

	}

	private void setListener() {
		rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i) {
				FragmentTransaction transaction = fragmentManager.beginTransaction();
				switch (i) {
				case R.id.rb_tab_home:
					if (homeFragment == null) {
						homeFragment = new HomeFragment();
					}
					if (homeFragment.isAdded()) {
						transaction.show(homeFragment);
					}else{
					transaction.replace(R.id.frame_main, homeFragment, HomeFragment.class.getSimpleName());
					}
					/*if (xplBluetoothService != null) {
						xplBluetoothService.writeCharacteristic(DataUtil.getBytesByString("45"));
					}*/
					break;
				case R.id.rb_tab_me:
					if (meFragment == null) {
						meFragment = new MeFragment();
					}
					if (meFragment.isAdded()) {
						transaction.show(meFragment);
					}else{
					transaction.replace(R.id.frame_main, meFragment, MeFragment.class.getSimpleName());
					}
					break;
				case R.id.rb_tab_setting:
					if (settingFragment == null) {
						settingFragment = new SettingFragment();
					}
					if (settingFragment.isAdded()) {
						settingFragment = new SettingFragment();
					}else{
						transaction.replace(R.id.frame_main, settingFragment, SettingFragment.class.getSimpleName());
					}
					break;
				}
				transaction.commit();
			}
		});
	}

	@Override
	public void onBackPressed() {
		ActionSheetDialog exitDialog = new ActionSheetDialog(this).builder();
		exitDialog.setTitle("退出App？");
		exitDialog.addSheetItem(getString(R.string.confirm), SheetItemColor.Red, new OnSheetItemClickListener() {
			@Override
			public void onClick(int which) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
					/*	if (xplBluetoothService != null) {
							xplBluetoothService.close();
						}*/
						mHandler.postDelayed(new Runnable() {
							@Override
							public void run() {
								finish();
								System.exit(0);
								// android.os.Process.killProcess(android.os.Process.myPid());

							}
						}, 1000);
					}
				});

			}
		}).show();

	}
}
