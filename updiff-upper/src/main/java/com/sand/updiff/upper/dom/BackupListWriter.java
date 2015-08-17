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
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
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
		itemEl.addElement("from").setText(backupItem.getFromPath());
		itemEl.addElement("to").setText(backupItem.getToPath());
	}

}
