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

import com.sand.updiff.upper.dom.RedologItem;
import com.sand.updiff.upper.dom.RedologReader;
import com.sand.updiff.upper.scan.Scanned;
import com.sand.updiff.upper.scan.impl.DiffScanned;
import com.sand.updiff.upper.scan.impl.RedologScanned;
import com.sand.updiff.upper.update.Executor;
import com.sand.updiff.upper.update.Update;
import com.sand.updiff.upper.update.UpdateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

	private String backupDir;

	public UpperExecutor (String backupDir) {
		this.backupDir = backupDir;
	}

	public UpperExecutor (int retryNum) {
		this.retryNum = retryNum;
	}

	public boolean backup(Scanned scanned){

		try {
			Update update = UpdateFactory.create(scanned, this.backupDir);
			update.backup();
			return true;
		} catch (Exception e) {
			LOGGER.error("备份出现异常：", e);
			return false;
		}
	}

	public boolean execute (Scanned scanned) {
		try {
			Update update = UpdateFactory.create(scanned, this.backupDir);
			update.execute();
			return true;
		} catch (Exception e) {
			LOGGER.error("更新过程出现异常执行恢复：", e);
			if(recovery()){
				LOGGER.error("恢复成功");
			}
			return false;
		}
	}

	public boolean recovery() {

		try {
			RedologReader redologReader = new RedologReader(this.backupDir);

			List<RedologItem> redologItems = redologReader.readAll();

			for (RedologItem item: redologItems){

				Scanned scanned = new RedologScanned(item);

				Update update = UpdateFactory.create(scanned, this.backupDir);

				update.recovery();
			}
			return true;
		} catch (Exception e){
			LOGGER.error("恢复出现异常：", e);
			return false;
		}
	}
}
