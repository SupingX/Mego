package com.mycj.jusd.protocol;

import com.mycj.jusd.bean.RemindSetting;
import com.mycj.jusd.bean.WatchSetting;
import com.mycj.jusd.bean.news.SleepHistory;
import com.mycj.jusd.bean.news.SportPlanSetting;
import com.mycj.jusd.ui.activity.SportInfo;

public abstract class AbstractProtolNotify {
	/**
	 * 手环支持的功能 <b>2 bytes</b>
	 * <p>
	 * tag : 0x01
	 * </p>
	 * <p>
	 * value : 0x03-0x0F (至少支持时钟与计步)
	 * </p>
	 * <p>
	 * bit0 bit1 bit2 bit3 (0不支持 1支持)
	 * </p>
	 * <p>
	 * 0x03 表示仅支持时钟与计步 0x0F 表示全部支持
	 * </p>
	 * @return int []{时钟 计步 心率 GPS}
	 */
	public abstract int[] parseSupportFeature(byte[] data);

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
	 * **/
	public abstract WatchSetting parseWatchSetting(byte[] data);

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
	public abstract SportPlanSetting parseSportPlanSetting(byte[] data);

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
	public abstract RemindSetting parseRemindSetting(byte[] data);

	/**
	 * 当前运动信息 <b>Total：15 bytes</b>
	 * <p>
	 * tag:0x07
	 * </p>
	 * <p>
	 * XX Type：1 bytes 0x00——计步数据；0x01——轨迹数据
	 * </p>
	 * <p>
	 * XXXXXX Steps：3 bytes（步数，仅在计步数据时有效）：｛0x00，0x01，0x02｝ >> 0x000102 >> 258 步
	 * </p>
	 * <p>
	 * XXXXXX Dist：3 bytes（距离，千米/英里） ｛0x01，0x02，0x03｝ >> 0x010203 >> 66051
	 * 此值为实际值100倍，所以真实值为 66051 / 100.f = 660.51
	 * </p>
	 * <p>
	 * XXXXXX Time： 3 bytes（用时，Seconds）｛0x01，0x02，0x03｝ >> 0x010203 >> 用时 66051
	 * 秒 Calorie：3 bytes（消耗能量，Kcal） ｛0x01，0x02，0x03｝ >> 0x010203 >> 66051
	 * 此值为实际值100倍，所以真实值为 66051 / 100.f = 660.51 Kcal
	 * </p>
	 * <p>
	 * XXAVG HR：1 bytes（平均心率）
	 * </p>
	 **/
	public abstract SportInfo parseSportInfo(byte[] data);

	/**
	 * 历史计步信息发送状态 （0xF7）
	 * <p>
	 * tag：0xF7
	 * </p>
	 * <p>
	 * XX Type： 0x00——同步计步开始 0x01——同步计步结束
	 * </p>
	 * *
	 * <p>
	 * XXXX Total Count：（总计有多少条数据，只需要在同步计步开始时返回，计步结束时可置零） ｛0x01，0x02｝ >> 0x0102
	 * >> 共计 258 条数据
	 * </p>
	 * 
	 * @param data
	 * @return
	 */
	public abstract int[] parseSyncHistoryStepStatus(byte[] data);

	/**
	 * 历史计步信息（0xA7）
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
	 * <p>
	 * XXXX Data Index：2
	 * bytes（此条数据所属哪一次计步运动的次序，比如今天运动了10次，共计数据300条，这个是表示此条数据属于10次中的哪一次）
	 * 默认从0开始计算一直到 0xFF、0xFF 例：｛0x0A，0x0B｝ >> 0x0A0B >> 第2571 + 1 次运动
	 * </p>
	 * <p>
	 * XXXXXX Steps：3 bytes ｛0x01，0x02，0x03｝ >> 0x010203 >> 66051 步
	 * </p>
	 * <p>
	 * XXXXXX Distance：3 bytes （前两个字节表示整数，后一个字节为小数，KM/英里） ｛0x01，0x02，0x03｝ >>
	 * 0x010203 >> 66051 此数为实际数的100倍，实际值为 66051 / 100.f = 660.51
	 * </p>
	 * <p>
	 * XXXX Time：2 bytes （耗时，秒） ｛0x0F，0x0A｝ >> 0x0F0A >> 3850 秒
	 * </p>
	 * <p>
	 * XXXXXX Calorie：3 bytes （前两个字节表示整数，后一个字节为小数，Kcal） ｛0x01，0x02，0x03｝ >>
	 * 0x010203 >> 66051 此数为实际数的100倍，实际值为 66051 / 100.f = 660.51 Kcal
	 * </p>
	 * <p>
	 * XX HR：1（心率 bpm）
	 * </p>
	 * */
	public abstract Object parseHistoryStep(byte[] data);

