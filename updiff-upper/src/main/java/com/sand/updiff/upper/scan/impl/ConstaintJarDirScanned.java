package com.sand.updiff.upper.scan.impl;

import com.sand.updiff.upper.scan.Scanned;

import java.io.File;
import java.util.List;

/**
 * @author : sun.mt
 * @create : 2015/9/17 13:00
 * @since : 1.0.5
 */
public class ConstaintJarDirScanned implements Scanned{

	private File oldFile;
	private File newFile;
	private String relativePath;

	private List<File> oldDirJarFiles;
	private List<File> newDirJarFiles;

	public ConstaintJarDirScanned (File oldFile, File newFile, String relativePath, List<File> oldDirJarFiles, List<File> newDirJarFiles) {
		this.oldFile = oldFile;
		this.newFile = newFile;
		this.relativePath = relativePath;
		this.oldDirJarFiles = oldDirJarFiles;
		this.newDirJarFiles = newDirJarFiles;
	}

	public boolean isDir () {
		return true;
	}

	public boolean isJar () {
		return false;
	}

	public boolean hasDiff () {
		return false;
	}

	public boolean isAddFile () {
		return false;
	}

	public boolean isModifyFile () {
		return true;
	}

	public boolean isDeleteFile () {
		return false;
	}

	public File getOldFile () {
		return oldFile;
	}

	public File getNewFile () {
		return newFile;
	}

	public String getRelativePath () {
		return relativePath;
	}

	public List<File> getOldDirJarFiles () {
		return oldDirJarFiles;
	}

	public List<File> getNewDirJarFiles () {
		return newDirJarFiles;
	}
}
