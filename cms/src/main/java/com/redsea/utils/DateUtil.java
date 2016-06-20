package com.redsea.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;


/**
 * 格式化 时间
 * @author Rocky
 * @date 2013-5-15 下午1:52:03
 */
public class DateUtil {
	
	/**
	 * 格式化时间
	 */
	public static String format(Date date, String pattern) {
		if(date==null){
			return "";
		}
		DateFormat df = new SimpleDateFormat(pattern, Locale.CHINA);
		return df.format(date);
	}
	
	/**
	 * hour小时之前
	 */
	public static Date hourBefor(int hour){
		return DateUtils.addHours(new Date(), -hour);
	}
	
	/**
	 * 出错时，返回当前时间
	 * @param dateStr 
	 * @param pattern 如果为null，默认 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date parse(String dateStr,String pattern){
		if(pattern == null)
			pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			Date date = sdf.parse(dateStr);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}
}
