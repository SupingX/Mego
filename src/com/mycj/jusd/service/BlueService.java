package com.mycj.jusd.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import com.laputa.blue.core.AbstractSimpleLaputaBlue;
import com.laputa.blue.core.Configration;
import com.laputa.blue.core.OnBlueChangedListener;
import com.laputa.blue.core.SimpleLaputaBlue;
import com.laputa.blue.util.BondedDeviceUtil;
import com.laputa.blue.util.XLog;
import com.mycj.jusd.bean.RemindSetting;
import com.mycj.jusd.bean.Music;
import com.mycj.jusd.bean.WatchSetting;
import com.mycj.jusd.bean.news.CurrentSportLocation;
import com.mycj.jusd.bean.news.SleepHistory;
import com.mycj.jusd.bean.news.SportHistory;
import com.mycj.jusd.bean.news.SportPlanSetting;
import com.mycj.jusd.protocol.ProtocolWriteManager;
import com.mycj.jusd.protocol.ProtocolNotifyManager;
import com.mycj.jusd.protocol.ProtocolNotifyManager.OnDataParseResuletListener;
import com.mycj.jusd.util.MessageUtil;

public class BlueService extends Service implements OnPreparedListener,
		OnCompletionListener {

	private Handler mHandler = new Handler() {
	};
	private Runnable taskIncoming;
	private int phoneNo;
	private int smsMNo;
	

	@Override
	public void onCreate() {
		super.onCreate();
		
		
		
		
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
			}
		}, 3000);
		
		initProtocolNotifyManager();
		Configration configration = new Configration();
		configration.UUID_SERVICE = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
		configration.UUID_CHARACTERISTIC_WRITE = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
		configration.UUID_CHARACTERISTIC_NOTIFY = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
		simpleLaputaBlue = new SimpleLaputaBlue(getApplicationContext(),
				configration, new OnBlueChangedListener() {
					@Override
					public void reconnect(HashSet<String> devices) {
						final String addressA = BondedDeviceUtil.get(1,
								getApplicationContext());
						if (BluetoothAdapter.checkBluetoothAddress(addressA)) {
							try {
								// 当前app存贮的蓝牙
								BluetoothDevice remoteDevice = simpleLaputaBlue
										.getAdapter().getRemoteDevice(addressA);
								// 所有的绑定蓝牙列表
								Set<BluetoothDevice> bondedDevices = simpleLaputaBlue
										.getAdapter().getBondedDevices();
								//
								if (bondedDevices.contains(remoteDevice)) {
									XLog.e("_____已绑定 ：" + addressA);
									if (!isConnect()) {
										connect(addressA);
										return;
									}
								} else {
									XLog.e("_____未绑定 ：" + addressA);
									// 当搜索列表中包含保存的addressA,并且未连接，就连接。
									if (devices.contains(addressA)) {
										if (!isConnect()) {
											connect(addressA);
										}
									} else {
										XLog.i("搜索列表无：" + addressA);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
								XLog.e("重新连接失败！");
							}

						} else {
							XLog.i("蓝牙地址不匹配，没有addessA" + addressA);
						}
					}

					@Override
					public void onStateChanged(String address, int state) {

					}

					@Override
					public void onServiceDiscovered(String address) {
						doUpdateSetting();
					}

					@Override
					public void onCharacteristicChanged(String address,
							byte[] value) {
						parseData(value);
					}

					@Override
					public boolean isAllConnected() {
						/*String address = BondedDeviceUtil.get(1, getApplicationContext());
						if (address !=null && !address.equals("")) {
							
							return simpleLaputaBlue.isConnected(address);
						}
						return true;*/
						return isAllConnect();
					}
				});

		/*
		 * xplBlueManager = XBlueManager.instance(getApplicationContext());
		 * xplBlueManager.setXBlueManagerListener(new XBlueManagerListener() {
		 * @Override public void doDeviceFound(HashMap<String, BluetoothDevice>
		 * scanDeviceMap) {
		 * }
		 * 
		 * @Override public void doPlayMusic(int order) { playMusic(order); }
		 * 
		 * @Override public void doFindPhone(int order) { if (order==1) {
		 * playResource(R.raw.crystal); }else{ stop(); } }
		 * 
		 * @Override public void doDisconnected(BluetoothDevice device) {
		 * stop(); } });
		 */

		/**********************************
		 * 电话和短信 *
		 **********************************/
		// 可以先在同步里设置好。

		taskIncoming = new Runnable() {
			@Override
			public void run() {
				int mmsCount = MessageUtil
						.getNewMmsCount(getApplicationContext());
				int msmCount = MessageUtil
						.getNewSmsCount(getApplicationContext());
				int phoneCount = MessageUtil
						.readMissCall(getApplicationContext());
				Log.i("BaseApp", "____电话数量 ： " + phoneNo + "-->" + phoneCount);
				Log.i("BaseApp", "____短信 ： " + smsMNo + "-->"
						+ (msmCount + mmsCount)
						+ (smsMNo != (mmsCount + msmCount)));
				// 数量只要有一个变化就发送
				// boolean isCallRemind;
				// isCallRemind = (boolean)
				// SharedPreferenceUtil.get(getApplicationContext(),
				// StaticValue.SHARE_REMIND_INCOMING, false);
				if (phoneNo != phoneCount || smsMNo != (mmsCount + msmCount)) {
					Log.i("xpl",
							"_______________________________________________________________________________________读取短信和电话数量 ： 有变化");
					// if (mmsCount == 0 && msmCount == 0 && phoneCount == 0) {
					// doWriteUnReadPhoneAndSmsToWatch(0, 0);
					// return;
					// } else {
					write(ProtocolWriteManager.getInstance().writeForPhoneAndSmsCount(
							phoneCount, mmsCount + msmCount));
					// }
					// 修改与10.28
					phoneNo = phoneCount;
					smsMNo = (mmsCount + msmCount);
				} else {
					Log.i("BaseApp", "__读取短信和电话数量 ： 无变化");
				}
				mHandler.postDelayed(taskIncoming, 8000);
			}
		};
		mHandler.postDelayed(taskIncoming, 8 * 1000);
	}

	public void startScan() {
		simpleLaputaBlue.scanDevice(true);
	}

	public void stopScan() {
		simpleLaputaBlue.scanDevice(false);
	}

	private String getBondedAddress() {
		return BondedDeviceUtil.get(1, getApplicationContext());

	}

	public void write(byte[] data) {

		simpleLaputaBlue.write(getBondedAddress(), data);
	}

	public void write(byte[][] datas) {

		simpleLaputaBlue.write(getBondedAddress(), datas);
	}

	public boolean isConnect() {
		return simpleLaputaBlue != null
				&& simpleLaputaBlue.isConnected(BondedDeviceUtil.get(1, this));
	}

	public void connect(String address) {
		simpleLaputaBlue.connect(address);
	}

	public void connect(BluetoothDevice device) {
		simpleLaputaBlue.connect(device);
	}

	public boolean isAllConnect() {
		return simpleLaputaBlue.isConnected(BondedDeviceUtil.get(1, this));
	}

	public void closeAll() {
		simpleLaputaBlue.closeAll();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return new XBlueBinder();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	public class XBlueBinder extends Binder {
		public BlueService getXBlueService() {
			return BlueService.this;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		closeAll();

	}

	private int[] ids;// 保存音乐ID临时数组
	private String[] artists;
	private String[] titles;
	private Cursor cursor;
	private List<Music> musicList;
	private MediaPlayer mediaPlayer;
	private int playingPosition = 0;
	/**
	 * 定义查找音乐信息数组，1.标题 2.音乐时间 3.艺术家 4.音乐id 5.显示名字 6.数据
	 */
	private String[] musicInfo = new String[] { MediaStore.Audio.Media.TITLE,
			MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST,
			MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME,
			MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID

	};
	private AbstractSimpleLaputaBlue simpleLaputaBlue;
	private ProtocolNotifyManager manager;

	/**
	 * 停止播放
	 */
	public void stop() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}
	}

	/**
	 * 释放
	 */
	public void release() {
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	public void reset() {
		if (mediaPlayer != null) {
			mediaPlayer.reset();
		}
	}

	public void playResource(int resId) {
		release();
		try {
			mediaPlayer = MediaPlayer.create(this, resId);
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		}
		// mediaPlayer.setOnPreparedListener(this);
		// mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.start();
	}

	public void play(Uri uri) {
		release();
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setDataSource(this, uri);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.prepareAsync();
			// mediaPlayer.start();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void playMusic(int order) {

	}

	public boolean isEnable() {
		// return xplBlueManager.isBluetoothAdapterEnable();
		return simpleLaputaBlue.isEnable();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mediaPlayer.start();
	}

	/**
	 * 当发现service时，同步手机设置
	 */
	private void doUpdateSetting() {
		XLog.e(BlueService.class, "doUpdateSetting() -- 开始");
		
		/*new MySyncTask()
				.execute(new byte[][] { DataUtil.hexStringToByte("FB"), });*/
		byte[] byteForTimeSync = ProtocolWriteManager.getInstance().getByteForTimeSync();
		write(byteForTimeSync);
		XLog.e(BlueService.class, "doUpdateSetting() -- 结束");
	}

	
	private void initProtocolNotifyManager(){
		manager = ProtocolNotifyManager.newInstance();
		manager.setOnDataParseResuletListener(new OnDataParseResuletListener() {
			
			@Override
			public void onWatchSetting(String msg, WatchSetting setting) {
				BroadSender.sendBroadcastForWatchSetting(setting, getApplicationContext());
			}
			
			@Override
			public void onSyncHistorySportInfoStatus(String msg, int status) {
				// 广播每一条历史运动信息开始结束状态
				BroadSender.sendBroadcastForHistorySportStatus(status, getApplicationContext());
			}
			
			@Override
			public void onSyncHistorySportInfo(String msg, com.mycj.jusd.bean.news.SportHistory info) {
				// 历史同步信息
				BroadSender.sendBroadcastForHistorySport(info,getApplicationContext());
				
//				if (info!=null) {
//					info.save();
//				}
			}
			
			@Override
			public void onSyncHistorySleepStatus(String msg, int status) {
				BroadSender.sendBroadcastForHistorySleepStatus(status, getApplicationContext());
			}
			
			@Override
			public void onSyncHistorySleep(String msg, SleepHistory info) {
				BroadSender.sendBroadcastForHistorySleep(info, getApplicationContext());
			}
			
			@Override
			public void onSupportFeature(String msg, int[] result) {
				
			}
			
			@Override
			public void onSportPlan(String msg, SportPlanSetting setting) {
				BroadSender.sendBroadcastForSportPlan(setting, getApplicationContext());
			}
			
			@Override
			public void onRemindSetting(String msg, RemindSetting setting) {
				BroadSender.sendBroadcastForRemind(setting, getApplicationContext());
			}
			
			@Override
			public void onHistorySyncStart(String msg,int type) {
				if (type == 1) {
					//运动开始同步
					BroadSender.sendBroadcastForStartSyncSport(getApplicationContext());
				}else{
					//睡眠开始同步
					BroadSender.sendBroadcastForStartSyncSleep(getApplicationContext());
				}
			}
			
			@Override
			public void onHistorySyncEnd(String msg,int type) {
				if (type == 1) {
					//运动结束同步
					BroadSender.sendBroadcastForEndSyncSport(getApplicationContext());
				}else{
					//睡眠结束同步
					BroadSender.sendBroadcastForEndSyncSleep(getApplicationContext());
				}
			}
			
			@Override
			public void onError(String msg) {
				
			}
			
			@Override
			public void onDelete(String msg, int status) {
				BroadSender.sendBroadcastForDelete(status, getApplicationContext());
			}
			
			

			@Override
			public void onCurrentSportInfo(String msg, SportHistory info) {
				BroadSender.sendBroadcastForCurrentSportInfo(info, getApplicationContext());
			}

			@Override
			public void onCurrentSportInfoStatus(String msg, int status) {
				BroadSender.sendBroadcastForCurrentSportInfoStatus(status, getApplicationContext());
			}
		});
	}
	
	
	/**
	 * <p>
	 * 解析数据
	 * </p>
	 * @param data
	 */
	private void parseData(byte[] data) {
		manager.parse(data);
		
		
		/*if (data != null) {
	
			JsdProtolNotify notify = JsdProtolNotify.instance();
			int protolNotifyType = notify.getProtolNotifyType(data);
			switch (protolNotifyType) {
			case JsdProtolNotify.NOTIFY_SUPPORT_FEATURE:
				XLog.i(getClass(),"解析数据 --设备支持功能");
				int[] parseSupportFeature = notify.parseSupportFeature(data);
				if (parseSupportFeature != null) {
					BroadSender.sendBroadcastForSupporFeature(parseSupportFeature,BlueService.this);
				}
				break;
			case JsdProtolNotify.NOTIFY_WATCH_SETTING:
				XLog.i(getClass(),"解析数据 --设备设置");
				WatchSetting parseWatchSetting = notify.parseWatchSetting(data);
				if (parseWatchSetting != null) {
					//保存设置
					sharedPreferenceWatchSetting(parseWatchSetting);
					//
					BroadSender.sendBroadcastForWatchSetting(parseWatchSetting, BlueService.this);
				}
				break;
				
			case JsdProtolNotify.NOTIFY_SPORT_PLAN:
				XLog.i(getClass(),"解析数据 --运动计划");
				SportPlanSetting parseSportPlanSetting = notify.parseSportPlanSetting(data);
				// 保存在本地
				sharedPreferenceSportPlanSetting(parseSportPlanSetting);
				// 发送给Activity，更新UI
				if (parseSportPlanSetting != null) {
					BroadSender.sendBroadcastForSportPlan(parseSportPlanSetting,BlueService.this);
				}
				break;
			case JsdProtolNotify.NOTIFY_REMIND:
				XLog.i(getClass(),"解析数据 --个人提醒");
				RemindSetting parseRemindSetting = notify.parseRemindSetting(data);
				sharedPreferenceRemindSetting(parseRemindSetting);
				if (parseRemindSetting != null) {
					BroadSender.sendBroadcastForRemind(parseRemindSetting,BlueService.this);
				}
				break;
				
			case JsdProtolNotify.NOTIFY_STEP_OR_PATH_INFO:
				XLog.i(getClass(),"解析数据 --当前运动信息");
				SportInfo parseSportInfo = notify.parseSportInfo(data);
				if (parseSportInfo != null) {
					BroadSender.sendBroadcastForCurrentSportInfo(parseSportInfo,BlueService.this);
				}
				break;
				
			case 0x07:
				XLog.i(getClass(),"解析数据 --当前运动信息");
//				CurrentSportData parseCurrentSportData = notify.parseCurrentSportData(data);
//				if (parseCurrentSportData != null) {
//					XLog.i("heiniu","======================解析数据 --当前运动信息--成功！！！" + parseCurrentSportData);
//				}
				break;
					
			default:
				break;
			}
			
		}else{
			XLog.e(getClass(),"解析数据data为null");
		}
		*/
		
		
		
		
	}
	
	
	
	private void sharedPreferenceRemindSetting(RemindSetting parseRemindSetting) {
		
	}
	
	/**
	 * 保存运动计划设置 
	 * @param parseSportPlanSetting
	 */
	private void sharedPreferenceSportPlanSetting(
			SportPlanSetting parseSportPlanSetting) {
		
	}
	
	/**
	 * 保存手表设置
	 * @param parseWatchSetting
	 */
	private void sharedPreferenceWatchSetting(WatchSetting parseWatchSetting) {
		
	}
	
	/*private void saveHistorySport(StepHistorySport historySport) {
		new SaveSportHistoryAsyncTask().execute(historySport);
	}*/

	
	
	
	

	/*private class SaveSportHistoryAsyncTask extends
			AsyncTask<StepHistorySport, Void, Void> {

		@Override
		protected Void doInBackground(StepHistorySport... params) {
			StepHistorySport sport = params[0];
			Date date = new Date();
			String dateStr = DateUtil.dateToString(date, "yyyyMMdd");
			if (sport != null) {
				List<StepHistorySport> sportAtDate = DataSupport.where("date =?",
						dateStr).find(StepHistorySport.class);
				if (sportAtDate != null && sportAtDate.size() > 0) { // 说明存在记录 ，
																		// 更新值就行了
					Log.e("xpl", dateStr + "已存记录");
					ContentValues values = new ContentValues();
					values.put("sportTime", sport.getSportTime());
					values.put("step", sport.getStep());
					DataSupport.updateAll(StepHistorySport.class, values,
							"date =?", dateStr);
				} else {
					sport.save(); // 不存在直接保存记录
				}

			}
			return null;
		}
	}*/


	public AbstractSimpleLaputaBlue getSimpleLaputaBlue() {
		return this.simpleLaputaBlue;
	}
}
