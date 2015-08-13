/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/13 15:48
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
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.XMLReader;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @ClassName ：com.sand.updiff.upper.dom.BackupListReader
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/13 15:48
 * @version 1.0.0
 *
 */
public class BackupListReader {

	private XMLReader reader;

	private Document document;

	private Element rootElement;

	public BackupListReader (String backupDir) throws IOException {
		File backupDirFile = new File(backupDir);

		SAXReader saxReader = new SAXReader();
		try {
			document = saxReader.read(new File(backupDirFile, backupDirFile.getName() + FileType.BAK_XML.getType()));
		} catch (DocumentException e) {
			throw new IOException(e);
		}
		rootElement = document.getRootElement();
	}

	public List<BackupItem> readAll(){

		List<BackupItem> allEl = new LinkedList<BackupItem>();

		List<Element> els = rootElement.elements();

		for (Element e: els){
			allEl.add(new BackupItem(e.attributeValue("from"), e.attributeValue("to")));
		}

		return allEl;
	}
}
