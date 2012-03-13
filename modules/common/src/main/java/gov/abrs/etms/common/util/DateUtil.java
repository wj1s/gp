/**
 * 
 */
package gov.abrs.etms.common.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * 
 */
public class DateUtil {
	public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DAYTIME = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DAY = "yyyy-MM-dd";
	public static final String FORMAT_TIME = "HH:mm:ss";
	public static final String FORMAT_TIME_SHORT = "HH:mm";
	public static final String FORMAT_DAY_HOUR_MIN = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_YYYYMM = "yyyyMM";
	public static final String FORMAT_DAY_CN = "yyyy年MM月dd日";
	public static final String FORMAT_DAY_CN_HM = "yyyy年MM月dd日 HH:mm";
	public static final String FORMAT_DAY_CN_HMS = "yyyy年MM月dd日 HH:mm:ss";
	//只显示月和日，郭翔添加	
	public static final String FORMAT_DAY_CN_MD = "MM月dd日";

	// public static final String format = "yyyy-MM-dd";
	// public static final String formatDetail = "yyyy-MM-dd HH:mm:ss";
	// public static final String formatSimple = " HH:mm:ss";
	public static String addOneDay(String dateStr, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);
		Date d = formatter.parse(dateStr, pos);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, 1);
		d = calendar.getTime();
		return formatter.format(d);
	}

	public static Long getTimeSecond(String timeStr) {
		if (timeStr.length() == 0) {
			return new Long(0);
		}
		Long hour = Long.parseLong(timeStr.substring(0, (timeStr.length() - 6)), 10);
		Long min = Long.parseLong(timeStr.substring((timeStr.length() - 5), (timeStr.length() - 3)), 10);
		Long sec = Long.parseLong(timeStr.substring((timeStr.length() - 2)), 10);
		Long second = hour * 3600 + min * 60 + sec * 1;
		return second;
	}

	// 根据秒计算时分秒的字符串
	public static String getTimeHMSstr(Long second) {
		boolean fu = false;
		if (second < 0) {
			fu = true;
			second = 0 - second;
		}
		long hours = second / (60 * 60);
		long minus = (second - hours * 60 * 60) / 60;
		long secs = second - hours * 60 * 60 - minus * 60;
		String timeStr = "";
		if (hours < 10) {
			timeStr = timeStr + "0";
		}
		timeStr = timeStr + hours;
		timeStr = timeStr + ":";
		if (minus < 10) {
			timeStr = timeStr + "0";
		}
		timeStr = timeStr + minus;
		timeStr = timeStr + ":";
		if (secs < 10) {
			timeStr = timeStr + "0";
			;
		}
		timeStr = timeStr + secs;
		if (fu) {
			return "-" + timeStr;
		} else {
			return timeStr;
		}
	}

	public static String addDay(String dateStr, String format, int count) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);
		Date d = formatter.parse(dateStr, pos);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, count);
		d = calendar.getTime();
		return formatter.format(d);
	}

	public static Date addOneDay(Date date, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		date = calendar.getTime();
		return date;
	}

	public static Date addDay(Date date, int count) {
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
			calendar.add(Calendar.DATE, count);
			date = calendar.getTime();
			return date;
		} else {
			return null;
		}

	}

	// 按照格式将字符串转化成日期
	public static Date stringToDate(String dateStr, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);
		Date d = formatter.parse(dateStr, pos);
		return d;
	}

	// 按照格式将日期转化成字符串
	public static String dateToString(Date d, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if (d == null) {
			return "";
		} else {
			return formatter.format(d);
		}
	}

	// 按照格式将字符串转化为字符串
	public static String stringToString(String temp, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);
		Date d = formatter.parse(temp, pos);
		return dateToString(d, format);
	}

	// Date 转化成 Calendar
	public static Calendar dateToCalendar(Date date) {
		if (date == null) {
			return null;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		}

	}

	// 按照格式将日期转化成字符串
	public static Date dateToDateByFormat(Date d, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateStr = formatter.format(d);
		ParsePosition pos = new ParsePosition(0);
		Date date = formatter.parse(dateStr, pos);
		return date;
	}

	public static boolean beforeDay(Date dateCom, Date dateBase) {
		Calendar calendarCom = Calendar.getInstance();
		calendarCom.setTime(dateCom);
		Calendar calendarBase = Calendar.getInstance();
		calendarBase.setTime(dateBase);
		return calendarCom.before(calendarBase);
	}

	public static boolean afterDay(Date dateCom, Date dateBase) {
		Calendar calendarCom = Calendar.getInstance();
		calendarCom.setTime(dateCom);
		Calendar calendarBase = Calendar.getInstance();
		calendarBase.setTime(dateBase);
		return calendarCom.after(calendarBase);
	}

	public static Date addMonth(Date date, int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, count);
		Date result = calendar.getTime();
		return result;
	}

	public static Date addYear(Date date, int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, count);
		date = calendar.getTime();
		return date;
	}

	public static String addMonth(String dateStr, String format, int count) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);
		Date d = formatter.parse(dateStr, pos);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.MONTH, count);
		d = calendar.getTime();
		return formatter.format(d);
	}

	// 获得某一日期的后一天
	public static Date getNextDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day + 1);
		return calendar.getTime();
	}

	// 获得某一日期的前一天
	public static Date getPreviousDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day - 1);
		return calendar.getTime();
	}

	// 获得某年某月第一天的日期
	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		return calendar.getTime();
	}

	public static Date getFirstDayOfMonth(Date thisDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(thisDay);
		calendar.set(Calendar.DATE, 1);
		return calendar.getTime();
	}

	// 获得某年某月最后一天的日期
	public static Date getLastDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);
		return getPreviousDate(calendar.getTime());
	}

	public static Date getLastDayOfMonth(Date thisDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(thisDay);
		int thisMonth = thisDay.getMonth();
		calendar.set(Calendar.MONTH, thisMonth + 1);
		calendar.set(Calendar.DATE, 1);
		return getPreviousDate(calendar.getTime());
	}

	// 获得当前日期上一个月的最后一天
	public static String getLastDayOfLastMonth() {
		Date thisDay = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(thisDay);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return DateUtil.dateToString(calendar.getTime(), "yyyy-MM-dd");
	}

	// 获得当前日期上一个月的第一天
	public static String getFirstDayOfLastMonth() {
		Date thisDay = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(thisDay);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return DateUtil.dateToString(calendar.getTime(), "yyyy-MM-dd");
	}

	public static String getLastMonthOfDay() {
		StringBuffer sb = new StringBuffer();
		sb.append(getFirstDayOfLastMonth()).append("@");
		sb.append(getLastDayOfLastMonth());
		return sb.toString();
	}

	// 由年月日构建java.sql.Date类型
	public static Date buildDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date);
		return calendar.getTime();
	}

	// 取得某月的天数
	public static int getDayCountOfMonth(Date thisDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(thisDay);
		calendar.set(Calendar.DATE, 0);
		return calendar.get(Calendar.DATE);
	}

	// 取得开始日期和结束日期之间的天数
	public static int getDayCountOfDate(Date startDate, Date endDate) {
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(startDate);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(endDate);
		return calendarEnd.get(Calendar.DATE) - calendarStart.get(Calendar.DATE) + 1;
	}

	// 计算从一天到某一天的所有日期的LIST
	public static List<Date> getDateList(Date startDate, Date endDate) {
		List<Date> dateList = new ArrayList<Date>();
		String dateFormat = "yyyy-MM-dd";
		long seconds = endDate.getTime() - startDate.getTime();
		long marginDays = seconds / (24 * 60 * 60 * 1000);
		dateList.add(startDate);

		for (int i = 0; i < marginDays; i++) {
			startDate = DateUtil.addOneDay(startDate, dateFormat);
			dateList.add(startDate);
		}
		return dateList;
	}

	// 获得某年某季度的最后一天的日期
	public static Date getLastDayOfQuarter(int year, int quarter) {
		int month = 0;
		if (quarter > 4) {
			return null;
		} else {
			month = quarter * 3;
		}
		return getLastDayOfMonth(year, month);

	}

	// 获得某年某季度的第一天的日期
	public static Date getFirstDayOfQuarter(int year, int quarter) {
		int month = 0;
		if (quarter > 4) {
			return null;
		} else {
			month = (quarter - 1) * 3 + 1;
		}
		return getFirstDayOfMonth(year, month);
	}

	// 获得某年的第一天的日期
	public static Date getFirstDayOfYear(int year) {
		return getFirstDayOfMonth(year, 1);
	}

	// 获得某年的最后一天的日期
	public static Date getLastDayOfYear(int year) {
		return getLastDayOfMonth(year, 12);
	}

	// 判断系统时间是否在开始时间和结束时间范围内
	public static boolean valiSysTimeInScope(Date startDate, Date sysTime, Date endDate) {
		if (endDate.getTime() > sysTime.getTime() && sysTime.getTime() > startDate.getTime()) {
			return true;
		} else
			return false;
	}

	// 计算两个任意时间中间的间隔天数
	public static int getIntervalDays(Date startday, Date endday) {
		if (startday.after(endday)) {
			Date cal = startday;
			startday = endday;
			endday = cal;
		}
		long sl = startday.getTime();
		long el = endday.getTime();
		long ei = el - sl;
		return (int) (ei / (1000 * 60 * 60 * 24));
	}

	// 获得某天周一的日期
	public static Date getMonday(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.stringToDate(date, "yyyy-MM-dd"));
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	public static Date getMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.dateToDateByFormat(date, "yyyy-MM-dd"));
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	// 得到某天是星期几
	public static int getWeekDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.dateToDateByFormat(date, "yyyy-MM-dd"));
		cal.add(Calendar.DAY_OF_MONTH, -1);
		int x = cal.get(Calendar.DAY_OF_WEEK);
		return x;
	}

	// 获得某天周日的日期
	public static Date getSunday(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.stringToDate(date, "yyyy-MM-dd"));
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.add(Calendar.DAY_OF_MONTH, +6);
		return cal.getTime();
	}

	// 获得最小日期
	public static String getMinDate(String[] dates) {
		if (dates != null && dates.length != 0) {
			Date init = null;
			for (int i = 0; i < dates.length; i++) {
				Date date = DateUtil.stringToDate(dates[i], DateUtil.FORMAT_DAY);
				if (i == 0) {
					init = date;
				} else {
					if (init.after(date)) {
						init = date;
					}
				}
			}
			return DateUtil.dateToString(init, DateUtil.FORMAT_DAY);
		}
		return null;
	}

	// 获得最大日期
	public static String getMaxDate(String[] dates) {
		if (dates != null && dates.length != 0) {
			Date init = null;
			for (int i = 0; i < dates.length; i++) {
				Date date = DateUtil.stringToDate(dates[i], DateUtil.FORMAT_DAY);
				if (i == 0) {
					init = date;
				} else {
					if (init.before(date)) {
						init = date;
					}
				}
			}
			return DateUtil.dateToString(init, DateUtil.FORMAT_DAY);
		}
		return null;
	}

	public static Date getSunday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.dateToDateByFormat(date, "yyyy-MM-dd"));
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.add(Calendar.DAY_OF_MONTH, +6);
		return cal.getTime();
	}

	// 将一个时间段按天划分，放入集合
	public static List<Date[]> getDates(Date startTime, Date endTime) {
		List<Date[]> list = new ArrayList<Date[]>();
		String dateFormat = "yyyy-MM-dd";
		Date startDate = DateUtil.dateToDateByFormat(startTime, dateFormat);
		Date endDate = DateUtil.dateToDateByFormat(endTime, dateFormat);
		if (DateUtil.beforeDay(startDate, endDate)) {
			long seconds = endDate.getTime() - startDate.getTime();
			long marginDays = seconds / (24 * 60 * 60 * 1000);
			for (long i = 0; i < marginDays + 1; i++) {
				Date[] temp = new Date[2];
				if (i == 0) {
					temp[0] = startTime;
				} else {
					temp[0] = startDate;
				}
				if (i == marginDays) {
					temp[1] = endTime;
				} else {
					startDate = DateUtil.addOneDay(startDate, "yyyy-MM-dd");
					temp[1] = startDate;
				}
				list.add(temp);
			}
		} else {
			Date[] onlyOne = new Date[2];
			onlyOne[0] = startTime;
			onlyOne[1] = endTime;
			list.add(onlyOne);
		}
		return list;
	}

	// 根据日期获得是周几
	public static int getDateDayOfWeek(Date date) {
		int count = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		count = day - 1;
		if (count == 0) {
			count = 7;
		}
		return count;
	}

	// 根据日期获得"星期几"
	public static String getWeek(Date date) {
		String week = "";
		switch (DateUtil.getDateDayOfWeek(date)) {
		case 0:
			week = "星期日";
			break;
		case 1:
			week = "星期一";
			break;
		case 2:
			week = "星期二";
			break;
		case 3:
			week = "星期三";
			break;
		case 4:
			week = "星期四";
			break;
		case 5:
			week = "星期五";
			break;
		case 6:
			week = "星期六";
			break;
		case 7:
			week = "星期日";
			break;
		default:
			break;
		}
		return week;
	}

	//由日期和时间拼成DATE类型的数据
	public static Date spliceDate(Date date, Date time) {
		return DateUtil.createDateTime(DateUtil.getDateStr(date) + " " + DateUtil.getTimeStr(time));
	}

	/**    
	 * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式 ,如：2009-09-12   
	 * @param date2 被比较的时间
	 * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年    
	 * @return    
	 * 举例：  
	 * compareDate("2009-09-12", null, 0);//比较天  
	 * compareDate("2009-09-12", null, 1);//比较月  
	 * compareDate("2009-09-12", null, 2);//比较年  
	 */
	public static int compareDate(Date startDay, Date endDay, int stype) {
		int n = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		Date start = null;
		Date end = null;
		if (stype == 1) {
			start = DateUtil.dateToDateByFormat(startDay, "yyyy-MM");
			end = DateUtil.dateToDateByFormat(endDay, "yyyy-MM");
		} else {
			start = DateUtil.dateToDateByFormat(startDay, "yyyy-MM-dd");
			end = DateUtil.dateToDateByFormat(endDay, "yyyy-MM-dd");
		}
		try {
			c1.setTime(start);
			c2.setTime(end);
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		//List list = new ArrayList();     
		while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果     
			//list.add(df.format(c1.getTime()));    // 这里可以把间隔的日期存到数组中 打印出来     
			n++;
			if (stype == 1) {
				c1.add(Calendar.MONTH, 1); // 比较月份，月份+1     
			} else {
				c1.add(Calendar.DATE, 1); // 比较天数，日期+1     
			}
		}
		n = n - 1;
		if (stype == 2) {
			n = n / 365;
		}
		return n;
	}

	// 几个简便方法

	public static Date createTime(String time) {
		return DateUtil.stringToDate(time, DateUtil.FORMAT_TIME);
	}

	public static Date createDateTime(String dateTime) {
		return DateUtil.stringToDate(dateTime, DateUtil.FORMAT_DAYTIME);
	}

	public static Date createDate(String date) {
		return DateUtil.stringToDate(date, DateUtil.FORMAT_DAY);
	}

	public static String getTimeStr(Date time) {
		return DateUtil.dateToString(time, DateUtil.FORMAT_TIME);
	}

	public static String getTimeShortStr(Date time) {
		return DateUtil.dateToString(time, DateUtil.FORMAT_TIME_SHORT);
	}

	public static String getDateStr(Date date) {
		return DateUtil.dateToString(date, DateUtil.FORMAT_DAY);
	}

	public static String getDateTimeStr(Date date) {
		return DateUtil.dateToString(date, DateUtil.FORMAT_DAYTIME);
	}

	public static String getDateCNStr(Date date) {
		return DateUtil.dateToString(date, DateUtil.FORMAT_DAY_CN);
	}

	public static String getDateCNHMStr(Date date) {
		return DateUtil.dateToString(date, DateUtil.FORMAT_DAY_CN_HM);
	}
	
	public static String getDateCNHMSStr(Date date) {
		return DateUtil.dateToString(date, DateUtil.FORMAT_DAY_CN_HMS);
	}

	//只显示月和日，郭翔添加
	public static String getDateCNMDStr(Date date) {
		return DateUtil.dateToString(date, DateUtil.FORMAT_DAY_CN_MD);
	}

}
