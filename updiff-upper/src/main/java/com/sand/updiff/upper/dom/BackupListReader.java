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
 * @author : sun.mt
 * @date : 2015/8/13 15:48
 * @since 1.0.0
 *
 */
public class BackupListReader extends AbstractReader{

	public BackupListReader (String backupDir) throws IOException {
		super(backupDir, FileType.REDOLOG);
	}

	public List<Item> readAll(){

		List<Item> allEl = new LinkedList<Item>();

		List<Element> els = rootElement.elements();

		for (Element e: els){
			allEl.add(new BackupItem(e.elementText("from"), e.elementText("to")));
		}
		return allEl;
	}
}
