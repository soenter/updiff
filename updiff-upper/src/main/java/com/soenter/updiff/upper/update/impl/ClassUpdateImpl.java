/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/7 16:35
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/7        Initailized
 */
package com.soenter.updiff.upper.update.impl;

import com.soenter.updiff.upper.scan.ScanedFile;
import com.soenter.updiff.upper.update.Update;

import java.io.IOException;

/**
 *
 * @ClassName ：com.soenter.updiff.upper.update.impl.ClassUpdateImpl
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/7 16:35
 * @version 1.0.0
 *
 */
public class ClassUpdateImpl extends UpdateImpl{



	public ClassUpdateImpl (ScanedFile scanedFile, String backupPath) throws IOException {
		super(scanedFile, backupPath);

		if(scanedFile.isDir() || !scanedFile.getNewFile().getName().endsWith(FileType.CLASS.getType())){
			throw new IOException("ClassUpdateImpl 只能处理以.class结尾的文件");
		}
	}

	public void backup () throws IOException {

	}

	public void recovery () throws IOException {

	}

	public void execute () throws IOException {

	}
}
