package com.mycj.jusd.bean;

public abstract class AbstractProtolWrite {
	/**
	 * 时间同步 5bytes
	 * <p>
	 * tag ：0x02
	 * </p>
	 * <p>
	 * DateTime:0xBB、0xf8、0x1e、0x00
	 * </p>
	 * <p>
	 * DateTime：从2000年1月1日0时0分0秒起已经过的秒数
	 * </p>
	 * <p>
	 * 如：｛0x12，0xCC，0x03，0x00｝ 表示 long diffSeconds = 0x12CC0300；
	 * </p>
	 * <p>
	 * 即从2000年1月1日0时0分0秒起已经过了315360000秒，即10*365*24*3600；
	 * </p>
	 **/
	public abstract byte[] getByteForTimeSync(long dateTime);

	/**
	 * 手表设置 <b>Total：18 bytes</b> 　　
	 * <p>
	 * 0x03
	 * </p>
	 * <p>
	 * XX公英制 0x00公制；0x01英制
	 * </p>
	 * <p>
	 * XXXX身高 ｛0x00，0xAF｝>> 0x00FF >> 175（厘米或者英寸）
	 * </p>
	 * <p>
	 * XXXX体重 ｛0x00，0x4B｝ >> 0x004B >> 75（公斤或者磅）
	 * </p>
	 * <p>
	 * XX年龄
	 * </p>
	 * <p>
	 * XX性别 0x00男；0x01女
	 * </p>
	 * <p>
	 * XX计步步长 略（厘米或者英寸）
	 * </p>
	 * <p>
	 * XX跑步步长 略（厘米或者英寸）
	 * </p>
	 * <p>
	 * XX心率开关 0x00关；0x01开
	 * </p>
	 * <p>
	 * XX最小心率 不小于30并小于最大心率
	 * </p>
	 * <p>
	 * XX最大心率 不超过240并大于最小心率
	 * </p>
	 * <p>
	 * XX睡眠检测 0x00关；0x01开
	 * </p>
	 * <p>
	 * XXXX睡觉时间 ｛0x09，0x0A｝>> 0x090A >> 9点10分
	 * </p>
	 * <p>
	 * XXXX起床时间 ｛0x07，0x00｝ >> 0x0700 >> 7点00分
	 * </p>
	 **/
	public abstract byte[] getByteForWatchSetting(int unit, int height, int weight, int age, int sex, int walkLength, int runLength, int hrOnoff, int hrMax, int hrMin, int sleepOnoff, int sleepHour,
			int sleepMin, int awakHour, int awakMin);

	/**
	 * 手表设置 <b>Total：18 bytes</b> 　　
	 * <p>
	 * 0x03
	 * </p>
	 * <p>
	 * XX公英制 0x00公制；0x01英制
	 * </p>
	 * <p>
	 * XXXX身高 ｛0x00，0xAF｝>> 0x00FF >> 175（厘米或者英寸）
	 * </p>
	 * <p>
	 * XXXX体重 ｛0x00，0x4B｝ >> 0x004B >> 75（公斤或者磅）
	 * </p>
	 * <p>
	 * XX年龄
	 * </p>
	 * <p>
	 * XX性别 0x00男；0x01女
	 * </p>
	 * <p>
	 * XX计步步长 略（厘米或者英寸）
	 * </p>
	 * <p>
	 * XX跑步步长 略（厘米或者英寸）
	 * </p>
	 * <p>
	 * XX心率开关 0x00关；0x01开
	 * </p>
	 * <p>
	 * XX最小心率 不小于30并小于最大心率
	 * </p>
	 * <p>
	 * XX最大心率 不超过240并大于最小心率
	 * </p>
	 * <p>
	 * XX睡眠检测 0x00关；0x01开
	 * </p>
	 * <p>
	 * XXXX睡觉时间 ｛0x09，0x0A｝>> 0x090A >> 9点10分
	 * </p>
	 * <p>
	 * XXXX起床时间 ｛0x07，0x00｝ >> 0x0700 >> 7点00分
	 * </p>
	 **/
	public abstract byte[] getByteForWatchSetting(WatchSetting setting);

	/**
	 * 运动计划 <b>Total：16 bytes</b>
	 * <p>
	 * 0x04
	 * </p>
	 * <p>
	 * XX运动计划：0x00关；0x01开
	 * </p>
	 * <p>
	 * XXXX起始时间：｛0x01，0x02 >> 0x0102 >> 01时02分
	 * </p>
	 * <p>
	 * XX运动目标：略（千米或者英里）
	 * </p>
	 * <p>
	 * XXXX目标距离：｛0x01，0x02 >> 0x0102 >> 258
	 * </p>
	 * <p>
	 * XX自动签到：0x00关；0x01开
	 * </p>
	 * <p>
	 * XX签到距离：略（千米或者英里）
	 * </p>
	 * <p>
	 * XX配速设置：0x00关；0x01开
	 * </p>
	 * <p>
	 * XXXX最慢配速：｛0x01，0x02 >> 0x0102 >> 01’02”
	 * </p>
	 * <p>
	 * XXXX最快配速：｛0x01，0x02>> 0x0102 >> 01’02”
	 * </p>
	 * <p>
	 * XX运动类型：0x00关；0x01开
	 * </p>
	 * <p>
	 * XX类型值：0x00步行；0x02骑行；0x03跑步
	 * </p>
	 **/
	public abstract byte[] getByteForSportPlanSetting(int sportPlanOnoff, int startHour, int startMin, int sportGoal, int sportGoalDistance, int autoSignOnoff, int signDistance, int paceOnoff,
			int paceMin, int paceMax, int sportStyleOnoff, int sportStyle);

