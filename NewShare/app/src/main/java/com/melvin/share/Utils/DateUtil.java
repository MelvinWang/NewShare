package com.melvin.share.Utils;


import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created Time: 2016/7/17.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：日期工具类
 */

public class DateUtil
{
	static SimpleDateFormat sdfShort = new SimpleDateFormat("yyyyMMdd");
	static SimpleDateFormat sdfLong = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat sdfLongCn = new SimpleDateFormat("yyyy年MM月dd日");
	static SimpleDateFormat sdfShortU = new SimpleDateFormat("MMM dd",Locale.ENGLISH);
	static SimpleDateFormat sdfLongU = new SimpleDateFormat("MMM dd,yyyy",Locale.ENGLISH);
	static SimpleDateFormat sdfLongTime = new SimpleDateFormat("yyyyMMddHHmmss");
	static SimpleDateFormat sdfLongTimePlus = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat sdfShortLongTimePlusCn = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	static SimpleDateFormat sdfLongTimePlusMill = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
	static SimpleDateFormat sdfMd = new SimpleDateFormat("MM月dd日");

	public DateUtil()
	{
	}

	/**
	 * @author Pablo
	 * Descrption:get Date format Example：2008-05-15
	 * @return String
	 * @throws Exception
	 */
	public static String getDateLong(Date date)
	{
		String nowDate = "";
		try
		{	
			if(date != null)
				nowDate = sdfLong.format(date);
			return nowDate;
		}
		catch (Exception e)
		{
			System.out.println("Error at getDate:" + e.getMessage());
			return "";
		}
	}
	/**
	 * @author Pablo
	 * Descrption:get Date format Example：2008年-05月-15日
	 * @return String
	 * @throws Exception
	 */
	public static String getDateLongCn(Date date)
	{
		String nowDate = "";
		try
		{	
			if(date != null)
				nowDate = sdfLongCn.format(date);
			return nowDate;
		}
		catch (Exception e)
		{
			System.out.println("Error at getDate:" + e.getMessage());
			return "";
		}
	}
	
	
	/**
	 * @author vowo
	 * Descrption:get Date format Example：05月-15日
	 * @return String
	 * @throws Exception
	 */
	public static String getDateMD(Date date)
	{
		String nowDate = "";
		try
		{	
			if(date != null)
				nowDate = sdfMd.format(date);
			return nowDate;
		}
		catch (Exception e)
		{
			System.out.println("Error at getDate:" + e.getMessage());
			return "";
		}
	}
	
	/**
	 * @author Pablo
	 * Descrption:get Date format Example：2008年-05月-15日 11:05
	 * @return String
	 * @throws Exception
	 */
	public static String getDateShortLongTimeCn(Date date)
	{
		String nowDate = "";
		try
		{	
			if(date != null)
				nowDate = sdfShortLongTimePlusCn.format(date);
			return nowDate;
		}
		catch (Exception e)
		{
			System.out.println("Error at getDate:" + e.getMessage());
			return "";
		}
	}
	
	/**
	 * @author Pablo
	 * Descrption:get Date format Example：Aug 28, 2007
	 * @return String
	 * @throws Exception
	 */
	public static String getDateUS(Date date)
	{
		String nowDate = "";
		try
		{	
			if(date != null)
				nowDate = sdfLongU.format(date);
			return nowDate;
		}
		catch (Exception e)
		{
			System.out.println("Error at getDate:" + e.getMessage());
			return "";
		}
	}
	
	/**
	 * @author Pablo
	 * Descrption:get Date format Example：Aug 28, 2007
	 * @return String
	 * @throws Exception
	 */
	public static String getDateUSShort(Date date)
	{
		String nowDate = "";
		try
		{	
			if(date != null)
				nowDate = sdfShortU.format(date);
			return nowDate;
		}
		catch (Exception e)
		{
			System.out.println("Error at getDate:" + e.getMessage());
			return "";
		}
	}
	
	/**
	 * 简单转换日期类型到字符串类型，本地信息设为UK
	 * 
	 * @param 	date
	 * @param 	format
	 * @return String
	 */
	public static String getFomartDate(Date date, String format)
	{
		try
		{
			return new SimpleDateFormat(format, Locale.UK).format(date);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return (date == null) ? new Date().toString() : date.toString();
		}
	}

