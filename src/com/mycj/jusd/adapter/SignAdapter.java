package com.mycj.jusd.adapter;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycj.jusd.R;
import com.mycj.jusd.bean.SignInfo;


public class SignAdapter extends BaseAdapter{
    private Context context;
    private List<SignInfo> signInfos = new ArrayList<SignInfo>();
    public SignAdapter(Context context,ArrayList<SignInfo> devices){
        this.context = context;
        this.signInfos = devices;
    }
    @Override
    public int getCount() {
        return signInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return signInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodler holder =null;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_sign,viewGroup,false);
            holder = new ViewHodler();
            holder.imgSignStatus = (ImageView)view.findViewById(R.id.img_sign_status);
            holder.tvSignStatus = (TextView)view.findViewById(R.id.tv_sign_status);
            holder.tvSignTime = (TextView)view.findViewById(R.id.tv_sign_time);
            holder.tvSignDistance = (TextView)view.findViewById(R.id.tv_sign_distance);
            holder.tvSignDuration = (TextView)view.findViewById(R.id.tv_sign_duration_time);
            holder.tvSignPace = (TextView)view.findViewById(R.id.tv_sign_pace);
            view.setTag(holder);
        }else{
            holder = (ViewHodler)view.getTag();
        }

        int res = -1;
        String statusStr = "";
        String signTimeStr = "";
        String distanceStr = "";
        String durationTimeStr = "";
        String paceStr = "";
        
        SignInfo signInfo = signInfos.get(i);
        int status = signInfo.getStatus();
        float distance = signInfo.getDistance();
        long durationTime = signInfo.getDurationTime();
        float pace = signInfo.getPace();
        //1
        if (status==0) {
        	res = R.drawable.ic_sign_start;
            holder.imgSignStatus.setVisibility(View.VISIBLE);
            holder.tvSignStatus.setVisibility(View.GONE);
            holder.imgSignStatus.setImageResource(res);
		}else if(status == -1){
			res = R.drawable.ic_sign_end;
            holder.imgSignStatus.setVisibility(View.VISIBLE);
		     holder.tvSignStatus.setVisibility(View.GONE);
            holder.imgSignStatus.setImageResource(res);
		}else{
			statusStr = String.valueOf(status);
		       holder.imgSignStatus.setVisibility(View.GONE);
			     holder.tvSignStatus.setVisibility(View.VISIBLE);
			     holder.tvSignStatus.setText(statusStr);
		}
        
        //
        signTimeStr = signInfo.getSignDate();
        holder.tvSignTime.setText(signTimeStr);
        //
        distanceStr=String.valueOf(signInfo.getDistance());
        holder.tvSignDistance.setText(distanceStr);
        //
        durationTimeStr = getTime(durationTime);
        holder.tvSignDuration.setText(durationTimeStr);
        //
        paceStr=getPace(pace);
        holder.tvSignPace.setText(paceStr);
        return view;
    }


    private String getPace(float pace) {
    	int big = 0;
    	int small = 0;
    	if (pace<60) {
    		small = (int) pace;
		}else{
			 big = (int) (pace/60);
        	 small = (int) (pace - big*60);
		}
    	
    	
		return getString(big)+ "\'"+getString(small);
	}
	private String getTime(long durationTime) {
	  	int big = 0;
    	int small = 0;
    	if (durationTime<60) {
    		small = (int) durationTime;
		}else{
			 big = (int) (durationTime/60);
        	 small = (int) (durationTime - big*60);
		}
		return getString(big)+ ":"+getString(small);
	}
	
	private String getString(int i){
		return i<10?"0"+i:""+i;
	}
	

	class ViewHodler {
    	ImageView imgSignStatus;
        TextView tvSignStatus;
        TextView tvSignTime;
        TextView tvSignDistance;
        TextView tvSignDuration;
        TextView tvSignPace;
        
    }
}
