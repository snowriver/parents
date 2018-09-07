package com.tenzhao.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 
 * Title: vote
 * Description:时间转换工具类
 * Company:
 * 创建时间:
 * 最后修改时间:
 * @author chenxj
 * @version 1.0
 * 
 */
public abstract class TimeUtils {

	/**
	 * 获取当前时间指定格式的字符串
	 * @param dateSymbol
	 * @return
	 */
	public static String nowStr(DateSymbol dateSymbol){
		Date date = new Date();
		return dateToStr(date,dateSymbol);
	}
	
	/**
	 * 将日期对象转换成对应格式的数字,最大支持到10位数，超过10位请使用{@link #dateToLong(Date, DateSymbol)}
	 * @param date
	 * @param dateSymbol
	 * @return
	 */
	public static int dateToInt(Date date ,DateSymbol dateSymbol){
		if(!(dateSymbol.name().indexOf("_DIGIT")>=0)){
			throw new IllegalArgumentException("Please use the digital format");
		}
		if(dateSymbol.pattern.length()>10){
			throw new IllegalArgumentException("The number that needs to be returned is too long,please use method 'dateToLong'");
		}
		DateFormat df = new SimpleDateFormat(dateSymbol.pattern);
		return Integer.parseInt(df.format(date));
	}
	
	/**
	 * 获取指定日期的unix时间戳
	 * @param date
	 * @return
	 */
	public static int unixTimestamp(Date date){
		return (int)(date.getTime()/1000l);
	}
	/**
	 * 返回指定时间毫秒数对应的罗马日历
	 * @param date
	 * @return
	 */
	public static Calendar milliSecondToCalendar(long milliSecond){
		Calendar calendar =Calendar.getInstance();
		calendar.setTimeInMillis(milliSecond);
		return calendar;
	}
	
	/**
	 * 获取指定日期的unix时间戳
	 * @param date
	 * @return
	 */
	public static int unixTimestamp(Calendar calendar){
		return (int)(calendar.getTimeInMillis()/1000l);
	}
	
	/**
	 * 将日期对象转换成对应格式的数字,最大支持到10位数，超过10位请使用
	 * @param date
	 * @param dateSymbol
	 * @return
	 */
	public static Long dateToLong(Date date ,DateSymbol dateSymbol){
		if(!(dateSymbol.name().indexOf("_DIGIT")>=0)){
			throw new IllegalArgumentException("Please use the digital format");
		}
		DateFormat df = new SimpleDateFormat(dateSymbol.pattern);
		return Long.parseLong(df.format(date));
	}
	
	/**
	 * 将指定日期按指定的时间格式返回
	 * @param date
	 * @param dateSymbol
	 * @return
	 */
	public static String dateToStr(Date date ,DateSymbol dateSymbol){
		DateFormat df = new SimpleDateFormat(dateSymbol.pattern);
		return df.format(date);
	}
	
	/**
	 * 根据时间毫秒返回对应时间格式的字符串;如果是unix时间戳可以乘以1000后作为参数值;
	 * @param timeMillis 如果等于0,返回“1970-01-01 08:00:00”,如果是负数,则返回1970-01-01 08:00:00之前的时间
	 * @param dateSymbol
	 * @return
	 */
	public static String milliSecondToDateStr(long timeMillis,DateSymbol dateSymbol){
		return dateToStr(new Date(timeMillis) ,dateSymbol);
	}

	/**
	 * 根据时间毫秒返回对应时间格式的字符串;如果是unix时间戳可以乘以1000后作为参数值;
	 * @param timeMillis 如果等于0,返回“1970-01-01 08:00:00”,如果是负数,则返回1970-01-01 08:00:00之前的时间
	 * @param dateSymbol
	 * @return
	 */
	public static String secondsToDateStr(int unixtimestamp,DateSymbol dateSymbol){
		return dateToStr(new Date(unixtimestamp*1000l) ,dateSymbol);
	}
	
	/**
	 * 长整型 yyyyMMddHHmmss 转换为 yyyy-MM-dd HH:mm:ss 字符串格式
	 * @param longDate
	 * @return
	 */
	public static String yearToSecondDigitToStr(long longDate) {
		String m = longDate+"";
	    return (m.substring(0,4)+"-"+m.substring(4,6)+"-"+m.substring(6,8)+" "+m.substring(8,10)+":"+m.substring(10,12)+":"+m.substring(12,14));
	}
	
	public static Date calendarToDate(Calendar calendar){
		return calendar.getTime();
	}
	