	/**
	 * 历史轨迹信息发送状态 （0xF8）
	 * <p>
	 * tag：0xF8
	 * </p>
	 * <p>
	 * XX Type： 0x00——同步轨迹开始 0x01——同步轨迹结束
	 * </p>
	 * *
	 * <p>
	 * XXXX Total Count：（总计有多少条数据，只需要在同步轨迹开始时返回，轨迹结束时可置零） ｛0x01，0x02｝ >> 0x0102
	 * >> 共计 258 条数据
	 * </p>
	 * 
	 * @param data
	 * @return
	 */
	public abstract int[] parseSyncHistoryPathStatus(byte[] data);

	/**
	 * 历史计步信息（0x08）Total：20 bytes
	 * <p>
	 * tag：0x08
	 * </p>
	 * <p>
	 * XXXXXXXX Start Time Stamp： 4 bytes（时间戳，仅在 type == 0x01 时有效，若 type == 0x00
	 * 可置为 0x000000） 从2000年1月1日0时0分0秒起已经过的秒数 如：0x12、0xCC、0x03、0x00 表示 long
	 * diffSeconds = 0x12CC0300；
	 * 即从2000年1月1日0时0分0秒起已经过了315360000秒，即10*365*24*3600；
	 * </p>
	 * <p>
	 * XX Data Index：1
	 * bytes（此条数据所属哪一次计步运动的次序，比如今天运动了10次，共计数据300条，这个是表示此条数据属于10次中的哪一次）
	 * 默认从0开始计算一直到 0xFF、0xFF 例：｛0x0A，0x0B ｝>> 0x0A0B >> 第2571 + 1 次运动
	 * 
	 * </p>
	 * <p>
	 * XX Mark Index：1 bytes（自动签到索引） 当 index == 0 时表示无签到，index == 1 时表示此次运动（Data
	 * Index 相同为同一次运动）的开始（即此次运动的第一条数据），依次+1
	 * 
	 * </p>
	 * <p>
	 * <修改>
	 * XXXXXXXX Lng：4 bytes经度 （第一个字节的最高位是符号位，0正1负） ｛0x01，0x02，0x03｝>> 0x010203 >>
	 * 66051 此结果为实际经度的 10000倍，即实现为 6.6051 {0x81，0x02，0x03} >> 0x810203 >>
	 * 8454659 当首字节的最高位为1，也就是 0x81 的最高位是1时，表示这个经度是负值，即表示 0x810203 >> 负0x010203
	 * >> 负66051 所以结果为：负6.6051
	 * 
	 * </p>
	 * <p>
	 * <修改>
	 * XXXXXX Lat：4 bytes 纬度 同经度
	 * 
	 * </p>
	 * <p>
	 * XXXX Dist：2 bytes 距离 ｛0x01，0x02｝>> 0x0102 >> 258 258 是距离的100倍，实际的距离是 258
	 * / 100.f = 2.58 （千米/英里）
	 * 
	 * </p>
	 * <p>
	 * XXXX Time：2 bytes 时间 ｛0x01，0x02｝ >> 0x0102 >> 258 秒
	 * 
	 * </p>
	 * *
	 * <p>
	 * XXXX Calorie：2 bytes 消耗能量 ｛0x01，0x02｝>> 0x0102 >> 258 258 是距离的100倍，实际的距离是
	 * 258 / 100.f = 2.58 （Kcal）
	 * 
	 * 
	 * </p>
	 * *
	 * <p>
	 * XX 1 bytes 心率
	 * </p>
	 * */
	public abstract Object parseHistoryPath(byte[] data);

	/**
	 * 睡眠信息 （0x0A）Total：4 bytes
	 * <p>
	 * tag：0x0A
	 * </p>
	 * <p>
	 * XX Type：0x02 是真实数据，主要为了与状态区分开
	 * </p>
	 * *
	 * <p>
	 * XXXXXXXX Start Date：睡眠数据开始时间戳，从2000年1月1日0时0分0秒起已经过的秒数
	 * 如：0x12、0xCC、0x03、0x00 表示 long diffSeconds = 0x12CC0300；
	 * 即从2000年1月1日0时0分0秒起已经过了315360000秒，即10*365*24*3600；
	 * 
	 * </p>
	 * XXXXXXXX End Date：睡眠数据结束时间戳，同上 </p> </p> XXXX Light Sleep：浅睡时间（分钟）
	 * ｛0x01，0x02｝>> 0x0102 >> 258 分钟
	 * 
	 * </p> </p> XXXX Deep Sleep：深睡时间（分钟） 同上
	 * 
	 * </p> * </p> XXXX 预留 同上
	 * 
	 * </p>
	 * 
	 * @param data
	 * @return
	 */
	public abstract int[] parseHistorySleepStatus(byte[] data);

	/**
	 * 
	 * @param data
	 * @return
	 */
	public abstract SleepHistory parseHistorySleep(byte[] data);

}
