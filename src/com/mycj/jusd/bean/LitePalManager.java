package com.mycj.jusd.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;

import android.util.Log;

import com.laputa.blue.util.XLog;
import com.mycj.jusd.bean.news.SleepHistory;
import com.mycj.jusd.bean.news.SportHistory;
import com.mycj.jusd.util.DateUtil;



public class LitePalManager {
	private static LitePalManager litePal;

	public static LitePalManager instance() {
		if (litePal == null) {
			litePal = new LitePalManager();
		}
		return litePal;
	}

	private LitePalManager() {

	}
	
	private void log(String msg){
		System.out.println(msg);
	}
	
	/*public int getTotalStep(){
		Integer sum = DataSupport.sum(StepHistorySport.class, "step", Integer.class);
		log("累计步数:"+sum);
		return sum;
	}
	public int getTotalSportTime(){
		Integer sum = DataSupport.sum(StepHistorySport.class, "sportTime", Integer.class);
		log("累计时间:"+sum);
		return sum;
	}*/
	

	
	
	public List<SleepHistory> findHistroySleepByDateNew(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		List<SleepHistory> datas = DataSupport.where("sleepDate like ?", dateCondition).find(SleepHistory.class);
		sys("日期 ："+DateUtil.dateToString(date, "yyyyMM") + "下的睡眠数据-->" + datas);
		return datas;
	}

	/*public int findHistroySportStepsByDate(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("date like ?", dateCondition).sum(StepHistorySport.class, "step", Integer.class);
		return sum;
	}*/

	/*public int findHistroySportTimeByDate(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("date like ?", dateCondition).sum(StepHistorySport.class, "sportTime", Integer.class);
		return sum;
	}*/
	
	
	public int findHistroySleepCountNew(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sleepDate like ?", dateCondition).count(SleepHistory.class);
		return sum;
	}
	
	
	public float findHistroySleepDeepByDateNew(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sleepDate like ?", dateCondition).sum(SleepHistory.class, "sleepDeep", Integer.class);
		return sum;
	}

	
	public float findHistroySleepLightByDateNew(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sleepDate like ?", dateCondition).sum(SleepHistory.class, "sleepLight", Integer.class);
		return sum;
	}

	/*public List<StepHistorySport> findHistroySportByDate(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		List<StepHistorySport> datas = DataSupport.where("date like ?", dateCondition).find(StepHistorySport.class);
		return datas;
	}*/

/*	public int findHistroySportCountByDate(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int count = DataSupport.where("date like ?", dateCondition).count(StepHistorySport.class);
		return count;
	}*/
	
	
	private void sys(String msg){
		System.out.println(msg);
	}
	
	
	


	
	
