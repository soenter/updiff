package com.sand.updiff.upper.scan.impl;

import com.sand.updiff.common.FileType;
import com.sand.updiff.upper.scan.Scanned;
import com.sand.updiff.upper.scan.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

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
		File[] jarFiles = getJarFiles(file);
		if(jarFiles != null && jarFiles.length > 0){
			List<File> newDirJarFiles = new ArrayList<File>(jarFiles.length);
			for (int i = 0; i < jarFiles.length; i++){
				newDirJarFiles.add(getNewFile(jarFiles[i]));
			}
			Scanned scanned = new ConstaintJarDirScanned(file, getNewFile(file), getRelativePath(file), Arrays.asList(jarFiles), newDirJarFiles);
			LOGGER.debug("扫描到包含jar文件的文件夹： {}", scanned);
			scanFiles.add(scanned);
		}
		File[] files = file.listFiles();
		for (File f: files){
			if(f.isDirectory()){
				walkFiles(scanFiles, f);
			}
		}
	}

	private File[] getJarFiles(File file){
		return file.listFiles(new FileFilter() {
			public boolean accept (File pathname) {
				return pathname.isFile() && pathname.getName().endsWith(FileType.JAR.getType());
			}
		});
	}

	private File getNewFile(File file){
		String oldFilePath = newDir.getAbsolutePath() + getRelativePath(file);
		return new File(oldFilePath);
	}

	private String getRelativePath(File file){
		return file.getAbsolutePath().substring(oldDir.getAbsolutePath().length());
	}
}
