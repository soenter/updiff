package com.soenter.updiff.upper.scan;

import java.io.File;

/**
 * @author : sun.mt@sand.com.cn
 * @version 1.0.0
 * @ClassName ：com.soenter.updiff.upper.scan.ScanedImpl
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

}
