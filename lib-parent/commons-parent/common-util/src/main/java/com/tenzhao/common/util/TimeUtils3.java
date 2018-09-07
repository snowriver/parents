package com.tenzhao.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


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
public class TimeUtils3 {
    
    private TimeUtils3() {}
    
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String ONLY_YEAR = "yyyy" ;
    public static final String ONLY_MONTH = "MM" ;
    public static final String ONLY_DATE = "dd" ;
    public static final String ONLY_HOUR = "HH" ;
    public static final String ONLY_MINUTE = "mm" ;
    public static final String ONLY_SECOND = "dd" ;
    public static final String SHORT_DATE = "yyyyMMdd" ;
    public static final String LONG_DATE_TIME = "yyyyMMddHHmmss";
    public static final String SHORT_DATE_TXT = "yyyy-MM-dd";
    
    /**
     * 获取当天日期字符串 格式为：yyyyMMdd
     * @return 当天日期字符串
     * @see getCurrDate(Date currDate)
     */
    public static String getCurrDate() {
        Date currDate = new Date();
        
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String strCurrDate = df.format(currDate);
        
        return strCurrDate;
    }
    
    public static String getCurrDateTime() {
        Date currDate = new Date();
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strCurrDate = df.format(currDate);
        
        return strCurrDate;
    }
    public static String getCurrDateTime2() {
    	Date currDate = new Date();
    	
    	DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	String strCurrDate = df.format(currDate);
    	
    	return strCurrDate;
    }
    
    /**
     * 
     * 获取当天日期字符串 格式为：yyyyMMdd
     * @return 当天日期字符串
     * @see getCurrDate()
     */
    public static String getCurrDate(Date currDate) {
    	
    	DateFormat df = new SimpleDateFormat("yyyyMMdd");
    	String strCurrDate = df.format(currDate);
    	
    	return strCurrDate;
    }
    
