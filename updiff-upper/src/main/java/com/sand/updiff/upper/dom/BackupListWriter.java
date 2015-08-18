/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/13 14:28
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/13        Initailized
 */
package com.sand.updiff.upper.dom;

import com.sand.updiff.common.FileType;
import com.sand.updiff.common.utils.DomUtils;
import org.dom4j.Element;

import java.io.IOException;

/**
 *
 * @ClassName ：com.sand.updiff.upper.dom.BackupListWriter
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/13 14:28
 * @version 1.0.0
 *
 */
public class BackupListWriter extends AbstractWriter{

	public BackupListWriter (String backupDir) throws IOException {
		super(backupDir,FileType.BAK_XML, "backups");
	}

	public void addItem(Item item){
		BackupItem backupItem = (BackupItem)item;
		Element itemEl = rootElement.addElement("item");

		DomUtils.setElementText(itemEl.addElement("from"), backupItem.getFromPath());
		DomUtils.setElementText(itemEl.addElement("to"), backupItem.getToPath());
	}

}
