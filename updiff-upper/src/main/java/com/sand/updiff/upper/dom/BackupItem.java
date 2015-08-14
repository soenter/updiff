/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/13 14:29
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/13        Initailized
 */
package com.sand.updiff.upper.dom;

/**
 *
 * @ClassName ：com.sand.updiff.upper.dom.BackupItem
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/13 14:29
 * @version 1.0.0
 *
 */
public class BackupItem  implements Item{

	private String fromPath;

	private String toPath;

	public BackupItem (String fromPath, String toPath) {
		this.fromPath = fromPath;
		this.toPath = toPath;
	}

	public String getFromPath () {
		return fromPath;
	}

	public String getToPath () {
		return toPath;
	}
}
