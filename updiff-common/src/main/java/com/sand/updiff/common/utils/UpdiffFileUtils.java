/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/11 11:01
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/11        Initailized
 */
package com.sand.updiff.common.utils;

import com.sand.updiff.common.FileType;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * @ClassName ：com.sand.updiff.common.utils.UpdiffFileUtils
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/11 11:01
 * @version 1.0.0
 *
 */
public class UpdiffFileUtils {


	/**
	 * 是否是内部类文件
	 * @param file
	 * @return
	 */
	public static boolean isInnerClassFile(File file){
		return file.getName().endsWith(FileType.CLASS.getType()) && file.getName().contains("$");
	}

	/**
	 * 是否是控制删除文件
	 * @param file
	 * @return
	 */
	public static boolean isControlDeleteFile(File file){
		return file.getName().endsWith(FileType.DELETE.getType());
	}

	/**
	 * 转换控制文件
	 * @param file
	 * @return
	 */
	public static File castControlFile(File file){
		if(isControlDeleteFile(file)){
			return new File(file.getParent(), file.getName().substring(0, file.getName().length() - FileType.DELETE.getType().length()));
		}
		return file;
	}

	/**
	 * 提取差异文件
	 * @param jar
	 * @return
	 * @throws IOException
	 */
	public static File readDiffFileFromJar(File jar){
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jar);

			String diffFileName = jar.getName().substring(0, jar.getName().length() - FileType.JAR.getType().length()) + FileType.DIFF.getType();
			JarEntry entry = jarFile.getJarEntry("META-INF/" + diffFileName);
			if(entry == null) return null;

			File diffFile = new File(jar.getParent(), diffFileName);
			FileUtils.copyInputStreamToFile(jarFile.getInputStream(entry), diffFile);
			return diffFile;
		} catch (IOException e) {
			return null;
		}
	}
	public static boolean hasDiffFile(File jar){
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jar);
			Enumeration<JarEntry> entryEnumeration =  jarFile.entries();
			while (entryEnumeration.hasMoreElements()){
				JarEntry entry = entryEnumeration.nextElement();
				if(entry.getName().startsWith("META-INF/") && entry.getName().endsWith(FileType.DIFF.getType())){
					return true;
				}
			}
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	public static Stack<File> mkdirs(File file){
		if(file.exists()){
			return null;
		}
		Stack<File> files = new Stack<File>();

		files.push(file);

		File parent = file.getParentFile();
		while(parent != null && !parent.exists()){
			files.push(parent);
			parent = parent.getParentFile();
		}

		if(!file.mkdirs()){
			return null;
		}

		return files;
	}

	public static List<File> getDiffFiles(File parentFile){
		if(parentFile.exists()){
			List<File> warDiffFile = new ArrayList<File>();
			File[] files = parentFile.listFiles();
			for(File file: files){
				if(!file.isDirectory() && file.getName().endsWith(FileType.DIFF.getType())){
					warDiffFile.add(file);
				}
			}
			return warDiffFile;
		}
		return null;
	}


}
