/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/10 17:05
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/10        Initailized
 */
package com.sand.updiff.upper.update.impl;

import com.sand.updiff.upper.update.Executor;
import com.sand.updiff.upper.update.Task;
import com.sand.updiff.upper.update.Update;
import com.sand.updiff.upper.update.UpdateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Stack;

/**
 *
 * @ClassName ：com.sand.updiff.upper.update.UpperExecutor
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/10 17:05
 * @version 1.0.0
 *
 */
public class UpperExecutor implements Executor{

	private static final Logger LOGGER = LoggerFactory.getLogger(UpperExecutor.class);

	private int retryNum = 3;
	private int retryIndex = 0;

	private long sleepTime = 5000;

	public UpperExecutor () {
	}

	public UpperExecutor (int retryNum) {
		this.retryNum = retryNum;
	}

	public void recovery() throws IOException {

	}

	public boolean execute (Task task) {
		try {
			Update update = UpdateFactory.create(task.getScand(), task.getBackupDir());
			update.backup();
			update.execute();
			return true;
		} catch (Exception e) {
			LOGGER.error("更新过程出现异常执行恢复：", e);
			try {
				recovery();
				LOGGER.error("恢复成功");
			} catch (IOException e1) {
				LOGGER.error("恢复出现异常：", e1);
			}
			return false;
		}
	}
}
