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
package com.soenter.updiff.common;

import java.io.File;

/**
 *
 * @ClassName ：com.soenter.updiff.common.UpiffFileUtils
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/11 11:01
 * @version 1.0.0
 *
 */
public class UpiffFileUtils {


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

}
