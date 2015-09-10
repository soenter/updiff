package com.sand.updiff.upper.scan;

import java.io.File;

/**
 * @author : sun.mt@sand.com.cn
 * @version 1.0.0
 * @ClassName ：com.sand.updiff.upper.scan.DefaultScanned
 * @Description :
 * @Date : 2015/8/7 13:30
 */
public interface Scanned {

	boolean isDir();

	boolean isJar();

	boolean hasDiff();

	boolean isUpVersionFile();

	boolean isAddFile();

	boolean isModifyFile();

	boolean isDeleteFile();

	File getOldFile();

	File getNewFile();

	String getRelativePath();

}
