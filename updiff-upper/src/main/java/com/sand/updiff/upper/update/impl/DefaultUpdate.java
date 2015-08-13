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

import com.sand.updiff.common.FileType;
import com.sand.updiff.upper.dom.BackupItem;
import com.sand.updiff.upper.dom.BackupListWriter;
import com.sand.updiff.upper.scan.Scaned;
import com.sand.updiff.upper.update.Update;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

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

	public DefaultUpdate (Scaned scaned, String backupDir) throws IOException{
		this.scaned = scaned;
		this.backupDir = backupDir;
		this.backupPath = backupDir + File.separator + scaned.getRelativePath();
		this.backupFile = new File(this.backupPath);

		if(backupFile.exists()){
			throw new IOException("备份文件已经存在：" + this.backupPath);
		}
	}

	public void backup () throws IOException {
		//不备份文件夹
		if(scaned.isModifyFile() || scaned.isDeleteFile()){
			FileUtils.copyFile(scaned.getOldFile(), backupFile);
			LOGGER.info("[备份]-备份文件:[{}] ==> [{}]", scaned.getOldFile(), backupFile);

			new BackupListWriter(backupDir).writeItem(new BackupItem(scaned.getOldFile().getPath(), backupPath));
		}
	}

	public void recovery () throws IOException {
		if(scaned.isDir()){
			if(scaned.isAddFile()){
				if(scaned.getOldFile().exists()){
					if(!scaned.getOldFile().delete()){
						throw new IOException("[恢复]-删除创建文件夹失败：" + scaned.getOldFile().getAbsolutePath());
					}
					LOGGER.info("[恢复]-添加的文件夹:[{}]", scaned.getOldFile());
				}
			}
			//文件夹不支持恢复删除修改操作
		} else {
			if(scaned.isAddFile()){
				if(scaned.getOldFile().exists()){
					if(!scaned.getOldFile().delete()){
						throw new IOException("[恢复]-删除旧文件失败：" + scaned.getOldFile().getAbsolutePath());
					}
					LOGGER.info("[恢复]-添加的文件:[{}]", scaned.getOldFile());
				}
			} else if(scaned.isModifyFile() || scaned.isDeleteFile()){
				if(scaned.getOldFile().exists()){
					if(!scaned.getOldFile().delete()){
						throw new IOException("[恢复]-删除旧文件失败：" + scaned.getOldFile().getAbsolutePath());
					}
				}
				FileUtils.copyFile(backupFile, scaned.getOldFile());
				LOGGER.info("[恢复]-修改或删除的文件:[{}] ==> [{}]", backupFile, scaned.getOldFile());
			}
		}
	}

	public void execute () throws IOException {
		if(scaned.isDir()){
			if(scaned.isAddFile()){
				if(!scaned.getOldFile().exists()){

					if(!scaned.getOldFile().mkdirs()){
						throw new IOException("创建文件夹失败：" + scaned.getOldFile().getAbsolutePath());
					}
					LOGGER.info("[执行]-创建文件夹:[{}]", scaned.getOldFile());
				}
			}
			//文件夹不支持删除修改
		} else {
			if(scaned.isAddFile()){
				if(!scaned.getOldFile().getParentFile().exists() && !scaned.getOldFile().getParentFile().mkdirs()){
					throw new IOException("创建父类文件夹失败：" + scaned.getOldFile().getParentFile().getAbsolutePath());
				}
				FileUtils.copyFile(scaned.getNewFile(), scaned.getOldFile());;
				LOGGER.info("[执行]-添加文件:[{}] ==> [{}]", scaned.getNewFile(), scaned.getOldFile());
			} else if(scaned.isModifyFile()){
				if(scaned.getOldFile().exists() && !scaned.getOldFile().delete()){
					throw new IOException("删除旧文件失败：" + scaned.getOldFile().getAbsolutePath());
				}
				FileUtils.copyFile(scaned.getNewFile(), scaned.getOldFile());
				LOGGER.info("[执行]-修改文件:[{}] ==> [{}]", scaned.getNewFile(), scaned.getOldFile());
			} else if(scaned.isDeleteFile()){
				if(scaned.getOldFile().exists()){
					if(!scaned.getOldFile().delete()){
						throw new IOException("删除旧文件失败：" + scaned.getOldFile().getAbsolutePath());
					}
					LOGGER.info("[执行]-删除文件:[{}] ==> [{}]", scaned.getNewFile(), scaned.getOldFile());
				}
			}
		}
	}

	public String getBackupPath(){
		return backupPath;
	}
}
