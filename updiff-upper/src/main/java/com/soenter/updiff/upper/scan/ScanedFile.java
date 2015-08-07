package com.soenter.updiff.upper.scan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author : sun.mt@sand.com.cn
 * @version 1.0.0
 * @ClassName £ºcom.soenter.updiff.upper.scan.ScanedFileImpl
 * @Description :
 * @Date : 2015/8/7 13:30
 */
public interface ScanedFile {

	boolean isDir();

	boolean isJar();

	boolean hasDiff();

	boolean isUpVersionFile();

	boolean isAddFile();

	boolean isModifyFile();

	File getOldFile();

	File getNewFile();

	File getDiffFile();

}
