/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/17 10:54
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/17        Initailized
 */
package com.sand.updiff.upper.scan.impl;

import com.sand.updiff.common.ChangeType;
import com.sand.updiff.common.FileType;
import com.sand.updiff.common.utils.FilePathUtils;
import com.sand.updiff.common.utils.StringUtils;
import com.sand.updiff.upper.dom.RedologItem;
import com.sand.updiff.upper.scan.Scanned;

import java.io.File;

/**
 *
 * @ClassName ：com.sand.updiff.upper.scan.impl.RedologScanned
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/17 10:54
 * @version 1.0.0
 *
 */
public class RedologScanned implements Scanned{

	private RedologItem redologItem;

	private File oldFile;

	private File newFile;

	private File backupFile;

	private String relativePath;

	private boolean isDir;


	public RedologScanned (RedologItem redologItem) {
		this.redologItem = redologItem;

		if(!StringUtils.isBlank(redologItem.getToPath())){
			this.oldFile = new File(redologItem.getToPath());
		}

		if(!StringUtils.isBlank(redologItem.getFromPath())){
			this.newFile = new File(redologItem.getFromPath());
		}

		if(!StringUtils.isBlank(redologItem.getBackupPath())){
			this.backupFile = new File(redologItem.getBackupPath());
		}

		if(!StringUtils.isBlank(redologItem.getToPath()) && !StringUtils.isBlank(redologItem.getFromPath())){
			this.relativePath = FilePathUtils.getSameSubfixPath(redologItem.getToPath(), redologItem.getFromPath());
		}

		isDir = redologItem.isDir();
	}

	public boolean isDir () {
		return isDir;
	}

	public boolean isJar () {
		return this.oldFile.getName().endsWith(FileType.JAR.getType());
	}

	public boolean hasDiff () {
		return false;
	}

	public boolean isUpVersionFile () {
		return false;
	}

	public boolean isAddFile () {
		return ChangeType.ADD == redologItem.getChange();
	}

	public boolean isModifyFile () {
		return ChangeType.MODIFY == redologItem.getChange();
	}

	public boolean isDeleteFile () {
		return ChangeType.DELETE == redologItem.getChange();
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

	@Override
	public String toString () {

		StringBuilder sb = new StringBuilder("[");

		sb.append("isDir: ").append(isDir()).append(", ");
		sb.append("isJar: ").append(isJar()).append(", ");
		sb.append("hasDiff: ").append(hasDiff()).append(", ");
		sb.append("isAddFile: ").append(isAddFile()).append(", ");
		sb.append("isModifyFile: ").append(isModifyFile()).append(", ");
		sb.append("isDeleteFile: ").append(isDeleteFile()).append(", ");
		sb.append("oldFile: ").append(oldFile).append(", ");
		sb.append("newFile: ").append(newFile).append(", ");
		sb.append("diffFile: null").append(", ");
		sb.append("relativePath: ").append(relativePath).append(", ");

		return sb.append("]").toString();
	}

}
