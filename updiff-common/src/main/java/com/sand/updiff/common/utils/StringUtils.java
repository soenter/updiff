/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/18 9:35
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/18        Initailized
 */
package com.sand.updiff.common.utils;

/**
 *
 * @ClassName ：com.sand.updiff.common.utils.StringUtils
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/18 9:35
 * @version 1.0.0
 *
 */
public class StringUtils {


	public static boolean isBlank(String str){
		return str == null || "".equals(str.trim());
	}
}
