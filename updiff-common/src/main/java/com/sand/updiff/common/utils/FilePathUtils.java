/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/13 16:55
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/13        Initailized
 */
package com.sand.updiff.common.utils;

import java.io.File;

/**
 *
 * @ClassName ：com.sand.updiff.common.utils.FilePathUtils
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/13 16:55
 * @version 1.0.0
 *
 */
public class FilePathUtils {

	/**
	 * 获取工作目录
	 * @return
	 */
	public static String getWorkingPath(){
		return new File(".").getAbsolutePath();
	}

	public static String getAbstractePath(String relativePath){
		return new File(".").getAbsolutePath() + File.separator + relativePath;
	}
}