	/**
	 * 运动计划 <b>Total：16 bytes</b>
	 * <p>
	 * 0x04
	 * </p>
	 * <p>
	 * XX运动计划：0x00关；0x01开
	 * </p>
	 * <p>
	 * XXXX起始时间：｛0x01，0x02 >> 0x0102 >> 01时02分
	 * </p>
	 * <p>
	 * XX运动目标：略（千米或者英里）
	 * </p>
	 * <p>
	 * XXXX目标距离：｛0x01，0x02 >> 0x0102 >> 258
	 * </p>
	 * <p>
	 * XX自动签到：0x00关；0x01开
	 * </p>
	 * <p>
	 * XX签到距离：略（千米或者英里）
	 * </p>
	 * <p>
	 * XX配速设置：0x00关；0x01开
	 * </p>
	 * <p>
	 * XXXX最慢配速：｛0x01，0x02 >> 0x0102 >> 01’02”
	 * </p>
	 * <p>
	 * XXXX最快配速：｛0x01，0x02>> 0x0102 >> 01’02”
	 * </p>
	 * <p>
	 * XX运动类型：0x00关；0x01开
	 * </p>
	 * <p>
	 * XX类型值：0x00步行；0x02骑行；0x03跑步
	 * </p>
	 **/
	public abstract byte[] getByteForSportPlanSetting(SportPlanSetting setting);

	/**
	 * 个人提醒设置 <b>Total：15 bytes</b>
	 * <p>
	 * 0x05
	 * </p>
	 * <p>
	 * XX来电提醒：0x00关；0x01开
	 * </p>
	 * <p>
	 * XX每日闹铃：0x00关；0x01开
	 * </p>
	 * <p>
	 * XXXX闹铃时间：｛0x01，0x02｝ >> 0x0102 >> 01时02分
	 * </p>
	 * <p>
	 * XX久坐提醒：0x00关；0x01开
	 * </p>
	 * <p>
	 * XXXX久坐阈值：0xFF >> 255 分钟
	 * </p>
	 * <p>
	 * xx生日提醒：0x00关；0x01开
	 * </p>
	 * <p>
	 * xxxx生日日期：｛0x01，0x02｝ >> 0x0102 >> 1月2日
	 * </p>
	 * <p>
	 * XXXX生日时间：｛0x01，0x02｝ >> 0x0102 >> 01时02分
	 * </p>
	 * <p>
	 * XX整点报时：0x00关；0x01开
	 * </p>
	 **/
	public abstract byte[] getByteForRemindSetting(int incomingOnoff, int alarmOnoff, int alarmHour, int alarmMin, int longSitOnoff, int longsitValue, int birthdayOnoff, int birthdayMonth,
			int birthdayDay, int birthdayHour, int birthdayMin, int nowTimeOnoff);

	/**
	 * 个人提醒设置 <b>Total：15 bytes</b>
	 * <p>
	 * 0x05
	 * </p>
	 * <p>
	 * XX来电提醒：0x00关；0x01开
	 * </p>
	 * <p>
	 * XX每日闹铃：0x00关；0x01开
	 * </p>
	 * <p>
	 * XXXX闹铃时间：｛0x01，0x02｝ >> 0x0102 >> 01时02分
	 * </p>
	 * <p>
	 * XX久坐提醒：0x00关；0x01开
	 * </p>
	 * <p>
	 * XXXX久坐阈值：0xFF >> 255 分钟
	 * </p>
	 * <p>
	 * xx生日提醒：0x00关；0x01开
	 * </p>
	 * <p>
	 * xxxx生日日期：｛0x01，0x02｝ >> 0x0102 >> 1月2日
	 * </p>
	 * <p>
	 * XXXX生日时间：｛0x01，0x02｝ >> 0x0102 >> 01时02分
	 * </p>
	 * <p>
	 * XX整点报时：0x00关；0x01开
	 * </p>
	 **/
	public abstract byte[] getByteForRemindSetting(RemindSetting setting);

	/**
	 * 请求手表配置信息
	 * <p>
	 * tag：0x06
	 * </p>
	 * <p>
	 * type：0xxx
	 * </p>
	 * 
	 * @param type
	 *            0x01——请求支持的功能 0x03——请求手表设置 0x04——请求计划运动设置 0x05——请求个人提醒设置
	 * @return
	 */
	public abstract byte[] getByteForRequstWatchSetting(int type);

