/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/11 16:57
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/11        Initailized
 */
package com.soenter.updiff.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @ClassName ：com.soenter.updiff.common.DateUtils
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/11 16:57
 * @version 1.0.0
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
