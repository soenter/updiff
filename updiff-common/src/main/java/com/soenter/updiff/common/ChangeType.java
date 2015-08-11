package com.soenter.updiff.common;

/**
 * @author : sun.mt@sand.com.cn
 * @version 1.0.0
 * @ClassName ：com.soenter.updiff.common.ChangeType
 * @Description :
 * @Date : 2015/8/11 9:12
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

}
