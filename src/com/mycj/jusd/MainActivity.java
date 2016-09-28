package com.mycj.jusd;

import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;

import com.laputa.blue.broadcast.LaputaBroadcast;
import com.laputa.blue.broadcast.LaputaBroadcastReceiver;
import com.laputa.blue.core.AbstractSimpleLaputaBlue;
import com.laputa.blue.util.DataUtil;
import com.mycj.jusd.base.BaseActivity;
import com.mycj.jusd.bean.JunConstant;
import com.mycj.jusd.bean.LitePalManager;
import com.mycj.jusd.bean.RemindSetting;
import com.mycj.jusd.bean.WatchSetting;
import com.mycj.jusd.bean.news.SleepHistory;
import com.mycj.jusd.bean.news.SportHistory;
import com.mycj.jusd.bean.news.SportPlanSetting;
import com.mycj.jusd.protocol.AbstractProtolNotify;
import com.mycj.jusd.protocol.ProtocolWriteManager;
import com.mycj.jusd.protocol.ProtocolNotifyManager;
import com.mycj.jusd.protocol.ProtocolNotifyManager.OnDataParseResuletListener;
import com.mycj.jusd.service.BroadSender;
import com.mycj.jusd.ui.activity.DeviceAcitivy;
import com.mycj.jusd.ui.activity.HistorySleepActivity;
import com.mycj.jusd.ui.activity.HistorySportActivity;
import com.mycj.jusd.ui.activity.SettingPersonalActivity;
import com.mycj.jusd.ui.activity.SettingRemindActivity;
import com.mycj.jusd.ui.activity.SettingSportPlanActivity;
import com.mycj.jusd.ui.fragment.HomeFragment;
import com.mycj.jusd.ui.fragment.MeFragment;
import com.mycj.jusd.ui.fragment.SettingFragment;
import com.mycj.jusd.util.DateUtil;
import com.mycj.jusd.util.MilkUtil;
import com.mycj.jusd.util.ShareUtil;
import com.mycj.jusd.util.SharedPreferenceUtil;
import com.mycj.jusd.view.ActionSheetDialog;
import com.mycj.jusd.view.ActionSheetDialog.OnSheetItemClickListener;
import com.mycj.jusd.view.ActionSheetDialog.SheetItemColor;
import com.mycj.jusd.view.FangRadioButton;
import com.mycj.jusd.view.LaputaAlertDialog;
import com.mycj.jusd.view.LaputaLoadingAlertDialog;

/**
 * Created by zeej on 2015/11/19.
 * 
 */
public class MainActivity extends BaseActivity {
	private LaputaLoadingAlertDialog loadDialog;
	private RadioGroup rgTab;
	private FangRadioButton rbHome;
	private FangRadioButton rbMe;
	private FangRadioButton rbSetting;
	private HomeFragment homeFragment;
	private MeFragment meFragment;
	private SettingFragment settingFragment;
	private FragmentManager fragmentManager;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case JunConstant.MSG_SHARE:
				String path = (String) msg.obj;
				ShareUtil.shareImage(path, MainActivity.this, "分享");
			
//				startActivity(new Intent(MainActivity.this,TestActivity.class));

				//				String dateStringFromTimeMilloons = DateUtil.getDateStringFromTimeMilloons(Integer.valueOf("19557308", 16)* 1000L,"yyyy/MM/dd HH:mm:ss EEEE");
//				XLog.e("_________vdateStringFromTimeMilloons" + dateStringFromTimeMilloons);
				// 030100AA007802000A0A01D2150116000800
				// 030100520078020000720072000116000800FF00
				// 步数
				
				// 051f 0d00 0000010108 00
				// 0555 0D00 0000010108 00
				
				//04 0F 0800 000000 006B 0052 00F0 012C 00 00000000
				//04 55 0800 000000 006B 0052 00F0 012C 00 00000000
				
				//03 01 00AA 0078 02 00 000A 000A 07 15 D2 1600 0800 00
				//03 01 0052 0078 02 00 000A 000A 00 15 D2 1600 0800 00
				
				//030100AA00780200000A000A 07 15D21600080000
				//0301005200780200000A000A 00 15D21600080000
				
