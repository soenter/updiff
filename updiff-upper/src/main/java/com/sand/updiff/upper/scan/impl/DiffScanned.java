package com.sand.updiff.upper.scan.impl;

import com.sand.updiff.common.ChangeType;
import com.sand.updiff.common.DiffItem;

import java.io.File;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/10 14:45
 * @since 1.0.0
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
