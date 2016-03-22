package com.mycj.jusd.broadcast;



import android.bluetooth.BluetoothDevice;

import com.mycj.jusd.bean.HistorySleep;
import com.mycj.jusd.bean.HistorySport;


/**
 * <p>AbstractXplBroadcastReceiver的空实现</p>
 * @author Administrator
 *
 */
public class ImpXplBroadcastReceiver extends AbstractXplBroadcastReceiver {

	@Override
	public void doDeviceFound(BluetoothDevice device, int rssi) {
		
	}

	@Override
	public void doConnectStateChange(BluetoothDevice device, int state) {
		
	}

	@Override
	public void doServiceDisCovered(BluetoothDevice device) {
		
	}
	

	@Override
	public void doBluetoothEnable() {
		
	}

	@Override
	public void doSportChanged(HistorySport sport) {
		
	}

	@Override
	public void doSportSyncStateChanged(int state) {
		
	}

	@Override
	public void doSleepChanged(HistorySleep sleep) {
		
	}

	@Override
	public void doSleepSyncStateChanged(int state) {
		
	}

	@Override
	public void doHeartRateChanged(int hr) {
		
	}

}
