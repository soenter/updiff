package com.sand.updiff.upper.update.impl;

import com.sand.updiff.common.ChangeType;
import com.sand.updiff.common.utils.StringUtils;
import com.sand.updiff.common.utils.UpdiffFileUtils;
import com.sand.updiff.upper.dom.BackupItem;
import com.sand.updiff.upper.dom.BackupListWriter;
import com.sand.updiff.upper.dom.RedologItem;
import com.sand.updiff.upper.dom.RedologWriter;
import com.sand.updiff.upper.scan.Scanned;
import com.sand.updiff.upper.scan.impl.ConstaintJarDirScanned;
import com.sand.updiff.upper.scan.impl.RedologScanned;
import com.sand.updiff.upper.update.Update;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/7 16:33
 * @since 1.0.0
 *
 */
public class DefaultUpdate implements Update {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUpdate.class);

	private Scanned scanned;

	private File backupFile;

	private String backupDir;

	private RedologWriter redologWriter;

	public DefaultUpdate (Scanned scanned, String backupDir) throws IOException{
		this.scanned = scanned;
		this.backupDir = backupDir;
		if(scanned instanceof RedologScanned){
			backupFile = ((RedologScanned) scanned).getBackupFile();
		} else {
			if(!StringUtils.isBlank(scanned.getRelativePath())){
				this.backupFile = new File(backupDir + File.separator + scanned.getRelativePath());
			}
		}

		redologWriter = RedologWriter.getInstance(backupDir);
	}

	public void backup () throws IOException {
		if(scanned.isDir()){
			if(scanned.isModifyFile()){
				if(scanned instanceof ConstaintJarDirScanned){
					backupNoNeedDependencyJarFileInOldPath((ConstaintJarDirScanned) scanned);
				} else {
					//文件夹不支持备份
					LOGGER.debug("[备份]-文件夹不支持备份:[{}]", scanned.getOldFile());
				}
			} else {
				//文件夹不支持备份
				LOGGER.debug("[备份]-文件夹不支持备份:[{}]", scanned.getOldFile());
			}
		} else {
			//不备份文件夹
			if(scanned.isModifyFile() || scanned.isDeleteFile()){
				if(scanned.getOldFile().exists()){
					FileUtils.copyFile(scanned.getOldFile(), backupFile);
					LOGGER.info("[备份]-备份文件:[{}] ==> [{}]", scanned.getOldFile(), backupFile);
					new BackupListWriter(backupDir).writeItem(new BackupItem(scanned.getOldFile().getAbsolutePath(), backupFile.getAbsolutePath()));
				} else {
					LOGGER.warn("[备份]-备份文件不存在:[{}]", scanned.getOldFile());
				}
			}
		}
	}

	public void execute () throws IOException {
		if(scanned.isDir()){
			if(scanned.isAddFile()){
				if(scanned.getOldFile().exists()){
					LOGGER.warn("[更新]-要添加的文件夹已经存在:[{}]", scanned.getOldFile());
					return;
				}
				LOGGER.info("[添加]-创建文件夹:[{}]", scanned.getOldFile());
				Stack<File> mkdirs = UpdiffFileUtils.mkdirs(scanned.getOldFile());
				if(mkdirs == null){
					throw new IOException("[添加]-创建添加的文件夹失败：" + scanned.getOldFile().getAbsolutePath());
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

				if(scanned instanceof ConstaintJarDirScanned){
					deleteNoNeedDependencyJarFileInOldPath((ConstaintJarDirScanned)scanned);
				} else {
					//文件夹不支持修改
					LOGGER.warn("[修改]-文件夹不支持修改:[{}]", scanned.getOldFile());
				}
			} else if(scanned.isDeleteFile()){
				if(scanned.getOldFile().exists()){
					LOGGER.info("[删除]-删除旧文件夹:[{}]", scanned.getOldFile());
					if(!scanned.getOldFile().delete()){
						throw new IOException("[更新]-删除旧文件夹失败：" + scanned.getOldFile().getAbsolutePath());
					}
					redologWriter.writeItem(new RedologItem(true, ChangeType.DELETE, null, scanned.getOldFile(), null));
				} else {
					LOGGER.warn("[删除]-旧文件夹不存在无需删除:[{}]", scanned.getOldFile());
				}
			}

		} else {
			if(scanned.isAddFile()){
				if(!scanned.getNewFile().exists()){
					LOGGER.warn("[添加]-添加的新文件不存在:[{}]", scanned.getNewFile());
					return;
				}
				LOGGER.info("[添加]-添加文件:[{}] ==> [{}]", scanned.getNewFile(), scanned.getOldFile());
				if(!scanned.getOldFile().getParentFile().exists()){
					Stack<File> mkdirs = UpdiffFileUtils.mkdirs(scanned.getOldFile().getParentFile());
					if(mkdirs == null){
						throw new IOException("[更新]-创建父类文件夹失败：" + scanned.getOldFile().getParentFile().getAbsolutePath());
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
				if(!scanned.getNewFile().exists()){
					LOGGER.warn("[修改]-修改的新文件不存在:[{}]", scanned.getNewFile());
					return;
				}
				LOGGER.info("[修改]-修改文件:[{}] ==> [{}]", scanned.getNewFile(), scanned.getOldFile());
				if(scanned.getOldFile().exists() && !scanned.getOldFile().delete()){
					throw new IOException("[更新]-删除旧文件失败：" + scanned.getOldFile().getAbsolutePath());
				}
				FileUtils.copyFile(scanned.getNewFile(), scanned.getOldFile());
				redologWriter.writeItem(new RedologItem(false, ChangeType.MODIFY, scanned.getNewFile(), scanned.getOldFile(), backupFile));
			} else if(scanned.isDeleteFile()){
				if(scanned.getOldFile().exists()){
					LOGGER.info("[删除]-删除文件:[{}]", scanned.getOldFile());
					if(!scanned.getOldFile().delete()) {
						throw new IOException("[更新]-删除旧文件失败：" + scanned.getOldFile().getAbsolutePath());
					}
					redologWriter.writeItem(new RedologItem(false, ChangeType.DELETE, null, scanned.getOldFile(), backupFile));
				} else {
					LOGGER.warn("[删除]-旧文件不存在无法删除:[{}]", scanned.getOldFile());
				}
			}
		}
	}

	public void recovery () throws IOException {
		if(scanned.isDir()){
			if(scanned.isAddFile()){
				if(scanned.getOldFile().exists()){
					LOGGER.info("[恢复]-创建的文件夹:[{}]", scanned.getOldFile());
					if(!scanned.getOldFile().delete()){
						throw new IOException("[恢复]-删除创建的文件夹失败：" + scanned.getOldFile().getAbsolutePath());
					}
				} else {
					LOGGER.warn("[恢复]-创建的文件夹不存在无需删除:[{}]", scanned.getOldFile());
				}
			} else if(scanned.isModifyFile()){
				//文件夹不支持修改恢复
				LOGGER.warn("[恢复]-文件夹不支持修改恢复:[{}]", scanned.getOldFile());
			} else if(scanned.isDeleteFile()){
				if(!scanned.getOldFile().exists()){
					LOGGER.info("[恢复]-创建删除的文件夹:[{}]", scanned.getOldFile());
					if(!scanned.getOldFile().mkdir()){
						throw new IOException("[恢复]-创建删除的文件夹失败：" + scanned.getOldFile().getAbsolutePath());
					}
				} else {
					LOGGER.warn("[恢复]-创建的文件夹已存在无需创建:[{}]", scanned.getOldFile());
				}
			}

		} else {
			if(scanned.isAddFile()){
				if (scanned.getOldFile().exists()){
					LOGGER.info("[恢复]-添加的文件:[{}]", scanned.getOldFile());
					if(!scanned.getOldFile().delete()){
						throw new IOException("[恢复]-删除添加的文件：" + scanned.getOldFile().getAbsolutePath());
					}
				} else {
					LOGGER.warn("[恢复]-添加的文件不存在无需删除:[{}]", scanned.getOldFile());
				}
			} else if (scanned.isModifyFile()){
				LOGGER.info("[恢复]-修改的文件:[{}] ==> [{}]", backupFile, scanned.getOldFile());
				if(scanned.getOldFile().exists()){
					if(!scanned.getOldFile().delete()){
						throw new IOException("[恢复]-删除旧文件失败：" + scanned.getOldFile().getAbsolutePath());
					}
				}
				FileUtils.copyFile(backupFile, scanned.getOldFile());
			} else if(scanned.isDeleteFile()){
				if(!scanned.getOldFile().exists()){
					LOGGER.info("[恢复]-删除的文件:[{}] ==> [{}]", backupFile, scanned.getOldFile());
					FileUtils.copyFile(backupFile, scanned.getOldFile());
				} else {
					LOGGER.warn("[恢复]-删除的文件已经存在无需恢复：[{}]", scanned.getOldFile());
				}
			}
		}
	}

	public Scanned getScanned () {
		return scanned;
	}

	public File getBackupFile () {
		return backupFile;
	}

	public String getBackupDir () {
		return backupDir;
	}

	public RedologWriter getRedologWriter () {
		return redologWriter;
	}

	//=======================================private=============================================

	private void backupNoNeedDependencyJarFileInOldPath(ConstaintJarDirScanned scanned) throws IOException {
		List<File> oldDirJarFiles = scanned.getOldDirJarFiles();
		List<File> newDirJarFiles = scanned.getNewDirJarFiles();
		BackupListWriter backupListWriter = new BackupListWriter(backupDir);
		StringBuilder stringBuilder = new StringBuilder();
		boolean isFirst = true;
		for (int i = 0; i < oldDirJarFiles.size(); i++){
			File oldJarFile = oldDirJarFiles.get(i);
			File newJarFile = newDirJarFiles.get(i);
			if(oldJarFile.exists() && !newJarFile.exists()){
				File bakFile = new File(this.backupFile, oldJarFile.getName());
				FileUtils.copyFile(oldJarFile, bakFile);
				if(isFirst){
					isFirst = false;
				} else {
					stringBuilder.append(", ");
				}
				stringBuilder.append(oldJarFile.getName());
				backupListWriter.writeItem(new BackupItem(scanned.getOldFile().getAbsolutePath(), bakFile.getAbsolutePath()));
			}

		}
		if(!isFirst){
			LOGGER.info("[备份]-要删除的不必要的依赖jar文件：在[{}]中的[{}]", scanned.getOldFile(), stringBuilder);
		}
	}

	private void deleteNoNeedDependencyJarFileInOldPath(ConstaintJarDirScanned scanned) throws IOException {
		List<File> oldDirJarFiles = scanned.getOldDirJarFiles();
		List<File> newDirJarFiles = scanned.getNewDirJarFiles();
		StringBuilder stringBuilder = new StringBuilder();
		boolean isFirst = true;
		for (int i = 0; i < oldDirJarFiles.size(); i++){
			File oldJarFile = oldDirJarFiles.get(i);
			File newJarFile = newDirJarFiles.get(i);
			if(oldJarFile.exists() && !newJarFile.exists()){
				File bakFile = new File(this.backupFile, oldJarFile.getName());
				if(!oldJarFile.delete()){
					LOGGER.warn("[删除]-删除不必要的依赖jar文件失败：[{}]", oldJarFile);
				}
				redologWriter.writeItem(new RedologItem(false, ChangeType.DELETE, null, oldJarFile, bakFile));
				if(isFirst){
					isFirst = false;
				} else {
					stringBuilder.append(", ");
				}
				stringBuilder.append(oldJarFile.getName());
			}
		}
		if(!isFirst){
			LOGGER.info("[删除]-删除不必要的依赖jar文件：在[{}]中的[{}]", scanned.getOldFile(), stringBuilder);
		}
	}
}
