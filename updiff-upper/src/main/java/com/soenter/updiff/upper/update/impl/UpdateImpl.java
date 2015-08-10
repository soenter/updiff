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

import com.soenter.updiff.upper.scan.Scaned;
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

	protected Scaned scaned;

	protected File backupFile;

	protected String backupPath;

	public UpdateImpl (Scaned scaned, String backupPath) throws IOException{
		this.scaned = scaned;
		this.backupPath = backupPath + File.separator + scaned.getRelativePath();
		this.backupFile = new File(backupPath);

		if(backupFile.exists()){
			throw new IOException("备份文件已经存在：" + backupPath);
		}
	}

	public void backup () throws IOException {
		if(scaned.isModifyFile() || scaned.isDeleteFile()){
			FileUtils.copyFile(scaned.getOldFile(), backupFile);
		}
	}

	public void recovery () throws IOException {
		if(scaned.isDir()){
			if(scaned.isAddFile()){
				if(scaned.getOldFile().exists() && !scaned.getOldFile().delete()){
					throw new IOException("[恢复]-删除创建文件夹失败：" + scaned.getOldFile().getAbsolutePath());
				}
			}
		} else {
			if(scaned.isAddFile()){
				if(scaned.getOldFile().exists() && !scaned.getOldFile().delete()){
					throw new IOException("[恢复]-删除旧文件失败：" + scaned.getOldFile().getAbsolutePath());
				}
			} else if(scaned.isModifyFile()){
				if(scaned.getOldFile().exists() && !scaned.getOldFile().delete()){
					throw new IOException("[恢复]-删除旧文件失败：" + scaned.getOldFile().getAbsolutePath());
				}
				FileUtils.copyFile(backupFile, scaned.getOldFile());
			}
		}
	}

	public void execute () throws IOException {
		if(scaned.isDir()){
			if(scaned.isAddFile()){
				if(scaned.getOldFile().exists() && !scaned.getOldFile().mkdirs()){
					throw new IOException("创建文件夹失败：" + scaned.getOldFile().getAbsolutePath());
				}
			}
		} else {
			if(scaned.isAddFile()){
				if(!scaned.getOldFile().getParentFile().mkdirs()){
					throw new IOException("创建父类文件夹失败：" + scaned.getOldFile().getParentFile().getAbsolutePath());
				}
			} else if(scaned.isModifyFile()){
				if(!scaned.getOldFile().delete()){
					throw new IOException("删除旧文件失败：" + scaned.getOldFile().getAbsolutePath());
				}
			}
			FileUtils.copyFile(scaned.getNewFile(), scaned.getOldFile());
		}
	}
}
