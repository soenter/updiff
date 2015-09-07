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

	private boolean isExcluded;

	private String path;

	private String packing;

	private String mainJavaGroup;

	private String[] mainResourceGroups;

	public DiffItem (
			String groupName,
			String changeName,
			boolean isExcluded,
			String path) {
		this.groupName = groupName;
		this.changeName = changeName;
		this.isExcluded = isExcluded;
		this.path = path;
	}

	public DiffItem (
			String groupName,
			String changeName,
			boolean isExcluded,
			String path,
			String packing,
			String mainJavaGroup,
			String[] mainResourceGroups) {
		this.groupName = groupName;
		this.changeName = changeName;
		this.isExcluded = isExcluded;
		this.path = path;
		this.packing = packing;
		this.mainJavaGroup = mainJavaGroup;
		this.mainResourceGroups = mainResourceGroups;
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

	public boolean isExcluded () {
		return isExcluded;
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

	public String getPacking () {
		return packing;
	}

	public String getMainJavaGroup () {
		return mainJavaGroup;
	}

	public String[] getMainResourceGroups () {
		return mainResourceGroups;
	}
}
