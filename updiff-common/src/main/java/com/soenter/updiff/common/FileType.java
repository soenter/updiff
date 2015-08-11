package com.soenter.updiff.common;

/**
 * @author : sun.mt@sand.com.cn
 * @version 1.0.0
 * @ClassName ：com.soenter.updiff.common.FileType
 * @Description :
 * @Date : 2015/8/11 9:09
 */
public enum FileType {
	CLASS(".class"),
	JAR(".jar"),
	DELETE(".delete"),
	DIFF(".diff");

	String type;
	FileType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}
}
