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
package com.sand.updiff.upper.update;

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
public interface Executor {

	boolean execute(Task task);
}
