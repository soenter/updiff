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
package com.sand.updiff.upper.update.impl;

import com.sand.updiff.common.ChangeType;
import com.sand.updiff.common.FileType;
import com.sand.updiff.common.utils.UpdiffFileUtils;
import com.sand.updiff.upper.dom.BackupItem;
import com.sand.updiff.upper.dom.BackupListWriter;
import com.sand.updiff.upper.dom.RedologItem;
import com.sand.updiff.upper.dom.RedologWriter;
import com.sand.updiff.upper.scan.Scaned;
import com.sand.updiff.upper.update.Update;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

/**
 *
 * @ClassName ：com.sand.updiff.upper.update.impl.DefaultUpdate
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/7 16:33
 * @version 1.0.0
 *
 */
public class DefaultUpdate implements Update {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUpdate.class);

	protected Scaned scaned;

	protected File backupFile;

	protected String backupPath;

	protected String backupDir;

	protected RedologWriter redologWriter;

	public DefaultUpdate (Scaned scaned, String backupDir) throws IOException{
		this.scaned = scaned;
		this.backupDir = backupDir;
		this.backupPath = backupDir + File.separator + scaned.getRelativePath();
		this.backupFile = new File(this.backupPath);

		if(backupFile.exists()){
			throw new IOException("备份文件已经存在：" + this.backupPath);
		}

		redologWriter = new RedologWriter(backupDir);
	}

	public void backup () throws IOException {
		//不备份文件夹
		if(scaned.isModifyFile() || scaned.isDeleteFile()){
			FileUtils.copyFile(scaned.getOldFile(), backupFile);
			LOGGER.info("[备份]-备份文件:[{}] ==> [{}]", scaned.getOldFile(), backupFile);

			new BackupListWriter(backupDir).writeItem(new BackupItem(scaned.getOldFile().getPath(), backupPath));
		}
	}

	public void execute () throws IOException {
		if(scaned.isDir()){
			if(scaned.isAddFile()){
				LOGGER.info("[执行]-创建文件夹:[{}]", scaned.getOldFile());
				if(scaned.getOldFile().exists()){
					throw new IOException("[执行]-要添加的文件夹已经存在：" + scaned.getOldFile().getAbsolutePath());
				}
				Stack<File> mkdirs = UpdiffFileUtils.mkdirs(scaned.getOldFile());
				if(mkdirs == null){
					throw new IOException("[执行]-创建添加的文件夹失败：" + scaned.getOldFile().getAbsolutePath());
				}
				while (!mkdirs.isEmpty()){
					File file = mkdirs.pop();
					redologWriter.addItem(new RedologItem(true, ChangeType.ADD, null, file, null));
				}
				redologWriter.write();
			} else if(scaned.isModifyFile()){
				//文件夹不支持修改
				LOGGER.warn("[执行]-文件夹不支持修改:[{}]", scaned.getOldFile());
			} else if(scaned.isDeleteFile()){
				if(scaned.getOldFile().exists()){
					LOGGER.warn("[执行]-删除旧文件夹:[{}]", scaned.getOldFile());
					if(!scaned.getOldFile().delete()){
						throw new IOException("[执行]-删除旧文件夹失败：" + scaned.getOldFile().getAbsolutePath());
					}
					redologWriter.writeItem(new RedologItem(true, ChangeType.DELETE, null, scaned.getOldFile(), null));
				} else {
					LOGGER.warn("[执行]-旧文件夹不存在无法删除:[{}]", scaned.getOldFile());
				}
			}

		} else {
			if(scaned.isAddFile()){
				LOGGER.info("[执行]-添加文件:[{}] ==> [{}]", scaned.getNewFile(), scaned.getOldFile());
				if(!scaned.getOldFile().getParentFile().exists()){
					Stack<File> mkdirs = UpdiffFileUtils.mkdirs(scaned.getOldFile().getParentFile());
					if(mkdirs == null){
						throw new IOException("[执行]-创建父类文件夹失败：" + scaned.getOldFile().getParentFile().getAbsolutePath());
					}
					while(!mkdirs.isEmpty()){
						File file = mkdirs.pop();
						redologWriter.addItem(new RedologItem(true, ChangeType.ADD, null, file, null));
					}
				}
				FileUtils.copyFile(scaned.getNewFile(), scaned.getOldFile());
				redologWriter.writeItem(new RedologItem(false, ChangeType.ADD, scaned.getNewFile(), scaned.getOldFile(), backupFile));
			} else if(scaned.isModifyFile()){
				LOGGER.info("[执行]-修改文件:[{}] ==> [{}]", scaned.getNewFile(), scaned.getOldFile());
				if(scaned.getOldFile().exists() && !scaned.getOldFile().delete()){
					throw new IOException("[执行]-删除旧文件失败：" + scaned.getOldFile().getAbsolutePath());
				}
				FileUtils.copyFile(scaned.getNewFile(), scaned.getOldFile());
				redologWriter.writeItem(new RedologItem(false, ChangeType.MODIFY, scaned.getNewFile(), scaned.getOldFile(), backupFile));
			} else if(scaned.isDeleteFile()){
				if(scaned.getOldFile().exists()){
					LOGGER.info("[执行]-删除文件:[{}]", scaned.getOldFile());
					if(!scaned.getOldFile().delete()) {
						throw new IOException("[执行]-删除旧文件失败：" + scaned.getOldFile().getAbsolutePath());
					}
					redologWriter.writeItem(new RedologItem(false, ChangeType.DELETE, null, scaned.getOldFile(), backupFile));
				} else {
					LOGGER.warn("[执行]-旧文件不存在无法删除:[{}]", scaned.getOldFile());
				}
			}
		}
	}

	public String getBackupPath(){
		return backupPath;
	}
}
