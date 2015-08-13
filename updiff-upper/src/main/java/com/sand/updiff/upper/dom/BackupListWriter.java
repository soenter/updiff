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
public class BackupListWriter {

	private XMLWriter writer;

	private Document document;

	private Element rootElement;

	public BackupListWriter (String backupDir) throws IOException {
		File backupDirFile = new File(backupDir);
		init(new File(backupDirFile, backupDirFile.getName() + FileType.BAK_XML.getType()));
	}

	private void init(File savePath) throws IOException {

		if(savePath.exists()){
			SAXReader saxReader = new SAXReader();
			try {
				document = saxReader.read(savePath);
			} catch (DocumentException e) {
				throw new IOException( e);
			}
			rootElement = document.getRootElement();
		} else {
			document = DocumentFactory.getInstance().createDocument("utf-8");
			rootElement = document.addElement("backups");
		}

		OutputFormat format = OutputFormat.createPrettyPrint();
		writer = new XMLWriter(new FileWriter(savePath), format);

	}

	public void addItem(BackupItem item){
		Element itemEl = rootElement.addElement("item");
		itemEl.addAttribute("from", item.getFromPath());
		itemEl.addAttribute("to", item.getToPath());
	}

	public void writeItem(BackupItem item) throws IOException {
		addItem(item);
		write();
	}

	public void write() throws IOException{
		writer.write(document);
		writer.flush();
		writer.close();
	}

}
