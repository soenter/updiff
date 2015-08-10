package com.soenter.updiff.upper.scan;

import java.io.File;

/**
 * @author : sun.mt@sand.com.cn
 * @version 1.0.0
 * @ClassName £ºcom.soenter.updiff.upper.scan.ScanedImpl
 * @Description :
 * @Date : 2015/8/7 13:30
 */
public interface Scaned {

	boolean isDir();

	boolean isJar();

	boolean hasDiff();

	boolean isUpVersionFile();

	boolean isAddFile();

	boolean isModifyFile();

	boolean isDeleteFile();

	File getOldFile();

	File getNewFile();

	File getDiffFile();

	String getRelativePath();

	public static enum ChangeType {
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
}
