package com.sand.updiff.common;

/**
 * @author : sun.mt
 * @since 1.0.0
 * @date : 2015/8/11 9:12
 */
public enum ChangeType {
	/** Add a new file to the project */
	ADD,

	/** Modify an existing file in the project (content and/or mode) */
	MODIFY,

	/** Delete an existing file from the project */
	DELETE,

	/** Rename an existing file to a new location */
	RENAME,

	/** Copy an existing file to a new location, keeping the original */
	COPY;

	public static ChangeType get(String type){

		if(ADD.name().equals(type)){
			return ADD;
		} else if(MODIFY.name().equals(type)){
			return MODIFY;
		} else if(DELETE.name().equals(type)){
			return DELETE;
		} else if(RENAME.name().equals(type)){
			return RENAME;
		} else if(COPY.name().equals(type)){
			return COPY;
		}
		return null;
	}
}
