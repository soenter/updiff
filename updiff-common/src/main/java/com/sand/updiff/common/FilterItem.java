package com.sand.updiff.common;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/5 20:22
 * @since 1.0.0
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
