package com.sand.updiff.upper.scan;

import java.io.File;

/**
 * @author : sun.mt
 * @date : 2015/8/7 13:30
 * @since 1.0.0
 */
public interface Scanned {

	boolean isDir();

	boolean isJar();

	boolean hasDiff();

	boolean isAddFile();

	boolean isModifyFile();

	boolean isDeleteFile();

	File getOldFile();

	File getNewFile();

	String getRelativePath();

}
