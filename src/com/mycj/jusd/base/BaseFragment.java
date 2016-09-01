package com.mycj.jusd.base;





import android.support.v4.app.Fragment;
import android.util.Log;

public class BaseFragment extends Fragment{
	protected boolean isDebug = false;
	protected void e(String tag,String msg){
		if (isDebug) {
			Log.e(tag, msg);
		}
	}
	protected void i(String tag,String msg){
		if (isDebug) {
			Log.e(tag, msg);
		}
	}
	
	public boolean isConnected(){
        BaseApp app  = (BaseApp) getActivity().getApplication();
        return app.getXBlueService()!=null && app.getXBlueService().isAllConnect();
}
}
