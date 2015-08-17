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

import com.sand.updiff.common.ChangeType;
import com.sand.updiff.common.DiffItem;
import com.sand.updiff.common.utils.DateUtils;
import com.sand.updiff.common.utils.UpdiffFileUtils;
import com.sand.updiff.upper.dom.BackupItem;
import com.sand.updiff.upper.dom.BackupListWriter;
import com.sand.updiff.upper.dom.RedologItem;
import com.sand.updiff.upper.scan.Scanned;
import com.sand.updiff.upper.scan.Scanner;
import com.sand.updiff.upper.scan.impl.DiffScanned;
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
import java.nio.file.Files;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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

	public JarUpdate (Scanned scanned, String backupPath) throws IOException {
		super(scanned, backupPath);
		if(!scanned.isJar()){
			throw new IOException("JarUpdate 只能处理jar文件");
		}
	}

	public void backup () throws IOException {
		//不备份文件夹
		if(!scanned.isAddFile() && scanned.hasDiff()){
			FileUtils.copyFile(scanned.getOldFile(), backupFile);
			LOGGER.info("[备份]-备份文件:[{}] ==> [{}]", scanned.getOldFile(), backupFile);

			new BackupListWriter(backupDir).writeItem(new BackupItem(scanned.getOldFile().getPath(), backupPath));
		}
	}

	@Override
	public void execute () throws IOException {

		if(scanned.isAddFile()){//新添加文件
			LOGGER.info("[执行]-添加文件:[{}] ==> [{}]", scanned.getNewFile(), scanned.getOldFile());
			if(!scanned.getOldFile().getParentFile().exists()){
				Stack<File> mkdirs = UpdiffFileUtils.mkdirs(scanned.getOldFile().getParentFile());
				if(mkdirs == null){
					throw new IOException("创建父类文件夹失败：" + scanned.getOldFile().getParentFile().getAbsolutePath());
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
			redologWriter.writeItem(new RedologItem(false, ChangeType.ADD, scanned.getNewFile(), scanned.getOldFile(), null));
		} else if(scanned.hasDiff()){//存在diff文件

			//0.设置工作路径
			File workDir = new File(backupDir, ".work" + File.separator + scanned.getRelativePath()).getParentFile();
			if(!workDir.exists() && !workDir.mkdirs()){
				throw new IOException("创建工作路径失败：" + workDir.getAbsolutePath());
			}
			LOGGER.info("[执行]-创建工作路径:[{}]", workDir);

			//1.解压旧jar 到 备份文件夹下 {filename}_old_{time}
			String  time = DateUtils.format("yyyyMMddHHmmss");
			ZipUnArchiver unArchiverOld = new ZipUnArchiver();
			unArchiverOld.enableLogging(new ConsoleLogger(Logger.LEVEL_ERROR,"Package"));
			unArchiverOld.setSourceFile(scanned.getOldFile());
			File unjarOld = new File(workDir, scanned.getOldFile().getName() + "_old_" + time);
			if(!unjarOld.exists()){
				unjarOld.mkdirs();
			}
			unArchiverOld.setDestDirectory(unjarOld);
			unArchiverOld.extract();

			LOGGER.info("[执行]-解压旧jar文件:[{}] ==> [{}]", scanned.getOldFile(), unjarOld);

			//2.解压新jar 到 备份文件夹下 {filename}_new_{time}
			ZipUnArchiver unArchiverNew = new ZipUnArchiver();
			unArchiverNew.enableLogging(new ConsoleLogger(Logger.LEVEL_ERROR,"Package"));
			unArchiverNew.setSourceFile(scanned.getNewFile());
			File unjarNew = new File(workDir, scanned.getOldFile().getName() + "_new_" + time);
			if(!unjarNew.exists()){
				unjarNew.mkdirs();
			}
			unArchiverNew.setDestDirectory(unjarNew);
			unArchiverNew.extract();

			LOGGER.info("[执行]-解压新jar文件:[{}] ==> [{}]", scanned.getNewFile(), unjarNew);

			//3.根据diff配置更新“{filename}_old”中的文件到“{filename}_new”中
			Scanner<DiffItem> diffElScanner = new DiffScanner(scanned.getDiffFile());
			Iterator<DiffItem> diffIt = diffElScanner.iterator();
			File updateJarBack = new File(workDir, scanned.getOldFile().getName() + "_bak_" + time);

			Executor executor = new UpperExecutor(updateJarBack.getAbsolutePath());
			List<Scanned> scannedList = new LinkedList<Scanned>();
			while(diffIt.hasNext()){
				DiffItem element = diffIt.next();
				String compliedPath = element.getCompiledNewPath();
				File oldFile = new File(unjarOld.getAbsolutePath() + File.separator + compliedPath);
				File newFile = new File(unjarNew.getAbsolutePath() + File.separator + compliedPath);
				Scanned scanned = new DiffScanned(oldFile, newFile, compliedPath, element);

				if(newFile.exists() || scanned.isDeleteFile()){
					scannedList.add(scanned);
				}
			}

			//先备份
			for(Scanned scanned: scannedList){
				if(!executor.backup(scanned)){
					throw new IOException("配置合成新jar结构文件备份出错");
				}
			}
			//再执行
			for(Scanned scanned: scannedList){
				if(!executor.execute(scanned)){
					throw new IOException("配置合成新jar结构文件执行出错");
				}
			}

			LOGGER.info("[执行]-配置合成新jar结构文件:[{}]", updateJarBack);

			//4.打包“{filename}_old”为新jar
			JarArchiver archiver = new JarArchiver();
			archiver.enableLogging(new ConsoleLogger(Logger.LEVEL_ERROR,"Package"));
			archiver.addDirectory(unjarOld);
			File newJarFile = new File(workDir, scanned.getNewFile().getName());
			archiver.setDestFile(newJarFile);
			archiver.createArchive();

			LOGGER.info("[执行]-打包合成后的jar文件:[{}] ==> [{}]", unjarOld, newJarFile);

			//5.用新jar替换旧文件
			LOGGER.info("[执行]-更新jar文件:[{}] ==> [{}]", newJarFile, scanned.getOldFile());
			if(scanned.getOldFile().exists() && !scanned.getOldFile().delete()){
				throw new IOException("删除旧文件失败：" + scanned.getOldFile().getAbsolutePath());
			}
			FileUtils.copyFile(newJarFile, scanned.getOldFile());
			redologWriter.writeItem(new RedologItem(false, ChangeType.MODIFY, newJarFile, scanned.getOldFile(), backupFile));
		}
	}
}
