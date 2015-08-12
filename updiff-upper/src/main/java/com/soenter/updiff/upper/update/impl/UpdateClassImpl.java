/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/7 16:35
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/7        Initailized
 */
package com.soenter.updiff.upper.update.impl;

import com.soenter.updiff.common.FileType;
import com.soenter.updiff.upper.scan.Scaned;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName ：com.soenter.updiff.upper.update.impl.UpdateClassImpl
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/7 16:35
 * @version 1.0.0
 *
 */
public class UpdateClassImpl extends UpdateImpl{

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateClassImpl.class);

	private List<File> oldFiles;
	private List<File> newFiles;



	public UpdateClassImpl (Scaned scaned, String backupPath) throws IOException {
		super(scaned, backupPath);

		if(scaned.isDir() || !scaned.getNewFile().getName().endsWith(FileType.CLASS.getType())){
			throw new IOException("UpdateClassImpl 只能处理以.class结尾的文件");
		}

		String name = scaned.getNewFile().getName();

		File oldParent = scaned.getOldFile().getParentFile();
		oldFiles = genInnerClassList(oldParent, name);

		File newParent = scaned.getNewFile().getParentFile();
		newFiles = genInnerClassList(newParent, name);


	}

	public void backup () throws IOException {
		if(!scaned.isAddFile()){
			File backupDir = new File(backupPath).getParentFile();

			if(!backupDir.exists() && !backupDir.mkdirs()){
				throw new IOException("创建备份文件夹失败:" + backupDir.getAbsolutePath());
			}

			for(File f: oldFiles){
				File backupFile = new File(backupDir, f.getName());
				if(backupFile.exists()){
					throw new IOException("备份文件已经存在:" + backupFile.getAbsolutePath());
				}
				FileUtils.copyFile(f, backupFile);
				LOGGER.info("[备份]-备份文件:[{}] ==> [{}]", f, backupFile);
			}
		}
	}

	public void recovery () throws IOException {
		if(scaned.isAddFile()){
			//删除新加文件
			deleteAddFile();
		} else if(scaned.isModifyFile()){
			//删除新加文件
			deleteAddFile();
			//还原备份文件
			recoveryOldFile();
		} else if(scaned.isDeleteFile()){
			//还原备份文件
			recoveryOldFile();
		}

	}

	public void execute () throws IOException {

		if(scaned.isAddFile()){
			//拷贝新文件
			File oldPathDir = scaned.getOldFile().getParentFile();
			for(File f: newFiles){
				File newFile = new File(oldPathDir, f.getName());
				if(newFile.exists()){
					throw new IOException("新文件已经存在:" + newFile.getAbsolutePath());
				}
				File newFileParent = newFile.getParentFile();

				if(!newFileParent.exists() && !newFileParent.mkdirs()){
					throw new IOException("新文件父目录创建失败:" + newFileParent.getAbsolutePath());
				}
				FileUtils.copyFile(f, newFile);
				LOGGER.info("[执行]-添加文件:[{}] ==> [{}]", f, newFile);
			}
		} else if(scaned.isModifyFile()){
			//删除旧文件
			for(File f: oldFiles){
				if(f.exists()){
					if(!f.delete()){
						throw new IOException("旧文件删除失败:" + f.getAbsolutePath());
					}
					LOGGER.info("[执行]-删除文件:[{}]", f);
				}
			}
			//拷贝新文件
			File oldPathDir = scaned.getOldFile().getParentFile();
			for(File f: newFiles){
				File newFile = new File(oldPathDir, f.getName());
				if(newFile.exists()){
					throw new IOException("新文件已经存在:" + f.getAbsolutePath());
				}
				FileUtils.copyFile(f, newFile);
				LOGGER.info("[执行]-修改文件:[{}] ==> [{}]", f, newFile);
			}
		} else if(scaned.isDeleteFile()){
			//删除旧文件
			for(File f: oldFiles){
				if(f.exists()){
					if(!f.delete()){
						throw new IOException("旧文件删除失败:" + f.getAbsolutePath());
					}
					LOGGER.info("[执行]-删除文件:[{}]", f);
				}
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

	private void deleteAddFile() throws IOException {
		//删除新加文件
		File oldPathDir = scaned.getOldFile().getParentFile();
		for(File f: newFiles){
			File newFile = new File(oldPathDir, f.getName());
			if(newFile.exists()){
				if(!newFile.delete()){
					throw new IOException("[恢复]-删除新加文件失败：" + newFile.getAbsolutePath());
				}
				LOGGER.info("[恢复]-删除添加的文件:[{}]", newFile);
			}
		}
	}

	private void recoveryOldFile() throws IOException {
		//还原备份文件
		File backupDir = new File(backupPath).getParentFile();
		for(File f: oldFiles){
			File backupFile = new File(backupDir, f.getName());
			if(f.exists() && !f.delete()){
				throw new IOException("[恢复]-删除已有旧文件失败：" + f.getAbsolutePath());
			}
			FileUtils.copyFile(backupFile, f);
			LOGGER.info("[恢复]-修改或删除的文件::[{}] ==> [{}]", backupFile, f);
		}
	}
}
