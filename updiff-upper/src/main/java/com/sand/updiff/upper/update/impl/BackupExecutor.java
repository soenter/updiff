/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/13 13:53
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/13        Initailized
 */
package com.sand.updiff.upper.update.impl;

import com.sand.updiff.upper.scan.Scaned;
import com.sand.updiff.upper.update.Executor;
import com.sand.updiff.upper.update.Task;
import com.sand.updiff.upper.update.Update;
import com.sand.updiff.upper.update.UpdateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 *
 * @ClassName ：com.sand.updiff.upper.update.impl.BackupExecutor
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/13 13:53
 * @version 1.0.0
 *
 */
public class BackupExecutor implements Executor{

	private static final Logger LOGGER = LoggerFactory.getLogger(BackupExecutor.class);

	public boolean execute (Task task) {

		Update update = null;
		try {
			Scaned scaned = task.getScand();
			update = UpdateFactory.create(scaned, task.getBackupDir());
			update.backup();
			return true;
		} catch (Exception e) {
			LOGGER.error("备份过程出现异常：", e);
		}
		return false;
	}


}
