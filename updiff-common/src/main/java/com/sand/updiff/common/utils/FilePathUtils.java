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
import java.util.Stack;

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

	/**
	 * 获取后缀相同的路径
	 * @param path1
	 * @param path2
	 * @return
	 */
	public static String getSameSubfixPath(String path1, String path2){

		if(path1.equals(path2)) return path1;

		path1 = path1.replace("\\", "/");
		path2 = path2.replace("\\", "/");

		Stack<String> path1Stack = new Stack<String>();
		for(String path: path1.split("/")){
			path1Stack.push(path);
		}

		Stack<String> path2Stack = new Stack<String>();
		for(String path: path2.split("/")){
			path2Stack.push(path);
		}

		StringBuilder sb = new StringBuilder();
		while(!path1Stack.empty()){
			String p1 = path1Stack.pop();
			String p2 = path2Stack.pop();
			if(!p1.equals(p2)){
				if(sb.length() > 0){
					return sb.toString().substring(1);
				} else {
					return null;
				}
			} else {
				sb.insert(0, "/" + p1);
			}
		}

		return null;
	}
}
