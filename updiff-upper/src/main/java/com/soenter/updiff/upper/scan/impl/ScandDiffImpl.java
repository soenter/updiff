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
package com.soenter.updiff.upper.scan.impl;

import com.soenter.updiff.common.ChangeType;
import com.soenter.updiff.common.DiffElement;
import com.soenter.updiff.upper.scan.Scaned;

import java.io.File;

/**
 *
 * @ClassName ：com.soenter.updiff.upper.scan.impl.ScandDiffImpl
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/10 14:45
 * @version 1.0.0
 *
 */
public class ScandDiffImpl extends ScanedImpl{

	private DiffElement diffElement;

	public ScandDiffImpl (File oldFile, File newFile, String relativePath, DiffElement diffElement) {
		super(oldFile, newFile, relativePath);
		this.diffElement = diffElement;
	}

	@Override
	public boolean isAddFile () {
		return ChangeType.ADD.name().equals(diffElement.getChangeName());
	}

	@Override
	public boolean isModifyFile () {
		return ChangeType.MODIFY.name().equals(diffElement.getChangeName());
	}

	@Override
	public boolean isDeleteFile () {
		return ChangeType.DELETE.name().equals(diffElement.getChangeName());
	}
}
