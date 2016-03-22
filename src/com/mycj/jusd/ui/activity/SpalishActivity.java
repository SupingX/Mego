package com.mycj.jusd.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mycj.jusd.MainActivity;
import com.mycj.jusd.R;

public class SpalishActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spalish);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(SpalishActivity.this,MainActivity.class));
				finish();
			}
		}, 2000);
	}
}
