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
package com.soenter.updiff.upper.update.impl;

import com.soenter.updiff.common.DiffElement;
import com.soenter.updiff.upper.scan.Scaned;
import com.soenter.updiff.upper.scan.Scanner;
import com.soenter.updiff.upper.scan.impl.ScandDiffImpl;
import com.soenter.updiff.upper.scan.impl.ScannerDiffImpl;
import com.soenter.updiff.upper.update.Executor;
import com.soenter.updiff.upper.update.UpdateFactory;
import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.archiver.jar.JarArchiver;
import org.codehaus.plexus.archiver.zip.ZipUnArchiver;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.logging.console.ConsoleLogger;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @ClassName ：com.soenter.updiff.upper.update.impl.UpdateJarImpl
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/7 16:34
 * @version 1.0.0
 *
 */
public class UpdateJarImpl extends UpdateImpl {

	public UpdateJarImpl (Scaned scaned, String backupPath) throws IOException {
		super(scaned, backupPath);
		if(!scaned.isJar()){
			throw new IOException("UpdateJarImpl 只能处理jar文件");
		}
	}

	@Override
	public void execute () throws IOException {

		if(scaned.isAddFile()){//新添加文件
			if(!scaned.getOldFile().getParentFile().exists() && !scaned.getOldFile().getParentFile().mkdirs()){
				throw new IOException("创建父类文件夹失败：" + scaned.getOldFile().getParentFile().getAbsolutePath());
			}
			FileUtils.copyFile(scaned.getNewFile(), scaned.getOldFile());
		} else if(scaned.hasDiff()){//存在diff文件

			//0.备份文件
			FileUtils.copyFile(scaned.getOldFile(), backupFile);

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

			//3.根据diff配置更新“{filename}_old”中的文件到“{filename}_new”中
			Scanner<DiffElement> diffElScanner = new ScannerDiffImpl(scaned.getDiffFile());
			Iterator<DiffElement> diffIt = diffElScanner.iterator();
			String updateJarBack = backupFile.getAbsolutePath() + "_bak";

			Executor executor = new Executor();
			while(diffIt.hasNext()){
				try {
					DiffElement element = diffIt.next();
					String compliedPath = element.getCompiledNewPath();
					File oldFile = new File(unjarOld.getAbsolutePath() + File.separator + compliedPath);
					File newFile = new File(unjarNew.getAbsolutePath() + File.separator + compliedPath);
					Scaned scaned = new ScandDiffImpl(oldFile, newFile, compliedPath, element);

					if(newFile.exists() || scaned.isDeleteFile()){
						executor.execute(UpdateFactory.create(scaned, updateJarBack));
					}
				} catch (Exception e){
					executor.recovery();
					throw new IOException(e);
				}
			}


			//4.打包“{filename}_old”为新jar
			JarArchiver archiver = new JarArchiver();

			archiver.addDirectory(unjarOld);
			File destFile = new File(backupFile.getParent(), backupFile.getName() + "_new.jar");
			archiver.setDestFile(destFile);
			archiver.createArchive();


			//5.用新jar替换旧文件
			if(scaned.getOldFile().exists() && !scaned.getOldFile().delete()){
				throw new IOException("删除旧文件失败：" + scaned.getOldFile().getAbsolutePath());
			}
			FileUtils.copyFile(destFile, scaned.getOldFile());
		}
	}
}
