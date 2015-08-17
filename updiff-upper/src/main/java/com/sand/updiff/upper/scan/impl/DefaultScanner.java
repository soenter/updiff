/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/7 13:46
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/7        Initailized
 */
package com.sand.updiff.upper.scan.impl;

import com.sand.updiff.common.FileType;
import com.sand.updiff.common.utils.UpdiffFileUtils;
import com.sand.updiff.upper.scan.Scanned;
import com.sand.updiff.upper.scan.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @ClassName ：com.sand.updiff.upper.scan.impl.DefaultScanner
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/7 13:46
 * @version 1.0.0
 *
 */
public class DefaultScanner implements Scanner {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultScanner.class);

	private File oldDir;

	private File newDir;

	private List<Scanned> scanFiles;

	public DefaultScanner (File oldDir, File newDir) {

		if(!oldDir.isDirectory() || !newDir.isDirectory()){
			throw new RuntimeException("Scanner 参数必须是文件夹");
		}

		this.oldDir = oldDir;
		this.newDir = newDir;
	}

	public Iterator<Scanned> iterator () {

		if(scanFiles == null){
			scanFiles = new LinkedList<Scanned>();
			walkFiles(scanFiles, newDir);
		}

		return scanFiles.iterator();
	}

	private void walkFiles(List<Scanned> scanFiles, File file){

		File[] files = file.listFiles();
		for (File f: files){
			if(f.isDirectory()){
				File realFile = UpdiffFileUtils.castControlFile(f);

				Scanned scanned = new DefaultScanned(getOldFile(realFile), realFile, getRelativePath(realFile));
				LOGGER.debug("扫描到文件夹： {}", scanned);
				scanFiles.add(scanned);
				walkFiles(scanFiles, f);
			} else if(!f.getName().endsWith(FileType.DIFF.getType()) && !UpdiffFileUtils.isInnerClassFile(f)){
				File realFile = UpdiffFileUtils.castControlFile(f);
				Scanned scanned = new DefaultScanned(getOldFile(realFile), realFile, getRelativePath(realFile));
				LOGGER.debug("扫描到文件： {}", scanned);
				scanFiles.add(scanned);
			}
		}
	}

	private File getOldFile(File file){
		String oldFilePath = oldDir.getAbsolutePath() + getRelativePath(file);
		return new File(oldFilePath);
	}

	private String getRelativePath(File file){
		return file.getAbsolutePath().substring(newDir.getAbsolutePath().length());
	}
}
