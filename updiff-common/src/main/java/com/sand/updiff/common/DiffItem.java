/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/3 18:50
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/3        Initailized
 */
package com.sand.updiff.common;

/**
 *
 * @ClassName ：com.sand.updiff.common.DiffItem
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/3 18:50
 * @version 1.0.0
 *
 */
public class DiffItem {

	private String groupName;

	private String changeName;

	private String path;

	public DiffItem (String groupName, String changeName, String path) {
		this.groupName = groupName;
		this.changeName = changeName;
		this.path = path;
	}

	public String getChangeName () {
		return changeName;
	}

	public String getPath () {
		return path;
	}


	public String getGroupName () {
		return groupName;
	}


	public String getCompiledNewPath(){

		String subfix = ".java";
		if(path.endsWith(subfix)){
			return path.substring(0, path.length() - subfix.length()) + ".class";
		}
		return path;
	}

	public void setGroupName (String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString () {
		StringBuilder sb = new StringBuilder("[");

		sb.append("groupName: ").append(groupName).append(", ");
		sb.append("changeName: ").append(changeName).append(", ");
		sb.append("path: ").append(path);

		return sb.append("]").toString();
	}
}
