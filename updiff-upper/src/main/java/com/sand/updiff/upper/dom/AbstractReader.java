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
 * @date : 2015/8/14 15:30
 * @since 1.0.0
 *
 */
public abstract class AbstractReader {

	protected Document document;

	protected Element rootElement;

	public AbstractReader (String backupDir, FileType fileType) throws IOException {
		File backupDirFile = new File(backupDir);

		SAXReader saxReader = new SAXReader();
		try {
			document = saxReader.read(new File(backupDirFile, backupDirFile.getName() + fileType.getType()));
		} catch (DocumentException e) {
			throw new IOException(e);
		}
		rootElement = document.getRootElement();
	}

	public abstract List<? extends  Item> readAll() ;
}