	/**
	 * 请求当前的计步或轨迹数据（0x07）
	 * <p>
	 * tag：0x07
	 * </p>
	 * <p>
	 * type：0xXX
	 * </p>
	 * 
	 * @param type
	 *            0x00——请求当前的计步数据 0x01——请求当前的轨迹数据
	 * @return
	 */
	public abstract byte[] getByteForRequstSportInfo(int type);

	/**
	 * 请请求历史计步信息（0xA7）
	 * <p>
	 * tag：0xA7
	 * </p>
	 * <p>
	 * XX Type：1 bytes （请求类型） 0x00——同步所有计步信息 0x01——同步从 Start Time Stamp
	 * 开始记录的计步信息
	 * </p>
	 * <p>
	 * XXXXXXXX Start Time Stamp： 4 bytes（时间戳，仅在 type == 0x01 时有效，若 type == 0x00
	 * 可置为 0x000000） 从2000年1月1日0时0分0秒起已经过的秒数 如：0x12、0xCC、0x03、0x00 表示 long
	 * diffSeconds = 0x12CC0300；
	 * 即从2000年1月1日0时0分0秒起已经过了315360000秒，即10*365*24*3600；
	 * </p>
	 * 
	 * @param type
	 *            0x00——同步所有计步信息 0x01——同步从 Start Time Stamp 开始记录的计步信息
	 * */

	public abstract byte[] getByteForRequestStep(int type);

	/**
	 * 请求历史轨迹信息（0x08）
	 * <p>
	 * tag：0x08
	 * </p>
	 * <p>
	 * XX Type：1 bytes （请求类型） 0x00——同步所有轨迹信息 0x01——同步从 Start Time Stamp
	 * 开始记录的轨迹信息
	 * </p>
	 * <p>
	 * XXXXXXXX Start Time Stamp： 4 bytes（时间戳，仅在 type == 0x01 时有效，若 type == 0x00
	 * 可置为 0x000000） 从2000年1月1日0时0分0秒起已经过的秒数 如：0x12、0xCC、0x03、0x00 表示 long
	 * diffSeconds = 0x12CC0300；
	 * 即从2000年1月1日0时0分0秒起已经过了315360000秒，即10*365*24*3600；
	 * </p>
	 * 
	 * @param type
	 *            0x00——同步所有计步信息 0x01——同步从 Start Time Stamp 开始记录的计步信息
	 * */

	public abstract byte[] getByteForRequestPath(int type);

	/**
	 * 请求睡眠记录（0x0A）Total：6 bytes
	 * <p>
	 * XX tag：0x0A
	 * </p>
	 * <p>
	 * XX Type：1 bytes （请求类型） 0x00——同步所有睡眠信息 0x01——同步从 Start Time Stamp
	 * 开始记录的睡眠信息
	 * 
	 * </p>
	 * <p>
	 * XXXXXXXX Start Time Stamp：4 bytes（时间戳，仅在 type == 0x01 时有效，若 type == 0x00
	 * 可置为 0x000000） 从2000年1月1日0时0分0秒起已经过的秒数 如：0x12、0xCC、0x03、0x00 表示 long
	 * diffSeconds = 0x12CC0300；
	 * 即从2000年1月1日0时0分0秒起已经过了315360000秒，即10*365*24*3600；
	 * </p>
	 * 
	 * @param type
	 * @return
	 */
	public abstract byte[] getByteForRequestSleep(int type);
	
	
	
	
	/**
	 * 删除记录（0x0B） 11bytes
	 * 
	 * 如果 Type == 0x00, Subtype == 0x00, 
		则删除在 Start Date 与 End Date 之间（包括这两个时间点）的计步数据

	 * 
	 *  <p>
	 * XX tag：0x0B
	 * </p>
	 *  <p>
	 * XX Type：删除记录所属类别
	 * 	0x00——计步数据
		0x01——轨迹数据
		0x02——睡眠数据

	 * </p>
	 *  <p>
	 * XX Subtype：删除采用的方式
	 * 		0x00——按开始时间与结束时间删除
			0x01——删除所有

	 * </p>
	 *  <p>
	 * XXXXXXXX Start Date：删除的数据开始时间戳，从2000年1月1日0时0分0秒起已经过的秒数
如：0x12、0xCC、0x03、0x00 表示 long diffSeconds = 0x12CC0300；
即从2000年1月1日0时0分0秒起已经过了315360000秒，即10*365*24*3600；

	 * </p>
	 *  <p>
	 * XXXXXXXX End Date：删除的数据结束时间戳，同上

	 * </p>
	 * 
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public abstract byte[] getByteForRequestDelete(int type,long startDate, long endDate);
	
	/**
	 * 恢复出厂设置  4 bytes
	 * <p>
	 * XX tag：0x0C
	 * </p>
	 * <p>
	 * XXXXXX other 预留
	 * </p>
	 * @return
	 */
	public abstract byte[] getByteForRequestResetFactorySetting();
	
}
