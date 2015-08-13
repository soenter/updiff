/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/7 16:34
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/7        Initailized
 */
package com.sand.updiff.upper.update.impl;

import com.sand.updiff.common.DiffItem;
import com.sand.updiff.upper.dom.BackupItem;
import com.sand.updiff.upper.dom.BackupListWriter;
import com.sand.updiff.upper.scan.Scaned;
import com.sand.updiff.upper.scan.Scanner;
import com.sand.updiff.upper.scan.impl.DiffScand;
import com.sand.updiff.upper.scan.impl.DiffScanner;
import com.sand.updiff.upper.update.Executor;
import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.archiver.jar.JarArchiver;
import org.codehaus.plexus.archiver.zip.ZipUnArchiver;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @ClassName ：com.sand.updiff.upper.update.impl.JarUpdate
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/7 16:34
 * @version 1.0.0
 *
 */
public class JarUpdate extends DefaultUpdate {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JarUpdate.class);

	public JarUpdate (Scaned scaned, String backupPath) throws IOException {
		super(scaned, backupPath);
		if(!scaned.isJar()){
			throw new IOException("JarUpdate 只能处理jar文件");
		}
	}

	public void backup () throws IOException {
		//不备份文件夹
		if(!scaned.isAddFile() && scaned.hasDiff()){
			FileUtils.copyFile(scaned.getOldFile(), backupFile);
			LOGGER.info("[备份]-备份文件:[{}] ==> [{}]", scaned.getOldFile(), backupFile);

			new BackupListWriter(backupDir).writeItem(new BackupItem(scaned.getOldFile().getPath(), backupPath));
		}
	}

	@Override
	public void execute () throws IOException {

		if(scaned.isAddFile()){//新添加文件
			if(!scaned.getOldFile().getParentFile().exists() && !scaned.getOldFile().getParentFile().mkdirs()){
				throw new IOException("创建父类文件夹失败：" + scaned.getOldFile().getParentFile().getAbsolutePath());
			}
			FileUtils.copyFile(scaned.getNewFile(), scaned.getOldFile());
			LOGGER.info("[执行]-添加文件:[{}] ==> [{}]", scaned.getNewFile(), scaned.getOldFile());
		} else if(scaned.hasDiff()){//存在diff文件

			//1.解压备份jar 到 备份文件夹下 {filename}_old
			ZipUnArchiver unArchiverOld = new ZipUnArchiver();
			unArchiverOld.enableLogging(new ConsoleLogger(Logger.LEVEL_ERROR,"Package"));
			unArchiverOld.setSourceFile(backupFile);
			File unjarOld = new File(backupFile.getParent(), backupFile.getName() + "_old");
			if(!unjarOld.exists()){
				unjarOld.mkdirs();
			}
			unArchiverOld.setDestDirectory(unjarOld);
			unArchiverOld.extract();

			LOGGER.info("[执行]-解压旧jar文件:[{}] ==> [{}]", backupFile, unjarOld);

			//2.解压新jar 到 备份文件夹下 {filename}_new
			ZipUnArchiver unArchiverNew = new ZipUnArchiver();
			unArchiverNew.enableLogging(new ConsoleLogger(Logger.LEVEL_ERROR,"Package"));
			unArchiverNew.setSourceFile(scaned.getNewFile());
			File unjarNew = new File(backupFile.getParent(), backupFile.getName() + "_new");
			if(!unjarNew.exists()){
				unjarNew.mkdirs();
			}
			unArchiverNew.setDestDirectory(unjarNew);
			unArchiverNew.extract();

			LOGGER.info("[执行]-解压新jar文件:[{}] ==> [{}]", scaned.getNewFile(), unjarNew);

			//3.根据diff配置更新“{filename}_old”中的文件到“{filename}_new”中
			Scanner<DiffItem> diffElScanner = new DiffScanner(scaned.getDiffFile());
			Iterator<DiffItem> diffIt = diffElScanner.iterator();
			String updateJarBack = backupFile.getAbsolutePath() + "_bak";

			Executor executor = new UpperExecutor();
			while(diffIt.hasNext()){
				DiffItem element = diffIt.next();
				String compliedPath = element.getCompiledNewPath();
				File oldFile = new File(unjarOld.getAbsolutePath() + File.separator + compliedPath);
				File newFile = new File(unjarNew.getAbsolutePath() + File.separator + compliedPath);
				Scaned scaned = new DiffScand(oldFile, newFile, compliedPath, element);

				if(newFile.exists() || scaned.isDeleteFile()){
					if(!executor.execute(new DefaultTask(scaned, updateJarBack))){
						throw new IOException("配置合成新jar结构文件出错");
					}
				}
			}

			LOGGER.info("[执行]-配置合成新jar结构文件:[{}]", updateJarBack);

			//4.打包“{filename}_old”为新jar
			JarArchiver archiver = new JarArchiver();
			archiver.enableLogging(new ConsoleLogger(Logger.LEVEL_ERROR,"Package"));
			archiver.addDirectory(unjarOld);
			File destFile = new File(backupFile.getParent(), backupFile.getName() + "_new.jar");
			archiver.setDestFile(destFile);
			archiver.createArchive();

			LOGGER.info("[执行]-打包合成后的jar文件:[{}]", destFile);

			//5.用新jar替换旧文件
			if(scaned.getOldFile().exists() && !scaned.getOldFile().delete()){
				throw new IOException("删除旧文件失败：" + scaned.getOldFile().getAbsolutePath());
			}
			FileUtils.copyFile(destFile, scaned.getOldFile());
			LOGGER.info("[执行]-更新jar文件:[{}] ==> [{}]", destFile, scaned.getOldFile());
		}
	}
}
