package com.sand.updiff.upper.scan.impl;

import com.sand.updiff.common.FileType;
import com.sand.updiff.upper.scan.Scanned;
import com.sand.updiff.upper.scan.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 扫描包含jar文件的文件夹
 * @author : sun.mt
 * @create : 2015/9/17 11:12
 * @since : 1.0.5
 */
public class ConstaintJarDirScanner implements Scanner<Scanned>{

	private static final Logger LOGGER = LoggerFactory.getLogger(ConstaintJarDirScanner.class);

	private File oldDir;

	private File newDir;

	private List<Scanned> scanFiles;

	public ConstaintJarDirScanner (File oldDir, File newDir) {
		this.oldDir = oldDir;
		this.newDir = newDir;
	}

	public Iterator<Scanned> iterator () {
		if(scanFiles == null){
			scanFiles = new LinkedList<Scanned>();
			walkFiles(scanFiles, oldDir);
		}

		return scanFiles.iterator();
	}


	private void walkFiles(List<Scanned> scanFiles, File file){
		List<File> oldDirJarFiles = null;
		List<File> newDirJarFiles = null;
		File[] files = file.listFiles();
		for (File f: files){
			if(f.isDirectory()){
				walkFiles(scanFiles, f);
			} else if(f.getName().endsWith(FileType.JAR.getType())){
				if(oldDirJarFiles == null){
					oldDirJarFiles = new ArrayList<File>();
					newDirJarFiles = new ArrayList<File>();
				}
				oldDirJarFiles.add(f);
				newDirJarFiles.add(getNewFile(f));
				LOGGER.debug("扫描到jar文件： {}", f);
			}
		}
		if(oldDirJarFiles != null){
			Scanned scanned = new ConstaintJarDirScanned(file, getNewFile(file), getRelativePath(file), oldDirJarFiles, newDirJarFiles);
			LOGGER.debug("扫描到包含jar文件的文件夹： {}", scanned);
			scanFiles.add(scanned);
		}
	}

	private File getNewFile(File file){
		String oldFilePath = newDir.getAbsolutePath() + getRelativePath(file);
		return new File(oldFilePath);
	}

	private String getRelativePath(File file){
		return file.getAbsolutePath().substring(oldDir.getAbsolutePath().length());
	}
}
