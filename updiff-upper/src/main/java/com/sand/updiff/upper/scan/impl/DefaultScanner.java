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
import com.sand.updiff.upper.scan.Scaned;
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


	public DefaultScanner (File oldDir, File newDir) {

		if(!oldDir.isDirectory() || !newDir.isDirectory()){
			throw new RuntimeException("Scanner 参数必须是文件夹");
		}

		this.oldDir = oldDir;
		this.newDir = newDir;
	}

	public Iterator<Scaned> iterator () {

		List<Scaned> scanFiles = new LinkedList<Scaned>();

		walkFiles(scanFiles, newDir);

		return scanFiles.iterator();
	}

	private void walkFiles(List<Scaned> scanFiles, File file){

		File[] files = file.listFiles();
		for (File f: files){
			if(f.isDirectory()){
				File realFile = UpdiffFileUtils.castControlFile(f);

				Scaned scaned = new DefaultScaned(getOldFile(realFile), realFile, getRelativePath(realFile));
				LOGGER.debug("扫描到文件夹： {}", scaned);
				scanFiles.add(scaned);
				walkFiles(scanFiles, f);
			} else if(!f.getName().endsWith(FileType.DIFF.getType()) && !UpdiffFileUtils.isInnerClassFile(f)){
				File realFile = UpdiffFileUtils.castControlFile(f);
				Scaned scaned = new DefaultScaned(getOldFile(realFile), realFile, getRelativePath(realFile));
				LOGGER.debug("扫描到文件： {}", scaned);
				scanFiles.add(scaned);
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
