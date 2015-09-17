package com.sand.updiff.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/11 16:57
 * @since 1.0.0
 *
 */
public class DateUtils {


	public static String format( String pattern){
		return format(new Date(), pattern);
	}

	public static String format(Date date, String pattern){
		String dateString = null;
		try{
			DateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dateString;
	}
}
