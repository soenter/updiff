/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/7 13:20
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/7        Initailized
 */
package com.soenter.updiff.upper;

import com.soenter.updiff.common.FileType;
import com.soenter.updiff.common.utils.DateUtils;
import com.soenter.updiff.upper.scan.Scaned;
import com.soenter.updiff.upper.scan.Scanner;
import com.soenter.updiff.upper.scan.impl.ScannerImpl;
import com.soenter.updiff.upper.update.Executor;
import com.soenter.updiff.upper.update.Update;
import com.soenter.updiff.upper.update.UpdateFactory;
import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.archiver.AbstractUnArchiver;
import org.codehaus.plexus.archiver.tar.TarGZipUnArchiver;
import org.codehaus.plexus.archiver.zip.ZipUnArchiver;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @ClassName ��com.soenter.updiff.upper.Upper
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/7 13:20
 * @version 1.0.0
 *
 */
public class Upper {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Upper.class);

	private String oldPath;

	private String newPath;

	private String backupDir;

	public Upper (String oldPath, String newPath) {


		LOGGER.info("要更新的目录:{}", oldPath);
		LOGGER.info("更新文件或目录{}", newPath);


		String time = DateUtils.format("yyyyMMddHHmmss");
		this.oldPath = oldPath;
		File oldFile = new File(oldPath);
		if(!oldFile.exists()){
			LOGGER.error("旧文件不存在：{}", newPath);
			return;
		}

		File newFile = new File(newPath);
		if(!newFile.exists()){
			LOGGER.error("更新文件不存在：{}", newPath);
			return;
		}
		if(!newFile.isDirectory()){
			AbstractUnArchiver unArchiverOld;

			if(newPath.endsWith(FileType.ZIP.getType())){
				unArchiverOld = new ZipUnArchiver();
			} else if(newPath.endsWith(FileType.TAR_GZ.getType())){
				unArchiverOld = new TarGZipUnArchiver();
			} else {
				LOGGER.error("仅支持.zip .tar.gz 压缩格式，非法文件格式： {}", newPath);
				return;
			}

			unArchiverOld.enableLogging(new ConsoleLogger(Logger.LEVEL_ERROR, "Package"));
			unArchiverOld.setSourceFile(newFile);
			File unpackFile = new File(newFile.getParent(), newFile.getName() + "_up_source_" + time);
			if(!unpackFile.exists()){
				unpackFile.mkdirs();
			}
			//unArchiverOld.setDestDirectory(unpackFile);
			unArchiverOld.setDestFile(unpackFile);
			unArchiverOld.extract();

			File[] files = unpackFile.listFiles();

			if(files.length == 1 && files[0].isDirectory()){
				try {
					File[] files_0 = files[0].listFiles();

					for (File f: files_0){
						if(f.isFile()){
							FileUtils.moveFile(f, new File(unpackFile, f.getName()));
						} else {
							FileUtils.moveDirectory(f, new File(unpackFile, f.getName()));
						}
					}

					if(!files[0].delete()){
						LOGGER.error("删除文件夹失败：{}", files[0].getAbsolutePath());
						return;
					}
				} catch (IOException e) {
					LOGGER.error("复制文件夹失败：{}", e.getMessage());
					return;
				}
			}
			this.newPath = unpackFile.getAbsolutePath();



		} else {
			this.newPath = newPath;
		}

		this.backupDir = oldPath + "_backup_" + time;
	}


	public void up(){
		Scanner<Scaned> scanner = new ScannerImpl(new File(oldPath), new File(newPath));
		Iterator<Scaned> it = scanner.iterator();

		Executor executor = new Executor();
		while(it.hasNext()){
			try {
				Update update = UpdateFactory.create(it.next(), backupDir);
				executor.execute(update);

			} catch (Exception e){

				try {
					executor.recovery();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			}
		}
	}


	public static void main(String[] args){
		if(args.length != 2){
			LOGGER.error("参数长度不够，长度必须为两个，第一个为要更新的目录，第二个为更新文件或目录。");
			return;
		}

		Upper upper = new Upper(args[0], args[1]);

		if(upper != null){
			upper.up();
		}

	}

}
