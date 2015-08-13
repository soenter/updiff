/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/5 20:22
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/5        Initailized
 */
package com.sand.updiff.common;

/**
 *
 * @ClassName ：com.sand.updiff.common.FilterItem
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/5 20:22
 * @version 1.0.0
 *
 */
public class FilterItem {

	private Type type;

	private String value;

	public FilterItem (Type type, String value) {
		this.type = type;
		this.value = value;
	}

	public Type getType () {
		return type;
	}

	public String getValue () {
		return value;
	}

	public static enum Type{
		INCLUDE,
		EXCLUDE;

	}
}