				//030100AA00780200000A000A 07 15D21600080000
				//0301005200780200000A000A 00 15D21600080000
				
//				MilkUtil.saveSportHistoryList();
//				List<SportHistory> findAll = DataSupport.findAll(SportHistory.class);
			
				
			/*	
			 	List<SportHistory> findAll = LitePalManager.instance().getSportHistoryListByMonth(new Date());
				if (findAll!=null) {
					Log.e("", "findAll :" + findAll.size());
					for (int i = 0; i < findAll.size(); i++) {
						Log.e("", findAll.get(i).toString());
					}
				}else{
					Log.e("", "findAll为空 " );
				}
				
				*/
				
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		isDebug = true;
		fragmentManager = getSupportFragmentManager();
		initFragments();
		initViews();
		setListener();
		rgTab.check(R.id.rb_tab_home);
		registerReceiver(receiver, BroadSender.getIntentFilter());
		registerReceiver(bleReceiver, LaputaBroadcast.getIntentFilter());
		
	
		boolean isFirst = (boolean) SharedPreferenceUtil.get(this, "isFirst", true);
		if (isFirst) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					MilkUtil.saveSportHistoryList();
					SharedPreferenceUtil.put(MainActivity.this, "isFirst", false);
				}
			}).start();

		}
	}

	

	@Override
	protected void onResume() {
		super.onResume();
		syncWatchSettingAndCurrentSport();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		unregisterReceiver(bleReceiver);
		super.onDestroy();
	}

	private void initFragments() {
		// 主页
		homeFragment = new HomeFragment();
		homeFragment
				.setOnHomeFragmentClickListener(new HomeFragment.OnHomeFragmentClickListener() {
					@Override
					public void doShare() {
						// 分享
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								share(mHandler);
							}
						});
						
						// 测试数据 
//						MilkUtil.saveSportHistoryList();
//						showTest();
						
						// 测试 ShowView
//						Intent intent = new Intent(MainActivity.this,TestActivity.class);
//						startActivity(intent);
						
					}

				

					@Override
					public void doRefresh() {
						// 此页面中意为同步，智能手表与手机连接后点击，可将手表中设置和测量的最新数据同步到app中（725app规格书.pdf）
						syncWatchSettingAndCurrentSport();
//						testForLoadData();
//						if (getXBlueService()!=null) {
//							getXBlueService().write(JsdProtolWrite.getInstance().getByteForRequstWatchConfigration(5));
//						}
//						051F022100DE050D020E
//						0555022100DE050D020E
					}

					@Override
					public void doSetStepUi(boolean isChecked) {
						// Toast.makeText(getApplicationContext(), "stepUI",
						// Toast.LENGTH_SHORT).show();
						// if (isChecked) {
						// homeFragment.freshInfos("距离", "用时", "步");
						// }

					}

					@Override
					public void doSetPathUi(boolean isChecked) {
						// if (isChecked) {
						// homeFragment.freshInfos("用时", "平均配速", "公里");
						// }
						// Toast.makeText(getApplicationContext(), "pathUI",
						// Toast.LENGTH_SHORT).show();
					}

					@Override
					public void doSportClick(int type) {
						// Intent intent = new
						// Intent(MainActivity.this,MapActivity.class);
						if (type == 0) {
							Intent intent = new Intent(MainActivity.this,
									StepStatisticsActivity.class);
							Date date = new Date ();
							String sportDate = DateUtil.dateToString(date, "yyyyMMdd");
							String sportTime = DateUtil.dateToString(date, "hhmmss");
							SportHistory historySport = new SportHistory();
							if (info!=null) {
								historySport.setType(info.getType());
								historySport.setSportIndex(info.getSportIndex());
							}
								historySport.setSportDate(sportDate);
								historySport.setSportTime(sportTime);
								Bundle data = new Bundle();
								data.putParcelable(	JunConstant.INTENT_SPORT_HISTORY, historySport);
								intent.putExtras(data);
							
							startActivity(intent);
						} else {
							Intent intent = new Intent(MainActivity.this,
									PathStatisticsActivity.class);
							Date date = new Date ();
							String sportDate = DateUtil.dateToString(date, "yyyyMMdd");
							String sportTime = DateUtil.dateToString(date, "hhmmss");
							SportHistory historySport = new SportHistory();
							if (info!=null) {
								historySport.setType(info.getType());
								historySport.setSportIndex(info.getSportIndex());
							}
								historySport.setSportDate(sportDate);
								historySport.setSportTime(sportTime);
								Bundle data = new Bundle();
								data.putParcelable(	JunConstant.INTENT_SPORT_HISTORY, historySport);
								intent.putExtras(data);
						
							startActivity(intent);
						}
					}
				});

		// 我的
		meFragment = new MeFragment();
		meFragment
				.setOnMeFragmentClickListener(new MeFragment.OnMeFragmentClickListener() {
					@Override
					public void doLookSleepHistory() {
						Intent intent = new Intent(MainActivity.this,
								HistorySleepActivity.class);
						startActivity(intent);
					}

					@Override
					public void doLookSportHistory() {
						Intent intent = new Intent(MainActivity.this,
								HistorySportActivity.class);
						startActivity(intent);
					}
				});

		// 设置
		settingFragment = new SettingFragment();
		settingFragment
				.setOnSettingFragmentListener(new SettingFragment.OnSettingFragmentListener() {

		
					@Override
					public void onClick(View v) {
						Intent intent = null;
						switch (v.getId()) {
						case R.id.rl_setting_device:
							intent = new Intent(getApplicationContext(),
									DeviceAcitivy.class);
							break;
						case R.id.rl_setting_information:
							intent = new Intent(getApplicationContext(),
									SettingPersonalActivity.class);
							break;
						case R.id.rl_setting_sync:
							// intent = new Intent(getApplicationContext(),
							// SettingSyncDataActivity.class);
							/*
							 * 判断是否连接，决定是否加载同步
							 */
							// if (xBlueService != null &&
							// xBlueService.isAllConnected()) {
							if (getXBlueService()!=null && getXBlueService().isAllConnect()) {
								loadDialog = new LaputaLoadingAlertDialog(
										MainActivity.this).builder("").max(200)
										.setListener(new OnClickListener() {

											@Override
											public void onClick(View v) {
												loadDialog.dismiss();
											}
										}).duration(5000);
								loadDialog.show();
								getXBlueService().write(new byte[][] {
										ProtocolWriteManager.getInstance().
												getByteForRequestHistorySleep(),
												ProtocolWriteManager.getInstance()
												.getByteForRequestHistorySportInfo() });
							} else {
								LaputaAlertDialog dialog = new LaputaAlertDialog(
										MainActivity.this)
										.builder("请先链接JUNSD运动表");
								dialog.show();
							}
							break;
						case R.id.rl_setting_sport_plan:
							// Toast.makeText(getApplicationContext(), "个人提醒",
							// Toast.LENGTH_SHORT).show();
							intent = new Intent(getApplicationContext(),
									SettingSportPlanActivity.class);
							break;
						case R.id.rl_setting_remind_person:
							// Toast.makeText(getApplicationContext(), "个人提醒",
							// Toast.LENGTH_SHORT).show();
							intent = new Intent(getApplicationContext(),
									SettingRemindActivity.class);
							break;
						case R.id.rl_setting_about:
							// Toast.makeText(getApplicationContext(), "关于产品",
							// Toast.LENGTH_SHORT).show();

							intent = new Intent(getApplicationContext(),
									AboutActivity.class);
							// intent = new Intent(getApplicationContext(),
							// TestActivity.class);

							break;
						}
						if (intent != null) {
							startActivity(intent);
						}
					}

				});

		// fragments = new ArrayList<Fragment>();
		// fragments.add(homeFragment);
		// fragments.add(meFragment);
		// fragments.add(settingFragment);
	}

	protected void syncWatchSettingAndCurrentSport() {
		// 1、请求当前手表设置
		// 2、请求当前计步信息 和 轨迹信息
		// 按照协议watch--〉app : 0x07 返回SportInfo，获取当前是计步还是轨迹
		if (null != getXBlueService() && getXBlueService().isAllConnect()) {
			ProtocolWriteManager write = ProtocolWriteManager.getInstance();
			byte[] byteForRequstSportInfo = write.getByteForRequstCurrentSportInfo();
			getXBlueService().write(byteForRequstSportInfo);
		} else {
//			dialogPlsConnectJSDWatchFirst();
		}
	}
	
	protected void testForLoadData() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
