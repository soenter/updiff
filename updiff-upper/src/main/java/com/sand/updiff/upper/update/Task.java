package com.sand.updiff.upper.update;

import com.sand.updiff.upper.scan.Scaned;

/**
 * @author : sun.mt@sand.com.cn
 * @version 1.0.0
 * @ClassName ：com.sand.updiff.upper.update.Task
 * @Description :
 * @Date : 2015/8/13 13:45
 */
public interface Task {

	Scaned getScand();

	String getBackupDir();
}
