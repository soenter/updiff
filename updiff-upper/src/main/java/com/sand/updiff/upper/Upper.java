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
package com.sand.updiff.upper;

import com.sand.updiff.common.FileType;
import com.sand.updiff.common.utils.DateUtils;
import com.sand.updiff.upper.dom.RedologWriter;
import com.sand.updiff.upper.scan.Scanned;
import com.sand.updiff.upper.scan.Scanner;
import com.sand.updiff.upper.scan.impl.DefaultScanner;
import com.sand.updiff.upper.scan.impl.DiffScanner;
import com.sand.updiff.upper.update.Executor;
import com.sand.updiff.upper.update.impl.UpperExecutor;
import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.archiver.AbstractUnArchiver;
import org.codehaus.plexus.archiver.tar.TarGZipUnArchiver;
import org.codehaus.plexus.archiver.zip.ZipUnArchiver;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @ClassName : com.sand.updiff.upper.Upper
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

	private boolean isUpWar = false;

	public Upper (String oldPath, String newPath) {
		init(oldPath, newPath, null);
	}

	public Upper (String oldPath, String newPath, String backupDir) {
		init(oldPath, newPath, backupDir);
	}

	private void init(String oldPath, String newPath, String backupDir){

		String time = DateUtils.format("yyyyMMddHHmmss");

		if(backupDir == null || "".equals(backupDir)){
			this.backupDir = oldPath + "_backup_" + time;
		} else {
			this.backupDir = backupDir;
		}

		LOGGER.info("要更新的文件夹:{}", oldPath);
		LOGGER.info("更新包或文件夹:{}", newPath);
		LOGGER.info("备份文件夹:{}", this.backupDir);

		if(new File(this.backupDir).exists()){
			throw new RuntimeException(String.format("备份文件夹已经存在：%s", this.backupDir));
		}

		this.oldPath = oldPath;
		File oldFile = new File(oldPath);
		if(!oldFile.exists()){
			throw new RuntimeException(String.format("要更新的文件夹不存在：%s", oldPath));
		}

		File newFile = new File(newPath);
		if(!newFile.exists()){
			LOGGER.error("更新文件或目录不存在：{}", newPath);
			return;
		}
		if(!newFile.isDirectory()){
			AbstractUnArchiver unArchiverOld;

			if(newPath.endsWith(FileType.ZIP.getType()) || (isUpWar = newPath.endsWith(FileType.WAR.getType()))){
				unArchiverOld = new ZipUnArchiver();
			} else if(newPath.endsWith(FileType.TAR_GZ.getType())){
				unArchiverOld = new TarGZipUnArchiver();
			} else {
				throw new RuntimeException(String.format("仅支持.zip或.tar.gz或.war 压缩格式，非法文件格式： {}", newPath));
			}

			unArchiverOld.enableLogging(new ConsoleLogger(Logger.LEVEL_ERROR, "Package"));
			unArchiverOld.setSourceFile(newFile);
			File unpackFile = new File(newFile.getParent(), newFile.getName() + "_upper_source_" + time);
			if(!unpackFile.exists()){
				unpackFile.mkdirs();
			}
			//unArchiverOld.setDestDirectory(unpackFile);
			unArchiverOld.setDestFile(unpackFile);
			unArchiverOld.extract();

			File[] files = unpackFile.listFiles();

			if(files.length == 1 && files[0].isDirectory() && files[0].getName().equals(oldFile.getName())){
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
						throw new RuntimeException(String.format("删除文件夹失败：{}", files[0].getAbsolutePath()));
					}
				} catch (IOException e) {
					throw new RuntimeException(String.format("复制文件夹失败：{}", e.getMessage()));
				}
			}
			this.newPath = unpackFile.getAbsolutePath();


			LOGGER.info("更新包解压后路径:{}", this.newPath);
		} else {
			this.newPath = newPath;
		}
	}

	/**
	 * 先备份，再执行更新，最后如果出现异常就恢复。
	 * @return
	 */
	public boolean up() throws IOException {
		if(isUpWar){
			upWar(false);
		} else {
			upDefault(false);
		}
		return true;
	}

	public boolean upDefault(boolean onlyBackup) throws IOException {
		Scanner<Scanned> scanner = new DefaultScanner(new File(oldPath), new File(newPath));

		Iterator<Scanned> backupIt = scanner.iterator();
		Executor executor = new UpperExecutor(backupDir);

		while(backupIt.hasNext()){
			if(!executor.backup(backupIt.next())){
				return false;
			}
		}
		if(!onlyBackup){
			Iterator<Scanned> excuteIt = scanner.iterator();
			while(excuteIt.hasNext()){
				if(!executor.execute(excuteIt.next())){
					return false;
				}
			}
		}
		return true;
	}

	public boolean upWar(boolean onlyBackup) throws IOException {

		Map<String, Object> updatedMap = new HashMap<String, Object>();

		//1、先根据 .diff 文件更新差异
		Scanner<Scanned> diffScanner = null;
		File warDiffFile = null;
		File warDiffFileParent = new File(newPath, "META-INF");
		if(warDiffFileParent.exists()){
			File[] files = warDiffFileParent.listFiles();
			for(File file: files){
				if(!file.isDirectory() && file.getName().endsWith(FileType.DIFF.getType())){
					warDiffFile = file;
					break;
				}
			}
		}
		if(warDiffFile != null) {
			diffScanner = new DiffScanner(new File(oldPath), new File(newPath), warDiffFile);
		} else {
			throw new RuntimeException("war 不包含 .diff 差异文件");
		}

		Iterator<Scanned> backupIt = diffScanner.iterator();
		Executor executor = new UpperExecutor(backupDir);

		while(backupIt.hasNext()){
			if(!executor.backup(backupIt.next())){
				return false;
			}
		}
		if(!onlyBackup){
			Iterator<Scanned> excuteIt = diffScanner.iterator();
			while(excuteIt.hasNext()){
				Scanned scanned = excuteIt.next();
				updatedMap.put(scanned.getNewFile().getAbsolutePath(), scanned);
				if(!executor.execute(scanned)){
					return false;
				}
			}
		}

		//2、再根据遍历目录更新未被更新的文件
		Scanner<Scanned> dirScanner = new DefaultScanner(new File(oldPath), new File(newPath));

		Iterator<Scanned> dirScannerIt = dirScanner.iterator();
		Executor dirScannerExecutor = new UpperExecutor(backupDir);

		while(dirScannerIt.hasNext()){
			Scanned scanned = dirScannerIt.next();
			if(!updatedMap.containsKey(scanned.getNewFile().getAbsolutePath())){
				if(!dirScannerExecutor.backup(scanned)){
					return false;
				}
			}
		}
		if(!onlyBackup){
			Iterator<Scanned> dirScannerExcuteIt = dirScanner.iterator();
			while(dirScannerExcuteIt.hasNext()){
				Scanned scanned = dirScannerExcuteIt.next();
				if(!updatedMap.containsKey(scanned.getNewFile().getAbsolutePath())){
					if(!dirScannerExecutor.execute(scanned)){
						return false;
					}
				}
			}
		}

		return true;
	}
	/**
	 * 仅备份
	 * @return
	 */
	public boolean backup() throws IOException {
		if(isUpWar){
			upWar(true);
		} else {
			upDefault(true);
		}
		return true;
	}

	/**
	 * 仅恢复
	 * @param backupDir
	 * @return
	 */
	public static boolean recovery(String backupDir){
		Executor executor = new UpperExecutor(backupDir);
		return executor.recovery();
	}

	public static void main(String[] args){
		if(args == null || args.length == 0){
			printUsage();
			return;
		}
		try {
			Upper upper = null;
			boolean isScussess = false;
			if("up".equals(args[0]) && args.length == 3){
				upper = new Upper(args[1], args[2]);
				isScussess = upper.up();
			} else if("backup".equals(args[0]) && (args.length == 3 || args.length == 4)){
				if(args.length == 3){
					upper = new Upper(args[1], args[2]);
				} else if(args.length == 4){
					upper = new Upper(args[1], args[2], args[3]);
				}
				isScussess = upper.backup();
			} else if("recovery".equals(args[0]) && args.length == 2){
				isScussess = recovery(args[1]);
			}  else {
				LOGGER.error(String.format("用法错误： %s ", getCommond(args)));
				printUsage();
				return;
			}

			if(isScussess){
				LOGGER.info(String.format("更新成功： %s", getCommond(args)));
			} else {
				LOGGER.error(String.format("更新失败： %s", getCommond(args)));
			}
		} catch (Exception e){
			LOGGER.error("出错：", e);
		}



	}


	private static void printUsage(){
		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println("用法：");
		System.out.println("     upper up oldDir newPath                    执行更新，包括：备份、更新、恢复");
		System.out.println("或：");
		System.out.println("     upper backup oldDir newPath [backupPath]   执行备份，仅备份不做更新");
		System.out.println("                                                backupDir 可选，默认值为：oldDir_backup_yyyyMMddHHmmss");
		System.out.println("或：");
		System.out.println("     upper recovery backupDir                   执行恢复，根据指定备份文件恢复");
		System.out.println("其中：");
		System.out.println("     oldDir   要更新的文件夹");
		System.out.println("     newPath  更新包或文件夹，它的文件结构必须和oldDir的一致。更新包格式只能为.zip或.tar.gz或.war，");
		System.out.println("              如果包内只有一个文件夹且名字和oldDir相等，则认为是更新包的根路径");
		System.out.println("----------------------------------------------------------------------------------------------------");
	}


	private static String getCommond(String[] args){
		StringBuilder sb = new StringBuilder("upper");
		for (String str : args){
			sb.append(" ").append(str);
		}
		return sb.toString();
	}

}
