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
package com.soenter.updiff.upper.update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName ：com.soenter.updiff.upper.update.Executor
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/10 17:05
 * @version 1.0.0
 *
 */
public class Executor {

	private List<Update> updates;

	private int retryNum = 3;

	public Executor () {
		this.updates = new ArrayList<Update>();
	}

	public Executor (int retryNum) {
		this.retryNum = retryNum;
		this.updates = new ArrayList<Update>();
	}

	public Executor execute(Update update) throws IOException {
		updates.add(update);
		update.backup();
		update.execute();
		return this;
	}

	public void recovery() throws IOException {

		for (Update up: updates){
			try {
				up.recovery();
			} catch (Exception e){
				retryNum --;
				if(retryNum < 0){
					throw new IOException(e);
				}

				try {
					Thread.currentThread().sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
