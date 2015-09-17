package com.sand.updiff.upper.update.impl;

import com.sand.updiff.common.ChangeType;
import com.sand.updiff.common.FileType;
import com.sand.updiff.common.utils.UpdiffFileUtils;
import com.sand.updiff.upper.dom.BackupItem;
import com.sand.updiff.upper.dom.BackupListWriter;
import com.sand.updiff.upper.dom.RedologItem;
import com.sand.updiff.upper.scan.Scanned;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/7 16:35
 * @since 1.0.0
 *
 */
public class ClassUpdate extends DefaultUpdate {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClassUpdate.class);

	private List<File> oldFiles;
	private List<File> newFiles;



	public ClassUpdate (Scanned scanned, String backupDir) throws IOException {
		super(scanned, backupDir);

		if(scanned.isDir() || !scanned.getNewFile().getName().endsWith(FileType.CLASS.getType())){
			throw new IOException("ClassUpdate 只能处理以.class结尾的文件");
		}

		String name = scanned.getNewFile().getName();

		if(!scanned.isAddFile()){
			File oldParent = scanned.getOldFile().getParentFile();
			oldFiles = genInnerClassList(oldParent, name);
		}

		File newParent = scanned.getNewFile().getParentFile();
		if(newParent != null){
			newFiles = genInnerClassList(newParent, name);
		}


	}

	public void backup () throws IOException {
		if(!getScanned().isAddFile()){
			File backupDirFile = getBackupFile().getParentFile();

			if(!backupDirFile.exists() && !backupDirFile.mkdirs()){
				throw new IOException("[备份]-创建备份文件夹失败:" + backupDirFile.getAbsolutePath());
			}
			BackupListWriter backupListWriter = new BackupListWriter(getBackupDir());

			try {
				for(File f: oldFiles){
					File backupFile = new File(backupDirFile, f.getName());
					if(backupFile.exists()){
						throw new IOException("[备份]-备份文件已经存在:" + backupFile.getAbsolutePath());
					}
					FileUtils.copyFile(f, backupFile);

					LOGGER.info("[备份]-备份文件:[{}] ==> [{}]", f, backupFile);

					backupListWriter.addItem(new BackupItem(f.getAbsolutePath(), backupFile.getAbsolutePath()));
				}
			} finally {
				backupListWriter.write();
			}
		}
	}

	public void execute () throws IOException {

		if(getScanned().isAddFile()){
			//拷贝新文件
			File oldPathDir = getScanned().getOldFile().getParentFile();
			for(File f: newFiles){
				File newFile = new File(oldPathDir, f.getName());
				File newFileParent = newFile.getParentFile();

				if(!newFileParent.exists()){
					Stack<File> mkdirs = UpdiffFileUtils.mkdirs(newFileParent);
					if(mkdirs == null){
						throw new IOException("[更新]-新文件父目录创建失败:" + newFileParent.getAbsolutePath());
					}
					try {
						while(!mkdirs.isEmpty()){
							File file = mkdirs.pop();
							getRedologWriter().addItem(new RedologItem(true, ChangeType.ADD, null, file, null));
						}
					} finally {
						getRedologWriter().write();
					}
				}
				LOGGER.info("[添加]-添加文件:[{}] ==> [{}]", f, newFile);
				FileUtils.copyFile(f, newFile);
				getRedologWriter().writeItem(new RedologItem(false, ChangeType.ADD, f, newFile, null));
			}
		} else if(getScanned().isModifyFile()){
			//删除旧文件
			for(File f: oldFiles){
				if(f.exists()){
					LOGGER.info("[删除]-删除文件:[{}]", f);
					if(!f.delete()){
						throw new IOException("[更新]-旧文件删除失败:" + f.getAbsolutePath());
					}
				}
			}
			//拷贝新文件
			File oldPathDir = getScanned().getOldFile().getParentFile();
			for(File f: newFiles){
				File newFile = new File(oldPathDir, f.getName());
				if(newFile.exists()){
					throw new IOException("[更新]-新文件已经存在:" + f.getAbsolutePath());
				}
				LOGGER.info("[修改]-修改文件:[{}] ==> [{}]", f, newFile);
				FileUtils.copyFile(f, newFile);
			}
			getRedologWriter().writeItem(new RedologItem(false, ChangeType.MODIFY, getScanned().getNewFile(), getScanned().getOldFile(), getBackupFile()));
		} else if(getScanned().isDeleteFile()){
			//删除旧文件
			for(File f: oldFiles){
				if(f.exists()){
					LOGGER.info("[删除]-删除文件:[{}]", f);
					if(!f.delete()){
						throw new IOException("[更新]-旧文件删除失败:" + f.getAbsolutePath());
					}
				}
			}

			getRedologWriter().writeItem(new RedologItem(false, ChangeType.DELETE, getScanned().getNewFile(), getScanned().getOldFile(), getBackupFile()));
		}


	}


	@Override
	public void recovery () throws IOException {
		if(getScanned().isAddFile()){
			//删除添加的文件
			for(File f: newFiles){
				if(f.exists()){
					LOGGER.info("[恢复]-删除添加的文件:[{}]", f);
					if(!f.delete()){
						throw new IOException("[恢复]-旧文件删除失败:" + f.getAbsolutePath());
					}
				}
			}
		} else if (getScanned().isModifyFile()){
			//删除新文件
			File oldPathDir = getScanned().getOldFile().getParentFile();
			for(File f: newFiles){
				File newFile = new File(oldPathDir, f.getName());
				if(newFile.exists()){
					LOGGER.info("[恢复]-删除添加的文件:[{}]", newFile);
					if(!newFile.delete()){
						throw new IOException("[恢复]-删除添加的文件失败:" + newFile.getAbsolutePath());
					}
				}
			}
			//恢复旧文件
			File backupDirFile = getBackupFile().getParentFile();
			for(File f: oldFiles){
				File backupFile = new File(backupDirFile, f.getName());
				LOGGER.info("[恢复]-修改过的文件:[{}] ==> [{}]", backupFile, f);
				if(!backupFile.exists()){
					throw new IOException("[恢复]-备份文件不存在无法恢复：" + backupFile.getAbsolutePath());
				}
				if(f.exists() && !f.delete()){
					throw new IOException("[恢复]-旧文件无法删除：" + f.getAbsolutePath());
				}
				FileUtils.copyFile(backupFile, f);
			}
		} else if(getScanned().isDeleteFile()){
			//恢复旧文件
			File backupDirFile = getBackupFile().getParentFile();
			for(File f: oldFiles){
				File backupFile = new File(backupDirFile, f.getName());
				LOGGER.info("[恢复]-修改过的文件:[{}] ==> [{}]", backupFile, f);
				if(!backupFile.exists()){
					throw new IOException("[恢复]-备份文件不存在无法恢复：" + backupFile.getAbsolutePath());
				}
				if(f.exists() && !f.delete()){
					throw new IOException("[恢复]-旧文件无法删除：" + f.getAbsolutePath());
				}
				FileUtils.copyFile(backupFile, f);
			}
		}
	}

	private static List<File> genInnerClassList(File file, String name){
		String filterName = name.substring(0, name.length() - FileType.CLASS.getType().length()) + "$";

		List<File> files = new ArrayList<File>();
		for(File f: file.listFiles()){
			if(f.isDirectory()) continue;

			if(f.getName().equals(name) || (f.getName().startsWith(filterName) && f.getName().endsWith(FileType.CLASS.getType()))){
				files.add(f);
			}
		}
		return files;
	}

}
