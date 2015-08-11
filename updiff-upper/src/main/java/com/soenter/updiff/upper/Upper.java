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

import com.soenter.updiff.common.DateUtils;
import com.soenter.updiff.upper.scan.Scaned;
import com.soenter.updiff.upper.scan.Scanner;
import com.soenter.updiff.upper.scan.impl.ScannerImpl;
import com.soenter.updiff.upper.update.Executor;
import com.soenter.updiff.upper.update.Update;
import com.soenter.updiff.upper.update.UpdateFactory;
import org.codehaus.plexus.archiver.zip.ZipUnArchiver;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.logging.console.ConsoleLogger;

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
	private String newPath;

	private String oldPath;

	private String backupDir;

	public Upper (String newPath, String oldPath) {

		File newFile = new File(newPath);
		if(newFile.isFile()){
			ZipUnArchiver unArchiverOld = new ZipUnArchiver();
			unArchiverOld.enableLogging(new ConsoleLogger(Logger.LEVEL_ERROR, "Package"));
			unArchiverOld.setSourceFile(newFile);
			File unpackFile = new File(newFile.getParent(), newFile.getName() + "_up_source" + DateUtils.format("yyyyMMddHHmmss"));
			if(!unpackFile.exists()){
				unpackFile.mkdirs();
			}
			unArchiverOld.setDestDirectory(unpackFile);
			unArchiverOld.extract();

			this.newPath = unpackFile.getAbsolutePath();
		} else {
			this.newPath = newPath;
		}

		this.oldPath = oldPath;

		this.backupDir = oldPath + "_up_" + DateUtils.format("yyyyMMddHHmmss");
	}


	public void up(){
		Scanner<Scaned> scanner = new ScannerImpl(new File(newPath), new File(oldPath));
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
			System.out.println("参数长度不够，长度必须为两个，第一个为更新文件或文件夹，第二个为要更新的目录。");
			return;
		}

		Upper upper = new Upper(args[0], args[1]);

		upper.up();

	}

}
