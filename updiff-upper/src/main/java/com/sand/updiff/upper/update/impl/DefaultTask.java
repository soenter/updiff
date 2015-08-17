/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/13 13:48
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/13        Initailized
 */
package com.sand.updiff.upper.update.impl;

import com.sand.updiff.upper.scan.Scanned;
import com.sand.updiff.upper.update.Task;

/**
 *
 * @ClassName ：com.sand.updiff.upper.update.impl.DefaultTask
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/13 13:48
 * @version 1.0.0
 *
 */
public class DefaultTask implements Task{

	private Scanned scanned;

	private String backupDif;

	public DefaultTask (Scanned scanned, String backupDif) {
		this.scanned = scanned;
		this.backupDif = backupDif;
	}

	public Scanned getScand () {
		return scanned;
	}

	public String getBackupDir () {
		return backupDif;
	}
}