    /**
     * 传入一个日期字符串，并将其转换成 yyyyMMdd 格式Date类，再将该Date类转换成罗马日历时间
     * @param strDate 日期字符串
     * @return 罗马日历时间
     */
    public static Calendar formatCalendar(String strDate) {

        try {
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            Date date = df.parse(strDate);
            Calendar tmpCal = Calendar.getInstance();
            tmpCal.setTime(date);
            
            return tmpCal;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * 时间字符串按 YYMMDDHH24MISS 格式转换成date类型
     * @param strDate 时间格式字符串
     * @return 按 yyMMddHHmmss 格式转换后的Date
     */
    public static Date convertDate(String strDate) {
        Date date = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
    /**
     * 将一个时间字符串按自定义格式转换成Date
     * @param strDate  时间字符串
     * @param format 自定义时间格式
     * @return 返回自定义时间格式后的Date类
     */
    public static Date convertDateFrom(String strDate,String format) {
        Date date = null;

        DateFormat df = new SimpleDateFormat(format);
        try {
            date = df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
    /**
     * 按自定义格式返回加、减运算后的时间字符串
     * @param i 指定用于运算的时间天数 
     * @param format 自定义的时间格式
     * @return 返回指定的当前时间的前后那天的时间字符串，例如参数 i 为 3时 返回当前时间加3天后的时间字符串 为负数时减
     */
	public static String getDate(int i, String format)
	{
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, i);
		SimpleDateFormat sf = new SimpleDateFormat(format);

		return sf.format(now.getTime());
	}

	public static void main(String[] args){
		System.out.println(getDate(40, "yyyyMMdd"));
	}
	
	/**
	 * 根据参数选择SimpleDateFormat的自定义格式
	 * 
	 * @param interval 2 SimpleDateFormat的格式为 yyyy/MM/dd<br> 3 yyyy/MM <br> 4 yyyy <br> 否则 HH:mm
	 *           
	 * @return 带格式SimpleDateFormat
	 */
	public static SimpleDateFormat getDateFormat(int interval)
	{

		switch (interval)
		{
			case 2:
				return new SimpleDateFormat("yyyy/MM/dd");
			case 3:
				return new SimpleDateFormat("yyyy/MM");
			case 4:
				return new SimpleDateFormat("yyyy");
			case 5:
				return new SimpleDateFormat("yyyy-MM-dd");
			default:
				return new SimpleDateFormat("HH:mm");
		}
	}
	
	
    /**
     * 获取上个小时的整点时间，格式 Wed Dec 02 23:00:00 CST 2009
     * @return 返回上个小时的整点时间，分钟和秒都为00
     */
	public static Date getLastHour() {
        Date tmp = new Date(System.currentTimeMillis()-60*60*1000);
        return strToDate(dateToStr(tmp,"yyyy-MM-dd HH"), "yyyy-MM-dd HH") ;
    }
    /**
     * 获取上个小时的整点时间的字符串， 格式为：yyyyMMddHHmmss
     * @return 返回上个小时的整点时间，分钟和秒都为00 
     */
    public String getLastHourStr() {
    	return formatDateYYYYMMDDHH24MMSS(getLastHour());
    }
    
    /**
     * 将指定时间按 yyyyMMddHHmmss 返回字符串
     * @param date 时间类型
     * @return 返回指定格式时间字符串 格式为 yyyyMMddHHmmss
     */
    public static String formatDateYYYYMMDDHH24MMSS(Date date) {
    	DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    	String dateStr = df.format(date);
    	return dateStr;
    }
    
    /**
     * 将指定时间按 yyyyMMddHH 返回字符串
     * @param date 时间类型
     * @return 返回指定格式时间字符串 格式为 yyyyMMddHH
     */
    public static String formatDateYYYYMMDDHH24(Date date) {
    	DateFormat df = new SimpleDateFormat("yyyyMMddHH");
    	String dateStr = df.format(date);
    	
    	return dateStr;
    }
    
    /**
     * 时间格式转换 yyyy-MM-dd HH:mm:ss
     * @return
     * @author cxj
     */
    public static String formatDate(Date date){
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String dateStr = df.format(date);
    	
    	return dateStr;
    }

    /**
     * 返回当前整点时间的字符串 格式为：yyyyMMddHHmmss 例如：20091202230000
     * @return 返回当前整点时间的字符串，分钟和秒都为00
     */
    public static String getCurrentHourStr() {
    	
    	return formatDateYYYYMMDDHH24MMSS(getCurrentHour());
    }
    
    /**
     *  获取当前小时时间 eg:Wed Dec 02 23:00:00 CST 2009
     * @return 当前的整点时间 分钟和秒都为00
     */
	public static Date getCurrentHour() {
        Date tmp = new Date(System.currentTimeMillis());
        return strToDate(String.format("%tY-%tm-%td %tH:%tM:%tS", tmp,tmp,tmp,tmp,tmp,tmp),"yyyy-MM-dd HH");
                
    }
    /**
     * 时间格式转换 yyyy-MM-ddTHH:mm:ss,适应JOBS.xml要求
     * @return
     * @param  时间
     */
    public static String getJobsDate(Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = df.format(date);
        return  dateStr.replaceFirst("\\s", "T");
      
    }
    
    /**
     *
     * <pre>
     * 将时间字符串+8小时后，按指定格式返回.
     * </pre>
     * @param dateStr     指定时间字符串
     * @param dateFormat  指定时间格式
     * @return
     */
    public static String griCalendarToString(String dateStr,String dateFormat){
    	try{
	    	DateFormat df = new SimpleDateFormat(dateFormat);
	    	Calendar cc = new GregorianCalendar();
	    	cc.setTime(df.parse(dateStr));
	    	cc.add(Calendar.HOUR ,8);
	    	return df.format(cc.getTime());
    	}catch (Exception ex) {
			ex.printStackTrace();
		}
    	return null;
    }
    
	/**
	 * 获取当前时间
	 * @param dateFormat
	 * @return
	 */ 
    public static java.util.Date getCurSysDate() {
		java.util.Date curDate = new Date();
		try {
			SimpleDateFormat sdf = TimeUtils3.getDateFormat(5);
			String strCurDate = TimeUtils3.getDateFormat(5).format(new java.util.Date());
			curDate = sdf.parse(strCurDate);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return curDate;
	}
    
    public static String dateToStr(Date date,String formate){
    	DateFormat df = new SimpleDateFormat(formate);
    	String dateStr = df.format(date);
    	return dateStr;
    }
    
    public static String calendarToStr(Calendar calendar,String formate){
    	DateFormat df = new SimpleDateFormat(formate);
    	String dateStr = df.format(calendar.getTime());
    	return dateStr;
    }
    public static Calendar strToCalendar(String dateStr,String formate){
    	Calendar c = Calendar.getInstance() ;
    	c.setTime(strToDate(dateStr, formate));
    	return c;
    }
    /**
     * 同时可用于验证传入的日期字符串是否正常值并返回出错位置：例如 2015-01-32
     * @param strDate
     * @param format
     * @return
     */
    public static Date strToDate(String strDate,String format) {
        Date parsedValue = null;
        DateFormat df = new SimpleDateFormat(format);
      //是否允许将日期转换为宽松，是的话只验证格式，不验证具体值,否：验证格式同时验证值例如：2016-02-30
        df.setLenient(false);
        try {
            // Parse the Date ParsePosition类用于定位出错误的字符串中出错字符的索引位置
           ParsePosition pos = new ParsePosition(0);
           parsedValue = df.parse(strDate, pos);
           if (pos.getErrorIndex() > -1) {
               throw new ParseException("Error parsing date '" + strDate +
                       "' at position="+ pos.getErrorIndex(),pos.getErrorIndex());
           }
           if (pos.getIndex() < strDate.length()) {
               throw new ParseException("Date '" + strDate +
                       "' contains unparsed characters from position=" + pos.getIndex(),pos.getIndex());
           }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return parsedValue;
    }
    
	/**
	 * <pre>
	 *   方法描述：当前时间timestamp
	 * </pre>
	 * @return
	 */
	public static Timestamp getCurrentTimestamp(){
		return new Timestamp(new Date().getTime());
	}
	
	/**
	 * <pre>
	 *   方法描述：timestamp转字符串
	 * </pre>
	 * @return
	 */
	public static String getCurrentTimestampStr(Timestamp date,String format){
		String dateStr = "";
		if(date!=null){
			try {
				SimpleDateFormat df = new SimpleDateFormat(format);
				dateStr = df.format(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dateStr;
	}

	/**
	 * <pre>
	 *  方法描述:根据传入条件获取时间date
	 * </pre>
	 * @author ZhangJianxiang
	 * Date 2014-4-7 下午12:57:44
	 * @param date
	 * @param fileds
	 * @param interval
	 * @return
	 */
	public static Date getDate(Date date,int fileds,int  interval){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(fileds, interval);
		return calendar.getTime();
	}
	
	/**
	 * <pre>
	 *  方法描述:根据传入条件获取时间timestamp
	 * </pre>
	 * @param fileds
	 * @param interval
	 * @return
	 */
	public static Timestamp getDate(Timestamp timestamp, int fileds,int  interval) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		cal.add(fileds, interval);
		return new Timestamp(cal.getTimeInMillis());
	}
	
	/**
	 * <pre>
	 *  方法描述:根据传入条件获取时间timestamp
	 * </pre>
	 * @author ysw
	 * Date 2014-4-22 下午16:10:56
	 * @param time
	 * @return
	 * @throws ParseException 
	 */
	public static Timestamp stringFormatTimestamp(String time) throws ParseException {
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 format.setLenient(false);
		  if(time!=null && !"".equals(time)){
			  Timestamp ts = new Timestamp(format.parse(time+" 00:00:00").getTime());
			  return ts;
		  }else{
			  return null;
		  }
	}
	
}
