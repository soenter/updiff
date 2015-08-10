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
import com.soenter.updiff.upper.scan.Scaned;
import org.apache.commons.codec.digest.DigestUtils;
import sun.applet.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @ClassName ：com.soenter.updiff.upper.scan.impl.ScanedImpl
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/7 14:38
 * @version 1.0.0
 *
 */
public class ScanedImpl implements Scaned {

	private File oldFile;

	private File newFile;

	private File diffFile;

	private String relativePath;

	private String newFileSha1;

	private String oldFileSha1;

	public ScanedImpl (File oldFile, File newFile, String relativePath) {
		init(oldFile, newFile, relativePath, false);
	}

	public ScanedImpl (File oldFile, File newFile, String relativePath, boolean isInnerDiffFile) {
		init(oldFile, newFile, relativePath, isInnerDiffFile);
	}

	private void init(File oldFile, File newFile, String relativePath, boolean isInnerDiffFile){
		if(oldFile.exists() && newFile.exists()){
			if(oldFile.isFile() != oldFile.isFile()){
				throw new RuntimeException("文件类型必须一样");
			}
		}

		this.oldFile = oldFile;
		this.newFile = newFile;

		String newFilePath = newFile.getAbsolutePath();
		if(isInnerDiffFile){
			this.diffFile = new File(newFilePath + "/" + newFile.getName() + DiffWriter.fileTypeName);
		} else {
			int newFileDotIndex = newFilePath.lastIndexOf(".");

			if(newFileDotIndex != -1){
				this.diffFile = new File(newFilePath.substring(0, newFileDotIndex) + DiffWriter.fileTypeName);
			} else {
				this.diffFile = new File(newFilePath + DiffWriter.fileTypeName);
			}
		}

		this.relativePath = relativePath;
	}

	public boolean isDir () {
		return newFile.isDirectory();
	}

	public boolean isJar () {
		return newFile.getName().endsWith(".jar");
	}

	public boolean isClass () {
		return newFile.getName().endsWith(".class");
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

		//如果是jar或class不能通过计算sha1值判断是否修改过
		if(isJar() || isClass()){
			return false;
		}

		try {
			if(newFileSha1 == null){
				newFileSha1 = DigestUtils.sha1Hex(new FileInputStream(newFile));
			}
			if(oldFileSha1 == null){
				oldFileSha1 = DigestUtils.sha1Hex(new FileInputStream(oldFile));
			}
		} catch (IOException e) {
			throw new RuntimeException("newFile 或 oldFile 文件不存在 ");
		}
		return !newFileSha1.equals(oldFileSha1);
	}

	public boolean isDeleteFile () {
		return new File(newFile.getParent(), newFile.getName() + ".delete").exists();
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

	public String getRelativePath () {
		return relativePath;
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
		sb.append("diffFile: ").append(diffFile);

		return sb.append("]").toString();
	}

}
