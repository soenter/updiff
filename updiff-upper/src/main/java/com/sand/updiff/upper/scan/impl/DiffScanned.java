/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/10 14:45
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/10        Initailized
 */
package com.sand.updiff.upper.scan.impl;

import com.sand.updiff.common.ChangeType;
import com.sand.updiff.common.DiffItem;

import java.io.File;

/**
 *
 * @ClassName ：com.sand.updiff.upper.scan.impl.DiffScanned
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/10 14:45
 * @version 1.0.0
 *
 */
public class DiffScanned extends DefaultScanned {

	private DiffItem diffItem;

	public DiffScanned (File oldFile, File newFile, String relativePath, DiffItem diffItem) {
		super(oldFile, newFile, relativePath);
		this.diffItem = diffItem;
	}

	@Override
	public boolean isAddFile () {
		return ChangeType.ADD.name().equals(diffItem.getChangeName());
	}

	@Override
	public boolean isModifyFile () {
		return ChangeType.MODIFY.name().equals(diffItem.getChangeName());
	}

	@Override
	public boolean isDeleteFile () {
		return ChangeType.DELETE.name().equals(diffItem.getChangeName());
	}
}
