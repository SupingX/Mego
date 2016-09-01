package com.laput.map;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import com.mycj.jusd.OffLineActivity;
import com.mycj.jusd.R;
import com.mycj.jusd.base.BaseActivity;
import com.mycj.jusd.ui.fragment.MapDataFragment;
import com.mycj.jusd.ui.fragment.MapHeartRateFragment;
import com.mycj.jusd.ui.fragment.MapPaceFragment;
import com.mycj.jusd.ui.fragment.MapSignFragment;
import com.mycj.jusd.ui.fragment.MapStepFreqFragment;
import com.mycj.jusd.view.FangRadioButton;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class MapActivity extends BaseActivity implements OnCheckedChangeListener {

	private MapView mapView;
	private BaiduMap map;
	private List<LatLng> points = new ArrayList<LatLng>();
	private Polyline myPath;
	private FangRadioButton tvData;
	private FangRadioButton tvSpeed;
	private FangRadioButton tvPerStep;
	private FangRadioButton tvHeartRate;
	private FangRadioButton tvUp;
	private TextView tvNetInfo;
	private Fragment dataFragment;
	private FragmentManager mFragmentManager;
	private BDLocation mBDLocation;
	StringBuffer info = new StringBuffer();
	private Handler mHandler = new Handler() {

	};
	private BroadcastReceiver netInfoReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.e("", "action :" + action);
			if (action.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
//						tvNetInfo.setVisibility(View.VISIBLE);
						info.append("\n 网络异常");
						tvNetInfo.setText(info.toString());
					}
				});

			} else if (action.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
//						tvNetInfo.setVisibility(View.GONE);
					}
				});

			} else if (action.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				info.append("\n key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
				tvNetInfo.setText(info.toString());
			} 
		}

	};
	private MapPaceFragment paceFragment;
	private MapStepFreqFragment stepFreqFragment;
	private MapHeartRateFragment heartRateFragment;
	private MapSignFragment signFragment;
	private LocationClient locationClient;
	protected boolean isFirstLocation = true;
	private int directionX;
