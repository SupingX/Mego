package com.mycj.jusd;

import com.mycj.jusd.base.BaseActivity;
import com.mycj.jusd.protocol.ProtocolWriteManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class TestActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
	}
	
	//030100AA00781201006400C80714DC161E071E00
	//0301005200781201000C00180014DC161E071E00
	
	public void test1(View v){
//		byte[] byteForWatchSetting = ProtocolWriteManager.getInstance().getByteForWatchSetting(1, 170, 120, 18, 1, 1, 100, 200, 1, 220, 20, 1, 22, 30, 7, 30);
//		if (getXBlueService()!= null && getXBlueService().isAllConnect()) {
//			getXBlueService().write(byteForWatchSetting);
//		}
	}
	public void test2(View v){
		byte[] data = ProtocolWriteManager.getInstance().getByteForRequstWatchConfigration(3);
		if (getXBlueService()!= null && getXBlueService().isAllConnect()) {
			getXBlueService().write(data);
		}
	}
	//040F161E0007D000C800140014001E0100000000
	public void test3(View v){
		byte[] byteForWatchSetting = ProtocolWriteManager.getInstance().getByteForSportPlanSetting(1, 22, 30, 2000, 200, 1, 20, 1, 20, 30, 1, 1);
		if (getXBlueService()!= null && getXBlueService().isAllConnect()) {
			getXBlueService().write(byteForWatchSetting);
		}
	}
	public void test4(View v){
		byte[] data = ProtocolWriteManager.getInstance().getByteForRequstWatchConfigration(4);
		if (getXBlueService()!= null && getXBlueService().isAllConnect()) {
			getXBlueService().write(data);
		}
	}
	
	// 051F161E00370A161614
	public void test5(View v){
//		byte[] byteForWatchSetting = ProtocolWriteManager.getInstance().getByteForRemindSetting(1, 1, 22, 30, 1, 55, 1, 10, 22, 22, 20, 1);
//		if (getXBlueService()!= null && getXBlueService().isAllConnect()) {
//			getXBlueService().write(byteForWatchSetting);
//		}
	}
	public void test6(View v){
		byte[] data = ProtocolWriteManager.getInstance().getByteForRequstWatchConfigration(5);
		if (getXBlueService()!= null && getXBlueService().isAllConnect()) {
			getXBlueService().write(data);
		}
	}
}
