package com.sand.updiff.common.utils;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/18 9:35
 * @since 1.0.0
 *
 */
public class StringUtils {


	public static boolean isBlank(String str){
		return str == null || "".equals(str.trim());
	}
}