//	private JsdOrientationListener jsdOrientationListener;
	private SensorManager sensorManager;
	private MKOfflineMap mkOfflineMap;
	private String typeDescription;
	private int i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		mFragmentManager = getSupportFragmentManager();
	
		initBaiduMap();
		initView();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// 注册 SDK 广播监听者
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		registerReceiver(netInfoReceiver, iFilter);
		
	
		
		//
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		map.setMyLocationEnabled(false);
	
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		locationClient.stop();
//		jsdOrientationListener.stop();
		unregisterReceiver(netInfoReceiver);
		mapView.onDestroy();
		mapView = null ;
		
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		switch (buttonView.getId()) {

		case R.id.tv_t_data:
			if (dataFragment == null) {
				dataFragment = new MapDataFragment(null);
			}
			if (dataFragment.isAdded()) {
				transaction.show(dataFragment);
			} else {
				transaction.replace(R.id.frame, dataFragment, MapDataFragment.class.getSimpleName());
			}
			break;
		case R.id.tv_t_speed:
			if (paceFragment == null) {
				paceFragment = new MapPaceFragment(null);
			}
			if (paceFragment.isAdded()) {
				transaction.show(paceFragment);
			} else {
				transaction.replace(R.id.frame, paceFragment, MapPaceFragment.class.getSimpleName());
			}
			break;
		case R.id.tv_t_per_step:
			if (stepFreqFragment == null) {
				stepFreqFragment = new MapStepFreqFragment(null);
			}
			if (stepFreqFragment.isAdded()) {
				transaction.show(stepFreqFragment);
			} else {
				transaction.replace(R.id.frame, stepFreqFragment, MapStepFreqFragment.class.getSimpleName());
			}
			break;
		case R.id.tv_t_hr:
			if (heartRateFragment == null) {
				heartRateFragment = new MapHeartRateFragment(null);

			}
			if (heartRateFragment.isAdded()) {
				transaction.show(heartRateFragment);
			} else {
				transaction.replace(R.id.frame, heartRateFragment, MapHeartRateFragment.class.getSimpleName());
			}
			break;
		case R.id.tv_t_up:
			if (signFragment == null) {
				signFragment = new MapSignFragment(null);
			}
			if (signFragment.isAdded()) {
				transaction.show(signFragment);
			} else {
				transaction.replace(R.id.frame, signFragment, MapSignFragment.class.getSimpleName());
			}
			break;
		default:
			break;
		}

		if (isChecked) {
			buttonView.setTextColor(Color.WHITE);
			buttonView.animate().setDuration(200)
			// .alpha(255)
					.scaleX(1.2f).scaleY(1.2f);
		} else {
			buttonView.setTextColor(Color.parseColor("#77ffffff"));
			buttonView.animate().setDuration(200)
			// .alpha(70)
					.scaleX(1.0f).scaleY(1.0f);
		}
		transaction.commit();
	}

	private void initView() {
		tvData = (FangRadioButton) findViewById(R.id.tv_t_data);
		tvSpeed = (FangRadioButton) findViewById(R.id.tv_t_speed);
		tvPerStep = (FangRadioButton) findViewById(R.id.tv_t_per_step);
		tvHeartRate = (FangRadioButton) findViewById(R.id.tv_t_hr);
		tvUp = (FangRadioButton) findViewById(R.id.tv_t_up);
		tvNetInfo = (TextView) findViewById(R.id.tv_net_info);

		tvData.setAlpha(70);
		tvSpeed.setAlpha(70);
		tvPerStep.setAlpha(70);
		tvHeartRate.setAlpha(70);
		tvUp.setAlpha(70);
		tvData.setOnCheckedChangeListener(this);
		tvSpeed.setOnCheckedChangeListener(this);
		tvPerStep.setOnCheckedChangeListener(this);
		tvHeartRate.setOnCheckedChangeListener(this);
		tvUp.setOnCheckedChangeListener(this);
		tvData.setChecked(true);
	}
	
	
	public void setOffLineMap(View v){
		startActivity(new Intent(this,OffLineActivity.class));
	}
	
	private void initBaiduMap() {
		mapView = (MapView) findViewById(R.id.bmapView);
		map = mapView.getMap();
		LocationMode locationMode = LocationMode.NORMAL;
//		BitmapDescriptor marker = BitmapDescriptorFactory.fromResource(R.drawable.ic_pos);
		map.setMyLocationConfigeration(new MyLocationConfiguration(locationMode, false, null));
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(19.0f);
		map.animateMapStatus(msu);
		//开启定位图层
		locationClient = new LocationClient(this);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); //打开GPS
		option.setCoorType("bd09ll");//设置坐标类型
		option.setProdName("laputa");
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		option.setAddrType("all");
		option.setPriority(LocationClientOption.GpsFirst);
		option.setIsNeedAddress(true);
		option.setScanSpan(2000);//设置定位事件爱你间隔 毫秒
		locationClient.setLocOption(option);
		locationClient.registerLocationListener(new BDLocationListener() {
			
			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location == null || mapView == null) {
					return ;
				}
				if (info.toString().length()>100) {
					info = new StringBuffer();
				}
				
				info.append("定位中....请稍后");
				tvNetInfo.setText(info.toString());
				
					
				int locType = location.getLocType();
				if (locType == BDLocation.TypeOffLineLocation) {//Gps定位结果
					 typeDescription = "\n 离线定位成功，离线定位结果也是有效的";
						info.append(typeDescription);
//					setLocation(location);
						if (isFirstLocation) {
							isFirstLocation = false;
							LatLng here = new LatLng(location.getLatitude(),location.getLongitude());
							MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(here);
							map.animateMapStatus(msu);
						}
					return;
					
				} else if(locType == BDLocation.TypeNetWorkLocation){
					 typeDescription = "\n 网络定位成功";
						info.append(typeDescription);
//					setLocation(location);
						if (isFirstLocation) {
							isFirstLocation = false;
							LatLng here = new LatLng(location.getLatitude(),location.getLongitude());
							MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(here);
							map.animateMapStatus(msu);
						}
						
					
					return;
				} else if(locType == BDLocation.TypeServerError){
					 typeDescription = "\n TypeServerError";
					info.append(typeDescription);
				} else if(locType == BDLocation.TypeNetWorkException){
					 typeDescription = "\n 网络不同导致定位失败，请检查网络是否通畅";
					info.append(typeDescription);
				} else if(locType == BDLocation.TypeCriteriaException){
					 typeDescription = "\n 无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机";
					info.append(typeDescription);
				} else if(locType == BDLocation.TypeGpsLocation){
					 typeDescription =  "\n gps定位成功";
					info.append(typeDescription);
					info.append("\nspeed : ");
					info.append(location.getSpeed());// 单位：公里每小时
					info.append("\nsatellite : ");
					info.append(location.getSatelliteNumber());
					info.append("\nheight : ");
					info.append(location.getAltitude());// 单位：米
					info.append("\ndirection : ");
					info.append(location.getDirection());
					info.append("\naddr : ");
					info.append(location.getAddrStr());
					info.append("\ndescribe : ");
					info.append("gps定位成功");
					setLocation(location);
					return;
				}
			
				
			}
		});
		
		//开启图层定位
		map.setMyLocationEnabled(true);
		if (!locationClient.isStarted()) {
			locationClient.start();
//			locationClient.requestLocation();
		}
	}
	
	
	public class Task implements Callable<String>{

		@Override
		public String call() throws Exception {
			return null;
		}
		
	}
	
	
	

	protected void setLocation(final BDLocation location) {
		CoordinateConverter converter  = new CoordinateConverter();
		converter.from(CoordType.GPS);
//		CoordinateConverter coord = converter.coord(new LatLng(location.getLatitude(), location.getLongitude()));
//		LatLng convert = coord.convert();
//		double latitude = convert.latitude;
//		double longitude = convert.longitude;
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		StringBuffer sb = new StringBuffer();
		sb.append("===当前定位信息===")
			.append("\n 坐标 ：" +"（" + latitude + "," + longitude + "）")
//			.append("\n 精度 ：" +location.getRadius())
			.append("\n 地址 ：" +location.getAddrStr())
//			.append("\n BuildingID：" +location.getBuildingID())
//			.append("\n BuildingName：" +location.getBuildingName())
//			.append("\n City：" +location.getCity())
//			.append("\n CityCode：" +location.getCityCode())
//			.append("\n Country：" +location.getCountry())
//			.append("\n CountryCode：" +location.getCountryCode())
			.append("\n 方向：" +location.getDirection())
//			.append("\n 区：" +location.getDistrict())
//			.append("\n floor：" +location.getFloor())
//			.append("\n 描述：" +location.getLocationDescribe())
//			.append("\n NetworkLocationType：" +location.getNetworkLocationType())
//			.append("\n 运营商：" +location.getOperators())
//			.append("\n 省：" +location.getProvince())
//			.append("\n SemaAptag：" +location.getSemaAptag())
//			.append("\n 街道：" +location.getStreet())
//			.append("\n 时间：" +location.getTime())
//			.append("\n ------------end" )
		;
		MyLocationData locationData = new MyLocationData.Builder()
			.accuracy(location.getRadius())
			.direction(360-location.getDirection())//角度
			.latitude(latitude)
			.longitude(longitude)
			.build()
			;
		map.setMyLocationData(locationData);
		LatLng here = new LatLng(latitude,longitude);
	
		
		if (points.size() == 0) {
			points.add(here);
			MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(here);
			map.animateMapStatus(msu,1000);
			
		}else{
			LatLng latLng = points.get(points.size()-1);
			Log.e("xpl", "______________latLng : ( i=" + latLng.latitude+","+latLng.longitude);
			double shortDistance = BaiduMapUtil.getShortDistance(latLng.longitude, latLng.latitude
					, here.longitude, here.latitude);
			Log.e("xpl", "______________shortDistance : "  + i+ "-->"+ shortDistance);
			if (shortDistance < 5 * i) {
				points.add(here);
				i=1;
			}else{
				i++;
			}
		}
		
//		if (isFirstLocation) {
//			isFirstLocation = false;
//			MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(here);
//			map.animateMapStatus(msu);
//		}
		
		if (myPath != null) {
			myPath.remove();
		}
		
		if (points.size() > 1) {
			PolylineOptions options = new PolylineOptions().width(10).color(0xffFF0000).points(points);
			myPath = (Polyline) map.addOverlay(options);
		}
		
		mBDLocation =location;
		tvNetInfo.setText(info.toString());
		
	}

}