	/**
	 * Descrption:取得当前日期时间,格式为:YYYYMMDDHHMISS
	 * @return String
	 * @throws Exception
	 */
	public static String getNowLongTime() throws Exception
	{
		String nowTime = "";
		try
		{
			java.sql.Date date = null;
			date = new java.sql.Date(new Date().getTime());
			nowTime = sdfLongTime.format(date);
			return nowTime;
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * Descrption:取得当前日期,格式为:YYYYMMDD
	 * @return String
	 * @throws Exception
	 */
	public static String getNowShortDate() throws Exception
	{
		String nowDate = "";
		try
		{
			java.sql.Date date = null;
			date = new java.sql.Date(new Date().getTime());
			nowDate = sdfShort.format(date);
			return nowDate;
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * Descrption:取得当前日期,格式为:YYYY-MM-DD
	 * @return String
	 * @throws Exception
	 */
	public static String getNowFormateDate() throws Exception
	{
		String nowDate = "";
		try
		{
			java.sql.Date date = null;
			date = new java.sql.Date(new Date().getTime());
			nowDate = sdfLong.format(date);
			return nowDate;
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * Descrption:取得当前日期,格式为:yyyy-MM-dd HH:mm:ss
	 * @return String
	 * @throws Exception
	 */
	public static String getNowPlusTime()
	{
		String nowDate = "";
		try
		{
			java.sql.Date date = null;
			date = new java.sql.Date(new Date().getTime());
			nowDate = sdfLongTimePlus.format(date);
			return nowDate;
		}
		catch (Exception e)
		{
			return "";
		}
	}

	/**
	 * Descrption:取得当前日期,格式为:yyyy-MM-dd HH:mm:ss
	 * @return String
	 * @throws Exception
	 */
	public static String getPlusTime(Date date) throws Exception
	{
		if(date == null ) return null;
		try
		{
			String nowDate = sdfLongTimePlus.format(date);
			return nowDate;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	/**
	 * Descrption:取得当前日期,格式为:yyyy-MM-dd HH:mm:ss
	 * @return String
	 * @throws Exception
	 */
	public static String getPlusTime2(Date date)
	{
		
		if(date == null ) return null;
		try
		{
			String nowDate = sdfLongTimePlus.format(date);
			return nowDate;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Descrption:取得当前日期到毫秒极,格式为:yyyyMMddHHmmssSSSS
	 * @return String
	 * @throws Exception
	 */
	public static String getNowPlusTimeMill()
	{
		String nowDate = "";
		try
		{
			java.sql.Date date = null;
			date = new java.sql.Date(new Date().getTime());
			nowDate = sdfLongTimePlusMill.format(date);
			return nowDate;
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 得到当前年份值:1900
	 * @return String
	 * @throws Exception
	 */
	public static String getNowYear() throws Exception
	{
		String nowYear = "";
		try
		{
			String strTemp = getNowLongTime();
			nowYear = strTemp.substring(0, 4);
			return nowYear;
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 得到当前月份值:12
	 * @return String
	 * @throws Exception
	 */
	public static String getNowMonth() throws Exception
	{
		String nowMonth = "";
		try
		{
			String strTemp = getNowLongTime();
			nowMonth = strTemp.substring(4, 6);
			return nowMonth;
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 得到当前日期值:30
	 * @return String
	 * @throws Exception
	 */
	public static String getNowDay() throws Exception
	{
		String nowDay = "";
		try
		{
			String strTemp = getNowLongTime();
			nowDay = strTemp.substring(6, 8);
			return nowDay;
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 得到当前小时值:23
	 * @return String
	 * @throws Exception
	 */
	public static String getNowHour() throws Exception
	{
		String nowHour = "";
		try
		{
			String strTemp = getNowPlusTimeMill();
			nowHour = strTemp.substring(8, 10);
			return nowHour;
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 根据秒数返回时分秒
	 * @param _second 秒数
	 * @return String
	 * @throws Exception
	 */
	public static String getTimeBySecond(String _second) throws Exception
	{
		String returnTime = "";
		long longHour = 0;
		long longMinu = 0;
		long longSec = 0;
		try
		{
			longSec = Long.parseLong(_second);
			if (longSec == 0)
			{
				returnTime = "0时0分0秒";
				return returnTime;
			}
			longHour = longSec / 3600; //取得小时数
			longSec = longSec % 3600; //取得余下的秒
			longMinu = longSec / 60; //取得分数
			longSec = longSec % 60; //取得余下的秒
			returnTime = longHour + "时" + longMinu + "分" + longSec + "秒";
			return returnTime;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	/**
	 * pablo
	 * 根据毫秒数返回时分秒毫秒
	 * @param
	 * @return String
	 * @throws Exception
	 */
	public static String getTimeBySecond(long ms_second) throws Exception
	{
		String returnTime = "";
		long longHour = 0;
		long longMinu = 0;
		long longSec = 0;
		long longMs = ms_second;
		try
		{
			if (longMs == 0)
			{
				returnTime = "0时0分0秒0毫秒";
				return returnTime;
			}
			longHour = longMs / 3600000; //取得小时数
			longMs = longMs % 3600000; //取得余下的毫秒
			longMinu = longMs / 60000; //取得分数
			longMs = longMs % 60000; //取得余下的毫秒
			longSec = longMs / 1000; //取得余下的秒
			longMs = longMs % 1000; //取得余下的毫秒
			returnTime = longHour + "时" + longMinu + "分" + longSec + "秒" + longMs + "毫秒";
			return returnTime;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	/**
	 * 得到日期中的年份
	 *
	 * @param date 日期
	 * @return yyyy格式的年份
	 */
	public static int convertDateToYear(Date date)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy", new DateFormatSymbols());
		return Integer.parseInt(df.format(date));
	}

	/**
	 * 得到日期中年月组成的字符串
	 *
	 * @param d 日期
	 * @return yyyyMM格式的年月字符串
	 */
	public static String convertDateToYearMonth(Date d)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM", new DateFormatSymbols());
		return df.format(d);
	}

	/**
	 * 得到日期中年月日组成的字符串
	 *
	 * @param d 日期
	 * @return yyyyMMdd格式的年月日字符串
	 */
	public static String convertDateToYearMonthDay(Date d)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", new DateFormatSymbols());
		return df.format(d);
	}
	/**
	 * 得到日期中的月份
	 *
	 * @param
	 * @return yyyy格式的年份
	 */
	public static String convertDateToMonth(Date d)
	{
		SimpleDateFormat df = new SimpleDateFormat("MM", new DateFormatSymbols());
		return df.format(d);
	}
	/**
	 * 得到日期中的日
	 *
	 * @param
	 * @return yyyy格式的年份
	 */
	public static String convertDateToDay(Date d)
	{
		SimpleDateFormat df = new SimpleDateFormat("dd", new DateFormatSymbols());
		return df.format(d);
	}
	
	
	
	
	/**
	 * 得到日期中的小时
	 *
	 * @param
	 * @return HH格式的小时
	 */
	public static String convertDateToHour(Date d)
	{
		SimpleDateFormat df = new SimpleDateFormat("HH", new DateFormatSymbols());
		return df.format(d);
	}
	
	/**
	 * 得到日期中的分钟
	 *
	 * @param
	 * @return mm格式的分钟
	 */
	public static String convertDateToMinute(Date d)
	{
		SimpleDateFormat df = new SimpleDateFormat("mm", new DateFormatSymbols());
		return df.format(d);
	}
	/**
	 * 获取当前日期为日期型
	 *
	 * @return 当前日期，java.util.Date类型
	 */
	public static Date getCurrentDate()
	{
		Calendar cal = Calendar.getInstance();

		//String currentDate = null;
		Date d = cal.getTime();

		return d;
	}

	/**
	 * 获取当前年月的字符串
	 *
	 * @return 当前年月，yyyyMM格式
	 */
	public static String getCurrentYearMonth()
	{
		Calendar cal = Calendar.getInstance();
		String currentYear = (new Integer(cal.get(Calendar.YEAR))).toString();
		String currentMonth = null;
		if (cal.get(Calendar.MONTH) < 9)
			currentMonth = "0" + (new Integer(cal.get(Calendar.MONTH) + 1)).toString();
		else
			currentMonth = (new Integer(cal.get(Calendar.MONTH) + 1)).toString();
		return (currentYear + currentMonth);
	}

	/**
	 * 获取当前年为整型
	 *
	 * @return 获取当前日期中的年，int型
	 */
	public static int getCurrentYear()
	{
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		return currentYear;
	}



	/**
	 * 将yyyyMM各式转换成yyyy年MM月格式
	 * @param month 月
	 * @return 返回年月型格式的日期
	 */
	public static String getYearMonthByMonth(String month)
	{
		if (null == month)
			return null;
		String ym = month.trim();
		if (6 != ym.length())
			return ym;
		String year = ym.substring(0, 4);
		//String month1 = ym.substring(4);
		return new StringBuffer(year).append("年").append(month).append("月").toString();
	}


	/**
	 * 得到当前日期，格式yyyy-MM-dd。
	 * @return String 格式化的日期字符串
	 */
	public static String getToday()
	{
		Date cDate = new Date();
		SimpleDateFormat cSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return cSimpleDateFormat.format(cDate);
	}

	/**
	 * 得到当前日期，格式yyyy-MM-dd。
	 * @return String 格式化的日期字符串
	 */
	public static String getYesterday()
	{
		Date cDate = new Date();
		cDate.setTime(cDate.getTime() - 24 * 3600 * 1000);
		SimpleDateFormat cSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return cSimpleDateFormat.format(cDate);
	}

	/**
	 * 得到当前日期，格式yyyy-MM-dd。
	 * @return String 格式化的日期字符串
	 */
	public static String getTomorrow()
	{
		Date cDate = new Date();
		cDate.setTime(cDate.getTime() + 24 * 3600 * 1000);
		SimpleDateFormat cSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return cSimpleDateFormat.format(cDate);
	}

	/**
	 * 得到当前日期时间,格式为yyyy-MM-dd hh:mm:ss.
	 * @return String
	 */
	public static String getCurrDateTime()
	{
		java.sql.Timestamp date = new java.sql.Timestamp(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return formatter.format(date);
	}
	
	/**
     * 得到当前日期时间,格式为yyyy-MM-dd hh:mm:ss.
     * @return String
	 * @throws ParseException 
     */
    public static Date getCurrentDateTime() throws ParseException
    {
        java.sql.Timestamp date = new java.sql.Timestamp(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return formatter.parse(formatter.format(date));
    }

}
