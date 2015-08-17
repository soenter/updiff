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
import com.sand.updiff.common.utils.UpdiffFileUtils;
import com.sand.updiff.upper.dom.BackupItem;
import com.sand.updiff.upper.dom.BackupListWriter;
import com.sand.updiff.upper.dom.RedologItem;
import com.sand.updiff.upper.dom.RedologWriter;
import com.sand.updiff.upper.scan.Scanned;
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

	protected Scanned scanned;

	protected File backupFile;

	protected String backupPath;

	protected String backupDir;

	protected RedologWriter redologWriter;

	public DefaultUpdate (Scanned scanned, String backupDir) throws IOException{
		this.scanned = scanned;
		this.backupDir = backupDir;
		this.backupPath = backupDir + File.separator + scanned.getRelativePath();
		this.backupFile = new File(this.backupPath);

		redologWriter = RedologWriter.getInstance(backupDir);
	}

	public void backup () throws IOException {
		//不备份文件夹
		if(scanned.isModifyFile() || scanned.isDeleteFile()){
			FileUtils.copyFile(scanned.getOldFile(), backupFile);
			LOGGER.info("[备份]-备份文件:[{}] ==> [{}]", scanned.getOldFile(), backupFile);

			new BackupListWriter(backupDir).writeItem(new BackupItem(scanned.getOldFile().getPath(), backupPath));
		}
	}

	public void execute () throws IOException {
		if(scanned.isDir()){
			if(scanned.isAddFile()){
				LOGGER.info("[执行]-创建文件夹:[{}]", scanned.getOldFile());
				if(scanned.getOldFile().exists()){
					throw new IOException("[执行]-要添加的文件夹已经存在：" + scanned.getOldFile().getAbsolutePath());
				}
				Stack<File> mkdirs = UpdiffFileUtils.mkdirs(scanned.getOldFile());
				if(mkdirs == null){
					throw new IOException("[执行]-创建添加的文件夹失败：" + scanned.getOldFile().getAbsolutePath());
				}
				try {
					while (!mkdirs.isEmpty()){
						File file = mkdirs.pop();
						redologWriter.addItem(new RedologItem(true, ChangeType.ADD, null, file, null));
					}
				} finally {
					redologWriter.write();
				}
			} else if(scanned.isModifyFile()){
				//文件夹不支持修改
				LOGGER.warn("[执行]-文件夹不支持修改:[{}]", scanned.getOldFile());
			} else if(scanned.isDeleteFile()){
				if(scanned.getOldFile().exists()){
					LOGGER.info("[执行]-删除旧文件夹:[{}]", scanned.getOldFile());
					if(!scanned.getOldFile().delete()){
						throw new IOException("[执行]-删除旧文件夹失败：" + scanned.getOldFile().getAbsolutePath());
					}
					redologWriter.writeItem(new RedologItem(true, ChangeType.DELETE, null, scanned.getOldFile(), null));
				} else {
					LOGGER.warn("[执行]-旧文件夹不存在无法删除:[{}]", scanned.getOldFile());
				}
			}

		} else {
			if(scanned.isAddFile()){
				LOGGER.info("[执行]-添加文件:[{}] ==> [{}]", scanned.getNewFile(), scanned.getOldFile());
				if(!scanned.getOldFile().getParentFile().exists()){
					Stack<File> mkdirs = UpdiffFileUtils.mkdirs(scanned.getOldFile().getParentFile());
					if(mkdirs == null){
						throw new IOException("[执行]-创建父类文件夹失败：" + scanned.getOldFile().getParentFile().getAbsolutePath());
					}
					try {
						while(!mkdirs.isEmpty()){
							File file = mkdirs.pop();
							redologWriter.addItem(new RedologItem(true, ChangeType.ADD, null, file, null));
						}
					} finally {
						redologWriter.write();
					}
				}
				FileUtils.copyFile(scanned.getNewFile(), scanned.getOldFile());
				redologWriter.writeItem(new RedologItem(false, ChangeType.ADD, scanned.getNewFile(), scanned.getOldFile(), backupFile));
			} else if(scanned.isModifyFile()){
				LOGGER.info("[执行]-修改文件:[{}] ==> [{}]", scanned.getNewFile(), scanned.getOldFile());
				if(scanned.getOldFile().exists() && !scanned.getOldFile().delete()){
					throw new IOException("[执行]-删除旧文件失败：" + scanned.getOldFile().getAbsolutePath());
				}
				FileUtils.copyFile(scanned.getNewFile(), scanned.getOldFile());
				redologWriter.writeItem(new RedologItem(false, ChangeType.MODIFY, scanned.getNewFile(), scanned.getOldFile(), backupFile));
			} else if(scanned.isDeleteFile()){
				if(scanned.getOldFile().exists()){
					LOGGER.info("[执行]-删除文件:[{}]", scanned.getOldFile());
					if(!scanned.getOldFile().delete()) {
						throw new IOException("[执行]-删除旧文件失败：" + scanned.getOldFile().getAbsolutePath());
					}
					redologWriter.writeItem(new RedologItem(false, ChangeType.DELETE, null, scanned.getOldFile(), backupFile));
				} else {
					LOGGER.warn("[执行]-旧文件不存在无法删除:[{}]", scanned.getOldFile());
				}
			}
		}
	}

	public void recovery () throws IOException {
		if(scanned.isDir()){
			if(scanned.isAddFile()){
				LOGGER.info("[恢复]-创建的文件夹:[{}]", scanned.getOldFile());
				if(scanned.getOldFile().exists()){
					if(!scanned.getOldFile().delete()){
						throw new IOException("[恢复]-删除创建文件夹：" + scanned.getOldFile().getAbsolutePath());
					}
				}
			} else if(scanned.isModifyFile()){
				//文件夹不支持修改恢复
				LOGGER.warn("[恢复]-文件夹不支持修改恢复:[{}]", scanned.getOldFile());
			} else if(scanned.isDeleteFile()){
				if(!scanned.getOldFile().exists()){
					LOGGER.info("[恢复]-删除的文件夹:[{}]", scanned.getOldFile());
					if(!scanned.getOldFile().mkdir()){
						throw new IOException("[恢复]-删除旧文件夹：" + scanned.getOldFile().getAbsolutePath());
					}
				}
			}

		} else {
			if(scanned.isAddFile()){
				LOGGER.info("[恢复]-添加的文件:[{}]", scanned.getOldFile());
				if (scanned.getOldFile().exists() && !scanned.getOldFile().delete()){
					throw new IOException("[恢复]-删除添加的文件：" + scanned.getOldFile().getAbsolutePath());
				}
			} else if (scanned.isModifyFile()){
				LOGGER.info("[恢复]-修改的文件:[{}] ==> [{}]", backupFile, scanned.getOldFile());
				if(scanned.getOldFile().exists() && !scanned.getOldFile().delete()){
					throw new IOException("[恢复]-删除旧文件失败：" + scanned.getOldFile().getAbsolutePath());
				}
				FileUtils.copyFile(backupFile, scanned.getOldFile());
			} else if(scanned.isDeleteFile()){
				if(!scanned.getOldFile().exists()){
					LOGGER.info("[恢复]-删除的文件:[{}]", scanned.getOldFile());
					FileUtils.copyFile(backupFile, scanned.getOldFile());
				} else {
					LOGGER.warn("[恢复]-删除的文件已经存在无法恢复：[{}]", scanned.getOldFile());
				}
			}
		}
	}
}