//				MilkUtil.loadData();
				LitePalManager.instance().getSportHistoryListByMonthForAll(new Date());
			}
		}).start();
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
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				switch (i) {
				case R.id.rb_tab_home:
					if (homeFragment == null) {
						homeFragment = new HomeFragment();
					}
					if (homeFragment.isAdded()) {
						transaction.show(homeFragment);
					} else {
						transaction.replace(R.id.frame_main, homeFragment,
								HomeFragment.class.getSimpleName());
					}
					
					// 
//					syncWatchSettingAndCurrentSport();
					
					break;
				case R.id.rb_tab_me:
					if (meFragment == null) {
						meFragment = new MeFragment();
					}
					if (meFragment.isAdded()) {
						transaction.show(meFragment);
					} else {
						transaction.replace(R.id.frame_main, meFragment,
								MeFragment.class.getSimpleName());
					}
					break;
				case R.id.rb_tab_setting:
					if (settingFragment == null) {
						settingFragment = new SettingFragment();
					}
					if (settingFragment.isAdded()) {
						settingFragment = new SettingFragment();
					} else {
						transaction.replace(R.id.frame_main, settingFragment,
								SettingFragment.class.getSimpleName());
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
		exitDialog.addSheetItem(getString(R.string.confirm),
				SheetItemColor.Red, new OnSheetItemClickListener() {
					@Override
					public void onClick(int which) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								if (getXBlueService()!=null) {
									getXBlueService().closeAll();
								}
								mHandler.postDelayed(new Runnable() {
									@Override
									public void run() {
										finish();
										System.exit(0);
									}
								}, 1000);
							}
						});
					}
				}).show();
	}

	/** 来自手环的广播 **/
	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, final Intent intent) {
			String action = intent.getAction();
			if (action.equals(BroadSender.ACTION_SUPPORT_FEATURE)) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						int[] supportFeature = intent.getExtras().getIntArray(
								BroadSender.EXTRA_SUPPORT_FEATURE);
						toast("手表支持的功能" + supportFeature);
					}
				});
			} else if (action.equals(BroadSender.ACTION_STEP_OR_PATH_INFO)) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						SportHistory info = intent.getExtras().getParcelable(
								BroadSender.EXTRA_CURRENT_SPORT_INFO);
						if (info != null) {
							i("BroadcastReceiver", info.toString());
							// updateUi
							updateCurrentSportInfo(info);
						} else {
							i("BroadcastReceiver", "当前运动信息info 为空 ");
						}
					}

				});
			}

		}
	};

	/**
	 * 更新当前运动信息
	 * 
	 * @param info
	 */
	private SportHistory info;
	private void updateCurrentSportInfo(SportHistory info) {
		if (homeFragment.isAdded()) {
			homeFragment.freshHomeFragmentUi(info);
			this.info= info;
		}
	}

	
	
	
	private LaputaBroadcastReceiver bleReceiver = new LaputaBroadcastReceiver(){

		@Override
		protected void onStateChanged(String address, int state) {
			super.onStateChanged(address, state);
			if (state == AbstractSimpleLaputaBlue.STATE_SERVICE_DISCOVERED) {
				if (settingFragment!=null && settingFragment.isAdded()) {
					settingFragment.updateSyncText(true);
				}
			} else {
				if (settingFragment!=null && settingFragment.isAdded()) {
					settingFragment.updateSyncText(false);
				}
			}
			
			
		}
		
		
	};
	
	
	
	 private ProtocolNotifyManager manager;
		private Runnable runProtocol = new Runnable() {
		
		@Override
		public void run() {
			try {
			
				for (int i = 0; i < 100; i++) {
					Log.e("", "-- "+i+" --");
					manager.parse(DataUtil.hexStringToByte("A9010000"));
					Thread.sleep(200);
					manager.parse(DataUtil.hexStringToByte("0910081922222201010212345643211234654321"));
					Thread.sleep(200);
					manager.parse(DataUtil.hexStringToByte("1A11223344556601011111222233440000000000"));
					Thread.sleep(200);
					manager.parse(DataUtil.hexStringToByte("A9000000"));
				}
		
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	
}