	/*********SportStepHistory************/
	/*public int getSportStepHistoryTotalCountByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ?", dateCondition).count(SportStepHistory.class);
		return sum;
	}

	public int getSportStepHistoryTotalStepByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ?", dateCondition).sum(SportStepHistory.class, "step", Integer.class);
		return sum;
	}

	public int getSportStepHistoryTotalDistanceByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ?", dateCondition).sum(SportStepHistory.class, "distance", Integer.class);
		return sum;
	}

	public int getSportStepHistoryTotalCalorieByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ?", dateCondition).sum(SportStepHistory.class, "calorie", Integer.class);
		return sum;
	}

	public int getSportStepHistoryTotalConsumingByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ?", dateCondition).sum(SportStepHistory.class, "consuming", Integer.class);
		return sum;
	}

	public List<SportStepHistory> getSportStepHistoryListByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		List<SportStepHistory> datas = DataSupport.where("sportDate like ?", dateCondition).find(SportStepHistory.class);
		sys("日期 ："+DateUtil.dateToString(date, "yyyyMM") + "下的计步数据-->" + datas);
		return datas;
	}*/
	
	
	/*********SportHistory 计步************/
	public int getSportHistoryTotalCountByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int count = DataSupport.where("sportDate like ? and type = ? ", dateCondition,String.valueOf(0)).count(SportHistory.class);
		return count;
	}

	public int getSportHistoryTotalStepByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ? and type = ? ", dateCondition,String.valueOf(0)).sum(SportHistory.class, "step", Integer.class);
		return sum;
	}

	public int getSportHistoryTotalDistanceByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ? and type = ?", dateCondition,String.valueOf(0)).sum(SportHistory.class, "distance", Integer.class);
		return sum;
	}

	public int getSportHistoryTotalCalorieByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ? and type = ?", dateCondition,String.valueOf(0)).sum(SportHistory.class, "calorie", Integer.class);
		return sum;
	}

	public int getSportHistoryTotalConsumingByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ? and type = ?", dateCondition,String.valueOf(0)).sum(SportHistory.class, "consuming", Integer.class);
		sys("___这个月份的总时间 consuming ：" +sum);
		return sum;
	}

	public List<SportHistory> getSportHistoryListByMonth(Date date) {
	
		
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		List<SportHistory> datas = DataSupport.where("sportDate like ? and type = ?", dateCondition,String.valueOf(0)).find(SportHistory.class);
		sys("日期 ："+DateUtil.dateToString(date, "yyyyMM") + "下的计步数据-->" + datas);
		return datas;
	}
	
	public int getSportHistoryListByHour(Date date,String hour) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMMdd") + "%";
		String timeCondition = hour+"%";
		XLog.e("____________timeCondition : " + timeCondition);
		int sum = DataSupport.where("sportDate like ? and type = 0 and sportTime like ?", dateCondition,timeCondition).sum(SportHistory.class,"step", Integer.class);
		XLog.e("____________sum : " + sum);
		return sum;
	}
	
	/*********SportHistory 轨迹************/
	public int getSportHistoryTotalCountByMonthForPath(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int count = DataSupport.where("sportDate like ? and type !=?", dateCondition,String.valueOf(0)).count(SportHistory.class);
		return count;
	}

	public int getSportHistoryTotalStepByMonthForPath(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ? and type != ?", dateCondition,String.valueOf(0)).sum(SportHistory.class, "step", Integer.class);
		return sum;
	}

	public int getSportHistoryTotalDistanceByMonthForPath(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ? and type != ?", dateCondition,String.valueOf(0)).sum(SportHistory.class, "distance", Integer.class);
		return sum;
	}

	public int getSportHistoryTotalCalorieByMonthForPath(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ?and type != ?", dateCondition,String.valueOf(0)).sum(SportHistory.class, "calorie", Integer.class);
		return sum;
	}

	public int getSportHistoryTotalConsumingByMonthForPath(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ? and type != ?", dateCondition,String.valueOf(0)).sum(SportHistory.class, "consuming", Integer.class);
		return sum;
	}

	public List<SportHistory> getSportHistoryListByMonthForPath(Date date) {
		Log.e("", "getSportHistoryListByMonth : "+DateUtil.dateToString(date, "yyyyMM"));
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		List<SportHistory> datas = DataSupport.where("sportDate like ? and type != ?", dateCondition,String.valueOf(0)).find(SportHistory.class);
		
		if (datas != null &&datas.size()>0) {
			for (int i = 0; i < datas.size(); i++) {
				sys(datas.get(i).toString());
			}
		}
		
		
		return datas;
	}
	
	
	/*********SportHistory 轨迹************/
	public int getSportHistoryTotalCountByMonthForAll(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int count = DataSupport.where("sportDate like ? ", dateCondition).count(SportHistory.class);
		return count;
	}

	public int getSportHistoryTotalStepByMonthForAll(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ? ", dateCondition).sum(SportHistory.class, "step", Integer.class);
		return sum;
	}

	public int getSportHistoryTotalDistanceByMonthForAll(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ?", dateCondition).sum(SportHistory.class, "distance", Integer.class);
		return sum;
	}
	
	// 累计里程 累计时间 累计步数 能量消耗 
	
	public int getSportHistoryTotalDistance() {
		int sum = DataSupport.sum(SportHistory.class, "distance", Integer.class);
		return sum;
	}
	public int getSportHistoryTotalStep() {
		int sum = DataSupport.sum(SportHistory.class, "step", Integer.class);
		return sum;
	}
	public int getSportHistoryTotalConsuming() {
		int sum = DataSupport.sum(SportHistory.class, "consuming", Integer.class);
		sys("总的耗时 consuming : " + sum);
		return sum;
	}
	public int getSportHistoryTotalCalorie() {
		int sum = DataSupport.sum(SportHistory.class, "calorie", Integer.class);
		return sum;
	}
	
	
	
	

	public int getSportHistoryTotalCalorieByMonthForAll(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ? ", dateCondition).sum(SportHistory.class, "calorie", Integer.class);
		return sum;
	}

	public int getSportHistoryTotalConsumingByMonthForAll(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ? ", dateCondition).sum(SportHistory.class, "consuming", Integer.class);
		return sum;
	}
	
	
	public void select(final Date date){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					final int monthMaxDay = DateUtil.getMonthMaxDay(date);
					for (int i = 0; i < monthMaxDay; i++) {
						Calendar c = Calendar.getInstance();
						c.setTime(date);
						c.set(Calendar.DAY_OF_MONTH, i+1);
						String dateStr = DateUtil.dateToString(c.getTime(), "yyyyMMdd");
						int sumstep = DataSupport.where("sportDate like ? ", dateStr).sum(SportHistory.class, "step", Integer.class);
						Thread.sleep(20);
						int sumdistance = DataSupport.where("sportDate like ? ", dateStr).sum(SportHistory.class, "distance", Integer.class);
						Thread.sleep(20);
						int sumcalorie = DataSupport.where("sportDate like ? ", dateStr).sum(SportHistory.class, "calorie", Integer.class);
						Thread.sleep(20);
						int sumconsuming = DataSupport.where("sportDate like ? ", dateStr).sum(SportHistory.class, "consuming", Integer.class);
						Thread.sleep(20);
						double avgHr = DataSupport.where("sportDate like ? ", dateStr).average(SportHistory.class, "hr");
						Thread.sleep(20);
						
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		}).start();
	}
	
	
	public List<SportHistory> getSportHistoryListByMonthForDay(Date date) {
		/**
		 * select sdept,count(sno)num
			from student
			group by sdept
			order by count(sno)DESC;
		 */
		final int monthMaxDay = DateUtil.getMonthMaxDay(date);
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		List<SportHistory> datas = DataSupport.where("sportDate like ? ", dateCondition).find(SportHistory.class);
//		sys("============== size : "+(datas!=null?datas.size():"0")+"===============");
//		if (datas != null &&datas.size()>0) {
//			for (int i = 0; i < datas.size(); i++) {
//				sys(datas.get(i).toString());
//			}
//		}
//		sys("日期 ："+DateUtil.dateToString(date, "yyyyMM") + "下的计步数据-->" + datas);
		List<SportHistory> historySportPaths = getSportHistoryListByMonthForAll(date);
		List<SportHistory> resultList = new ArrayList<SportHistory>();
		if (historySportPaths != null && historySportPaths.size()>0) {
			SportHistory sh = new SportHistory();
			int step = 0;
			
			for (int i = 0; i < monthMaxDay; i++) {
				SportHistory sport = historySportPaths.get(i);
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.set(Calendar.DAY_OF_MONTH, i+1);
				String dateStr = DateUtil.dateToString(c.getTime(), "yyyyMMdd");
				if (sport.getSportDate().equals(dateStr)) {
					step +=sport.getStep();
				}
				sh.setStep(step);
				resultList.add(sh);
			}
			
			
		
		}
		
		
		return resultList;
	}
	
	public List<SportHistory> getSportHistoryListByMonthForAll(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		List<SportHistory> datas = DataSupport.where("sportDate like ? ", dateCondition).find(SportHistory.class);
//		sys("============== size : "+(datas!=null?datas.size():"0")+"===============");
//		if (datas != null &&datas.size()>0) {
//			for (int i = 0; i < datas.size(); i++) {
//				sys(datas.get(i).toString());
//			}
//		}
//		sys("日期 ："+DateUtil.dateToString(date, "yyyyMM") + "下的计步数据-->" + datas);
		return datas;
	}
	
	
	
	/*********SportPathHistory************/
	/*public int getSportPathHistoryTotalCountByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ?", dateCondition).count(SportPathHistory.class);
		return sum;
	}

	public int getSportPathHistoryTotalStepByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ?", dateCondition).sum(SportPathHistory.class, "step", Integer.class);
		return sum;
	}


	public int getSportPathHistoryTotalConsumingByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ?", dateCondition).sum(SportPathHistory.class, "consuming", Integer.class);
		return sum;
	}

	public int getSportPathHistoryTotalDistanceByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ?", dateCondition).sum(SportPathHistory.class, "distance", Integer.class);
		return sum;
	}

	public int getSportPathHistoryTotalCalorieByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		int sum = DataSupport.where("sportDate like ?", dateCondition).sum(SportPathHistory.class, "calorie", Integer.class);
		return sum;
	}

	public List<SportPathHistory> getSportPathHistoryListByMonth(Date date) {
		String dateCondition = DateUtil.dateToString(date, "yyyyMM") + "%";
		List<SportPathHistory> datas = DataSupport.where("sportDate like ?", dateCondition).find(SportPathHistory.class);
		sys("日期 ："+DateUtil.dateToString(date, "yyyyMM") + "下的轨迹数据-->" + datas);
		return datas;
	}*/
	
	
	/**  SportInfoHistory  **/
	public List<SportHistory> getSportInfoHistoryByDateTime(String date ,int sportIndex) {
		List<SportHistory> find = DataSupport.where("sportDate like ? and sportIndex = ?", date,String.valueOf(sportIndex)).find(SportHistory.class);
		return find;
	}
	
	
	public double getAvgSpeedByDateTime(String date ,int sportIndex) {
		double avg = DataSupport.where("sportDate like ? and sportIndex = ?", date,String.valueOf(sportIndex)).average(SportHistory.class, "speed");
		return avg;
	}
	
	public int getTotalConsumingByDateTime(String date ,int sportIndex) {
		int sum = DataSupport.where("sportDate like ? and sportIndex = ?", date,String.valueOf(sportIndex)).sum(SportHistory.class, "consuming", Integer.class);
		return sum;
	}
	
	public int getTotalStepByDateTime(String date ,int sportIndex) {
		int sum = DataSupport.where("sportDate like ? and sportIndex = ?", date,String.valueOf(sportIndex)).sum(SportHistory.class, "step", Integer.class);
		return sum;
	}
	
	public int getTotalCalorieByDateTime(String date ,int sportIndex) {
		int sum = DataSupport.where("sportDate like ? and sportIndex = ?", date,String.valueOf(sportIndex)).sum(SportHistory.class, "calorie", Integer.class);
		return sum;
	}
	
	public int getTotalDistanceByDateTime(String date ,int sportIndex) {
		int sum = DataSupport.where("sportDate like ? and sportIndex = ?", date,String.valueOf(sportIndex)).sum(SportHistory.class, "distance", Integer.class);
//		Log.e("", "--> distance : " + sum);
		return sum;
	}
	
	public double getAvgStepFreqByDateTime(String date ,int sportIndex) {
		double avg = DataSupport.where("sportDate like ? and sportIndex = ?", date,String.valueOf(sportIndex)).average(SportHistory.class, "freq");
		return avg;
	}
	
	
	
	public double getAvgHrByDateTime(String date ,int sportIndex) {
		double avg = DataSupport.where("sportDate like ? and sportIndex = ?", date,String.valueOf(sportIndex)).average(SportHistory.class, "hr");
		return avg;                                                                                                                                                            
	}
	
	/**  SportLocationHistory  **/
	public List<SportHistory> getSportLocationHistoryByDateTime(String date ,int sportIndex) {
		List<SportHistory> find = DataSupport.where("sportDate like ? and sportIndex = ?", date,String.valueOf(sportIndex)).find(SportHistory.class);
		return find;
	}
	
	public List<SportHistory> getSportLocationHistoryByDateTimeWhenSigned(String date ,int sportIndex) {
		List<SportHistory> find = DataSupport.where("sportDate like ? and sportIndex = ? and signed=1", date,String.valueOf(sportIndex)).find(SportHistory.class);
		return find;
	}

	public List<SportHistory> getSportHistoryListByIdx(List<SportHistory> all) {
		if (all == null || all.size() == 0 ) {
			return null;
		}
		List<SportHistory> list = new ArrayList<>();
		SportHistory sh = all.get(0);
		if (all.size() == 1) {
			list.add(sh);
			return list;
		}
		
		for (int i = 1; i < all.size(); i++) {
			SportHistory sportHistory = all.get(i);
			if (sportHistory.getSportDate().equals(sh.getSportDate())
					&&sportHistory.getSportIndex() == sh.getSportIndex()) {
				int consuming = sportHistory.getConsuming();
				int consuming2 = sh.getConsuming();
				sh.setConsuming(consuming2+consuming);
				int step = sportHistory.getStep();
				int step2 = sh.getStep();
				sh.setStep(step2+step);
				int distance = sportHistory.getDistance();
				int distance2 = sh.getDistance();
				sh.setDistance(distance2+distance);
			}else{
				list.add(sh);
				sh = sportHistory;
			}
			Log.e("", "list :" +list);
		}
		
		
		return list;
	}

}
