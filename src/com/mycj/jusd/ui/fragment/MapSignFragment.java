package com.mycj.jusd.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycj.jusd.R;
import com.mycj.jusd.base.BaseFragment;

public class MapSignFragment extends BaseFragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map_sign, container,false);
		return view;
	}
}
