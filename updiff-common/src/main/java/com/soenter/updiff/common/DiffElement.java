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
package com.soenter.updiff.common;

/**
 *
 * @ClassName ：com.soenter.updiff.common.DiffElement
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/3 18:50
 * @version 1.0.0
 *
 */
public class DiffElement {

	private String newGroupName;

	private String oldGroupName;

	private String changeName;

	private String newPath;

	private String oldPath;

	public DiffElement (String newGroupName, String oldGroupName, String changeName, String newPath, String oldPath) {
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
}
