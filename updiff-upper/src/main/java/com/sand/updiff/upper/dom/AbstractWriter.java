/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/14 15:22
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/14        Initailized
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
 * @ClassName ：com.sand.updiff.upper.dom.AbstractWriter
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/14 15:22
 * @version 1.0.0
 *
 */
public abstract class AbstractWriter {


	protected XMLWriter writer;

	protected Document document;

	protected Element rootElement;

	public AbstractWriter (String backupDir, FileType fileType, String rootDomName) throws IOException {
		File backupDirFile = new File(backupDir);
		init(new File(backupDirFile, backupDirFile.getName() + fileType.getType()), rootDomName);
	}

	private void init(File savePath, String rootDomName) throws IOException {

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
			rootElement = document.addElement(rootDomName);
		}

		OutputFormat format = OutputFormat.createPrettyPrint();
		writer = new XMLWriter(new FileWriter(savePath), format);

	}

	public abstract void addItem(Item item);

	public void writeItem(Item item) throws IOException {
		addItem(item);
		write();
	}

	public void write() throws IOException{
		writer.write(document);
		writer.flush();
		writer.close();
	}
}
