package com.sand.updiff.upper.update.impl;

import com.sand.updiff.upper.dom.RedologItem;
import com.sand.updiff.upper.dom.RedologReader;
import com.sand.updiff.upper.scan.Scanned;
import com.sand.updiff.upper.scan.impl.RedologScanned;
import com.sand.updiff.upper.update.Executor;
import com.sand.updiff.upper.update.Update;
import com.sand.updiff.upper.update.UpdateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/10 17:05
 * @since 1.0.0
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

			//倒序恢复
			for(int i = redologItems.size() - 1; i >= 0; i --){

				RedologItem item = redologItems.get(i);
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
