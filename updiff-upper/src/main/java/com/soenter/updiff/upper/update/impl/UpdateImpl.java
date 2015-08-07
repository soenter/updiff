/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/7 16:33
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/7        Initailized
 */
package com.soenter.updiff.upper.update.impl;

import com.soenter.updiff.upper.scan.ScanedFile;
import com.soenter.updiff.upper.update.Update;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 *
 * @ClassName ：com.soenter.updiff.upper.update.impl.UpdateImpl
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/7 16:33
 * @version 1.0.0
 *
 */
public class UpdateImpl implements Update{

	protected ScanedFile scanedFile;

	protected File backupFile;

	public UpdateImpl (ScanedFile scanedFile, String backupPath) throws IOException{
		this.scanedFile = scanedFile;
		String backupFilePath = backupPath + "/" + scanedFile.getRelativePath();
		this.backupFile = new File(backupFilePath);

		if(backupFile.exists()){
			throw new IOException("备份文件已经存在：" + backupFilePath);
		}
	}

	public void backup () throws IOException {
		if(!scanedFile.isDir() && !scanedFile.isAddFile()){
			FileUtils.copyFile(scanedFile.getOldFile(), backupFile);
		}
	}

	public void recovery () throws IOException {
		if(!scanedFile.isDir() && !scanedFile.isAddFile()) {
			FileUtils.copyFile(backupFile, scanedFile.getOldFile());
		}
	}

	public void execute () throws IOException {
		if(scanedFile.isDir()){
			if(scanedFile.getNewFile().mkdirs()){
				throw new IOException("创建文件夹失败：" + scanedFile.getNewFile().getAbsolutePath());
			}
		} else {
			if(scanedFile.isModifyFile()){
				if(scanedFile.getOldFile().delete()){
					throw new IOException("删除旧文件失败：" + scanedFile.getOldFile().getAbsolutePath());
				}
			}
			FileUtils.copyFile(scanedFile.getNewFile(), scanedFile.getOldFile());
		}
	}
}
