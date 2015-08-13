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

	private String newGroupName;

	private String oldGroupName;

	private String changeName;

	private String newPath;

	private String oldPath;

	public DiffItem (String newGroupName, String oldGroupName, String changeName, String newPath, String oldPath) {
		this.newGroupName = newGroupName;
		this.oldGroupName = oldGroupName;
		this.changeName = changeName;
		this.newPath = newPath;
		this.oldPath = oldPath;
	}

	public String getChangeName () {
		return changeName;
	}

	public String getNewPath () {
		return newPath;
	}

	public String getOldPath () {
		return oldPath;
	}

	public String getNewGroupName () {
		return newGroupName;
	}

	public void setNewGroupName (String newGroupName) {
		this.newGroupName = newGroupName;
	}

	public String getOldGroupName () {
		return oldGroupName;
	}

	public void setOldGroupName (String oldGroupName) {
		this.oldGroupName = oldGroupName;
	}


	public String getCompiledNewPath(){

		String subfix = ".java";
		if(newPath.endsWith(subfix)){
			return newPath.substring(0, newPath.length() - subfix.length()) + ".class";
		}
		return newPath;
	}

	@Override
	public String toString () {
		StringBuilder sb = new StringBuilder("[");

		sb.append("newGroupName: ").append(newGroupName).append(", ");
		sb.append("oldGroupName: ").append(oldGroupName).append(", ");
		sb.append("changeName: ").append(changeName).append(", ");
		sb.append("newPath: ").append(newPath).append(", ");
		sb.append("oldPath: ").append(oldPath);

		return sb.append("]").toString();
	}
}