	public static Calendar dateToCalendar(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar ;
	}
	
	public static String calendarToStr(Calendar calendar,DateSymbol dateSymbol){
		return dateToStr(calendar.getTime(),dateSymbol);
	}
	
	public static Calendar strToCalendar(String dateStr,DateSymbol dateSymbol){
		return dateToCalendar(strToDate(dateStr,dateSymbol));
	}
	
	/**
	 * 添加指定数量后的日期
	 * @param date
	 * @param timeUnit 添加的单位
	 * @param amount 整数为增加，负数为减少
	 * @return
	 */
	public static Date dateAdd(Date date,TimeUnit timeUnit, int amount){
		Calendar calendar  = Calendar.getInstance();
		calendar.setTime(date);
		return dateAdd(calendar,timeUnit,amount);
	}
	
	/**
	 * 罗马日历加减操作后返回日期 
	 */
	public static Date dateAdd(Calendar calendar,TimeUnit timeUnit, int amount){
		calendar.add(timeUnit.value, amount);
		return calendar.getTime();
	}
	
	public static Date dateAddYear(Date date,int amount){
		return dateAdd(date,TimeUnit.YEAR,amount);
	}
	public static Date dateAddMonth(Date date,int amount){
		return dateAdd(date,TimeUnit.MONTH,amount);
	}
	public static Date dateAddDate(Date date,int amount){
		return dateAdd(date,TimeUnit.DATE,amount);
	}
	public static Date dateAddHour(Date date,int amount){
		return dateAdd(date,TimeUnit.HOUR,amount);
	}
	public static Date dateAddMinute(Date date,int amount){
		return dateAdd(date,TimeUnit.MINUTE,amount);
	}
	public static Date dateAddSecond(Date date,int amount){
		return dateAdd(date,TimeUnit.SECOND,amount);
	}
	public static Date dateAddMilliSecond(Date date,int amount){
		return dateAdd(date,TimeUnit.MILLISECOND,amount);
	}
	public static String dateAddYear(Date date,int amount,DateSymbol dateSymbol){
		return dateToStr(dateAdd(date,TimeUnit.YEAR,amount),dateSymbol);
	}
	public static String dateAddMonth(Date date,int amount,DateSymbol dateSymbol){
		return dateToStr(dateAdd(date,TimeUnit.MONTH,amount),dateSymbol);
	}
	public static String dateAddDate(Date date,int amount,DateSymbol dateSymbol){
		return dateToStr(dateAdd(date,TimeUnit.DATE,amount),dateSymbol);
	}
	public static String dateAddHour(Date date,int amount,DateSymbol dateSymbol){
		return dateToStr(dateAdd(date,TimeUnit.HOUR,amount),dateSymbol);
	}
	public static String dateAddMinute(Date date,int amount,DateSymbol dateSymbol){
		return dateToStr(dateAdd(date,TimeUnit.MINUTE,amount),dateSymbol);
	}
	public static String dateAddSecond(Date date,int amount,DateSymbol dateSymbol){
		return dateToStr(dateAdd(date,TimeUnit.SECOND,amount),dateSymbol);
	}
	public static String dateAddMilliSecond(Date date,int amount,DateSymbol dateSymbol){
		return dateToStr(dateAdd(date,TimeUnit.MILLISECOND,amount),dateSymbol);
	}
	/**
	 * 获取指定日期所在月份多少天
	 * @return
	 */
	public static int dayCountOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);
		calendar.set(Calendar.DATE, 1);  
		calendar.roll(Calendar.DATE, -1);  
		return calendar.get(Calendar.DATE); 
	}
	
	/**
	 * 获取指定日期格式字符串所在月份的天数
	 * @see #dayCountOfMonth(Date)
	 */
	public static int dayCountOfMonth(String dateStr,DateSymbol dateSymbol) {
		return dayCountOfMonth(strToDate(dateStr,dateSymbol));
	}
	
	
	/**
     * 
     * 获取指定日期所在周，指定某一天的日期
	 */
	public static Date dateOfSpecifyWeekDay(Date date,WeekDay weekDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(TimeUnit.DATE.value,(weekDay.ordinal()+1)-(calendar.get(Calendar.DAY_OF_WEEK)-1));
		return calendar.getTime();
	}
	
	/**
     * 同时可用于验证传入的日期字符串是否正常值并返回出错位置：例如 2015-01-32<br>
     * @param dateStr 字符串不带年份时返回的是1970年的日期
     * @param format
     * @return
     */
	public static Date strToDate(String dateStr,DateSymbol dateSymbol){
		Date parsedValue = null;
		DateFormat df = new SimpleDateFormat(dateSymbol.pattern);
		//是否允许将日期转换为宽松，是的话只验证格式，不验证具体值,否：验证格式同时验证值例如：2016-02-30
		df.setLenient(false);
		try {
			// Parse the Date ParsePosition类用于定位出错误的字符串中出错字符的索引位置
			ParsePosition pos = new ParsePosition(0);
			parsedValue = df.parse(dateStr, pos);
			if (pos.getErrorIndex() > -1) {
				throw new ParseException(String.format("Error parsing date '%s' at position=%d", dateStr,pos.getErrorIndex()),pos.getErrorIndex());
			}
			if (pos.getIndex() < dateStr.length()) {
				throw new ParseException(String.format("Date '%s' contains unparsed characters from position=%d", dateStr,pos.getIndex()),pos.getIndex());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parsedValue;
	}
	
	
	public enum WeekDay{Monday,Tuesday,Wednesday,Thursday,Friday,Saturday ,Sunday}
	public enum TimeUnit{
		YEAR(Calendar.YEAR)
		,MONTH(Calendar.MONTH)
		,DATE(Calendar.DATE)
		,HOUR(Calendar.HOUR)
		,MINUTE(Calendar.MINUTE)
		,SECOND(Calendar.SECOND)
		,MILLISECOND(Calendar.MILLISECOND);
		private int value;
		private TimeUnit(int value){
			this.value = value;
		}
		public int getValue(){
			return value ;
		}
	}
	public enum DateSymbol{
		 // RFC 822 Date Format
	    RFC822_DATE("EEE, dd MMM yyyy HH:mm:ss z"),
		/** 年 */
		YEAR("yyyy"),
		/** 年、月, 字符格式 yyyy-MM */
		YEAR_TO_MONTH("yyyy-MM"),
		
		/** 年、月, 数字格式 yyyyMM */
		YEAR_TO_MONTH_DIGIT("yyyyMM"),
		
		/** 长日期字符格式：yyyy-MM-dd */
		YEAR_TO_DATE("yyyy-MM-dd"),
		
		/**长日期数字格式：yyyyMMdd */
		YEAR_TO_DATE_DIGIT("yyyyMMdd"),
		
		/** 长日期到小时 字符格式：yyyy-MM-dd HH:mm*/
		YEAR_TO_HOUR("yyyy-MM-dd HH"),
		
		/** 长日期到小时数字格式： yyyyMMddHH*/
		YEAR_TO_HOUR_DIGIT("yyyyMMddHH"),
		
		/** 长日期到分钟 字符格式：yyyy-MM-dd HH:mm*/
		YEAR_TO_MINUTE("yyyy-MM-dd HH:mm"),
		
		/** 长日期到分钟数字格式： yyyyMMddHHmm*/
		YEAR_TO_MINUTE_DIGIT("yyyyMMddHHmm"),
		
		/** 长日期到秒, 字符格式：yyyy-MM-dd HH:mm:ss */
		YEAR_TO_SECOND("yyyy-MM-dd HH:mm:ss"),
		
		/** 长日期到秒数字格式：yyyyMMddHHmmss */
		YEAR_TO_SECOND_DIGIT("yyyyMMddHHmmss"),
		
		/** 长日期到毫秒的长日期和时间,字符格式 yyyy-MM-dd HH:mm:ss.SSS */
		YEAR_TO_MSEC("yyyy-MM-dd HH:mm:ss.SSS"),
		
		/** 长日期到毫秒的长日期和时间,数字格式 yyyyMMddHHmmssSSS */
		YEAR_TO_MSEC_DIGIT("yyyyMMddHHmmssSSS"),
		
		/** 月 */
		MONTH("MM"),
		/** 月、日,字符格式 MM-dd */
		MONTH_DATE("MM-dd"),
		/** 月、日, 数字格式 MMdd */
		MONTH_DATE_DIGIT("MMdd"),
		/** 月到小时,字符格式MM-dd HH */
		MONTH_TO_HOUR("MM-dd HH"),
		/** 月到小时,数字格式 MMddHH */
		MONTH_TO_HOUR_DIGIT("MMddHH"),
		/** 月到分钟,字符格式 MM-dd HH:mm */
		MONTH_TO_MINUTE("MM-dd HH:mm"),
		/** 月到分钟,数字格式 MMddHHmm */
		MONTH_TO_MINUTE_DIGIT("MMddHHmm"),
		/** 月到秒,字符格式 MM-dd HH:mm:ss */
		MONTH_TO_SECOND("MM-dd HH:mm:ss"),
		/** 月到秒,数字格式 MMddHHmmss */
		MONTH_TO_SECOND_DIGIT("MMddHHmmss"),
		/** 月到毫秒,字符格式 MM-dd HH:mm:ss.SSS */
		MONTH_TO_MESEC("MM-dd HH:mm:ss.SSS"),
		/** 月到毫秒,数字格式 MMddHHmmssSSS */
		MONTH_TO_MESEC_DIGIT("MMddHHmmssSSS"),
		
		/** 日 */
		DATE("dd"),
		/** 日、时,字符格式dd HH */
		DATE_HOUR("dd HH"),
		/** 日、时,数字格式 ddHH */
		DATE_HOUR_DIGIT("ddHH"),
		/** 日到分钟,字符格式 dd HH:mm */
		DATE_TO_MINUTE("dd HH:mm"),
		/** 日到分钟,数字格式 ddHHmm */
		DATE_TO_MINUTE_DIGIT("ddHHmm"),
		/** 日到秒,字符格式 dd HH:mm:ss */
		DATE_TO_SECOND("dd HH:mm:ss"),
		/** 日到秒,数字格式 ddHHmmss */
		DATE_TO_SECOND_DIGIT("ddHHmmss"),
		/** 日到毫秒,字符格式dd HH:mm:ss.SSS */
		DATE_TO_MESEC("dd HH:mm:ss.SSS"),
		/** 日到毫秒,数字格式 ddHHmmssSSS */
		DATE_TO_MESEC_DIGIT("ddHHmmssSSS"),
		
		/** 24小时 */
		HOUR("HH"),
		
		/** 时分, 字符格式 HH:mm */
		HOUR_MINUTE("HH:mm"),
		
		/** 时分,数字格式 HHmm */
		HOUR_MINUTE_DIGIT("HHmm"),
		
		/** 长时间字符格式：HH:mm:ss */
		HOUR_TO_SECOND("HH:mm:ss"),
		
		/** 长时间数字格式 HHmmss*/
		HOUR_TO_SECOND_DIGIT("HHmmss"),
		
		/** 长时间到毫秒,字符格式：HH:mm:ss.SSS */
		HOUR_TO_MESC("HH:mm:ss.SSS"),
		
		/** 长时间到毫秒,数字格式 HHmmssSSS*/
		HOUR_TO_MESC_DIGIT("HHmmssSSS"),
		
		/** 分钟 */
		MINUTE("mm"),
		
		/** 分:秒，字符格式 ：mm:ss */
		MINUTE_SECOND("mm:ss"),
		
		/** 分秒,数字格式：mmss */
		MINUTE_SECOND_DIGIT("mmss"),
		
		/** 分到毫秒，字符格式 ：mm:ss */
		MINUTE_TO_MESC("mm:ss.SSS"),
		
		/** 分到毫秒,数字格式：mmss */
		MINUTE_TO_MESC_DIGIT("mmssSSS"),
		/** 秒*/
		SECOND("ss"),
		SECOND_MSEC("ss.SSS"),
		SECOND_MSEC_DIGIT("ssSSS"),
		/** 毫秒 */
		MSEC("SSS"), 
		/** yyyy年MM月dd日 HH:mm:ss */
		YEAR_TO_SECOND_GBK("yyyy年MM月dd日 HH:mm:ss"),
		/**yyyy年MM月dd日 */
		YEAR_TO_DATE_GBK("yyyy年MM月dd日"),
		/** yyyy年MM月dd日 HH:mm */
		YEAR_TO_MINUTE_GBK("yyyy年MM月dd日 HH:mm")
		;
		
		private String pattern ;
		private DateSymbol(String pattern){
			this.pattern = pattern ;
		}
		public String getPattern(){
			return pattern;
		}
		
	/*	private static final Map<String,DateSymbol> _dateSymbolMap = Maps.newHashMapWithExpectedSize(DateSymbol.values().length);
		static {
			for(DateSymbol en : DateSymbol.values()){
				_dateSymbolMap.put(en.getPattern(), en);
			}
		}
		
		public static DateSymbol getInstance(String pattern){
			return _dateSymbolMap.get(pattern);
		}*/
	}
}
