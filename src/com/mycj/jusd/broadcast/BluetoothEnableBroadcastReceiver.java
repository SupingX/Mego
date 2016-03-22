package com.mycj.jusd.broadcast;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mycj.jusd.service.XplBluetoothService;

public abstract class BluetoothEnableBroadcastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(XplBluetoothService.ACTION_BLUETOOTH_ENABLE)) {
			doBluetoothEnable();
		}
	}
	public abstract void doBluetoothEnable() ;
}
