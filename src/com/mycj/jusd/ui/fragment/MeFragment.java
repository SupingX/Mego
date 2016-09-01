package com.mycj.jusd.ui.fragment;




import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mycj.jusd.R;
import com.mycj.jusd.bean.LitePalManager;
import com.mycj.jusd.util.DataUtil;
import com.mycj.jusd.util.DateUtil;


/**
 * 我
 * 
 */
public class MeFragment extends Fragment implements View.OnClickListener {


    private RelativeLayout rlHistrorySleep;
    private RelativeLayout rlHistrorySprot;
    private TextView tvHistoryDistance; // 累计里程
    private TextView tvHistoryTime; // 累计时间
    private TextView tvHistoryStep; // 累计步数
    private TextView tvHistoryCal; // 能量消耗
	private ScrollView mScrollView;
	private PtrClassicFrameLayout mPtrFrame;

    public MeFragment() {
    }
    private String hourUnit ="";
	private String minuteUnit="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View meView =  inflater.inflate(R.layout.fragment_me, container, false);
        rlHistrorySleep = (RelativeLayout) meView.findViewById(R.id.rl_history_sleep);
        rlHistrorySprot = (RelativeLayout) meView.findViewById(R.id.rl_history_sport);
        tvHistoryDistance = (TextView) meView.findViewById(R.id.tv_history_distance );
        tvHistoryTime = (TextView) meView.findViewById(R.id.tv_history_time );
        tvHistoryStep = (TextView) meView.findViewById(R.id.tv_history_step );
        tvHistoryCal = (TextView) meView.findViewById(R.id.tv_history_cal );

//        refreshMeFragmentUi();
       hourUnit = getString(R.string.hour);
    	minuteUnit = getString(R.string.minute);
        rlHistrorySleep.setOnClickListener(this);
        rlHistrorySprot.setOnClickListener(this);

        initRefreshView(meView);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
            	mPtrFrame.autoRefresh();
            	refreshMeFragmentUi();
            }
        }, 100);
        return meView;
    }

    public void refreshMeFragmentUi(){
    	new MyAsyncTask().execute();
    }
    
    
    private void initRefreshView(View v){
        mScrollView = (ScrollView) v.findViewById(R.id.rotate_header_scroll_view);
        mPtrFrame = (PtrClassicFrameLayout) v.findViewById(R.id.rotate_header_web_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mScrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                     	refreshMeFragmentUi();
//                        refresh();
                    }
                }, 2000);
            }
        });

        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
   /*     mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
            	mPtrFrame.autoRefresh();
            	refreshMeFragmentUi();
            }
        }, 100);*/
}
    

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rl_history_sleep:
                if (mOnMeFragmentClickListener != null) {
                    mOnMeFragmentClickListener.doLookSleepHistory();
                }
                break;
            case R.id.rl_history_sport:
                if (mOnMeFragmentClickListener != null) {
                    mOnMeFragmentClickListener.doLookSportHistory();
                }
                break;
        }

    }


    public interface OnMeFragmentClickListener{
        void doLookSleepHistory();
        void doLookSportHistory();
    }

    private OnMeFragmentClickListener mOnMeFragmentClickListener;
    public void setOnMeFragmentClickListener(OnMeFragmentClickListener mOnMeFragmentClickListener){
        this.mOnMeFragmentClickListener = mOnMeFragmentClickListener;
    }
    
    private class MyAsyncTask extends AsyncTask<Void, Void, String[]>{
    	
    /*	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		mPtrFrame.autoRefresh();
    	}*/
    	
		@Override
		protected String[] doInBackground(Void... params) {
			
			LitePalManager pal = LitePalManager.instance();
			int totalStep = 	pal.getSportHistoryTotalStep();
	    	int time = 	pal.getSportHistoryTotalConsuming();
	    	int totalCalorie = 	pal.getSportHistoryTotalCalorie();;
	    	int totalDistance =	pal.getSportHistoryTotalDistance();
	    	String distanceStr = totalDistance*1.0/100 +"千米";
	    	String sportTimeStr = formateTime2(time);
	    	String kalStr = totalCalorie*1.0/100 + "千卡";
	    	String stepStr = String.valueOf(totalStep) + "步";
			return new String[]{distanceStr,sportTimeStr,kalStr,stepStr};
		}
		
		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			mPtrFrame.refreshComplete();
			if (result!=null) {
				 tvHistoryDistance.setText(result[0]);
		         tvHistoryTime.setText(result[1]);
		         tvHistoryCal.setText(result[2]);
		         tvHistoryStep.setText(result[3]);
			}
		}

    	
    }

	public  String formateTime2(long seconds){
		StringBuffer sb = new StringBuffer();
		
		if (seconds < 60) {
			sb.append("00").append(hourUnit).append("00")
			.append(minuteUnit);
		}else{
			int min = (int)(seconds/60f);
			if (min <60) {
				sb.append("00").append(hourUnit)
					.append(getTimeString(min))
					.append(minuteUnit);
			}else{
				int hour =(int) Math.floor(min/60f);
				int newMin = min%60;
				sb.append(getTimeString(hour))
					.append(hourUnit)
					.append(getTimeString(newMin))
					.append(minuteUnit)
				;
			}
		}
		
		return sb.toString();
	}
	
	public  String getTimeString(long value){
		String valueOf = String.valueOf(value);
		if (valueOf.length()==1) {
			return "0"+valueOf;
		}
		return valueOf;
	}
}
