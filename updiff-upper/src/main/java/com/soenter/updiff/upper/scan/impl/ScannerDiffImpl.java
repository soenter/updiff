/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/10 15:36
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/10        Initailized
 */
package com.soenter.updiff.upper.scan.impl;

import com.soenter.updiff.common.DiffElement;
import com.soenter.updiff.common.DiffReader;
import com.soenter.updiff.upper.scan.Scaned;
import com.soenter.updiff.upper.scan.Scanner;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @ClassName ：com.soenter.updiff.upper.scan.impl.ScannerDiffImpl
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/10 15:36
 * @version 1.0.0
 *
 */
public class ScannerDiffImpl implements Scanner<DiffElement>{

	private File diffFile;

	private DiffReader diffReader;

	public ScannerDiffImpl (File diffFile) throws IOException {
		this.diffFile = diffFile;

		if(!this.diffFile.exists()){
			throw new IOException("diff 文件不存在");
		}
		try {
			diffReader = new DiffReader(this.diffFile);
		} catch (DocumentException e) {
			throw new IOException("diff 文件格式非法");
		}
	}

	public Iterator<DiffElement> iterator () {
		return diffReader.readAll().iterator();
	}
}
