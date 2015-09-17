package com.sand.updiff.common;

/**
 * @author : sun.mt
 * @date : 2015/8/11 9:09
 * @since 1.0.0
 */
public enum FileType {
	CLASS(".class"),
	JAR(".jar"),
	DELETE(".delete"),
	DIFF(".diff"),
	ZIP(".zip"),
	TAR_GZ(".tar.gz"),
	WAR(".war"),
	BAK_XML(".backup.xml"),
	REDOLOG(".redolog");

	String type;
	FileType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}
}
