package com.sand.updiff.upper.dom;

import com.sand.updiff.common.FileType;
import com.sand.updiff.common.utils.DomUtils;
import org.dom4j.Element;

import java.io.IOException;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/13 14:28
 * @since 1.0.0
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
