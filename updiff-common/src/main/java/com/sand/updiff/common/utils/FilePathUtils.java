package com.sand.updiff.common.utils;

import org.apache.maven.model.Resource;
import org.codehaus.plexus.util.SelectorUtils;

import java.io.File;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/13 16:55
 * @since 1.0.0
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

	public static boolean isFiltered(Resource resource, String path){

		List<String> excludes = resource.getExcludes();
		if(excludes != null && !excludes.isEmpty()){
			for (String exclude: excludes){
				if(SelectorUtils.matchPath(exclude, path, true)){
					return true;
				}
			}
		}

		boolean isInclude = false;
		List<String> includes = resource.getIncludes();
		if(includes != null && !includes.isEmpty()){
			for (String include: includes){
				if(SelectorUtils.matchPath(include, path, true)){
					isInclude = true;
					break;
				}
			}
		} else {
			isInclude = true;
		}

		return !isInclude;

	}

	public static String getDiffPrefixPath(String basePath, String path){
		if(path.indexOf(basePath) == 0){
			path = path.substring(basePath.length()).replace("\\", "/");
		}
		if(path.indexOf("/") == 0){
			path = path.substring(1);
		}
		return path;
	}
}
