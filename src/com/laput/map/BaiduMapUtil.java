package com.laput.map;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.litepal.util.LogUtil;

import android.util.Base64;

import com.baidu.location.BDLocation;

public class BaiduMapUtil {
	static BDLocation tempBDLocation = new BDLocation(); // 临时变量，百度位置
	static GpsLocation tempGPSLocation = new GpsLocation(); // 临时变量，gps位置

	public static enum Method {
		origin, correct;
	}

	private static final Method method = Method.correct;

	/**
	 * 位置转换
	 *
	 * @param lBdLocation
	 *            百度位置
	 * @return GPS位置
	 */
	public static GpsLocation convertWithBaiduAPI(BDLocation lBdLocation) {
		switch (method) {
		case origin: // 原点
			GpsLocation location = new GpsLocation();
			location.lat = lBdLocation.getLatitude();
			location.lng = lBdLocation.getLongitude();
			return location;
		case correct: // 纠偏
			// 同一个地址不多次转换
			if (tempBDLocation.getLatitude() == lBdLocation.getLatitude() && tempBDLocation.getLongitude() == lBdLocation.getLongitude()) {
				return tempGPSLocation;
			}
			String url = "http://api.map.baidu.com/ag/coord/convert?from=0&to=4&" + "x=" + lBdLocation.getLongitude() + "&y=" + lBdLocation.getLatitude();
			// "http://api.map.baidu.com/ag/coord/convert?from=0&to=4&x=114.1234&y=12.1234"
			String result = executeHttpGet(url);
			if (result != null) {
				GpsLocation gpsLocation = new GpsLocation();
				try {
					JSONObject jsonObj = new JSONObject(result);
					String lngString = jsonObj.getString("x");
					String latString = jsonObj.getString("y");
					// 解码
					double lng = Double.parseDouble(new String(Base64.decode(lngString, 10)));
					double lat = Double.parseDouble(new String(Base64.decode(latString, 10)));
					// 换算
					gpsLocation.lng = 2 * lBdLocation.getLongitude() - lng;
					gpsLocation.lat = 2 * lBdLocation.getLatitude() - lat;
					tempGPSLocation = gpsLocation;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}

				tempBDLocation = lBdLocation;

				return gpsLocation;

			} else {

				return null;

			}

		}
		return tempGPSLocation;

	}

	private static String executeHttpGet(String url) {

		// Log.e("", "开始注册");
		HttpURLConnection connection = null;
		InputStream is = null;
		ByteArrayOutputStream out = null;
		OutputStream os = null;
		try {
			URL urlConn = new URL(url);
			connection = (HttpURLConnection) urlConn.openConnection();

			;
			// 设置请求的头
			connection.setRequestProperty("Connection", "keep-alive");
			// 设置请求的头
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Accept-Charset", "utf-8");
			connection.setRequestProperty("contentType", "utf-8");
			// 设置请求的头
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(true); // 设置是否向connection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			// 获取输出流
			os = connection.getOutputStream();
			os.flush();
			// connection.connect();
			// -------------------------------------------------------------
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				is = connection.getInputStream();//
			}
			if (is == null) {
				return null;
			}
			out = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = is.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			byte[] datas = out.toByteArray();
			return datas.toString();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// connection.disconnect();
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	static double DEF_PI = 3.14159265359; // PI
	static double DEF_2PI = 6.28318530712; // 2*PI
	static double DEF_PI180 = 0.01745329252; // PI/180.0
	static double DEF_R = 6370693.5; // radius of earth

	public static double getShortDistance(double lon1, double lat1, double lon2, double lat2) {
		double ew1, ns1, ew2, ns2;
		double dx, dy, dew;
		double distance;
		// 角度转换为弧度
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 经度差
		dew = ew1 - ew2;
		// 若跨东经和西经180 度，进行调整
		if (dew > DEF_PI)
			dew = DEF_2PI - dew;
		else if (dew < -DEF_PI)
			dew = DEF_2PI + dew;
		dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
		dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
		// 勾股定理求斜边长
		distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
	}
}
