/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/7 14:38
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/7        Initailized
 */
package com.soenter.updiff.upper.scan.impl;

import com.soenter.updiff.common.DiffWriter;
import com.soenter.updiff.upper.scan.ScanedFile;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @ClassName ：com.soenter.updiff.upper.scan.impl.ScanedFileImpl
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/7 14:38
 * @version 1.0.0
 *
 */
public class ScanedFileImpl implements ScanedFile{

	private File oldFile;

	private File newFile;

	private File diffFile;

	public ScanedFileImpl (File oldFile, File newFile) {

		if(oldFile.exists() && newFile.exists()){
			if(oldFile.isFile() != oldFile.isFile()){
				throw new RuntimeException("文件类型必须一样");
			}
		}

		this.oldFile = oldFile;
		this.newFile = newFile;

		String newFilePath = newFile.getAbsolutePath();
		int newFileDotIndex = newFilePath.lastIndexOf(".");

		if(newFileDotIndex != -1){
			this.diffFile = new File(newFilePath.substring(0, newFileDotIndex) + DiffWriter.fileTypeName);
		}
	}

	public boolean isDir () {
		return newFile.isDirectory();
	}

	public boolean isJar () {
		return newFile.getName().endsWith(".jar");
	}

	public boolean hasDiff () {
		return diffFile == null?false:diffFile.exists();
	}

	public boolean isUpVersionFile () {
		return false;//FIXME 文件版本增高或降低功能
	}

	public boolean isAddFile () {
		return !oldFile.exists();
	}

	public boolean isModifyFile ()  {
		if(!newFile.exists() || !oldFile.exists() || newFile.isDirectory() || oldFile.isDirectory()){
			return false;
		}

		String newFileSha1 = null;
		String oldFileSha1 = null;
		try {
			newFileSha1 = DigestUtils.sha1Hex(new FileInputStream(newFile));
			oldFileSha1 = DigestUtils.sha1Hex(new FileInputStream(oldFile));
		} catch (IOException e) {
			throw new RuntimeException("newFile 或 oldFile 文件不存在 ");
		}
		return !newFileSha1.equals(oldFileSha1);
	}

	public File getOldFile () {
		return oldFile;
	}

	public File getNewFile () {
		return newFile;
	}

	public File getDiffFile () {
		return diffFile;
	}

	@Override
	public String toString () {

		StringBuilder sb = new StringBuilder("[");

		sb.append("isDir: ").append(isDir()).append(", ");
		sb.append("isJar: ").append(isJar()).append(", ");
		sb.append("hasDiff: ").append(hasDiff()).append(", ");
		sb.append("isAddFile: ").append(isAddFile()).append(", ");
		sb.append("isModifyFile: ").append(isModifyFile()).append(", ");
		sb.append("oldFile: ").append(oldFile).append(", ");
		sb.append("newFile: ").append(newFile).append(", ");
		sb.append("diffFile: ").append(diffFile).append("");

		return sb.append("]").toString();
	}
}
