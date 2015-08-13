/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/10 15:47
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/10        Initailized
 */
package com.sand.updiff.common;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.XMLReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName ：com.sand.updiff.common.DiffReader
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/10 15:47
 * @version 1.0.0
 *
 */
public class DiffReader {

	private XMLReader reader;

	private Document document;

	private Element rootElement;


	public DiffReader (File diffFile) throws DocumentException {

		SAXReader saxReader = new SAXReader();
		document = saxReader.read(diffFile);
		rootElement = document.getRootElement();
	}

	public List<DiffItem> readAll(){

		List<DiffItem> allEl = new ArrayList<DiffItem>();

		List<Element> group = rootElement.elements();

		for (Element g: group){
			List<Element> files = g.elements();
			String gName = g.attributeValue("name");
			for (Element f: files){
				allEl.add(new DiffItem(gName, f.attributeValue("change"), f.attributeValue("path")));
			}
		}

		return allEl;
	}

}
