package com.sand.updiff.upper.scan.impl;

import com.sand.updiff.common.FileType;
import com.sand.updiff.common.utils.UpdiffFileUtils;
import com.sand.updiff.upper.scan.Scanned;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/7 14:38
 * @since 1.0.0
 *
 */
public class DefaultScanned implements Scanned {

	private File oldFile;

	private File newFile;

	private boolean hasDiff;

	private String relativePath;

	private String newFileSha1;

	private String oldFileSha1;

	private boolean isAddFile;

	private boolean isDeleteFile;

	private boolean isModifyFile;

	public DefaultScanned (File oldFile, File newFile, String relativePath) {
		init(oldFile, newFile, relativePath, true);
	}

	public DefaultScanned (File oldFile, File newFile, String relativePath, boolean isInnerDiffFile) {
		init(oldFile, newFile, relativePath, isInnerDiffFile);
	}

	private void init(File oldFile, File newFile, String relativePath, boolean isInnerDiffFile){
		if(oldFile.exists() && newFile.exists()){
			if(oldFile.isDirectory() != oldFile.isDirectory()){
				throw new RuntimeException("文件类型必须一样");
			}
		}

		this.oldFile = oldFile;
		this.newFile = newFile;

		if(isInnerDiffFile){
			if(isJar()){
				hasDiff = UpdiffFileUtils.hasDiffFile(newFile);
			}
		} else {
			int newFileDotIndex = newFile.getName().lastIndexOf(".");

			if(newFileDotIndex != -1){
				hasDiff = new File(newFile.getParent(), newFile.getName().substring(0, newFileDotIndex) + FileType.DIFF.getType()).exists();

			} else {
				hasDiff = new File(newFile.getParent(), newFile.getName() + FileType.DIFF.getType()).exists();
			}
		}

		this.relativePath = relativePath;

		this.isAddFile = !oldFile.exists();

		this.isModifyFile = _isModifyFile();

		this.isDeleteFile = new File(newFile.getParent(), newFile.getName() + FileType.DELETE.getType()).exists();
	}

	public boolean isDir () {
		return newFile.isDirectory();
	}

	public boolean isJar () {
		return newFile.getName().endsWith(FileType.JAR.getType());
	}

	public boolean isClass () {
		return newFile.getName().endsWith(FileType.CLASS.getType());
	}

	public boolean hasDiff () {
		return hasDiff;
	}

	public boolean isUpVersionFile () {
		return false;//FIXME 文件版本增高或降低功能
	}

	public boolean isAddFile () {
		return this.isAddFile;
	}

	public boolean isModifyFile ()  {
		return isModifyFile;
	}

	public boolean isDeleteFile () {
		return isDeleteFile;
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
		sb.append("newFile: ").append(newFile);

		return sb.append("]").toString();
	}

	private boolean _isModifyFile (){
		if(!newFile.exists() || !oldFile.exists() || newFile.isDirectory() || oldFile.isDirectory()){
			return false;
		}

		//如果是jar或class不能通过计算sha1值判断是否修改过
		if(isJar() || isClass()){
			return false;
		}
		InputStream newFileIs = null;
		InputStream oldFileIs = null;
		try {
			if(newFileSha1 == null){
				newFileIs = new FileInputStream(newFile);
				newFileSha1 = DigestUtils.sha1Hex(newFileIs);
			}
			if(oldFileSha1 == null){
				oldFileIs = new FileInputStream(oldFile);
				oldFileSha1 = DigestUtils.sha1Hex(oldFileIs);
			}
		} catch (IOException e) {
			throw new RuntimeException("newFile 或 oldFile 文件不存在 ", e);
		} finally {
			try {
				if(newFileIs != null){
					newFileIs.close();
				}
				if(oldFileIs != null){
					oldFileIs.close();
				}
			} catch (IOException e) {
				//忽略
			}
		}
		return !newFileSha1.equals(oldFileSha1);
	}
}
